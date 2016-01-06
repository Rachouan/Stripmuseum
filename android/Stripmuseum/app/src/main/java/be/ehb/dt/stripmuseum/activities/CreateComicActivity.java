package be.ehb.dt.stripmuseum.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import be.ehb.dt.stripmuseum.R;
import be.ehb.dt.stripmuseum.dao.ComicDAO;

public class CreateComicActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView customImage;
    private ComicDAO comicDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_comic);

        customImage = (ImageView) findViewById(R.id.createImg);

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });


        ImageButton check = (ImageButton) findViewById(R.id.save);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);*/

                comicDAO = new ComicDAO(getApplicationContext());


                try{
                    comicDAO.open();

                    comicDAO.insertComic("Heey");

                    Log.d("INSERTED","true");

                    finish();

                } catch (SQLException e){

                    e.printStackTrace();
                    Log.d("ERROR","DID'T CATCH");

                }

                Log.d("SAVED","TRUE");
            }
        });


        ImageButton retry = (ImageButton) findViewById(R.id.retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takepic.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takepic, REQUEST_IMAGE_CAPTURE);
                }

            }
        });



        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takepic.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takepic, REQUEST_IMAGE_CAPTURE);
        }

    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageState() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Log.d("SAVED","TRUE");
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_comic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            customImage.setImageBitmap(imageBitmap);
        }else{
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }
    public void hideStatusBar(){

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
