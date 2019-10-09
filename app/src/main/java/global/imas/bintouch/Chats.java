package global.imas.bintouch;

/**
 * Created by bachirhabib on 9/3/2018.
 */
public class Chats {
  String  dateTime;
    String   from;
    String   msg_or_url;
    String   msg_type;
    String   sent_received_seen;

    String   from_orignal_id;
    String   from_orignal_key;


    public String getFrom_orignal_id() {
        return from_orignal_id;
    }

    public void setFrom_orignal_id(String from_orignal_id) {
        this.from_orignal_id = from_orignal_id;
    }

    public String getFrom_orignal_key() {
        return from_orignal_key;
    }

    public void setFrom_orignal_key(String from_orignal_key) {
        this.from_orignal_key = from_orignal_key;
    }




    public Chats(String dateTime, String from, String msg_or_url, String msg_type, String sent_received_seen, String from_orignal_id, String from_orignal_key) {
        this.dateTime = dateTime;
        this.from = from;
        this.msg_or_url = msg_or_url;
        this.msg_type = msg_type;
        this.sent_received_seen = sent_received_seen;
        this.from_orignal_id = from_orignal_id;
        this.from_orignal_key = from_orignal_key;
    }
    public Chats() {

    }




    public String getdateTime() {
        return dateTime;
    }

    public void setdateTime(String s) {
        dateTime = s;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMsg_or_url() {
        return msg_or_url;
    }

    public void setMsg_or_url(String msg_or_url) {
        this.msg_or_url = msg_or_url;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getSent_received_seen() {
        return sent_received_seen;
    }

    public void setSent_received_seen(String sent_received_seen) {
        this.sent_received_seen = sent_received_seen;
    }



}
