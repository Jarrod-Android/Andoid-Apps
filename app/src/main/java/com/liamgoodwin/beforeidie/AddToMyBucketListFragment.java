package com.liamgoodwin.beforeidie;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liamgoodwin on 2017-03-27.
 */
public class AddToMyBucketListFragment extends Fragment {

    EditText name;
    EditText description;
    EditText subItem;
    DatePicker date;
    FragmentManager fm;
    ImageView plusButton;
    LinearLayout bucketList;
    LinearLayout subEditLayout;
    TextView sub;
    EditText subEdit;
    EditText subItemEditText;
    List<EditText> holdEdit = new ArrayList<EditText>();
    List<String> subItems = new ArrayList<String>();
    ArrayList<String> subItemExtra = new ArrayList<String>();

    public AddToMyBucketListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_to_my_bucket_list, container, false);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"},
                1);

        subItemEditText = (EditText) view.findViewById(R.id.subItemEditText);
        name = (EditText) view.findViewById(R.id.nameEditText);
        description = (EditText) view.findViewById(R.id.descriptionEditText);
        date = (DatePicker) view.findViewById(R.id.datePicker);
        bucketList = (LinearLayout) view.findViewById(R.id.bucketlist);
        plusButton = (ImageView) view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subEditLayout = new LinearLayout(getActivity().getApplicationContext());
                subEditLayout.setOrientation(LinearLayout.HORIZONTAL);
                sub = new TextView(getActivity().getApplicationContext());
                subEdit = new EditText(getActivity().getApplicationContext());
                subEdit.setPadding(30, 0, 0, 0);
                subEdit.setEms(10);
                subEdit.setTextColor(Color.BLACK);
                holdEdit.add(subEdit);
//                for (int i = 0; i < subItemExtra.size(); i++) {
//                    subItemExtra.add(subEdit.getText().toString());
//                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                sub.setText("Sub Item");
                sub.setTextSize(14);
                sub.setTextColor(Color.BLACK);
                sub.setPadding(90, 40, 0, 0);
                subEditLayout.addView(sub);
                subEditLayout.addView(subEdit);
                bucketList.addView(subEditLayout, layoutParams);
            }
        });

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

//                subItems.add(subItemEditText.getText().toString());
//                //subItems.add(subEdit.getText().toString());
//
//                int listSize = subItems.size();
//
//                for (int i = 0; i<listSize; i++){
//                    Log.i("Member name: ", subItems.get(i));
//                }

                subItems.add(subItemEditText.getText().toString());

                for (EditText e : holdEdit) {
                    String test = e.getText().toString();
                    subItems.add(test);
                }



                int listSize = subItems.size();

                for (int i = 0; i < listSize; i ++) {
                    Log.d("TESTING :", subItems.get(i));
                }

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