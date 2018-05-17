package android.lifeistech.com.foode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public class Location {
    private static final String TAG = Location.class.getSimpleName();
    private final Location self = this;

    public static double latitude;
    public static double longtitude;

    public Location(double lat, double lng) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public static double getLatitude() {
        return latitude;
    }

    public void setLat(double latitude) {
        this.latitude = latitude;
    }

    public static double getLng() {
        return longtitude;
    }

    public  void setLng(double longtitude) {
        this.longtitude = longtitude;
    }
}
