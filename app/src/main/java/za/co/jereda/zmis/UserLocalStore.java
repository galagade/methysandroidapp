package za.co.jereda.zmis;

import android.content.Context;
import android.widget.ShareActionProvider;
import android.content.SharedPreferences;

/**
 * Created by Jereda  on 2016/02/19.
 */
public class UserLocalStore {

    public static final String SP_NAME = "user_Log";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);

    }

    public void storeUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name", user.name);
        spEditor.putInt("age", user.age);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.commit();

    }

    public User getLoggedInUser(){
        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        int age = userLocalDatabase.getInt("age", -1);

        User storedUser = new User(name, age, username, password);
        return storedUser;

    }

    public void setUserLoggedIn(Integer loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("status", loggedIn);
        spEditor.commit();

    }

    public Integer getUserLoggedIn(){

        Integer log = userLocalDatabase.getInt("status", -1);
        return log;
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
    }
}
