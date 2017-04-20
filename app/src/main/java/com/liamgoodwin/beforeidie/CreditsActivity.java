package com.liamgoodwin.beforeidie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author Jarrod and Liam
 * @version 1.0
 * @date April 19th, 2017
 */

public class CreditsActivity extends AppCompatActivity {

    TextView link1;
    TextView link2;
    TextView link3;
    TextView link4;
    TextView link5;
    TextView link6;
    TextView link7;
    TextView link8;
    TextView link9;
    TextView link10;
    TextView link11;

    /**
     * @author Jarrod and Liam
     * @version 1.0
     *
     * onCreate sets everything up in the view when we load this fragment
     *
     * @param savedInstanceState the bundle that holds all of the current info
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        link1 = (TextView) findViewById(R.id.link1);
        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/remove-button-delete-denied-red-151678"));
                startActivity(browserIntent);
            }
        });

        link2 = (TextView) findViewById(R.id.link2);
        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.clipartkid.com/simple-bucket-clip-art-at-clker-com-vector-clip-art-online-royalty-xoP3uv-clipart"));
                startActivity(browserIntent);
            }
        });

        link3 = (TextView) findViewById(R.id.link3);
        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/volcanoes-magma-lava-mountains-691939/"));
                startActivity(browserIntent);
            }
        });

        link4 = (TextView) findViewById(R.id.link4);
        link4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/grand-canyon-arizona-landscape-1995038/"));
                startActivity(browserIntent);
            }
        });

        link5 = (TextView) findViewById(R.id.link5);
        link5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/new-york-skyline-new-york-city-city-668616/"));
                startActivity(browserIntent);
            }
        });

        link6 = (TextView) findViewById(R.id.link6);
        link6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/aoraki-mount-cook-mountain-90388/"));
                startActivity(browserIntent);
            }
        });

        link7 = (TextView) findViewById(R.id.link7);
        link7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/paris-france-eiffel-tower-night-1836415/"));
                startActivity(browserIntent);
            }
        });

        link8 = (TextView) findViewById(R.id.link8);
        link8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/confirmation-symbol-icon-green-1152155/"));
                startActivity(browserIntent);
            }
        });

        link9 = (TextView) findViewById(R.id.link9);
        link9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/camera-photography-photo-old-camera-2155318/"));
                startActivity(browserIntent);
            }
        });

        link10 = (TextView) findViewById(R.id.link10);
        link10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/edit-icon-icons-matt-paste-symbol-1294441/"));
                startActivity(browserIntent);
            }
        });

        link11 = (TextView) findViewById(R.id.link11);
        link11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/en/envelope-postage-letter-mail-158279/"));
                startActivity(browserIntent);
            }
        });
    }
}
