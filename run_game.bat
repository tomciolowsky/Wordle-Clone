@echo off
echo Starting Wordle...

:: Run Java, pointing it to the 'bin' folder for the DLL
java -cp bin -Djava.library.path=bin com.wordle.Main

pause