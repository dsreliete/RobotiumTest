package br.com.teste.robotiumtest;

import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by eliete-luizalabs on 05/08/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
        solo.getCurrentActivity();

    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testMainActivity(){
        solo.assertCurrentActivity("Expected NoteEditor activity", "MainActivity");

        solo.clickOnActionBarHomeButton();
        solo.clickInList(1);
        assertTrue(solo.waitForText("Section 1"));

        testFragment("Section 1");
        testMenuOption();

        solo.clickOnActionBarHomeButton();
        solo.clickInList(2);
        assertTrue(solo.waitForText("Section 2"));

        getActivity().getSupportFragmentManager().findFragmentByTag("Section 2");
        solo.waitForFragmentByTag("Section 2");

        solo.clickOnActionBarHomeButton();
        solo.clickInList(3);
        assertTrue(solo.waitForText("Section 3"));
    }

    public void testMenuOption(){
        solo.clickOnMenuItem("Settings");
        solo.waitForFragmentByTag("Section 4", 3000);
        testFragment("Section 4");
        solo.waitForText("Settings touched");
    }

    public void testFragment(String TAG) {
        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(TAG);
        assertNotNull("Frag is null", f);
    }




}
