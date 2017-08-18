package com.example.root.cooklearninggame.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.cooklearninggame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment implements View.OnClickListener  {

    private int wynik;
    private int maxi;
    public static TopFragment newInstance() {
        return new TopFragment();
    }
    public void setMyResult(int wynik,int maxi){
        this.wynik=wynik;
        this.maxi=maxi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null) {
            wynik = savedInstanceState.getInt("wynik");
            maxi = savedInstanceState.getInt("maxi");
        }
    }

    public void onResume(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onResume();
        View layout=inflater.inflate(R.layout.fragment_top, container, false);

        TextView tv2=(TextView) layout.findViewById(R.id.textView5);
        if(savedInstanceState!=null){
            tv2.setText(savedInstanceState.getInt("wynik"));
        }else {
           // tv2.setText("1");
        }
        TextView tv=(TextView)layout.findViewById(R.id.textView6);
        if(savedInstanceState!=null){
            tv.setText(savedInstanceState.getInt("maxi"));
        }else {
           // tv.setText("1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      /*  ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.titles)
        );*/
        //setListAdapter(adapter);
        //setOnClickListener(this);
        View layout=inflater.inflate(R.layout.fragment_top, container, false);
        ImageView iv=(ImageView) layout.findViewById(R.id.imageView);
        //iv.setImageResource(R.drawable.cooker);

        TextView tv=(TextView) layout.findViewById(R.id.textView5);
        tv.setText(Integer.toString(this.wynik));

        TextView tv2=(TextView) layout.findViewById(R.id.textView6);
        tv2.setText("/"+Integer.toString(this.maxi));
        return layout;
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return super.onCreateView(inflater, container, savedInstanceState);//inflater.inflate(R.layout.fragment_top, container, false);
    }
    @Override
    public void onClick(View v) {
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("maxi",this.maxi);
        savedInstanceState.putInt("wynik",this.wynik);
    }
}
