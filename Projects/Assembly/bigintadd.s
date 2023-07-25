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
   .equ  LLARGER, 8 

   /* Parameter stack offsets */

    .equ LLENGTHTWO, 16
    .equ LLENGTHONE, 24

BigInt_Larger:

/* Prolog */
sub     sp, sp, BIGINTLARGER_STACK_BYTE
str     x30, [sp]
str     x0, [sp, LLENGTHONE]
str     x1, [sp, LLENGTHTWO]

/* lLarger */

/*if (lLength1 <= Length2) goto elseIf*/
ldr x0, [sp, LLENGTHONE]
ldr x1, [sp, LLENGTHTWO]
cmp x0, x1
ble elseIf

/* lLarger = lLength1 */
ldr x0, [sp, LLENGTHONE]
mov x1, x0
str x1, [sp, LLARGER]
b noElseIf

elseIf:

/* lLarger = lLength2 */
ldr x0, [sp, LLENGTHTWO]
mov x1, x0
str x1, [sp, LLARGER]

 
noElseIf:

/* BigInt_Larger End */
/* Epilog */
ldr x0, [sp, LLARGER]
ldr x30, [sp]
add sp, sp, BIGINTLARGER_STACK_BYTE
ret

/*--------------------------------------------------------------------*/

/* Assign the sum of OADDEND1 and OADDEND2 to oSum.  oSum should be
   distinct from OADDEND1 and OADDEND2.  Return 0 (FALSE) if an
   overflow occurred, and 1 (TRUE) otherwise. */

/* BigIntAdd Stack Offset */

    .equ BIGINTADD_STACK_BYTE, 64 

/* Local variable stack offsets */

    .equ LSUMLENGTH, 8
    .equ LINDEX, 16
    .equ ULSUM, 24
    .equ ULCARRY, 32

/* Parameter stack offsets */

    .equ OSUM, 40
    .equ OADDEND2, 48
    .equ OADDEND1, 56

/* Other equ */

    .equ SIZEOFLONG, 8
    .equ MAXDIGITS, 32768



/* int BigInt_add(BigInt_T oAddend1, BigInt_T oAddend2,
BigInt_T oSum) */

BigInt_add:

/* Prolog */

sub sp, sp, BIGINTADD_STACK_BYTE
str x30, [sp]
str x0, [sp, OADDEND1]
str x1, [sp, OADDEND2]
str x2, [sp, OSUM]

/* unsigned long ulCarry;
   unsigned long ulSum;
   long lIndex;
   long lSumLength;  */

/* Assert's don't exist in assembly */

/* Determine the larger length 
lSumLength = BigInt_Larger(OADDEND1->lLength, OADDEND2->lLength) */

/* OADDEND1->lLength */

ldr x0, [sp, OADDEND1]
ldr x0, [x0]

/* OADDEND2-> lLength */

ldr x1, [sp, OADDEND2]
ldr x1, [x1]

/* Calls BigInt_Larger */
bl BigInt_Larger
str x0, [sp, LSUMLENGTH]

/* Clear oSum's array if necessary 
if (oSum->lLength <= lSumLength) goto noClear*/

/* Loads oSum->lLength */

ldr x0, [sp, OSUM]
ldr x0, [x0]

/* loads lSumLength */
ldr x1, [sp, LSUMLENGTH]

/*Comparison */
cmp x0, x1 
bls noClear

/* memset(oSum->aulDigits, 0, MAX_DIGITS * sizeof(unsigned long))*/

/* Load oSum->aulDigits */

ldr x0, [sp, OSUM]
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
str x1, [sp, ULCARRY]

/* lIndex = 0 */
mov x1, 0
str x1, [sp, LINDEX]

loop1:

/*if (lIndex >= lSumLength) goto endLoop1 */

/*Loads lIndex */
ldr x0, [sp, LINDEX]

/*Loads lSumLength */
ldr x1, [sp, LSUMLENGTH]

/*Comparison */
cmp x0, x1 
bge endloop1

/*ulSum = ulCarry */
ldr x0, [sp, ULCARRY]
str x0, [sp, ULSUM]


/*ulCarry = 0 */
mov x1, 0
str x1, [sp, ULCARRY]

/*ulSum += oAddend1->aulDigits[lIndex] */


/*Loads oAddend1->aulDigits[lIndex] */
ldr x0, [sp, LINDEX]

ldr x1, [sp, OADDEND1]
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/*ulSum = ulSum + oAddend1->aulDigits */
ldr x0, [sp, ULSUM]
add x0, x0, x1  
str x0, [sp, ULSUM]

/* if(ulSum >= oAddend1->aulDigits[lIndex]) goto noOverFlowOne */ 

/*Loads oAddend1->aulDigits[lIndex] */
ldr x0, [sp, LINDEX]

ldr x1, [sp, OADDEND1]
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/* loads ulSum */
ldr x0, [sp, ULSUM]

/* Comparison */
cmp x0, x1
bhs noOverFlowOne

/*ulCarry = 1 */
mov x1, 1
str x1, [sp, ULCARRY]

noOverFlowOne:

/*Loads oAddend2->aulDigits[lIndex] */
ldr x0, [sp, LINDEX]

ldr x1, [sp, OADDEND2]
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/*ulSum = ulSum + oAddend2->aulDigits[lIndex] */

ldr x0, [sp, ULSUM]
add x0, x0, x1  
str x0, [sp, ULSUM]


/*Loads oAddend2->aulDigits[lIndex] */
ldr x0, [sp, LINDEX]

ldr x1, [sp, OADDEND2]
add x1, x1, 8 

mov x2, x0 
lsl x2, x2, 3

add x1, x1, x2
ldr x1, [x1] /* x1 contains value */

/* loads ulSum */
ldr x0, [sp, ULSUM]

/* Comparison */
cmp x0, x1
bhs noOverFlowTwo

/*ulCarry = 1 */
mov x1, 1
str x1, [sp, ULCARRY]

noOverFlowTwo:

/*oSum->aulDigits[lIndex] = ulSum */
ldr x0, [sp, LINDEX]
mov x2, x0

ldr x0, [sp, OSUM]
add x0, x0, 8
lsl x2, x2, 3
add x0, x0, x2

ldr x1, [sp, ULSUM]

ldr x3, [x0]
str x1, [x0]

/* lIndex++ */

ldr x1, [sp, LINDEX]
add x1, x1, 1
str x1, [sp, LINDEX]
b loop1


endloop1:

/* Check for a carry out of the last "column of the addition" 
if(ulCarry != 1)goto noCarry */
ldr x0, [sp, ULCARRY]
cmp x0, 1
bne noCarry

/*if (lSumLength != MAX_DIGITS) goto NotMAX_DIGITS */
ldr x0, [sp, LSUMLENGTH]
cmp x0, MAXDIGITS
bne NotMaxDigits

/* Possible Epilog */
mov x0, FALSE
ldr x30, [sp]
add sp, sp, BIGINTADD_STACK_BYTE
ret

NotMaxDigits:

/* oSum->aulDigits[lSumLength] = 1 */
ldr x1, [sp, LSUMLENGTH]
mov x2, x1
lsl x2, x2, 3

ldr x0, [sp, OSUM]

add x0, x0, 8
add x0, x0, x2
mov x1, 1
str x1, [x0]

/*lSumLength++*/

ldr x1, [sp, LSUMLENGTH]
add x1, x1, 1
str x1, [sp, LSUMLENGTH]
b noCarry


noCarry:
/* Set the length of the sum */

/*oSum->lLength = lSumLength */
ldr x0, [sp, OSUM]
ldr x1, [sp, LSUMLENGTH]
str x1, [x0]
 
 /* BigIntAddEnd */

/* Epilog */
mov x0, TRUE
ldr x30, [sp]
add sp, sp, BIGINTADD_STACK_BYTE
ret




