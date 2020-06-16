@echo off
rmdir .gradle
rmdir gradle
rmdir /S /Q build
if not "%1"=="nopause" pause
