package com.example.architpanwar.lockup.Services;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.architpanwar.lockup.Constants;
import com.example.architpanwar.lockup.MainActivity;
import com.example.architpanwar.lockup.R;
import com.example.architpanwar.lockup.passwordpage;
import com.example.architpanwar.lockup.utility.Sharedprefence;

import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

/**
 * Created by architpanwar on 20/01/18.
 */

public class check extends Service {
    @Nullable






    public static final String TAG = "AppCheckServices";
    private Context context = null;
    private Timer timer;
    ImageView imageView;
    private WindowManager windowManager;
    private Dialog dialog;
    public static String currentApp = "";
    public static String previousApp = "";
    Sharedprefence sharedpreference;
    SharedPreferences.Editor edit;

    PatternLockView mpattern;
    List<String> pakageName;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Log.d("Servicessss","uhvjjbjjjuvchccchchcvhvhvhgvhchcccchvhvvvjhbb b  hvhvhchb jjvjvjjvjvujyhvjvjvvhvhvhgvgvhvjhmjjjjvjvvvvvh");
        sharedpreference = new Sharedprefence();
        if (sharedpreference != null) {
            pakageName = sharedpreference.getLocked(context);
        }
        timer = new Timer("AppCheckServices");
        timer.schedule(updateTask, 1000L, 1000L);

        Log.d("Servicessss","uhvjjbjjjuvchccchchcvhvhvhgvhchcccchvhvvvjhbb b  hvhvhchb jjvjvjjvjvujyhvjvjvvhvhvhgvgvhvjhmjjjjvjvvvvvh");
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        imageView = new ImageView(this);
        imageView.setVisibility(View.GONE);



        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.x = ((getApplicationContext().getResources().getDisplayMetrics().widthPixels) / 2);
        params.y = ((getApplicationContext().getResources().getDisplayMetrics().heightPixels) / 2);
        windowManager.addView(imageView, params);

    }

    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            if (sharedpreference != null) {
                pakageName = sharedpreference.getLocked(context);
            }
            if (isConcernedAppIsInForeground()) {
                if (imageView != null) {
                    imageView.post(new Runnable() {
                        public void run() {
                            if (!currentApp.matches(previousApp)) {
                                showUnlockDialog();
                                previousApp = currentApp;
                            }
                        }
                    });
                }
            } else {
                if (imageView != null) {
                    imageView.post(new Runnable() {
                        public void run() {

                            hideUnlockDialog();
                        }
                    });
                }
            }
        }
    };

    void showUnlockDialog() {
        showDialog();
    }

    void hideUnlockDialog() {
        previousApp = "";
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showDialog() {
        if (context == null)
            context = getApplicationContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptsView = layoutInflater.inflate(R.layout.outlock, null);
    mpattern=(PatternLockView)promptsView.findViewById(R.id.pattern_lock_view3);
        final String pass;
        pass=sharedpreference.getPassword(context);
        mpattern.setInStealthMode(true);


        mpattern.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                if( PatternLockUtils.patternToString(mpattern, pattern).matches(pass))
                {
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Try Again wrong password",Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCleared() {

            }
        });





        dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(promptsView);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == KeyEvent.ACTION_UP) {
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                }
                return true;
            }
        });

        Log.d("ho raha hai kya show","good one boy");
        dialog.show();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {

        }
        /* We want this service to continue running until it is explicitly
        * stopped, so return sticky.
        */
        return START_STICKY;
    }

    public boolean isConcernedAppIsInForeground() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task = manager.getRunningTasks(5);
        if (Build.VERSION.SDK_INT <= 20) {
            if (task.size() > 0) {
                ComponentName componentInfo = task.get(0).topActivity;
                for (int i = 0; pakageName != null && i < pakageName.size(); i++) {
                    if (componentInfo.getPackageName().equals(pakageName.get(i))) {
                        currentApp = pakageName.get(i);
                        return true;
                    }
                }
            }
        }
        else {
            String mpackageName = manager.getRunningAppProcesses().get(0).processName;
            UsageStatsManager usage = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> stats = usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, 0, time);
            if (stats != null) {
                SortedMap<Long, UsageStats> runningTask = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : stats) {
                    runningTask.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (runningTask.isEmpty()) {
                    Log.d(TAG,"isEmpty Yes");
                    mpackageName = "";
                }else {
                    mpackageName = runningTask.get(runningTask.lastKey()).getPackageName();
                    Log.d(TAG,"isEmpty No : "+mpackageName);
                }
            }


            for (int i = 0; pakageName != null && i < pakageName.size(); i++) {
                if (mpackageName.equals(pakageName.get(i))) {
                    currentApp = pakageName.get(i);
                    Log.d("app running",currentApp);
                    return true;
                }
            }
        }
        return false;
    }


    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
        if (imageView != null) {
            windowManager.removeView(imageView);
        }
        /**** added to fix the bug of view not attached to window manager ****/
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
