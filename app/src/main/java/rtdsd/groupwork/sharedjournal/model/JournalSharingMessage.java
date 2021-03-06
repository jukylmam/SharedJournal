package rtdsd.groupwork.sharedjournal.model;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;

import java.nio.charset.Charset;


public class JournalSharingMessage {

    private static final Gson gson = new Gson();

    private final String uuid;
    private final String journalIdentifier;
    private final String messageJournalTitle;

    public static Message newNearbyMessage(String newMessageIdentifier, String newMessageContent, String newJournalTitle){
        JournalSharingMessage jSharingMessage = new JournalSharingMessage(newMessageIdentifier, newMessageContent, newJournalTitle);
        return new Message(gson.toJson(jSharingMessage).getBytes(Charset.forName("UTF-8")));
    }

    private JournalSharingMessage(String messageId, String journalIdentifier, String journalTitle){
        this.uuid = messageId;
        this.journalIdentifier = journalIdentifier;
        this.messageJournalTitle = journalTitle;
    }

    public String getContent() {
        return journalIdentifier;
    }
    public String getTitle() {
        return messageJournalTitle;
    }

    public static JournalSharingMessage fromNearbyMessage(Message message) {
        String nearbyMessageString = new String(message.getContent()).trim();
        return gson.fromJson(
                (new String(nearbyMessageString.getBytes(Charset.forName("UTF-8")))),
                JournalSharingMessage.class);
    }

    public String getJournalTitle() {
        return messageJournalTitle;
    }

    public String getJournalIdentifier() {
        return journalIdentifier;
    }

}
