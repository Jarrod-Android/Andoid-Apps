package com.liamgoodwin.beforeidie;

import android.graphics.drawable.Drawable;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


/**
 * @author Jarrod & Liam
 * @version 1.0
 * @date April 19th, 2017
 */
public class MainActivity extends ActionBarActivity implements MaterialTabListener,
    ImageFragment.OnFragmentInteractionListener,
    AddPhotoFragment.OnFragmentInteractionListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "cE8s0Za7sXYfczB2rlMg45Xmd";
    private static final String TWITTER_SECRET = "jBhdrlXa1zrnJTULLC1DQzjAF2GA606spCGEJGS7hiyxkZ37Cq";

    MaterialTabHost tabHost;
    ViewPager viewPager;
    ViewPagerAdapter androidAdapter;
    Toolbar toolBar;
    ImageView settingsButton;
    private TwitterLoginButton loginButton;
    TwitterAuthConfig authConfig;
    Button accountLogin;
    AdapterView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //creating twitter author config
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        //using fabric to confirm
        authConfig =  new TwitterAuthConfig("consumerKey", "consumerSecret");
        Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());

        //adding in settings button and giving intent to launch settings activity
        settingsButton = (ImageView) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        //loading twitter login function
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                String username = result.data.getUserName();
                TwitterSession userInfo = result.data;
                String userName = userInfo.getUserName();
                //changing the text to the current users username
                loginButton.setText("@" + userName);
                //TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                //String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(MainActivity.this, username, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


        //android toolbar
        toolBar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolBar);
        this.setSupportActionBar(toolBar);

        //tab host
        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        viewPager = (ViewPager) this.findViewById(R.id.viewPager);

        //adapter view
        androidAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(androidAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setCurrentItem(2);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int tabposition) {
                tabHost.setSelectedNavigationItem(tabposition);
            }
        });

        //for tab position
        for (int i = 1; i <= androidAdapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
//                            .setIcon(getDrawable(R.drawable.camerabutton))
                            .setText(androidAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    //tab on selected
    @Override
    public void onTabSelected(MaterialTab materialTab) {

        viewPager.setCurrentItem(materialTab.getPosition());
    }

    //tab on reselected
    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    //tab on unselected
    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // view pager adapter to switch between views/fragments
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        //loading the correct fragment based on current view
        public Fragment getItem(int num) {

            switch(num) {
                case 0:
                    return new MyBucketListFragment();
                case 1:
                    return new MainFragment();
                case 2:
                    return new AddToMyBucketListFragment();
                default:
                    return new MainFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        /**
         * Setting the tab names
         * @param tabposition used to get tab postion
         * @return the name of the tab item
         */
        @Override
        public CharSequence getPageTitle(int tabposition) {

            switch(tabposition) {
                case 1:
                    return "My List";
                case 2:
                    return "Home";
                case 3:
                    return "Add";
                default:
                    return "Home";
            }
        }

        /**
         *
         * @param pos used to get the current postion
         * @return
         */
        public Drawable getIcon (int pos) {

            switch(pos) {
                case 1:
                    return getDrawable(R.drawable.deleteimage);
                case 2:
                    return getDrawable(R.drawable.editimage);
                case 3:
                    return getDrawable(R.drawable.twittericon);
                case 4:
                    return getDrawable(R.drawable.facebookicon);
                default:
                    return getDrawable(R.drawable.checkmark);
            }
        }
    }

    //Zoom out page transformer to add animation when swiping
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    @Override
    public void onBackPressed() { }
}