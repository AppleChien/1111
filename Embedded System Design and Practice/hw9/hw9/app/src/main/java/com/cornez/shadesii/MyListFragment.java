package com.cornez.shadesii;

import android.database.Cursor;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.*;
import android.widget.SimpleCursorAdapter;
import androidx.annotation.*;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MyListFragment extends Fragment {
    private OnItemSelectedListener listener;
    List<String> shadelisting;
    List<String> shadeNameDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        shadelisting = new ArrayList<String>(Arrays.asList(DummyData.shade_name));
        shadeNameDetail = new ArrayList<String>(Arrays.asList(DummyData.shade_detail));



        // Now that we have some dummy shade data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy shades) and
        // use it to populate the ListView it's attached to.
        final ArrayAdapter<String> mShadeAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_shade, // The name of the layout ID.
                        R.id.list_item_shade_textview, // The ID of the textview to populate.
                        shadelisting);


        //MyAdapter myAdapter = new MyAdapter(shadelisting);


        View rootView =
                inflater.inflate(R.layout.color_list_fragment,
                        container,
                        false);

        // Get a reference to the ListView, and attach this adapter to it.
        //ListView listView = (ListView) rootView.findViewById(R.id.listview_shades);
        //listView.setAdapter(mShadeAdapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String shadeIndexString = mShadeAdapter.getItem(i);
                String information = shadeIndexString + "\n\n\n" + shadeNameDetail.get(i);
                updateDetail(information);
            }

        });*/

        //RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.listview_shades);
        //recycler.setAdapter(mShadeAdapter);
        RecyclerView recyclerView = rootView.findViewById(R.id.listview_shades);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*LooperLayoutManager layoutManager = new LooperLayoutManager();
        layoutManager.setLooperEnable(true);

        recyclerView.setLayoutManager(layoutManager);*/

        MyAdapter adapter = new MyAdapter(shadelisting);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int i) {
                        // do whatever
                        int t = i%11;
                        String shadeIndexString = mShadeAdapter.getItem(t);
                        String information = shadeIndexString + "\n\n\n" + shadeNameDetail.get(t);
                        updateDetail(information);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        //recyclerView.setOnClickListener();

        //recycler_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        return rootView;

    }

    public interface OnItemSelectedListener {
        public void onColorItemSelected(String link);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    public void updateDetail(String information) {
        listener.onColorItemSelected(information);
    }

}
