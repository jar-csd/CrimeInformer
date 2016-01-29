package csd.jar.crimeinformer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity {

    //Explicit
    private TextView showNameTextView, showLatTextView, showLngTextView;
    private String nameString, surnameString, idString;
    private int idAnInt;
    private LocationManager objLocationManager;
    private Criteria objCriteria;
    private boolean GPSABoolean, networkABoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Bind Widget
        bindWidget();

        // ShowName
        showName();

        //Setup Location
        setupLocation();

    } // Main Method

    @Override
    protected void onResume() {
        super.onResume();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        objLocationManager.removeUpdates(objLocationListener);
        String strLat = null;
        String strLng = null;

        Location networkLocation = myRequestLocation(LocationManager.NETWORK_PROVIDER,
                "Network Error");
        if (networkLocation !=null) {

            strLat = String.format("%.7f", networkLocation.getLatitude());
            strLng = String.format("&.7f", networkLocation.getLongitude());


        }//if

        Location GPSLocation = myRequestLocation(LocationManager.GPS_PROVIDER, "GPS Error");
        if (GPSLocation !=null) {

            strLat = String.format("%.7f", GPSLocation.getLatitude());
            strLng = String.format("%.7f", GPSLocation.getLongitude());


        }//if
        showLatTextView.setText(strLat);
        showLngTextView.setText(strLng);

    }// onResume

    @Override
    protected void onStop() {
        super.onStop();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        objLocationManager.removeUpdates(objLocationListener);
    }

    public Location myRequestLocation(String strProvider, String strError) {

        Location objLocation = null;
        if (objLocationManager.isProviderEnabled(strProvider)) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener);
            objLocation = objLocationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("Test", strError);
        }


        return null;
    }

    private int checkSelfPermission(String accessFineLocation) {
        return 0;
    }

    public final LocationListener objLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            showLatTextView.setText(String.format("%.7f", location.getLatitude()));
            showLngTextView.setText(String.format("%.7f", location.getLongitude()));

        } // onLocationChanged

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void setupLocation() {
        objLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objCriteria = new Criteria();
        objCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        objCriteria.setAltitudeRequired(false);
        objCriteria.setBearingRequired(false);

    }//setupLocation

    private void showName() {

        //Receive Value From Intent
        idString = getIntent().getStringExtra("ID");
        idAnInt = Integer.parseInt(idString);

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM " + ManageTABLE.TABLE_NAME, null);

        objCursor.moveToFirst();
        objCursor.moveToPosition(idAnInt - 1);
        nameString = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_NAME));
        surnameString = objCursor.getString(objCursor.getColumnIndex(ManageTABLE.COLUMN_SURNAME));

        showNameTextView.setText(nameString + " " + surnameString);



    }// showName

    private void bindWidget() {
        showNameTextView = (TextView) findViewById(R.id.textView7);
        showLatTextView = (TextView) findViewById(R.id.textView10);
        showLngTextView = (TextView) findViewById(R.id.textView12);

    }// bindWidget


}  //Main Class
