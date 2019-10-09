package global.imas.bintouch;

/**
 * Created by bachirhabib on 9/11/2018.
 */
public class State {
    public State(String state, String date) {
        this.state = state;
        this.date = date;
    }
    public State() {

    }

    String state;
    String date;
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
