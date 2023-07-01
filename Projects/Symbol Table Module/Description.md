# Symbol Table Module

## Description
The Symbol Table Module is a project that involves creating an Abstract Data Type (ADT) named SymTable. This ADT represents a symbol table and offers two implementations: one using a linked list and another using a hash table. The purpose of this module is to provide a generic symbol table that can store key-value pairs of any type. It allows users to add, replace, retrieve, and remove key-value bindings.

## Languages & Tools
- C programming language

## File Descriptions

- `symtablelist.c`: Implementation of the SymTable ADT using a linked list for efficient memory management and constant-time length retrieval.
- `symtablehash.c`: Implementation of the SymTable ADT using a hash table with separate chaining for efficient storage and retrieval of bindings. It dynamically expands the number of buckets to accommodate a growing number of bindings.
- `symtable.h`: Interface and function declarations for the SymTable ADT. Users can include this header file in their projects to use the SymTable module.


