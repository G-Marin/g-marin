/*--------------------------------------------------------------------*/
/* stra.c                                            */
/* Author: Gabriel Marin                                              */
/*--------------------------------------------------------------------*/

#include "str.h"
#include <stdio.h>
#include <ctype.h>
#include <stddef.h>
#include <assert.h> 
#include <stdlib.h>


size_t Str_getLength(const char pcSrc[])
{
   size_t uLength = 0;
   assert(pcSrc != NULL);
   while (pcSrc[uLength] != '\0'){
      uLength++;
   }
   return uLength;
}

char *Str_copy(char pcDest[], const char pcSrc[]){

    size_t c = 0;

    assert(pcSrc != NULL && pcDest != NULL);
        while(pcSrc[c] != '\0'){
            pcDest[c] = pcSrc[c];
            c++;
        }

        pcDest[c] = '\0';

    return pcDest;
}


char *Str_concat(char pcDest[], const char pcSrc[]){


    size_t c = 0;
    int endOfDestination = 0;
    int endOfSource = 0;
    int sourceIndex = 0;
    
    assert(pcSrc != NULL && pcDest != NULL);

    while(endOfSource == 0 || endOfDestination == 0){
        
    
        if(pcDest[c] == '\0'){
            endOfDestination = 1;
        }

        if(pcSrc[sourceIndex] == '\0'){
            endOfSource = 1;
        }

        if(endOfDestination == 1){
            pcDest[c] = pcSrc[sourceIndex];
            sourceIndex++;
        }
        c++;
    } 

    return pcDest;    
}

int Str_compare(const char pcDest[], const char pcSrc[]){

    int c = 0;
    size_t lengthOne = Str_getLength(pcDest);
    size_t lengthTwo = Str_getLength(pcSrc);
    
    assert(pcDest != NULL && pcSrc != NULL);

    while(pcSrc[c] != '\0' || pcDest[c] != '\0'){

        if (pcDest[c] < pcSrc[c]){
            return -1;
        }

         if (pcDest[c] > pcSrc[c]){
            return 1;
        }

        c++;
    }   

    if(lengthOne == lengthTwo){
        return 0;
    } 

    if(lengthOne > lengthTwo){
        return 1;
    }

        return -1;
    }
 

char *Str_search(const char haystack[], const char needle[]){

    /*Keeps track of how many chars in the needle we have found*/
    int counter = 0;
    
    int c = 0;
    int index = 0;
    int possibleMatch = 0;
    char *pointer = NULL;

    assert(needle != NULL && haystack != NULL);


    /*Checks if needle is empty*/
    if(needle[index] == '\0'){
        return (char *) &haystack[c];
    }


    while(needle[index] != '\0' && haystack[c] != '\0'){

        if(counter != 0 && haystack[c] != needle[index]){
            c = c - (counter - 1); 
            index = 0; 
            counter = 0;
        }

        if(haystack[c] == needle[index]){
            counter++;
            index++;
        }

        if(counter == 1){
            possibleMatch = c; 
        }

        c++;
        }

        /*Check if haystack ended before needle ended*/
        if(haystack[c] == '\0' && needle[index] != '\0'){
            return pointer;
        }

        /*Needle was not found*/
        if(counter == 0){ 
            return pointer;
        }
        return (char *) &haystack[possibleMatch];    
    }
        

