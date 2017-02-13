package com.example.a3reyea63.mapping;

/**
 * Created by 3reyea63 (AlejandroReBa on github) on 06/02/2017.
 */
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class MapChooseActivity extends Activity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mca);

        Button regular = (Button) findViewById(R.id.btnRegular);
        regular.setOnClickListener(this);
        Button cyclemap = (Button) findViewById(R.id.btnCyclemap);
        cyclemap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        boolean cyclemap = false;

        if(view.getId() == R.id.btnCyclemap)
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
}
