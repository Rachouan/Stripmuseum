package be.ehb.dt.stripmuseum;

import android.annotation.TargetApi;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by rachouanrejeb on 05/01/16.
 */
public class DetailFragment extends Fragment {

    ImageButton close;
    TextViewPlus title;
    Button like;
    View mainview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainview = inflater.inflate(R.layout.detail_layout, container, false);

        String txtTitle = "";
        //

        Bundle arguments = getArguments();
        if (arguments != null)
        {
            txtTitle  = arguments.getString("INFO_NAME");
        }

        ImageView imageView = (ImageView) mainview.findViewById(R.id.fragImage);
        new ImageDownloader(imageView).execute("http://rachouanrejeb.be/stripmuseum/raket.jpg");

        title = (TextViewPlus) mainview.findViewById(R.id.fragTitle);
        title.setText(txtTitle);

        close = (ImageButton) mainview.findViewById(R.id.closebtn);

        close.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                Log.d("ACTION","CLOSE");

                //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                //params.topMargin = 0;

                int height = getContext().getResources().getDisplayMetrics().heightPixels;

                getView().animate().translationY(height).withLayer();

            }
        });

        return mainview;


    }
}
