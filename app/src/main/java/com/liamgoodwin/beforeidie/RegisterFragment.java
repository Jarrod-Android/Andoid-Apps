package com.liamgoodwin.beforeidie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterFragment extends Fragment {

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



        return view;
    }

}
