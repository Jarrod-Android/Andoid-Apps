package com.liamgoodwin.beforeidie;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class EditFragment extends Fragment {

    Spinner editSpin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        editSpin = (Spinner) view.findViewById(R.id.blEditSpinner);

        Database db = new Database(getContext());
        ArrayList<Bucketlist> listOfNames = db.getAllBucketlist();
        db.closeDB();

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, listOfNames);
        editSpin.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

}
