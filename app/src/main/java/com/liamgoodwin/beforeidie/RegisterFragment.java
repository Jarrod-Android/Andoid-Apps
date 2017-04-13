package com.liamgoodwin.beforeidie;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends android.support.v4.app.Fragment {

    TextView errorMessage;
    EditText username;
    EditText password;
    EditText password2;
    CheckBox privacy;
    Button addAccount;

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

        final String regUsername = username.getText().toString();
        final String regPassword = password.getText().toString();
        String regPassword2 = password2.getText().toString();
        final int regPrivacy;

        if(privacy.isChecked()) {
            regPrivacy = 1;
        } else {
            regPrivacy = 0;
        }

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
                    User user = new User(regUsername,
                            regPassword, regPrivacy);

                    Database db = new Database(getContext());
                    //db.addUser(user);
                    db.closeDB();

                    Toast.makeText(getActivity(), "The username '" + user.getUsername() + "' has been registered",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

}
