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

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class NotePadTest {

    private static final String NOTE_1 = "Note 1";
    private static final String NOTE_2 = "Note 2";


    @Rule
    public ActivityTestRule<NotesList> activityTestRule =
            new ActivityTestRule<>(NotesList.class);

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
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_add));
        //Assert that NoteEditor activity is opened
        solo.assertCurrentActivity("Expected NoteEditor Activity", NoteEditor.class);
        //In text field 0, enter Note 1
        solo.enterText(0, NOTE_1);
        //Click on action menu item Save
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_save));
        //Click on action menu item Add
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_add));
        //In text field 0, type Note 2
        solo.typeText(0, NOTE_2);
        //Click on action menu item Save
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_save));
        //Takes a screenshot and saves it in "/sdcard/Robotium-Screenshots/".
        solo.takeScreenshot();
        //Search for Note 1 and Note 2
        boolean notesFound = solo.searchText(NOTE_1) && solo.searchText(NOTE_2);
        //To clean up after the test case
        deleteNotes();
        //Assert that Note 1 & Note 2 are found
        assertTrue("Note 1 and/or Note 2 are not found", notesFound);
    }

    @Test
    public void testEditNoteTitle() throws Exception {
        //Click on add action menu item
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_add));
        //In text field 0, enter Note 1
        solo.enterText(0, NOTE_1);
        //Press hard key back button
        solo.goBack();
        solo.clickOnText(NOTE_1);
        //Click on menu item "Edit title"
        solo.clickOnMenuItem("Edit title");
        //Clear the edit text field
        solo.clearEditText(1);
        //In the text field enter Note 2
        solo.enterText(1, NOTE_2);
        //Click on button "OK"
        solo.clickOnButton("OK");
        //Click on action menu item Save
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_save));
        //Long click Note 2
        solo.clickLongOnText(NOTE_2);
        //Click on Delete
        solo.clickOnText("Delete");
        //Assert that Note 2 is deleted
        assertFalse("Note 2 is found", solo.searchText(NOTE_2));
    }

    private void deleteNotes() {
        //Click on first item in List
        solo.clickInList(1);
        //Click on delete action menu item
        solo.clickOnView(solo.getView(com.example.android.notepad.R.id.menu_delete));
        //Long click first item in List
        solo.clickLongInList(1);
        //Click delete
        solo.clickOnText(solo.getString(R.string.menu_delete));
    }
}
