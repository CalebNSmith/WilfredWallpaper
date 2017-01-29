package com.caleb.wallpaper;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Caleb on 4/23/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int day = calendar.get(Calendar.DAY_OF_YEAR);

        int[] resources = new int[500];
        for(int i = 0; i < resources.length; i++) {
            try {
                resources[i] = context.getResources().getIdentifier("image" + i, "raw", context.getPackageName());
            } catch(Exception e) {
                break;
            }
        }

        int resourcesLength = 0;
        while(resources[resourcesLength] != 0)
            resourcesLength++;

        int[] totalResources = new int[resourcesLength];
        for(int i = 0; i < totalResources.length; i++) {
            totalResources[i] = resources[i];
        }

        int picNum = day % totalResources.length;
        try {
            //wallpaperManager.setResource(totalResources[picNum]);
            wallpaperManager.setResource(totalResources[new Random().nextInt(totalResources.length)]);

        } catch(Exception e) {}
    }

}
