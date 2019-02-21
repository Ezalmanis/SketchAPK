package com.example.sketchapk;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Vector;
import androidx.navigation.fragment.NavHostFragment;



public class PaintScreen extends Fragment {
    private TextView diffBund;
    private Button done;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private long timeLeftInMillis;
    private boolean navigate = false;
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
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paint_screen, container, false);
        done = view.findViewById(R.id.buttonDone);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Creates and starts a new CountDownTimer with timeLeftInMilis time limit
     */
    private void startTimer() {
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

    /**
     * Calls "DONE" button's onClick method
     */
    public void performClick() {
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button1.performClick();
                }
            }, 1000);
    }

    /**
     * Updates the text view which displays the amount of time left
     * and calls the performClick method if the time has run out
     */
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

    /**
     * Stops the current running timer, clears the vector of Dbimage objects
     * Navigates to the end screen
     */
    private void goNext() {
        if (timerRunning) {
            countDownTimer.cancel();
        }
        images.clear();
        NavHostFragment.findNavController(this).navigate(R.id.toEnd);
    }

    /**
     * Generates a vector of Dbimage objects with requested tags
     * Displays the requested amount of images on the screen,
     * switching them on timer
     * @param savedInstanceState
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b2 = getArguments();
        picture = getView().findViewById(R.id.imageView);
        diffBund = getView().findViewById(R.id.text_view_countdown);
        String time = b2.getString("time");
        timer = Long.parseLong(time);

        //generating a list of images

        String tag1 = b2.getString("tag1");
        String tag2 = b2.getString("tag2");
        String tag3 = b2.getString("tag3");
        final int amountInt = b2.getInt("amountInt");

        button1 = getView().findViewById(R.id.buttonDone);
        button1.setVisibility(View.GONE);

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

        } catch (IOException e) {}

        //displaying images

        amountDisplayed = 0;
        button1.setVisibility(View.VISIBLE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (amountDisplayed < amountInt) {
                    Random r = new Random();
                    if (images.size() > 0) {
                        int num = r.nextInt(images.size());
                        String imgname = images.elementAt(num).filename;
                        int res = getResources().getIdentifier(imgname, "drawable", getContext().getPackageName());

                        if (res != 0) {
                            picture.setImageResource(res);
                            images.remove(num);
                            amountDisplayed++;
                        }

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
                        }
                    }
                    else {
                        goNext();
                    }
                }
                else {
                    goNext();
                }
            }
        });

        button1.performClick();

    }
}