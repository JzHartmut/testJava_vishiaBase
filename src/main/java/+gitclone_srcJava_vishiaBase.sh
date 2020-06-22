echo this shell script gets the srcJava_vishiaBase core sources of emC
echo if not exists srcJava_vishiaBase: clone https://github.com/JzHartmut/srcJava_vishiaBase.git
cd `dirname $0`  ##script directory as current
VERSION_TAG="2020-06-22"
if ! test -d srcJava_vishiaBase; then
  echo for the present clone the srcJava_vishiaBase with tag "$VERSION_TAG" as 'detached head':
  git clone https://github.com/JzHartmut/srcJava_vishiaBase.git -b $VERSION_TAG
  cd srcJava_vishiaBase
  pwd
  echo touch all files with the timestamp in .filelist:
  #this file is part of test_emC, hence the .../libs exists:
  java -cp ../../../../libs/vishiaMinisys.jar org.vishia.util.FileList T -l:.filelist -d:.
else 
  echo srcJava_vishiaBase already exists, not cloned 
fi

