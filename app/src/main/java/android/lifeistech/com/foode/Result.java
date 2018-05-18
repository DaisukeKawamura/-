package android.lifeistech.com.foode;

import com.google.gson.annotations.SerializedName;
import com.google.gson.Gson;

import java.security.PrivateKey;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public  class  Result {

    private static final String TAG = Result.class.getSimpleName();
    private final Result self = this;


    @SerializedName("geometry")
    public  Geometry geometry;
    @SerializedName("name")
    public  String name;
    @SerializedName("rating")
    public  double rating;
    @SerializedName("icon")
    private String icon;
    @SerializedName("id")
    private String id;
    @SerializedName("place_id")
    private String place_id;
    @SerializedName("reference")
    private String reference;
    @SerializedName("types")
    private String[] types;
    @SerializedName("vicinity")
    private String vicinity;


    public Result(Geometry geometry, String icon, String id, String name, String place_id, double rating, String reference, String[] types, String vicinity) {
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.place_id = place_id;
        this.rating = rating;
        this.reference = reference;
        this.types = types;
        this.vicinity = vicinity;
    }

    public  Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }


}
