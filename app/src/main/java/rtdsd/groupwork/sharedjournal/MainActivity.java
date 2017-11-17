package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.DialogFragments.AddElementDialogFragment;
import rtdsd.groupwork.sharedjournal.DialogFragments.BaseAppDialogFragment;
import rtdsd.groupwork.sharedjournal.model.RpgJournal;
import rtdsd.groupwork.sharedjournal.nearbyFragment.JournalScanningActivity;
import rtdsd.groupwork.sharedjournal.nearbyFragment.JournalSharingFragment;
import rtdsd.groupwork.sharedjournal.recyclerViewAdapters.JournalsRecyclerAdapter;
import rtdsd.groupwork.sharedjournal.viewmodel.FireBaseJournalCommunication;
import rtdsd.groupwork.sharedjournal.viewmodel.JournalViewModel;

public class MainActivity extends BaseActivity
        implements BaseAppDialogFragment.OnDialogFragmentInteraction {

    private final String TAG = "MainActivity";
    private TextView textView;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private final String ADD_JOURNAL_FRAGMENT_TAG = "addJournalFragment";

    FireBaseJournalCommunication firebaseJournalReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseJournalReference = new FireBaseJournalCommunication();

        recyclerView = findViewById(R.id.mainactivity_recyclerview);
        final JournalsRecyclerAdapter adapter = new JournalsRecyclerAdapter(new ArrayList<RpgJournal>());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new fabOnClickListener());

        JournalViewModel model = ViewModelProviders.of(this).get(JournalViewModel.class);
        model.getData().observe(this, new Observer<ArrayList<RpgJournal>>() {
            @Override
            public void onChanged(@Nullable ArrayList<RpgJournal> journals) {
                if(journals != null){
                    /*for(RpgJournal journal : journals){
                        //journal.getName();
                        adapter.addItem(journal);
                    }*/
                    adapter.setJournals(journals);
                }
                /*if(strings != null){
                    for (RpgJournal journal: strings) {
                        Log.d(TAG, "message: " + journal.getName());
                        textView.setText(journal.getName());
                    }
                }*/
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.scan_journals){
            Log.d(TAG, "onOptionsItemSelected: should open scan journals stuff now");
            // TODO: 11/2/17 add open scan stuff here Niko
            Intent i = new Intent(this, JournalScanningActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class fabOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //create args for the dialog fragment texts
            Bundle args = new Bundle();
            args.putString(BaseAppDialogFragment.DIALOG_TITLE_ARG,
                    getString(R.string.add_journal_dialog_title));
            args.putString(BaseAppDialogFragment.DIALOG_EDITTEXT_HINT_ARG,
                    getString(R.string.add_journal_edittext_hint));

            DialogFragment addJournalFragment = new AddElementDialogFragment();
            addJournalFragment.setArguments(args);
            addJournalFragment.show(getSupportFragmentManager(), ADD_JOURNAL_FRAGMENT_TAG);
        }
    }

    //called when OK is clicked in the set journal name fragment
    @Override
    public void onDialogOkButtonClicked(String editTextContents) {
        Log.d(TAG, "onDialogOkButtonClicked: mainactivity got journal name:" + editTextContents);
        firebaseJournalReference.addJournal(editTextContents);
    }
}
