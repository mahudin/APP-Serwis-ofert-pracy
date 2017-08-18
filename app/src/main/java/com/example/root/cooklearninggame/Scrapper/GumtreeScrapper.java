package com.example.root.cooklearninggame.Scrapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.root.cooklearninggame.DB.OffersDatabaseHelper;
import com.example.root.cooklearninggame.Objects.Offer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by root on 08.08.2017.
 */

public class GumtreeScrapper extends JVScrapper {
    private Document document;
    private int id_web_service=1;
    private String url="https://www.gumtree.pl";
    private Map<String,String> categories=new HashMap<String, String>();
    private Context context;
          //  "","https://www.gumtree.pl/s-grafika-i-web-design/v1c9140p1"
    // "https://www.gumtree.pl/s-inzynierowie-technicy-i-architekci/v1c9094p1"
    ;
    public GumtreeScrapper(Context context){
        this.context=context;
        initial_categories();
    }

    private void initial_categories(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        String stp=prefs.getString("gumtree_category","DEFAULT");
        switch (stp){
            case "Graficy i webdesign":
                categories.put("1",this.url+"/s-grafika-i-web-design/v1c9140p1");
                break;
            case "Inżynierowie technicy i architekci":
                categories.put("1",this.url+"/s-inzynierowie-technicy-i-architekci/v1c9094p1");
                break;
            case "Praca inne":
                categories.put("1",this.url+"/s-praca-inne/krakow/v1c9099l3200208p1");
                break;
        }
    }

    public ArrayList<Offer> action_start(){
        try{
            ArrayList<Offer> aof = new ArrayList<Offer>();
            for (String category : categories.values()) {
                Document doc = Jsoup.connect(category).get();
                Elements elements = doc.getElementsByClass("href-link");
                for (Element element : elements) {
                    String link = element.attr("href");
                    Log.d("LINK", link);
                    Document doc_link = Jsoup.connect(url + link).get();
                    Elements titles = doc_link.getElementsByClass("myAdTitle");
                    Element title = titles.get(0);
                    String s_title = title.text().replace("\n","");
                    Log.d("TITLE", s_title);
                    /*if (s_title.length() >= 32) {
                        s_title = s_title.substring(0, 32) + "\n" + s_title.substring(32, s_title.length());
                    }*/
                    Elements descs = doc_link.getElementsByClass("pre");
                    StringBuilder sb = new StringBuilder();
                    for (Element desc : descs) {
                        sb.append(descs.text());
                        sb.append("\n\n");
                    }
                    String nr_phone="";
                    Elements phones = doc_link.getElementsByClass("telephone");

                    if(!phones.isEmpty()){
                        String nr_phone_pre=phones.get(0).attr("href");
                        nr_phone=nr_phone_pre.substring(4,nr_phone_pre.length());
                    } else {
                        nr_phone="";
                    }

                    String url="";

                    Elements mains = doc_link.getElementsByClass("main");
                    for(Element elem:mains){
                        Elements src=elem.getElementsByAttribute("src");
                        if(!src.get(0).attr("src").equals("")){
                            Log.v("MAINs",src.get(0).attr("src"));
                            url=src.get(0).attr("src");
                        } else {
                            url="cholerqa";
                        }
                    }

                    String data_dodania = "";
                    String lokalizacja = "";
                    String autor = "";
                    String rodzaj_pracy = "";
                    String rodzaj_umowy = "";
                    Elements elements_attributes = doc_link.getElementsByClass("attribute");
                    for (Element element_attribute : elements_attributes) {
                        Elements elem = element_attribute.getElementsByClass("name");
                        Elements elem2 = element_attribute.getElementsByClass("value");
                        switch (elem.text()) {
                            case "Data dodania":
                                data_dodania = elem2.text();
                                break;
                            case "Lokalizacja":
                                lokalizacja = elem2.text();
                                break;
                            case "Ogłaszane przez":
                                autor = elem2.text();
                                break;
                            case "Rodzaj pracy":
                                rodzaj_pracy = elem2.text();
                                break;
                            case "Rodzaj umowy":
                                rodzaj_umowy = elem2.text();
                                break;
                        }
                    }
                    Offer of = new Offer(s_title.trim(), "0", sb.toString().replace(". M",".\n M").replace(" Proszę","\n Proszę").replace("*","\n*").replace(" -","\n-").replace(" Wymagania","\n\n Wymagania").replace(" Mile","\n\n Mile").replace(" >","\n >").replace(" W CV","\n\n W CV").replace("&bull;","\n &bull;"), lokalizacja, rodzaj_pracy, rodzaj_umowy, autor, 0, data_dodania,nr_phone,url);
                    aof.add(of);
                }
            }
            return aof;
        } catch(Exception e){}
        return null;
    }
}