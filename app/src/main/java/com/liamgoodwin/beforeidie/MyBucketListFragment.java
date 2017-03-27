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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;

public class MyBucketListFragment extends Fragment {

    FragmentManager fm;
    ListView list;
    TextView BucketlistDescriptionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bucket_list, container, false);

        fm = getActivity().getSupportFragmentManager();

        list = (ListView) view.findViewById(R.id.bucketlistListView);
        final ArrayList<Bucketlist> bucketList = new ArrayList<Bucketlist>();
        bucketList.add(new Bucketlist("Snorkel in The Great Barrier Reef", "The Great Barrier Reef is the largest aquatic animal habitat in the world", 666));
        final CustomAdapter adapter = new CustomAdapter(getContext(), bucketList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BucketlistDescriptionTextView = (TextView) view.findViewById(R.id.bucketlistDescription);
                TextView details = (TextView) view.findViewById(R.id.details);
                ImageView chevron = (ImageView) view.findViewById(R.id.chevron);
                ImageView addPhoto = (ImageView) view.findViewById(R.id.addphoto);
                ImageView edit = (ImageView) view.findViewById(R.id.edit);
                ImageView delete = (ImageView) view.findViewById(R.id.delete);
                addPhoto.setImageResource(R.drawable.camerabutton);
                addPhoto.setVisibility(View.INVISIBLE);
                edit.setImageResource(R.drawable.editimage);
                edit.setVisibility(View.INVISIBLE);
                delete.setImageResource(R.drawable.deleteimage);
                delete.setVisibility(View.INVISIBLE);
                if(BucketlistDescriptionTextView.getText() != (bucketList.get(position)).getDescription() ){
                    //Update the text of the description
                    BucketlistDescriptionTextView.setText(((Bucketlist) list.getItemAtPosition(position)).getDescription());

                    //update the text of the show more
                    details.setText("Click to show less");
                    //update the chevron image
                    chevron.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    addPhoto.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);

                }
                else{
                    BucketlistDescriptionTextView.setText("");
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
                bucketList.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

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
                                R.layout.bucketlist_card_view, parent, false);
            }

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());

            return  convertView;
        }



    }
}