package com.example.sketchapk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import androidx.navigation.Navigation;



public class Category extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Category() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category.
     */
    // TODO: Rename and change types and number of parameters
    public static Category newInstance(String param1, String param2) {
        Category fragment = new Category();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button1 = getView().findViewById(R.id.buttonConfirm);

        Bundle b1 = getArguments();
        final Bundle b2 = b1;
        String amountStr = b1.getString("amountStr");

        button1.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.catToPaint, b2));

        //ACCESS THE SKETCHES.TXT FILE AND MAKE AN ARRAY OF CATEGORIES
        Vector<String> tagVec = new Vector<String>(10, 5); //array of available tags

        try {
            InputStream sketches = getContext().getAssets().open("sketches.txt");
            BufferedReader is = new BufferedReader(new InputStreamReader(sketches, "UTF8"));
            String line;
            Vector<Dbimage> images = new Vector<Dbimage>(3,1);  //array of image structure
            line = is.readLine();
            while ((line = is.readLine()) != null) {
                Log.d("OMAN", line);
                Dbimage animage = new Dbimage(line);    //building an image object using the line of data from db file
                images.add(animage);                    //adding a new image into the vector of images
                for (int i = 0; i < images.size(); i++) {
                    int tagAmount = images.elementAt(i).tags.length;    //we'll be checking all tags of the current image
                    boolean isPresent = false;
                    for (int j = 0; j < tagAmount; j++) {
                        for (int z = 0; z < tagVec.size(); z++) {
                            if (images.elementAt(i).tags[j].trim().equals(tagVec.elementAt(z).trim())) {
                                isPresent = true;
                            }
                            else {
                                Log.d("IDENTICAL", images.elementAt(i).tags[j] + "_" + tagVec.elementAt(z));
                            }
                        }
                        if (!isPresent) {
                            tagVec.add(images.elementAt(i).tags[j]);
                            Log.d("ARRAYTAG", images.elementAt(i).tags[j]);
                        }
                        else {
                            isPresent = false;
                        }
                    }
                }
            }


        }
        catch (IOException e) {

        }


        List<String> tagArray = new ArrayList<>();
        for (int i = 0; i < tagVec.size(); i++) {
            tagArray.add(tagVec.elementAt(i));

        }
        List<String> tagArray2 = new ArrayList<>();
        for (int d = 0; d < tagVec.size(); d++) {
            tagArray2.add(tagVec.elementAt(d));
        }
        tagArray.add(0,"default");
        tagArray2.add(0,"nothing");


        // Dropdown menus:

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_text, tagArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_text, tagArray2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        Spinner spinner1 = getView().findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter1);

        Spinner spinner2 = getView().findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter2);

        Spinner spinner3 = getView().findViewById(R.id.spinner3);
        spinner3.setAdapter(adapter2);


         //bundle for passing the timer, amount of images and tags (added in this fragment)
        int amountInt = b2.getInt("amountInt");
        b2.putInt("amountInt", amountInt);  //put in the amount, which we got on the amount selection screen
        b2.putString("amountStr", amountStr);
        final Random rand = new Random();
        int cnt =adapter1.getCount();
        cnt = cnt++;
        final int n = rand.nextInt(cnt);


        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adview, View view, int position, long id) {
                // On selecting a spinner item
                String spinValue = adview.getItemAtPosition(position).toString();
                if (!(spinValue.equals("default"))){
                    b2.putString("tag1", spinValue);
                }else{
                    spinValue = adview.getItemAtPosition(n).toString();
                    b2.putString("tag1", spinValue);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adview) {
                //what am I supposed to do?
            }
        });

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adview, View view, int position, long id) {
                // On selecting a spinner item
                String spinValue = adview.getItemAtPosition(position).toString();
                if (!(spinValue.equals("nothing"))){
                    b2.putString("tag2", spinValue);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adview) {
                //what am I supposed to do?
            }
        });

        spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adview, View view, int position, long id) {
                // On selecting a spinner item
                String spinValue = adview.getItemAtPosition(position).toString();
                if (!(spinValue.equals("nothing"))){
                    b2.putString("tag3", spinValue);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adview) {
                //what am I supposed to do?
            }
        });



    }
}
