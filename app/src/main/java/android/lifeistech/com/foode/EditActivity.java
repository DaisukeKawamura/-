package android.lifeistech.com.foode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import io.realm.Realm;


public class EditActivity extends AppCompatActivity {

    public Realm realm;


    EditText editText1;
    EditText editText2;
    EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        realm = Realm.getDefaultInstance();


        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);


    }


    public void save(final String content1, final String content2, final String content3){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Memo memo = realm.createObject(Memo.class);
                memo.content1 = content1;
                memo.content2 = content2;
                memo.content3 = content3;
            }
        });
    }



    public void decision(View v){
        Intent intent = new Intent(this,MapsActivity_Select.class);
        startActivity(intent);

        String content1 = editText1.getText().toString();
        String content2 = editText2.getText().toString();
        String content3 = editText3.getText().toString();

//        output(content1, content2, content3);

        save(content1, content2, content3);

        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        realm.close();
    }

    public void output(String content1, String content2, String content3){

        Memo memo = new Memo();

        memo.content1 = content1;
        memo.content2 = content2;
        memo.content3 = content3;

        Log.d("Memo", memo.content1);
        Log.d("Memo", memo.content2);
        Log.d("Memo", memo.content3);
    }
}
