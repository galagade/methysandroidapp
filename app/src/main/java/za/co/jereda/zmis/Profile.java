package za.co.jereda.zmis;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    TextView etUsername, etName, etAge;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
       /* android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.plus_icon);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);*/
        etName = (TextView)findViewById(R.id.etName);
        etAge = (TextView)findViewById(R.id.etAge);
        etUsername = (TextView)findViewById(R.id.etUsername);
        userLocalStore = new UserLocalStore(this);
        User storedUser = userLocalStore.getLoggedInUser();
        etName.setText(storedUser.name);
        etUsername.setText(storedUser.username);
        etAge.setText(storedUser.age + "");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mLogout:
                Intent intent = new Intent();
                PendingIntent pintent = PendingIntent.getActivity(Profile.this,0,intent,0);
                Notification noti = new Notification.Builder(Profile.this)
                        .setTicker("Methys")
                        .setContentTitle("Status")
                        .setContentText("You have successfully log out")
                        .setSmallIcon(R.drawable.notifie)
                        .setContentIntent(pintent).build();

                noti.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0,noti);
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(-1);
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.mProfile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.mHome:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.mVehicleadd:
                startActivity(new Intent(this, VehicleaddActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}


