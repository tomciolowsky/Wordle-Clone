#include <jni.h>
#include <iostream>
#include <vector>
#include <memory>
#include "com_wordle_bridge_GameBridge.h" // This is the file you just generated!
#include "StandardGame.h"

// POLYMORPHISM: Pointer of type Parent, holding object of type Child 
StandardGame* game = new StandardGame();

extern "C" JNIEXPORT void JNICALL Java_com_wordle_bridge_GameBridge_startGame
  (JNIEnv *env, jobject obj){
    std::cout << "[C++] Starting new game..." << std::endl;
    
    game->startNewGame("assets/dictionary.txt");
    
    std::cout << "[C++] Secret word selected: " << game->getDebugTarget() << std::endl;
}

extern "C" JNIEXPORT jstring JNICALL Java_com_wordle_bridge_GameBridge_getStatistics
  (JNIEnv *env, jobject obj){
    // get c++ string
    std::string statsStr = game->getStatsString();
    // convert c++ to Java
    return env->NewStringUTF(statsStr.c_str());
  }

extern "C" JNIEXPORT jintArray JNICALL Java_com_wordle_bridge_GameBridge_processGuess
  (JNIEnv *env, jobject obj, jstring javaGuess){
    // Java String to C++ string
    const char *nativeString = env->GetStringUTFChars(javaGuess, 0);
    std::string guess = nativeString;
    env->ReleaseStringUTFChars(javaGuess, nativeString);

    // Check guess
    std::vector<int> resultVector = game->processGuess(guess);

    // C++ string back to Java String
    jintArray javaResult = env->NewIntArray(5);
    jint fill[5];
    for (int i = 0; i < 5; i++)
    {
        fill[i]=resultVector[i];
    }
    env->SetIntArrayRegion(javaResult, 0, 5, fill);

    return javaResult;
}