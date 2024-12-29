#include "cache.h"
#include "stdio.h"

/* 
 * Returns a pointer to a dynamically allocated cache with the specified
 * associativity and using the number of specified set index bits and block 
 * offset bits.
 */
struct cache *initializeCache(int setIndexBits, int associativity,
			      int blockOffsetBits)
{
	struct cache *cache = malloc(sizeof(struct cache));
	cache->setArray = malloc((1 << setIndexBits) * sizeof(struct set *));
	cache->setIndexBits = setIndexBits;
	cache->blockOffsetBits = blockOffsetBits;

	//initialize each set in cache
	for (int i = 0; i < (1 << setIndexBits); i++) {
		cache->setArray[i] = initializeSet(associativity);
	}

	//initialize stats
	cache->numHits = 0;
	cache->numMisses = 0;
	cache->numEvictions = 0;

	return cache;
}

/* 
 * Frees all memory dynamically allocated to store cache data structure 
 */
void freeCache(struct cache *cache)
{
	for (int i = 0; i < (1 << cache->setIndexBits); i++) {
		free(cache->setArray[i]);
	}
	free(cache);
}

/* 
 * Adds the access of specified address to the cache.  
 */
void addCacheAccess(struct cache *cache, unsigned long long int address)
{
	int setMask = (1 << cache->setIndexBits) - 1;
	int setIndex = setMask & (address >> cache->blockOffsetBits);
	int tag = address >> (cache->setIndexBits + cache->blockOffsetBits);
	int status = addSetAccess(cache->setArray[setIndex], tag); 

	//update statistics
	if (status == 1) {
		cache->numHits++;
	}
	else if (status == 0) {
		cache->numMisses++;
	}
	else {
		cache->numMisses++;
		cache->numEvictions++;
	}
}


