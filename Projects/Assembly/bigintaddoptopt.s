/*--------------------------------------------------------------------*/
/* mywc.s                                                             */
/* Author: Gabriel Marin & Kurt Lemai                                 */
/*--------------------------------------------------------------------*/

    .section .rodata

    .section .data

    .section .bss

    .section .text

    
   .equ FALSE, 0
   .equ TRUE, 1
   .global BigInt_add   
/*--------------------------------------------------------------------*/

/* Assign the sum of OADDEND1 and OADDEND2 to oSum.  oSum should be
   distinct from OADDEND1 and OADDEND2.  Return 0 (FALSE) if an
   overflow occurred, and 1 (TRUE) otherwise. */

/* BigIntAdd Stack Offset */

    .equ BIGINTADD_STACK_BYTE, 64 

/* Local variable stack offsets */

    LSUMLENGTH  .req x24
    LINDEX      .req x23
    ULSUM       .req x22

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

/* Load parameters into corresponding registers */
mov OADDEND1, x0
mov OADDEND2, x1
mov OSUM, x2

   /* 
   unsigned long ulSum;
   long lIndex;
   long lSumLength;  */


/* Determine the larger length 
lSumLength = BigInt_Larger(OADDEND1->lLength, OADDEND2->lLength) */

/* OADDEND1->lLength */

ldr x0, [OADDEND1]

/* OADDEND2-> lLength */

ldr x1, [OADDEND2]

/* Calls BigInt_Larger */

BigInt_Larger:

/*if (lLength1 <= lLength2) */
cmp x0, x1
ble elseIf

/* lLarger = lLength1 */
mov x2, x0
b noElseIf

elseIf:

/* lLarger = lLength2 */
mov x2, x1

noElseIf:

mov LSUMLENGTH, x2

/* Clear oSum's array if necessary 
if (oSum->lLength <= lSumLength) goto noClear*/

/* Loads oSum->lLength */

ldr x0, [OSUM]

/*Comparison */
cmp x0, LSUMLENGTH
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

/* lIndex = 0 */
mov LINDEX, 0

/* Resets Flags */
mov x1, 1
adcs x1, x1, x1

/*Set's up OADDEND1 and OADDEND2 to save time on loop */
mov x3, OADDEND1
add x3, x3, 8
mov x4, OADDEND2
add x4, x4, 8

/*Set's up OSUM to save time on loop */
mov x6, OSUM 
add x6, x6, 8

/* if lIndex >= lSumLength goto endLoop1*/

/*Comparison */
sub x0, LINDEX, LSUMLENGTH
CBZ x0, endloop1

loop1:

/*Loads oAddend1->aulDigits[lIndex] */
ldr x1, [x3, LINDEX, lsl 3] /* x1 contains value */

/*Loads oAddend2->aulDigits[lIndex] */
ldr x2, [x4, LINDEX, lsl 3] /* x2 contains value */

adcs ULSUM, x1, x2 /* Plus Carry Flag */ 

/*oSum->aulDigits[lIndex] = ulSum */
str ULSUM, [x6, LINDEX, lsl 3]

/* lIndex++ */

add LINDEX, LINDEX, 1

/*if (lIndex < lSumLength) goto loop1 */
sub x5, LINDEX, LSUMLENGTH 
CBNZ x5, loop1

endloop1:

/* Check for a carry out of the last "column of the addition" */
blo noCarry

/*if (lSumLength != MAX_DIGITS) goto NotMAX_DIGITS */
cmp LSUMLENGTH, MAXDIGITS
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
add sp, sp, BIGINTADD_STACK_BYTE
ret

NotMaxDigits:

/* oSum->aulDigits[lSumLength] = 1 */
mov x1, 1
str x1, [x6, LSUMLENGTH, lsl 3]

/*lSumLength++*/

add LSUMLENGTH, LSUMLENGTH, 1
b noCarry

noCarry:
/* Set the length of the sum */

/*oSum->lLength = lSumLength */
str LSUMLENGTH, [OSUM]
 
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
add sp, sp, BIGINTADD_STACK_BYTE
ret




