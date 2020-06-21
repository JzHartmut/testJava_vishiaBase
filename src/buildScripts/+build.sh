#linux shell call via sh.exe -c thisfile in Windows. sh.exe is part for example of MinGW or git distribution
#Java as JRE8 should be available.

##set all *.sh to executable for all
##do it firstly after clone from git or copy, the file properties will be retained
cd ../..
find -name '*.sh' -exec chmod 777 {} \;
##NOTE -R only for all files in directory, does not run: chmod -R 777 *.sh  

src/buildScripts/+resolveDepsFromWWW.sh
src/buildScripts/+mkLinkBuild.sh



#export JAVAC_HOME=c:/Programs/Java/jdk1.8.0_211
if test "$JAVAC_HOME" = ""; then export JAVAC_HOME=c:/Programs/Java/jdk1.8.0_241; fi
#export JAVAC_HOME=/usr/share/JDK/jdk1.8.0_241

export BUILD="../../../../../build"
cd src/main/java/srcJava_vishiaBase/_make
./+makejar_vishiaBase.sh 

#Note: check the result of the vishiaBaseJar-build, adjust the version in the script above if necessary
#and correct the next line if necessary, in git-Versions.
export VishiaBaseJAR=build/deploy/vishiaBase-2020-06-16.jar
echo it has produced $VishiaBaseJAR

cd ../../../../..
#uses $VishiaBaseJAR as CLASSPATH and for jar builder

#Do not change the version on repeated build, and check the checksum and content of jar.
#If it is equal, it is a reproduces build. The Version is important because it determines the timestamp
#and hence the checksum in the jar file. 
#Set the version newly here to the current date if the sources are changed in jar and checksum.
export VERSION="2020-06-16"


export sepPath=":"
if test "$OS" = "Windows_NT"; then export sepPath=";"; fi

export CLASSPATH=$VishiaBaseJAR
# located from this workingdir as currdir for shell execution:
####export SRCPATH="src/main/java/srcJava_vishiaBase$sepPath./src/test/java"
export SRCPATH="src/test/java"
echo $SRCPATH
##export TMPJAVAC=/tmp/javac_TestvishiaBase


# located from this workingdir as currdir for shell execution:
export MANIFEST=src/test/java/_make/TestvishiaBase.manifest
export SRC_ALL="src/test/java/org/vishia/testBase"
##export SRC_ALL2="src/main/java/srcJava_vishiaBase"

export TMPJAVAC=build/Test_vishiaBase
export DEPLOY=build/deploy/vishiaTestBase-$VERSION
export JAR_zipjar=$VishiaBaseJAR

#now run the common compilation javac script:
src/main/java/srcJava_vishiaBase/_make/-makejar-coreScript.sh

echo =========================================================
echo =========================================================
echo =========================================================
echo   TEST
echo =========================================================
echo =========================================================
if ! test -f $JARFILE; then
  echo any error while compilation, see results in $TMPJAVAC
else  
  if ! test -d build/testResult; then mkdir build/testResult; fi
  echo ================================================================================
  echo has compiled all sources, vishiaBase and test, to $JARFILE
  echo java -cp $DEPLOY.jar$sepPath$VishiaBaseJAR org.vishia.testBase.Test
  java -cp $DEPLOY.jar$sepPath$VishiaBaseJAR org.vishia.testBase.TestJava_vishiaBase 1> build/testResult/Test.txt 2> build/testResult/Test.err
  echo Test output:
  cat build/testResult/Test.err
  cat build/testResult/Test.txt
  echo
  echo ================================================================================
  echo java -cp $DEPLOY.jar$sepPath$VishiaBaseJAR org.vishia.testBase.Test ---TESTverbose:7
  java -cp $DEPLOY.jar$sepPath$VishiaBaseJAR org.vishia.testBase.TestJava_vishiaBase ---TESTverbose:7 1> build/testResult/TestVerbose.txt 2> build/testResult/TestVerbose.err
  echo Test output:
  cat build/testResult/TestVerbose.err
  cat build/testResult/TestVerbose.txt
  echo
  echo to check result compare build/testResult/*.txt with src/test/testResult_Ref/*.txt
fi
echo -- end ---------------------------------------------------------------------------


