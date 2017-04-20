package com.liamgoodwin.beforeidie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Jarrod & Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class MyBucketListFragment extends Fragment {

    FragmentManager fm;
    TextView name;
    ListView list;
    TextView BucketlistDescriptionTextView;
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
    GridLayout galleryLayout;
    String companyEmail = "beforeidie@gmail.com";
    Button current;
    Button completed;
    SharedPreferences pref;


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bucket_list, container, false);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

        fm = getActivity().getSupportFragmentManager();
        list = (ListView) view.findViewById(R.id.bucketlistListView);

        //calling in the preference setting to change order
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String option = pref.getString("order", "1");

        int orderSelected = Integer.parseInt(option);

        //running the correct query based on settings
        if (orderSelected != 2) {
            Database dbb = new Database(getContext());
            bucketList = dbb.getAllAscendingBucketlist();
            dbb.closeDB();
        } else {
            Database ab = new Database(getContext());
            bucketList = ab.getAllDescendingBucketlist();
            ab.closeDB();
        }

        current = (Button) view.findViewById(R.id.currentBucketlist);
        current.setBackgroundColor(getResources().getColor(R.color.buttonClicked));

        completed = (Button) view.findViewById(R.id.completedBucketlist);

        current.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                current.setBackgroundColor(getResources().getColor(R.color.buttonClicked));
                completed.setBackgroundColor(getResources().getColor(R.color.buttonUnclicked));

                //calling the bucketlist items
                Database db = new Database(getContext());
                bucketList = db.getAllBucketlist();
                db.closeDB();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.addToBackStack(null);

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

                        //adding all buttons and textviews to card views
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

                        if (galleryLayout.getVisibility() == View.GONE || galleryLayout.getVisibility() == View.INVISIBLE) {

                            //Update the text of the description
                            BucketlistDescriptionTextView.setText(((Bucketlist) list.getItemAtPosition(position)).getDescription());

                            //update the text of the show more
                            details.setText("Click to show less");
                            //update the chevron image
                            chevron.setImageResource(R.drawable.ic_expand_less_black_24dp);
                            galleryLayout.setVisibility(View.VISIBLE);
                            additem.setVisibility(View.VISIBLE);
                            addPhoto.setVisibility(View.VISIBLE);
                            edit.setVisibility(View.VISIBLE);
                            delete.setVisibility(View.VISIBLE);
                            email.setVisibility(View.VISIBLE);
                            twitter.setVisibility(View.VISIBLE);
                        } else {
                            galleryLayout.setVisibility(View.GONE);

                            BucketlistDescriptionTextView.setText("");
                            //update the text of the show more
                            details.setText("Click to show more");
                            //update the chevron image
                            chevron.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        }
                    }
                });
            }
        });

        //used to load the completed items
        completed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                current.setBackgroundColor(getResources().getColor(R.color.buttonUnclicked));
                completed.setBackgroundColor(getResources().getColor(R.color.buttonClicked));

                //running the completed items query
                Database db = new Database(getContext());
                bucketList = db.getAllBucketlistCompleted();
                db.closeDB();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.addToBackStack(null);

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

                        //populating completed layout
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

                        if (galleryLayout.getVisibility() == View.GONE || galleryLayout.getVisibility() == View.INVISIBLE) {
                            //Update the text of the description
                            BucketlistDescriptionTextView.setText(((Bucketlist) list.getItemAtPosition(position)).getDescription());

                            //update the text of the show more
                            details.setText("Click to show less");
                            //update the chevron image
                            chevron.setImageResource(R.drawable.ic_expand_less_black_24dp);
                            galleryLayout.setVisibility(View.VISIBLE);
                            addPhoto.setVisibility(View.VISIBLE);
                            edit.setVisibility(View.VISIBLE);
                            delete.setVisibility(View.VISIBLE);
                            email.setVisibility(View.VISIBLE);
                            twitter.setVisibility(View.VISIBLE);
                        } else {
                            galleryLayout.setVisibility(View.GONE);

                            BucketlistDescriptionTextView.setText("");
                            //update the text of the show more
                            details.setText("Click to show more");
                            //update the chevron image
                            chevron.setImageResource(R.drawable.ic_expand_more_black_24dp);
                        }
                    }
                });

            }
        });

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

                //populating card view
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

                if (galleryLayout.getVisibility() == View.GONE || galleryLayout.getVisibility() == View.INVISIBLE) {
                    //Update the text of the description
                    BucketlistDescriptionTextView.setText(((Bucketlist) list.getItemAtPosition(position)).getDescription());

                    //update the text of the show more
                    details.setText("Click to show less");
                    //update the chevron image
                    chevron.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    galleryLayout.setVisibility(View.VISIBLE);
                    additem.setVisibility(View.VISIBLE);
                    addPhoto.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                    delete.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    twitter.setVisibility(View.VISIBLE);
                } else {
                    galleryLayout.setVisibility(View.GONE);

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

    //custom adapter to make custom card views
    public class CustomAdapter extends ArrayAdapter<Bucketlist> {

        /**
         *
         * @param context used to get context
         * @param items used for the array list
         */
        public CustomAdapter(Context context, ArrayList<Bucketlist> items) {
            super(context, 0, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final Bucketlist item = getItem(position);
            final int pos = position;

            //setting the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bucketlist_card_view, parent, false);
            }

            galleryLayout = (GridLayout) convertView.findViewById(R.id.galleryLayout);
            //Make the gallery layout invisible
            galleryLayout.setVisibility(View.GONE);
            //only add items to the gallery if the gallery is empty
            if(galleryLayout.getChildCount() == 0){
                //Grab all the photos that match the id of the current location
                Database db = new Database(getContext());
                ArrayList<Image> pics = db.getAllImages(item.getId());
                db.closeDB();
                //Add those photos to the gallery
                for(int i =0; i < pics.size(); i++){
                    File image = new File(pics.get(i).getResource());
                    ImageView imageView = new ImageView(getContext());
                    Picasso.with(getContext()).load(image).resize(280, 280).centerCrop().into(imageView);
                    galleryLayout.addView(imageView);
                }
            }

            //delete button action
            delete = (ImageView) convertView.findViewById(R.id.delete);
            delete.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //creating a pop up to confirm the delete or not.
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                //if delete is selected delete it from the database and view
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    Database db = new Database(getContext());
                                    Bucketlist location = bucketList.get(pos);
                                    db.deleteBucketlist(location.getId());
                                    db.closeDB();
                                    Toast.makeText(getActivity(), "'" + location.getName() + "' has been deleted",
                                            Toast.LENGTH_LONG).show();
                                    bucketList.remove(pos);
                                    adapter.notifyDataSetChanged();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    //poping up the alert box and givign values.
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Are you sure you want to delete this" + "?");
                            builder.setPositiveButton("Yes", dialogClickListener);
                            builder.setNegativeButton("Cancel", dialogClickListener).show();
                }
            });

            addPhoto = (ImageView) convertView.findViewById(R.id.addphoto);
            addPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String bucketListItemName = item.getName();
                    int addPhotoBucketListID = item.getId();

                    //sending over the bucketlist id and name
                    Bundle data = new Bundle();
                    data.putInt("addPhotoBucketlistID", addPhotoBucketListID);
                    data.putString("bucketListItemName", bucketListItemName);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    //starting to load the addphoto fragment
                    AddPhotoFragment newFrag = new AddPhotoFragment();
                    newFrag.setArguments(data);

                    transaction.addToBackStack(null);
                    transaction.replace(R.id.mainActivity, newFrag);
                    transaction.commit();
                }
            });

            //intent used to load twitter and make a tweet
            Intent tweetIntent = new Intent(Intent.ACTION_SEND);
            tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
            tweetIntent.setType("text/plain");

            final String emailItem = item.getName();

            email = (ImageView) convertView.findViewById(R.id.email);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //intent used to load the email app and put some default infromation
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


                    //getting the text to send over
                    int editBucketListID = item.getId();
                    String editBucketListItemName = item.getName();
                    String editBucketListItemDescription = item.getDescription();

                    Bundle editBundle = new Bundle();

                    //sending over the id, name, and description
                    editBundle.putInt("editBucketListID", editBucketListID);
                    editBundle.putString("editBucketListItemName", editBucketListItemName);
                    editBundle.putString("editBucketListItemDescription", editBucketListItemDescription);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    //starting the new fragment
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

                    //pulling the different attributes about the item
                    int completedID = item.getId();
                    String completedName = item.getName();
                    String completedDescription = item.getDescription();
                    Long completedmillis = item.getTime();
                    int completedField = 1;

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();

                    //adding the values in a array
                    Bucketlist bucketlist = new Bucketlist(completedID, completedName,
                            completedDescription, completedmillis, completedField);

                    //updating the bucketlist to change to completed
                    Database db = new Database(getContext());
                    db.updateBucketlist(bucketlist);
                    db.closeDB();

                    //Toast to show it was added
                    Toast.makeText(getActivity(), "'" + completedName + "' was added to the Completed tab",
                            Toast.LENGTH_LONG).show();
                }
            });


            dayCounter = (TextView) convertView.findViewById(R.id.dayCounter);
            name = (TextView) convertView.findViewById(R.id.name);
            name.setText(item.getName());

            final String tweetName = item.getName();


            twitter = (ImageView) convertView.findViewById(R.id.twitter);
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //used to make a new tweet with defaut information
                    TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                            .text("I just added " + tweetName + " to my Bucketlist! Download BeforeIDie to make your own Bucketlist");
                    builder.show();
                }
            });

            //getting the entered time
            long databaseTime = item.getTime();


            //getting system time
            long time = System.currentTimeMillis();

            long diffInMillis = databaseTime - time;

            //conversion to calculate days left
            int diffInDays = (int) (diffInMillis / (1000 * 60 * 60 * 24));

            dayCounter.setVisibility(View.VISIBLE);


            //used to see what preference item is selected
            pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String option = pref.getString("order", "1");

            int orderSelected = Integer.parseInt(option);

            //running the correct query based on settings
            if (orderSelected != 2) {
                Database dbb = new Database(getContext());
                bucketList = dbb.getAllAscendingBucketlist();
                dbb.closeDB();
            } else {
                Database ab = new Database(getContext());
                bucketList = ab.getAllDescendingBucketlist();
                ab.closeDB();
            }


            //making different text and colour outputs based on number of days left
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

}