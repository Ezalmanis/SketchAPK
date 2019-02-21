package com.example.sketchapk;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Button;
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

    private OnFragmentInteractionListener mListener;

    public Category() {
        /**
         * Required empty public constructor
         */

    }

    public static Category newInstance() {
        Category fragment = new Category();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Generates a list of all available tags and populates the spinners with them
     * After the user has selected the tags, it gets passed to the paint screen
     * If no tags have been selected, a completely random selection of images
     * will be shown on the next screen
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Bundle b2 = getArguments();
        String amountStr = b2.getString("amountStr");

        Button button1 = getView().findViewById(R.id.buttonConfirm);
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
                        }
                        if (!isPresent) {
                            tagVec.add(images.elementAt(i).tags[j]);
                        }
                        else {
                            isPresent = false;
                        }
                    }
                }
            }
        }
        catch (IOException e) {}

        /**
         * Creates two different Arrays meant for tags,
         * First one starts with Default, second with Nothing
         */


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


        /**
         * Makes dropdown menus from the available tags. First dropdown uses first tag Array, second and third, uses second tag Array
         */


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


        /**
         *  bundle for passing the timer, amount of images and tags (added in this fragment)
         */

        int amountInt = b2.getInt("amountInt");
        b2.putInt("amountInt", amountInt);
        b2.putString("amountStr", amountStr);
        final Random rand = new Random();
        int cnt =adapter1.getCount();
        cnt = cnt++;
        final int n = rand.nextInt(cnt);

        /**
         * enables functionality of the dropdown menus,
         * if nothing is selected on the first listener, it calls a random tag
         * if nothing is selected on the second or third listener, nothing is selected.
         */
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adview, View view, int position, long id) {

                String spinValue = adview.getItemAtPosition(position).toString();
                if (!(spinValue.equals("default"))){
                    b2.putString("tag1", spinValue);
                }else{
                    spinValue = adview.getItemAtPosition(n).toString();
                    b2.putString("tag1", spinValue);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adview) {}
        });

        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adview, View view, int position, long id) {
                String spinValue = adview.getItemAtPosition(position).toString();
                if (!(spinValue.equals("nothing"))){
                    b2.putString("tag2", spinValue);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adview) {}
        });

        spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adview, View view, int position, long id) {
                String spinValue = adview.getItemAtPosition(position).toString();
                if (!(spinValue.equals("nothing"))){
                    b2.putString("tag3", spinValue);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adview) {}
        });
    }
}
