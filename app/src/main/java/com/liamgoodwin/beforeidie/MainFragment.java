package com.liamgoodwin.beforeidie;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

/**
 * Created by HP on 4/4/2016.
 */

public class MainFragment extends Fragment {

    //Declare the list view for the CardView usage on the Main page
    ListView list;
    TextView RecommendationsDescriptionTextView;
    Button LearnMore;
    TextView daysName;
    TextView daysTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

//        daysName = (TextView) view.findViewById(R.id.daysName);
//        daysTime = (TextView) view.findViewById(R.id.daysTime);
//
//        Bundle extras = getArguments();
//        String bucketListItemName = extras.getString("homeBucketListItemName");
//        String homeBucketListDays = extras.getString("homeBucketListItemDays");
//
//
//        daysName.setText(bucketListItemName);
//        daysTime.setText(homeBucketListDays);



        return view;
    }
}