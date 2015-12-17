package com.example.samcastaigne.stripmuseumdemo2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.net.Uri;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"Bariol-Bold.ttf");

        TextView titel1 = (TextView)findViewById(R.id.titel1);
        titel1.setTypeface(myTypeface);
        TextView titel2 = (TextView)findViewById(R.id.titel2);
        titel2.setTypeface(myTypeface);
        TextView titel3 = (TextView)findViewById(R.id.titel3);
        titel3.setTypeface(myTypeface);
        TextView titel4 = (TextView)findViewById(R.id.titel4);
        titel4.setTypeface(myTypeface);

        TextView subTitel1 = (TextView)findViewById(R.id.subTitel1);
        subTitel1.setTypeface(myTypeface);
        TextView subTitel2 = (TextView)findViewById(R.id.subTitel2);
        subTitel2.setTypeface(myTypeface);
        TextView subTitel3 = (TextView)findViewById(R.id.subTitel3);
        subTitel3.setTypeface(myTypeface);
        TextView subTitel4 = (TextView)findViewById(R.id.subTitel4);
        subTitel4.setTypeface(myTypeface);


        int alpha = 108;
        ((TextView)findViewById(R.id.subTitel1)).setTextColor(Color.argb(alpha, 0, 0, 0));
        ((TextView)findViewById(R.id.subTitel2)).setTextColor(Color.argb(alpha, 0, 0, 0));
        ((TextView)findViewById(R.id.subTitel3)).setTextColor(Color.argb(alpha, 0, 0, 0));
        ((TextView)findViewById(R.id.subTitel4)).setTextColor(Color.argb(alpha, 0, 0, 0));


    }

    public void openTeaser(View view) {

        Intent showTeaserScherm = new Intent(view.getContext(), TeaserScherm.class);
        startActivity(showTeaserScherm);
        overridePendingTransition(R.anim.right_in, R.anim.stand_still);

    }



    public void openOrderTicket(View view) {

        Intent showBestelScherm = new Intent(view.getContext(), BestelScherm.class);
        startActivity(showBestelScherm);
        overridePendingTransition(R.anim.right_in, R.anim.stand_still);
    }

    public void openMyComics(View view) {

        Intent showMyComics = new Intent(view.getContext(), MyComicMenu.class);
        startActivity(showMyComics);
        overridePendingTransition(R.anim.bottom_in, R.anim.stand_still);
    }


    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";



    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();


             if(contents.equalsIgnoreCase("start de volgende activity"))

                 Log.d("jajaj","het is hetzelfde");
                    Intent showTeaserScherm = new Intent(this.getApplicationContext(), TeaserScherm.class);
                    startActivity(showTeaserScherm);
                    overridePendingTransition(R.anim.right_in, R.anim.stand_still);



            }
        }
    }








}