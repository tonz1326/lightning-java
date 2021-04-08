# DTN Exercise Java
This is a simple code snippet that check if particular assets has been striked by lightning.
It will assets striked by lightning with format: lightning alert for <assetOwner>:<assetName>
It will only log asset once

# Filesystem
* ```assets``` - Hold sample asset json file
* ```input``` - Hold sample lightning input file
* ```src``` - Contains main file which performs the program

# Requirements
Java openjdk version "11.0.10"
Javac version 11.0.10

# Setup
Clone the repository in your local machine

# How to run
1. Navigate to project root folder using terminal
2. Navigate to java code by command 'cd src/main/java'
3. Run command 'java --class-path ../libs/jar/ethlo.jquad.jar:../libs/jar/json-simple-1.1.jar  Lightning.java'

# Libraries used
jquad - Used for computing quadkey to lon/lat (https://github.com/ethlo/jquad)
JSONParser - Parse json text file
