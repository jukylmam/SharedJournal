package rtdsd.groupwork.sharedjournal.model;

/**
 * Created by domuska on 10/27/17.
 */

/*
package models;
public class Journal
{
	UUID id
	List<UUID> entries
	List<UUID> sessions
	List<UUID> linkedUsers
}

public class Session
{
	UUID id
	List<UUID> listEntries
	String title
	DateTime startedOn
	DateTime endedOn
}

public class Entry
{
	UUID id
	UUID sessionId
	DateTime createdOn
	DateTime modifiedOn
	String content
}

public class User
{
	UUID id
	List<UUID> linkedJournals
}
 */

public class JournalEntry {

    String id, sessionId, createdOn, modifiedOn, content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
