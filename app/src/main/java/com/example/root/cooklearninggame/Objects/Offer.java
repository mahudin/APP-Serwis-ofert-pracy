package com.example.root.cooklearninggame.Objects;

/**
 * Created by root on 11.07.2017.
 */

public class Offer {
    private String name_offer;
    private String category;
    private String desc;
    private String localisation;
    private String type_of_job;
    private String kind_of_contract;
    private String author;
    private String phone;
    private String url;
    private int image_resource_id;
    private String add_date;
    public static String offer_id="offer_id";
    public static Integer id_offer;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType_of_job() {
        return type_of_job;
    }

    public String getKind_of_contract() {
        return kind_of_contract;
    }

    public Offer(String name_offer, String category, String desc, String localisation, String type_of_job, String kind_of_contract, String author, int image_resource_id, String add_date,String phone,String url) {
        this.name_offer = name_offer;
        this.category = category;
        this.desc = desc;
        this.localisation = localisation;
        this.type_of_job = type_of_job;
        this.kind_of_contract = kind_of_contract;
        this.author = author;
        this.image_resource_id = image_resource_id;
        this.add_date = add_date;
        this.phone = phone;
        this.url = url;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url;}

    public String getName_offer() {
        return name_offer;
    }

    public String getCategory() {
        return category;
    }

    public String getDesc() {
        return desc;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getAuthor() {
        return author;
    }

    public int getImage_resource_id() {
        return image_resource_id;
    }

    public String getAdd_date() {
        return add_date;
    }
    public String _toString(){
        return name_offer;
    }
}
