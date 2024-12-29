# CS237 Malloc Lab

In this lab, you will be writing a dynamic storage allocator for C programs,
i.e., your own version `malloc` and `free`.
Memory allocation is a classic implementation problem
with many interesting algorithms;
this lab is an opportunity to put several of the skills you have learned
in this course to good use.


## Learning Objectives

 * Implement a memory allocator using an explicit free list.
 * Examine how algorithm choice impacts tradeoffs between utilization and throughput.
 * Read and modify a substantial C program.
 * Improve your C programming skills including gaining more experience with structs, pointers, macros, and debugging.


## Useful links
 * [Lab Handout](http://cs.williams.edu/~cs237/labs/lab06/index.html)


## Main Files


 * `mm.{c,h}` : Your implementaiton of the `malloc` routines. `mm.c` is the file that you will be handing in, and it is the only file you should modify.

 * `mdriver.c`:	The driver that tests your `mm.c` file

 * `short{1,2}-bal.rep`: Two tiny tracefiles to help you get started. 

 * `Makefile`: Compiles your lab.


## Support files (for the `mdriver.c`)
 * `config.h`: Configures the driver
 * `fsecs.{c,h}`: Wrapper function for the different timer packages
 * `clock.{c,h}`: Routines for accessing the cycle counters
 * `fcyc.{c,h}`: Timer functions based on cycle counters
 * `ftimer.{c,h}`: Timer functions (based on interval timers and `gettimeofday()`)
 * `memlib.{c,h}`: Models the heap and the `sbrk` function


## Building and running the driver

 * To build the driver, type `make`

 * To run the driver on a tiny test trace:
 ```
	$ mdriver -V -f traces/short1-bal.rep
 ```
   The -V option prints out helpful tracing and summary information.

 * To get a list of the driver flags:
 ```
	$ mdriver -h
 ```
 ## Honor Code:
 We are the sole authors of this repository.
