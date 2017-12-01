package ir.oveissi.searchmovies.utils;

import android.content.res.Resources;

/**
 * Created by abbas on 7/10/16.
 */
public class Utility {

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static boolean isNotNullOrEmpty(String string) {
        return !(string == null || string.length() == 0);
    }
}
