package com.liamgoodwin.beforeidie;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by liamgoodwin on 2017-03-27.
 */
public class AddToMyBucketListFragment extends Fragment {

    EditText name;
    EditText description;
    DatePicker date;
    FragmentManager fm;

    public AddToMyBucketListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_to_my_bucket_list, container, false);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"},
                1);

        name = (EditText) view.findViewById(R.id.nameEditText);
        description = (EditText) view.findViewById(R.id.descriptionEditText);
        date = (DatePicker) view.findViewById(R.id.datePicker);

        Button submit = (Button) view.findViewById(R.id.submitButton);
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
                db.addBucketlist(bucketlist);
                db.closeDB();

                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return view;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}