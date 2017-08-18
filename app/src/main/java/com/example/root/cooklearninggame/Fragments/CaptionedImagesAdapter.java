package com.example.root.cooklearninggame.Fragments;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.cooklearninggame.R;


/**
 * Created by root on 08.05.2017.
 */

class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder>
{
    private int lastPosition = -1;

    private Context context;
    private String[] captions;
    private int[] imageIds;
    private Listener listener;

    public static interface Listener {
        public void onClick(int position);
    }

    public void setListener(Listener listener){
        this.listener=listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView=v;
        }
    }

    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv=(CardView)  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image,parent,false);
        context=parent.getContext();
        return new ViewHolder(cv);
    }

    public CaptionedImagesAdapter(String[] captions, int[] imageIds){
        this.captions=captions;
        this.imageIds=imageIds;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView=holder.cardView;
        ImageView imageView=(ImageView)cardView.findViewById(R.id.info_image);

        Drawable drawable=cardView.getResources().getDrawable(imageIds[position]);// .getDrawable(getResources(), R.drawable.name, null);.get (imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions[position]);

        TextView textView=(TextView) cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);
        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        cardView.startAnimation(animation);
    }

    @Override
    public int getItemCount(){
        return captions.length;
    }

}