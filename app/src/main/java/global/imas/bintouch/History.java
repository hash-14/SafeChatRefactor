package global.imas.bintouch;

/**
 * Created by bachirhabib on 10/21/2018.
 */

public class History {



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

    public String getKeyCurrent() {
        return keyCurrent;
    }

    public void setKeyCurrent(String keyCurrent) {
        this.keyCurrent = keyCurrent;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public String getNote_msg() {
        return note_msg;
    }

    public void setNote_msg(String note_msg) {
        this.note_msg = note_msg;
    }

    String   from;

    public History(String from, String keyCurrent, String keyUser, String note_msg, String dateTime) {
        this.from = from;
        this.keyCurrent = keyCurrent;
        this.keyUser = keyUser;
        this.note_msg = note_msg;
        this.dateTime = dateTime;
    }
    public History() {

    }


    String  keyCurrent;
    String  keyUser;
    String  note_msg;
    String  dateTime;
}
