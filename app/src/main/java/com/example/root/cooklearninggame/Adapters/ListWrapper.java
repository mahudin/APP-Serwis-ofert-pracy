package com.example.root.cooklearninggame.Adapters;

/**
 * Created by root on 12.08.2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.cooklearninggame.R;

public class ListWrapper extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;

    public ListWrapper(Activity context, String[] web, Integer[] imageId) {
        super(context, R.layout.list_wrapper, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_wrapper, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.result);
        txtTitle.setTextColor(Color.BLACK);
        txtTitle.setText(web[position]);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        imageView.setImageResource(imageId[position]);

        return rowView;
    }
}