package com.example.root.cooklearninggame.Adapters;

/**
 * Created by root on 17.07.2017.
 */

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.cooklearninggame.R;

import java.util.List;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final Integer[] id_offer;
    private final String[] web;
    private final String[] result;
    private final Integer[] imageId;
    private final String[] dates;

    List<String> arrayList;

    public CustomList(Activity context, Integer[] id_offer,String[] web, Integer[] imageId,String[] result,String[] dates) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.id_offer=id_offer;
        this.result=result;
        this.web = web;
        this.imageId = imageId;
        this.dates = dates;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView id_offer_view = (TextView) rowView.findViewById(R.id.id_offer);
        id_offer_view.setText(Integer.toString(id_offer[position]));

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(web[position]);

        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.result);
        txtTitle2.setText(result[position]);

        TextView txtTitle3 = (TextView) rowView.findViewById(R.id.date);
        txtTitle3.setText(dates[position]);

        return rowView;
    }

    @Override
    public int getCount(){
        return web!=null ? web.length : 0;
    }
}