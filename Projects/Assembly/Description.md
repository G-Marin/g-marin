# Assembly Language and Testing

## Description
This assigments purpose is to learn about the ARMv8 architecture, assembly language, and testing strategies. This project is split into two parts in which code is translated from C to Assembly. The first part of the assigment includes tranlsating a C program into assembly language and testing it to ensure it can run the same tests as it's non-assembly counterpart. The second part of this assigment focuses around optimizing the assembly language to its full capabilities in order to beat the C compiler in runtime. The goal is to create more and more optimized version until we beat the compiler.

This project was developed in collaboration with a partner.

## Languages & Tools
- C
- gProf
- GitHub

## File Descriptions

## Part 1: 
- `mywc.c`: This c program reads characters from stdin until end-of-file, and writes to stdout a count of how many lines, words, and characters it has read. This file was given
- `mywc.s`: This is the assembly translated version of the mywc.c program

## Part 2: 
The BigInt ADT performs high precision integer arithmetic. It is a minimal ADT; essentially it implements only an "add" operation. The BigInt ADT consists of four files:
- `bigint.h`:  is the interface. Note that the ADT makes eight functions available to clients: BigInt_new, BigInt_free, BigInt_assignFromHexString, BigInt_largest, BigInt_random, BigInt_writeHex, BigInt_writeHexAbbrev, and BigInt_add.
- `bigint.c`: contains implementations of the BigInt_new, BigInt_free, BigInt_assignFromHexString, BigInt_largest, BigInt_random, BigInt_writeHex, and BigInt_writeHexAbbrev functions.
- `bigintadd.c`: contains an implementation of the BigInt_add function.
- `bigintprivate.h`: is a private header file — private in the sense that clients never use it. It allows code to be shared between the two implementation files, bigint.c and bigintadd.c
- `fib.c`: The client accepts an integer x as a command-line argument, where x must be a non-negative integer. The client computes and writes fib(x) to stdout as a hexadecimal number. Then it writes to stderr the amount of CPU time consumed while performing the computation. Finally the client performs some boundary condition and stress tests, writing the results to stdout. The client module delegates most of the work to BigInt objects.
- `bigintaddflat.c`: Contains the bigintadd.c implemention in flat format ready for translation to assembly
- `bigintadd.s`: This is a simple assembly translation from the bigintadd.c program with no optimizations
- `bigintaddopt.s`: This is the second assembly translation of the bigintadd.c program with optimizations by using callee-saved registers, .equ directives, and .req directives
- `bigintoptopt.s`: This is the most optimized version of the assembly code that beats the compiler by optimizing through the use of adcs, guarded loops, and inlining. 
  
  
