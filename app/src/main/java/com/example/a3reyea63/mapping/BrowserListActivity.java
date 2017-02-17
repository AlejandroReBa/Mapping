package com.example.a3reyea63.mapping;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BrowserListActivity extends ListActivity {

    private String[] names, details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new String[] {"Firefox", "Chrome", "Internet Explorer"};
        details = new String[] {"Made by Microsoft", " Made by Mozilla", "Made by Google"};

        BrowsersAdapter browsersAdapter = new BrowsersAdapter();
        //browsersAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1 , names);
        setListAdapter(browsersAdapter);

        //setContentView(R.layout.browser_entry);
    }


    public void onListItemClick (ListView lv, View view, int index, long id){
        // handle list item selection
    }

    class BrowsersAdapter extends ArrayAdapter<String>
    {
        public BrowsersAdapter() {
            super(BrowserListActivity.this, android.R.layout.simple_list_item_1, names);
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent){
            LayoutInflater inflater = (LayoutInflater) getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.browser_entry, parent, false);

            TextView nameTextView = (TextView) view.findViewById(R.id.browser_name);
            TextView descriptionTextView = (TextView) view.findViewById(R.id.browser_desc);

            nameTextView.setText(names[index]);
            descriptionTextView.setText(details[index]);

            return view;
        }
    }
}


