::if "%JAVA_HOME%" == "" 
set JAVA_HOME=c:\Programs\Java\jdk1.8.0_241
set JAVAC_HOME=c:\Programs\Java\jdk1.8.0_241
call +mkLinkBuildGradle.bat
:repeat
cls
call gradle.bat --warning-mode all test 2>build\err.txt
type build\err.txt
echo close window or repeat
pause
goto :repeat
call inetBrowser %CD%\build\reports\tests\test\index.html

