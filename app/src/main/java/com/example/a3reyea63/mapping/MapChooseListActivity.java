package com.example.a3reyea63.mapping;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MapChooseListActivity extends ListActivity {

    String[] mapTitles;
    String[] mapDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapTitles= new String[]{"Regular Map", "Cycle Map"};
        mapDescriptions = new String[]{"Ordinary map, as always, be traditional!", "A new world of experiences, cycle everyday to new places!"};

        ChooseMapAdapter mapsAdapter = new ChooseMapAdapter();
        //browsersAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1 , names);
        setListAdapter(mapsAdapter);

        //setContentView(R.layout.activity_map_choose_list);
    }


    @Override
    public void onListItemClick (ListView lv, View view, int index, long id){
        boolean cyclemap = false;

        if(index == 1)
        {
            cyclemap = true;
        }

        Intent intent = new Intent();

        Bundle bundle = new Bundle();

        bundle.putBoolean("com.example.cyclemap", cyclemap);

        intent.putExtras(bundle);

        setResult(RESULT_OK,intent);
        finish();
    }



    class ChooseMapAdapter extends ArrayAdapter<String>
    {
        public ChooseMapAdapter() {
            super(MapChooseListActivity.this, android.R.layout.simple_list_item_1, mapTitles);
        }


        @Override
        public View getView(int index, View convertView, ViewGroup parent){

            View view = convertView;
            if (view == null)
            {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.activity_map_choose_list, parent, false);
            }

            TextView titleTextView = (TextView) view.findViewById(R.id.map_title);
            TextView descriptionTextView = (TextView) view.findViewById(R.id.map_desc);

            titleTextView.setText(mapTitles[index]);
            descriptionTextView.setText(mapDescriptions[index]);

            return view;
        }
    }
}
