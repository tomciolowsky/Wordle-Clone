@echo off
echo ========================================
echo       Wordle Project Builder
echo ========================================

:: 1. Create Output Directory
if not exist "bin" mkdir bin

:: 2. COMPILE C++ (The Logic)
echo [1/3] Compiling C++ Logic...

:: SET JAVA PATH HERE:
set JAVA_DIR="C:\Program Files\Eclipse Adoptium\jdk-21.0.7.6-hotspot"

g++ -shared -o bin/WordleLogic.dll ^
    src/cpp/impl/*.cpp ^
    -I%JAVA_DIR%\include ^
    -I%JAVA_DIR%\include\win32 ^
    -I"src/cpp/include" ^
    -static-libgcc -static-libstdc++

if %errorlevel% neq 0 (
    echo [ERROR] C++ Compilation Failed!
    pause
    exit /b
)

:: 3. COMPILE JAVA (The GUI)
echo [2/3] Compiling Java GUI...
dir /s /B src\java\*.java > sources.txt
javac -d bin -h src/cpp/include @sources.txt
del sources.txt

if %errorlevel% neq 0 (
    echo [ERROR] Java Compilation Failed!
    pause
    exit /b
)

echo.
echo [SUCCESS] Build Complete! You can now run run.bat
echo ========================================
pause