package global.imas.bintouch;

/**
 * Created by bachirhabib on 9/13/2018.
 */
public class Friends {
   String count;

    public Friends(String count, String image, String name, String status) {
        this.count = count;
        this.image = image;
        this.name = name;
        this.status = status;
    }
    public Friends() {

    }

    String image;
    String name;
    String status;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
