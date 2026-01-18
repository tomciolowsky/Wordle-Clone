@echo off
echo ========================================
echo       Wordle Project Builder
echo ========================================

:: 1. Check if JAVA_HOME is set
if "%JAVA_HOME%" == "" (
    echo [ERROR] JAVA_HOME is not set on this computer.
    echo Please set the JAVA_HOME environment variable to your JDK folder.
    pause
    exit /b
)

:: 2. Create Output Directory
if not exist "bin" mkdir bin

:: ============================================================
:: STEP 3: COMPILE JAVA & GENERATE HEADER (Moved to First!)
:: ============================================================
echo [1/2] Compiling Java GUI and Generating Header...
dir /s /B src\java\*.java > sources.txt

:: -h flag generates the C++ header file in the include folder
javac -d bin -h src/cpp/include -encoding UTF-8 @sources.txt
del sources.txt

if %errorlevel% neq 0 (
    echo [ERROR] Java Compilation Failed!
    pause
    exit /b
)

:: ============================================================
:: STEP 4: COMPILE C++ (The Logic)
:: ============================================================
echo [2/2] Compiling C++ Logic...
g++ -shared -o bin/WordleLogic.dll ^
    src/cpp/impl/*.cpp ^
    -I"%JAVA_HOME%\include" ^
    -I"%JAVA_HOME%\include\win32" ^
    -I"src/cpp/include" ^
    -static-libgcc -static-libstdc++

if %errorlevel% neq 0 (
    echo [ERROR] C++ Compilation Failed!
    pause
    exit /b
)

echo.
echo [SUCCESS] Build Complete! You can now run run.bat
echo ========================================
pause