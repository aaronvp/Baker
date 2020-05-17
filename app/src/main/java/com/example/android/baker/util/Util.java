package com.example.android.baker.util;

import android.content.Context;
import android.util.DisplayMetrics;

public class Util {

    private static DisplayMetrics displayMetrics;

    public static float getScaledWidth(Context context) {
        displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.widthPixels / displayMetrics.scaledDensity);
    }

}


