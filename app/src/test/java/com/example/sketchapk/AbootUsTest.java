package com.example.sketchapk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AbootUsTest {

    AbootUs abootUs;

    @Before
    public void setUp() throws Exception {
        AbootUs.setUp();
        abootUs = new AbootUs();
    }
    @Test
    public void newInstance() {
    }

    @After
    public void tearDown() throws Exception {
    }



    /* @Test
    public void onCreate() {
    }

    @Test
    public void onCreateView() {
    }

    @Test
    public void onButtonPressed() {
    }

    @Test
    public void onAttach() {
    }

    @Test
    public void onDetach() {
    }

    */
}