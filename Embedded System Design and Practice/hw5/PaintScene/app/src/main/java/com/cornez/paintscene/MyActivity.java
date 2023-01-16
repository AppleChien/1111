package com.cornez.paintscene;

import android.app.Activity;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MyActivity extends Activity {

    ViewGroup paintingContainer;
    Transition transition;

    Scene activeScene;
    Scene passiveScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        paintingContainer = (ViewGroup) findViewById(R.id.painting_container);
        transition = TransitionInflater.from(this).inflateTransition(
                R.anim.transition);

        activeScene = Scene.getSceneForLayout(paintingContainer,
                R.layout.scene01, this);
        passiveScene = Scene.getSceneForLayout(paintingContainer,
                R.layout.scene02, this);
        activeScene.enter();
    }

    public void changeScenes(View view) {

        Scene temp = passiveScene;
        passiveScene = activeScene;
        activeScene = temp;

        TransitionManager.go(activeScene, transition);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
