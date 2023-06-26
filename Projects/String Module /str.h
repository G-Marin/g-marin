/*--------------------------------------------------------------------*/
/* str.h                                              */
/* Author: Gabriel Marin                                              */
/*--------------------------------------------------------------------*/

#include <stddef.h>
#include <stdio.h>
#include <assert.h>
#include <stdlib.h>

#ifndef STR_INCLUDED
#define STR_INCLUDED 


/*Return length of string pcSrc*/
size_t Str_getLength(const char *pcSrc);

/*Return copies string from pcSrc to pcDest. */
char *Str_copy(char *pcDest, const char *pcSrc);

/*Concatenates string pcSrc to the end of string pcDest. Returns new concatenated string.*/
char *Str_concat(char *pcDest, const char *pcSrc);

/*Returns result of comparison between string pcDest and pcSrc*/
int Str_compare(const char *pcDest, const char *pcSrc);

/*Searches for an occurence of substring needle in the string haystack. Returns where that occurence occurs or NULL if it does not exist*/
char *Str_search(const char *haystack, const char *needle);

#endif
