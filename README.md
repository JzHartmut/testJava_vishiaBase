# This git Archive contains test Sources and docu for srcJava_vishiaBase

## build

See also https://www.vishia.org/Java/html5/source+build/buildtestVishiaJava.html#truethe-compilation-tools-and-reproducible-build


use files in src/buildScripts

* +adjustTimestamps.sh   ... to set the timestamps from originals (from .filelist)
* +clean.bat, +clean.sh  
* +build.bat  ... prepares a build directory in TMP via Windows mklink /JUNCTION
* +build.sh   ... prepares a build directory via symblic link in /tmp

On windows sh.exe should be present, via git-installation or MinGW

## Tools necessary

If git is installed, sh.exe is present. Alternative: MinGW

Java is need. The files are compiled with JAVA-8.245 (Oracle). 
For invocation the simple java call is used. 

check if present:

 java --version
 
A JAVA-JDK is necessary. javac should be run.

The file src/main/java/srcJava_vishiaBase/_make/JAVAC_CMD.sh can be adapted
to use the proper JAVA-JDK. 
The compilation is done for test with Oracles jdk1.8.0_241 both on linux and windows
with reproduced equal binary results. 
Another jdk version is possible, Open-JDK 11 was used in Linux. 
The test results shows whether it is proper. 

The build is done only using shell scripts and JRE/JDK as commands (java, javac)
Gradle & co are not used. Less dependencies. Obviously. Reproducible build approach.

## test

* +build.sh executes a test, result produced in build/testResult in text form
* should be compared with src/test/testResult_Ref
* also independent, obviously and simple

## Dependencies

junit-platform-console-standalone-1.6.0.jar is not used anymore

for srcJava_vishiaBase:
This sources have not external dependencies. Only the capability of a standard JRE 
(used JRE-8) is necessary, or any proper open-JDK


