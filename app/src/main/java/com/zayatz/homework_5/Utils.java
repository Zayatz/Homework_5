package com.zayatz.homework_5;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Zayatz on 29.02.2016.
 */
public class Utils {

    /*checking input fields (isEmpty, email adress validation)*/
    public static boolean checkFields(Context context, EditText emailAdress,
                                EditText emailSubject, EditText emailText) {
        boolean result = false;

        String strEmailAdress = String.valueOf(emailAdress.getText());
        String strEmailSubject = String.valueOf(emailSubject.getText());
        String strEmailText = String.valueOf(emailText.getText());

        if (TextUtils.isEmpty(strEmailAdress)) showToast(context,   //if email field is empty
                                                    R.string.toast_empty_email_adress_AM);
        else if (TextUtils.isEmpty(strEmailSubject)) showToast(context,  //if subject field is empty
                                                        R.string.toast_empty_email_subject_AM);
        else if (TextUtils.isEmpty(strEmailText)) showToast(context,   //if message field is empty
                                                        R.string.toast_empty_email_text_AM);
        else if (isEmailValid(strEmailAdress))result = true;          //if email adress is valid
        else showToast(context, R.string.toast_wrong_email_adress_AM);

        return result;
    }

    public static void showToast(Context context, int stringId) {
        Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
    }

    /*checking validation of email adress by Android tools (true - adress is valid)*/
    private static boolean isEmailValid(String emailAdress) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(emailAdress).matches();
    }
}
