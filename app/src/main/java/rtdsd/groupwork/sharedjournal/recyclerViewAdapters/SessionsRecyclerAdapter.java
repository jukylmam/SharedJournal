package rtdsd.groupwork.sharedjournal.recyclerViewAdapters;

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
import java.util.Collection;
import java.util.List;

import rtdsd.groupwork.sharedjournal.R;
import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by OMISTAJA on 18.10.2017.
 */

public class SessionsRecyclerAdapter extends RecyclerView.Adapter<SessionsRecyclerAdapter.ViewHolder>{

    private ArrayList<Session> sessions;
    private final String TAG = "SessionsRecyclerAdapter";
    private final String DIFF_TITLE = "titleDiff";
    private final String DIFF_START = "startDiff";
    private final String DIFF_END = "endDiff";

    public SessionsRecyclerAdapter(){
        sessions = new ArrayList<>();
    }

    public void addSession(Session session){

        sessions.add(session);
        notifyItemInserted(sessions.size()-1);
    }

    public void removeSession(){
        // TODO: 18.10.2017 implement
    }

    public void setSessions(final ArrayList<Session> newSessions){
        /*
        This is called when new sessions are added and then all sessions are supplied,
        this is a limitation of the LiveData used with Firebase, we only get the whole list of
        Sessions when the setData in SessionsLiveData is called, so we have to iterate
        the list in here to get the actual diff between the list we have and the list we get from
        liveData. Not optimal but this should not be a big problem in app of this scale. */

        Log.d(TAG, "setSessions: newSessions size: " + newSessions.size());
        Log.d(TAG, "setSessions: sessions size:" + sessions.size());
        for(Session s : sessions){
            Log.d(TAG, "setSessions: session title: " + s.getTitle());
        }

        //call diffUtil to figure out differences between new list and current list
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return sessions.size();
            }

            @Override
            public int getNewListSize() {
                return newSessions.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                String oldSessionId = sessions.get(oldItemPosition).getId();
                String newSessionId = newSessions.get(newItemPosition).getId();
                return oldSessionId.equals(newSessionId);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Session oldItem = sessions.get(oldItemPosition);
                Session newItem = newSessions.get(newItemPosition);
                Log.d(TAG, "areContentsTheSame: old session title: " + oldItem.getTitle() +
                        " new session title: " + newItem.getTitle());
                return oldItem.areContentsSame(newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                Log.d(TAG, "getChangePayload: called");
                Session oldSession = sessions.get(oldItemPosition);
                Session newSession = newSessions.get(newItemPosition);
                Bundle diffBundle = new Bundle();
                //see what has changed
                if(!oldSession.getTitle().equals(newSession.getTitle())){
                    diffBundle.putString(DIFF_TITLE, newSession.getTitle());
                }
                // TODO: 10/23/17 add here other fields, startedOn and endedOn

                if(diffBundle.size() == 0)
                    return null;
                else
                    return diffBundle;
            }
        });

        //just add all elements, dispatchUpdates will take care of notifying adapter of changes
        this.sessions.clear();

        // we need to create a real copy (instead of shallow clone) of Sessions
        // so that when SessionsLiveData changes its' copies, here we can see the diff and can act
        // accordingly
        for(Session s : newSessions){
            sessions.add(new Session(s));
        }
        result.dispatchUpdatesTo(this);
    }

    @Override
    public SessionsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.sessions_list_row, parent, false);

        return new SessionsRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Session session = sessions.get(position);
        holder.sessionNameText.setText(session.getTitle());
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: row clicked: " + session.getTitle());
            }
        });
    }

    @Override
        public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        Log.d(TAG, "onBindViewHolder: called with position " + position);
            //called when a Session has changed and this is noticed (in setSession, in diffUtils)
        if(!payloads.isEmpty()) {
            Log.d(TAG, "onBindViewHolder: payloads not empty");
            final Session session = sessions.get(position);

            Bundle bundle = (Bundle) payloads.get(0);
            for(String key : bundle.keySet()){
                if(key.equals(DIFF_TITLE)){
                    Log.d(TAG, "onBindViewHolder setting title: " + bundle.getString(key));
                    holder.sessionNameText.setText(bundle.getString(key));
                }
                // TODO: 10/23/17 add here other fields: startedOn and endedOn
            }
        }
        else{
            final Session session = sessions.get(position);
            holder.sessionNameText.setText(session.getTitle());
            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: row clicked: " + session.getTitle());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView sessionNameText;
        private View row;

        public ViewHolder(View itemView) {
            super(itemView);
            row = itemView;
            sessionNameText = itemView.findViewById(R.id.session_name);
        }
    }
}
