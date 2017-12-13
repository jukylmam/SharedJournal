package rtdsd.groupwork.sharedjournal.recyclerViewAdapters;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rtdsd.groupwork.sharedjournal.R;
import rtdsd.groupwork.sharedjournal.SessionsActivity;
import rtdsd.groupwork.sharedjournal.model.RpgJournal;

/**
 * Created by OMISTAJA on 17.10.2017.
 */

public class JournalsRecyclerAdapter extends RecyclerView.Adapter<JournalsRecyclerAdapter.ViewHolder> {

    private final String TAG = "JournalsRecyclerAdapter";
    private ArrayList<RpgJournal> journals;
    private final String DIFF_NAME = "diffName";

    public JournalsRecyclerAdapter(ArrayList<RpgJournal> journals){
        this.journals = journals;
    }

    public void emptyList(){
        journals.clear();
        notifyDataSetChanged();
    }

    public void setJournals(final ArrayList<RpgJournal> newJournals) {

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return journals.size();
            }

            @Override
            public int getNewListSize() {
                return newJournals.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                return journals.get(oldItemPosition).getId().equals(
                        newJournals.get(newItemPosition).getId()
                );
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return journals.get(oldItemPosition).getName().equals(
                        newJournals.get(newItemPosition).getName()
                );
                //// TODO: 10/27/17 if we need, add here other content from a journal
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                RpgJournal oldJournal = journals.get(oldItemPosition);
                RpgJournal newJournal = newJournals.get(newItemPosition);
                Bundle diffBundle = new Bundle();

                //see if the journal's name has changed
                if(!oldJournal.getName().equals(newJournal.getName())){
                    diffBundle.putString(DIFF_NAME, newJournal.getName());
                }

                //// TODO: 10/27/17 if we need, add here other content from a journal

                if(diffBundle.size() == 0)
                    return null;
                else
                    return diffBundle;
            }
        });

        this.journals.clear();
        //add copies of the journal entries
        for(RpgJournal journal : newJournals){
            journals.add(new RpgJournal(journal));
        }
        result.dispatchUpdatesTo(this);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //viewholder is created for a row
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.mainactivity_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {

        final RpgJournal journal = journals.get(position);

        if(!payloads.isEmpty()){
            Bundle bundle = (Bundle) payloads.get(0);

            for(String key : bundle.keySet()){
                //sorta silly for loop since there can only be one value for now,
                //but this might change
                if(key.equals(DIFF_NAME))
                    holder.journalNameText.setText(bundle.getString(DIFF_NAME));
            }
        }
        else{
            holder.journalNameText.setText(journal.getName());
        }

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "row with text: " + journal.getName() + " clicked");
                Intent i = new Intent(view.getContext(), SessionsActivity.class);
                i.putExtra(SessionsActivity.EXTRA_JOURNAL_ID, journal.getId());
                i.putExtra(SessionsActivity.EXTRA_JOURNAL_TITLE, journal.getName());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //data is bound to the viewholder
        final RpgJournal journal = journals.get(position);
        final String journalName = journal.getName();

        Log.d(TAG, "onBindViewHolder: adding new journal entry with name " + journalName);
        Log.d(TAG, "onBindViewHolder: journals size: " + journals.size());
        holder.journalNameText.setText(journalName);
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "row with text: " + journalName + " clicked");
                Intent i = new Intent(view.getContext(), SessionsActivity.class);
                i.putExtra(SessionsActivity.EXTRA_JOURNAL_ID, journal.getId());
                view.getContext().startActivity(i);

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
            if(journal.getId().equals(journals.get(i).getId()))
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
