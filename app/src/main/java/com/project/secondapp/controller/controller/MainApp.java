package com.project.secondapp.controller.controller;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.project.secondapp.ContactsFragment;
import com.project.secondapp.EndTravelFragment;

import com.project.secondapp.R;
import com.project.secondapp.TravelFragment;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.service.MyService;
import com.project.secondapp.dummy.DummyContent;

public class MainApp extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static ComponentName service = null;
    DummyContent content;
    DummyContent endTravel;
    DummyContent contactList;
    FragmentManager fragmentManager = getFragmentManager();
    final Backend backend = BackendFactory.getBackend();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (service == null) {
            Intent intent = new Intent(getBaseContext(), MyService.class);
            service = startService(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        content = new DummyContent(backend.getAllDrive());
        endTravel = new DummyContent(backend.getAllFinishDrive());
        contactList = new DummyContent(backend.getAllContacts());
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, TravelFragment.newInstance(1, content))
                .commit();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.choose_radius) {
            //seek_bar.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_travels) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, TravelFragment.newInstance(1, content))
                    .commit();
        } else if (id == R.id.nav_my_travels) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, EndTravelFragment.newInstance(1, endTravel))
                    .commit();
        } else if (id == R.id.nav_my_clients) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, ContactsFragment.newInstance(1, contactList))
                    .commit();

        } else if (id == R.id.nav_exit) {
            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
