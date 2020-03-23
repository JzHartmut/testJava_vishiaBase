set DIRBIN=%TMP%\Eclipse\testJava_vishiaBase
if exist bin rmdir /S/Q bin
if exist %DIRBIN% rmdir /S/Q %DIRBIN% 
mkdir %DIRBIN%
mklink /J bin %DIRBIN%

