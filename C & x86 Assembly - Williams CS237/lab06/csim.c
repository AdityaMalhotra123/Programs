#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <getopt.h>
#include "cachelab.h"
#include "cache.h"

#define MAX_TRACE_LENGTH 60

// Given a tracefile filename and an initialized cache,
// reads memory traces and accesses cache memory accordingly
void readTracefile(char *filename, struct cache *cache) 
{  
    FILE *fp;
    char trace[MAX_TRACE_LENGTH];
    
    //ensure file is readable and exists
    fp = fopen(filename, "r");
    if (fp == NULL) {
        perror("Error opening file.");
        exit(EXIT_FAILURE);
    }
    else {
        while (fgets(trace, MAX_TRACE_LENGTH, fp)!= NULL) {
            char firstChar;
            char operation;
            unsigned long long int address;
            int size;

            // parse trace
            sscanf(trace, "%c%c %llx,%d", &firstChar, &operation, 
            &address, &size);

            // read or write in cache based on operation mentioned in trace
            switch(operation) {
                case 'L':
                    addCacheAccess(cache, address);
                    break; 
                case 'S':
                    addCacheAccess(cache, address);
                    break;
                case 'M':
                    // since M is a combination of L and S,
                    // cache access added twice.
                    addCacheAccess(cache, address);
                    addCacheAccess(cache, address);
                    break;
                default:
                    break;
            }
        }
        fclose(fp);
    }
}

// Given a memory address tracefile and the parameters of the cache,  
// simulates the hit/miss behavior of a cache memory on this trace, 
// and outputs the total number of hits, misses, and evictions. 
int main(int argc, char *argv[])
{
    struct cache *cache = NULL;
    int inputs = 0;
    int setIndexBits = 0;
	int blockOffsetBits = 0;
    int associativity = 0;
    char filename[MAX_TRACE_LENGTH];

    // parse inputs
    while ((inputs = getopt(argc, argv, "s:E:b:t:")) != -1) {
        switch(inputs){
            case 's':
                setIndexBits = atoi(optarg);
                break;
            case 'E':
                associativity = atoi(optarg);
                break;
            case 'b':
                blockOffsetBits = atoi(optarg);  
                break;
            case 't':
                strcpy(filename, optarg);
                break;
        }
    }

    // check for invalid input
    if (setIndexBits <= 0 || associativity <= 0 || blockOffsetBits <= 0 ||
        strcmp(filename, "") == 0) {
            printf("Usage: ./csim [-hv] -s <s> -E <E> -b <b>"); 
            printf(" -t <tracefile>.\n");
            printf("s, E, b must be integers greater than 0"); 
            printf(" and tracefile is a valid filename.\n");
            exit(1);
        }

    cache = initializeCache(setIndexBits, associativity, blockOffsetBits);
    readTracefile(filename, cache);
    printSummary(cache->numHits, cache->numMisses, cache->numEvictions);
    return 0;
}
