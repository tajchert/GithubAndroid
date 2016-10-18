package pl.tajchert.githubpreview.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mtajc on 17.10.2016.
 */

public class AdapterViewPagerRepo extends FragmentPagerAdapter {
  final int PAGE_COUNT = 3;
  private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
  private Context context;

  public AdapterViewPagerRepo(FragmentManager fm, Context context) {
    super(fm);
    this.context = context;
  }

  @Override
  public int getCount() {
    return PAGE_COUNT;
  }

  @Override
  public Fragment getItem(int position) {
    return FragmentFolderStructure.newInstance(position + 1);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    // Generate title based on item position
    return tabTitles[position];
  }
}