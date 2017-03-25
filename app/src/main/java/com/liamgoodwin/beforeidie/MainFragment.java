package com.liamgoodwin.beforeidie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 4/4/2016.
 */

public class MainFragment extends Fragment {

    //Declare the list view for the CardView usage on the Main page
    ListView list;


//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_main, container, false);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        list = (ListView) view.findViewById(R.id.recommendationList);
        final ArrayList<Location> locationslist = new ArrayList<Location>();
        locationslist.add(new Location("Wheatly Park", "This is a nice park This is a nice park This is a nice park This is a nice park This is a nice park This is a nice park", "geo:0,0?q=42.088782,-82.444714(Wheatly Park)"));
        locationslist.add(new Location("Algonquin Park", "This is a large park This is a nice park This is a nice park This is a nice park This is a nice park This is a nice park", "geo:0,0?q=45.554195,-78.596781(Algonquin Park)"));
        locationslist.add(new Location("Elora Gorge", "This park has a gorge in it This is a nice park This is a nice park This is a nice park This is a nice park This is a nice park", "geo:0,0?q=43.672014,-80.444244(Elora Gorge)"));
        locationslist.add(new Location("Greenway", "This used to be train tracks This is a nice park This is a nice park This is a nice park This is a nice park This is a nice park", "geo:0,0?q=42.101273,-82.933795(Green Way)"));
        final CustomAdapter adapter = new CustomAdapter(getContext(), locationslist);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationDescriptionTextView =
                        (TextView) view.findViewById(R.id.description);
                TextView details = (TextView) view.findViewById(R.id.details);
                ImageView chevron = (ImageView) view.findViewById(R.id.chevron);
                if(LocationDescriptionTextView.getText() !=
                        (locationslist.get(position)).getDescription() ){
                    //Update the text of the description
                    LocationDescriptionTextView.setText(
                            ((Location) list.getItemAtPosition(position)).getDescription());
                    //update the text of the show more
                    details.setText("Click to show less");
                    //update the chevron image
                    chevron.setImageResource(R.drawable.ic_expand_less_black_24dp);

                }
                else{
                    LocationDescriptionTextView.setText("");
                    //update the text of the show more
                    details.setText("Click to show more");
                    //update the chevron image
                    chevron.setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                locationslist.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return view;
    }

    public class CustomAdapter extends ArrayAdapter<Location> {

        public CustomAdapter(Context context, ArrayList<Location> items) {
            super(context, 0, items);
        }

        /**
         * getView is used to take every item in a list
         * and assign a view to it.
         * With this specific adapter we specified item_view as the view
         * we want every item in a list to look like.
         * After that item has item_view attached to it
         * we populate the item_view's name TextView
         */
        public View getView(int position, View convertView, ViewGroup parent){
            final Location item = getItem(position);

            if(convertView == null){
                convertView =
                        LayoutInflater.from(getContext()).inflate(
                                R.layout.item_view, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());
            ImageView image = (ImageView) convertView.findViewById(R.id.location);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri geoLocation = Uri.parse(item.getLocation());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(geoLocation);
                    if(intent.resolveActivity(
                            getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });



            return  convertView;
        }



    }
}