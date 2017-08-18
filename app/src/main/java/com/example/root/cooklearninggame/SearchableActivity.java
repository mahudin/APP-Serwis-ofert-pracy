package com.example.root.cooklearninggame;

import android.app.Activity;
import android.app.ListActivity;
import android.app.SearchManager;

//import android.widget.SearchView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.cooklearninggame.Adapters.CustomList;
import com.example.root.cooklearninggame.DB.OffersDatabaseHelper;
import com.example.root.cooklearninggame.Objects.Offer;

import static java.security.AccessController.getContext;

public class SearchableActivity extends AppCompatActivity {
    ListView list;
    private CustomList csml;
    ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            showResults(query);
        }
    }

    private void showResults(String query) {
        Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
        csml=OffersDatabaseHelper.getActualOffer(query,getApplicationContext(),this);
        list=(ListView)findViewById(R.id.list_recipes);
        list.setAdapter(csml);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                TextView rt=(TextView) view.findViewById(R.id.id_offer);
                Intent intent=new Intent(SearchableActivity.this,DetailOfferActivity.class);
                intent.putExtra(Offer.offer_id,rt.getText().toString().trim());
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search,menu);

        try {
            SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        } catch(NullPointerException e){
            Log.e("Błąd",e.getMessage());
        }
        return super.onCreateOptionsMenu(menu);
    }
}