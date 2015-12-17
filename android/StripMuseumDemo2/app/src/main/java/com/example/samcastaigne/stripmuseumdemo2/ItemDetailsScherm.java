package com.example.samcastaigne.stripmuseumdemo2;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by samcastaigne on 11/12/15.
 */
public class ItemDetailsScherm extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_scherm);

    }
}
