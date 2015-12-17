package com.example.samcastaigne.stripmuseumdemo2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by samcastaigne on 11/12/15.
 */
public class MyComicMenu extends MainActivity {

    Button buttonSluiten;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_comic_menu);

        buttonSluiten = (Button)findViewById(R.id.back_button);

        int alpha = 178;
        ((TextView)findViewById(R.id.subtext1)).setTextColor(Color.argb(alpha, 255, 255, 255));


    }


    public void closeActivity(View view) {

        finish();
        overridePendingTransition(R.anim.stand_still, R.anim.bottom_out);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.stand_still, R.anim.bottom_out);

    }
}
