package rtdsd.groupwork.sharedjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import rtdsd.groupwork.sharedjournal.R;
import rtdsd.groupwork.sharedjournal.model.RpgJournal;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private TextView textView;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = findViewById(R.id.mainactivity_recyclerview);
        final MainRecyclerAdapter adapter = new MainRecyclerAdapter(new ArrayList<RpgJournal>());
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
                    for(RpgJournal journal : journals){
                        //journal.getName();
                        adapter.addItem(journal);
                    }
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

    class fabOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //// TODO: 18.10.2017 juhana add here the stuff needed to show a popup thing for user
        }
    }
}
