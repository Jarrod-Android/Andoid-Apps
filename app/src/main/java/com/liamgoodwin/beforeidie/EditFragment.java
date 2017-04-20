package com.liamgoodwin.beforeidie;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Jarrod and Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class EditFragment extends Fragment {

    EditText name;
    EditText description;
    DatePicker date;
    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Declare and set the view to the fragment_edit layout
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        //Get the sent bundle with the information from the Bucketlist item
        Bundle extras = getArguments();
        final int bucketListID = extras.getInt("editBucketListID");
        final Bucketlist bucketlistItem = extras.getParcelable("bucketlistItem");
        String bucketListItemName = extras.getString("editBucketListItemName");
        String bucketListItemDescription = extras.getString("editBucketListItemDescription");

        //Declare and set the name and description fields
        name = (EditText) view.findViewById(R.id.nameEditTextEdit);
        name.setText(bucketListItemName);
        description = (EditText) view.findViewById(R.id.descriptionEditTextEdit);
        description.setText(bucketListItemDescription);

        //Declare the date picker
        date = (DatePicker) view.findViewById(R.id.datePickerEdit);

        //Open a new database connection and set listOfNames to the db method called getAllNames
        Database db = new Database(getContext());
        ArrayList<Bucketlist> listOfNames = db.getAllNames();
        db.closeDB();

        //Get the name, description and date and set them to the fields when an item is selected in the spinner
        Button submit = (Button) view.findViewById(R.id.submitButtonEdit);
        submit.setOnClickListener(new View.OnClickListener() {
            @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                //Declare a new instance of a calendar and set it to the chosen day, month, and year
                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth());
                long millis = calendar.getTimeInMillis();
                int completed = 0;

                //Set the values of the new bucketList to the new fields
                Bucketlist bucketlist = new Bucketlist(bucketListID, name.getText().toString(),
                        description.getText().toString(), millis, completed);

                Database db = new Database(getContext());
                db.updateBucketlist(bucketlist);

                db.closeDB();

                //Close the popup of the edit fragment
                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        
        // Inflate the layout for this fragment
        return view;
    }

}
