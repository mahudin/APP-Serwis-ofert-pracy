package com.example.root.cooklearninggame.DB;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.root.cooklearninggame.Adapters.CustomList;
import com.example.root.cooklearninggame.R;

/**
 * Created by root on 10.07.2017.
 */

public class OffersDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="offers";
    private static final int DB_VERSION=1;

    public OffersDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        updateMyDatabase(db,oldVersion,DB_VERSION);
    }

    /*@Override
    public void onDowngrade(SQLiteDatabase db,int oldVersion,int newVersion){
        if(oldVersion<3){

        }
    }*/

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion < 1){
            db.execSQL("CREATE TABLE if not exists OFFERS (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"NAME_OFFER TEXT not null unique,"
                    +"CATEGORY TEXT,"
                    +"DESC TEXT,"
                    +"LOCALISATION TEXT,"
                    +"TYPE_OF_WORK TEXT,"
                    +"KIND_OF_CONTRACT TEXT,"
                    +"AUTHOR TEXT,"
                    +"IMAGE_RESOURCE_ID INTEGER,"
                    +"ADD_DATE TEXT,"
                    +"NR_PHONE TEXT,"
                    +"URL_IMAGE TEXT)");

        }
        /*if(oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
        if(oldVersion < 3){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN RATING INTEGER;");
        }*/
    }

    public static void insertOffer(SQLiteDatabase db, String offer_name, int id_category,String desc, String localisation,
                                     String type_of_work,String kind_of_contract,String author,int image_resource_id,String date,String phone,String url){
        ContentValues offerValues=new ContentValues();
        offerValues.put("NAME_OFFER",offer_name);
        offerValues.put("CATEGORY",id_category);
        offerValues.put("DESC",desc);
        offerValues.put("LOCALISATION",localisation);
        offerValues.put("TYPE_OF_WORK",type_of_work);
        offerValues.put("KIND_OF_CONTRACT",kind_of_contract);
        offerValues.put("AUTHOR",author);
        offerValues.put("IMAGE_RESOURCE_ID",image_resource_id);
        offerValues.put("ADD_DATE",date);
        offerValues.put("NR_PHONE",phone);
        offerValues.put("URL_IMAGE",url);
        db.insert("OFFERS",null,offerValues);
    }

    public static CustomList getActualOffer(String query, Context context, Activity app){
        OffersDatabaseHelper ofhd = new OffersDatabaseHelper(context);
        SQLiteDatabase db = ofhd.getReadableDatabase();
        Cursor cursor;
        if(query.equals("")){
            cursor=db.query("OFFERS",
                    new String[] {"NAME_OFFER","IMAGE_RESOURCE_ID","LOCALISATION","AUTHOR","ADD_DATE","_id"},
                    null,null,null,null,"_id DESC"
            );
        } else {
            cursor=db.query("OFFERS",
                    new String[] {"NAME_OFFER","IMAGE_RESOURCE_ID","LOCALISATION","AUTHOR","ADD_DATE","_id"},
                    "NAME_OFFER LIKE '%"+query+"%'",null,null,null,"_id DESC"
            );
        }

        if(cursor.moveToNext()) {
            Integer[] id_offers = new Integer[cursor.getCount()];
            String[] webs = new String[cursor.getCount()];
            String[] localisation = new String[cursor.getCount()];
            String[] author = new String[cursor.getCount()];
            Integer[] imageId = new Integer[cursor.getCount()];
            String[] dates = new String[cursor.getCount()];
            for (int i = 0; !cursor.isAfterLast(); i++) {
                webs[i] = cursor.getString(0);
                imageId[i] = cursor.getInt(1);
                localisation[i] = cursor.getString(2);
                author[i] = cursor.getString(3);
                dates[i] = cursor.getString(4);
                id_offers[i] = cursor.getInt(5);
                cursor.moveToNext();
            }
            CustomList adapter = new CustomList(app, id_offers,webs, imageId, localisation, dates);
            cursor.close();
            db.close();
            return adapter;
        } else {
            Integer[] id_offers = new Integer[cursor.getCount()];
            String[] webs = new String[cursor.getCount()];
            String[] localisation = new String[cursor.getCount()];
            String[] author = new String[cursor.getCount()];
            Integer[] imageId = new Integer[cursor.getCount()];
            String[] dates = new String[cursor.getCount()];
            CustomList adapter = new CustomList(app, id_offers,webs, imageId, localisation, dates);
            cursor.close();
            db.close();
            return adapter;
        }
    }
}