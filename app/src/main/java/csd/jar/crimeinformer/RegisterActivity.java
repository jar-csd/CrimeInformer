package csd.jar.crimeinformer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEditText, surnameEditText, idCardEditText, phoneEditText, emailEditText;
    private String nameString, surnameString,idCardString, phoneString, emailString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Bind Widget
        bindWidget();


    } // Main Method


    public void clickOK(View view) {

     //Get Value to String
        nameString = nameEditText.getText().toString().trim();
        surnameString = surnameEditText.getText().toString().trim();
        idCardString = idCardEditText.getText().toString().trim();
        phoneString = phoneEditText.getText().toString().trim();
        emailString = emailEditText.getText().toString().trim();


        //Check Zero
        if (nameString.equals("") || surnameString.equals("") ||
                idCardString.equals("") || phoneString.equals("") ||
                emailString.equals("")) {

            //Have Space
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.nagativeDialog(RegisterActivity.this, "มีช่องว่าง", "กรุณากรอกให้ครบทุกช่องครับ");

        } else {
            //No Space

        }

    } // clickOK

    public void clickCancel(View view) {

        finish();
    }

    private void bindWidget() {

        nameEditText = (EditText) findViewById(R.id.editText);
        surnameEditText = (EditText) findViewById(R.id.editText2);
        idCardEditText = (EditText) findViewById(R.id.editText3);
        phoneEditText = (EditText) findViewById(R.id.editText4);
        emailEditText = (EditText) findViewById(R.id.editText5);

    }

} // Main Class
