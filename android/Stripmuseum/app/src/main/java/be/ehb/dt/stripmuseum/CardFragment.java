package be.ehb.dt.stripmuseum;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rachouanrejeb on 04/01/16.
 */
public class CardFragment extends Fragment {

    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.card_layout, container, false);

        String txtTitle = getArguments().getString("NAME");

        title = (TextView) v.findViewById(R.id.cardTitle);
        title.setText(txtTitle);
        Log.d("NAME = ",txtTitle);
        txtTitle = getArguments().getString("COMIC");
        title = (TextView) v.findViewById(R.id.cardSubtitle);
        title.setText(txtTitle);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent room = new Intent(getActivity(),RoomActivity.class);
                room.putExtra("ROOM_ID", getArguments().getInt("ID"));
                startActivity(room);
            }
        });

        return v;
    }


}

