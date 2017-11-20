package rtdsd.groupwork.sharedjournal.recyclerViewAdapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rtdsd.groupwork.sharedjournal.EntriesFragment;
import rtdsd.groupwork.sharedjournal.R;
import rtdsd.groupwork.sharedjournal.model.Entry;
import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by domuska on 11/9/17.
 */

public class EntriesRecyclerAdapter extends RecyclerView.Adapter<EntriesRecyclerAdapter.ViewHolder> {

    private final static String TAG = "EntriesRecyclerAdapter";
    private final static String DIFF_ENTRY_BODY = "diffEntryBody";
    private final static String DIFF_ENTRY_TITLE = "diffEntryTitle";

    private ArrayList<Entry> entries = new ArrayList<>();

    private final EntriesFragment hostFragment;


    public EntriesRecyclerAdapter(EntriesFragment host){
        hostFragment = host;
    }

    @Override
    public void onBindViewHolder(EntriesRecyclerAdapter.ViewHolder holder,
                                 int position, List<Object> payloads) {


        if(!payloads.isEmpty()){
            //get the bundle, it's the first element in payloads, this from official docs
            Bundle bundle = (Bundle) payloads.get(0);

            //iterate through the changed things in the bundle
            for(String key : bundle.keySet()){
                if(key.equals(DIFF_ENTRY_BODY)){
                    holder.bodyView.setText(bundle.getString(key));
                }
                if(key.equals(DIFF_ENTRY_TITLE)){
                    holder.titleView.setText(bundle.getString(key));
                }
            }
        }
        else{
            final Entry entry = entries.get(position);
            holder.titleView.setText(entry.getEntryTitle());
            holder.bodyView.setText(entry.getEntryBody());
            holder.rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: entry clicked! ID: " + entry.getId());
                }
            });
            holder.rowView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    hostFragment.elementLongClicked(entry.getId());
                    return true;
                }
            });
        }
    }

    @Override
    public EntriesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //inflate the layout that shows a single entry, passed to ViewHolder constructor
        View v = inflater.inflate(R.layout.entries_list_row, parent, false);

        return new EntriesRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EntriesRecyclerAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: onBindViewHolder called");
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

                //check what has changed in the entry, ID should never change on an element
                //so changes to it are not handled
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

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titleView, bodyView;
        private View rowView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.entry_title);
            bodyView = itemView.findViewById(R.id.entry_body);
            rowView = itemView;
        }

    }


}
