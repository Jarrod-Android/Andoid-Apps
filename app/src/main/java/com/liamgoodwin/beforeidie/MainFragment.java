package com.liamgoodwin.beforeidie;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by HP on 4/4/2016.
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

        Database db = new Database(getContext());
        Bucketlist bucketListSmallestTime = db.getSmallestTime();
        System.out.println("Error" + bucketListSmallestTime);
        db.closeDB();

        daysName = (TextView) view.findViewById(R.id.daysName);
        daysTime = (TextView) view.findViewById(R.id.daysTime);

        if(bucketListSmallestTime != null ) {
            long smallestDays = bucketListSmallestTime.getTime();
            String homeBucketListItemName = bucketListSmallestTime.getName();

            long homeTime = System.currentTimeMillis();

            long homeDiffInMillis = smallestDays - homeTime;

            int homeDiffInDays = (int) (homeDiffInMillis / (1000 * 60 * 60 * 24));

            daysName.setText(homeBucketListItemName);
            daysTime.setText("" + homeDiffInDays);
        } else {
            daysName.setText("No Items in Database");
            daysTime.setText("Invalid");
        }

        LocationText = (TextView) view.findViewById(R.id.locationText);

        db = new Database(getContext());
        Recommendation rec = db.getRandomRecommendation();
        if(rec != null) {
            String recName = rec.getName();
            String recDescription = rec.getDescription();
            long recImage = rec.getImage();
            LocationText.setText(recName);
        } else {
            LocationText.setText("Cannot Pull From DB");
        }

        db.closeDB();


//        final String name = recommendationsName.get(randomName);
//        final String description = recommendationsDescription.get(randomDescription);
//        final Integer image = recommendationsImage.get(randomImage);

        LearnMore = (Button) view.findViewById(R.id.learnMore);
        LearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showPopup(inflater, view, name, description, image);
            }
        });

        return view;
    }

    public void showPopup(LayoutInflater inflater, View anchorView, String name, String description, Integer image) {

        View popupView = inflater.inflate(R.layout.popup_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView popupName = (TextView) popupView.findViewById(R.id.popupName);
        ImageView popupImage = (ImageView) popupView.findViewById(R.id.popupImage);
        TextView popupDescription = (TextView) popupView.findViewById(R.id.popupDescription);

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
    }

}