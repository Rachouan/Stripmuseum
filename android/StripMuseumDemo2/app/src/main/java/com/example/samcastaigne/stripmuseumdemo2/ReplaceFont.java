package com.example.samcastaigne.stripmuseumdemo2;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by samcastaigne on 14/12/15.
 */
public class ReplaceFont {

    public static void replaceDefaultFont(Context context, String nameOfFontBeingReplaced, String nameOfFontInAssets) {

        Typeface bariolBold = Typeface.createFromAsset(context.getAssets(), nameOfFontInAssets);
        replaceFont(nameOfFontBeingReplaced, bariolBold);

    }

    private static void replaceFont(String namOfFontBeingReplaced, Typeface bariolBold) {
        try {
            Field myfield = Typeface.class.getDeclaredField(namOfFontBeingReplaced);
            myfield.setAccessible(true);
            myfield.set(null, bariolBold);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
