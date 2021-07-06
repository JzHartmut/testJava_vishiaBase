#linux shell call via sh.exe -c thisfile in Windows. sh.exe is part for example of MinGW or git distribution
#Java as JRE8 should be available.

##set all *.sh to executable for all
##do it firstly after clone from git or copy, the file properties will be retained
##NOTE -R only for all files in directory, does not run: chmod -R 777 *.sh  

if test -f ../../src/buildScripts/+build.sh; then cd ../..; fi
find -name '*.sh' -exec chmod 777 {} \;

## Echo the libstd is the destination of jar and bom files.
if test ! -d ../libstd; then mkdir ../libstd; fi

src/buildScripts/+resolveDepsFromWWW.sh
src/buildScripts/+mkLinkBuild.sh

## (Re-)Compile the vishiaBase.jar to .../build
cd src/main/java/srcJava_vishiaBase/_make
find -name '*.sh' -exec chmod 777 {} \;  # again after clone the srcJava_vishiaBase
export VERSIONSTAMP=$(date -I)           # It determines the name of the files.
export BUILD_TMP="../../../../../build"  # relative to src/.../_make
./+makejar_vishiaBase.sh 
cd ../../../../..

export VishiaBaseJAR=build/deploy/vishiaBase-$VERSIONSTAMP.jar
echo it has produced $VishiaBaseJAR
#Note: check the result of the vishiaBaseJar-build, adjust the version in the script above if necessary
#and correct the next line if necessary, in git-Versions.

#uses $VishiaBaseJAR as CLASSPATH and for jar builder

export sepPath=":"
if test "$OS" = "Windows_NT"; then export sepPath=";"; fi

export CLASSPATH=$VishiaBaseJAR
# located from this workingdir as currdir for shell execution:
export SRCPATH="src/test/java"
echo $SRCPATH
export MANIFEST=src/test/java/_make/TestvishiaBase.manifest
export SRC_ALL="src/test/java/org/vishia/testBase"                                                                                                               
export SRC_ALL2=""
export FILE1SRC=""
export TMPJAVAC="build/Test_vishiaBase"
export DSTNAME="vishiaTestBase"
export JAR_zipjar=$VishiaBaseJAR
export BUILD_TMP="build"
export TIMEinJAR="2021-07-01+00:00"

#now run the compilation of the tests:
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
  echo java -cp $BUILD_TMP/deploy/$DSTNAME-$VERSIONSTAMP.jar$sepPath$VishiaBaseJAR org.vishia.testBase.Test
  java -cp $BUILD_TMP/deploy/$DSTNAME-$VERSIONSTAMP.jar$sepPath$VishiaBaseJAR org.vishia.testBase.TestJava_vishiaBase 1> build/testResult/Test.txt 2> build/testResult/Test.err
  echo Test output:
  cat build/testResult/Test.err
  cat build/testResult/Test.txt
  echo
  echo ================================================================================
  echo java -cp $BUILD_TMP/deploy/$DSTNAME-$VERSIONSTAMP.jar$sepPath$VishiaBaseJAR org.vishia.testBase.Test ---TESTverbose:7
  java -cp $BUILD_TMP/deploy/$DSTNAME-$VERSIONSTAMP.jar$sepPath$VishiaBaseJAR org.vishia.testBase.TestJava_vishiaBase ---TESTverbose:7 1> build/testResult/TestVerbose.txt 2> build/testResult/TestVerbose.err
  echo Test output:
  cat build/testResult/TestVerbose.err
  cat build/testResult/TestVerbose.txt
  echo
  echo to check result compare build/testResult/*.txt with src/test/testResult_Ref/*.txt
fi
echo -- end ---------------------------------------------------------------------------



