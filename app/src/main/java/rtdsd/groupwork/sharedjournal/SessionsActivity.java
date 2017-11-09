package rtdsd.groupwork.sharedjournal;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rtdsd.groupwork.sharedjournal.nearbyFragment.JournalSharingFragment;

public class SessionsActivity extends AppCompatActivity implements SessionsFragment.OnFragmentInteractionListener{

    private final String TAG = "SessionsActivity";
    public static final String EXTRA_JOURNAL_ID = "journalId";
    public static final String EXTRA_JOURNAL_TITLE = "journalTitle";

    private static final String SESSIONS_FRAGMENT_TAG = "sessionsFragment";

    private String journalId;
    private String journalTitle;
    private Button shareJournalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shareJournalButton = findViewById(R.id.journal_share_button);

        journalId = getIntent().getStringExtra(EXTRA_JOURNAL_ID);
        journalTitle = getIntent().getStringExtra(EXTRA_JOURNAL_ID);

        Log.d(TAG, "onCreate: extra journal id got:" + journalId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FabSessionsOnClickListener());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        SessionsFragment sessionsFragment = SessionsFragment.newInstance(journalId, journalTitle);
        transaction.add(R.id.fragmentLayout, sessionsFragment, SESSIONS_FRAGMENT_TAG);
        transaction.commit();


        shareJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment sharingFragment = JournalSharingFragment.newInstance(journalId, journalTitle);
                FragmentTransaction shareButtonTransaction = getSupportFragmentManager().beginTransaction();
                shareButtonTransaction.replace(R.id.fragmentLayout, sharingFragment);
                shareButtonTransaction.addToBackStack(null);
                shareButtonTransaction.commit();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //// TODO: 18.10.2017 implement
    }

    class FabSessionsOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //// TODO: 18.10.2017 implement
            Log.d(TAG, "onClick: fab clicked!");
        }
    }
}
