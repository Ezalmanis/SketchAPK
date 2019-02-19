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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class PaintScreen extends Fragment {
    private TextView diffBund;
    private Button buttonStartPause;
    private Button done;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long timeLeftInMillis;
    private long endTime;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public PaintScreen() { }

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
        buttonStartPause = view.findViewById(R.id.button_start_pause);
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

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b2 = getArguments();
        diffBund = getView().findViewById(R.id.text_view_countdown);
        String time = b2.getString("time");
        Long timer = Long.parseLong(time);
        if (timer != null) {
            String initial_Time = timer.toString();
            diffBund.setText(initial_Time);
            timeLeftInMillis = timer;
            if (timerRunning == false) {
                buttonStartPause.setVisibility(View.INVISIBLE);
                startTimer();
                updateCountDownText();
            }

        }
        Button button1 = getView().findViewById(R.id.buttonDone);
        button1.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toEnd, null));
    }

    private void startTimer() {
        endTime = System.currentTimeMillis() + timeLeftInMillis;
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                timerRunning = false;
                updateButtons();
            }
        }.start();
        timerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        diffBund.setText(timeLeftFormatted);
    }
    private void updateButtons() {
        if (timerRunning) {
        } else {
            if (timeLeftInMillis < 1000) {
                buttonStartPause.setVisibility(View.INVISIBLE);
                diffBund.setVisibility(View.INVISIBLE);
                done.setVisibility(View.INVISIBLE);
                NavHostFragment.findNavController(this).navigate(R.id.toEnd);
            }
        }
    }
}