package com.basti.floatingactionbuttonmenu;

import android.content.Context;

/**
 * Created by SHIBW-PC on 2016/1/20.
 */
public class Utils {

    public static float dp2px(Context context,float dp){
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (dp * scale + 0.5f);
    }
}
