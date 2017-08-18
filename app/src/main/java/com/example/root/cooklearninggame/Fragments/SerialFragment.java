package com.example.root.cooklearninggame.Fragments;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.root.cooklearninggame.NumberSerialActivity;
import com.example.root.cooklearninggame.R;
import com.example.root.cooklearninggame.Objects.Serials;

public class SerialFragment extends DialogFragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        RecyclerView optionRecycler=(RecyclerView) inflater.inflate(R.layout.fragment_serial,container,false);

        String[] serialNames=new String[Serials.serial.length];
        for(int i=0;i<serialNames.length;i++){
            serialNames[i]=Serials.serial[i].getName();
        }

        int[] serialImages= new int[Serials.serial.length];
        for(int i=0;i<serialImages.length;i++){
            serialImages[i]=Serials.serial[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter= new CaptionedImagesAdapter(serialNames,serialImages);
        optionRecycler.setAdapter(adapter);

        //LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        optionRecycler.setLayoutManager(layoutManager);
        adapter.setListener(new CaptionedImagesAdapter.Listener(){
            public void onClick(int position){
                if(position==0){
                    Intent intent=new Intent(getActivity(),NumberSerialActivity.class);
                    intent.putExtra(NumberSerialActivity.EXTRA_PIZMANO,position);
                    getActivity().startActivity(intent);
                } else {
                    AlertDialog.Builder alertdFragment = new AlertDialog.Builder(inflater.getContext());
                    alertdFragment.setMessage("W obecnej wersji aplikacji, opcja ta nie jest dostępna!");
                    alertdFragment.show();
                }
            }
        });
        return optionRecycler;
    }
}