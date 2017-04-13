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

import java.util.ArrayList;

public class loginFragment extends Fragment {

    EditText username;
    EditText password;
    TextView errorMessage;
    Button login;
    Button register;
    User user;


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

                //Error Codes
                if(username.getText().toString().equals("")) {
                    errorMessage.setText("Please enter a Username");
                } else if(password.getText().toString().equals("")) {
                    errorMessage.setText("Please enter a Password");
                } else if ((username.getText().toString().equals("")) && (password.getText().toString().equals(""))) {
                    errorMessage.setText("Please enter a username & password");
                } else {
                    Database db = new Database(getContext());
                    user = db.findUser(username.getText().toString());
                    db.closeDB();

                    //If the username is returned as null from the database
                    if(user.equals(null)) {
                        errorMessage.setText("Username not found");
                    }

                    //If the username is not null, it means we returned something and we can check if the password is correct
                    else if(!user.equals(null)) {

                        //If the password in the DB and the password field do not match display an error
                        if(!user.getPassword().equals(password)) {
                            errorMessage.setText("Incorrect password");
                        }

                        //If the password in the DB and the password field to match display a success
                        else if(user.getPassword().equals(password)) {
                            errorMessage.setText("Login Successful");
                        }
                    }
                }

            }
        });

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
}
