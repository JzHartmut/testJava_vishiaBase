call +clean.bat
set DIRBIN=%TMP%\Eclipse\testJava_vishiaBase
if exist %DIRBIN% rmdir /S/Q %DIRBIN%
mkdir %DIRBIN%
mklink /J bin %DIRBIN%

