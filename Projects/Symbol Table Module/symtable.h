/*--------------------------------------------------------------------*/
/* symtable.h                                                  */
/* Author: Gabriel Marin                                               */
/*--------------------------------------------------------------------*/

#include <stddef.h>
#ifndef SYMTABLE_INCLUDED
#define SYMTABLE_INCLUDED


/* A SymTable object is a Symbol Table collection of bindings which consist of a string key and a value of any type. SymTable_T is a SymTable object*/

typedef struct SymTable *SymTable_T;

/* Allocates space for a new SymTable object and returns it, or NULL if insufficient memory is available*/

SymTable_T SymTable_new(void);

/* Frees the memory allocated by oSymTable object including any bindings that it may hold. */

  void SymTable_free(SymTable_T oSymTable);

/* Returns number of bindings in oSymTable object */

  size_t SymTable_getLength(SymTable_T oSymTable);

/* Inserts new string key, pcKey, and value, pvValue, binding to oSymTable object if binding does not exist already. Returns 0 if binding exists*/

  int SymTable_put(SymTable_T oSymTable,
     const char *pcKey, const void *pvValue);

/* Replaces existing binding with pcKey with new value, pvValue. Returns NULL if key does not exist in oSymTable object*/

  void *SymTable_replace(SymTable_T oSymTable,
     const char *pcKey, const void *pvValue);

/* Checks if oSymTable object contains binding with pcKey. Returns 1 (TRUE) or 0 (FALSE) */

int SymTable_contains(SymTable_T oSymTable, const char *pcKey);

/* Returns the value of binding in oSymTable object with pcKey or NULL if the binding does not exist */

void *SymTable_get(SymTable_T oSymTable, const char *pcKey);

/* Removes the binding with key, pcKey, and returns the value of the binding removed. Returns NULL if binding does not exist in oSymTable object */

void *SymTable_remove(SymTable_T oSymTable, const char *pcKey);

/* Applies pfApply function to all bindings in oSymTable object with pvExtra being an optional extra parameter */

void SymTable_map(SymTable_T oSymTable,
  void (*pfApply)(const char *pcKey, void *pvValue, void *pvExtra),
  const void *pvExtra);

#endif
