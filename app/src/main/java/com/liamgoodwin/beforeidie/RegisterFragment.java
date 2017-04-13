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
                if((username.getText().toString().equals(null)) || (username.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a Username");
                } else if((password.getText().toString().equals(null)) || (password.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a Password");
                } else if((password2.getText().toString().equals(null)) || (password2.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a 2nd Password");
                } else if (!password.getText().toString().equals(password2.getText().toString())) {
                    errorMessage.setText("Passwords do not match");
                } else if ((!username.getText().toString().equals(null) || !username.getText().equals("")) && (!password.getText().toString().equals(null) || !password.getText().toString().equals("")) && (password.getText().toString().equals(password2.getText().toString()))) {
                    final String regUsername = username.getText().toString();
                    final String regPassword = password.getText().toString();
                    final int regPrivacy;

                    String encryptedPassword = null;

                    try {
                        encryptedPassword = SHA1(password.getText().toString());
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    if (privacy.isChecked()) {
                        regPrivacy = 1;
                    } else {
                        regPrivacy = 0;
                    }

                    User user = new User(regUsername, regPassword, regPrivacy);

                    Database db = new Database(getContext());
                    //db.addUser(user);
                    db.closeDB();

                    Toast.makeText(getActivity(), "The username '" + encryptedPassword + "' has been registered",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

            }
        });

        return view;
    }

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

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

}
