package za.co.jereda.zmis;

/**
 * Created by kineo on 2016/02/19.
 */
public class User {
    String name,username,password;
    int age;

    public User(String name, int age,String username, String password){
        this.name = name;
        this.password = password;
        this.username = username;
        this.age = age;
    }

    public User(String username, String password){
        this.password = password;
        this.username = username;
        this.age = -1;
        this.name ="";
    }
}
