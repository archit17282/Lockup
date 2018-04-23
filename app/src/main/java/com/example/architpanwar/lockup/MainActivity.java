package com.example.architpanwar.lockup;




import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.architpanwar.lockup.Fragment.Allappfrag;
import com.example.architpanwar.lockup.Fragment.passwordreset;
import com.example.architpanwar.lockup.data.appi;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Context context;
    FragmentManager frago;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("All Applications");

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            // Show alert dialog to the user saying a separate permission is needed
//            // Launch the settings activity if the user prefers
//            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//            startActivity(myIntent);
//        }


//        if (!Settings.canDrawOverlays(this)) {
//            Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//            myIntent.setData(Uri.parse("package: " + getPackageName()));
//            startActivityForResult(myIntent, 101);
//        }

        frago=getSupportFragmentManager();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Allappfrag f = Allappfrag.newinstanceapp(Constants.ALL_APPS);
        frago.beginTransaction().replace(R.id.fragment_container, f).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static List<appi> getListOfInstalledApp(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ArrayList<appi> installedApps = new ArrayList();
        ArrayList<PackageInfo> apps = (ArrayList<PackageInfo>) packageManager.getInstalledPackages(PackageManager.SIGNATURE_MATCH);
        if (apps != null && !apps.isEmpty()) {

            for (int i = 0; i < apps.size(); i++) {
                PackageInfo p = apps.get(i);
                ApplicationInfo appInfo = null;
                try {
                    appInfo = packageManager.getApplicationInfo(p.packageName, 0);
                    appi app = new appi();
                    app.setName(p.applicationInfo.loadLabel(packageManager).toString());
                    app.setPackageName(p.packageName);
                    app.setVersionName(p.versionName);
                    app.setVersionCode(p.versionCode);
                    app.setIcon(p.applicationInfo.loadIcon(packageManager));

                    //check if the application is not an application system
//                    Intent launchIntent = app.getLaunchIntent(context);
//                    if (launchIntent != null && (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
                    installedApps.add(app);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            //sort the list of applications alphabetically
//            if (installedApps.size() > 0) {
//                Collections.sort(installedApps, new Comparator() {
//
//                    @Override
//                    public int compare(final AppInfo app1, final AppInfo app2) {
//                        return app1.getName().toLowerCase(Locale.getDefault()).compareTo(app2.getName().toLowerCase(Locale.getDefault()));
//                    }
//                });
//            }
            return installedApps;
        }
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            getSupportActionBar().setTitle("All Applications");
            Allappfrag f = Allappfrag.newinstanceapp(Constants.ALL_APPS);
            frago.beginTransaction().replace(R.id.fragment_container, f).commit();

        } else if (id == R.id.nav_gallery) {

            getSupportActionBar().setTitle("Locked Applications");
            Allappfrag f = Allappfrag.newinstanceapp(Constants.LOCKED);
            frago.beginTransaction().replace(R.id.fragment_container, f).commit();


        } else if (id == R.id.nav_slideshow) {
            getSupportActionBar().setTitle("Unlocked Applications");
            Allappfrag f = Allappfrag.newinstanceapp(Constants.UNLOCKED);
            frago.beginTransaction().replace(R.id.fragment_container, f).commit();


        } else if (id == R.id.nav_manage) {

            getSupportActionBar().setTitle("Change Password");
            passwordreset f = passwordreset.newInstance();
            frago.beginTransaction().replace(R.id.fragment_container, f).commit();


        }else if(id==R.id.auth){
            final Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "If you have not allowed , allow App Lock so that it can work properly", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
