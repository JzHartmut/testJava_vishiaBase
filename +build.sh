export JAVAC_HOME=c:/Programs/Java/jdk1.8.0_241

cd src/main/java/srcJava_vishiaBase/_make
./+makejar_vishiaBase.sh
export VishiaBaseJAR=build/vishiaBase/result/vishiaBase-2020-06-16.jar
echo it has produced $VishiaBaseJAR

cd ../../../../..
#uses $VishiaBaseJAR as CLASSPATH and for jar builder

export sepPath=":"
if test "$OS" = "Windows_NT"; then export sepPath=";"; fi

export CLASSPATH=$VishiaBaseJAR
# located from this workingdir as currdir for shell execution:
####export SRCPATH="src/main/java/srcJava_vishiaBase$sepPath./src/test/java"
export SRCPATH="src/test/java"
echo $SRCPATH
export VERSION="2020-06-16"
##export TMPJAVAC=/tmp/javac_TestvishiaBase
export TMPJAVAC=build/Test_vishiaBase
export DEPLOY=vishiaTestBase

#use the built before jar to generate jar
##export JAR_zipjar=$TMPJAVAC/binjar
export JAR_zipjar=$VishiaBaseJAR
echo AAA

# located from this workingdir as currdir for shell execution:
export MANIFEST=src/test/java/_make/TestvishiaBase.manifest
export SRC_ALL="src/test/java/org/vishia/testBase"
##export SRC_ALL2="src/main/java/srcJava_vishiaBase"
echo AAA

#now run the common compilation javac script:
chmod 777 src/main/java/srcJava_vishiaBase/_make/-makejar-coreScript.sh
src/main/java/srcJava_vishiaBase/_make/-makejar-coreScript.sh

export JARFILE=$TMPJAVAC/result/$DEPLOY-$VERSION.jar
echo =========================================================
if ! test -f $JARFILE; then
  echo any error while compilation, see results in $TMPJAVAC
else  
  echo has compiled all sources, vishiaBase and test, to $JARFILE
  echo executes java -jar $JARFILE
  java -cp $JARFILE$sepPath$VishiaBaseJAR org.vishia.testBase.Test 1> $TMPJAVAC/result/Test.txt 2> $TMPJAVAC/result/Test.err
  echo ================================================================================
  echo Test output:
  cat $TMPJAVAC/result//Test.err
  cat $TMPJAVAC/result//Test.txt
  echo
  echo to check result compare $TMPJAVAC/result/Test.txt with src/test/testResult_Ref/Test.txt
fi
echo -- end ---------------------------------------------------------------------------



