package com.liamgoodwin.beforeidie;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * @author Jarrod and Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class AddPhotoFragment extends Fragment {

    //Declarations
    TextView title;
    ImageView cameraButton;
    LinearLayout galleryLayout;
    FragmentManager fm;
    Button returnButton;
    int addPhoto;

    private static final int CAMERA_INTENT = 1;
    private String imageLocation;

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * onCreateView inflates the view we tell it to with the following code
     * in this instance it inflates the fragment_add_photo layout
     *
     * @param  inflater  inflates the Layout
     * @param  container specifies the ViewGroup
     * @param  savedInstanceState bundle to hold the core data of the View
     * @return returns the view to inflate it
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Declare and inflate the view
        View view = inflater.inflate(R.layout.fragment_add_photo, container, false);

        //Request the permissions
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"},
                1);

        //Make a bundle and set the arguments
        Bundle extras = getArguments();
        String bucketListItemName = extras.getString("bucketListItemName");
        addPhoto = extras.getInt("addPhotoBucketlistID");

        //Set the value of the views
        galleryLayout = (LinearLayout) view.findViewById(R.id.galleryLayout);
        title = (TextView) view.findViewById(R.id.addPhotoTitle);
        title.setText(bucketListItemName);

        //Return button onClickListener, sets the view to the previous fragment
        returnButton = (Button) view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        //Sets the camera button
        cameraButton = (ImageView) view.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File picture = null;
                try{
                    picture = createImage();
                }catch(IOException e){
                    e.printStackTrace();
                }

                //Set an intent and the image properties
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(i, CAMERA_INTENT);
                }

            }
        });

        //Return the view
        return view;
    }

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * onActivityResult gets a result back from an activity when it ends
     *
     * @param  requestCode  determines what is requested by the user
     * @param  resultCode holds the result requested by the user
     * @param  data Holds the intent data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_INTENT && resultCode == RESULT_OK){
            Bitmap image = BitmapFactory.decodeFile(imageLocation);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(image);
            galleryLayout.addView(imageView);

            //Open the database, and add the photo
            Database db = new Database(getContext());
            int picID = db.addImage(new Image(imageLocation));
            if(picID != -1){
                db.addImageLocation(picID, addPhoto);
                Toast.makeText(getActivity(), "Photo Added",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getActivity(), "Photo Not Added",
                        Toast.LENGTH_LONG).show();
            }
            db.closeDB();
        }
    }

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * createImage will set the Data, fileName, directory, and combine these
     * into a usable image
     *
     * @return the picture
     */
    File createImage() throws IOException{

        //Create a timestamp to help create a collision free name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHss").format(new Date());

        //Create the name of the image
        String fileName = "before_i_die_" + timeStamp;

        //Grab the directory we want to save the image
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //Create the image in that directory
        File picture = File.createTempFile(fileName, ".jpg", directory);

        //Save the location of the image
        imageLocation = picture.getAbsolutePath();
        return picture;
    }

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * onRequestPermissionsResult determines the permissions requested
     * and the permissions that are granted access and returns them
     *
     * @param  requestCode  what is requested by the user
     * @param  permissions  array of the permissions
     * @param  grantResults  array of the permissions that are granted
     * @return the permissions granted
     */
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
