#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "set.h"

// For the specified set, determines if the set contains size elements
// and contains exactly the elements specified in array.  If both criteria
// are met, returns 1.  Otherwise, returns false.
int doContentsMatch(struct set *s, int array[], int size)
{
	if(s == NULL){
		return 0;
	}

	if(s->num_elements != size){
		return 0;
	}

	// Check that all elements in array are in set
        for(int i = 0; i < size; i++){
		if(containsItem(s, array[i]) == 0)
			return 0;
        }
        
        return 1;
}

// For each of the specified sets, initializes them with specific values
// that are expected in each of the test functions.
void setupSets(struct set *testEmpty, struct set *testOne,
	       struct set *testTwo, struct set *testThree,
	       struct set *testFour)
{
	// no elements
	initializeSet(testEmpty);

	// {1}
	initializeSet(testOne);
	addItem(testOne, 1);

	// {4, -9}
	initializeSet(testTwo);	
	addItem(testTwo, 4);
	addItem(testTwo, -9);

	// {1, 2, 8}
	initializeSet(testThree);	
	addItem(testThree, 1);
	addItem(testThree, 8);
	addItem(testThree, 2);

	// {-20, 2, 8, 42}
	initializeSet(testFour);	
	addItem(testFour, -20);
	addItem(testFour, 8);
	addItem(testFour, 2);
	addItem(testFour, 42);	
}	

// Test the initializeSet function
void testInitializeSet()
{
	struct set newSet;
	initializeSet(&newSet);
        int array[] = {};
        assert(doContentsMatch(&newSet, array, 0) == 1);
}

// Test the copySet function using already initialized sets specified as
// arguments
void testCopySet(struct set *testEmpty, struct set *testOne,
	      struct set *testTwo, struct set *testThree, struct set *testFour)
{	
	struct set result = copySet(testEmpty);
        int array[] = {};
	assert(doContentsMatch(&result, array, 0) == 1);

	result = copySet(testOne);
        int array2[] = {1};
	assert(doContentsMatch(&result, array2, 1) == 1);	

	result = copySet(testThree);
        int array3[] = {1, 2, 8};
	assert(doContentsMatch(&result, array3, 3) == 1);	
        
}

// Test the addItem function using already initialized sets specified as
// arguments
void testAddItem(struct set *testEmpty, struct set *testOne,
	      struct set *testTwo, struct set *testThree, struct set *testFour)
{	
        assert(addItem(testEmpty, 1) == 1);
        int array[] = {1};
        assert(doContentsMatch(testEmpty, array, 1) == 1);

        assert(addItem(testEmpty, 1) == 0);
        assert(doContentsMatch(testEmpty, array, 1) == 1);

        assert(addItem(testOne, -1) == 1);
        int array2[] = {-1, 1};	
        assert(doContentsMatch(testOne, array2, 2) == 1);		

        assert(addItem(testThree, 8) == 0);
        int array3[] = {1, 2, 8};	
        assert(doContentsMatch(testThree, array3, 3) == 1);			
        assert(addItem(testThree, -1) == 1);
        int array4[] = {1, 2, 8, -1};	
        assert(doContentsMatch(testThree, array4, 4) == 1);			

}

// Test the removeItem function using already initialized sets specified as
// arguments
void testRemoveItem(struct set *testEmpty, struct set *testOne,
	      struct set *testTwo, struct set *testThree, struct set *testFour)
{	
        assert(removeItem(testEmpty, 1) == 0);
        int array[] = {};
        assert(doContentsMatch(testEmpty, array, 0) == 1);

        assert(removeItem(testOne, 2) == 0);
	int array2[] = {1};
        assert(doContentsMatch(testOne, array2, 1) == 1);

        assert(removeItem(testOne, 1) == 1);
	int array3[] = {};
        assert(doContentsMatch(testOne, array3, 0) == 1);	

        assert(removeItem(testThree, -1) == 0);
        int array4[] = {1, 2, 8};	
        assert(doContentsMatch(testThree, array4, 3) == 1);

	assert(removeItem(testThree, 8) == 1);
        int array5[] = {1, 2};	
        assert(doContentsMatch(testThree, array5, 2) == 1);  

}

// Test the containsItem function using already initialized sets specified as
// arguments
void testContainsItem(struct set *testEmpty, struct set *testOne,
	      struct set *testTwo, struct set *testThree, struct set *testFour)
{	
        assert(containsItem(testEmpty, 1) == 0);
        int array[] = {};
        assert(doContentsMatch(testEmpty, array, 0) == 1);

        assert(containsItem(testOne, -1) == 0);
	int array2[] = {1};
        assert(doContentsMatch(testOne, array2, 1) == 1);

	assert(containsItem(testOne, 1) == 1);
        assert(doContentsMatch(testOne, array2, 1) == 1);	

        assert(containsItem(testThree, -1) == 0);
        int array3[] = {1, 2, 8};	
        assert(doContentsMatch(testThree, array3, 3) == 1);

	assert(containsItem(testThree, 8) == 1);
        assert(doContentsMatch(testThree, array3, 3) == 1);  
	
}

// Test the getUnion function using already initialized sets specified as
// arguments
void testGetUnion(struct set *testEmpty, struct set *testOne,
	      struct set *testTwo, struct set *testThree, struct set *testFour)
{	
        // {} + {} = {}
	struct set result;
	initializeSet(&result);
	result = getUnion(testEmpty, testEmpty);
        int array[] = {};
        assert(doContentsMatch(&result, array, 0) == 1);
	// also make sure testEmpty was unchanged
	assert(doContentsMatch(testEmpty, array, 0) == 1); 

        // {} + {1} = {1}
	initializeSet(&result);
	result = getUnion(testEmpty, testOne);
        int array2[] = {1};
        assert(doContentsMatch(&result, array2, 1) == 1);
	// also make sure testEmpty and testOne unchanged
	assert(doContentsMatch(testEmpty, array, 0) == 1);
	assert(doContentsMatch(testOne, array2, 1) == 1);	
	          
        // {1} + {1} = {1}
	initializeSet(&result);
	result = getUnion(testOne, testOne);
        assert(doContentsMatch(&result, array2, 1) == 1);
	// also make sure testOne unchanged
	assert(doContentsMatch(testOne, array2, 1) == 1);	

        // {1} + {1, 2, 8} = {1, 2, 8}
	initializeSet(&result);
	result = getUnion(testOne, testThree);
        int array3[] = {1, 2, 8};
        assert(doContentsMatch(&result, array3, 3) == 1);
	// also make sure testOne and testThree unchanged
	assert(doContentsMatch(testOne, array2, 1) == 1);
	assert(doContentsMatch(testThree, array3, 3) == 1);	
	
        // {1, 2, 8} + {1, 2, 8} = {1, 2, 8}
	initializeSet(&result);
	result = getUnion(testThree, testThree);
        assert(doContentsMatch(&result, array3, 3) == 1);
	// also make sure testThree unchanged
	assert(doContentsMatch(testThree, array3, 3) == 1);		

        // {1, 2, 8} + {4, -9} = {1, 2, 8, 4, -9}
	initializeSet(&result);
	result = getUnion(testThree, testTwo);
        int  array4[] = {1, 2, 8, 4, -9};
	assert(doContentsMatch(&result, array4, 5) == 1);
	// also make sure testTwo and testThree unchanged
	int array5[] = {4, -9};
	assert(doContentsMatch(testTwo, array5, 2) == 1);
	assert(doContentsMatch(testThree, array3, 3) == 1);
	
        // {-20, 8, 2, 42} + {1, 2, 8} = {1, 2, 8, 42, -20}
	initializeSet(&result);
	result = getUnion(testThree, testFour);
        int  array6[] = {1, 2, 8, 42, -20};
        assert(doContentsMatch(&result, array6, 5) == 1);
	// also make sure testFour and testThree unchanged
	int array7[] = {2, 8, 42, -20};
	assert(doContentsMatch(testFour, array7, 4) == 1);
	assert(doContentsMatch(testThree, array3, 3) == 1);
       
}    

// Test the getIntersection function using already initialized sets specified as
// arguments
void testGetIntersection(struct set *testEmpty, struct set *testOne,
	      struct set *testTwo, struct set *testThree, struct set *testFour)
{	
        // {} * {} = {}
	struct set result;
	initializeSet(&result);
	result = getIntersection(testEmpty, testEmpty);
        int array[] = {};
        assert(doContentsMatch(&result, array, 0) == 1);
	// also make sure testEmpty was unchanged
	assert(doContentsMatch(testEmpty, array, 0) == 1);
	
        // {} * {1} = {}
	initializeSet(&result);
	result = getIntersection(testEmpty, testOne);
        int array2[] = {1};
        assert(doContentsMatch(&result, array, 0) == 1);
	// also make sure testEmpty and testOne unchanged
	assert(doContentsMatch(testEmpty, array, 0) == 1);
	assert(doContentsMatch(testOne, array2, 1) == 1);	
	
        // {1} * {1} = {1}
	initializeSet(&result);
	result = getIntersection(testOne, testOne);
        assert(doContentsMatch(&result, array2, 1) == 1);
	// also make sure testOne unchanged
	assert(doContentsMatch(testOne, array2, 1) == 1);
	
        // {1} * {1, 2, 8} = {1}
	initializeSet(&result);
	result = getIntersection(testOne, testThree);
        assert(doContentsMatch(&result, array2, 1) == 1);
	// also make sure testOne and testThree unchanged
	assert(doContentsMatch(testOne, array2, 1) == 1);
	int array3[] = {1, 2, 8};
	assert(doContentsMatch(testThree, array3, 3) == 1);
	
        // {1, 2, 8} * {1, 2, 8} = {1, 2, 8}
	initializeSet(&result);
	result = getIntersection(testThree, testThree);
        assert(doContentsMatch(&result, array3, 3) == 1);
	// also make sure testThree unchanged
	assert(doContentsMatch(testThree, array3, 3) == 1);
	
        // {1, 2, 8} * {4, -9} = {}
	initializeSet(&result);
	result = getIntersection(testThree, testTwo);
        assert(doContentsMatch(&result, array, 0) == 1);
	// also make sure testTwo and testThree unchanged
	int array5[] = {4, -9};
	assert(doContentsMatch(testTwo, array5, 2) == 1);
	assert(doContentsMatch(testThree, array3, 3) == 1);
	
        // {-20, 8, 2, 42} * {1, 2, 8} = {2, 8}
	initializeSet(&result);
	result = getIntersection(testThree, testFour);
        int  array6[] = {2, 8};
        assert(doContentsMatch(&result, array6, 2) == 1);
	// also make sure testFour and testThree unchanged
	int array7[] = {2, 8, 42, -20};
	assert(doContentsMatch(testFour, array7, 4) == 1);
	assert(doContentsMatch(testThree, array3, 3) == 1);
}	

// Test the areSetsEqual function using already initialized sets specified as
// arguments
void testAreSetsEqual(struct set *testEmpty, struct set *testOne,
	      struct set *testTwo, struct set *testThree, struct set *testFour)
{
        // {} = {}
	assert(areSetsEqual(testEmpty, testEmpty) == 1);
	
        // {} = {1}
	assert(areSetsEqual(testEmpty, testOne) == 0);	
        
        // {1} = {-20, 8, 2, 42}
	assert(areSetsEqual(testOne, testFour) == 0);		
     
        // {1} = {1, 2, 8}
	assert(areSetsEqual(testOne, testThree) == 0);			

	// {1, 2, 8} == {8, 1, 2}
	struct set tmp;
	initializeSet(&tmp);
	addItem(&tmp, 8);
	addItem(&tmp, 1);
	addItem(&tmp, 2);
	assert(areSetsEqual(testThree, &tmp) == 1);
	
}

// Tests the correctness of set functions.  Assertions will fail if
// implementations of functions are incorrect.
int main(int argc, char *argv[])
{
	struct set testEmpty, testOne, testTwo, testThree, testFour;

	// Before every test function is called, reinitialize the sets
	// to the expected contents.
	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testInitializeSet();

	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testAddItem(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	
	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testCopySet(&testEmpty, &testOne, &testTwo, &testThree, &testFour);

	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testRemoveItem(&testEmpty, &testOne, &testTwo, &testThree, &testFour);

	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testContainsItem(&testEmpty, &testOne, &testTwo, &testThree,
			 &testFour);

	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testGetUnion(&testEmpty, &testOne, &testTwo, &testThree, &testFour);

	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testGetIntersection(&testEmpty, &testOne, &testTwo, &testThree,
			    &testFour);

	setupSets(&testEmpty, &testOne, &testTwo, &testThree, &testFour);
	testAreSetsEqual(&testEmpty, &testOne, &testTwo, &testThree,
			 &testFour);
	
	return 0;
}
