package com.example.sketchapk;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.navigation.Navigation;

public class homeFragment extends Fragment {
    /**
     * @param savedInstanceState
     * savedInstanceState is set to null to ensure that when the application loops after End Screen, there are no garbage values
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
    }

    public homeFragment() { }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        Button button1 = getView().findViewById(R.id.button1);
        Button button2 = getView().findViewById(R.id.button2);
        Button button3 = getView().findViewById(R.id.button3);
        /**
         * button 1 sends the user to AbootUs
         * button 2 prompts the player to start the sketching process
         * button 3 exits the application.
         */

        button1.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.homeToAbout, null));
        button2.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.homeToDifficulty));
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

}

