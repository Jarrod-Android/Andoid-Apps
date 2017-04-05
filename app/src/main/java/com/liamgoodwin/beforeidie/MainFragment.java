package com.liamgoodwin.beforeidie;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 4/4/2016.
 */

public class MainFragment extends Fragment {

    //Declare the list view for the CardView usage on the Main page
    ListView list;
    TextView LocationText;
    Button LearnMore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Map<String, String> map = new HashMap<String, String>();
        map.put("dog", "type of animal");
        System.out.println(map.get("dog"));

        LocationText = (TextView) view.findViewById(R.id.locationText);
        LearnMore = (Button) view.findViewById(R.id.learnMore);



        return view;
    }
}