package android.lifeistech.com.foode;

import android.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapsActivity_10 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView textView;
    Retrofit mRetrofit;

    private PlaceAPIService mService;

    //FusedLocationProvider API
    private FusedLocationProviderClient fusedLocationProviderClient;

    //Location Setting API
    private SettingsClient settingsClient;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private android.location.Location location;

    private String lastUpDateTime;
    private Boolean reqestingLocationUpdates;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private int priority = 0;
    private double lat,lng;

    String loc = null;

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

        //FusedLocationProviderClientの使用を可能にする
        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        priority = 0;


        //「位置情報関連のメソッド」
        //現在地の情報を求めるリクエストを渡して、結果が帰ってくる設定のメソッドの呼び出し
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        //実際にMap上に現在地が表示されるようにデータをップロードするメソッド
        startLocationUpdates();
        stopLocationUpdates();

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
        LatLng osaka = new LatLng(34.6937378, 135.5021651);
        mMap.addMarker(new MarkerOptions().position(osaka).title("大阪"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(osaka));

    }






    public void search(View v){


        /*APIを叩いて受け取った情報を、検索ボタン（OnClick search）を
        クリックした時に、GoogleMap画面内に表示させる*/


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







    //locationのコールバック位を受け取る
    public void createLocationCallback(){
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);

                location = locationResult.getLastLocation();

                lastUpDateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();

            }
        };
    }







    private void updateLocationUI() {
        // getLastLocation()からの情報がある場合のみ
        if (location != null) {

            String fusedName[] = {
                    "Latitude", "Longitude", "Accuracy",
                    "Altitude", "Speed", "Bearing"
            };

            double fusedData[] = {
                    location.getLatitude(),
                    location.getLongitude(),
                    location.getAccuracy(),
                    location.getAltitude(),
                    location.getSpeed(),
                    location.getBearing()
            };

            StringBuilder strBuf =
                    new StringBuilder("---------- UpdateLocation ---------- \n");

            for(int i=0; i< fusedName.length; i++) {
                strBuf.append(fusedName[i]);
                strBuf.append(" = ");
                strBuf.append(String.valueOf(fusedData[i]));
                strBuf.append("\n");
            }

            strBuf.append("Time");
            strBuf.append(" = ");
            strBuf.append(lastUpDateTime);
            strBuf.append("\n");


            lat = location.getLatitude();
            lng = location.getLongitude();

            StringBuilder strBuf2 = new StringBuilder();
            strBuf2.append(lat);
            strBuf2.append(",");
            strBuf2.append(lng);

            loc = String.valueOf(strBuf2);
            Log.d("loc",loc);

            searchMap(loc);
        }
    }








    public void searchMap(String argloc){

        Call<Responce> mResultCall = mService.requestPlaces(argloc,750,"restaurant", API_KEY);

        mResultCall.enqueue(new retrofit2.Callback<Responce>() {
            @Override
            public void onResponse(Call<Responce> call, Response<Responce> response) {
                List<Result> results = response.body().getResults();

                for (Result r:results){
                    Log.d("geometry", r.getGeometry() + "\n");
                    Log.d("icon", r.getIcon() + "\n");
                    Log.d("id", r.getId() + "\n");
                    Log.d("name",r.getName() + "\n");
                    Log.d("place_id", r.getPlace_id() + "\n");
                    Log.d("rating", r.getRating() + "\n");
                }
            }

            @Override
            public void onFailure(Call<Responce> call, Throwable t){

            }
        });
    }






    @SuppressLint("RestrictedApi")
    private void createLocationRequest() {
        locationRequest = new LocationRequest();

        if (priority == 0) {
            // 高い精度の位置情報を取得したい場合
            // インターバルを例えば5000msecに設定すれば
            // マップアプリのようなリアルタイム測位となる
            // 主に精度重視のためGPSが優先的に使われる
            locationRequest.setPriority(
                    LocationRequest.PRIORITY_HIGH_ACCURACY);

        } else if (priority == 1) {
            // バッテリー消費を抑えたい場合、精度は100mと悪くなる
            // 主にwifi,電話網での位置情報が主となる
            // この設定の例としては　setInterval(1時間)、setFastestInterval(1分)
            locationRequest.setPriority(
                    LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        } else if (priority == 2) {
            // バッテリー消費を抑えたい場合、精度は10kmと悪くなる
            locationRequest.setPriority(
                    LocationRequest.PRIORITY_LOW_POWER);

        } else {
            // 受け身的な位置情報取得でアプリが自ら測位せず、
            // 他のアプリで得られた位置情報は入手できる
            locationRequest.setPriority(
                    LocationRequest.PRIORITY_NO_POWER);
        }

        // アップデートのインターバル期間設定
        // このインターバルは測位データがない場合はアップデートしません
        // また状況によってはこの時間よりも長くなることもあり
        // 必ずしも正確な時間ではありません
        // 他に同様のアプリが短いインターバルでアップデートしていると
        // それに影響されインターバルが短くなることがあります。
        // 単位：msec
        locationRequest.setInterval(60000);
        // このインターバル時間は正確です。これより早いアップデートはしません。
        // 単位：msec
        locationRequest.setFastestInterval(5000);

    }

    // 端末で測位できる状態か確認する。wifi, GPSなどがOffになっているとエラー情報のダイアログが出る
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            //最初にstartResolutionForResult()に与えられたリクエストコードを確認する
            case REQUEST_CHECK_SETTINGS:
                switch (requestCode){
                    case Activity.RESULT_OK:
                        Log.i("debug", "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;

                    case Activity.RESULT_CANCELED:
                        Log.i("debug", "User chose not to make required location settings changes.");
                        reqestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }






    // FusedLocationApiによるlocation updatesをリクエスト
    private void startLocationUpdates(){
        //デバイスが必要な場所の設定を持っているかどうかを調べる
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i("debug", "All location settings are satisfied.");

                        // パーミッションの確認
                        if(ActivityCompat.checkSelfPermission(
                                MapsActivity_10.this,
                                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(
                                MapsActivity_10.this,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest, locationCallback, Looper.myLooper());

                    }



                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i("debug", "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(
                                            MapsActivity_10.this,
                                            REQUEST_CHECK_SETTINGS);

                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i("debug", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e("debug", errorMessage);
                                Toast.makeText(MapsActivity_10.this,
                                        errorMessage, Toast.LENGTH_LONG).show();

                                reqestingLocationUpdates = false;
                        }
                    }
                });

        reqestingLocationUpdates = true;
    }







    private void stopLocationUpdates() {


        if (!reqestingLocationUpdates) {
            Log.d("debug", "stopLocationUpdates: " +
                    "updates never requested, no-op.");


            return;
        }

        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reqestingLocationUpdates = false;
                            }
                        });
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        // バッテリー消費を鑑みLocation requestを止める
//        stopLocationUpdates();
//    }
//






}


