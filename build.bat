call +clean_mkLinkBuild.bat
:repeat
sh.exe -C ./build.sh
echo ctrl-C for exit, other key: repeat build & test
pause
cls
goto :repeat

