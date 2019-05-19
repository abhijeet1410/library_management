package com.smarttersstudio.libraryapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.fragments.AboutUsFragment;
import com.smarttersstudio.libraryapp.fragments.DirectoryFragment;
import com.smarttersstudio.libraryapp.fragments.HomeFragment;
import com.smarttersstudio.libraryapp.fragments.MyHistoryFragment;
import com.smarttersstudio.libraryapp.fragments.NotifcationFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment = null;
    private String tag = "others";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        initFragmentTransaction();

        navigationView.setCheckedItem(R.id.nav_home);

        mAuth = FirebaseAuth.getInstance();
    }

    private void initFragmentTransaction() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,new HomeFragment(),"home");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().findFragmentById(R.id.container).getTag().equals("home")){
                super.onBackPressed();
            }else{
                fragment = new HomeFragment();
                tag = "home";
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container,fragment,tag);
                ft.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            AlertDialog.Builder ad = new AlertDialog.Builder(this);
            ad.setMessage(getResources().getString(R.string.log_out_dialog_msg));
            ad.setCancelable(false);
            ad.setPositiveButton(getResources().getString(R.string.yes_msg), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAuth.signOut();
                    startActivity(new Intent(HomeActivity.this,SignInActivity.class));
                    finish();
                }
            }).setNegativeButton(getResources().getString(R.string.no_msg), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            ad.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        String tag="other";

        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            tag = "home";
        } else if (id == R.id.nav_directory) {
            fragment = new DirectoryFragment();
            tag = "directory";
        } else if (id == R.id.nav_notification) {
            fragment = new NotifcationFragment();
            tag = "notification";
        } else if (id == R.id.nav_about_us) {
            fragment = new AboutUsFragment();
            tag = "about us";
        } else if (id == R.id.nav_history){
            fragment = new MyHistoryFragment();
            tag = "my history";
        }
        ft.replace(R.id.fragment_container, fragment, tag);
        ft.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
