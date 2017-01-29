package com.caleb.wallpaper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);


        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 5);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis() + 1000*15, 1000*15, alarmIntent);

        button = (Button) findViewById(R.id.pictureButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int day = calendar.get(Calendar.DAY_OF_YEAR);

                int[] resources = new int[500];
                for(int i = 0; i < resources.length; i++) {
                    try {
                        resources[i] = getApplicationContext().getResources().getIdentifier("image" + i, "raw", getApplicationContext().getPackageName());
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
                    wallpaperManager.setResource(totalResources[new Random().nextInt(totalResources.length)]);
                } catch(Exception e) {}
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
