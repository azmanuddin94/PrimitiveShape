@echo off
color 1e
echo.
:menu
echo.
echo 			WELCOME TO    
echo 		TMP2634 Multimedia Programming                 
echo.
echo 		    --- PROJECT ---                     
echo.
echo 	1) Drawing.java
echo 	2) VIEW COVER PAGE                                     
echo 	3) EXIT                                                
echo ***********************************************************
echo.
set /p m=Select an option :
if '%m%'=='1' goto Q1
if '%m%'=='2' goto VIEW
if '%m%'=='3' goto EXIT

:Q1
echo.
javac SplashScreen.java
java SplashScreen
javac Drawing.java
java Drawing
echo ---------------------------------------------------------
cls
goto menu

:VIEW
echo.
Cover_report.pdf
cls
goto menu

:EXIT
echo.
exit()