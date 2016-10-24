package pl.tajchert.githubpreview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import pl.tajchert.githubpreview.api.ApiGithub;
import pl.tajchert.githubpreview.api.GithubLicense;
import pl.tajchert.githubpreview.view.AdapterViewPagerRepo;
import pl.tajchert.githubpreview.view.CircularTextView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
  public static final String TAG = MainActivity.class.getCanonicalName();
  @Inject SharedPreferences sharedPreferences;
  @Inject Picasso picasso;
  @Inject ApiGithub apiService;

  @BindView(R.id.tabLayout) TabLayout tabLayout;
  @BindView(R.id.viewPager) ViewPager viewPager;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.nav_view) NavigationView navigationView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    AppGithubPreview.getAppInstance(this).getAppComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    AdapterViewPagerRepo adapterViewPagerRepo = new AdapterViewPagerRepo(getSupportFragmentManager(), MainActivity.this);
    viewPager.setAdapter(adapterViewPagerRepo);
    tabLayout.setupWithViewPager(viewPager);
    for (int i = 0; i < MainActivity.this.tabLayout.getTabCount(); i++) {
      TabLayout.Tab tab = MainActivity.this.tabLayout.getTabAt(i);
      if (tab != null) {
        tab.setText("");
        if (i == 0) {
          tab.setCustomView(adapterViewPagerRepo.getTabView(MainActivity.this, i, true));
        } else {
          tab.setCustomView(adapterViewPagerRepo.getTabView(MainActivity.this, i, false));
        }
      }
    }
    viewPager.addOnPageChangeListener(this);

    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        getRepoDetails();
      }
    });
    if (getIntent() != null && Intent.ACTION_VIEW.equals(getIntent().getAction())) {
      if (getIntent().getData() != null) {
        String Url = "www.github.com" + getIntent().getData().getPath();
        //TODO detect type of path (username, repo...)
        getRepoDetails();
      }
    }

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    navigationView.setNavigationItemSelectedListener(this);
  }

  private void getRepoDetails() {
    apiService.getRepositoryDetails("tajchert", "BusWear").doOnSubscribe(() -> {
      Timber.i("getRepositoryDetails - OnSubscribe");
    }).doOnCompleted(() -> {
      Timber.i("getRepositoryDetails - OnCompleted");
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(githubRepository -> {
      Observable<GithubLicense> repoLicense = apiService.getRepoLicense(githubRepository.owner.login, githubRepository.name)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
      return Observable.zip(repoLicense, Observable.just(githubRepository), Pair::new);
    }).subscribe(pair -> {
      Timber.i("getRepositoryDetails - onNext");
    }, e -> {
      Timber.i("getRepositoryDetails - onError");
    });
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
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

  @SuppressWarnings("StatementWithEmptyBody") @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    } else if (id == R.id.nav_manage) {

    } else if (id == R.id.nav_share) {

    } else if (id == R.id.nav_send) {

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
  }

  @Override public void onPageSelected(int position) {
    setActiveTab(position);
  }

  @Override public void onPageScrollStateChanged(int state) {
  }

  private void setActiveTab(int position) {
    for (int i = 0; i < MainActivity.this.tabLayout.getTabCount(); i++) {
      TabLayout.Tab tab = MainActivity.this.tabLayout.getTabAt(i);
      boolean isSelected = false;
      if (position == i) {
        isSelected = true;
      }
      if (tab != null && tab.getCustomView() != null) {
        TextView tabText = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
        CircularTextView tabBadge = (CircularTextView) tab.getCustomView().findViewById(R.id.tab_badge);
        AdapterViewPagerRepo.setTabSelected(isSelected, tabText, tabBadge);
      }
    }
  }
}
