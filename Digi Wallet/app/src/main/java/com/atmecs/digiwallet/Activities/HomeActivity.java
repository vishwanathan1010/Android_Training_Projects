package com.atmecs.digiwallet.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.atmecs.digiwallet.BuildConfig;
import com.atmecs.digiwallet.Fragments.HomeFragment;
import com.atmecs.digiwallet.R;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public Toolbar toolbar;
    private Fragment fragment;
    String versionName = BuildConfig.VERSION_NAME;
    Menu menu;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        try {

            TextView version = findViewById(R.id.nav_version);
            version.setText("Version:" + versionName);
        }catch (Exception e){
            e.printStackTrace();
        }


        int positionOfMenuItem = 7;

        NavigationView navigationViewVersion =  findViewById(R.id.navigation_view);
        if (navigationViewVersion != null) {
            menu = navigationViewVersion.getMenu();
           // menu.findItem(R.id.nav_version).setTitle("Version: "+versionName);
            item = menu.getItem(positionOfMenuItem);
            SpannableString st = new SpannableString(item.getTitle());
            st.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, st.length(), 0);
            //st.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, st.length(), 0);

            item.setTitle(st);
           // menu.findItem(R.id.nav_version).setTitle("Version: "+versionName);
            //menu.findItem(R.id.nav_pkg_manage).setVisible(false);//In case you want to remove menu item
            //navigationView.setNavigationItemSelectedListener(this);
        }


        toolbar = findViewById(R.id.toolbar_home_activity);
        toolbar.setTitle(R.string.home_activity_title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.whiteColor));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                closeKeyBoard();
            }
        };
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.whiteColor));
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        loadHomeFragment();
    }

    protected void closeKeyBoard() throws NullPointerException {

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();

            int hideType = InputMethodManager.HIDE_NOT_ALWAYS;

            inputManager.hideSoftInputFromWindow(windowToken, hideType);
        }
    }

    public void loadHomeFragment() {
        fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_content_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)
            getQuitApplication();
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getQuitApplication() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        // set title
        alertDialogBuilder.setTitle("Exit");
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to exit?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        finish();
                    }
                });

        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"You clicked on Cancel",Toast.LENGTH_SHORT).show();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_my_logout:
                logOut();
                return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        getLogout();
    }

    public void getLogout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        // set title
        alertDialogBuilder.setTitle("Logout");
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to logout?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Toast.makeText(HomeActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
