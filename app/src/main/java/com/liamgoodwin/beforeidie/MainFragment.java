package com.liamgoodwin.beforeidie;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by HP on 4/4/2016.
 */

public class MainFragment extends Fragment {

    //Declare the list view for the CardView usage on the Main page
    ListView list;
    TextView LocationText;
    Button LearnMore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        Map<String, String> recommendationsName = new HashMap<String, String>();

        recommendationsName.put("0", "Paris, France");
        recommendationsName.put("1", "New Zealand");
        recommendationsName.put("2", "New York City");
        recommendationsName.put("3", "Grand Canyon");
        recommendationsName.put("4", "Mauna Loa");

        Map<String, String> recommendations = new HashMap<String, String>();

        recommendations.put("Paris, France", "Paris, France's capital, is a major European city and a global center for art, fashion, gastronomy and culture. Its 19th-century cityscape is crisscrossed by wide boulevards and the River Seine. Beyond such landmarks as the Eiffel Tower and the 12th-century, Gothic Notre-Dame cathedral, the city is known for its cafe culture and designer boutiques along the Rue du Faubourg Saint-Honoré.");
        recommendations.put("New Zealand", "New Zealand is a country in the southwestern Pacific Ocean consisting of 2 main islands, both marked by volcanoes and glaciation. Capital Wellington, on the North Island, is home to Te Papa Tongarewa, the expansive national museum. Wellington’s dramatic Mt. Victoria, along with the South Island’s Fiordland and Southern Lakes, stood in for mythical Middle Earth in Peter Jackson’s \"Lord of the Rings\" films.");
        recommendations.put("New York", "New York City comprises 5 boroughs sitting where the Hudson River meets the Atlantic Ocean. At its core is Manhattan, a densely populated borough that’s among the world’s major commercial, financial and cultural centers. Its iconic sites include skyscrapers such as the Empire State Building and sprawling Central Park. Broadway theater is staged in neon-lit Times Square.");
        recommendations.put("Grand Canyon", "The Grand Canyon in Arizona is a natural formation distinguished by layered bands of red rock, revealing millions of years of geological history in cross-section. Vast in scale, the canyon averages 10 miles across and a mile deep along its 277-mile length. Much of the area is a national park, with Colorado River white-water rapids and sweeping vistas.");
        recommendations.put("Mauna Loa", "Mauna Loa is one of five volcanoes that form the Island of Hawaii in the U.S. state of Hawaiʻi in the Pacific Ocean. The largest subaerial volcano in both mass and volume, Mauna Loa has historically been considered the largest volcano on Earth.");

        Random r = new Random();

        List<String> titles = new ArrayList<String>(recommendationsName.keySet());
        String randomName = titles.get(r.nextInt(titles.size()));

        List<String> keys = new ArrayList<String>(recommendations.keySet());
        String randomKey = keys.get(r.nextInt(keys.size()));

        String name = "";

        LocationText = (TextView) view.findViewById(R.id.locationText);
        LearnMore = (Button) view.findViewById(R.id.learnMore);

        LocationText.setText(recommendationsName.get(randomKey));

        return view;
    }
}