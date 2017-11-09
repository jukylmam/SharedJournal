package rtdsd.groupwork.sharedjournal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Application's base activity that should things common for all
 * activities in the app
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JodaTimeAndroid.init(this);
    }
}
