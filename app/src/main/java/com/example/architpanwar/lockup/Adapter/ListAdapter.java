package com.example.architpanwar.lockup.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.example.architpanwar.lockup.Constants;
import com.example.architpanwar.lockup.R;
import com.example.architpanwar.lockup.data.appi;
import com.example.architpanwar.lockup.utility.Sharedprefence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by architpanwar on 17/01/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.listholder> {


    ArrayList<appi> installedApps = new ArrayList();
    private Context context;
    Sharedprefence sharedPreference;
    String requiredAppsType;


    public ListAdapter(ArrayList<appi>inslist,Context context,String required){

        requiredAppsType=required;
        installedApps=inslist;
        this.context=context;
        sharedPreference=new Sharedprefence();
        ArrayList<appi>Locked=new ArrayList<>();
        ArrayList<appi>UnLocked=new ArrayList<>();

        if(requiredAppsType.matches(Constants.LOCKED)||requiredAppsType.matches(Constants.UNLOCKED))
        {
            boolean flag=true;
            for(int i=0;i<installedApps.size();i++)
            {
                flag=true;
                if(sharedPreference.getLocked(context)!=null)
                {for(int j=0;j<sharedPreference.getLocked(context).size();j++)
                    {
                        if(installedApps.get(i).getPackageName().matches(sharedPreference.getLocked(context).get(j)))
                        {
                            Locked.add(installedApps.get(i));
                            flag=false;
                        }

                    }
                    if(flag)
                    {
                        UnLocked.add(installedApps.get(i));
                    }
                }
                if(flag)
                {
                    UnLocked.add(installedApps.get(i));
                }
            }


            if (requiredAppsType.matches(Constants.LOCKED)) {
                installedApps.clear();
                installedApps.addAll(Locked);
            } else if (requiredAppsType.matches(Constants.UNLOCKED)) {
                installedApps.clear();
                installedApps.addAll(UnLocked);
            }

        }


    }


    @Override
    public listholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist, parent, false);

        return new listholder(v);
    }

    @Override
    public void onBindViewHolder(final listholder holder, int position) {



        final appi appInfo = installedApps.get(position);
        holder.applicationName.setText(appInfo.getName());
        holder.icon.setBackgroundDrawable(appInfo.getIcon());

        holder.switchView.setOnCheckedChangeListener(null);
        holder.cardView.setOnClickListener(null);
        if (checkLockedItem(appInfo.getPackageName())) {
            holder.switchView.setChecked(true);
        } else {
            holder.switchView.setChecked(false);
        }

        holder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Lock Clicked", "lock_clicked", appInfo.getPackageName());
                    sharedPreference.addLocked(context, appInfo.getPackageName());
                } else {
                    //AppLockLogEvents.logEvents(AppLockConstants.MAIN_SCREEN, "Unlock Clicked", "unlock_clicked", appInfo.getPackageName());
                    sharedPreference.removeLocked(context, appInfo.getPackageName());
                }
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.switchView.performClick();
            }
        });

    }


    public boolean checkLockedItem(String checkApp) {
        boolean check = false;
        List<String> locked = sharedPreference.getLocked(context);
        if (locked != null) {
            for (String lock : locked) {
                if (lock.equals(checkApp)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public int getItemCount() {
        return installedApps.size();
    }

    static class listholder extends RecyclerView.ViewHolder{

        public TextView applicationName;
        public CardView cardView;
        public ImageView icon;
        public Switch switchView;

        public listholder(View itemView) {

            super(itemView);
            applicationName = (TextView) itemView.findViewById(R.id.applicationName);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            switchView = (Switch) itemView.findViewById(R.id.switchView);




        }
    }


}
