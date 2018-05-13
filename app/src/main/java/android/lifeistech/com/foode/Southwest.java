package android.lifeistech.com.foode;

import android.media.SoundPool;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kawamuradaisuke on 2018/05/13.
 */

public class Southwest {


    private static final String TAG = Southwest.class.getSimpleName();
    private final Southwest self = this;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;



    public double getLat(){
        return lat;
    }
    public void setLat(double lat){
        this.lat = lat;
    }


    public double getLng(){
        return lng;
    }
    public void setLng(double lng){
        this.lng =lng;
    }

}
