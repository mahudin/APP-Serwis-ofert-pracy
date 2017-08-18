package com.example.root.cooklearninggame.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.cooklearninggame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreeFragment extends Fragment {

    private String add_recipe;
    public ThreeFragment() {
        // Required empty public constructor
    }
    public void setAdd_recipe(String ar){
        add_recipe=ar;
    }

    public static ThreeFragment newInstance() {
        return new ThreeFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString("add_recipe",add_recipe);
    }

    public void onResume(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onResume();
        View layout=inflater.inflate(R.layout.fragment_one, container, false);
        TextView tv=(TextView)layout.findViewById(R.id.main_text);
        if(savedInstanceState!=null){
            tv.setText(savedInstanceState.getString("add_recipe"));
        }else {
            tv.setText(add_recipe);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_three, container, false);
        TextView tv=(TextView)layout.findViewById(R.id.main_text);
        if(savedInstanceState!=null){
            tv.setText(savedInstanceState.getString("add_recipe"));
        }else {
            tv.setText(add_recipe);
        }
        return layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}