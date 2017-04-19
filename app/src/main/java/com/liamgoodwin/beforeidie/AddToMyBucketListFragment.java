package com.liamgoodwin.beforeidie;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
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
    TextView learnMore;

    public AddToMyBucketListFragment() {
        // Required empty public constructor
    }

     /**
     * onCreateView inflates the view we tell it to with the following code
     * in this instance it inflates the fragment_add_to_my_bucket_list layout
     *
     * @param  inflater  inflates the Layout
     * @param  container specifies the ViewGroup
     * @param  savedInstanceState bundle to hold the core data of the View
     * @return returns the view to inflate it
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_to_my_bucket_list, container, false);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"},
                1);

        name = (EditText) view.findViewById(R.id.nameEditText);
        description = (EditText) view.findViewById(R.id.descriptionEditText);
        date = (DatePicker) view.findViewById(R.id.datePicker);
        bucketList = (LinearLayout) view.findViewById(R.id.bucketlist);

        Button submit = (Button) view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth());
                              long millis = calendar.getTimeInMillis();
                int completed = 0;

                Database db = new Database(getContext());
                Bucketlist bucketlist = new Bucketlist(name.getText().toString(),
                        description.getText().toString(), millis, completed);
                db.addBucketlist(bucketlist);
                db.closeDB();

                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return view;
    }

    /**
     * onCreateView inflates the view we tell it to with the following code
     * in this instance it inflates the fragment_add_photo layout
     *
     * @param  inflater  inflates the Layout
     * @param  anchorView shows the overlaying view ontop of the current view
     */
    public void showPopup(LayoutInflater inflater, View anchorView) {

        View popupView = inflater.inflate(R.layout.learn_more_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorView.getHeight());

        //For exit button
        //popupwindow.dismiss();
    }

    /**
     * onRequestPermissionsResult determines the requested permissions
     * and determines what the result will be
     *
     * @param  requestCode  determines what is requested by the user
     * @param  permissions holds the result requested by the user
     * @param  grantResults Holds the granted Results
     * @return returns the view to inflate it
     */
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