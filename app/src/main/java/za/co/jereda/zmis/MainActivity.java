package za.co.jereda.zmis;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Notification.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button bLogout, bregions;

   private TextView etName,etOnwer, etmt, etDate;
    UserLocalStore userLocalStore;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.plus_icon);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        etName = (TextView) findViewById(R.id.etName);
        //etOnwer = (TextView) findViewById(R.id.etOwner);
        //etmt = (TextView) findViewById(R.id.etMt);
        //etDate = (TextView) findViewById(R.id.etDateR);
        new VehicleDataJson().execute("http://www.jereda.co.za/androidapp/VehicleFetch.php");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
         userLocalStore=new UserLocalStore(this);


}

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        int log = authenticate();

        //return log;

        if (log == 1) {
            displayUserDetails();
        } else {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://za.co.jereda.zmis/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    private Integer authenticate() {
        int log = userLocalStore.getUserLoggedIn();
        return log;

    }

    private void displayUserDetails() {
        User storedUser = userLocalStore.getLoggedInUser();
        etName.setText("Welcome " + storedUser.name);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }


    public class VehicleDataJson extends AsyncTask<String,String,String > {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();
                InputStream stream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);

                }

                line = buffer.toString();

                String string = buffer.toString();
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
           // etTable.setText(result);

            try {
                JSONArray jArray = new JSONArray(result);
                TableLayout tv = (TableLayout) findViewById(R.id.table);
                tv.removeAllViewsInLayout();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json;
                    json = jArray.getJSONObject(i);// JSONObject(String.valueOf(i));
                    TableRow tr = new TableRow(MainActivity.this);
                    tr.setLayoutParams(new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT));
                    tr.setBackgroundColor(Color.argb(222,222,222,222));
                    tr.setPadding(5,5,5,5);

                    String name = json.getString("name");
                    String mileage = json.getString("year");
                    String manufacture = json.getString("manufacture");


                    TextView b = new TextView(MainActivity.this);
                    String stime = json.getString("name");
                    b.setText(stime);
                    //b.setPadding(10, 10, 10, 10);
                    b.setTextSize(13);
                    b.setLayoutParams(new LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT,1.0f));

                    tr.addView(b);
                    TextView b1 = new TextView(MainActivity.this);
                    //b1.setPadding(10,10,10,10);
                    b1.setTextSize(13);
                    String stime1 = json.getString("manufacture");
                    b1.setLayoutParams(new LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT,1.0f));
                    b1.setText(stime1);

                    tr.addView(b1);
                    TextView b2 = new TextView(MainActivity.this);
                    //b2.setPadding(10,10,10,10);
                    String stime2 = json.getString("year");
                    b2.setText(stime2);
                    b2.setLayoutParams(new LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT,1.0f));

                    b2.setTextSize(13);
                    tr.addView(b2);
                    tv.addView(tr);
                    final View vline1 = new View(MainActivity.this);
                    vline1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 1));
                    vline1.setBackgroundColor(Color.WHITE);
                    tv.addView(vline1);



                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                PendingIntent pintent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                Notification noti = new Builder(MainActivity.this)
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

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://za.co.jereda.zmis/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
