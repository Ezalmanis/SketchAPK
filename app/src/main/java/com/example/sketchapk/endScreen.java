package com.example.sketchapk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.Navigation;

public class endScreen extends Fragment {

    private OnFragmentInteractionListener mListener;

    public endScreen() { }


    public static endScreen newInstance() {
        endScreen fragment = new endScreen();
        return fragment;
    }
    /**
     * Sets savedInstanceState bundle to null, we have made this method so that no garbage data would remain, and proper cleanup would happen
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        System.gc();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_end_screen, container, false);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void onActivityCreated(final Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Button button1 = getView().findViewById(R.id.buttonAgain);
        Button button2 = getView().findViewById(R.id.buttonQuit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        button1.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.playAgain, null));
    }
}
