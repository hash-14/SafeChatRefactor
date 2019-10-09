package global.imas.bintouch;

/**
 * Created by bachirhabib on 11/20/2018.
 */

public class Blocked {
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String key;

    public Blocked(String key, String name) {
        this.key = key;
        this.name = name;
    }
    public Blocked() {

    }

    String name;
}
