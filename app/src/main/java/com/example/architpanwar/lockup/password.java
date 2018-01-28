package com.example.architpanwar.lockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class password extends AppCompatActivity {



    PatternLockView mpattern;
    //PatternLockViewListener mpattl;
    TextView ttv;
    Button confirm;
    Button retry;
    boolean first;
    boolean second;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor edit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ttv=(TextView)findViewById(R.id.abcd) ;
        final String[] pass = new String[1];



        sharedPreference=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);
        edit=sharedPreference.edit();

        String cond=sharedPreference.getString(Constants.PASSWORD,"ad");
        Toast.makeText(getApplicationContext(),"new check "+cond,Toast.LENGTH_SHORT).show();

    mpattern=(PatternLockView)findViewById(R.id.pattern_lock_view);

        first=false;
        second=false;

        confirm=(Button)findViewById(R.id.confirm);
        retry=(Button)findViewById(R.id.retry);
        confirm.setEnabled(false);
        retry.setEnabled(false);
        mpattern.setInStealthMode(true);



        mpattern.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Log.d(getClass().getName(), "Pattern drawing started");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {


    String sec;
                if(first==false)
                {
                    pass[0] =PatternLockUtils.patternToString(mpattern, pattern);
                    first=true;
                    retry.setEnabled(true);
                }
                else if(PatternLockUtils.patternToString(mpattern, pattern).matches(pass[0])&&second==false)
                {
                    second=true;
                    Toast.makeText(getApplicationContext(),"Password matches",Toast.LENGTH_SHORT).show();
                    confirm.setEnabled(true);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wrong password try again",Toast.LENGTH_SHORT).show();
                    first=false;
                    second=false;
                    retry.setEnabled(false);
                }

            }



            @Override
            public void onCleared() {
                Log.d(getClass().getName(), "Pattern has been cleared");
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


              edit.putString(Constants.PASSWORD, pass[0]);
                edit.putBoolean(Constants.IS_PASSWORD_SET,true);
               edit.commit();


                //Toast.makeText(getApplicationContext(),"new check "+abc,Toast.LENGTH_SHORT).show();

                Intent i=new Intent(password.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first=false;
                second=false;

            }
        });






    }



}
