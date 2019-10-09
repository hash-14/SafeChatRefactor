package global.imas.bintouch;

/**
 * Created by bachirhabib on 9/4/2018.
 */
public class Notes {

    public Notes(String dateTime, String note_msg, String sent_received_seen, String from) {
        this.dateTime = dateTime;
        this.note_msg = note_msg;
        this.sent_received_seen = sent_received_seen;
        this.from = from;
    }
    public Notes() {

    }

    String  dateTime;
    String note_msg;

    String sent_received_seen;
    String from;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getNote_msg() {
        return note_msg;
    }

    public void setNote_msg(String note_msg) {
        this.note_msg = note_msg;
    }

    public String getSent_received_seen() {
        return sent_received_seen;
    }

    public void setSent_received_seen(String sent_received_seen) {
        this.sent_received_seen = sent_received_seen;
    }



}
