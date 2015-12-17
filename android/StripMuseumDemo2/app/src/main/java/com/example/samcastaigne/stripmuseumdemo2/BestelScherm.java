package com.example.samcastaigne.stripmuseumdemo2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by samcastaigne on 11/12/15.
 */
public class BestelScherm extends com.example.samcastaigne.stripmuseumdemo2.MainActivity {

    Button buttonSluiten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestel_scherm);


        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"Bariol-Bold.ttf");


        Button buttonSluiten = (Button)findViewById(R.id.buttonSluit);
        buttonSluiten.setTypeface(myTypeface);

        TextView voorNaam = (TextView)findViewById(R.id.voorNaam);
        voorNaam.setTypeface(myTypeface);

        TextView achterNaam = (TextView)findViewById(R.id.achterNaam);
        achterNaam.setTypeface(myTypeface);


        TextView aantalpersonen = (TextView)findViewById(R.id.aantalPersonen);
        aantalpersonen.setTypeface(myTypeface);
        TextView vervaldatum = (TextView)findViewById(R.id.vervaldatum);
        vervaldatum.setTypeface(myTypeface);


        TextView scanInstructies = (TextView)findViewById(R.id.scanInstructies);
        scanInstructies.setTypeface(myTypeface);




    }


    public void closeActivity(View view) {

        finish();
        overridePendingTransition(R.anim.stand_still,R.anim.right_out);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.stand_still, R.anim.right_out);

    }

}