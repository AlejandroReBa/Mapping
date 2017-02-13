package com.example.a3reyea63.mapping;

/**
 * Created by 3reyea63 on 13/02/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.osmdroid.util.GeoPoint;

public class SetLocationActivity extends Activity implements View.OnClickListener {


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sla);

        Button changeButton = (Button)findViewById(R.id.changeButton);
        changeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        EditText lat = (EditText)findViewById(R.id.editlat);
        EditText lon = (EditText)findViewById(R.id.editlon);

        TextView error = (TextView)findViewById(R.id.errorId);

        if (lat.getText().toString().equals("") || lon.getText().toString().equals("")){
            error.setText("ERROR: Please fill both longitude and latitude fields");
        }else{
            double doubleLat = Double.parseDouble(lat.getText().toString());
            double doubleLon = Double.parseDouble(lon.getText().toString());
            error.setText("");

            Intent intent = new Intent();

            Bundle bundle = new Bundle();

            bundle.putDouble("com.example.selectedlat", doubleLat);
            bundle.putDouble("com.example.selectedlon", doubleLon);

            intent.putExtras(bundle);

            setResult(RESULT_OK,intent);
            finish();
        }

    }
}
