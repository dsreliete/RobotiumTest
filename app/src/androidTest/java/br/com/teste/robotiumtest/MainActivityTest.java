package br.com.teste.robotiumtest;

import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by eliete-luizalabs on 05/08/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    private TextView textView;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(getInstrumentation(), getActivity());
        textView = (TextView) solo.getView(R.id.section_label);
    }

    @Override
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    public void testMainActivity(){
        solo.assertCurrentActivity("Expected NoteEditor activity", "MainActivity");

        String tagFrag = "Section 1";
        solo.clickOnActionBarHomeButton();
        solo.clickInList(1);
        assertTrue(solo.waitForText(tagFrag));
        //Test if expected fragment is correct
        testFragment(tagFrag);
        //test if expected textview isn't null and shows the correct text
        testTextView("Fragment 1");
        //test if expect button isn't null and goes to expected fragment
        assertNotNull("button is null", solo.waitForView(R.id.button));
        solo.clickOnButton("Go");
        testFragment("Section 3");

        tagFrag = "Section 2";
        solo.clickOnActionBarHomeButton();
        solo.clickInList(2);
        assertTrue(solo.waitForText(tagFrag));

        //Another way to assert a correct Fragment
        assertTrue(solo.waitForFragmentByTag(tagFrag));
        //Another way to assert that textview isn't null and shows the correct text
        assertNotNull("Textview is null", solo.getView(R.id.section_label));
        assertTrue(solo.waitForText("Fragment 2"));

        tagFrag = "Section 3";
        solo.clickOnActionBarHomeButton();
        solo.clickInList(3);
        assertTrue(solo.waitForText(tagFrag));
        solo.waitForFragmentByTag(tagFrag);
        testFragment(tagFrag);
        testTextView("Fragment 3");

    }

    public void testMenuOption(){
        solo.clickOnMenuItem("Settings");
        String tagFrag = "Section 4";
        solo.waitForFragmentByTag(tagFrag);
        //test if toast message is correct
        solo.waitForText("Settings touched");
        //test if textview message is correct
        solo.waitForText("Fragment 4");
    }

    public void testFragment(String tag) {
        solo.waitForFragmentByTag(tag);
        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag(tag);
        assertTrue(tag.equals(f.getTag().toString()));
    }

    public void testTextView(String tag){
        TextView t = (TextView) solo.getView(R.id.section_label);
        assertNotNull("Textview is null", t);
        assertTrue(tag.equals(t.getText().toString()));
    }

}
