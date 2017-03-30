package com.liamgoodwin.beforeidie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyBucketListFragment extends Fragment {

    FragmentManager fm;
    TextView name;
    TextView description;
    ListView list;
    TextView BucketlistDescriptionTextView;
    ViewPager viewPager;
    ImageView image;
    LinearLayout galleryLayout;
    SectionPagerAdapter sectionPagerAdapter;
    GestureDetectorCompat tapGestureDetector;
    ArrayList<Bucketlist> bucketList;
    TextView dayCounter;
    TextView details;
    ImageView chevron;
    CustomAdapter adapter;
    ImageView additem;
    ImageView addPhoto;
    ImageView edit;
    ImageView delete;
    ImageView email;
    ImageView twitter;
    ImageView facebook;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bucket_list, container, false);

        fm = getActivity().getSupportFragmentManager();
        image = (ImageView) view.findViewById(R.id.bucketlistImage);
        fm = getActivity().getSupportFragmentManager();
        list = (ListView) view.findViewById(R.id.bucketlistListView);

        Database db = new Database(getContext());
        bucketList = db.getAllBucketlist();
        db.closeDB();

        adapter = new CustomAdapter(getContext(), bucketList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BucketlistDescriptionTextView = (TextView) view.findViewById(R.id.bucketlistDescription);
                details = (TextView) view.findViewById(R.id.details);
                chevron = (ImageView) view.findViewById(R.id.chevron);
                additem = (ImageView) view.findViewById(R.id.additem);
                addPhoto = (ImageView) view.findViewById(R.id.addphoto);
                edit = (ImageView) view.findViewById(R.id.edit);
                delete = (ImageView) view.findViewById(R.id.delete);
                email = (ImageView) view.findViewById(R.id.email);
                twitter = (ImageView) view.findViewById(R.id.twitter);
                facebook = (ImageView) view.findViewById(R.id.facebook);

                sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());
                viewPager = (ViewPager) view.findViewById(R.id.imageViewpager);
                viewPager.setAdapter(sectionPagerAdapter);
                viewPager.setVisibility(View.GONE);

                additem.setImageResource(R.drawable.checkmark);
                additem.setVisibility(View.GONE);
                addPhoto.setImageResource(R.drawable.camerabutton);
                addPhoto.setVisibility(View.GONE);
                edit.setImageResource(R.drawable.editimage);
                edit.setVisibility(View.GONE);
                delete.setImageResource(R.drawable.deleteimage);
                delete.setVisibility(View.GONE);
                email.setImageResource(R.drawable.emailicon);
                email.setVisibility(View.GONE);
                twitter.setImageResource(R.drawable.twittericon);
                twitter.setVisibility(View.GONE);
                facebook.setImageResource(R.drawable.facebookicon);
                facebook.setVisibility(View.GONE);

                if(BucketlistDescriptionTextView.getText() != (bucketList.get(position)).getDescription() ){
                    //Update the text of the description
                    BucketlistDescriptionTextView.setText(((Bucketlist) list.getItemAtPosition(position)).getDescription());

                    //update the text of the show more
                    details.setText("Click to show less");
                    //update the chevron image
                    chevron.setImageResource(R.drawable.ic_expand_less_black_24dp);
//                    viewPager.setVisibility(View.VISIBLE);
//                    image.setVisibility(View.VISIBLE);
                    additem.setVisibility(View.VISIBLE);
                    addPhoto.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);

                    email.setVisibility(View.VISIBLE);
                    twitter.setVisibility(View.VISIBLE);
                    facebook.setVisibility(View.VISIBLE);
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
                Database db = new Database(getContext());
                Bucketlist location = bucketList.get(position);
                db.deleteBucketlist(location.getId());
                db.closeDB();
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
            final int pos = position;

            additem.setVisibility(View.GONE);
            addPhoto.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            twitter.setVisibility(View.GONE);
            facebook.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bucketlist_card_view, parent, false);
            }

            ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
            delete.setOnClickListener(new AdapterView.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Database db = new Database(getContext());
                    Bucketlist location = bucketList.get(pos);
                    db.deleteBucketlist(location.getId());
                    db.closeDB();
                    bucketList.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            });

            dayCounter = (TextView) convertView.findViewById(R.id.dayCounter);
            name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());

            long databaseTime = item.getTime();

            long time = System.currentTimeMillis();

            long diffInMillis = databaseTime - time;

            int diffInDays = (int) (diffInMillis / (1000 * 60 * 60 * 24));

            dayCounter.setVisibility(View.VISIBLE);

            if(diffInDays == 1) {
                dayCounter.setText(diffInDays + " day");
            } else {
                dayCounter.setText(diffInDays + " days" );
            }

            if(diffInDays <= 7) {
                dayCounter.setTextColor(Color.RED);
            } else if(diffInDays <= 30) {
                dayCounter.setTextColor(Color.YELLOW);
            } else {
                dayCounter.setTextColor(Color.parseColor("#60be6a"));
            }

            return convertView;
        }
    }

    class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm){
            super(fm);
        }

        public Fragment getItem(int position){

            switch(position){
                case 0:
                    return ImageFragment.newInstance(R.drawable.checkmark);
                case 1:
                    return ImageFragment.newInstance(R.drawable.beforeidie);
                default:
                    return ImageFragment.newInstance(R.drawable.deleteimage);
            }
        }
        public int getCount(){ return 2; }

    }

}