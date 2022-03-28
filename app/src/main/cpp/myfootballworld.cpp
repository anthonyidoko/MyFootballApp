// Write C++ code here.
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_myfootballworld_data_repository_CompetitionsRepoImp_getApiKey(JNIEnv *env, jobject object) {
    std::string key = "af127ec2af54424980bf938668f08463";
    return env->NewStringUTF(key.c_str());
}

// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("weatherapp");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("weatherapp")
//      }
//    }