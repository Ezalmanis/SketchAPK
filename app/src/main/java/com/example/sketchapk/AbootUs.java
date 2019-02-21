package com.example.sketchapk;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * AbootUs is the fragment that contains information about our team
 */
public class AbootUs extends Fragment {


    private OnFragmentInteractionListener mListener;

    public AbootUs() { }

    public static AbootUs newInstance() {
        AbootUs fragment = new AbootUs();
        return fragment;
    }

    /**
     * onCreate gets called whenever a fragment is loaded by NavHost
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *Creates and returns the view hierarchy associated with the fragment.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_aboot_us, container, false);
    }

    /**
     * method is needed for proper navigation to be possible between fragments
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
