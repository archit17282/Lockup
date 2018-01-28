package com.example.architpanwar.lockup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.architpanwar.lockup.Services.alarm;
import com.example.architpanwar.lockup.Services.check;

public class pathactivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathactivity);





        context = getApplicationContext();

        /****************************** too much important don't miss it *****************************/
        startService(new Intent(pathactivity.this, check.class));
       Log.d("Service hui kya","uhvjjbjjjuvchccchchcvhvhvhgvhchcccchvhvvvjhbb b  hvhvhchb jjvjvjjvjvujyhvjvjvvhvhvhgvgvhvjhmjjjjvjvvvvvh");
        try {
            Intent alarmIntent = new Intent(context, alarm.class);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 999, alarmIntent, 0);
            int interval = (86400 * 1000) / 4;
            if (manager != null) {
                manager.cancel(pendingIntent);
            }
            manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***************************************************************************************/










     sharedPreferences=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);

        edit=sharedPreferences.edit();

        boolean cond=sharedPreferences.getBoolean(Constants.IS_PASSWORD_SET,false);


        //Toast.makeText(getApplicationContext(),"Wrong password try again "+cond,Toast.LENGTH_SHORT).show();
        if(cond==true)
        {
            Intent i=new Intent(pathactivity.this,passwordpage.class);
           startActivity(i);
        }
        else
        {
           Intent i=new Intent(pathactivity.this,password.class);
          startActivity(i);
        }

        finish();





    }
}
