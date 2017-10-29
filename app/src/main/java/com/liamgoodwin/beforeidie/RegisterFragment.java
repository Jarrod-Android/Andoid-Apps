package com.liamgoodwin.beforeidie;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jarrod & Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class RegisterFragment extends android.support.v4.app.Fragment {

    TextView errorMessage;
    EditText username;
    EditText password;
    EditText password2;
    CheckBox privacy;
    Button addAccount;
    Button backToLogin;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);


        //loading in the different elements in the form.
        errorMessage = (TextView) view.findViewById(R.id.errorMessage);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        password2 = (EditText) view.findViewById(R.id.password2);
        privacy = (CheckBox) view.findViewById(R.id.privacy);
        password = (EditText) view.findViewById(R.id.password);
        addAccount = (Button) view.findViewById(R.id.addAccount);
        backToLogin = (Button) view.findViewById(R.id.backToLogin);

        addAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //if there is no username
                if((username.getText().toString().equals(null)) || (username.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a Username");
                }
                //if there is no password
                else if((password.getText().toString().equals(null)) || (password.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a Password");
                }
                // if the second password isn't entered
                else if((password2.getText().toString().equals(null)) || (password2.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a 2nd Password");
                }
                // if the passwords do not match
                else if (!password.getText().toString().equals(password2.getText().toString())) {
                    errorMessage.setText("Passwords do not match");
                }
                //if the account does create successfully
                else if ((!username.getText().toString().equals(null) || !username.getText().equals("")) && (!password.getText().toString().equals(null) || !password.getText().toString().equals("")) && (password.getText().toString().equals(password2.getText().toString()))) {
                    final String regUsername = username.getText().toString();
                    final int regPrivacy;

                    String encryptedPassword = null;

                    // used to encrypt password
                    try {
                        encryptedPassword = SHA1(password.getText().toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    // change the privacy if it is checked
                    if (privacy.isChecked()) {
                        regPrivacy = 1;
                    } else {
                        regPrivacy = 0;
                    }

                    //adding the new User to the datavase
                    User user = new User(regUsername, encryptedPassword, regPrivacy);


                    Database db = new Database(getContext());
                    db.addUser(user); //running the addUser method.
                    db.closeDB();

                    //Toast to show the user has been registered.
                    Toast.makeText(getActivity(), "The username '" + regUsername + "' has been registered",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // button handler to return back to the login screen
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

            }
        });

        return view;
    }

    /**
     * This method hashes a password
     * @param data
     * @return the converted string of what the hashed password is
     */
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        int length = data.length;
        for(int i = 0; i < length; ++i) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            }
            while(++two_halfs < 1);
        }
        return buf.toString();
    }

    /**
     * This puts it into a SHA String
     * @param text
     * @return calls the convertToHex and returns the SHA String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

}
