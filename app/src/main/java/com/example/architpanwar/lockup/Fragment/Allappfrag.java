package com.example.architpanwar.lockup.Fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.architpanwar.lockup.Adapter.ListAdapter;
import com.example.architpanwar.lockup.Constants;
import com.example.architpanwar.lockup.MainActivity;
import com.example.architpanwar.lockup.R;
import com.example.architpanwar.lockup.data.appi;

import java.util.ArrayList;

/**
 * Created by architpanwar on 17/01/18.
 */

public class Allappfrag extends Fragment {

    private static String Reqiure;
    private RecyclerView mrecycle;
    private RecyclerView.LayoutManager mLayoutManager;

     ArrayList<appi>abc;
    appi app;

    public static Allappfrag newinstanceapp(String required)
    {
        Reqiure=required;

        Allappfrag a=new Allappfrag();
        return a;
    }



    public Allappfrag()
    {
   super();


    }








    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment,container,false);

//        abc=new ArrayList<>();
//        app = new appi();
//        app.setName("abc");
//        app.setPackageName("sss");
//        app.setVersionName("lkkk");
//        app.setVersionCode(123);
//        Drawable myImage = getResources().getDrawable(R.drawable.small);
//        app.setIcon(myImage);
//abc.add(app);

        //Log.d("WRITTEN THINGS",abc.get(0).getName());

        mrecycle=(RecyclerView)v.findViewById(R.id.frag);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mrecycle.setLayoutManager(mLayoutManager);
        ListAdapter adap=new ListAdapter((ArrayList<appi>) ((MainActivity) getActivity()).getListOfInstalledApp(getActivity()), getActivity(), Reqiure);
       // ListAdapter adap=new ListAdapter(abc,getActivity(), Constants.ALL_APPS);
        mrecycle.setAdapter(adap);

        return v;
    }
}
