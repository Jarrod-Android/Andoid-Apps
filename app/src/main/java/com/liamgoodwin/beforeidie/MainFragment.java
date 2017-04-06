package com.liamgoodwin.beforeidie;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.support.v4.content.res.ResourcesCompat.getDrawable;

/**
 * Created by HP on 4/4/2016.
 */

public class MainFragment extends Fragment {

    //Declare the list view for the CardView usage on the Main page
    ListView list;
    TextView LocationText;
    Button LearnMore;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        Map<String, String> recommendationsName = new HashMap<String, String>();

        recommendationsName.put("0", "Paris, France");
        recommendationsName.put("1", "New Zealand");
        recommendationsName.put("2", "New York City");
        recommendationsName.put("3", "Grand Canyon");
        recommendationsName.put("4", "Mauna Loa");

        Map<String, String> recommendationsDescription = new HashMap<String, String>();

        recommendationsDescription.put("Paris, France", "Paris, France's capital, is a major European city and a global center for art, fashion, gastronomy and culture. Its 19th-century cityscape is crisscrossed by wide boulevards and the River Seine. Beyond such landmarks as the Eiffel Tower and the 12th-century, Gothic Notre-Dame cathedral, the city is known for its cafe culture and designer boutiques along the Rue du Faubourg Saint-Honoré.");
        recommendationsDescription.put("New Zealand", "New Zealand is a country in the southwestern Pacific Ocean consisting of 2 main islands, both marked by volcanoes and glaciation. Capital Wellington, on the North Island, is home to Te Papa Tongarewa, the expansive national museum. Wellington’s dramatic Mt. Victoria, along with the South Island’s Fiordland and Southern Lakes, stood in for mythical Middle Earth in Peter Jackson’s \"Lord of the Rings\" films.");
        recommendationsDescription.put("New York", "New York City comprises 5 boroughs sitting where the Hudson River meets the Atlantic Ocean. At its core is Manhattan, a densely populated borough that’s among the world’s major commercial, financial and cultural centers. Its iconic sites include skyscrapers such as the Empire State Building and sprawling Central Park. Broadway theater is staged in neon-lit Times Square.");
        recommendationsDescription.put("Grand Canyon", "The Grand Canyon in Arizona is a natural formation distinguished by layered bands of red rock, revealing millions of years of geological history in cross-section. Vast in scale, the canyon averages 10 miles across and a mile deep along its 277-mile length. Much of the area is a national park, with Colorado River white-water rapids and sweeping vistas.");
        recommendationsDescription.put("Mauna Loa", "Mauna Loa is one of five volcanoes that form the Island of Hawaii in the U.S. state of Hawaiʻi in the Pacific Ocean. The largest subaerial volcano in both mass and volume, Mauna Loa has historically been considered the largest volcano on Earth.");

        Map<String, Drawable> recommendationsImage = new HashMap<String, Drawable>();

        recommendationsImage.put("Paris, France", getDrawable(getResources(), R.drawable.beforeidie, null));
        recommendationsImage.put("New Zealand", getDrawable(getResources(), R.drawable.beforeidie, null));
        recommendationsImage.put("New York", getDrawable(getResources(), R.drawable.beforeidie, null));
        recommendationsImage.put("Grand Canyon", getDrawable(getResources(), R.drawable.beforeidie, null));
        recommendationsImage.put("Mauna Loa", getDrawable(getResources(), R.drawable.beforeidie, null));

        Random r = new Random();
        int randomNum = r.nextInt(5);

        List<String> titles = new ArrayList<String>(recommendationsName.keySet());
        String randomName = titles.get(randomNum);

        List<String> keys = new ArrayList<String>(recommendationsDescription.keySet());
        String randomDescription = keys.get(randomNum);

        List<String> images = new ArrayList<String>(recommendationsImage.keySet());
        Drawable randomImage = Drawable.createFromPath(images.get(randomNum));

        LocationText = (TextView) view.findViewById(R.id.locationText);
        LocationText.setText(recommendationsName.get(randomName));

        final String name = recommendationsName.get(randomName);
        final String description = recommendationsDescription.get(randomDescription);
        final Drawable image = recommendationsImage.get(randomImage);

        LearnMore = (Button) view.findViewById(R.id.learnMore);
        LearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(inflater, view, name, description, image);
            }
        });

        return view;
    }

    public void showPopup(LayoutInflater inflater, View anchorView, String name, String description, Drawable image) {

        View popupView = inflater.inflate(R.layout.popup_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView popupName = (TextView) popupView.findViewById(R.id.popupName);
        ImageView popupImage = (ImageView) popupView.findViewById(R.id.popupImage);
        TextView popupDescription = (TextView) popupView.findViewById(R.id.popupDescription);

        popupName.setText(name);
        popupImage.setImageDrawable(image);
        popupDescription.setText(description);

        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorView.getHeight());

        //For exit button
        //popupwindow.dismiss();
    }

}