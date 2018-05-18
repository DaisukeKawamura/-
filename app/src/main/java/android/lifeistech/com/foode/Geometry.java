package android.lifeistech.com.foode;

import android.util.Log;
import android.view.View;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public class Geometry {

    private static final String TAG = Geometry.class.getSimpleName();
    private final Geometry self = this;


    @SerializedName("location")
    public  Location location;

    public Geometry(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
