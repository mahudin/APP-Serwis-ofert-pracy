package com.example.root.cooklearninggame.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BestTimeFragment extends ListFragment {

    //private ArrayList<Record> records;
    public static BestTimeFragment newInstance() {
        return new BestTimeFragment();
    }

    public BestTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);//inflater.inflate(R.layout.fragment_best_time, container, false);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        //savedInstanceState.putParcelableArrayList("records",this.records);
        //super.onSaveInstanceState(savedInstanceState);
        //savedInstanceState.putInt("wynik",this.wynik);
    }
}