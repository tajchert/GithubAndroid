package pl.tajchert.githubpreview.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import pl.tajchert.githubpreview.R;

/**
 * Created by mtajc on 17.10.2016.
 */

public class AdapterViewPagerRepo extends FragmentPagerAdapter {
  private ArrayList<TabItemRepo> tabItemRepos = new ArrayList<>();
  private Context context;

  public AdapterViewPagerRepo(FragmentManager fm, Context context) {
    super(fm);
    this.context = context;
    tabItemRepos.add(new TabItemRepo("Code", 0));
    tabItemRepos.add(new TabItemRepo("Issues", 0));
    tabItemRepos.add(new TabItemRepo("Pull requests", 0));
    tabItemRepos.add(new TabItemRepo("Projects", 0));
    tabItemRepos.add(new TabItemRepo("Wiki", 0));
    tabItemRepos.add(new TabItemRepo("Pulse", 0));
    tabItemRepos.add(new TabItemRepo("Graphs", 0));
    tabItemRepos.add(new TabItemRepo("Settings", 0));//TODO change title to enum
  }

  @Override public int getCount() {
    return tabItemRepos.size();
  }

  @Override public Fragment getItem(int position) {
    return FragmentFolderStructure.newInstance(position + 1);
  }

  @Override public CharSequence getPageTitle(int position) {
    return tabItemRepos.get(position).title;
  }

  public View getTabView(Activity activity, int position, boolean isSelected) {
    View tab = LayoutInflater.from(activity).inflate(R.layout.tab_viewpager, null);

    if (tab != null) {
      TextView tabText = (TextView) tab.findViewById(R.id.tab_text);
      TextView tabBadge = (TextView) tab.findViewById(R.id.tab_badge);
      setTabSelected(isSelected, tabText, tabBadge);
      tabText.setText(tabItemRepos.get(position).title);
      tabBadge.setText(Integer.toString(tabItemRepos.get(position).counter));
    }

    return tab;
  }

  public static void setTabSelected(boolean isSelected, TextView tabText, TextView tabBadge) {
    if (isSelected) {
      if (tabText != null) {
        tabText.setAlpha(1f);
      }
      if (tabBadge != null) {
        tabBadge.setBackgroundResource(R.drawable.circle_accent);
        tabBadge.setTextColor(ContextCompat.getColor(tabBadge.getContext(), R.color.white));
      }
    } else {
      if (tabText != null) {
        tabText.setAlpha(0.7f);
      }
      if (tabBadge != null) {
        tabBadge.setBackgroundResource(R.drawable.circle_accent_dark);
        tabBadge.setTextColor(ContextCompat.getColor(tabBadge.getContext(), R.color.black_gray));
      }
    }
  }
}