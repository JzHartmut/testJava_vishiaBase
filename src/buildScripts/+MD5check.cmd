@echo off
call setEnv.cmd
set GUIJAR=vishiaGui-2020-03-14.jar
set GUIJAR_MD5=b9dca1ff8f01c63265007160d3f77de1
set SWTJAR=org.eclipse.swt.win32.win32.x86_64_3.110.0.v20190305-0602.jar
set SWTJAR_MD5=06c8adb06c0be6136a29f8cb1cb98469
set VISHIABASEJAR=vishiaBase-2020-03-14.jar
set VISHIABASE_MD5=3295cc8c0ac593161aadd91bf5b8c380

REM check MD5, uses Windows standard cmd certUtil
::certUtil -hashfile libs/%GUIJAR% MD5
::certUtil -hashfile libs/%SWTJAR% MD5
certUtil -hashfile libs/%VISHIABASEJAR% MD5

pause

