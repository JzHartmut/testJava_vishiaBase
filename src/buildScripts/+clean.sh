cd $(dirname $0)/../..
pwd
if test -d build; then
  rm -r build/*
  rm build  ##it is usual a symlink
fi

