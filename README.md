## UnicodeNormalizer

Author: Oliver Kaljuvee

#### Overview

TBD

#### Installing and Running
These commands assume that you have installed **Java JDK 8+**:


```shell script
> git clone https://github.com/okaljuvee/UnicodeNormalizer.git
> cd UnicodeNormalizer
> chmod u+x gradlew
> ./gradlew runNormalizer --args "sample.csv output.csv"
```


#### Observations

While Java can be quite verbose for something like string normalization; however, it lends itself 
well for writing an extendable solutions.  Java of course also has great open source APIs 
as demonstrated here with the use of Apache Commons CSV library.