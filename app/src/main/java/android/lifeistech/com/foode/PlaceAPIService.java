package android.lifeistech.com.foode;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Query;
import retrofit2.Call;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public interface PlaceAPIService {
    @Headers("Accept-Language: ja")
    @GET("maps/api/place/search/json?")
    Call<Responce> requestPlaces(@Query("location")String location,
                                 @Query("radius")int radius,
                                 @Query("keyward")String keyword,
                                 @Query("key")String key);

}



//https://maps.googleapis.com/maps/api/place/search/json?location=34.6937378,135.5021651&radius=750&types=カレー+in+Osaka&key=AIzaSyDonRgMSNGSG_1dF2YehCscx2eHRYp5JZQ
//https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Osaka&key=AIzaSyDonRgMSNGSG_1dF2YehCscx2eHRYp5JZQ
//https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Osaka&key=AIzaSyAEJJjYOFLqiU550af8F4hRuO1bg1Ov-k4
//https://maps.googleapis.com/maps/api/place/search/json?location=カレー+in+Osaka&radius=750&types=カレー&key=AIzaSyCVFZYOj9WB8ZJraEGzMwubFei3oJ2XlkE