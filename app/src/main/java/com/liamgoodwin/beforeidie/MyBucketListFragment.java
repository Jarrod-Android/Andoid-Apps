package com.liamgoodwin.beforeidie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.util.ArrayList;

import static android.R.color.darker_gray;
import static com.liamgoodwin.beforeidie.R.attr.colorButtonNormal;
import io.fabric.sdk.android.Fabric;

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


    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bucket_list, container, false);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

        fm = getActivity().getSupportFragmentManager();
        list = (ListView) view.findViewById(R.id.bucketlistListView);

        Database db = new Database(getContext());
        bucketList = db.getAllBucketlist();
        db.closeDB();

        current = (Button) view.findViewById(R.id.currentBucketlist);
        current.setBackgroundColor(getResources().getColor(R.color.buttonClicked));

        completed = (Button) view.findViewById(R.id.completedBucketlist);

        current.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                current.setBackgroundColor(getResources().getColor(R.color.buttonClicked));
                completed.setBackgroundColor(getResources().getColor(R.color.buttonUnclicked));

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

                        galleryLayout.setVisibility(View.GONE);
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
                            galleryLayout.setVisibility(View.VISIBLE);
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
            }
        });

        completed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                current.setBackgroundColor(getResources().getColor(R.color.buttonUnclicked));
                completed.setBackgroundColor(getResources().getColor(R.color.buttonClicked));

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

                        galleryLayout.setVisibility(View.GONE);
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
                            galleryLayout.setVisibility(View.VISIBLE);
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

                galleryLayout.setVisibility(View.GONE);
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
                    galleryLayout.setVisibility(View.VISIBLE);
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

            delete = (ImageView) convertView.findViewById(R.id.delete);
            delete.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    Database db = new Database(getContext());
                                    Bucketlist location = bucketList.get(pos);
                                    db.deleteBucketlist(location.getId());
                                    db.closeDB();
                                    bucketList.remove(pos);
                                    adapter.notifyDataSetChanged();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

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

                    Bundle data = new Bundle();
                    data.putInt("addPhotoBucketlistID", addPhotoBucketListID);
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
                    TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                            .text("I just added " + tweetName + " to my Bucketlist! Download BeforeIDie to make your own Bucketlist");
                    builder.show();
                }
            });

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

}