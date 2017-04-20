package com.liamgoodwin.beforeidie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jarrod and Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class loginFragment extends Fragment {

    //Declare vairables
    EditText username;
    EditText password;
    TextView errorMessage;
    Button login;
    Button register;
    User user;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Declare and inflate the view
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Initialize the variables we declared above
        login = (Button) view.findViewById(R.id.login);
        register = (Button) view.findViewById(R.id.register);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        errorMessage = (TextView) view.findViewById(R.id.errorMessage);

        //Set the username and password text to null
        username.setText(null);
        password.setText(null);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Error Codes
                if(username.getText().toString().equals("")) {
                    errorMessage.setText("Please enter a Username");
                } else if(password.getText().toString().equals("")) {
                    errorMessage.setText("Please enter a Password");
                } else if ((username.getText().toString().equals("")) && (password.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a username & password");
                } else {

                    //Open the Database and set the user to the value returned by findUser
                    Database db = new Database(getContext());
                    user = null;
                    user = db.findUser(username.getText().toString());
                    db.closeDB();

                    //If the username is returned as null from the database
                    if((user.getUsername().isEmpty()) || (!username.getText().toString().equals(user.getUsername())) ) {
                        errorMessage.setText("Username not found");
                    }

                    //If the username is not null, it means we returned something and we can check if the password is correct
                    else if(!user.getPassword().equals(null)) {

                        String encryptedPassword = null;

                        //Try and encrypt the password using the SHA1 method, if fails it will print the stack trace
                        try {
                            encryptedPassword = SHA1(password.getText().toString());
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        //If the password in the DB and the password field do not match display an error
                        if(!user.getPassword().equals(encryptedPassword)) {
                            errorMessage.setText("Incorrect password");
                        }

                        //If the password in the DB and the password field to match display a success
                        else if(user.getPassword().equals(encryptedPassword)) {
                            fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack();
                        }
                    }
                }
            }
        });

        //If register is clicked the Fragment will commit to the RegisterFragment and replace the login fragment
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                transaction.addToBackStack(null);
                transaction.replace(R.id.mainActivity, new RegisterFragment());
                transaction.commit();
            }
        });

        //Return the view
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
