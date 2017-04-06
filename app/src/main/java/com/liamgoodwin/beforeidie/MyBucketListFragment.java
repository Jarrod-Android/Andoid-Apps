package com.liamgoodwin.beforeidie;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.id.message;
import static android.content.ContentValues.TAG;

public class MyBucketListFragment extends Fragment {

    FragmentManager fm;
    FragmentTransaction ft;
    TextView name;
    TextView description;
    ListView list;
    TextView BucketlistDescriptionTextView;
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
    String companyEmail = "beforeidie@gmail.com";
    Button current;
    Button completed;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bucket_list, container, false);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

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
                current = (Button) view.findViewById(R.id.currentBucketlist);
                completed = (Button) view.findViewById(R.id.completedBucketlist);

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

                if (BucketlistDescriptionTextView.getText() != (bucketList.get(position)).getDescription()) {
                    //Update the text of the description
                    BucketlistDescriptionTextView.setText(((Bucketlist) list.getItemAtPosition(position)).getDescription());

                    //update the text of the show more
                    details.setText("Click to show less");
                    //update the chevron image
                    chevron.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    additem.setVisibility(View.VISIBLE);
                    addPhoto.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    twitter.setVisibility(View.VISIBLE);
                } else {
                    BucketlistDescriptionTextView.setText("");
                    //update the text of the show more
                    details.setText("Click to show more");
                    //update the chevron image
                    chevron.setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
            }
        });
        return view;
    }

    public class CustomAdapter extends ArrayAdapter<Bucketlist> {

        public CustomAdapter(Context context, ArrayList<Bucketlist> items) {
            super(context, 0, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final Bucketlist item = getItem(position);
            final int pos = position;
            final View view;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bucketlist_card_view, parent, false);
            }

            delete = (ImageView) convertView.findViewById(R.id.delete);
            delete.setOnClickListener(new AdapterView.OnClickListener() {
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

            addPhoto = (ImageView) convertView.findViewById(R.id.addphoto);
            addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String bucketListItemName = item.getName();

                    Bundle data = new Bundle();
                    data.putString("bucketListItemName", bucketListItemName);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    AddPhotoFragment newFrag = new AddPhotoFragment();
                    newFrag.setArguments(data);

                    transaction.addToBackStack(null);
                    transaction.replace(R.id.mainActivity, newFrag);
                    transaction.commit();
                }
            });


            Intent tweetIntent = new Intent(Intent.ACTION_SEND);
            tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
            tweetIntent.setType("text/plain");

            //PackageManager packManager = getPackageManager();
           // List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_DEFAULT_ONLY);

//            boolean resolved = false;
//            for(ResolveInfo resolveInfo: resolvedInfoList){
//                if(resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")){
//                    tweetIntent.setClassName(
//                            resolveInfo.activityInfo.packageName,
//                            resolveInfo.activityInfo.name );
//                    resolved = true;
//                    break;
//                }
//            }
//            if(resolved){
//                startActivity(tweetIntent);
//            }else{
//                Intent i = new Intent();
//                i.putExtra(Intent.EXTRA_TEXT, "Hello Download our app");
//                i.setAction(Intent.ACTION_VIEW);
//                i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + (message));
//                startActivity(i);
//               // Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
//            }
//
            final String emailItem = item.getName();

            email = (ImageView) convertView.findViewById(R.id.email);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", companyEmail, null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "My Bucketlist");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hey, I just added " + emailItem + " to my Bucklist. Download BeforeIDie so you can do it too");
                    startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                }
            });

            edit = (ImageView) convertView.findViewById(R.id.edit);
            edit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    int editBucketListID = item.getId();
                    String editBucketListItemName = item.getName();
                    String editBucketListItemDescription = item.getDescription();

                    Bundle editBundle = new Bundle();

                    //editBundle.putParcelable("bucketlistItem", (Parcelable) item);
                    editBundle.putInt("editBucketListID", editBucketListID);
                    editBundle.putString("editBucketListItemName", editBucketListItemName);
                    editBundle.putString("editBucketListItemDescription", editBucketListItemDescription);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    EditFragment editFrag = new EditFragment();
                    editFrag.setArguments(editBundle);

                    transaction.addToBackStack(null);
                    transaction.replace(R.id.mainActivity, editFrag);
                    transaction.commit();
                }
            });

            additem = (ImageView) convertView.findViewById(R.id.additem);
            additem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    int completedID = item.getId();
                    String completedName = item.getName();
                    String completedDescription = item.getDescription();
                    Long completedmillis = item.getTime();
                    int completedField = 1;

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    Bucketlist bucketlist = new Bucketlist(completedID, completedName,
                            completedDescription, completedmillis, completedField);

                    Database db = new Database(getContext());
                    db.updateBucketlist(bucketlist);
                    db.closeDB();
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

            if(diffInDays <= 0) {
                dayCounter.setText("Expired");
            } else if(diffInDays == 1) {
                dayCounter.setText(diffInDays + " day");
            } else {
                dayCounter.setText(diffInDays + " days");
            }

            if (diffInDays <= 7) {
                dayCounter.setTextColor(Color.RED);
            } else if (diffInDays <= 30) {
                dayCounter.setTextColor(Color.YELLOW);
            } else {
                dayCounter.setTextColor(Color.parseColor("#60be6a"));
            }

            return convertView;
        }
    }

    class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return ImageFragment.newInstance(R.drawable.checkmark);
                case 1:
                    return ImageFragment.newInstance(R.drawable.beforeidie);
                default:
                    return ImageFragment.newInstance(R.drawable.deleteimage);
            }
        }

        public int getCount() {
            return 2;
        }

    }

}