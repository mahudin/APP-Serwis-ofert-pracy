package com.example.root.cooklearninggame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.root.cooklearninggame.DB.OffersDatabaseHelper;
import com.example.root.cooklearninggame.Fragments.OneFragment;
import com.example.root.cooklearninggame.Fragments.TwoFragment;
import com.example.root.cooklearninggame.Objects.Offer;


import java.util.ArrayList;
import java.util.List;

public class DetailOfferActivity extends AppCompatActivity {

    SQLiteOpenHelper offersDatabaseHelper;
    SQLiteDatabase db;
    Bundle data;
    Cursor cursor;
    int offerId;
    private ShareActionProvider shareActionProvider;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String nr_phone;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            case R.id.action_share:
                if(nr_phone!=null && !nr_phone.equals("")){
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String special_message=prefs.getString("default_sms_message","DEFAULT");
                    Intent intent2=new Intent(Intent.ACTION_SEND);
                    intent2.setType("text/plain");
                    intent2.putExtra("address", nr_phone);
                    intent2.putExtra(Intent.EXTRA_TEXT,special_message);
                    startActivity(intent2);
                } else {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Do tej oferty nie był dołączony numer telefonu!");
                    dlgAlert.setTitle("Brak numeru telefonu");
                    dlgAlert.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        try{
            String offerId2=(String) getIntent().getExtras().get(Offer.offer_id);//-1
            offersDatabaseHelper = new OffersDatabaseHelper(this);
            db = offersDatabaseHelper.getReadableDatabase();
            cursor=db.query("OFFERS",
                    new String[] {"_id","NAME_OFFER","IMAGE_RESOURCE_ID","LOCALISATION","DESC",
                            "KIND_OF_CONTRACT","TYPE_OF_WORK","AUTHOR","ADD_DATE","NR_PHONE","URL_IMAGE"},
                    " _id ='"+offerId2+"'",null,null,null,null
            );
            cursor.moveToNext();

            String name_offer=cursor.getString(1);
            setTitle(name_offer);

            String localisation=cursor.getString(3);
            String desc=cursor.getString(4);
            String kind_of_contract=cursor.getString(5);
            String type_of_work=cursor.getString(6);

            String author=cursor.getString(7);
            String add_date=cursor.getString(8);

            this.nr_phone=cursor.getString(9);
            String url_image=cursor.getString(10);
            Bundle data_bundle=new Bundle();
            data_bundle.putString("key_value", "String to pass");

            ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

            setupViewPager(viewPager,data_bundle,desc,localisation,desc,kind_of_contract,type_of_work,author,add_date,url_image);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(viewPager);
            cursor.close();
            db.close();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupViewPager(ViewPager viewPager,Bundle data,String desc_recipe,String localisation,String desc,String kind_of_contract,String type_of_work,String author,String add_date,String url_image) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        OneFragment overviewFrag = OneFragment.newInstance();
        overviewFrag.setDesc_recipe(desc_recipe);
        overviewFrag.setImg_offer(url_image);
        adapter.addFrag(overviewFrag, "Opis pracy");

        TwoFragment overviewFrag2 = TwoFragment.newInstance();
        overviewFrag2.setKind_of_contract(kind_of_contract);
        overviewFrag2.setType_of_work(type_of_work);
        overviewFrag2.setAuthor(author);
        overviewFrag2.setAdd_date(add_date);
        overviewFrag2.setLocalisation(localisation);
        adapter.addFrag(overviewFrag2, "Parametry");

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(!cursor.isClosed()) cursor.close();
        if(db.isOpen()) db.close();
    }
}
