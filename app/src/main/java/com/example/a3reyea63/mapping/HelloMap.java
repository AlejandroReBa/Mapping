package com.example.a3reyea63.mapping;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;


public class HelloMap extends Activity implements View.OnClickListener{

    MapView mv;
    boolean onActivityResultExecuted;
    boolean deviceRotated;
    double lon;
    double lat;
    int zoom;

    ItemizedIconOverlay<OverlayItem> items;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this line tells OpenStreetMap about our app.
        // If you miss this out, you might get banned from OSM servers
        Configuration.getInstance().load
                (this, PreferenceManager.getDefaultSharedPreferences(this));

        mv = (MapView) findViewById(R.id.map1);

        mv.setBuiltInZoomControls(true);

        /* 06/03/2017
        Now this is done in onStart method. longitude, latitude and zoom read from preferences.

        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(40.1, 22.5));//Litochoro, Greece
        */

        //Button changeButton = (Button)findViewById(R.id.changeButton);
        //changeButton.setOnClickListener(this);

        onActivityResultExecuted = false;
        deviceRotated = false;
        lat = 0;
        lon = 0;
        zoom = 0;
        if (savedInstanceState != null)
        {
            //not working well, once initialized is never null again until close emulator
            //deviceRotated = true;
            //lat = savedInstanceState.getDouble("lat");
            //lon = savedInstanceState.getDouble("lot");
           //zoom = savedInstanceState.getInt("zoom");
            //works?
           // savedInstanceState = null;
        }

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), null);
        OverlayItem fernhurst = new OverlayItem("Fernhurst", "Village in West Sussex", new GeoPoint(51.05, -0.72));
        OverlayItem blackdown = new OverlayItem("Blackdown", "highest point in West Sussex", new GeoPoint(51.0581, -0.6897));
        items.addItem(fernhurst);
        items.addItem(blackdown);
        mv.getOverlays().add(items);


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

        }else if (item.getItemId() == R.id.prefsactivity){
            Intent intent = new Intent (this, MyPrefsActivity.class);
            startActivityForResult(intent, 3);
        }
        return false;
    }

    // Called before onResume()

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        onActivityResultExecuted = true;
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

    public void onSaveInstanceState (Bundle savedInstanceState)
    {
        //savedInstanceState.putBoolean("isRecording", isRecording);
        savedInstanceState.putDouble("lat", mv.getMapCenter().getLatitude());
        savedInstanceState.putDouble("lon", mv.getMapCenter().getLongitude());
        savedInstanceState.putInt("zoom", mv.getZoomLevel());
        if(mv.getTileProvider().getTileSource()==TileSourceFactory.CYCLEMAP) {
            savedInstanceState.putString("tile", "CM");
        }else{
            savedInstanceState.putString("tile", "RM");
        }
        //mv.getTileProvider().getMapTile();
    }

    public void onResume()
    {
        super.onResume();
        if(deviceRotated){
            mv.getController().setZoom(this.zoom);
            mv.getController().setCenter(new GeoPoint(this.lat,this.lon));
        }else if(!onActivityResultExecuted){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            double lat = Double.parseDouble ( prefs.getString("lat", "50.9") );
            double lon = Double.parseDouble ( prefs.getString("lon", "-1.4") );
            int zoom = Integer.parseInt ( prefs.getString("zoom", "14") );

            String mapStyle = prefs.getString("mapstyle","RM");
            if(mapStyle.equals("RM")){
                mv.setTileSource(TileSourceFactory.MAPNIK);
            }else{
                mv.setTileSource(TileSourceFactory.CYCLEMAP);
            }

            mv.getController().setZoom(zoom);
            mv.getController().setCenter(new GeoPoint(lat, lon));
        }else{
            onActivityResultExecuted = false;
        }

    }

}