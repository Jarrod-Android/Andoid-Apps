package com.liamgoodwin.beforeidie;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.math.BigInteger;
import java.util.ArrayList;

public class MyBucketListFragment extends Fragment {

    FragmentManager fm;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bucket_list, container, false);

        fm = getActivity().getSupportFragmentManager();

        list = (ListView) view.findViewById(R.id.bucketlistListView);
        final ArrayList<Bucketlist> bucketList = new ArrayList<Bucketlist>();
        bucketList.add(new Bucketlist("Snorkel in The Great Barrier Reef", "The Great Barrier Reef is th largest aquatic animal habitant", 666));

        return view;
    }
}