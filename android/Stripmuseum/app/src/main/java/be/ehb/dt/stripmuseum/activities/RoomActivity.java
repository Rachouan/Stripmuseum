package be.ehb.dt.stripmuseum.activities;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Locale;

import be.ehb.dt.stripmuseum.R;
import be.ehb.dt.stripmuseum.adapters.RoomListAdapter;
import be.ehb.dt.stripmuseum.assets.ImageDownloader;
import be.ehb.dt.stripmuseum.models.Info;

public class RoomActivity extends AppCompatActivity {

    private ListView mListView;
    private RoomListAdapter mAdapter;
    private String roomid;
    private LinearLayout detail;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        switch (Locale.getDefault().getLanguage()){
            case "en":
                lang = "en";
                break;
            case "nl":
                lang = "nl";
                break;
            case "fr":
                lang = "fr";
                break;

            default:
                lang = "en";
        }


        mListView = (ListView) findViewById(R.id.room_list);

        detail = (LinearLayout) findViewById(R.id.dragDown);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        int height = getResources().getDisplayMetrics().heightPixels - 150;
        params.topMargin = -height;

        detail.setLayoutParams(params);
        detail.setTranslationY(height);

        ImageButton close = (ImageButton) findViewById(R.id.closebtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int screenheight = getResources().getDisplayMetrics().heightPixels - 150;
                detail.animate().translationY(screenheight);
            }
        });

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomid = Integer.toString(extras.getInt("ROOM_ID"));
            Log.d("roomid",roomid);
            new HttpRequestTask().execute();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    protected void onResume() {
        super.onResume();
        hideStatusBar();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Info>> {
        @Override
        protected ArrayList<Info> doInBackground(Void... params) {
            try {
                final String url = "http://10.3.50.226:8080/Info/getAlleInfoPerLang?r_id="+roomid+"&lang="+lang;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //De volgende lijn maakt automatisch 1 ruimte object aan
                //Ruimte ruimte = restTemplate.getForObject(url, Ruimte.class);
                ArrayList result = restTemplate.getForObject(url, ArrayList.class, "SpringSource");
                Log.d("Toon resultaat: ", result.toString());
                ArrayList<Info> ruimteinfos = new ArrayList<Info>();


                for ( int i = 0; i < result.size() ; i++){
                    String object = result.get(i).toString();
                    String[] o = object.split(",");

                    Info r = new Info();
                    String il_id = o[0].replace("{il_id=", "");
                    String i_id = o[1].replace(" i_id=", "");
                    String info = o[2].replace(" info=", "");
                    String lang = o[3].replace(" lang=", "");
                    String name = o[4].replace(" name=", "");
                    String image = o[5].replace("image=", "");
                    image = image.replace("}", "");
                    r.setIl_id(Integer.parseInt(il_id));
                    r.setI_id(Integer.parseInt(i_id));
                    r.setName(name);
                    r.setInfo(info);
                    r.setLang(lang);
                    r.setImage(image);
                    ruimteinfos.add(r);
                }


                return ruimteinfos;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Info> ruimteInfos) {

            mAdapter = new RoomListAdapter(getApplicationContext(),ruimteInfos);
            mListView.setAdapter(mAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Info currentInfo =mAdapter.getItem(position);


                    detail.animate().translationY(0.0f);

                    ImageView image = (ImageView) detail.findViewById(R.id.fragImage);
                    String url = "http://rachouanrejeb.be/stripmuseum/info/"+currentInfo.getImage();

                    new ImageDownloader(image).execute(url.replace(" ", ""));

                    TextView text = (TextView) detail.findViewById(R.id.infoText);
                    text.setText(currentInfo.getInfo());

                    TextView text2 = (TextView) detail.findViewById(R.id.fragTitle);
                    text2.setText(currentInfo.getName());


                }
            });


        }


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
