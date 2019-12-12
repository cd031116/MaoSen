package gp.ms.com.utils;

import android.util.Log;

import gp.ms.com.BuildConfig;


public   class LogUtil {
    public static boolean debug = BuildConfig.LOG_DEBUG;


    public static void d(String msg) {
        if (debug){
            Log.d("huierles", msg);
        }

     }
    public static void i(String msg) {
        if (debug){
            Log.i("huierles", msg);
        }
    }

    public static void e(String msg) {
        if (debug){
            Log.e("huierles", msg);
        }
    }
}
