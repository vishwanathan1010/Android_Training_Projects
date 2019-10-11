package com.atmecs.digiwallet.Utilities;

import android.content.Context;

public class Utils {

    /**
     *
     * @param id
     * @param context to get string value as a part of context.
     * @return
     */
    public static String getStringValue(int id, Context context){
        String value = context.getResources().getString(id);
        return value;
    }
}
