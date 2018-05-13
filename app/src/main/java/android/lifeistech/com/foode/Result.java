package android.lifeistech.com.foode;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

import java.security.PrivateKey;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public class Result {

    private static final String TAG =Result.class.getSimpleName();
    private final Result self = this;



    //変数の宣言

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("icon")
    private String icon;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("place_id")
    private String place_id;

    @SerializedName("rating")
    private String rating;

    @SerializedName("types")
    private String[] types;

    @SerializedName("reference")
    private String reference;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("scope")
    private  String scope;



    //

    public Result(Geometry geometry, String icon, String id, String name, String place_id, String rating,String[] types, String reference, String vicinity, String scope){
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
        this.rating = rating;
        this.types = types;
        this.reference = reference;
        this.vicinity = vicinity;
        this.scope = scope;

    }


////getter/setterを使ってgsonからjsonオブジェクトに変換された内容を変数内に格納

    //Geometry
    public Geometry getGeometry() {
        return geometry;
    }
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }



    //icon
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }


    //id
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }



    //name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    //place_id
    public String getPlace_id() {
        return place_id;
    }
    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }



    //rating
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }



    //types
    public String[] getTypes() {
        return types;
    }
    public void setTypes(String[] types) {
        this.types = types;
    }



    //reference
    public String getReference(){
        return reference;
    }
    public void setReference(String reference){
        this.reference = reference;
    }



    //vicinity
    public String getVicinity(){
        return vicinity;
    }
    public void setVicinity(String vicinity){
        this.vicinity =vicinity;
    }



    //scope
    public String getScope(){
        return scope;
    }
    public void setScope(String scope){
        this.scope = scope;
    }





}
