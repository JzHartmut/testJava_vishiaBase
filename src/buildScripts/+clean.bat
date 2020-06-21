@echo off
if exist ..\..\.gradle rmdir /S/Q ..\..\.gradle
if exist ..\..\gradle rmdir /S/Q ..\..\gradle
if exist ..\..\build rmdir /S/Q ..\..\build
if not "%1"=="nopause" pause
