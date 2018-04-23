package com.example.architpanwar.lockup.Fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.architpanwar.lockup.Constants;
import com.example.architpanwar.lockup.R;
import com.example.architpanwar.lockup.password;
import com.example.architpanwar.lockup.passwordrecoverset;

import java.util.List;

/**
 * Created by architpanwar on 22/04/18.
 */

public class passwordreset extends Fragment {

    PatternLockView mpattern;
    //PatternLockViewListener mpattl;
    TextView ttv;
    Button confirm;
    Button retry;
    boolean first;
    boolean second;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor edit;

    public static passwordreset newInstance() {
        passwordreset f = new passwordreset();
        return (f);
    }

    public passwordreset() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){


        View v = inflater.inflate(R.layout.activity_password, container, false);

        ttv=(TextView)v.findViewById(R.id.abcd) ;
        final String[] pass = new String[1];



        sharedPreference=getActivity().getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        edit=sharedPreference.edit();

        String cond=sharedPreference.getString(Constants.PASSWORD,"ad");
       // Toast.makeText(getApplicationContext(),"new check "+cond,Toast.LENGTH_SHORT).show();

        mpattern=(PatternLockView)v.findViewById(R.id.pattern_lock_view);

        first=false;
        second=false;

        confirm=(Button)v.findViewById(R.id.confirm);
        retry=(Button)v.findViewById(R.id.retry);
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
                    pass[0] = PatternLockUtils.patternToString(mpattern, pattern);
                    first=true;
                    retry.setEnabled(true);
                }
                else if(PatternLockUtils.patternToString(mpattern, pattern).matches(pass[0])&&second==false)
                {
                    second=true;
                    Toast.makeText(getActivity(),"Password matches",Toast.LENGTH_SHORT).show();
                    confirm.setEnabled(true);

                }
                else
                {
                    Toast.makeText(getActivity(),"Wrong password try again",Toast.LENGTH_SHORT).show();
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

                Intent i=new Intent(getActivity(),passwordrecoverset.class);
                getActivity().startActivity(i);
                getActivity().finish();

            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first=false;
                second=false;

            }
        });








        return v;
    }
}
