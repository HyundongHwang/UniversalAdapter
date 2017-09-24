package com.hhd2002.universaladaptertest;

import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MyUtils {
    public static Gson createGson() {
        //noinspection UnnecessaryLocalVariable
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }

    public static void writeLog(String name, Object value) {
        StackTraceElement[] stList = Thread.currentThread().getStackTrace();
        String tag = String.format("%s:%d", stList[3].getFileName(), stList[3].getLineNumber());
        String json = MyUtils.createGson().toJson(value);
        String msg = String.format("%s : %s", name, json);
        Log.i(tag, msg);
    }

    public static void writeLog(String msg) {
        StackTraceElement[] stList = Thread.currentThread().getStackTrace();
        String tag = String.format("%s:%d", stList[3].getFileName(), stList[3].getLineNumber());
        Log.i(tag, msg);
    }

    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        return width;
    }
}
