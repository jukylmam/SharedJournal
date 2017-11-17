package rtdsd.groupwork.sharedjournal.model;

/**
 * Created by domuska on 11/9/17.
 */

public class Entry {

    public Entry(Entry copyEntry){
        this.id = copyEntry.getId();
        this.entryBody = copyEntry.getEntryBody();
        this.entryTitle = copyEntry.getEntryTitle();
    }

    public Entry(){
        //empty constructor for firebase
    }

    private String entryTitle, entryBody, id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryBody() {
        return entryBody;
    }

    public void setEntryBody(String entryBody) {
        this.entryBody = entryBody;
    }

    public boolean areContentsSame(Entry newItem) {
        return this.id.equals(newItem.id)
                && this.entryBody.equals(newItem.entryBody)
                && this.entryTitle.equals(newItem.entryTitle);
    }
}
