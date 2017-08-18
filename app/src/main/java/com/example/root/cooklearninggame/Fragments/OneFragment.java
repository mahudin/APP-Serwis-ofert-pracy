package com.example.root.cooklearninggame.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.root.cooklearninggame.R;
import com.fmsirvent.ParallaxEverywhere.PEWImageView;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment{
    private String activityAssignedValue ="";
    private TextView textView;
    private String desc_recipe;
    private String img_offer;
    public OneFragment() {
        // Required empty public constructor
    }

    public static OneFragment newInstance() {
        return new OneFragment();
    }

    public void setDesc_recipe(String desc){
        this.desc_recipe=desc;
    }
    public void setImg_offer(String img){
        this.img_offer=img;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("desc_text",desc_recipe);
        savedInstanceState.putString("img_recipe",img_offer);
    }

    public void onResume(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onResume();
        View layout=inflater.inflate(R.layout.fragment_one, container, false);
        textView =(TextView)layout.findViewById(R.id.desc_text);
        PEWImageView pewi= (PEWImageView) layout.findViewById(R.id.toper_image);
        if(savedInstanceState!=null){
            textView.setText(savedInstanceState.getString("desc_text"));
            img_offer=savedInstanceState.getString("img_recipe");

            try {
                if(!this.img_offer.equals("") || this.img_offer!=null ) {
                    Picasso.with(layout.getContext()).load(img_offer).into(pewi);
                } else {
                    Picasso.with(layout.getContext()).load(R.drawable.brak_zdjecia).into(pewi);
                }
            } catch (Exception e){
                Log.e("Error",e.getMessage());
            }
        }else {
            textView.setText(desc_recipe);
            try {
                if(!this.img_offer.equals("") || this.img_offer!=null ) {
                    Picasso.with(layout.getContext()).load(img_offer).into(pewi);
                } else {
                    Picasso.with(layout.getContext()).load(R.drawable.brak_zdjecia).into(pewi);
                }
            } catch (Exception e){
                Log.e("Error",e.getMessage());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_one, container, false);
        textView =(TextView)layout.findViewById(R.id.desc_text);
        PEWImageView pewi= (PEWImageView) layout.findViewById(R.id.toper_image);
        if(savedInstanceState!=null){
            desc_recipe=savedInstanceState.getString("desc_text");
            img_offer=savedInstanceState.getString("img_recipe");
            try {
                if(!this.img_offer.equals("") && this.img_offer!=null ) {
                    Picasso.with(layout.getContext()).load(img_offer).into(pewi);
                } else {
                    Picasso.with(layout.getContext()).load(R.drawable.brak_zdjecia).into(pewi);
                }
            } catch (Exception e){
                String err = (e.getMessage()==null)?"failed":e.getMessage();
                Log.e("Error",err);
            }
            textView.setText(savedInstanceState.getString("desc_text"));
        } else {
            textView.setText(desc_recipe);
            try {
                if(!this.img_offer.equals("") && this.img_offer!=null ) {
                    Picasso.with(layout.getContext()).load(img_offer).into(pewi);
                } else {
                    Picasso.with(layout.getContext()).load(R.drawable.brak_zdjecia).into(pewi);
                }

            } catch (Exception e){
                String err = (e.getMessage()==null)?"failed":e.getMessage();
                Log.e("Error",err);
            }
        }
        Animation animation = AnimationUtils.loadAnimation(layout.getContext(), android.R.anim.slide_out_right);
        pewi.startAnimation(animation);
        return layout;
    }

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        PEWImageView bmImage;
        public DownloadImageWithURLTask(PEWImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {

            bmImage.setImageBitmap(result);
        }
    }
}