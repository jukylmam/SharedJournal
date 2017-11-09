package rtdsd.groupwork.sharedjournal;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import rtdsd.groupwork.sharedjournal.DialogFragments.AddElementDialogFragment;
import rtdsd.groupwork.sharedjournal.DialogFragments.BaseAppDialogFragment;

public class SessionsActivity extends AppCompatActivity implements
        SessionsFragment.OnFragmentInteractionListener,
        BaseAppDialogFragment.OnDialogFragmentInteraction {

    private final String TAG = "SessionsActivity";
    public static final String EXTRA_JOURNAL_ID = "journalId";

    private static final String SESSIONS_FRAGMENT_TAG = "sessionsFragment";
    private static final String ADD_SESSION_FRAGMENT_TAG = "addSessionFragmentTag";

    private String journalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        journalId = getIntent().getStringExtra(EXTRA_JOURNAL_ID);

        Log.d(TAG, "onCreate: extra journal id got:" + journalId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FabSessionsOnClickListener());

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        SessionsFragment sessionsFragment = SessionsFragment.newInstance(journalId,"");
        transaction.add(R.id.fragmentLayout, sessionsFragment, SESSIONS_FRAGMENT_TAG);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //// TODO: 18.10.2017 implement
    }

    @Override
    public void onDialogOkButtonClicked(String editTextContents) {
        ((SessionsFragment)getSupportFragmentManager()
                .findFragmentByTag(SESSIONS_FRAGMENT_TAG))
                .userAddingSession(editTextContents);
    }

    class FabSessionsOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            //create args for the dialog fragment texts
            Bundle args = new Bundle();
            args.putString(BaseAppDialogFragment.DIALOG_TITLE_ARG,
                    getString(R.string.add_session_dialog_title));
            args.putString(BaseAppDialogFragment.DIALOG_EDITTEXT_HINT_ARG,
                    getString(R.string.add_session_edittext_hint));

            DialogFragment addJournalFragment = new AddElementDialogFragment();
            addJournalFragment.setArguments(args);
            addJournalFragment.show(getSupportFragmentManager(), ADD_SESSION_FRAGMENT_TAG);
        }
    }
}
