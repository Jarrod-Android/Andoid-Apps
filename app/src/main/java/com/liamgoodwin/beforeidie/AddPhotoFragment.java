package com.liamgoodwin.beforeidie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AddPhotoFragment extends Fragment {

    TextView title;
    ImageView cameraButton;
    LinearLayout galleryLayout;
    TextView icon;
    TextView bucketlistItem;

    private static final int CAMERA_INTENT = 1;
    private String imageLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_photo, container, false);

        Bundle extras = getArguments();
        String bucketListItemName = extras.getString("bucketListItemName");

        galleryLayout = (LinearLayout) view.findViewById(R.id.galleryLayout);
        title = (TextView) view.findViewById(R.id.addPhotoTitle);
        title.setText(bucketListItemName);

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
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(i, CAMERA_INTENT);
                }
            }
        });

        Button returnButton = (Button) view.findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_INTENT && resultCode == RESULT_OK){
            Bitmap image = BitmapFactory.decodeFile(imageLocation);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(image);
            galleryLayout.addView(imageView);
            /**
             * Add the photo to the database
             */
            Database db = new Database(getContext());
            int picID = db.addImage(new Image(imageLocation));
            if(picID != -1){
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

    File createImage() throws IOException{
        //Create a timestamp to help create a collision free name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHss").format(new Date());
        //Create the name of the image
        String fileName = "before_i_die_" + timeStamp;
        //Grab the directory we want to save the image
        File directory =
                Environment.
                        getExternalStoragePublicDirectory
                                (Environment.DIRECTORY_PICTURES);
        //Create the image in that directory
        File picture = File.createTempFile(fileName, ".jpg", directory);
        //Save the location of the image
        imageLocation = picture.getAbsolutePath();
        return picture;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
