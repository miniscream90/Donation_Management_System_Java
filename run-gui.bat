@echo off
setlocal

rem Detect JDK bundled in project
set JDK_DIR=%~dp0oracleJdk-25
if exist "%JDK_DIR%\bin\javac.exe" (
  set JAVA_HOME=%JDK_DIR%
) else (
  echo Using system Java. Ensure JDK 17+ is installed and on PATH.
)

set PATH=%JAVA_HOME%\bin;%PATH%

echo Compiling sources...
javac -encoding UTF-8 DonationManagementSystemConsole.java DonationManagementSystemApp.java || goto :error

echo Launching GUI...
java DonationManagementSystemApp
goto :eof

:error
echo Build failed. Fix errors above.
exit /b 1


