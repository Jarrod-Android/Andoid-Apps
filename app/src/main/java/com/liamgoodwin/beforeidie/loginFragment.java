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

import org.w3c.dom.Text;

public class loginFragment extends Fragment {

    EditText username;
    EditText password;
    TextView errorMessage;
    Button login;
    Button register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        login = (Button) view.findViewById(R.id.login);
        register = (Button) view.findViewById(R.id.register);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        errorMessage = (TextView) view.findViewById(R.id.errorMessage);

        username.setText(null);
        password.setText(null);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernameText = null;
                String passwordText = null;

                //If username is empty set the text to "", else if it is not empty then set it to whatever the user entered
                if(username.getText().toString().equals(null)) {
                    usernameText = "";
                } else if (!username.getText().toString().equals(null)) {
                    usernameText = username.getText().toString();
                }

                //If password is empty set the text to "", else if it is not empty then set it to whatever the user entered
                if (password.getText().toString().equals(null)) {
                    passwordText = "";
                } else if (!password.getText().toString().equals(null)) {
                    passwordText = username.getText().toString();
                }

                //Error code to determine if the username, password or both are empty still, it will alert the user with an error message
                if((usernameText.equals("") || usernameText.equals(null)) && (passwordText.equals("") || passwordText.equals(null))) {
                    errorMessage.setText("Please enter a username & password");
                } else if ((usernameText.equals("") || usernameText.equals(null)) && (!passwordText.equals("") || !passwordText.equals(null))) {
                    errorMessage.setText("Please enter a username");
                } else if ((passwordText.equals("") || passwordText.equals(null)) && (!usernameText.equals("") || !usernameText.equals(null))) {
                    errorMessage.setText("Please enter a password");
                } else if((!usernameText.equals("") || !usernameText.equals(null)) && (!passwordText.equals("") || !passwordText.equals(null))) {
                    errorMessage.setText("Valid Entry");
                } else {
                    errorMessage.setText("Balls");
                }
            }
        });

        //Return the view
        return view;
    }
}
