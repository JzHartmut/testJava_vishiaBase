echo this shell script gets the srcJava_vishiaBase core sources
echo if not exists src/main/java/org: clone https://github.com/JzHartmut/srcJava_vishiaBase.git
cd `dirname $0`  ##script directory as current
if ! test -d src/main/java/org; then
  git clone -b 2020-04-04 https://github.com/JzHartmut/srcJava_vishiaBase.git src/main/java
  cd src/main/java
  pwd
  echo touch all files with the timestamp in .filelist:
  #TODO java -cp ../../../../downloaded/jars/vishiaBase.jar org.vishia.util.FileList T -l:emC.filelist -d:.
else 
  echo srcJava_vishiaBase already exists, not cloned 
fi

