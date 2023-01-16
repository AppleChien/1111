/*
 * This is an example test project created in Eclipse to test NotePad which is a sample 
 * project located in AndroidSDK/samples/android-11/NotePad
 * 
 * 
 * You can run these test cases either on the emulator or on device. Right click
 * the test project and select Run As --> Run As Android JUnit Test
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

package com.example.android.notepad;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class NotePadTest {

    private static final String NOTE_1 = "Note 1";
    private static final String NOTE_2 = "Note 2";


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private Solo solo;


    @Before
    public void setUp() throws Exception {
        //setUp() is run before a test case is started.
        //This is where the solo object is created.
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),
                activityTestRule.getActivity());
    }

    @After
    public void tearDown() throws Exception {
        //tearDown() is run after a test case has finished.
        //finishOpenedActivities() will finish all the activities that have been opened during the test execution.
        solo.finishOpenedActivities();
    }

    @Test
    public void testAddNote() throws Exception {
        //Unlock the lock screen
        solo.unlockScreen();
        //Click on action menu item add
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button2));


        boolean notesFound = solo.searchText("5") && solo.searchText("3")&& solo.searchText("2");
        assertTrue("test1error", notesFound);


    }

    @Test
    public void testEditNoteTitle() throws Exception {
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button1));
        solo.clickOnView(solo.getView(R.id.button1));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button));
        solo.clickOnView(solo.getView(R.id.button2));


        boolean notesFound = solo.searchText("8") && solo.searchText("4")&& solo.searchText("4");
        assertTrue("test2error", notesFound);

    }
}
