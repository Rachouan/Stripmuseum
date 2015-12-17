package com.example.samcastaigne.stripmuseumdemo2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by samcastaigne on 11/12/15.
 */
public class TeaserScherm extends com.example.samcastaigne.stripmuseumdemo2.MainActivity {

    Button buttonSluiten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.teaser_scherm);

        Typeface bariolBolt = Typeface.createFromAsset(getAssets(),"Bariol-Bold.ttf");

        buttonSluiten = (Button)findViewById(R.id.back_button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Hergé");

    }


    public void closeActivity(View view) {

        finish();
        overridePendingTransition(R.anim.stand_still, R.anim.right_out);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.stand_still, R.anim.right_out);

    }
}
