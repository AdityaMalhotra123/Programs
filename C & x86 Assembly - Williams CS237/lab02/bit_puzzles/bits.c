/* 
 * CS:APP Data Lab 
 * 
 * 
 * bits.c - Source file with your solutions to the Lab.
 *          This is the file you will hand in to your instructor.
 *
 * WARNING: Do not include the <stdio.h> header; it confuses the dlc
 * compiler. You can still use printf for debugging without including
 * <stdio.h>, although you might get a compiler warning. In general,
 * it's not good practice to ignore compiler warnings, but in this
 * case it's OK.  
 */

#if 0
/*
 * Instructions to Students:
 *
 * STEP 1: Read the following instructions carefully.
 */

You will provide your solution to the Data Lab by
editing the collection of functions in this source file.

INTEGER CODING RULES:
 
  Replace the "return" statement in each function with one
  or more lines of C code that implements the function. Your code 
  must conform to the following style:
 
  int Funct(arg1, arg2, ...) {
      /* comment describing logical group of code that follows */ 
      int var1 = Expr1;
      ...
      int varM = ExprM;

      /* comment describing logical group of code that follows */             
      varJ = ExprJ;
      ...
      varN = ExprN;

      /* comment describing logical group of code that follows */             
      return ExprR;
  }

  Each "Expr" is an expression using ONLY the following:
  1. Integer constants 0 through 255 (0xFF), inclusive. You are
      not allowed to use big constants such as 0xffffffff.
  2. Function arguments and local variables (no global variables).
  3. Unary integer operations ! ~
  4. Binary integer operations & ^ | + << >>
    
  Some of the problems restrict the set of allowed operators even further.
  Each "Expr" may consist of multiple operators. You are not restricted to
  one operator per line.

  You are expressly forbidden to:
  1. Use any control constructs such as if, do, while, for, switch, etc.
  2. Define or use any macros.
  3. Define any additional functions in this file.
  4. Call any functions.
  5. Use any other operations, such as &&, ||, -, or ?:
  6. Use any form of casting.
  7. Use any data type other than int.  This implies that you
     cannot use arrays, structs, or unions.

 
  You may assume that your machine:
  1. Uses 2s complement, 32-bit representations of integers.
  2. Performs right shifts arithmetically.
  3. Has unpredictable behavior when shifting an integer by more
     than the word size.

EXAMPLES OF ACCEPTABLE CODING STYLE:
  /*
   * pow2plus1 - returns 2^x + 1, where 0 <= x <= 31
   */
  int pow2plus1(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     return (1 << x) + 1;
  }

  /*
   * pow2plus4 - returns 2^x + 4, where 0 <= x <= 31
   */
  int pow2plus4(int x) {
     /* exploit ability of shifts to compute powers of 2 */
     int result = (1 << x);
     result += 4;
     return result;
  }

FLOATING POINT CODING RULES

For the problems that require you to implent floating-point operations,
the coding rules are less strict.  You are allowed to use looping and
conditional control.  You are allowed to use both ints and unsigneds.
You can use arbitrary integer and unsigned constants.

You are expressly forbidden to:
  1. Define or use any macros.
  2. Define any additional functions in this file.
  3. Call any functions.
  4. Use any form of casting.
  5. Use any data type other than int or unsigned.  This means that you
     cannot use arrays, structs, or unions.
  6. Use any floating point data types, operations, or constants.


NOTES:
  1. Use the dlc (data lab checker) compiler (described in the handout) to 
     check the legality of your solutions.
  2. Each function has a maximum number of operators (! ~ & ^ | + << >>)
     that you are allowed to use for your implementation of the function. 
     The max operator count is checked by dlc. Note that '=' is not 
     counted; you may use as many of these as you want without penalty.
  3. Use the btest test harness to check your functions for correctness.
  4. Use the BDD checker to formally verify your functions
  5. The maximum number of ops for each function is given in the
     header comment for each function. If there are any inconsistencies 
     between the maximum ops in the writeup and in this file, consider
     this file the authoritative source.

/*
 * STEP 2: Modify the following functions according the coding rules.
 * 
 *   IMPORTANT. TO AVOID GRADING SURPRISES:
 *   1. Use the dlc compiler to check that your solutions conform
 *      to the coding rules.
 *   2. Use the BDD checker to formally verify that your solutions produce 
 *      the correct answers.
 */


#endif
/* Copyright (C) 1991-2020 Free Software Foundation, Inc.
   This file is part of the GNU C Library.

   The GNU C Library is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   The GNU C Library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with the GNU C Library; if not, see
   <https://www.gnu.org/licenses/>.  */
/* This header is separate from features.h so that the compiler can
   include it implicitly at the start of every compilation.  It must
   not itself include <features.h> or any other header that includes
   <features.h> because the implicit include comes before any feature
   test macros that may be defined in a source file before it first
   explicitly includes a system header.  GCC knows the name of this
   header in order to preinclude it.  */
/* glibc's intent is to support the IEC 559 math functionality, real
   and complex.  If the GCC (4.9 and later) predefined macros
   specifying compiler intent are available, use them to determine
   whether the overall intent is to support these features; otherwise,
   presume an older compiler has intent to support these features and
   define these macros by default.  */
/* wchar_t uses Unicode 10.0.0.  Version 10.0 of the Unicode Standard is
   synchronized with ISO/IEC 10646:2017, fifth edition, plus
   the following additions from Amendment 1 to the fifth edition:
   - 56 emoji characters
   - 285 hentaigana
   - 3 additional Zanabazar Square characters */
/* 
 * can_truncate - return 1 if x can be represented as a 
 *   16-bit, two's complement integer.
 *   Examples: can_truncate(33000) = 0, can_truncate(-32768) = 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 8
 *   Rating: 1
 */
int can_truncate(int x) {
   //represents 17 most significant bits in x
   int excess_bits_in_x = x >> 15;

   //can truncate when 17 excess_bits_in_x are all 1s or all 0s.
   return (!excess_bits_in_x) | (!(~excess_bits_in_x));
}
/* 
 * divide_pow_2 - Compute x/(2^n), for 0 <= n <= 30
 *  Round toward zero
 *   Examples: divide_pow_2(15,1) = 7, divide_pow_2(-33,4) = -2
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 15
 *   Rating: 2
 */
int divide_pow_2(int x, int n) {
   //represents 1 when bias is needed (only when x is negative) and 0 otherwise
   int bias;

   //represents 2^n - 1
   int mask = 0xFF | (0xFF << 8);
   mask = mask | (mask << 16);
   mask = ~(mask << n);

   bias = ((x >> 31) & mask);
   return (x + bias) >> n;
}
/* 
 * is_sub_ok - Determine if can compute x-y without overflow
 *   Example: is_sub_ok(0x80000000,0x80000000) = 1,
 *            is_sub_ok(0x80000000,0x70000000) = 0, 
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 20
 *   Rating: 3
 */
int is_sub_ok(int x, int y) {
  //diff represents x - y
  int diff = (x + ~y + 1) >> 31;
  int x_MSB = x >> 31;
  int y_MSB = y >> 31;

  //can subtract when x and y are the same sign or
  //x and y are different signs and diff is the same sign as x
  return (!(x_MSB ^ y_MSB)) | ((x_MSB ^ y_MSB) & !(diff ^ x_MSB));
}
/* 
 * is_greater_than - if x > y  then return 1, else return 0 
 *   Example: is_greater_than(4,5) = 0, is_greater_than(5,4) = 1
 *   Legal ops: ! ~ & ^ | + << >>
 *   Max ops: 24
 *   Rating: 3
 */
int is_greater_than(int x, int y) {
  //sub represents x - y
  //subtract 1 from diff because 0 needs to be treated as negative
  int diff = (x + ~y) >> 31;
  int x_MSB = x >> 31;
  int y_MSB = y >> 31;
  
  //x is not greater than y when:
  //x is negative and y is positive, x and y are the same sign and diff is negative
  return !((x_MSB & ~y_MSB) | ((~(x_MSB ^ y_MSB)) & diff));
}
/* 
 * logical_neg - implement the ! operator, using all of 
 *              the legal operators except !
 *   Examples: logical_neg(3) = 0, logical_neg(0) = 1
 *   Legal ops: ~ & ^ | + << >>
 *   Max ops: 12
 *   Rating: 4 
 */
int logical_neg(int x) {
  //return 1 only when MSB for x and -x are both 0.
  return (~((x >> 31) | ((~x + 1) >> 31)) & 0x01);
}
/* 
 * float_abs - Return bit-level equivalent of absolute value of f for
 *   floating point argument f.
 *   Both the argument and result are passed as unsigned int's, but
 *   they are to be interpreted as the bit-level representations of
 *   single-precision floating point values.
 *   When argument is NaN, return argument..
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 10
 *   Rating: 2
 */
unsigned float_abs(unsigned uf) {
  int mask = 0x7fffffff;

  //Condition for NaN
  if ((!!((0x7fffff) & uf)) && (0xFF == ((mask & uf) >> 23))) {
   return uf;
  }
  else {
   return (mask & uf);
  }

}
/* 
 * float_to_int - Return bit-level equivalent of expression (int) f
 *   for floating point argument f.
 *   Argument is passed as unsigned int, but
 *   it is to be interpreted as the bit-level representation of a
 *   single-precision floating point value.
 *   Anything out of range (including NaN and infinity) should return
 *   0x80000000u.
 *   Legal ops: Any integer/unsigned operations incl. ||, &&. also if, while
 *   Max ops: 30
 *   Rating: 4
 */
int float_to_int(unsigned uf) {
  int frac = uf & 0x7fffff;
  int exp = (uf >> 23) & 0xFF;
  int sign = (uf >> 31) & 1;
  int E = exp - 127;

  //represent Significand when uf is Normalized.
  int M = 0x800000 | frac;

  //float value is out of range for integers
  if (E >= 31) {
    return 0x80000000u;
  }
  //denormalized float values should return 0
  else if (E < 0) {
   return 0;
  }
  //normalized conversion to int
  else {
    if (E < 23) {
      M = M >> (23 - E);
    }
    else {
      M = M << (E - 23);
    }
  }
  //if sign is negative, return -M
  if (sign == 1) {
   return ~M + 1;
  }
  return M;
}
