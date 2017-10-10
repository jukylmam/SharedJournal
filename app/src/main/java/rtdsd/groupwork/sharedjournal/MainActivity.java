package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import rtdsd.groupwork.sharedjournal.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        JournalViewModel model = ViewModelProviders.of(this).get(JournalViewModel.class);
        model.getData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if(strings != null){
                    for (String string: strings) {
                        Log.d(TAG, "message: " + string);
                        textView.setText(string);
                    }
                }
            }
        });
    }


}
