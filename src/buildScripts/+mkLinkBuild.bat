REM it creates the links to TMP and the directories in TMP

REM do nothing if build exists, as link or direct.
REM Note: removing only the build link is sufficient to clean all.
if exist ..\..\build exit /b

REM TMP should be set in windows, it may refer a RAM disk
REM only emergency if TMP is not set:
if "%TMP%"=="" ( 
  set TMP=c:\tmp
  mkdir c:\tmp
)

REM The used temporary inside %TMP%
set TD=%TMP%\BuildJava_vishiaBase

REM clean content if build is existing, and link
REM Note rmdir /S/Q does not remove files under a found link inside
REM Note: rmdir /S/Q cleans all, del /S/Q/F does not clean the directory tree
if exist %TD% rmdir /S/Q %TD% 
mkdir %TD%
mklink /J ..\..\build %TD%

exit /b

