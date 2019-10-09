package global.imas.bintouch;

/**
 * Created by bachirhabib on 8/24/2018.
 */
public class Users {

    public  String name;
    public  String image;
    public  String status;
    public  String Thumb_image;
    public  String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;

    public Users(String name, String image, String status, String Thumb_image, String email, String country) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.Thumb_image = Thumb_image;
        this.email = email;
        this.country = country;
    }




    public String getThumb() {
        return Thumb_image;
    }

    public void setThumb(String thumb) {
        this.Thumb_image = thumb;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public Users() {

    }


}
