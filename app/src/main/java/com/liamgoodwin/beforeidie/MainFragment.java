package com.liamgoodwin.beforeidie;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Jarrod & Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class MainFragment extends Fragment {

    //Declare the list view for the CardView usage on the Main page
    ListView list;
    TextView LocationText;
    Button LearnMore;
    ArrayList<Recommendation> recommendation;
    TextView daysName;
    TextView daysTime;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        //used to stop the login fragment from launching on rotate. Saving the data
        if(savedInstanceState == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();

            transaction.addToBackStack(null);
            transaction.replace(R.id.mainActivity, new loginFragment());
            transaction.commit();
        }

        //pulling in the smallest time
        Database db = new Database(getContext());
        Bucketlist bucketListSmallestTime = db.getSmallestTime();
        db.closeDB();

        daysName = (TextView) view.findViewById(R.id.daysName);
        daysTime = (TextView) view.findViewById(R.id.daysTime);


        //doing the correct calculations to display the time for the item expiring the soonest.
        if (bucketListSmallestTime != null) {
            long smallestDays = bucketListSmallestTime.getTime();
            String homeBucketListItemName = bucketListSmallestTime.getName();

            long homeTime = System.currentTimeMillis();

            long homeDiffInMillis = smallestDays - homeTime;

            int homeDiffInDays = (int) (homeDiffInMillis / (1000 * 60 * 60 * 24));

            daysName.setText(homeBucketListItemName);


            if(homeDiffInDays == 1) {
                daysTime.setText(homeDiffInDays + " day");
            } else {
                daysTime.setText(homeDiffInDays + " days");
            }

            //changing the colours
            if (homeDiffInDays <= 7) {
                daysTime.setTextColor(Color.RED);
            } else if (homeDiffInDays <= 30) {
                daysTime.setTextColor(Color.YELLOW);
            } else {
                daysTime.setTextColor(Color.parseColor("#60be6a"));
            }

        }

        LocationText = (TextView) view.findViewById(R.id.locationText);

        //pulling in a random location
        db = new Database(getContext());
        Recommendation rec = db.getRandomRecommendation();
        if(rec != null) {
            String recName = rec.getName();
            String recDescription = rec.getDescription();
            int recImage = rec.getImage();
            LocationText.setText(recName);

            final String name = recName;
            final String description = recDescription;
            final Integer image = recImage;

            //loading the new view
            LearnMore = (Button) view.findViewById(R.id.learnMore);
            LearnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //infating the new pop up view
                    showPopup(inflater, view, name, description, image);
                }
            });
        } else {
            LocationText.setText("Cannot Pull From DB");
        }

        db.closeDB();

        return view;
    }

    public void showPopup(LayoutInflater inflater, View anchorView, final String name, final String description, Integer image) {

        View popupView = inflater.inflate(R.layout.popup_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView popupName = (TextView) popupView.findViewById(R.id.popupName);
        ImageView popupImage = (ImageView) popupView.findViewById(R.id.popupImage);
        TextView popupDescription = (TextView) popupView.findViewById(R.id.popupDescription);
        Button add = (Button) popupView.findViewById(R.id.add);

        popupName.setText(name);
        popupImage.setImageResource(image.intValue());
        popupDescription.setText(description);

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

        add.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {

                int completed = 0;

                long time = System.currentTimeMillis();
                long millis = time + 909999999 ;

                Bucketlist bucketlist = new Bucketlist(name,
                        description, millis, completed);

                Database db = new Database(getContext());
                db.addBucketlist(bucketlist);
                db.closeDB();
            }
        });
    }

}