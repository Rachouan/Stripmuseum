package be.ehb.dt.stripmuseum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;

import net.sourceforge.zbar.Symbol;
;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ArrayList<ItemMenu> mainmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mainmenu = new ArrayList<ItemMenu>();
        mainmenu.add(new ItemMenu("TEASER OF THE WEEK",0,"HERGE THE ADVENTURES OF TINTIN",R.drawable.yellowmenu,new Intent(this,MainActivity.class)));
        mainmenu.add(new ItemMenu("SCAN TICKET",1,"HERGE THE ADVENTURES OF TINTIN",R.drawable.greenmenu,new Intent(this,ZBarScannerActivity.class)));
        mainmenu.add(new ItemMenu("ORDER TICKER",0,"HERGE THE ADVENTURES OF TINTIN",R.drawable.bluemenu,new Intent(this,MainActivity.class)));
        mainmenu.add(new ItemMenu("MY COMICS",0,"HERGE THE ADVENTURES OF TINTIN",R.drawable.redmenu,new Intent(this,MainActivity.class)));

        drawMenu();
    }

    public void drawMenu(){

        LinearLayout layout = (LinearLayout)findViewById(R.id.mainmenu);

        if(layout.getChildCount() > 0)layout.removeAllViews();

        int i = 0;
        for (ItemMenu item : mainmenu) {

            int height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;

            LinearLayout linear = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams linearParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height/mainmenu.size());


            LinearLayout child = (LinearLayout) getLayoutInflater().inflate(R.layout.menuitemlayout, null);
            //paramsLinearLayout =  child.getLayoutParams();
            child.setLayoutParams(linearParams);

            child.setBackgroundDrawable(getResources().getDrawable(item.getBg()));
            child.setId(i);

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(getApplicationContext(), Integer.toString(v.getId()), Toast.LENGTH_LONG).show();

                    ItemMenu selectedItem = mainmenu.get(v.getId());
                    Intent inten = selectedItem.getMainclass();

                    if(selectedItem.getType() == 1){
                        inten.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
                        startActivityForResult(inten, 0);
                    }else{
                        startActivity(inten);
                    }
                    //Intent room = selectedItem.getMainclass();



                }
            });
            TextViewPlus title = (TextViewPlus) child.findViewById(R.id.menutitle);
            title.setText(item.getTitle());
            TextView subtitle = (TextView) child.findViewById(R.id.menusubtitle);
            subtitle.setText(item.getSubtitle());
            layout.addView(child);


            i++;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            drawMenu();
            Intent roomsintent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(roomsintent);

        } else if(resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
            Intent roomsintent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(roomsintent);
        }
    }
}
