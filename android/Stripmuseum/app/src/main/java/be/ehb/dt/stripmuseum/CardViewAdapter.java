package be.ehb.dt.stripmuseum;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CardViewAdapter extends FragmentPagerAdapter{

    private ArrayList<Ruimte> ruimtes;

    public CardViewAdapter(FragmentManager supportFragmentManager, Context applicationContext,ArrayList<Ruimte> alleruimtes) {
        super(supportFragmentManager);
        ruimtes = alleruimtes;
    }

    @Override
    public Fragment getItem(int position) {

        if(position < ruimtes.size()){

            Bundle bundle = new Bundle();
            bundle.putInt("ID", ruimtes.get(position).getId());
            bundle.putString("NAME", ruimtes.get(position).getName());
            bundle.putString("COMIC", ruimtes.get(position).getComic());
            bundle.putString("IMAGE", ruimtes.get(position).getImage());
            CardFragment c = new CardFragment();
            c.setArguments(bundle);
            return c;
        }else{
            return null;
        }

    }

    @Override
    public int getCount() {
        return ruimtes.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return ruimtes.get(position).getName();
    }
}
