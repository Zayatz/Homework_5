package com.zayatz.homework_5;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int PERMISSION_CALL_PHONE = 5;

    EditText etEditAdress, etEditSubject, etEditMessage;
    Button btnSendEmail, btnCallHelp;

    private boolean mCallPhoneAccessGranted; //defines the access to CALL_PHONE resource
                                             //(true - access granted, false - access denied)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        mCallPhoneAccessGranted = (ContextCompat.checkSelfPermission(this, //check permission
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED);

        btnSendEmail.setOnClickListener(this);
        btnCallHelp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCallHelp_AM:
                callHelp();
                break;
            case R.id.btnSendEmail_AM:
                if (Utils.checkFields(this, etEditAdress, //checking input and validation input fields
                        etEditSubject, etEditMessage)) {
                    sendEmail();
                }
                break;
        }
    }

    /* requestPermission handler */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CALL_PHONE:
                /*in case permission allowed by user restart method called by button*/
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mCallPhoneAccessGranted = true;  //set this var true cause permission already granted
                    callHelp(); //method called by button

                } else {

                    /*check if permission was requested before and denied by user. if was -
                    * show toast with permission`s explanation*/

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                        Utils.showToast(this, R.string.toast_please_chage_decision_AM);     //explanation toast

                    /*in case user first time denied request permission just show toast
                    * with short message*/
                    } else Utils.showToast(this, R.string.toast_access_denied_AM);           //short-message toast
                }
        }
    }

    private void findViews() {
        etEditAdress = (EditText) findViewById(R.id.etEmailAdress_AM);
        etEditSubject = (EditText) findViewById(R.id.etEmailSubject_AM);
        etEditMessage = (EditText) findViewById(R.id.etEmailMessage_AM);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail_AM);
        btnCallHelp = (Button) findViewById(R.id.btnCallHelp_AM);
    }

    /*checking permission CALL_PHONE, if granted - make call to help service
    * if denied - request permission*/
    protected void callHelp() {
        if (mCallPhoneAccessGranted) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            /*set help service phone number. number is defined in Strings*/
            intent.setData(Uri.parse("tel:" + this.getString(R.string.help_phone_AM)));
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this,  //request permission
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_CALL_PHONE);
        }
    }


    /*create new intent to launch email client on device and send adress, subject and message to
    * intent*/
    protected void sendEmail() {
        String strEmailAdress = String.valueOf(etEditAdress.getText());
        String strEmailSubject = String.valueOf(etEditSubject.getText());
        String strEmailText = String.valueOf(etEditMessage.getText());

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{strEmailAdress});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, strEmailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, strEmailText);
        emailIntent.setType("text/plain");

        /*check if there are email clients on phone. If no - catch exception and show toat*/

        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.send_to)));
            finish();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Utils.showToast(this, R.string.toast_no_client_installed_AM);
        }
    }

}
