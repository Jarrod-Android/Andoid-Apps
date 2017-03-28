package com.liamgoodwin.beforeidie;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
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
    SectionPagerAdapter sectionPagerAdapter;
    ViewPager viewPager;
    ImageView image;
    LinearLayout galleryLayout;
    GestureDetectorCompat tapGestureDetector;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bucketlist_card_view, container, false);
        image = (ImageView) view.findViewById(R.id.bucketlistImage);
        fm = getActivity().getSupportFragmentManager();
        sectionPagerAdapter = new SectionPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.imageViewpager);
//        tapGestureDetector = new GestureDetector(this, new TapGestureListener());
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                tapGestureDetector.onTouchEvent(event);
//                return false;
//            }
//        });
        viewPager.setAdapter(sectionPagerAdapter);
        list = (ListView) view.findViewById(R.id.bucketlistListView);
//        delete = (Button) view.findViewById(R.id.delete);
        Database db = new Database(getContext());
        final ArrayList<Bucketlist> bucketList = db.getAllBucketlist();
        db.closeDB();

//        final ArrayList<Bucketlist> bucketList = new ArrayList<Bucketlist>();
//        bucketList.add(new Bucketlist("Snorkel in The Great Barrier Reef", "The Great Barrier Reef is the largest aquatic animal habitat in the world", 6666));
        final CustomAdapter adapter = new CustomAdapter(getContext(), bucketList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BucketlistDescriptionTextView = (TextView) view.findViewById(R.id.bucketlistDescription);
                TextView details = (TextView) view.findViewById(R.id.details);
                ImageView chevron = (ImageView) view.findViewById(R.id.chevron);
                ImageView additem = (ImageView) view.findViewById(R.id.additem);
                ImageView addPhoto = (ImageView) view.findViewById(R.id.addphoto);
                ImageView edit = (ImageView) view.findViewById(R.id.edit);
                ImageView delete = (ImageView) view.findViewById(R.id.delete);
//                viewPager.setVisibility(View.INVISIBLE);
//                image.setVisibility(View.INVISIBLE);
                additem.setImageResource(R.drawable.checkmark);
                additem.setVisibility(View.INVISIBLE);
                addPhoto.setImageResource(R.drawable.camerabutton);
                addPhoto.setVisibility(View.INVISIBLE);
                edit.setImageResource(R.drawable.editimage);
                edit.setVisibility(View.INVISIBLE);
                delete.setImageResource(R.drawable.deleteimage);
                delete.setVisibility(View.INVISIBLE);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");

                java.util.Date currentDate = null;
                java.util.Date bldate = null;
                try {

                    DatePicker bucketlistDate = (DatePicker) view.findViewById(R.id.datePicker);
                    bldate = dateFormat.parse("27/3/2018");

                    Calendar calandar = Calendar.getInstance();
                    int day = calandar.get(Calendar.DAY_OF_MONTH);
                    int month = calandar.get(Calendar.MONTH) + 1;
                    int year = calandar.get(Calendar.YEAR);

                    currentDate = dateFormat.parse(day + "/" + month + "/" + year);

                    //bldate = dateFormat.parse(bucketlistDate.getDayOfMonth() + "/" + bucketlistDate.getMonth() + "/" + bucketlistDate.getYear());

                    int diffInDays = (int) ((bldate.getTime() - currentDate.getTime())/ (1000 * 60 * 60 * 24));

                    TextView dayCounter = (TextView) view.findViewById(R.id.dayCounter);
                    dayCounter.setVisibility(View.VISIBLE);
                    dayCounter.setText(diffInDays + " days");

                } catch (ParseException e) {
                    e.printStackTrace();
                }

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



            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bucketlist_card_view, parent, false);
            }

//            galleryLayout = (LinearLayout) convertView.findViewById(R.id.galleryLayout);
//            //Make the gallery layout invisible
//            galleryLayout.setVisibility(View.GONE);
//            //only add items to the gallery if the gallery is empty
//            if(galleryLayout.getChildCount() == 0) {
//                image.setImageResource(R.drawable.checkmark);
//            }

            name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());


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