#nothing to do, no furter dependencies.
#Note: Java-jdk should be present. javac should be run as command. 

echo =========================================================================
echo execute $0
echo 'test using $0: dirname $0 outputs the dir path'
dirname $0
echo 'cd to the cmpn directory: '
cd $(dirname $0)/../..
pwd
#rem if necessary clone sources.
src/main/java/+gitclone_srcJava_vishiaBase.sh


