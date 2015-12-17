package com.example.samcastaigne.stripmuseumdemo2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by samcastaigne on 11/12/15.
 */
public class RoomMenuScherm extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_menu_scherm);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"Bariol-Bold.ttf");

        TextView titel1 = (TextView)findViewById(R.id.comicTitel1);
        titel1.setTypeface(myTypeface);
        TextView titel2 = (TextView)findViewById(R.id.comicTitel2);
        titel2.setTypeface(myTypeface);
        TextView titel3 = (TextView)findViewById(R.id.comicTitel3);
        titel3.setTypeface(myTypeface);

        



    }
}
