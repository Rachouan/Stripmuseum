package be.ehb.dt.stripmuseum;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {

    private ListView mListView;
    private RoomListAdapter mAdapter;
    private String roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);



        mListView = (ListView) findViewById(R.id.room_list);


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

    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Info>> {
        @Override
        protected ArrayList<Info> doInBackground(Void... params) {
            try {
                final String url = "http://10.3.50.226:8080/Info/getAlleInfoPerLang?r_id="+roomid+"&lang=en";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //De volgende lijn maakt automatisch 1 ruimte object aan
                //Ruimte ruimte = restTemplate.getForObject(url, Ruimte.class);
                ArrayList result = restTemplate.getForObject(url, ArrayList.class, "SpringSource");
                Log.d("Toon resultaat: ", result.toString());
                ArrayList<Info> ruimteinfos = new ArrayList<Info>();


                for ( int i = 0; i < result.size() ; i++){
                    Log.d("Toon resultaat: ", result.get(i).toString());
                    String object = result.get(i).toString();
                    String[] o = object.split(",");
                    Info r = new Info();
                    Log.d("Toon resultaat: ",o[0].toString());
                    String il_id = o[0].replace("{il_id=", "");
                    String i_id = o[1].replace(" i_id=", "");
                    String info = o[2].replace(" info=", "");
                    String lang = o[1].replace(" lang=", "");
                    String name = o[4].replace(" name=", "");
                    String image = o[5].replace("image=", "");
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


        }
    }

}
