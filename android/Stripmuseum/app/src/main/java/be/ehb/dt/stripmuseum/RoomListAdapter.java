package be.ehb.dt.stripmuseum;

import android.content.Context;
import android.media.Image;
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

        ImageView imageView = (ImageView) roomlist.findViewById(R.id.mainImage);

        info = getItem(position);
        Log.d("URL", "http://rachouanrejeb.be/stripmuseum/info/" + info.getImage());



        String url = "http://rachouanrejeb.be/stripmuseum/info/"+info.getImage();

        new ImageDownloader(imageView).execute(url.replace(" ", ""));


        return roomlist;

    }

}
