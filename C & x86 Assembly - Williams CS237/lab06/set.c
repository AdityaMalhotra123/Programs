#include "set.h"

/* 
 * Returns a pointer to a dynamically allocated set with the specified
 * number of blocks allocated and initialized.
 */
struct set *initializeSet(int associativity)
{
    struct set *set = malloc(sizeof(struct set));
    set->blockArray = malloc((1 << associativity)*sizeof(struct block));
    set->associativity = associativity;

    // initially, each block is invalid
    for (int i = 0; i < associativity; i++) {
		set->blockArray[i].valid = 0;
	}
    return set;
}

/* 
 * Frees all memory dynamically allocated to store set data structure.
 */
void freeSet(struct set* set)
{
    free(set->blockArray);
    free(set);
}

/*
* Update LRUs for blocks in set based on given index of most
* recently used block in blockArray.
*/
void updateLRU(struct set *s, int lruIndex) 
{
    for (int i = 0; i < s->associativity; i++) {
        // only update lru if block contains cache data.
        if (s->blockArray[i].valid != 0) {
            // if miss, increment all valid block LRUs.
            if (s->blockArray[lruIndex].valid == 0) {
                s->blockArray[i].lru++;
            }
            // if hit or eviction, only increment LRUs of blocks that don't
            // have greater LRU than most recently used block.
            // ensures lru < associativity for all blocks.
            else if (s->blockArray[i].lru < s->blockArray[lruIndex].lru) {
                if ((i != lruIndex)) {
                    s->blockArray[i].lru++;
                }
                
            }    
        }
    }
        
    // set most recently used block LRU to 0.
    s->blockArray[lruIndex].lru = 0;
}

/* 
 * Adds the access of specified tag to the set. Returns 1 if
 * was a hit. Returns 0 if a miss with no eviction, -1 if a 
 * miss that caused an eviction.
 */
int addSetAccess(struct set*s, unsigned long long int tag)
{
    if (s != NULL) {
        // check for hit
        for (int i = 0; i < s->associativity; i++) {
            if (s->blockArray[i].valid != 0) {
                if (s->blockArray[i].tag == tag) {
                    updateLRU(s, i);
                    return 1;
                }
            }
        }

        // check for miss and update first available block
        for (int i = 0; i < s->associativity; i++) {
            if (s->blockArray[i].valid == 0) {
                s->blockArray[i].tag = tag;
                updateLRU(s, i);
                s->blockArray[i].valid = 1;
                return 0;
            }
        }

        // since it is not a hit or miss, perform eviction of lru block
        for (int i = 0; i < s->associativity; i++) {
            if (s->blockArray[i].lru == (s->associativity - 1)) {
                s->blockArray[i].tag = tag;
                updateLRU(s, i);
                return -1;
            }
        }
    }
    return 0;
}
