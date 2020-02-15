set JAVA_HOME=c:\Programs\Java\jdk1.8.0_211
::if exist T:\vishia\Testbuild rmdir /S/Q T:\vishia\Testbuild
if not exist T:\vishia\Testbuild mkdir T:\vishia\Testbuild
if not exist build mklink /J build T:\vishia\Testbuild
::pause
call gradle.bat test 2>build\err.txt
type build\err.txt
pause


