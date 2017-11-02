package rtdsd.groupwork.sharedjournal.model;

import com.google.android.gms.nearby.messages.Message;
import com.google.gson.Gson;

import java.nio.charset.Charset;


public class JournalSharingMessage {

    private static final Gson gson = new Gson();

    private final String uuid;
    private final String messageContent;

    public static Message newNearbyMessage(String newMessageIdentifier, String newMessageContent){
        JournalSharingMessage jSharingMessage = new JournalSharingMessage(newMessageIdentifier, newMessageContent);
        return new Message(gson.toJson(jSharingMessage).getBytes(Charset.forName("UTF-8")));
    }

    private JournalSharingMessage(String messageId, String messageContent){
        this.uuid = messageId;
        this.messageContent = messageContent;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
