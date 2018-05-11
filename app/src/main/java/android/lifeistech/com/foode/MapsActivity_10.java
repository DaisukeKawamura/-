package android.lifeistech.com.foode;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.lifeistech.com.foode.PlaceAPIService;
import android.lifeistech.com.foode.Responce;
import android.lifeistech.com.foode.Geometry;
import android.lifeistech.com.foode.Location;
import android.lifeistech.com.foode.Result;

import retrofit.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.util.List;
import java.util.Random;




public class MapsActivity_10 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView textView;
    Retrofit mRetrofit;

    private PlaceAPIService mService;
    private double lat,lng;
    private Location location;

    private final String API_KEY = "AIzaSyAEJJjYOFLqiU550af8F4hRuO1bg1Ov-k4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_10);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        textView = (TextView) findViewById(R.id.textView);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = mRetrofit.create(PlaceAPIService.class);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void searchMap(){

        Call<Responce> mResultCall = mService.requestPlaces("restaurant+in+Osaka", API_KEY);

        mResultCall.enqueue(new retrofit2.Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Result> results = response.body().getResults();

                for (Result r:results){
                    Log.d("name",r.getName() + "\n");
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t){

            }
        });
    }



    public void search(View v) {

    }


    public void random(View v) {
        Random random = new Random();
        int number;
        number = random.nextInt(11);



        if (number == 0) {
            textView.setText("オムライス");
        } else if (number == 1) {
            textView.setText("うどん");
        } else if (number == 2) {
            textView.setText("カレー");
        } else if (number == 3) {
            textView.setText("焼肉");
        } else if (number == 4) {
            textView.setText("鍋");
        } else if (number == 5) {
            textView.setText("ラーメン");
        } else if (number == 6) {
            textView.setText("海鮮");
        } else if (number == 7) {
            textView.setText("お好み焼き");
        } else if (number == 8) {
            textView.setText("串カツ");
        } else if (number == 9) {
            textView.setText("とんかつ");
        }else if (number == 10){
            textView.setText("寿司");
        }
    }



    public void back(View v) {
        finish();
    }


}


