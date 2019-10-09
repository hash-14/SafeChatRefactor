package global.imas.bintouch;

/**
 * Created by bachirhabib on 9/18/2018.
 */
public class Catt {
    public String getUnder_Cat_level1() {
        return Under_Cat_level1;
    }

    public void setUnder_Cat_level1(String under_Cat_level1) {
        Under_Cat_level1 = under_Cat_level1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Catt() {

    }


    String Under_Cat_level1;

    public Catt(String under_Cat_level1, String under_Cat_level2, String name) {
        Under_Cat_level1 = under_Cat_level1;
        Under_Cat_level2 = under_Cat_level2;
        this.name = name;
    }

    String Under_Cat_level2;
    String name;

    public String getUnder_Cat_level2() {
        return Under_Cat_level2;
    }

    public void setUnder_Cat_level2(String under_Cat_level2) {
        Under_Cat_level2 = under_Cat_level2;
    }



}
