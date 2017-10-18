package rtdsd.groupwork.sharedjournal.recyclerViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

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
