package rtdsd.groupwork.sharedjournal.model;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;

import java.nio.charset.Charset;


public class JournalSharingMessage {

    private static final Gson gson = new Gson();

    private final String uuid;
    private final String messageContent;
    private final String messageJournalTitle;

    public static Message newNearbyMessage(String newMessageIdentifier, String newMessageContent, String newJournalTitle){
        JournalSharingMessage jSharingMessage = new JournalSharingMessage(newMessageIdentifier, newMessageContent, newJournalTitle);
        return new Message(gson.toJson(jSharingMessage).getBytes(Charset.forName("UTF-8")));
    }

    private JournalSharingMessage(String messageId, String messageContent, String journalTitle){
        this.uuid = messageId;
        this.messageContent = messageContent;
        this.messageJournalTitle = journalTitle;
    }

    public String getContent() {
        return messageContent;
    }
    public String getTitle() {
        return messageJournalTitle;
    }

}
