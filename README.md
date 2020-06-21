# This git Archive contains test Sources and docu for srcJava_vishiaBase

## build

use files in src/buildScripts

* +clean.bat, +clean.sh
* +build.bat, +build.sh

On windows sh.exe should be present, via git-installation or MinGW

The build is done only using shell scripts and JDK commands (javac)
Gradle & co are not used. Less dependencies. Obviously. Reproducible build approach.

## test

* +build.sh executes a test, result produced in build/testResult in text form
* should be compared with src/test/testResult_Ref
* also independent, obviously and simple

## Dependencies

junit-platform-console-standalone-1.6.0.jar is not used anymore

for srcJava_vishiaBase:
This sources have not dependencies. Only the capability of a standard JRE 
(used JRE-8) is necessary, or any proper open-JDK


