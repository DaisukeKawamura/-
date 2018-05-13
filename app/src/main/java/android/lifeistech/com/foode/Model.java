package android.lifeistech.com.foode;

/**
 * Created by kawamuradaisuke on 2018/05/13.
 */

import com.google.gson.annotations.SerializedName;

public class Model {

    //変数宣言
    @SerializedName("geometry")
    private String geometry;

    @SerializedName("icon")
    private String icon;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("place_id")
    private String place_id;

    @SerializedName("types")
    private String types;

    @SerializedName("reference")
    private String reference;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("scope")
    private  String scope;






    //getter/setterを使ってgsonからjsonオブジェクトに変換された内容を変数内に格納

    //Geometry
    public String getGeometry(){
        return geometry;
    }
    public void setGeometry(String geometry){
        this.geometry = geometry;
    }

    //icon
    public String getIcon(){
        return icon;
    }
    public void setIcon(String icon){
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
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    //place_id
    public String getPlace_id(){
        return place_id;
    }
    public void setPlace_id(String place_id){
        this.place_id = place_id;
    }

    //types
    public String getTypes(){
        return types;
    }
    public void setTypes(String types){
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
        this.vicinity = vicinity;
    }

    public String getScope(){
        return scope;
    }
    public void setScope(String scope){
        this.scope = scope;
    }





}
