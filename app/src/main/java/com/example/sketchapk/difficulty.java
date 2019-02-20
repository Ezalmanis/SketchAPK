package com.example.sketchapk;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Locale;

import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class difficulty extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    Bundle b = new Bundle();

    public difficulty() { }

    public static difficulty newInstance(String param1, String param2) {
        difficulty fragment = new difficulty();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_difficulty, container, false);
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
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button button1 = getView().findViewById(R.id.buttonEasy);
        Button button2 = getView().findViewById(R.id.buttonNormal);
        Button button3 = getView().findViewById(R.id.buttonHard);
        Button custom = getView().findViewById(R.id.buttonCustom);
        boolean lol = false;
        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_BUTTON_PRESS)
                    b.putInt("Timer", 60000);
                //b.putString("StringDiff", "big gay android");
                b.putString("time", "60000");
                if (arg1.getAction()==MotionEvent.ACTION_UP)
                    Navigation.findNavController(arg0).navigate(R.id.diffToAmount,b);
                return true;
            }
        });

        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    b.putInt("Timer", 30000);
                b.putString("StringDiff", "30");
                b.putString("time", "30000");
                Navigation.createNavigateOnClickListener(R.id.diffToAmount,b);
                if (arg1.getAction()==MotionEvent.ACTION_UP)
                    Navigation.findNavController(arg0).navigate(R.id.diffToAmount,b);
                return true;
            }
        });
        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    b.putInt("Timer", 15000);
                b.putString("StringDiff", "15");
                b.putString("time", "15000");
                if (arg1.getAction()==MotionEvent.ACTION_UP)
                    Navigation.findNavController(arg0).navigate(R.id.diffToAmount,b);
                return true;
            }
        });
        custom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    b.putInt("Timer", 120000);
                b.putString("StringDiff", "120");
                b.putString("time", "120000");
                if (arg1.getAction()==MotionEvent.ACTION_UP)
                    Navigation.findNavController(arg0).navigate(R.id.diffToAmount,b);
                return true;
            }
        });
    }
}

