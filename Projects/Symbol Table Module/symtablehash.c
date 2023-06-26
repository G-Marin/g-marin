/*--------------------------------------------------------------------*/
/* symtablehash.c                                                     */
/* Author: Gabriel Marin                                               */
/*--------------------------------------------------------------------*/

#include <assert.h>
#include <stdlib.h>
#include "symtable.h"
#include <string.h>
#include <stdio.h>

/* Array that holds the possible number of buckets that can be used to Hash */

static const size_t auBucketCounts[] = {509, 1021, 2039, 4093, 8191, 16381, 32749, 65521};


/* Binding Structure contains a string key, a void* value that can pointer to any data type, and a pointer to the next Binding.*/

struct Binding{

   /* A pointer to a string that holds the key*/
   const char* key;

   /* A pointer to the value that could be of any data type*/
   const void* value;

   /* The pointer to the next Binding*/
   struct Binding *next;
};

/* SymTable structure as defined in the interface the first Node in the collection of bindings of keys and values. Also holds the current number of nodes/bindings in the collection */

struct SymTable {

   /* The start of the hash table */
   struct Binding **tableStart;

   /* The current number of buckets that are being used to hash*/
   size_t bucketCountIndex;

   /* Current amount of bindings/nodes in the collection */
   size_t length;
};

/* Return a hash code for pcKey that is between 0 and uBucketCount-1,
   inclusive. */

static size_t SymTable_hash(const char *pcKey, size_t uBucketCount)
{
   const size_t HASH_MULTIPLIER = 65599;
   size_t u;
   size_t uHash = 0;

   assert(pcKey != NULL);

   for (u = 0; pcKey[u] != '\0'; u++)
      uHash = uHash * HASH_MULTIPLIER + (size_t)pcKey[u];

   return uHash % uBucketCount;
}

/* Expands table when number of bindings exceeds buckets in oSymTable object */

static void SymTable_expand(SymTable_T oSymTable){

   struct Binding* current; 
   struct Binding* next;
   struct Binding* newCurrent;
   struct Binding** firstNewTable;
   struct Binding** newTable;
   struct Binding** oldTable;
   size_t bucketCountIndex;
   size_t oldBucketCount;
   size_t bucketCount;
   size_t hashValue;
   size_t i;
   const char* key;

   bucketCountIndex = oSymTable->bucketCountIndex;
   oldBucketCount = auBucketCounts[bucketCountIndex];

   bucketCountIndex += 1; 

   oSymTable->bucketCountIndex = bucketCountIndex;
   bucketCount = auBucketCounts[bucketCountIndex];

   newTable = calloc(bucketCount, sizeof(struct Binding*));
   assert(newTable != NULL);
   oldTable = oSymTable->tableStart;

   for(i = 0; i < oldBucketCount; i++){

      if(*(oldTable + i) != NULL){ 

         current = *(oldTable + i);

         while(current != NULL){

            key = current->key;
            next = current->next;
            
            hashValue = SymTable_hash(key, bucketCount);

            firstNewTable = (newTable + hashValue);
            newCurrent = *firstNewTable;

            if(newCurrent != NULL){
               current->next = newCurrent;
               newCurrent = current;
               *firstNewTable = newCurrent;
            } 

            else{
               newCurrent = current;
               newCurrent->next = NULL;
               *firstNewTable = newCurrent;
            }
            current = next;
         }
      }
   }

   free(oSymTable->tableStart);
   oSymTable->tableStart = newTable;
}
      
SymTable_T SymTable_new(void){

   SymTable_T oSymTable;
   struct Binding** tableStart;
   size_t length = 0;
   
   oSymTable = calloc(1, sizeof(struct SymTable));
   tableStart = calloc(auBucketCounts[0], sizeof(struct Binding*));

   if(oSymTable == NULL || tableStart == NULL){
      return NULL;
   }


   oSymTable->length = length;
   oSymTable->bucketCountIndex = 0;
   oSymTable->tableStart = tableStart;

   return oSymTable;
}



void SymTable_free(SymTable_T oSymTable){

   struct Binding** tablePointer;
   struct Binding* current;
   struct Binding* next; 
   size_t bucketCount;
   size_t i; 

   bucketCount = auBucketCounts[oSymTable->bucketCountIndex];

   assert(oSymTable != NULL);

   tablePointer = oSymTable->tableStart; 

   for(i = 0; i < bucketCount; i++){

      if(*(tablePointer + i) != NULL){ 

         current = *(tablePointer + i);

         while(current != NULL){

            next = current->next;
            free((char *)current->key);
            free(current);
            current = next;
         }  
      }
   }
   free(oSymTable->tableStart);
   free(oSymTable); 
}





size_t SymTable_getLength(SymTable_T oSymTable){

   size_t length;  
   length = oSymTable->length;
   return length;
}



int SymTable_put(SymTable_T oSymTable,
   const char *pcKey, const void *pvValue){

   enum {MAXNUMBEROFBUCKETS = 65522};
   size_t hashValue;
   size_t length;
   size_t keyLength;
   size_t bucketCount;
   struct Binding** tablePointer;
   struct Binding** first;
   struct Binding* current; 
   struct Binding* newBinding;
   int iSuccessful;
   char *keyCopy;
   
   assert(pcKey != NULL);

   length = oSymTable->length;
   bucketCount = auBucketCounts[oSymTable->bucketCountIndex];


   hashValue = SymTable_hash(pcKey, bucketCount);

   tablePointer = oSymTable->tableStart; 
   first = (tablePointer + hashValue);
   current = *first;

   while(current != NULL){

      if(strcmp(current->key, pcKey) == 0){
         return 0;
      }

      current = current->next;
   }
   length++;

   if (bucketCount < length && length < MAXNUMBEROFBUCKETS) {
         SymTable_expand(oSymTable);
         iSuccessful = SymTable_put(oSymTable, pcKey, pvValue);
         return iSuccessful;
      }
   

   oSymTable->length = length;
   newBinding = calloc(1, sizeof(struct Binding));

   if(newBinding == NULL){
      return 0;
   }

   keyLength = strlen(pcKey);
   keyLength += 1;
   keyCopy = malloc((keyLength) * sizeof(char));
   keyCopy = strcpy(keyCopy, pcKey);

   if(keyCopy == NULL){
      free(newBinding);
      return 0;
   }

   newBinding->value = pvValue;
   newBinding->next = *first;
   newBinding->key = (const char*) keyCopy;

   *first = newBinding;
   return 1;
}



void *SymTable_replace(SymTable_T oSymTable,
   const char *pcKey, const void *pvValue){

   size_t hashValue;
   size_t bucketCount;
   struct Binding** tablePointer;
   struct Binding* current; 
   struct Binding** first;
   void* oldValue;
   int found = 0;
   
   assert(pcKey != NULL);

   bucketCount = auBucketCounts[oSymTable->bucketCountIndex];
   hashValue = SymTable_hash(pcKey, bucketCount);
   tablePointer = oSymTable->tableStart; 
   first = (tablePointer + hashValue);

   current = *first;

   while(current != NULL){

      if(strcmp(current->key, pcKey) == 0){
         found = 1;
         break;
      }

      current = current->next;
   }

   if(found == 0){
      return NULL;
   }
    
   oldValue = (void *) current->value;
   current->value = pvValue;
   return oldValue;
}





int SymTable_contains(SymTable_T oSymTable, const char *pcKey){

   size_t hashValue;
   size_t bucketCount;
   struct Binding** tablePointer;
   struct Binding* current; 
   struct Binding** first;
   
   assert(pcKey != NULL);

   bucketCount = auBucketCounts[oSymTable->bucketCountIndex];
   hashValue = SymTable_hash(pcKey, bucketCount);

   tablePointer = oSymTable->tableStart; 
   first = (tablePointer+hashValue);

   if(*first == NULL){
      return 0;
   } 
   
   current = *first;

   while(current != NULL){

      if(strcmp(current->key, pcKey) == 0){
         return 1;
      }
      current = current->next;
   }

   return 0;


}


void *SymTable_get(SymTable_T oSymTable, const char *pcKey){

   size_t hashValue;
   size_t bucketCount;
   struct Binding** tablePointer;
   struct Binding* current; 
   struct Binding** first;
   void* value;
   int found = 0;

   assert(pcKey != NULL);

   bucketCount = auBucketCounts[oSymTable->bucketCountIndex];
   hashValue = SymTable_hash(pcKey, bucketCount);

   tablePointer = oSymTable->tableStart; 

   first = (tablePointer + hashValue);

   current = *first;

   while(current != NULL){

      if(strcmp(current->key, pcKey) == 0){
         found = 1; 
         break;
      }
      current = current->next;
   }

   if(found == 0){
      return NULL;
   }
   
   value = (void *) current->value;
   return value;
}



void *SymTable_remove(SymTable_T oSymTable, const char *pcKey){

   size_t hashValue;
   size_t bucketCount;
   size_t length;
   struct Binding** tablePointer;
   struct Binding* current; 
   struct Binding* prev;
   struct Binding* next;
   struct Binding** first;
   void* value;
   int counter = 0;
   int found = 0;

   length = oSymTable->length;

   assert(pcKey != NULL);

   bucketCount = auBucketCounts[oSymTable->bucketCountIndex];
   hashValue = SymTable_hash(pcKey, bucketCount);

   tablePointer = oSymTable->tableStart; 

   first = (tablePointer + hashValue);

   current = *first;
   prev = *first;

   if (current == NULL){
      return NULL;
   }

   /*Keeps track of previous to correctly set pointers after binding has been removed*/

   while(current != NULL){

      if(strcmp(current->key, pcKey) == 0){
         found = 1;
         break;
      }

      current = current->next;

      if(counter != 0){
        prev = prev->next;
      }
      counter++;
   }

   if(found == 0){
      return NULL;
   }

   value = (void *) current->value;
   length--;
   oSymTable->length = length;

   if(prev == current){
      *first = current->next;
      free((char*) current->key);
      free(current);
   } 
    
   else{
      next = current->next;
      free((char*) current->key);
      free(current);
      prev->next = next;
   }
   return value;
}




void SymTable_map(SymTable_T oSymTable,
   void (*pfApply)(const char *pcKey, void *pvValue, void *pvExtra),
   const void *pvExtra){

   struct Binding** tablePointer;
   struct Binding* current; 
   size_t bucketCount;
   size_t i; 

   bucketCount = auBucketCounts[oSymTable->bucketCountIndex];

   assert(pfApply != NULL);

   tablePointer = oSymTable->tableStart; 

   for(i = 0; i < bucketCount; i++){

      if(*(tablePointer + i) != NULL){ 

         current = *(tablePointer + i);
         while(current != NULL){
            (*pfApply)(current->key, (void*) current->value, (void*) pvExtra);
            current = current->next;
         }
      }
   }
}
