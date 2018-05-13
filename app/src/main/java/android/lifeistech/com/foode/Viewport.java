package android.lifeistech.com.foode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kawamuradaisuke on 2018/05/13.
 */

public class Viewport {

    private static final String TAG = Viewport.class.getSimpleName();
    private final Viewport self = this;



    @SerializedName("northest")
    private String northest;

    @SerializedName("southest")
    private String southest;

    //northest
    public String getNorthest(){
        return northest;
    }
    public void setNorthest(String northest){
        this.northest = northest;
    }


    //southest
    public String getSouthest(){
        return southest;
    }
    public void setSouthest(String southest){
        this.southest = southest;
    }


}
