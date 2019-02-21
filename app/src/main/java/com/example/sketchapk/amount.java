package com.example.sketchapk;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.TextInputEditText;

import androidx.navigation.Navigation;

public class amount extends Fragment {


    private OnFragmentInteractionListener mListener;

    public amount() { }

    public static amount newInstance() {
        amount fragment = new amount();
        return fragment;
    }
	/**
	* Loads savedInstanceState, which is a bundle that contains values that the application uses
	* ensures that minimizing the application wouldn't impede its functionality/make it crash
	*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_amount, container, false);
    }

	/**
     * method is needed for proper navigation to be possible between fragments
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Bundle b1 = getArguments();
        b1.putInt("amountInt", 0);
	
	/**
         *  Assigns buttons to specific elements on the screen
         */
        Button button1 = getView().findViewById(R.id.buttonOne);
        Button button2 = getView().findViewById(R.id.buttonTwo);
        Button button3 = getView().findViewById(R.id.buttonThree);

        /**
         * buttonX.setOnTouchListener checks whether the corresponding button has been pressed or released.
         * If the button is pressed, its' corresponding value is added to bundle
         * Upon release of said button, the bundle is passed to the next fragment, and NavHost is propmted to move to it.
         */

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
        
	/**
         * TexInputEditText is bound to a design interface on a users screen
         * said method monitors input, and if a value has been input, it gets passed to a bundle, that would later be sent to the next Fragment
         */
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
        
	/**
         * OnTouchListener, that is assigned to confirm value button
         * It takes the bundle value that was passed from TextInputEditText and checks whether the passed values are null and 0, if so, it assigns 1 as the defalt value for the bundle, if they are not null and 0, it does not alter the value
         *  passed a value, then the
         */

        amountConfirmButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction()==MotionEvent.ACTION_DOWN)
                    if ((b1.getString("amountString") == null)&&(b1.getInt("amountInt")==0)) {
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
