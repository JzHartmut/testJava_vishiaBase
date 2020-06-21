echo this shell script gets the TestJava_vishiaBase archive
echo if not exists TestJava_vishiaBase: clone https://github.com/JzHartmut/TestJava_vishiaBase.git
cd `dirname $0`  ##script directory as current
if ! test -d TestJava_vishiaBase; then
  git clone https://github.com/JzHartmut/TestJava_vishiaBase.git 
  cd TestJava_vishiaBase
  pwd
  echo touch all files with the timestamp in .filelist:
  java -cp libs/vishiaMinisys.jar org.vishia.util.FileList T -l:.filelist -d:.
else 
  echo TestJava_vishiaBase already exists, not cloned 
fi

