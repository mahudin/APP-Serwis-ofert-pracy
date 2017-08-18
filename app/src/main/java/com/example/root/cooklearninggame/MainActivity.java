package com.example.root.cooklearninggame;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.cooklearninggame.Adapters.CustomList;
import com.example.root.cooklearninggame.Adapters.ListWrapper;
import com.example.root.cooklearninggame.DB.OffersDatabaseHelper;
import com.example.root.cooklearninggame.Fragments.BestTimeFragment;
import com.example.root.cooklearninggame.Fragments.ConfigurationFragment;
import com.example.root.cooklearninggame.Fragments.CreditFragment;
import com.example.root.cooklearninggame.Fragments.SerialFragment;
import com.example.root.cooklearninggame.Fragments.TopFragment;
import com.example.root.cooklearninggame.Objects.Offer;
import com.example.root.cooklearninggame.Scrapper.GumtreeScrapper;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ShareActionProvider shareActionProvider;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    private String[] titles;
    private ListView drawerList;

    private int currentPosition = 0;
    SQLiteOpenHelper recipeDatabaseHelper;
    SQLiteDatabase db;
    Bundle data;
    Cursor cursor;
    int recipeId;

    private Button button;
    private EditText time;
    private TextView finalResult;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = titles[0];
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }

    private void selectItem(int position) {
        currentPosition = position;
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new ConfigurationFragment();
                break;
            /*case 2:
                fragment = new BestTimeFragment();
                break;*/
            case 2:
                fragment = new CreditFragment();
                break;
            default:
                fragment = new SerialFragment();
                break;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Integer[] images=new Integer[]{R.drawable.notebook,R.drawable.wrench,R.drawable.briefcase,R.drawable.cloud};

        ListWrapper adapter = new ListWrapper(MainActivity.this, titles, images);

        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                        }
                        if (fragment instanceof ConfigurationFragment) {
                            currentPosition = 1;
                        }
                        /*if (fragment instanceof BestTimeFragment) {
                            currentPosition = 2;
                        }*/
                        /*if (fragment instanceof CreditFragment) {
                            currentPosition = 3;
                        }*/
                        setActionBarTitle(currentPosition);
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }
}