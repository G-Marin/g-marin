/*--------------------------------------------------------------------*/
/* mywc.s                                                             */
/* Author: Gabriel Marin & Kurt Lemai                                 */
/*--------------------------------------------------------------------*/


    .section .rodata

    .section .data

    .section .bss

    .section .text

    
    /* Return the larger of lLength1 and lLength2. */

   .equ FALSE, 0
   .equ TRUE, 1
   .global BigInt_add
   
   /*BigIntLarger Stack Offset */  
   .equ  BIGINTLARGER_STACK_BYTE, 32
   
   /* Local variable stack offsets */
    LLARGER         .req x21 /* Callee Saved */

   /* Parameter stack offsets */

    LLENGTHTWO      .req x20 /*Callee Saved */
    LLENGTHONE      .req x19 /*Callee Saved */

BigInt_Larger:

/* Prolog */
sub sp, sp, BIGINTLARGER_STACK_BYTE
str x30, [sp]
str x19, [sp, 8]
str x20, [sp, 16]
str x21, [sp, 24]


/* Stores parameters in corresponding registers */
mov LLENGTHONE, x0
mov LLENGTHTWO, x1

/* lLarger */

/*if (lLength1 <= Length2) goto elseIf*/
cmp LLENGTHONE, LLENGTHTWO
ble elseIf

/* lLarger = lLength1 */
mov LLARGER, LLENGTHONE
b noElseIf

elseIf:

/* lLarger = lLength2 */
mov LLARGER, LLENGTHTWO

noElseIf:

/* BigInt_Larger End Return lLarger */
/* Epilog */
mov x0, LLARGER
ldr x30, [sp]
ldr x19, [sp, 8]
ldr x20, [sp, 16]
ldr x21, [sp, 24]
add sp, sp, BIGINTLARGER_STACK_BYTE
ret

/*--------------------------------------------------------------------*/

/* Assign the sum of OADDEND1 and OADDEND2 to oSum.  oSum should be
   distinct from OADDEND1 and OADDEND2.  Return 0 (FALSE) if an
   overflow occurred, and 1 (TRUE) otherwise. */

/* BigIntAdd Stack Offset */

    .equ BIGINTADD_STACK_BYTE, 64 

/* Local variable stack offsets */

    LSUMLENGTH  .req x25
    LINDEX      .req x24
    ULSUM       .req x23
    ULCARRY     .req x22

/* Parameter stack offsets */

    OSUM        .req x21
    OADDEND2    .req x20
    OADDEND1    .req x19

/* Other equ */

    .equ SIZEOFLONG, 8
    .equ MAXDIGITS, 32768


/* int BigInt_add(BigInt_T oAddend1, BigInt_T oAddend2,
BigInt_T oSum) */

BigInt_add:

/* Prolog */

sub sp, sp, BIGINTADD_STACK_BYTE
str x30, [sp]
str x19, [sp, 8]
str x20, [sp, 16]
str x21, [sp, 24]
str x22, [sp, 32]
str x23, [sp, 40]
str x24, [sp, 48]
str x25, [sp, 56]

/* Load parameters into corresponding registers */
mov OADDEND1, x0
mov OADDEND2, x1
mov OSUM, x2

/* unsigned long ulCarry;
   unsigned long ulSum;
   long lIndex;
   long lSumLength;  */

/* Assert's don't exist in assembly */

/* Determine the larger length 
lSumLength = BigInt_Larger(OADDEND1->lLength, OADDEND2->lLength) */

/* OADDEND1->lLength */

mov x0, OADDEND1
ldr x0, [x0]

/* OADDEND2-> lLength */

mov x1, OADDEND2
ldr x1, [x1]

/* Calls BigInt_Larger */
bl BigInt_Larger
mov LSUMLENGTH, x0

/* Clear oSum's array if necessary 
if (oSum->lLength <= lSumLength) goto noClear*/

/* Loads oSum->lLength */

mov x0, OSUM
ldr x0, [x0]

/* loads lSumLength */
mov x1, LSUMLENGTH

/*Comparison */
cmp x0, x1 
bls noClear

/* memset(oSum->aulDigits, 0, MAX_DIGITS * sizeof(unsigned long))*/

/* Load oSum->aulDigits */

mov x0, OSUM
add x0, x0, 8
mov x1, 0

/*  MAX_DIGITS * sizeof(unsigned long) */

mov x2, MAXDIGITS
mov x3, SIZEOFLONG
mul x2, x2, x3

/* calls memset */

bl memset

/* Perform the addition */

noClear:

/*ul Carry = 0 */
mov x1, 0
mov ULCARRY, x1

/* lIndex = 0 */
mov x1, 0
mov LINDEX, x1

loop1:

/*if (lIndex >= lSumLength) goto endLoop1 */

/*Loads lIndex */
mov x0, LINDEX

/*Loads lSumLength */
mov x1, LSUMLENGTH

/*Comparison */
cmp x0, x1 
bge endloop1

/*ulSum = ulCarry */
mov ULSUM, ULCARRY

/*ulCarry = 0 */
mov x1, 0
mov ULCARRY, x1

/*ulSum += oAddend1->aulDigits[lIndex] */

/*Loads oAddend1->aulDigits[lIndex] */
mov x0, LINDEX

mov x1, OADDEND1
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/*ulSum = ulSum + oAddend1->aulDigits[lIndex] */
mov x0, ULSUM
add x0, x0, x1  
mov ULSUM, x0

/* if(ulSum >= oAddend1->aulDigits[lIndex]) goto noOverFlowOne */ 

/*Loads oAddend1->aulDigits[lIndex] */
mov x0, LINDEX

mov x1, OADDEND1
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/* loads ulSum */
mov x0, ULSUM

/* Comparison */
cmp x0, x1
bhs noOverFlowOne

/*ulCarry = 1 */
mov x1, 1
mov ULCARRY, x1

noOverFlowOne:

/*Loads oAddend2->aulDigits[lIndex] */
mov x0, LINDEX

mov x1, OADDEND2
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/*ulSum = ulSum + oAddend2->aulDigits[lIndex] */

mov x0, ULSUM
add x0, x0, x1  
mov ULSUM, x0


/*if(ulSum >= oAddend2->aulDigits[lIndex]) goto noOverFlowTwo*/
/*Loads oAddend2->aulDigits[lIndex] */
mov x0, LINDEX

mov x1, OADDEND2
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/* loads ulSum */
mov x0, ULSUM

/* Comparison */
cmp x0, x1
bhs noOverFlowTwo

/*ulCarry = 1 */
mov x1, 1
mov ULCARRY, x1

noOverFlowTwo:

/*oSum->aulDigits[lIndex] = ulSum */
mov x0, LINDEX
mov x2, x0

mov x0, OSUM
add x0, x0, 8
lsl x2, x2, 3
add x0, x0, x2

mov x1, ULSUM
str x1, [x0]

/* lIndex++ */

mov x1, LINDEX
add x1, x1, 1
mov LINDEX, x1

b loop1

endloop1:

/* Check for a carry out of the last "column of the addition" 
if(ulCarry != 1)goto noCarry */
mov x0, ULCARRY
cmp x0, 1
bne noCarry

/*if (lSumLength != MAX_DIGITS) goto NotMAX_DIGITS */
mov x0, LSUMLENGTH
cmp x0, MAXDIGITS
bne NotMaxDigits

/* Possible Epilog */

mov x0, FALSE
ldr x30, [sp]
ldr x19, [sp, 8]
ldr x20, [sp, 16]
ldr x21, [sp, 24]
ldr x22, [sp, 32]
ldr x23, [sp, 40]
ldr x24, [sp, 48]
ldr x25, [sp, 56]
add sp, sp, BIGINTADD_STACK_BYTE
ret

NotMaxDigits:

/* oSum->aulDigits[lSumLength] = 1 */
mov x1, LSUMLENGTH
mov x2, x1
lsl x2, x2, 3

mov x0, OSUM

add x0, x0, 8
add x0, x0, x2
mov x1, 1
str x1, [x0]

/*lSumLength++*/

mov x1, LSUMLENGTH
add x1, x1, 1
mov LSUMLENGTH, x1
b noCarry

noCarry:
/* Set the length of the sum */

/*oSum->lLength = lSumLength */
mov x0, OSUM
mov x1, LSUMLENGTH

str x1, [x0]
 
 /* BigIntAddEnd */

/* Epilog */
mov x0, TRUE
ldr x30, [sp]
ldr x19, [sp, 8]
ldr x20, [sp, 16]
ldr x21, [sp, 24]
ldr x22, [sp, 32]
ldr x23, [sp, 40]
ldr x24, [sp, 48]
ldr x25, [sp, 56]
add sp, sp, BIGINTADD_STACK_BYTE
ret





