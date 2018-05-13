package android.lifeistech.com.foode;


import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class StartActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSION = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }


    public void free(View v) {

        if (Build.VERSION.SDK_INT >= 23) {
            MapsActivity();
          checkPermission();
        } else {
            MapsActivity();
        }


    }

    public void constraint(View v) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);

    }

    //位置情報の確認
    public void checkPermission() {
        //すでに確認している
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            MapsActivity();
        }
        //拒否していた場合
        else {
            requestLocationPermission();
        }

    }

    //許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSION);

        }else {
            Toast toast =Toast.makeText(this,
                    "許可されないとアプリを実行できません",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                    @NonNull String[] permissions,
                                    @NonNull int[] grantResults){
        if (requestCode == REQUEST_PERMISSION){
            //使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                MapsActivity();
            }else {
                //それでも拒否された時の対応
                Toast toast = Toast.makeText(this,
                        "これ以上は何もできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void MapsActivity(){
        Intent intent = new Intent(this, MapsActivity_10.class);
        startActivity(intent);
    }
}
