echo this shell script gets the srcJava_vishiaBase core sources
cd `dirname $0`  ##script directory as current
VERSION_TAG="2020-07-09"
echo clone srcJava_vishiaBase tag $VERSION_TAG from github if necessary ...
if ! test -d srcJava_vishiaBase; then
  echo for the present clone the srcJava_vishiaBase with tag "$VERSION_TAG" as 'detached head':
  echo git clone https://github.com/JzHartmut/srcJava_vishiaBase.git -b $VERSION_TAG
  #git clone https://github.com/JzHartmut/srcJava_vishiaBase.git -b $VERSION_TAG
  #Note comment with VERSION_TAG, uncomment next, to get the proper last stored version 
  git clone https://github.com/JzHartmut/srcJava_vishiaBase.git
  cd srcJava_vishiaBase
  pwd
  echo touch all files with the timestamp in .filelist:
  #this file is part of TestJava_vishiaBase, hence the .../libs exists:
  java -cp ../../../../libs/vishiaMinisys.jar org.vishia.util.FileList T -l:.filelist -d:.
else 
  echo srcJava_vishiaBase already exists, not cloned 
fi

