package za.co.jereda.zmis;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VehicleaddActivity extends AppCompatActivity implements View.OnClickListener {
    UserLocalStore userLocalStore;
    Button bSave, bCancel;

    EditText etname, etcontactnum, etemail, ettype, etyear, etColour, etMileage, etmanu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleadd);

        etname = (EditText) findViewById(R.id.etName);
        etcontactnum = (EditText) findViewById(R.id.etContactnum);
        etemail = (EditText) findViewById(R.id.etEmail);
        ettype = (EditText) findViewById(R.id.etType);
        etyear = (EditText) findViewById(R.id.etYear);
        etColour = (EditText) findViewById(R.id.etColour);
        etMileage = (EditText) findViewById(R.id.etMileage);
        etmanu = (EditText) findViewById(R.id.etManu);
        bSave = (Button)findViewById(R.id.etSave);
        bCancel = (Button)findViewById(R.id.etCancel);

        bSave.setOnClickListener(this);
        bCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.etSave:
                boolean ret = true;
                String name = etname.getText().toString();
                String manufacture = etmanu.getText().toString();
                int contactnum = Integer.parseInt(etcontactnum.getText().toString());
                String email = etemail.getText().toString();
                String type = ettype.getText().toString();
                String colour = etColour.getText().toString();

                int year = Integer.parseInt(etyear.getText().toString());
                int mileage = Integer.parseInt(etMileage.getText().toString());
                if(name.isEmpty() && name.length()==0
                        || manufacture.isEmpty()
                        || manufacture.length()==0
                        || email.isEmpty() && email.length()==0
                        || type.isEmpty() && type.length()==0
                        ||colour.isEmpty() && colour.length()==0
                        ||etcontactnum.getText().toString().length()==0
                        || etyear.getText().toString().length()==0
                        || etMileage.getText().toString().length()==0){
                    ret = false;
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VehicleaddActivity.this);
                    dialogBuilder.setMessage("One or All inputs are Empty");
                    dialogBuilder.setPositiveButton("Ok", null);
                    dialogBuilder.show();
                }




                if(ret == true){
                    Vehicle vehicle = new Vehicle(name,contactnum,email, manufacture, type,  year, colour, mileage);
                    addVehicle(vehicle);
                }
                break;

            case R.id.etCancel:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void addVehicle(Vehicle vehicle){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeVehicleDataInBackground(vehicle, new GetVehicleCallback() {
            @Override
            public void done(Vehicle returnedVehicle) {
                 Toast.makeText(getApplicationContext(), "Vehicle Details Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(VehicleaddActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mLogout:
                Intent intent = new Intent();
                PendingIntent pintent = PendingIntent.getActivity(VehicleaddActivity.this, 0, intent, 0);
                Notification noti = new Notification.Builder(VehicleaddActivity.this)
                        .setTicker("ZMIS")
                        .setContentTitle("Status")
                        .setContentText("You have successfully log out")
                        .setSmallIcon(R.drawable.notifie)
                        .setContentIntent(pintent).build();

                noti.flags = Notification.FLAG_AUTO_CANCEL;
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0, noti);
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(-1);
                startActivity(new Intent(this, Login.class));
                break;

            case R.id.mProfile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.mVehicleadd:
                startActivity(new Intent(this, VehicleaddActivity.class));
                break;
            case R.id.mHome:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
