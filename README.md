# Project Title

Robot challenge

## Getting Started

To build run the following:
```
gradlew build
```
Then to run in windows:
```
start.bat
```
or:
```
gradlew run
```

### Prerequisites

You need to have an internet connection

## Running the tests

Tests are run during the build, mentioned in getting started.  
One test uses an input file which can be added to, /src/test/resources/Instructions.txt  
The test which uses the file runs as follows:  
Run each line as an instruction  
Once it hits "RESULT:" it checks that the result matches the toString of the Robot object.  

## Authors

* **Dean Scott**
