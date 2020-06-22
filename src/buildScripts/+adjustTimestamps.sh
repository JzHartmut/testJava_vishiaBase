#cd `dirname $0`/../..  ##script directory as current
cd $(dirname $0)/../..  ##script directory as current
pwd
echo touch all files with the timestamp in .filelist:
java -cp libs/vishiaMinisys.jar org.vishia.util.FileList T -l:.filelist -d:.

