#include "set.h"
#include <stdlib.h>

//Initializes the number of elements to 0 and returns 1. 
//If specified set is NULL, returns 0. 
int initializeSet(struct set * s) {
    if (s == NULL) {
        return 0;
    }
    s->num_elements = 0;
    return 1;
}

//If the item is already in the set or the set is full, returns 0. 
//Otherwise, adds the item to the set and returns 1. 
//If the specified set is NULL, returns 0. 
int addItem(struct set *s, int num) {
    if ((s == NULL) || (containsItem(s, num) == 1)) {
        return 0;
    }
    else if (s->num_elements == MAX_SIZE) {
        return 0;
    }
    else {
        s->elements[s->num_elements] = num;
        s->num_elements++;
        return 1;
    }
}

//If the specified set is NULL, returns a new set that is empty. 
//Otherwise, returns a new set that is a copy of the specified set.
struct set copySet(struct set *s) {
    struct set newSet;
    initializeSet(&newSet);
    if (s == NULL) {
        return newSet;
    }
    else {
        
        //copy num_elements and elements[] to newSet
        newSet.num_elements = s->num_elements;
        for (int i = 0; i < s->num_elements; i++) {
            newSet.elements[i] = s->elements[i];
        }

        return newSet;
    }
}

//If the item is in the specified set, removes the item and returns 1. 
//If the item is not in the specified set, returns 0. 
//If the specified set is NULL, returns 0.
int removeItem(struct set *s, int item) {
    if ((s != NULL) && (containsItem(s, item) == 1)) {
        for (int i = 0; i < s->num_elements; i++) {

            //once item index found, swap item with last item in set and 
            //decremenet num_elements to reduce 'relevant' length of array
            if (s->elements[i] == item) {
                s->elements[i] = s->elements[s->num_elements - 1];
                s->num_elements--;
                return 1; 
            }
        }
    }
    else {
        return 0;
    }
}

//If the item is in the specified set, returns 1. 
//Otherwise, returns 0. If the specified set is NULL, returns 0.
int containsItem(struct set *s, int item) {
    if (s == NULL) {
        return 0;
    }
    for (int i = 0; i < s->num_elements; i++) {
        if (s->elements[i] == item) {
            return 1; 
        }
    }
    return 0;
}

//Returns a new set that is the union of the contents of the two specified sets. 
//Meaning, the new set contains elements that appear in either set, with no duplicates. 
//If a specified set is NULL, view that as an empty set.
struct set getUnion(struct set *a, struct set *b) {
    struct set newSet;
    initializeSet(&newSet);

    //handles cases when one or both sets are null
    if ((a == NULL) && (b != NULL)) {
        return copySet(b);
    }
    else if ((b == NULL) && (a != NULL)) {
        return copySet(a);
    }
    else if ((a == NULL) && (b == NULL)) {
        return newSet;
    }

    //add all elements of b to newSet
    for (int j = 0; j < b->num_elements; j++) {    
        addItem(&newSet, b->elements[j]); 
    }
    
    //if any element of a isn't already in newSet, add it
    for (int j = 0; j < a->num_elements; j++) {
        if (containsItem(&newSet, a->elements[j]) == 0) {
            addItem(&newSet, a->elements[j]);
        }
    }
    return newSet;
}

//Returns a new set that is the intersection of the contents of the two specified sets. 
//Meaning, the new set contains elements that appear in both sets. 
//If a specified set is NULL, view that as an empty set.
struct set getIntersection(struct set *a, struct set *b) {
    struct set newSet;
    initializeSet(&newSet);
    if (a == NULL || b == NULL) {
        return newSet;
    }

    //for every element in a, check every element in b
    //if b contains the same element as in a, add it to newSet
    for (int i = 0; i < a->num_elements; i++) {
        for (int j = 0; j < b->num_elements; j++) {
            if (a->elements[i] == b->elements[j]) {
                addItem(&newSet, a->elements[i]);
            }
        }
    }
    return newSet;
}

//Returns 1 if the elements in the first set are identical to the elements in the second set. 
//Otherwise, returns 0. If a specified set is NULL, view that as an empty set.
int areSetsEqual(struct set *a, struct set *b) {
    //handles cases when one or both sets are null. Treats null set as empty.
    if (a == NULL && b != NULL) {
        if (b->num_elements == 0) {
            return 1;
        }
        else {
            return 0;
        }     
    }
    else if (a != NULL && b == NULL) {
        if (a->num_elements == 0) {
            return 1;
        }
        else {
            return 0;
        }
    }
    else if (a == NULL && b == NULL) {
        return 1;
    }

    //if num_elements are different, not possible for sets to be equal
    if (a->num_elements != b->num_elements) {
        return 0;
    }
    else {
        if (getIntersection(a, b).num_elements == a->num_elements) {
            return 1;
        }
        else {
            return 0;
        }
    } 
}
