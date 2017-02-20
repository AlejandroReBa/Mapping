package com.example.a3reyea63.mapping;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.MenuItem;
import android.view.View;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;

import android.view.Menu;
import android.view.MenuInflater;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;


public class HelloMap extends Activity implements View.OnClickListener{

    MapView mv;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this line tells OpenStreetMap about our app.
        // If you miss this out, you might get banned from OSM servers
        Configuration.getInstance().load
                (this, PreferenceManager.getDefaultSharedPreferences(this));

        mv = (MapView) findViewById(R.id.map1);

        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(40.1, 22.5));//Litochoro, Greece

        //Button changeButton = (Button)findViewById(R.id.changeButton);
        //changeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        /*
        EditText lat = (EditText)findViewById(R.id.editlat);
        EditText lon = (EditText)findViewById(R.id.editlon);

        TextView error = (TextView)findViewById(R.id.errorId);

        if (lat.getText().toString().equals("") || lon.getText().toString().equals("")){
            error.setText("ERROR: Please fill both longitude and latitude fields");
        }else{
            double doubleLat = Double.parseDouble(lat.getText().toString());
            double doubleLon = Double.parseDouble(lon.getText().toString());
            error.setText("");
            mv.getController().setCenter(new GeoPoint(doubleLat, doubleLon));
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.choosemap)
        {
            //System.exit(0);
            Intent intent = new Intent (this, MapChooseActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, 0);

            return true;
        }else if (item.getItemId() == R.id.setlocation){
            Intent intent = new Intent (this, SetLocationActivity.class);
            startActivityForResult(intent, 1);

        }else if (item.getItemId() == R.id.poilist){
            Intent intent = new Intent (this, BrowserListActivity.class);
            startActivityForResult(intent, 2);

        }else if (item.getItemId() == R.id.choosemaplist){
            Intent intent = new Intent (this, MapChooseListActivity.class);
            startActivityForResult(intent, 0);
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {

        if(requestCode==0)
        {

            if (resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                boolean cyclemap = extras.getBoolean("com.example.cyclemap");
                if(cyclemap==true)
                {
                    mv.setTileSource(TileSourceFactory.CYCLEMAP);
                }
                else
                {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }

        }else if(requestCode==1){
            if (resultCode==RESULT_OK){
                Bundle extras=intent.getExtras();
                double lat = extras.getDouble("com.example.selectedlat");
                double lon = extras.getDouble("com.example.selectedlon");
                mv.getController().setCenter(new GeoPoint(lat, lon));
            }
        }
    }

}