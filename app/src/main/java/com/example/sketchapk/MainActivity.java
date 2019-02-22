package com.example.sketchapk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;


/**
 * MainActivity is our NavHost Activity, MainActivity loads Navhost, and implements every fragments Interaction Listener. Without this implementation, the NavHost fragment could not interact with the fragments
 * NavHost is the process that enables navigation within our application.
 */
public class MainActivity extends AppCompatActivity implements endScreen.OnFragmentInteractionListener,
        AbootUs.OnFragmentInteractionListener,
        difficulty.OnFragmentInteractionListener,
        amount.OnFragmentInteractionListener,
        PaintScreen.OnFragmentInteractionListener,
        Category.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onFragmentInteraction(Uri uri) { }

}
