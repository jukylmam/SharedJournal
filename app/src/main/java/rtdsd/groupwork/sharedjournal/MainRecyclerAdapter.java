package rtdsd.groupwork.sharedjournal;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import rtdsd.groupwork.sharedjournal.model.RpgJournal;

/**
 * Created by OMISTAJA on 17.10.2017.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

    private final String TAG = "MainRecyclerAdapter";
    private ArrayList<RpgJournal> journals;


    public MainRecyclerAdapter(ArrayList<RpgJournal> journals){
        this.journals = journals;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //viewholder is created for a row
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.mainactivity_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //data is bound to the viewholder
        final String journalName = journals.get(position).getName();

        Log.d(TAG, "onBindViewHolder: adding new journal entry with name " + journalName);
        Log.d(TAG, "onBindViewHolder: journals size: " + journals.size());
        holder.journalNameText.setText(journalName);
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "row with text: " + journalName + " clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return journals.size();
    }

    public void addItem(RpgJournal journal){
        // TODO: 17.10.2017 make a more reasonable way to check if journal already in journals
        boolean journalExists = false;
        for(int i = 0; i < journals.size(); i++){
            if(journal.getId() == journals.get(i).getId())
                journalExists = true;
        }

        if(!journalExists){
            journals.add(journal);
            this.notifyItemInserted(journals.size()-1);
        }
    }

    public void removeItem(RpgJournal journal){
        //todo implement
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public View rowLayout;
        public TextView journalNameText;

        public ViewHolder(View v){
            super(v);
            rowLayout = v;
            journalNameText = v.findViewById(R.id.journalNameTextView);
        }

    }

}
