package android.lifeistech.com.foode;


import android.app.Application;
import io.realm.Realm;

/**
 * Created by kawamuradaisuke on 2018/05/11.
 */

public class MemoApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(getApplicationContext());
    }
}
