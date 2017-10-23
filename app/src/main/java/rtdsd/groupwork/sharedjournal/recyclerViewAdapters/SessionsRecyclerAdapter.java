package rtdsd.groupwork.sharedjournal.recyclerViewAdapters;

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

import rtdsd.groupwork.sharedjournal.R;
import rtdsd.groupwork.sharedjournal.model.Session;

/**
 * Created by OMISTAJA on 18.10.2017.
 */

public class SessionsRecyclerAdapter extends RecyclerView.Adapter<SessionsRecyclerAdapter.ViewHolder>{

    private ArrayList<Session> sessions;
    private final String TAG = "SessionsRecyclerAdapter";

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

                //return sessions.get(oldItemPosition).getId().equals(
                //        newSessions.get(newItemPosition).getId());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Session oldItem = sessions.get(oldItemPosition);
                Session newItem = newSessions.get(newItemPosition);
                return oldItem.areContentsSame(newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return super.getChangePayload(oldItemPosition, newItemPosition);
            }
        });

        //just add all elements, dispatchUpdates will take care of notifying adapter of changes
        this.sessions.clear();
        this.sessions.addAll(newSessions);
        result.dispatchUpdatesTo(this);

        //first add the lists that are missing
        /*for(Session session : newSessions){
            if(!sessions.contains(session)){
                sessions.add(session);
                notifyItemInserted(sessions.size()-1);
            }
        }

        //then remove the sessions that are not in the newSessions list
        for(Session session : sessions){
            if(!newSessions.contains(session)){
                int removableItemIndex = sessions.indexOf(session);
                sessions.remove(session);
                notifyItemRemoved(removableItemIndex);
            }
        }*/

        //an element was updated, whole list needs to be re-drawn (we dont get any indication from
        //liveData which element has changed)

        //if adding elements
        /*Collection<Session> sourceList = sessions;
        newSessions.removeAll(sourceList);
        Log.d(TAG, "elements left in the newSessions: " + newSessions.size());
        sessions.addAll(newSessions);

        //if removing elements...
        //get the diff between lists
        sourceList.removeAll(newSessions);
        //remove the diff from adapter's list
        sessions.removeAll(sourceList);*/


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
