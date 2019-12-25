## UnicodeNormalizer

Author: Oliver Kaljuvee

#### Overview

General purpose CSV normalizer for UTF-8 based on an abstract processor, which can be assigned to any CSV column by key and linked 
with other processors, giving the ability to define separate parsing and normalization rules for every field
in the CSV file.  See `sample.csv` and `output.csv` for sample input and output.  The solution uses aspects of 
_Chain of Responsibility_ and _Iterator_ patterns to accomplish the task.

#### Installing and Running
These commands assume that you have installed **Java JDK 8+**:


```shell script
> git clone https://github.com/okaljuvee/UnicodeNormalizer.git
> cd UnicodeNormalizer
> chmod u+x normalizer
> ./normalizer sample.csv output.csv
```

On Windows, the last two commands should be replaced with:

```shell script
> ./gradlew runNormalizer --args "sample.csv output.csv"
```

### Requirements

The requirements for the exercise are listed and commented here:
* One of the requirements was to use `stdin` and `stdout`.  I didn't have time to convert the argument-based 
setup to use the standard input and output, but I did create the wrapper script for convenience.
* Entire CSV is in the UTF-8 character set ([Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/Normalizer.java#L105))
* The `Timestamp` field is formatted in ISO-8601 format ([Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/processor/TimestampProcessor.java#L36))
* The `Timestamp` field is assumed to be in US/Pacific time and converted into US/Eastern ([Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/processor/TimestampProcessor.java#L27) and [Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/processor/TimestampProcessor.java#L34))
* The `ZIP` code field is formatted as 5 digits and if there are less than 5 digits, 0 is used as the prefix ([Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/processor/ZipCodeProcessor.java#L18))
* The `FullName` field is converted to uppercase ([Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/processor/NameProcessor.java#L22))
* The `Address` field is passed through as is and commas are parsed correctly within the quoted fields
* The `FooDuration` and `BarDuration` fields are parsed from HH:MM:SS.MS format (where MS is milliseconds) and these durations are converted to total number of seconds expressed in floating point format without any rounding ([Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/processor/FieldProcessor.java#L77))
* The `TotalDuration` field in the output is the sum of the `FooDuration` and `BarDuration`, again as floating point seconds and is deliberately not converted back tot he duration format above
* The `Notes` field is free form text input by end-users any invalid UTF-8 characters are replaced with the Unicode Replacement Character, which is handled by the Apache Commons CSV library
* If any field is ate field "unparseable," warning is printed in `stderr` and the record is dropped from the output ([Reference](https://github.com/okaljuvee/UnicodeNormalizer/blob/a9dd289af99ddbbaa45490ce2049cc745b53b04c/src/main/java/info/kaljuvee/Normalizer.java#L123))

#### Observations

While Java can be quite verbose for something like string normalization; it lends itself 
well for writing extendable solutions.  Java of course also has great open source APIs 
as demonstrated here with the use of Apache Commons CSV library.