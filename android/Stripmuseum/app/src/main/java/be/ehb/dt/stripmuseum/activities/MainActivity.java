package be.ehb.dt.stripmuseum.activities;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import com.viewpagerindicator.CirclePageIndicator;

import org.altbeacon.beacon.BeaconManager;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Locale;

import be.ehb.dt.stripmuseum.R;
import be.ehb.dt.stripmuseum.adapters.CardViewAdapter;
import be.ehb.dt.stripmuseum.models.Ruimte;

public class MainActivity extends AppCompatActivity {

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private static int MAX = -10;
    private static int MIN = -100;
    private Boolean inRange = null;
    private RadioGroup tabs;
    private ArrayList<Ruimte> mainRuimteList;
    private ViewPager viewPager;
    private CirclePageIndicator tabLayout;
    private String lang;

    protected static final String TAG = "MonitoringActivity";
    private BeaconManager beaconManager;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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

        setContentView(R.layout.activity_main2);

        bluetoothManager = (BluetoothManager) getSystemService(getApplicationContext().BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();

        bluetoothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, final int rssi, byte[] scanRecord) {

                if(device.getAddress().equals("F2:78:17:07:B3:F2")){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           if(inRange == null){
                               if(rssi > MIN){
                                   inRange = true;
                               }
                           }else{

                               if(inRange){
                                   if(rssi < MAX){
                                       inRange = !inRange;
                                   }
                               }else if (rssi > MIN) {

                                   inRange = !inRange;

                                   Snackbar beacondetected = Snackbar.make(viewPager.getRootView(), "Experience Herge space", Snackbar.LENGTH_INDEFINITE).setAction("Enter", new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent room = new Intent(getApplicationContext(), RoomActivity.class);
                                           room.putExtra("ROOM_ID", 1);
                                           startActivity(room);
                                       }
                                   });

                                   beacondetected.show();

                                   Log.d("STRING", "BEACON FOUND");

                               }

                           }
                       }
                   });

                }

            }
        });


        ImageButton backbtn = (ImageButton) findViewById(R.id.backbutton);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        new HttpRequestTask().execute();
    }



    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Ruimte>> {
        @Override
        protected ArrayList<Ruimte> doInBackground(Void... params) {
            try {
                final String url = "http://10.3.50.226:8080/Ruimtes/getAll?lang="+lang;
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


            viewPager = (ViewPager) findViewById(R.id.mainviewpager);
            viewPager.setAdapter(new CardViewAdapter(getSupportFragmentManager(), getApplicationContext(),ruimtes));

            tabLayout = (CirclePageIndicator)findViewById(R.id.titles);
            tabLayout.setViewPager(viewPager);
            tabLayout.setSnap(true);


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
