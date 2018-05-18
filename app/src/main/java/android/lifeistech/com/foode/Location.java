package android.lifeistech.com.foode;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public class Location {
    private static final String TAG = Location.class.getSimpleName();
    private final Location self = this;

    @SerializedName("lat")
    public double lat;

    @SerializedName("lng")
    public double lng;

    public Location(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public  void setLng(double lng) {
        this.lng = lng;
    }
}
