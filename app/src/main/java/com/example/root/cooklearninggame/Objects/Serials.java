package com.example.root.cooklearninggame.Objects;

import com.example.root.cooklearninggame.R;

/**
 * Created by root on 04.07.2017.
 */

public class Serials {
    private String name;
    private int imageResourceId;
    public static final Serials[] serial = {
            new Serials("Najnowsze", R.drawable.joblist),
            new Serials("Według kryteriów",R.drawable.jobsearch),
    };

    public static final String[] serial_names = {
            "Najnowsze","Według kryteriów"
    };

    private Serials(String name, int imageResourceId){
        this.name=name;
        this.imageResourceId=imageResourceId;
    }

    public String getName(){
        return name;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }
}
