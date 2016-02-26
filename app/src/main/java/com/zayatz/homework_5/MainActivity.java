package com.zayatz.homework_5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etEditAdress;
    EditText etEditSubject;
    EditText etEditMessage;
    Button btnSendEmail;
    Button btnCallHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEditAdress = (EditText) findViewById(R.id.etEmailAdress_AM);
        etEditSubject = (EditText) findViewById(R.id.etEmailSubject_AM);
        etEditMessage = (EditText) findViewById(R.id.etEmailMessage_AM);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail_AM);
        btnCallHelp = (Button) findViewById(R.id.btnCallHelp_AM);


    }
}
