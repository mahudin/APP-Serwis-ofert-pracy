package com.example.root.cooklearninggame;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
//import android.widget.SearchView;
//import android.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ListView;
//import android.widget.SearchView;
import android.support.v4.widget.SearchViewCompat;
// widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.cooklearninggame.Adapters.CustomList;
import com.example.root.cooklearninggame.DB.OffersDatabaseHelper;
import com.example.root.cooklearninggame.Objects.Offer;
import com.example.root.cooklearninggame.Scrapper.GumtreeScrapper;

import java.util.ArrayList;
import java.util.List;

import static com.example.root.cooklearninggame.Objects.Serials.serial_names;

public class NumberSerialActivity extends AppCompatActivity {
    public static final String EXTRA_PIZMANO="pizmaNo";
    ListView list;
    SQLiteOpenHelper recipeDatabaseHelper;
    //SQLiteDatabase db;
    Cursor cursor;
    int pizmaNos;
    private ShareActionProvider shareActionProvider;
    private GetterOffersJobTask mAuthTask = null;
    private DropOffersJobTask dAuthTask = null;
    private CustomList csml=null;
    ListView lv;

    private void dropOffers() {
        try {
            dAuthTask = new DropOffersJobTask(this);
            dAuthTask.execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "," + e.getMessage() + ",", Toast.LENGTH_LONG).show();
        }
    }

    private void updateOffers() {
        try {
            mAuthTask = new GetterOffersJobTask(this);
            mAuthTask.execute();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "," + e.getMessage() + ",", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_serial);

        try{
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                csml=OffersDatabaseHelper.getActualOffer("",getApplicationContext(),this);

                if(csml.getCount()==0){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Twój stos ofert jest pusty. Kliknij w ikonkę ze strzałkami na pasku menu, by pobrać najnowsze oferty");
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                list=(ListView)findViewById(R.id.list_recipes);
                list.setAdapter(csml);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                            TextView rt=(TextView) view.findViewById(R.id.id_offer);
                            Intent intent=new Intent(NumberSerialActivity.this,DetailOfferActivity.class);
                            intent.putExtra(Offer.offer_id,rt.getText().toString().trim());
                            startActivity(intent);
                        }
                    });
                lv=list;
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                updateOffers();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Czy jesteś pewien, że chcesz usunąć wszystkie oferty ?");
                dlgAlert.setTitle("Usunięcie wszystkich ofert");
                dlgAlert.setPositiveButton("Tak",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dropOffers();
                    }
                });
                dlgAlert.setNegativeButton("Nie",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_update,menu);

        try {
            SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
            SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        } catch(NullPointerException e){
            Log.e("Błąd",e.getMessage());
        }

        return super.onCreateOptionsMenu(menu);
    }


    public class GetterOffersJobTask extends AsyncTask<String, Integer, ArrayList<Offer>> {

        private ProgressDialog dialog=new ProgressDialog(NumberSerialActivity.this);
        private String Content;
        private String Error = null;
        private String texter = null;
        String data = "";
        private Context context;

        GetterOffersJobTask(Context ct) {
            context=ct;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Proszę czekać... trwa pobieranie ofert pracy!");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Offer> a) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            runOnUiThread(new Runnable(){
                public void run(){
                    csml=OffersDatabaseHelper.getActualOffer("",getApplicationContext(),NumberSerialActivity.this);//adapter;
                    list=(ListView)findViewById(R.id.list_recipes);
                    list.setAdapter(csml);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                            TextView rt=(TextView) view.findViewById(R.id.id_offer);
                            Intent intent=new Intent(NumberSerialActivity.this,DetailOfferActivity.class);
                            intent.putExtra(Offer.offer_id,rt.getText().toString().trim());
                            startActivity(intent);
                        }
                    });
                }
            });
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            dialog.setProgress(progress[0]);
        }

        @Override
        protected ArrayList<Offer> doInBackground(String... c) {
            try {
                GumtreeScrapper jvsc = new GumtreeScrapper(context);
                ArrayList<Offer> offers = jvsc.action_start();
                OffersDatabaseHelper ofdh = new OffersDatabaseHelper(context);
                SQLiteDatabase db = ofdh.getWritableDatabase();
                if(!offers.isEmpty()){
                    for (Offer offer : offers) {
                        ContentValues recipeValues = new ContentValues();
                        recipeValues.put("NAME_OFFER", offer.getName_offer());
                        recipeValues.put("CATEGORY", offer.getCategory());
                        recipeValues.put("DESC", offer.getDesc());
                        recipeValues.put("LOCALISATION", offer.getLocalisation());
                        recipeValues.put("TYPE_OF_WORK", offer.getType_of_job());
                        recipeValues.put("KIND_OF_CONTRACT", offer.getKind_of_contract());
                        recipeValues.put("AUTHOR", offer.getAuthor());
                        recipeValues.put("IMAGE_RESOURCE_ID", offer.getImage_resource_id());
                        recipeValues.put("ADD_DATE", offer.getAdd_date());
                        recipeValues.put("NR_PHONE", offer.getPhone());
                        recipeValues.put("URL_IMAGE", offer.getUrl());
                        db.insert("OFFERS", null, recipeValues);
                    }
                }
            } catch (Exception e) {
                 Toast.makeText(getApplicationContext(), "Błąd: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return new ArrayList<Offer>();
        }
    }

    public class DropOffersJobTask extends AsyncTask<String, Integer, ArrayList<Offer>> {

        private ProgressDialog dialog=new ProgressDialog(NumberSerialActivity.this);
        private String Content;
        private String Error = null;
        private String texter = null;
        String data = "";
        private Context context;

        DropOffersJobTask(Context ct) {
            context=ct;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Proszę czekać... trwa kasowanie ofert!");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Offer> a) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            runOnUiThread(new Runnable(){
                public void run(){
                    csml=OffersDatabaseHelper.getActualOffer("",getApplicationContext(),NumberSerialActivity.this);//adapter;
                    list=(ListView)findViewById(R.id.list_recipes);
                    list.setAdapter(csml);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                            TextView rt=(TextView) view.findViewById(R.id.id_offer);
                            Intent intent=new Intent(NumberSerialActivity.this,DetailOfferActivity.class);
                            intent.putExtra(Offer.offer_id,rt.getText().toString().trim());
                            startActivity(intent);
                        }
                    });
                }
            });
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            dialog.setProgress(progress[0]);
        }

        @Override
        protected ArrayList<Offer> doInBackground(String... c) {
            try {
                OffersDatabaseHelper ofdh = new OffersDatabaseHelper(context);
                SQLiteDatabase db = ofdh.getWritableDatabase();
                db.execSQL("delete from OFFERS; VACUUM");
                db.close();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Błąd: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return new ArrayList<Offer>();
        }
    }
}