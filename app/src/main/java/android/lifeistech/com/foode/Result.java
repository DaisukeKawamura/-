package android.lifeistech.com.foode;

import java.security.PrivateKey;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public class Result {

    private static final String TAG =Result.class.getSimpleName();
    private final Result self = this;

    private Geometry geometry;
    private String icon;
    private String id;
    private String name;
    private String place_id;
    private String rating;
    private String[] types;



    public Result(Geometry geometry, String icon, String id, String name, String place_id, String rating,String[] types){
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
        this.rating = rating;
        this.types = types;

    }




    //Geometryデータの取得
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }



    //iconデータの取得
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    //idデータの取得
    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }



    //nameデータの取得
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    //place_idデータの取得
    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }



    //ratingデータの取得
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }



    //typesデータの取得
    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }



}
