package com.liamgoodwin.beforeidie;

import android.annotation.TargetApi;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by liamgoodwin on 2017-03-27.
 */
public class AddToMyBucketListFragment extends Fragment {

    EditText name;
    EditText description;
    DatePicker date;
    FragmentManager fm;

    public AddToMyBucketListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_to_my_bucket_list, container, false);

        name = (EditText) view.findViewById(R.id.nameEditText);
        description = (EditText) view.findViewById(R.id.descriptionEditText);
        date = (DatePicker) view.findViewById(R.id.datePicker);

        Button submit = (Button) view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDayOfMonth());
                long millis = calendar.getTimeInMillis();

//                String formattedDate = date.getDayOfMonth() + "/" + date.getMonth() + "/" + date.getYear();


                Bucketlist bucketlist = new Bucketlist(name.getText().toString(),
                        description.getText().toString(), millis);

                Database db = new Database(getContext());
                db.addBucketlist(bucketlist);
                db.closeDB();

                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();

            }
        });

        return view;
    }

}