package com.example.sketchapk;

import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;



public class PaintScreen extends Fragment {
    private TextView diffBund;
    // private Button buttonStartPause;
    private Button done;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private long timeLeftInMillis;
    private long endTime;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private boolean navigate = false;
    int delay =1000; // in milliseconds
    private int amountDisplayed = 0;
    private Vector<Dbimage> images = new Vector<Dbimage>(3, 1);  //array of image structure
    private ImageView picture;
    int amountInt = 0;
    Long timer;
    private Button button1;
    final Handler handler = new Handler();

    public Handler getHandler() {
        return handler;

    }




    private OnFragmentInteractionListener mListener;

    public PaintScreen() {
    }

    public static PaintScreen newInstance(String param1, String param2) {
        PaintScreen fragment = new PaintScreen();
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
        View view = inflater.inflate(R.layout.fragment_paint_screen, container, false);
        //buttonStartPause = view.findViewById(R.id.button_start_pause);
        done = view.findViewById(R.id.buttonDone);
        return view;
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

    private void startTimer() {

        endTime = 1000 + timeLeftInMillis;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
            }
        }.start();
        timerRunning = true;
    }
    TimerTask a = new TimerTask() {
        @Override
        public void run() {
            diffBund.setText("00:00");
        }
    };
    private void setToZero() {

        diffBund.setText("00:00");

    }
    public void performClick() {
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button1.performClick();
                }
            }, 1000);

    }
    private void updateCountDownText() {

        int minutes = (int) (((timeLeftInMillis)) / 1000) / 60;
        int seconds = (int) (((timeLeftInMillis)) / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        diffBund.setText(timeLeftFormatted);

        if (timeLeftFormatted.toString().equals("00:01")) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    diffBund.setText("00:00");
                    performClick();
                }
            }, 1000);
        }

    }

    private void goNext() {
        if (timerRunning) {
            countDownTimer.cancel();
        }
        images.clear();
        NavHostFragment.findNavController(this).navigate(R.id.toEnd);
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b2 = getArguments();
        picture = getView().findViewById(R.id.imageView);
        diffBund = getView().findViewById(R.id.text_view_countdown);
        String time = b2.getString("time");
        timer = Long.parseLong(time);
        /*testing if the values from previous screens have been passed correctly
        TextView paintScreenText = getView().findViewById(R.id.paintScreenText);

        String tag1 = b2.getString("tag1");
        String tag2 = b2.getString("tag2");
        String tag3 = b2.getString("tag3");
        String amountStr = b.getString("amountStr");
        int amountInt = b.getInt("amountInt");
        paintScreenText.setText(amountStr + " " + tag1 + "," + tag2 + "," + tag3);*/


        //generating a list of images

        String tag1 = b2.getString("tag1");
        String tag2 = b2.getString("tag2");
        String tag3 = b2.getString("tag3");
        String amountStr = b2.getString("amountStr");
        final int amountInt = b2.getInt("amountInt");

        try {
            InputStream sketches = getContext().getAssets().open("sketches.txt");
            BufferedReader is = new BufferedReader(new InputStreamReader(sketches, "UTF8"));
            String line;
            line = is.readLine();
            boolean addimg = false;
            while ((line = is.readLine()) != null) {
                addimg = false;
                Dbimage animage = new Dbimage(line);    //building an image object using the line of data from db file
                for (int i = 0; i < animage.tags.length; i++) {
                    if ((animage.tags[i].equals(tag1)) || (animage.tags[i].equals(tag2)) || (animage.tags[i].equals(tag3))) {
                        addimg = true;  //if image object contrains a requested tag, we will add it
                        break;
                    }
                }
                if (addimg) {
                    images.add(animage);
                }
                addimg = false;
            }

        } catch (IOException e) {

        }


        amountDisplayed = 0;
        button1 = getView().findViewById(R.id.buttonDone);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (amountDisplayed < amountInt) {
                    Log.d("VALUES", amountDisplayed+" / "+amountInt);
                    Random r = new Random();
                    if (images.size() > 0) {
                        int num = r.nextInt(images.size());
                        String imgname = images.elementAt(num).filename;
                        int res = getResources().getIdentifier(imgname, "drawable", getContext().getPackageName());
                        Log.d("VALUES", "1st checkpoint");
                        if (res != 0) {
                            picture.setImageResource(res);
                            images.remove(num);
                            amountDisplayed++;
                        }
                        Log.d("VALUES", "2st checkpoint");
                        if (timerRunning) {
                            countDownTimer.cancel();
                            timerRunning = false;
                        }
                        if (timer != null) {
                            timeLeftInMillis = timer;
                            int minutes = (int) (((timeLeftInMillis)) / 1000) / 60;
                            int seconds = (int) (((timeLeftInMillis)) / 1000) % 60;
                            String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

                            diffBund.setText(timeLeftFormatted);

                            startTimer();
                            //updateCountDownText();
                        }
                    }
                    else {
                        goNext();
                    }
                    Log.d("VALUES", "3st checkpoint");
                }
                else {
                    Log.d("VALUES", "4th checkpoint");
                    goNext();
                }
            }
        });

        Log.d("VALUES", "5st checkpoint");
        button1.performClick();





        //display %amount% random images
  /*          int num = 0;
            ImageView picture = getView().findViewById(R.id.imageView);

            for (int i = 0; i < amountInt; i++) {
                Random r = new Random();
                if (images.size() != 0) {
                    num = r.nextInt(images.size());
                    Log.d("FUCKYOU", Integer.toString(num));
                    //display the image under the index "num"
                    String imgname = images.elementAt(num).filename;
                    Log.d("FUCKYOU", imgname);
                    int res = getResources().getIdentifier(imgname, "drawable", getContext().getPackageName());
                    Log.d("FUCKYOU", Integer.toString(res));
                    Log.d("FUCKYOU", getContext().getPackageName());

                    if (res != 0) {
                        picture.setImageResource(res);
                        images.remove(num);
                        Log.d("FUCKYOU", "Picture changed to " + imgname);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
                //wait until time runs out
            }

            //"done" button
            Button button1 = getView().findViewById(R.id.buttonDone);
            button1.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toEnd, null));
        }*/
    }
}