package rtdsd.groupwork.sharedjournal.nearbyFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import rtdsd.groupwork.sharedjournal.R;

public class JournalScanningActivity extends AppCompatActivity {

    private final String TAG = "JournalScanningActivity";
    private TextView scanningTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_scanning);
        Log.d(TAG, "onCreate: Scanning activity created");

        scanningTitle = findViewById(R.id.journal_sharing_header_text);
    }



}
