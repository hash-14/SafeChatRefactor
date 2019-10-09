package global.imas.bintouch;

/**
 * Created by bachirhabib on 7/27/2017.
 */
public class Books {
    // Instance variables
    private String score;

    // Setters
    public void set_score(String a) { score = a; }
    // Getters
    public String get_score() { return score; }
    // Constructors
    public Books() { score ="Search";}
    public Books(String a)
    {
        score = a;
    }
}
