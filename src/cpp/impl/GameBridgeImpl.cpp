#include <jni.h>
#include <iostream>
#include <vector>
#include <memory>
#include <stdexcept>
#include "com_wordle_bridge_GameBridge.h" 
#include "StandardGame.h"

std::unique_ptr<AbstractGame> game = std::make_unique<StandardGame>();

extern "C" JNIEXPORT void JNICALL Java_com_wordle_bridge_GameBridge_startGame
  (JNIEnv *env, jobject obj, jstring jPath){
    try
    {
      const char *pathChars = env->GetStringUTFChars(jPath, 0);
      std::string dictPath = pathChars;
      env->ReleaseStringUTFChars(jPath, pathChars);
      std::cout << "[C++] Bridge: Loading dictionary from: " << dictPath << std::endl;

      std::cout << "[C++] Starting new game..." << std::endl;
      game->startNewGame(dictPath);
      std::cout << "[C++] Secret word selected: " << game->getDebugTarget() << std::endl;
    }
    catch(const std::exception& e)
    {
      std::cerr << "[C++ ERROR] Exception in startGame: " << e.what() << std::endl;
    }
}

extern "C" JNIEXPORT jstring JNICALL Java_com_wordle_bridge_GameBridge_getStatistics
  (JNIEnv *env, jobject obj){
    try {
        std::string statsStr = game->getStatsString();
        return env->NewStringUTF(statsStr.c_str());
    } 
    catch (...) {
        return env->NewStringUTF("Error retrieving stats");
    }
  }

extern "C" JNIEXPORT jintArray JNICALL Java_com_wordle_bridge_GameBridge_processGuess
  (JNIEnv *env, jobject obj, jstring javaGuess){

    jintArray errorResult = env->NewIntArray(5); 
    
    try {
        
        const char *nativeString = env->GetStringUTFChars(javaGuess, 0);
        std::string guess = nativeString;
        env->ReleaseStringUTFChars(javaGuess, nativeString);

        std::vector<int> resultVector = game->processGuess(guess);

        jintArray javaResult = env->NewIntArray(5);
        jint fill[5];
        for (int i = 0; i < 5; i++) {
            fill[i] = resultVector[i];
        }
        env->SetIntArrayRegion(javaResult, 0, 5, fill);

        return javaResult;
    } 
    catch (const std::exception& e) {
        std::cerr << "[C++ ERROR] Exception in checkGuess: " << e.what() << std::endl;
        return errorResult; 
    }

}