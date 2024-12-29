#ifndef SET_H
#define SET_H
#define MAX_SIZE 100

//struct set contains int array of unique elements and num_elements in array
struct set {
  int num_elements;
  int elements[MAX_SIZE];
};

//Initializes the number of elements to 0 and returns 1. 
//If specified set is NULL, returns 0. 
int initializeSet(struct set * s);

//If the item is already in the set or the set is full, returns 0. 
//Otherwise, adds the item to the set and returns 1. 
//If the specified set is NULL, returns 0. 
int addItem(struct set *s, int num);

//If the specified set is NULL, returns a new set that is empty. 
//Otherwise, returns a new set that is a copy of the specified set.
struct set copySet(struct set *s);

//If the item is in the specified set, removes the item and returns 1. 
//If the item is not in the specified set, returns 0. 
//If the specified set is NULL, returns 0.
int removeItem(struct set *s, int item);

//If the item is in the specified set, returns 1. 
//Otherwise, returns 0. If the specified set is NULL, returns 0.
int containsItem(struct set *s, int item);

//Returns a new set that is the union of the contents of the two specified sets. 
//Meaning, the new set contains elements that appear in either set, with no duplicates. 
//If a specified set is NULL, view that as an empty set.
struct set getUnion(struct set *a, struct set *b);

//Returns a new set that is the intersection of the contents of the two specified sets. 
//Meaning, the new set contains elements that appear in both sets. 
//If a specified set is NULL, view that as an empty set.
struct set getIntersection(struct set *a, struct set *b);

//Returns 1 if the elements in the first set are identical to the elements in the second set. 
//Otherwise, returns 0. If a specified set is NULL, view that as an empty set.
int areSetsEqual(struct set *a, struct set *b);

#endif
