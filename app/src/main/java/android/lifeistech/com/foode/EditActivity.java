package android.lifeistech.com.foode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

import io.realm.Realm;


public class EditActivity extends AppCompatActivity {

    public Realm realm;


    EditText editText1;
    EditText editText2;
    EditText editText3;

    String food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);

    }







    public void decision(View v){

        //ランダムで3択の中から一択決定
        Random random = new Random();
        int number;
        number = random.nextInt(3);

        if (number == 0){

           food = editText1.getText().toString();

        }else if (number == 1){

            food = editText2.getText().toString();

        }else if (number == 2){

            food = editText3.getText().toString();

        }

        Intent intent = new Intent(this,MapsActivity_Select.class);
        intent.putExtra("food",food);
        startActivity(intent);
    }
}
