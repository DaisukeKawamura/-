package android.lifeistech.com.foode;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class StartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    public void free(View v){
        Intent intent = new Intent(this,MapsActivity_10.class);
        startActivity(intent);
    }

    public void constraint(View v){
        Intent intent = new Intent(this,EditActivity.class);
        startActivity(intent);

    }
}
