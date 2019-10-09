package global.imas.bintouch;

/**
 * Created by bachirhabib on 9/11/2018.
 */

public class AutoReplyForMe {



    public String getAuto_reply_msg() {
        return auto_reply_msg;
    }

    public void setAuto_reply_msg(String auto_reply_msg) {
        this.auto_reply_msg = auto_reply_msg;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate_from_d() {
        return date_from_d;
    }

    public void setDate_from_d(String date_from_d) {
        this.date_from_d = date_from_d;
    }

    public String getDate_from_m() {
        return date_from_m;
    }

    public void setDate_from_m(String date_from_m) {
        this.date_from_m = date_from_m;
    }

    public String getDate_from_y() {
        return date_from_y;
    }

    public void setDate_from_y(String date_from_y) {
        this.date_from_y = date_from_y;
    }

    public String getDate_to_d() {
        return date_to_d;
    }

    public void setDate_to_d(String date_to_d) {
        this.date_to_d = date_to_d;
    }

    public String getDate_to_m() {
        return date_to_m;
    }

    public void setDate_to_m(String date_to_m) {
        this.date_to_m = date_to_m;
    }

    public String getDate_to_y() {
        return date_to_y;
    }

    public void setDate_to_y(String date_to_y) {
        this.date_to_y = date_to_y;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getList_keys() {
        return list_keys;
    }

    public void setList_keys(String list_keys) {
        this.list_keys = list_keys;
    }

    public String getList_names() {
        return list_names;
    }

    public void setList_names(String list_names) {
        this.list_names = list_names;
    }

    public String getTime_from_h() {
        return time_from_h;
    }

    public void setTime_from_h(String time_from_h) {
        this.time_from_h = time_from_h;
    }

    public String getTime_from_m() {
        return time_from_m;
    }

    public void setTime_from_m(String time_from_m) {
        this.time_from_m = time_from_m;
    }

    public String getTime_to_h() {
        return time_to_h;
    }

    public void setTime_to_h(String time_to_h) {
        this.time_to_h = time_to_h;
    }

    public String getTime_to_m() {
        return time_to_m;
    }

    public void setTime_to_m(String time_to_m) {
        this.time_to_m = time_to_m;
    }

    String  auto_reply_msg;
    String  dateTime;
    String  date_from_d;
    String  date_from_m;
    String  date_from_y;

    public AutoReplyForMe(String auto_reply_msg, String dateTime, String date_from_d, String date_from_m, String date_from_y, String date_to_d, String date_to_m, String date_to_y, String from, String list_keys, String list_names, String time_from_h, String time_from_m, String time_to_h, String time_to_m) {
        this.auto_reply_msg = auto_reply_msg;
        this.dateTime = dateTime;
        this.date_from_d = date_from_d;
        this.date_from_m = date_from_m;
        this.date_from_y = date_from_y;
        this.date_to_d = date_to_d;
        this.date_to_m = date_to_m;
        this.date_to_y = date_to_y;
        this.from = from;
        this.list_keys = list_keys;
        this.list_names = list_names;
        this.time_from_h = time_from_h;
        this.time_from_m = time_from_m;
        this.time_to_h = time_to_h;
        this.time_to_m = time_to_m;
    }

    public AutoReplyForMe(){
    }

    String  date_to_d;
    String  date_to_m;
    String  date_to_y;
 private   String  from;
    String  list_keys;
    String  list_names;
    String  time_from_h;
    String  time_from_m;
    String  time_to_h;
   String time_to_m;


}
