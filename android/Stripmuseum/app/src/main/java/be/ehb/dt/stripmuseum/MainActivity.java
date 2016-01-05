package be.ehb.dt.stripmuseum;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup tabs;
    private ArrayList<Ruimte> mainRuimteList;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        new HttpRequestTask().execute();
    }

    private final HorizontalPager.OnScreenSwitchListener onScreenSwitchListener =
            new HorizontalPager.OnScreenSwitchListener() {
                @Override
                public void onScreenSwitched(final int screen) {
                    // Check the appropriate button when the user swipes screens.
                    RadioButton check = (RadioButton) tabs.getChildAt(screen);
                    tabs.check(check.getId());
                }
            };

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


    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Ruimte>> {
        @Override
        protected ArrayList<Ruimte> doInBackground(Void... params) {
            try {
                final String url = "http://10.3.50.226:8080/Ruimtes/getAll";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //De volgende lijn maakt automatisch 1 ruimte object aan
                //Ruimte ruimte = restTemplate.getForObject(url, Ruimte.class);
                ArrayList result = restTemplate.getForObject(url, ArrayList.class, "SpringSource");
                Log.d("Toon resultaat: ", result.toString());
                ArrayList<Ruimte> ruimtes = new ArrayList<Ruimte>();


                for ( int i = 0; i < result.size() ; i++){
                    Log.d("Toon resultaat: ", result.get(i).toString());
                    String object = result.get(i).toString();
                    String[] o = object.split(",");
                    Ruimte r = new Ruimte();
                    Log.d("Toon resultaat: ",o[0].toString());
                    String id = o[0].replace("{id=", "");
                    String naam = o[1].replace(" name=", "");
                    String image = o[2].replace(" image=", "");
                    String info_id = o[3].replace(" info_id=", "");
                    String comic = o[4].replace(" comic=", "");
                    String creation_date = o[5].replace(" creation_date=", "");
                    creation_date = creation_date.replace("}", "");
                    r.setId(Integer.parseInt(id));
                    r.setName(naam);
                    r.setImage(image);
                    r.setInfo_id(Integer.parseInt(info_id));
                    r.setComic(comic);
                    r.setCreation_date(creation_date);
                    ruimtes.add(r);
                }
                return ruimtes;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Ruimte> ruimtes) {

            //HorizontalPager layout = (HorizontalPager)findViewById(R.id.horizontal_pager);

            viewPager = (ViewPager) findViewById(R.id.mainviewpager);
            viewPager.setAdapter(new CardViewAdapter(getSupportFragmentManager(), getApplicationContext(),ruimtes));

            tabLayout = (TabLayout) findViewById(R.id.logintablayout);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                    viewPager.setCurrentItem(tab.getPosition());

                }
            });

            /*if(layout.getChildCount() > 0)layout.removeAllViews();
            tabs = (RadioGroup) findViewById(R.id.tabs);
            if(tabs.getChildCount() > 0)tabs.removeAllViews();
            layout.setOnScreenSwitchListener(onScreenSwitchListener);

            int i = 0;
            for (Ruimte room : ruimtes) {

                View child = (View) getLayoutInflater().inflate(R.layout.card_layout, null);
                child.setId(room.getId());
                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent room = new Intent(getApplicationContext(),RoomActivity.class);
                        room.putExtra("ROOM_ID", v.getId());
                        startActivity(room);


                    }
                });
                TextView title = (TextView) child.findViewById(R.id.cardTitle);
                title.setText(room.getName());
                TextView subtitle = (TextView) child.findViewById(R.id.cardSubtitle);
                subtitle.setText(room.getComic());
                layout.addView(child);

                RadioButton check = new RadioButton(getApplicationContext());
                check.setBackgroundDrawable(getResources().getDrawable(R.drawable.pagecontrollers));

                Resources r = getResources();
                float checkButtonSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
                float margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());

                RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams((int)checkButtonSize, (int)checkButtonSize);

                layoutParams.setMargins((int)margin,(int)margin, (int)margin, (int)margin);

                check.setLayoutParams(layoutParams);

                tabs.addView(check);

                if(i == 0){
                    tabs.check(check.getId());
                }

                i++;

            }*/
        }
    }
}
