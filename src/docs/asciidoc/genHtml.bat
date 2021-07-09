:loop
echo generating buildtestVishiaJava
::call D:\vishia\Java\Asciidoc\asciidoctorj-2.5.1\bin\asciidoctorj.bat source+build/buildtestVishiaJava.adoc -D ../../../../html/source+build
echo generating src_Archive
::call D:\vishia\Java\Asciidoc\asciidoctorj-2.5.1\bin\asciidoctorj.bat source+build/src_Archive.adoc -D ../../../../html/source+build
echo generating DataExchange_C
call ..\..\..\..\Asciidoc\asciidoctorj-2.5.1\bin\asciidoctorj.bat Java_C/DataExchange_C.adoc -D ../../../../html/Java_C
echo done
pause
goto :loop

