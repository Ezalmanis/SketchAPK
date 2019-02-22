package com.example.sketchapk;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.Navigation;

/**
 * Fragment is meant so that the end user select their prefered difficulty, which sets an appropriate time limit for drawing a picture.
 */

public class difficulty extends Fragment {
    private OnFragmentInteractionListener mListener;
    Bundle b = new Bundle();

    public difficulty() { }

    public static difficulty newInstance() {
        difficulty fragment = new difficulty();
        return fragment;
    }
    /**
     * Loads savedInstanceState, a bundle that contains values that the application uses,
     * this ensures that minimizing the application wouldn't impede its functionality/make it crash.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    /**
     * interface is needed for proper navigation to be possible between fragments, it enables NavHost for it.
     */

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Button button1 = getView().findViewById(R.id.buttonEasy);
        Button button2 = getView().findViewById(R.id.buttonNormal);
        Button button3 = getView().findViewById(R.id.buttonHard);
        Button custom = getView().findViewById(R.id.buttonCustom);

        /**
         * buttonX.setOnTouchListener checks whether the corresponding button has been pressed or released.
         * If the button is pressed, its' corresponding value is added to bundle
         * Upon release of said button, the bundle is passed to the next fragment, and NavHost is propmted to move to it.
         * correspondingly, button 1 sets timer to 1 minute, button 2 sets it to 30 seconds, button 3 sets it to 15 seconds, and "custom" button sets it 2 minutes.
         * Initially, we wanted to make custom timer value, but later we did not implement this, it could be added if demand for it is sufficient
         */

        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    b.putInt("Timer", 60000);
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

