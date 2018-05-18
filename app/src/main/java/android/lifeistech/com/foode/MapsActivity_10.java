package android.lifeistech.com.foode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.location.LocationManager;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.lifeistech.com.foode.Result;


public class MapsActivity_10 extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {


    private Retrofit mRetrofit;
    private PlaceAPIService mService;

    //FusedLocationProvider API
    private FusedLocationProviderClient fusedLocationClient;

    //Location Setting API
    private SettingsClient settingsClient;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Location location;
    LocationManager locationManager;

    private String lastUpDateTime;
    private Boolean reqestingLocationUpdates;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private int priority = 0;
    private double lat,lng;
    private LatLng latlng;

    private double[] latitude;
    private double[] longtitude;
    private String[] nameR;
    private Result result;
    String loc = null;

    private final String API_KEY = "AIzaSyDonRgMSNGSG_1dF2YehCscx2eHRYp5JZQ";


    private GoogleMap mMap;
    TextView textView;


    String[] foodArray;
    String food;

    List<Result> results;


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
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        priority = 0;

        foodArray =  new String[]{"オムライス","うどん","カレー","焼肉","鍋","ラーメン","海鮮","お好み焼き","串カツ","とんかつ","寿司"};

        //「位置情報関連のメソッド」
        //現在地の情報を求めるリクエストを渡して、結果が帰ってくる設定のメソッドの呼び出し
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();


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

        mMap.setOnMarkerClickListener(this);
    }


    public void search(View v){

        startLocationUpdates();


        stopLocationUpdates();

        searchMap("34.6937378, 135.5021651");


        List<Result> results = new ArrayList<>();
    }


    public void random(View v) {
        Random random = new Random();
        int number;
        number = random.nextInt(11);

        food = foodArray[number];
        textView.setText(food);
    }

    public void back(View v) {
        finish();
    }


    public void searchMap(String argloc) {

        Call<APIResponse> mResultCall = mService.requestPlaces(argloc,750, "restaurant", food ,API_KEY);

        mResultCall.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {


                   // レスポンスからResultのリストを取得
                results = response.body().getResults();

                Log.e("TAG",results.toString());

                //for文にNullPointerExceptionのエラー出る
                for (Result r : results) {
                    Log.d("name", r.getName() + "\n");
                    Log.d("geometry", r.getGeometry() + "\n");
                    Log.d("rating", r.getRating() + "\n");
                    Log.d("reference", r.getReference() + "\n");
                    Log.d("id", r.getId() + "\n");
                    Log.d("icon", r.getIcon() + "\n");
                    Log.d("vicinity", r.getVicinity() + "\n");
                }

                for (int i = 0; i<results.size(); i++){


                    latlng = new LatLng(
                            results.get(i).geometry.location.getLat(),
                            results.get(i).geometry.location.getLng());

                    Log.d("lat", String.valueOf(results.get(i).geometry.location.getLat()));
                    Log.d("lng", String.valueOf(results.get(i).geometry.location.getLng()));


                    double rat = results.get(i).rating;

                    Marker maker = null;

                    LatLng osaka = new LatLng(34.6937378, 135.5021651);

                    if (rat <= 1.0){

                       maker = mMap.addMarker(new MarkerOptions().position(latlng).title(results.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(osaka));

                    }else if (1 < rat && rat <=2 ){

                        maker =  mMap.addMarker(new MarkerOptions().position(latlng).title(results.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(osaka));

                    }else if (2 < rat && rat <=3){

                        maker =  mMap.addMarker(new MarkerOptions().position(latlng).title(results.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(osaka));

                    }else if (3 < rat && rat <=4){

                        maker =  mMap.addMarker(new MarkerOptions().position(latlng).title(results.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(osaka));

                    }else if (rat >4){

                        maker =  mMap.addMarker(new MarkerOptions().position(latlng).title(results.get(i).getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(osaka));

                    }
                    maker.setTag(i);
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        int clickCount = (int) marker.getTag();
        String vicinity = results.get(clickCount).getVicinity();
        String place = "geo:0,0?q=" + vicinity;
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(place));
        startActivity(intent);

        return false;
    }



    //locationのコールバック値を受け取る
    public void createLocationCallback(){
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
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

            Log.d("location", String.valueOf(location));


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

            for (int i = 0; i < fusedName.length; i++) {
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
            Log.d("loc", loc);
        }
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

    // FusedLocationApiによるlocation updatesをリクエスト
    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this,
                        new OnSuccessListener<LocationSettingsResponse>() {
                            @Override
                            public void onSuccess(
                                    LocationSettingsResponse locationSettingsResponse) {
                                Log.i("debug", "All location settings are satisfied.");

                                // パーミッションの確認
                                if (ActivityCompat.checkSelfPermission(
                                        MapsActivity_10.this,
                                        android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                                        PackageManager.PERMISSION_GRANTED
                                        && ActivityCompat.checkSelfPermission(
                                        MapsActivity_10.this,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                        PackageManager.PERMISSION_GRANTED) {

                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                fusedLocationClient.requestLocationUpdates(
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

        fusedLocationClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reqestingLocationUpdates = false;
                            }
                        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // バッテリー消費を鑑みLocation requestを止める
        stopLocationUpdates();
    }



}

