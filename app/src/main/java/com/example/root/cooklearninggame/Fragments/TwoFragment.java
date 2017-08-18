package com.example.root.cooklearninggame.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.cooklearninggame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {

    private int seconds = 0 ;
    private boolean running;
    private boolean wasRunning;
    private String myres;
    private String res;
    private boolean is_exceeded_limit;
    private String color="#FF2400";
    String kind_of_contract;
    String type_of_work;
    String author;
    String add_date;
    String localisation;

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setKind_of_contract(String kind_of_contract) {
        this.kind_of_contract = kind_of_contract;
    }

    public void setType_of_work(String type_of_work) {
        this.type_of_work = type_of_work;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }


    public TwoFragment() {
        // Required empty public constructor
    }
    public static TwoFragment newInstance() {
        return new TwoFragment();
    }
    public void setMyResult_recipe(String myres){ this.myres=myres; }
    public void setResult_recipe(String res){ this.res=res;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            localisation=savedInstanceState.getString("localisation");
            author=savedInstanceState.getString("author");
            add_date=savedInstanceState.getString("add_date");
            type_of_work=savedInstanceState.getString("type_of_work");
            kind_of_contract=savedInstanceState.getString("kind_of_contract");

        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("localisation",localisation);
        savedInstanceState.putString("author",author);
        savedInstanceState.putString("add_date",add_date);
        savedInstanceState.putString("type_of_work",type_of_work);
        savedInstanceState.putString("kind_of_contract",kind_of_contract);

    }

    public void onResume(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        super.onResume();
        View layout=inflater.inflate(R.layout.fragment_two, container, false);

        if(savedInstanceState!=null){
            localisation=savedInstanceState.getString("localisation");
            author=savedInstanceState.getString("author");
            add_date=savedInstanceState.getString("add_date");
            type_of_work=savedInstanceState.getString("type_of_work");
            kind_of_contract=savedInstanceState.getString("kind_of_contract");
            TextView tv=(TextView)layout.findViewById(R.id.textView9);
            tv.setText(type_of_work);
            TextView tv2=(TextView)layout.findViewById(R.id.textView10);
            tv2.setText(kind_of_contract);
            TextView tv3=(TextView)layout.findViewById(R.id.textView11);
            tv3.setText(add_date);
            TextView tv4=(TextView)layout.findViewById(R.id.textView12);
            tv4.setText(author);
            TextView tv5=(TextView)layout.findViewById(R.id.textView13);
            tv5.setText(localisation);
        }
        else {
            TextView tv=(TextView)layout.findViewById(R.id.textView9);
            tv.setText(type_of_work);
            TextView tv2=(TextView)layout.findViewById(R.id.textView10);
            tv2.setText(kind_of_contract);
            TextView tv3=(TextView)layout.findViewById(R.id.textView11);
            tv3.setText(add_date);
            TextView tv4=(TextView)layout.findViewById(R.id.textView12);
            tv4.setText(author);
            TextView tv5=(TextView)layout.findViewById(R.id.textView13);
            tv5.setText(localisation);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout=inflater.inflate(R.layout.fragment_two, container, false);

        if(savedInstanceState!=null){
            localisation=savedInstanceState.getString("localisation");
            author=savedInstanceState.getString("author");
            add_date=savedInstanceState.getString("add_date");
            type_of_work=savedInstanceState.getString("type_of_work");
            kind_of_contract=savedInstanceState.getString("kind_of_contract");
            TextView tv=(TextView)layout.findViewById(R.id.textView9);
            tv.setText(type_of_work);
            TextView tv2=(TextView)layout.findViewById(R.id.textView10);
            tv2.setText(kind_of_contract);
            TextView tv3=(TextView)layout.findViewById(R.id.textView11);
            tv3.setText(add_date);
            TextView tv4=(TextView)layout.findViewById(R.id.textView12);
            tv4.setText(author);
            TextView tv5=(TextView)layout.findViewById(R.id.textView13);
            tv5.setText(localisation);
        }
        else {
            TextView tv=(TextView)layout.findViewById(R.id.textView9);
            tv.setText(type_of_work);
            TextView tv2=(TextView)layout.findViewById(R.id.textView10);
            tv2.setText(kind_of_contract);
            TextView tv3=(TextView)layout.findViewById(R.id.textView11);
            tv3.setText(add_date);
            TextView tv4=(TextView)layout.findViewById(R.id.textView12);
            tv4.setText(author);
            TextView tv5=(TextView)layout.findViewById(R.id.textView13);
            tv5.setText(localisation);
        }
        return layout;
    }
}