/*--------------------------------------------------------------------*/
/* symtablelist.c                                                     */
/* Author: Gabriel Marin                                               */
/*--------------------------------------------------------------------*/


#include <assert.h>
#include <stdlib.h>
#include "symtable.h"
#include <string.h>
#include <stdio.h>

/* SymTable structure as defined in the interface the first Node in the collection of bindings of keys and values. Also holds the current number of nodes/bindings in the collection */

struct SymTable {

  /* The first binding/node in the collection of bindings*/
  struct Node *first;

  /* Current amount of bindings/nodes in the collection */
  size_t length;
};

/* Node structure contains a string key, a void* value that can pointer to any data type, and a pointer to the next Node.*/

struct Node{

  /* A pointer to a string that holds the key*/
  const char* key;

  /* A pointer to the value that could be of any data type*/
  const void* value;

  /* The pointer to the next Node*/
  struct Node *next;
};

SymTable_T SymTable_new(void){

 SymTable_T oSymTable;
 size_t length = 0;

  oSymTable = calloc(1, sizeof(struct SymTable));
  assert(oSymTable != NULL);

  oSymTable->first = NULL;
  oSymTable->length = length;
  return oSymTable;
}

void SymTable_free(SymTable_T oSymTable){
    
  struct Node *current;
  struct Node *next;   
  assert(oSymTable != NULL);

  if(oSymTable->first == NULL){
    free(oSymTable->first);
    free(oSymTable);
    return;
  }

  current = oSymTable->first;

  while(current != NULL){
    next = current->next;
    free((char *)current->key);
    free(current);
    current = next;   
  }

  free(oSymTable);

}


size_t SymTable_getLength(SymTable_T oSymTable){
  
  size_t length;
  length = oSymTable->length;
  return length;
}


int SymTable_put(SymTable_T oSymTable, const char *pcKey, const void *pvValue) {

  struct Node *current;
  struct Node *new;
  size_t keyLength;
  size_t length;
  char *keyCopy;

  assert(pcKey != NULL);

  current = oSymTable -> first;
  length = oSymTable->length;
  
  while(current != NULL){

    if(strcmp(current->key, pcKey) == 0){
      return 0;
    }
    current = current->next;
  }
    
  
  length++;
  oSymTable->length = length;
     
  new = calloc(1, sizeof(struct Node));
  if(new == NULL){
    return 0;
  }

  keyLength = strlen(pcKey);
  keyLength += 1;
  keyCopy = malloc((keyLength * sizeof(char)));

  if(keyCopy == NULL){
    free(new);
    return 0;
  }

  new->value = pvValue;
  new->next = oSymTable->first;

  keyCopy = strcpy(keyCopy, pcKey);
      
  new->key = (const char*) keyCopy;
  oSymTable->first = new;   
  
  return 1; 
}




void *SymTable_replace(SymTable_T oSymTable, const char *pcKey, const void *pvValue){
 
  struct Node *current;
  void* oldValue;
  int found = 0;

  current = oSymTable -> first;

  assert(pcKey != NULL);
  
  if(current == NULL){
    return NULL;
  }

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
  current -> value = pvValue;
  return oldValue;
}



int SymTable_contains(SymTable_T oSymTable, const char *pcKey){

  struct Node *current;
  current = oSymTable -> first;
  
  assert(pcKey != NULL);

    if(current == NULL){
      return 0;
    }

    while(current != NULL){

      if(strcmp(current->key, pcKey) == 0){
        return 1;
      }

      current = current->next;
    }

    return 0;    
}


void *SymTable_get(SymTable_T oSymTable, const char *pcKey){

  struct Node *current;
  void* value;
  int found = 0;

  current = oSymTable -> first;

  assert(pcKey != NULL);
  
  if(current == NULL){
    return NULL;
  }

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

  struct Node *current;
  struct Node *next;
  struct Node *prev;
  size_t length;
  int counter = 0;
  int found = 0;
  void* value;

  current = oSymTable->first;
  prev = oSymTable->first;
  length = oSymTable->length;

  assert(pcKey != NULL);
    
  if(current == NULL){
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
    oSymTable->first = current->next;
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


void SymTable_map(SymTable_T oSymTable,void (*pfApply)(const char *pcKey, void *pvValue, void *pvExtra), const void *pvExtra){


  struct Node *current;
  current = oSymTable -> first;

  assert(pfApply != NULL);

  while(current != NULL){
  
    (*pfApply)(current->key, (void*) current->value, (void*) pvExtra);
    current = current->next;

  }
}
