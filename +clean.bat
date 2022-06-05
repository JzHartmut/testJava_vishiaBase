echo off
REM cleans build directory

echo called: %0

cd src\buildScripts
call +clean.bat nopause
cd ..\..\src\test\EclipsePj
call +clean.bat nopause
cd ..\..\..

REM remove first the content of build because it may be a link.
REM the next deletes only files, do not delete directories ...
if exist build del /S/Q/F build\* > NUL

REM remove all
if exist build rmdir /S /Q build

REM now remove build, maybe as linked folder.
REM Note: rmdir does not remove files in a linked folder. ...?
if exist build rmdir build

if not "%1"=="nopause" pause
exit /b
