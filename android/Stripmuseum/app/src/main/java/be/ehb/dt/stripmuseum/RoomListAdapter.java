package be.ehb.dt.stripmuseum;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by rachouanrejeb on 04/01/16.
 */
public class RoomListAdapter extends ArrayAdapter<Info>{

    private Info info;

    public RoomListAdapter(Context context, ArrayList<Info> rooms ) {
        super(context, R.layout.room_list_layout ,rooms);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater playInflater = LayoutInflater.from(getContext());

        View roomlist = playInflater.inflate(R.layout.room_list_layout, parent, false);

        info = getItem(position);
        new HttpRequestTask().execute();


        return roomlist;

    }


    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Info>> {
        @Override
        protected ArrayList<Info> doInBackground(Void... params) {
            try {
                final String url = "http://10.3.50.226:8080/Info/getImageById?i_id="+info.getI_id();
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                //De volgende lijn maakt automatisch 1 ruimte object aan
                //Ruimte ruimte = restTemplate.getForObject(url, Ruimte.class);
                ArrayList result = restTemplate.getForObject(url, ArrayList.class, "SpringSource");
                Log.d("Toon resultaat: ", result.toString());
                ArrayList<Info> ruimteinfos = new ArrayList<Info>();

                return ruimteinfos;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Info> ruimteInfos) {

        }
    }
}
