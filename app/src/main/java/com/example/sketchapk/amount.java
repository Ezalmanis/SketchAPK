package com.example.sketchapk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.support.design.widget.TextInputEditText;
import android.widget.TextView;

import androidx.navigation.Navigation;

public class amount extends Fragment {


    private OnFragmentInteractionListener mListener;

    public amount() { }

    public static amount newInstance(String param1, String param2) {
        amount fragment = new amount();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_amount, container, false);
    }

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
	// TODO: Clean this part please
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b = getArguments();
        final Bundle b1 = b;
        String strDiff = b.getString("StringDiff");
        //TextView diffBund = getView().findViewById(R.id.diffBund);
        //diffBund.setText(strDiff);
        Button button1 = getView().findViewById(R.id.buttonOne);
        Button button2 = getView().findViewById(R.id.buttonTwo);
        Button button3 = getView().findViewById(R.id.buttonThree);

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    b1.putInt("amountInt", 1);
                    b1.putString("amountStr", "1");

               if (arg1.getAction()==MotionEvent.ACTION_UP)
                 Navigation.findNavController(arg0).navigate(R.id.amountToCat,b1);
                 return true;
            }
        });
        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    b1.putInt("amountInt", 2);
                    b1.putString("amountStr", "2");

              if (arg1.getAction()==MotionEvent.ACTION_UP)
                Navigation.findNavController(arg0).navigate(R.id.amountToCat,b1);
                return true;
            }
        });
        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    b1.putInt("amountInt", 3);
                    b1.putString("amountStr", "3");

               if (arg1.getAction()==MotionEvent.ACTION_UP)
                 Navigation.findNavController(arg0).navigate(R.id.amountToCat,b1);
                return true;
            }
        });

        Button amountConfirmButton = getView().findViewById(R.id.amountConfirmButton);
        TextInputEditText e = getView().findViewById(R.id.textInputEditText);
        e.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText userInput = getView().findViewById(R.id.textInputEditText);
                String amountStr = userInput.getText().toString();
                b1.putString("amountStr", amountStr);
                int amountInt = Integer.parseInt(amountStr);
                b1.putInt("amountInt", amountInt);
            }
        });

        if (b1.getInt("amountInt") == 0) {
            b1.putInt("amountInt", 1);
            b1.putString("amountStr", "1");
        }

       // amountConfirmButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.amountToCat, b1));


        amountConfirmButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    if (b1.getString("amountString") == null) {
                        b1.putInt("amountInt", 1);
                        b1.putString("amountStr", "1");
                    }

                if (arg1.getAction()==MotionEvent.ACTION_UP)
                    Navigation.findNavController(arg0).navigate(R.id.amountToCat,b1);
                return true;
            }
        });





    }
}
