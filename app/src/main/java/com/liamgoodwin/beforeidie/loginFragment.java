package com.liamgoodwin.beforeidie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = (EditText) view.findViewById(R.id.username);
                password = (EditText) view.findViewById(R.id.password);
                errorMessage = (TextView) view.findViewById(R.id.errorMessage);

                String usernameText = "";
                String passwordText = "";

                if(username.getText().toString().equals(null)) {
                    usernameText = "";
                } else if (!username.getText().toString().equals(null)) {
                    usernameText = username.getText().toString();
                }

                if (password.getText().toString().equals(null)) {
                    passwordText = "";
                } else if (!password.getText().toString().equals(null)) {
                    passwordText = username.getText().toString();
                }

                if(usernameText.equals("")) {
                    errorMessage.setText("Please enter a username");
                } else if (passwordText.equals("")) {
                    errorMessage.setText("Please enter a password");
                } else if (usernameText.equals("") && passwordText.equals("")) {
                    errorMessage.setText("Please enter a username & password");
                } else if(!usernameText.equals("") && !passwordText.equals("")) {
                    errorMessage.setText("Valid Entry");
                } else {
                    errorMessage.setText("Balls");
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
