package com.liamgoodwin.beforeidie;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
        bucketList.add(new Bucketlist("Snorkel in The Great Barrier Reef", "The Great Barrier Reef is the largest aquatic animal habitat in the world", 666));
        final CustomAdapter adapter = new CustomAdapter(getContext(), bucketList);
        list.setAdapter(adapter);

        return view;
    }

    public class CustomAdapter extends ArrayAdapter<Bucketlist> {

        public CustomAdapter(Context context, ArrayList<Bucketlist> items) {
            super(context, 0, items);
        }


        public View getView(int position, View convertView, ViewGroup parent){
            final Bucketlist item = getItem(position);

            if(convertView == null){
                convertView =
                        LayoutInflater.from(getContext()).inflate(
                                R.layout.card_view_item, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());

            return  convertView;
        }



    }
}