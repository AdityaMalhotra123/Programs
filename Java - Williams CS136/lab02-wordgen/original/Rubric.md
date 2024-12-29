# Lab Feedback

## Summary Score

| Category       | Score |
| -------------- | ----- |
| Functionality  |   B   |
| Design         |   B   |
| Documentation  |   A   |

---

## General Comments
Good work, but certain cases should be handled differently.

### Functionality
A few issues.  Although you check for the case when a user starts the program without any arguments, the program does not quit after telling the user what to type (it actually waits for input after the check).  Moreover, the usage message is incorrect.  Such messages should inform the user how to run the program.  Reading your code, the program should be called like:

```
$ java WordGen 2 < input.txt
```

Everything works, but the program is slow---which suggests problematic data structure usage--and sometimes the k-value is decreased for reasons I don't understand.

### Design
The design could be improved.  The biggest issue is that the `main` method does quite a bit of work.  It would be better to package up some of that work into methods, so that the processing phases of the program can be clearly seen at a glance.  For example, the loop that calls `Scanner` would be better encapsulated in a method.

The variable `k` was presumably made to be `static` because of the structure of your first conditional, where `parseInt` is called in the `else` clause of the loop.  generally, we avoid `static` variables---they often introduce surprising bugs.  Second, is the `else` clause actually needed?  If, for instance, your program quit after checking `args.length`, then an `else` clause would not be needed.  Alternatively, the entire rest of the `main` method could be moved into the `else` clause, which would avoid the issue mentioned above in the functionality section.

Finally-- if `k` is not an integer, your program throws an exception.  Try the following

```
$ java WordGen hi < text/whosonfirst.txt
```

These problems are hard to see when methods are long, so try to shorten methods in the future.  Your `Table` and `FrequencyList` classes look much better.

### Documentation
Helpful and thorough comments.

---

## Rubric


## Implementation
[+] Submitted code successfully compiles

[~] Parsing Text: code exists to handle input string, using it to generate k-length substrings

[+] Generating Text: code exists to create new string using Table/FrequencyList methods

[~] WordGen allows user input to specify level of analysis (k should not be fixed)

[+] Allows either input redirection (`< file.txt`) or reading file using Scanner

[+] Table has data structure to map String objects to FrequencyList (Likely Vector<Association<String, FrequencyList>>)

[+] FrequencyList has data structure to map String (or Character) to Integer (Likely Vector<Association<String, Integer>>)

[+] Table's update method compares Association keys with k-length String and updates target FrequencyList if found

[+] Table's update method compares Association keys with k-length String and creates a new Association if missing

[+] FrequencyList's update method compares Association's keys String/Character and updates target count if found (count + 1)

[+] FrequencyList's update method compares Association's keys String/Character and creates a new Association if missing (count = 1)

[+] FrequencyList has method to sample from distribution *weighted by frequencies*

[+] WordGen has a method to gracefully handle the case where last-output characters are not found in the Table

## Style
[+] Passes checkstyle with no ERROR messages (WARN is OK)
[+] `javac` compiles without warnings for incomplete use of Java Generics

## General
[+] Assignment is anonymous (no identifying information in submitted files)
