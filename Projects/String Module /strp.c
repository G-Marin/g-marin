/*--------------------------------------------------------------------*/
/* strp.c                                            */
/* Author: Gabriel Marin                                              */
/*--------------------------------------------------------------------*/


#include "str.h"
#include <stddef.h>
#include <stdio.h>
#include <ctype.h>
#include <assert.h> 
#include <stdlib.h>


size_t Str_getLength(const char *pcSrc)
{
   const char *pcEnd;
   assert(pcSrc != NULL);
   pcEnd = pcSrc;
   while (*pcEnd != '\0')
      pcEnd++;
   return (size_t)(pcEnd - pcSrc);
}

char *Str_copy(char *pcDest, const char *pcSrc){

    char *source = (char*) pcSrc;
    char *destChange = pcDest;
    char *destStart = pcDest;

    assert(pcSrc != NULL && pcDest != NULL);

    while(*source != '\0'){
        *destChange = *source;

    destChange++;
    source++;
    }
    
    *destChange = '\0';
    return destStart;
}


char *Str_concat(char *pcDest, const char *pcSrc){

    int endOfDest = 0;
    int endofSource = 0;
    char *source = (char*) pcSrc;
    char *destChange = pcDest;
    char *destStart = pcDest;
    
    assert(pcSrc != NULL && pcDest != NULL);

   while(endofSource == 0 || endOfDest == 0){

        if(*destChange == '\0'){
            endOfDest = 1;
        }

        if(*source == '\0'){
            endofSource = 1;
        }

        if (endOfDest == 1){
            *destChange = *source;
            source++;
        }

        destChange++;
    }   

    return destStart;    
}


int Str_compare(const char *pcDest, const char *pcSrc){

    size_t lengthOne = Str_getLength(pcDest);
    size_t lengthTwo = Str_getLength(pcSrc);
    char *source = (char*) pcSrc;
    char *destChange = (char*) pcDest;

    assert(pcDest != NULL && pcSrc != NULL);
    
    while(*source != '\0' || *destChange != '\0'){

        if (*destChange < *source){
            return -1;
        }

         if (*destChange > *source){
            return 1;
        }

        destChange++;
        source++;
    }   

    /*Compares lenghts in case one string is smaller or larger*/
    if(lengthOne == lengthTwo){
        return 0;
    }

    if(lengthOne == lengthTwo){
        return 0;
    } 

    if(lengthOne > lengthTwo){
        return 1;
    }
        return -1;
    } 
    

char *Str_search(const char *haystack, const char *needle){

    /*Keeps track of how many chars in the needle we have found*/
    int counter = 0; 
   
    char *pointerHaystack = (char *) haystack;
    char *pointerNeedle= (char *) needle;
    char *possibleMatch = NULL;

    assert(haystack != NULL && needle != NULL);

    if(*needle == '\0'){
       return pointerHaystack;
    }

    while(*pointerHaystack != '\0' && *pointerNeedle!= '\0'){
    
        
        if(counter != 0 && *pointerHaystack != *pointerNeedle){
            pointerHaystack = pointerHaystack - (counter - 1); 
            pointerNeedle= pointerNeedle- counter; 
            counter = 0;
        }

        if(*pointerHaystack == *pointerNeedle){
            counter++;
            pointerNeedle++;
        }

        if(counter == 1){
            possibleMatch = pointerHaystack;
        }    
    
        pointerHaystack++;
        }


        /*Check if haystack ended before needle ended*/
        if(*pointerHaystack == '\0' && *pointerNeedle!= '\0'){
            return NULL;
        }
        /*Needle was not found*/
        if(counter == 0){
            return NULL;
        }

        return possibleMatch;    
    }

