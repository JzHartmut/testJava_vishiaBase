#this file is the user-adapt-able frame for makejar_vishiaBase.sh
#edit some settings if there are different form default.

#export JAVAC_HOME=c:/Programs/Java/jdk1.8.0_211
export JAVAC_HOME=c:/Programs/Java/jdk1.8.0_241
#export JAVAC_HOME=/usr/share/JDK/jdk1.8.0_241

export CLASSPATH=xx
# located from this workingdir as currdir for shell execution:
export SRCPATH="src/main/java/srcJava_vishiaBase;src/test/java"

##build zipjar:
#Note: on changes firstly build with the same given date here.
#Then check the checksum. If it matches, this VERSION date is ok.
#For example if only a comment is changed in sources or nothing is changed,
#the repeated built should deliver a reproducible build.
#-----
#If the checksum and the content does not match, 
# then set the new date here from the last timestamp of the last commit or changed file.
export VERSION="2020-06-16"
#Note: The $VERSIONZIPJAR is used for the further jar builds.
export VERSIONZIPJAR="$VERSION"   #generate exact this version   
export TMPJAVAC=/tmp/javac_TestvishiaBase/javac
export DEPLOY=vishiaTestBase

#use the built before jar to generate jar
export JAR_zipjar=$TMPJAVAC/binjar

# located from this workingdir as currdir for shell execution:
export MANIFEST=src/test/java/_make/TestvishiaBase.manifest
export SRC_ALL="src/test/java/org/vishia/testBase"
export SRC_ALL2="src/main/java/srcJava_vishiaBase"

#now run the common compilation javac script:
chmod 777 src/main/java/srcJava_vishiaBase/_make/-makejar-coreScript.sh
##src/main/java/srcJava_vishiaBase/_make/-makejar-coreScript.sh

export JARFILE=$TMPJAVAC/result/$DEPLOY-$VERSION.jar

echo java -jar $JARFILE 1> build/Test.txt 2> build/Test.err
java -jar $JARFILE 1> build/Test.txt 2> build/Test.err
echo
echo Test output:
cat build/Test.err
cat build/Test.txt
echo
echo to check result compare build/Test.txt with src/test/testResult_Ref/Test.txt

