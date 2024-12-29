# Lab Feedback

## Summary Score

| Category       | Score |
| -------------- | ----- |
| Functionality  |   A   |
| Design         |   A   |
| Documentation  |   A   |

---

## General Comments
Nice job this week! 

### Functionality
All of the tests pass — good work! It would have been great to include a main() method in the LexiconNode class (just to ensure that the class functions as expected), but this wasn't required. 

### Design
Good helper method for the iterator in LexiconTrie (and good use of recursion). There are a couple of places where a helper method could have been beneficial (containsWord and containsPrefix, for example, are very similar), but the methods are fairly concise throughout.

### Documentation
Solid documentation. I appreciated the use of both javadoc comments and inline comments — this made the program logic clear and easy to follow. Remember to inlcude class-level comments as well in the future!

---

## Rubric

### Assignment is anonymous

[+] Submission should be anonymous (no name at top)

### Basics

[+] Assignment compiles

### Implementation

[+] addWordsFromFile works correctly

[+] addWord works correctly

[+] removeWord works correctly

[+] numWords works correctly

[+] containsPrefix works correctly

[+] iterator works correctly

### Extra Credit

[-] fixing remove to correctly delete extrannous nodes (so that containsPrefix works correctly) is correctly implemented

[-] spelling corrections are correctly implemented

[-] regular expressions are correctly implemented

### Design

[+] Children are stored in sorted order

[+] Operations are implemented efficiently using trie operations

[~/+] Functionality is split into helper methods where appropriate

### Style

[+] Checkstyle passes
