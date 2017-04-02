package com.liamgoodwin.beforeidie;

import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditFragment extends Fragment {

    Spinner editSpin;
    EditText name;
    EditText description;
    DatePicker date;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        editSpin = (Spinner) view.findViewById(R.id.blEditSpinner);
        name = (EditText) view.findViewById(R.id.nameEditTextEdit);
        description = (EditText) view.findViewById(R.id.descriptionEditTextEdit);
        date = (DatePicker) view.findViewById(R.id.datePickerEdit);

        Database db = new Database(getContext());
        ArrayList<Bucketlist> listOfNames = db.getAllNames();
        db.closeDB();

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listOfNames);
        editSpin.setAdapter(adapter);

        //Get the name, description and date and set them to the fields when an item is selected in the spinner

        Button submit = (Button) view.findViewById(R.id.submitButtonEdit);
        submit.setOnClickListener(new View.OnClickListener() {
            @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth());
                long millis = calendar.getTimeInMillis();
                int completed = 0;

                Bucketlist bucketlist = new Bucketlist(name.getText().toString(),
                        description.getText().toString(), millis, completed);

                Database db = new Database(getContext());
                db.updateBucketlist(bucketlist);
                db.closeDB();

                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });



        // Inflate the layout for this fragment
        return view;
    }

}
