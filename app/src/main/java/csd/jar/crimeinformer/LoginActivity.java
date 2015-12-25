package csd.jar.crimeinformer;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;
    private ManageTABLE objManageTABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Bind Widget
        bindWidget();

        //Connected Database
        objManageTABLE = new ManageTABLE(this);
    } // Main Method

    public void clickLogin(View view) {

        //Check Space
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("") || passwordString.equals("") ) {

            //Have Space
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.nagativeDialog(LoginActivity.this, "Have Space", "Please Fill All Every Blank");

        } else {

            //No Space

            //objManageTABLE.addNewValue("User", "Pass", "Name", "Surname", "idCard", "phoneNumber", "Email");

            deleteAllSQLite();

            synChronizedJSON();
            checkUser();


        }

    }// ClickLogin

    private void checkUser() {
        try {

            String[] strMyResult = objManageTABLE.searchUser(userString);
            if (passwordString.equals(strMyResult[2])) {
                welcomeDialog();

            } else {

                MyAlertDialog objMyAlertDialog = new MyAlertDialog();
                objMyAlertDialog.nagativeDialog(LoginActivity.this,
                        "Password False", "Please Try Again Password False");
            }

        } catch (Exception e) {

            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.nagativeDialog(LoginActivity.this,
                    "User False", "No " + userString + " in my Database");

        }

    } //checkUser

    private void welcomeDialog() {


    }

    private void deleteAllSQLite() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase(MyOpenHelper.DATABASE_NAME,
                MODE_PRIVATE, null);
        objSqLiteDatabase.delete(ManageTABLE.TABLE_NAME, null, null);
    }

    private void synChronizedJSON() {

        //Setup NewPolicy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);

        //1.Create InputStream
        InputStream objInputStream = null;
        String TAG = "Jar";
        String strURL = "http://swiftcodingthai.com/jar/php_get_data.php";

        try {

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost(strURL);
            HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();


        } catch (Exception e) {
            Log.d(TAG, "InputStream ==>" + e.toString());

        }
        //2.Create JSON String
        String strJSON = null;
        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();

            String strLine = null;

            while ((strLine =objBufferedReader.readLine()) !=null) {
                objStringBuilder.append(strLine);

            }//while
            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e) {
            Log.d(TAG, "strJSON ==>" + e.toString());

        }
        //3.Create JSON to SQLite
        try {
            JSONArray objJsonArray = new JSONArray(strJSON);
            for (int i=0;i<objJsonArray.length(); i++) {

                JSONObject object = objJsonArray.getJSONObject(i);
                String strUser = object.getString(ManageTABLE.COLUMN_USER);
                String strPassword = object.getString(ManageTABLE.COLUMN_PASSWORD);
                String strName = object.getString(ManageTABLE.COLUMN_NAME);
                String strSurname = object.getString(ManageTABLE.COLUMN_SURNAME);
                String strID_card = object.getString(ManageTABLE.COLUMN_ID_CARD);
                String strPhoneNumber = object.getString(ManageTABLE.COLUMN_PHONENUMBER);
                String strEmail = object.getString(ManageTABLE.COLUMN_EMAIL);
                objManageTABLE.addNewValue(strUser, strPassword, strName, strSurname, strID_card, strPhoneNumber, strEmail);
            }
        } catch (Exception e) {
            Log.d(TAG, "Update ==>" + e.toString());
        }

    }//SynChoronizedJSON

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText6);
        passwordEditText = (EditText) findViewById(R.id.editText7);

    }


} // Main Class
