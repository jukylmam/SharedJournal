package rtdsd.groupwork.sharedjournal.recyclerViewAdapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rtdsd.groupwork.sharedjournal.model.Entry;
import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by domuska on 11/9/17.
 */

public class EntriesRecyclerAdapter extends RecyclerView.Adapter<JournalsRecyclerAdapter.ViewHolder> {

    private final static String TAG = "EntriesRecyclerAdapter";
    private final static String DIFF_ENTRY_BODY = "diffEntryBody";
    private final static String DIFF_ENTRY_TITLE = "diffEntryTitle";

    private ArrayList<Entry> entries = new ArrayList<>();

    @Override
    public void onBindViewHolder(JournalsRecyclerAdapter.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public JournalsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(JournalsRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


    public void setEntries(final ArrayList<Entry> newEntries){

        Log.d(TAG, "setSessions: newSessions size: " + newEntries.size());
        Log.d(TAG, "setSessions: sessions size:" + entries.size());
        /*for(Entry e: entries){
            Log.d(TAG, "setSessions: session title: " + e.getTitle());
        }*/

        //call diffUtil to figure out differences between new list and current list
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return entries.size();
            }

            @Override
            public int getNewListSize() {
                return newEntries.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                String oldSessionId = entries.get(oldItemPosition).getId();
                String newSessionId = newEntries.get(newItemPosition).getId();
                return oldSessionId.equals(newSessionId);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Entry oldItem = entries.get(oldItemPosition);
                Entry newItem = newEntries.get(newItemPosition);
                /*Log.d(TAG, "areContentsTheSame: old session title: " + oldItem.getTitle() +
                        " new session title: " + newItem.getTitle());*/
                return oldItem.areContentsSame(newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                Log.d(TAG, "getChangePayload: called");
                Entry oldEntry = entries.get(oldItemPosition);
                Entry newEntry = newEntries.get(newItemPosition);
                Bundle diffBundle = new Bundle();

                //check what has changed in the entry
                if(!oldEntry.getEntryBody().equals(newEntry.getEntryBody())){
                    diffBundle.putString(DIFF_ENTRY_BODY, newEntry.getEntryBody());
                }

                if(!oldEntry.getEntryTitle().equals(newEntry.getEntryTitle())){
                    diffBundle.putString(DIFF_ENTRY_TITLE, newEntry.getEntryTitle());
                }


                if(diffBundle.size() == 0)
                    return null;
                else
                    return diffBundle;
            }
        });

        //just add all elements, dispatchUpdates will take care of notifying adapter of changes
        this.entries.clear();

        // we need to create a real copy (instead of shallow clone) of Sessions
        // so that when SessionsLiveData changes its' copies, here we can see the diff and can act
        // accordingly
        for(Entry entry: newEntries){
            entries.add(new Entry(entry));
        }
        result.dispatchUpdatesTo(this);
    }
}
