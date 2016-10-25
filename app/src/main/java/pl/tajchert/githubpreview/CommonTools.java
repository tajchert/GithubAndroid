package pl.tajchert.githubpreview;

import android.content.res.Resources;

/**
 * Created by mtajc on 25.10.2016.
 */

public class CommonTools {
  public static int dpToPx(int dp) {
    return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
  }

  public static int pxToDp(int px) {
    return (int) (px / Resources.getSystem().getDisplayMetrics().density);
  }
}
