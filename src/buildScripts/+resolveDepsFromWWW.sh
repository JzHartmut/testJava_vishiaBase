#nothing to do, no furter dependencies.
#Note: Java-jdk should be present. javac should be run as command. 

#The script is never called in a path, always with immediately directory.
dirname $0
cd $(dirname $0)/../..
pwd
#rem if necessary clone sources.
src/main/java/+gitclone_srcJava_vishiaBase.sh


