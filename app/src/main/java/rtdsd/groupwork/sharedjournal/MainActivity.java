package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.model.RpgJournal;
import rtdsd.groupwork.sharedjournal.recyclerViewAdapters.JournalsRecyclerAdapter;
import rtdsd.groupwork.sharedjournal.viewmodel.FireBaseJournalCommunication;
import rtdsd.groupwork.sharedjournal.viewmodel.JournalViewModel;

public class MainActivity extends AppCompatActivity
        implements AddJournalFragment.OnFragmentInteraction{

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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class fabOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            DialogFragment addJournalFragment = new AddJournalFragment();
            addJournalFragment.show(getSupportFragmentManager(), ADD_JOURNAL_FRAGMENT_TAG);

        }
    }

    //called when OK is clicked in the set journal name fragment
    @Override
    public void onOkButtonClicked(String journalName) {
        Log.d(TAG, "onOkButtonClicked: mainactivity got journal name:" + journalName);
        firebaseJournalReference.addJournal(journalName);
    }
}
