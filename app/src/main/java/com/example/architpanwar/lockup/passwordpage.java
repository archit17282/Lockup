package com.example.architpanwar.lockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class passwordpage extends AppCompatActivity {


    PatternLockView mpattern;
    //PatternLockViewListener mpattl;
    TextView ttv;
   Button forgot;
    boolean first;
    boolean second;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor edit;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordpage);




        sharedPreference=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);
        edit=sharedPreference.edit();

       pass=sharedPreference.getString(Constants.PASSWORD,"");
        mpattern=(PatternLockView)findViewById(R.id.pattern_lock_view2);
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
                    Intent i=new Intent(passwordpage.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Try Again wrong password",Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onCleared() {

            }
        });





    }
}
