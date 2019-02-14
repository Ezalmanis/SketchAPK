package com.example.sketchapk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;


public class MainActivity extends AppCompatActivity implements AbootUs.OnFragmentInteractionListener, difficulty.OnFragmentInteractionListener, amount.OnFragmentInteractionListener, PaintScreen.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
