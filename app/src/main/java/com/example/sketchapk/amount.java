package com.example.sketchapk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link amount.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link amount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class amount extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public amount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment amount.
     */
    // TODO: Rename and change types and number of parameters
    public static amount newInstance(String param1, String param2) {
        amount fragment = new amount();
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
        return inflater.inflate(R.layout.fragment_amount, container, false);
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
        Bundle b = getArguments();
        final Bundle b1 = b;
        String strDiff = b.getString("StringDiff");
        TextView diffBund = getView().findViewById(R.id.diffBund);
        diffBund.setText(strDiff);
        Button button1 = getView().findViewById(R.id.buttonOne);
        Button button2 = getView().findViewById(R.id.buttonTwo);
        Button button3 = getView().findViewById(R.id.buttonThree);

        //button1.setVisibility(View.GONE);
        //button2.setVisibility(View.GONE);
        //button3.setVisibility(View.GONE);


        //button1.setOnClickListener(Navigation.createNavigateOnClickListener(
        //        R.id.amountToPaint, null));
        //button2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.amountToPaint));
        //button3.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.amountToPaint));

        Button amountConfirmButton = getView().findViewById(R.id.amountConfirmButton);
        //EditText userInput = getView().findViewById(R.id.textInputEditText);
        //int amountSelected = Integer.parseInt(userInput.getText().toString());
        //String amountStr = userInput.getText().toString();
        //int amountInt = Integer.parseInt(amountStr);
        //Bundle b = new Bundle();
        //b.putInt("amount", 1);
        //b.putString("amountTxt", "Hard coded string");
        //Navigation.createNavigateOnClickListener(R.id.amountToCat, b)
        //amountConfirmButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.amountToCat, b));



        TextInputEditText e = getView().findViewById(R.id.textInputEditText);

        e.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText userInput = getView().findViewById(R.id.textInputEditText);
                String amountStr = userInput.getText().toString();
                b1.putString("amountStr", amountStr);
                int amountInt = 1;
                b1.putInt("amountInt", amountInt);
            }
        });


        amountConfirmButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.amountToCat, b1));


    /*    amountConfirmButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                EditText userInput = getView().findViewById(R.id.textInputEditText);

                Bundle b = new Bundle();
                String amountStr = userInput.getText().toString();
                Log.d("CREATION", "amountStr = "+amountStr);
               // if (amountStr != "") {
                    b.putString("amountStr", amountStr);
                    //int amountInt = Integer.parseInt(amountStr);
                    int amountInt = 1;
                    b.putInt("amountInt", amountInt);
               // }
                //else {
                    //b.putString("amountTxt", "1");
                    //b.putInt("amount", 1);
                //}

                Log.d("CREATION", "bundle str = "+b.get("amountStr"));
                Log.d("CREATION", "bundle int = "+b.get("amountInt"));
                //View.OnClickListener n = Navigation.createNavigateOnClickListener(R.id.homeToDifficulty);
                //Navigation.createNavigateOnClickListener(R.id.homeToDifficulty);
                Navigation.findNavController(view).navigate(R.id.homeToDifficulty, b);
                // Navigation.createNavigateOnClickListener(R.id.amountToCat, b);
            }
        });*/

    }

}
