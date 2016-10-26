package pl.tajchert.githubpreview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;
import com.mukesh.MarkdownView;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import pl.tajchert.githubpreview.api.ApiGithub;
import pl.tajchert.githubpreview.api.ApiGithubRaw;
import pl.tajchert.githubpreview.api.File;
import pl.tajchert.githubpreview.api.GithubLicense;
import pl.tajchert.githubpreview.api.GithubRepository;
import pl.tajchert.githubpreview.api.Owner;
import pl.tajchert.githubpreview.databinding.ActivityMainBinding;
import pl.tajchert.githubpreview.view.AdapterViewPagerRepo;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {
  public static final String TAG = MainActivity.class.getCanonicalName();
  @Inject SharedPreferences sharedPreferences;
  @Inject Picasso picasso;
  @Inject ApiGithub apiService;
  @Inject ApiGithubRaw apiRawService;

  @BindView(R.id.tabLayout) TabLayout tabLayout;
  @BindView(R.id.viewPager) ViewPager viewPager;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
  @BindView(R.id.nav_view) NavigationView navigationView;
  @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
  @BindView(R.id.appBar) AppBarLayout appBarLayout;
  @BindView(R.id.toolbarContentLayout) LinearLayout toolbarContentLayout;
  @BindView(R.id.swipeRefreshRepo) SwipeRefreshLayout swipeRefreshRepo;
  @BindView(R.id.bottom_sheet) RelativeLayout bottomSheetLayout;
  @BindView(R.id.fileName) TextView textFileName;
  @BindView(R.id.fileContent) MarkdownView textFileContent;
  private BottomSheetBehavior mBottomSheetBehavior;
  private ActivityMainBinding binding;
  private GithubRepository repo;

  @Override protected void onCreate(Bundle savedInstanceState) {
    LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    AppGithubPreview.getAppInstance(this).getAppComponent().inject(this);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    setViewPagerWithTabs();
    setToolbar();
    getRepoDetails("BusWear", "tajchert");
    mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    bottomSheetLayout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
      }
    });
    mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override public void onStateChanged(@NonNull View bottomSheet, int newState) {
        if (newState != BottomSheetBehavior.STATE_COLLAPSED
            && newState != BottomSheetBehavior.STATE_HIDDEN
            && newState != BottomSheetBehavior.STATE_SETTLING) {
          if (appBarLayout != null) {
            appBarLayout.setExpanded(false);
          }
        }
      }

      @Override public void onSlide(@NonNull View bottomSheet, float slideOffset) {
      }
    });
    swipeRefreshRepo.setEnabled(false);
    swipeRefreshRepo.setOnRefreshListener(this);
    swipeRefreshRepo.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
    swipeRefreshRepo.setProgressViewOffset(false, 0, CommonTools.dpToPx(156));
    fab.setImageDrawable(new IconicsDrawable(this, "gmd-star_border").colorRes(R.color.white));
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
      }
    });
    if (getIntent() != null && Intent.ACTION_VIEW.equals(getIntent().getAction())) {
      if (getIntent().getData() != null) {
        String Url = "www.github.com" + getIntent().getData().getPath();
        //TODO detect type of path (username, repo...)
        getRepoDetails("BusWear", "tajchert");
      }
    }

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    navigationView.setNavigationItemSelectedListener(this);
  }

  private void setToolbar() {
    collapsingToolbarLayout.setTitle(" ");
    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      boolean isShow = false;
      int scrollRange = -1;

      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1) {
          scrollRange = appBarLayout.getTotalScrollRange();
        }
        if (scrollRange + verticalOffset == 0) {
          if (repo != null && repo.getName() != null) {
            collapsingToolbarLayout.setTitle(repo.getName());
          }
          toolbarContentLayout.setVisibility(View.GONE);
          isShow = true;
        } else if (isShow) {
          toolbarContentLayout.setVisibility(View.VISIBLE);
          collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
          isShow = false;
        }
      }
    });
  }

  private void setViewPagerWithTabs() {
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
  }

  private void getRepoDetails(String repoName, String authorLogin) {
    if (binding != null) {
      binding.setRepo(new GithubRepository(repoName, new Owner(authorLogin)));
    }
    apiService.getRepositoryDetails(authorLogin, repoName).doOnSubscribe(() -> {
      Timber.i("getRepositoryDetails - OnSubscribe");
      if (swipeRefreshRepo != null) {
        swipeRefreshRepo.setRefreshing(true);
      }
    }).doOnCompleted(() -> {
      Timber.i("getRepositoryDetails - OnCompleted");
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(githubRepository -> {
      Observable<GithubLicense> repoLicense = apiService.getRepoLicense(githubRepository.owner.login, githubRepository.getName())
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
      return Observable.zip(repoLicense, Observable.just(githubRepository), Pair::new);
    }).flatMap(pair -> {
      GithubRepository repository = pair.second;
      if (pair.first != null) {
        repository.license = pair.first;
      }
      return Observable.just(repository);
    }).subscribe(repository -> {
      Timber.i("getRepositoryDetails - onNext");
      MainActivity.this.repo = repository;
      if (binding != null) {
        binding.setRepo(repository);
        binding.progressIndicator.setVisibility(View.GONE);
      }
      if (swipeRefreshRepo != null) {
        swipeRefreshRepo.setRefreshing(false);
      }
      if (repository != null) {
        getReadMeContent(repoName, authorLogin);
        if (repo.id != null) {
          getFileStructure(repo.id, "");
        }
      }
    }, e -> {
      Timber.i("getRepositoryDetails - onError: " + e.getLocalizedMessage());
      if (binding != null) {
        binding.progressIndicator.setVisibility(View.GONE);
      }
      if (swipeRefreshRepo != null) {
        swipeRefreshRepo.setRefreshing(false);
      }
      if (fab != null) {
        Snackbar.make(fab, "Something failed, try again.", Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
          @Override public void onClick(View view) {
            getRepoDetails("BusWear", "tajchert");
          }
        }).show();
      }
    });
  }

  private void getReadMeContent(String repoName, String authorLogin) {
    apiService.getReadmeDetails(authorLogin, repoName).doOnSubscribe(() -> {
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(repositoryReadme -> {//TODO handle other branches than master
      Observable<ResponseBody> repoLicense = apiRawService.getFile(authorLogin, repoName, "master", repositoryReadme.name)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
      return Observable.zip(repoLicense, Observable.just(repositoryReadme), Pair::new);
    }).subscribe(pair -> {
      Log.d(TAG, "getReadMeContent: ");
      if (pair.first != null) {
        if (textFileContent != null) {
          try {
            textFileContent.setMarkDownText(pair.first.string());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      if (pair.second != null) {
        if (textFileName != null) {
          textFileName.setText(pair.second.name);
        }
      }
      if (mBottomSheetBehavior != null) {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
      }
    }, e -> {
      Log.e(TAG, "getReadMeContent: error: " + e.getLocalizedMessage());
    });
  }

  private void getFileStructure(Long repoId, String file) {
    apiService.getFile(repoId, file, null).doOnSubscribe(() -> {
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Func1<List<File>, Observable<?>>() {
      @Override public Observable<?> call(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
          @Override public int compare(File file1, File file2) {
            if (file1 != null && file1.type != null) {
              int res = file1.type.compareToIgnoreCase(file2.type);
              if (res == 0) {
                if (file1.name != null) {
                  return file1.name.compareToIgnoreCase(file2.name);
                }
              }
              return res;
            } else {
              return 0;
            }
          }
        });
        return Observable.from(files);
      }
    }).subscribe(files -> {
      Log.d(TAG, "getFileStructure: ");//TODO handle file
    }, e -> {
      Log.d(TAG, "getFileStructure: error: " + e.getLocalizedMessage());
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
        TextView tabBadge = (TextView) tab.getCustomView().findViewById(R.id.tab_badge);
        AdapterViewPagerRepo.setTabSelected(isSelected, tabText, tabBadge);
      }
    }
  }

  @OnClick(R.id.textRepoNames) public void onClickUsername() {
    //TODO it works poorly (due to all coordinatorLayouts?)
    if (repo != null && repo.owner != null) {
      MainActivity.this.startActivity(UserDetailsActivity.getInstance(MainActivity.this, repo.owner));
    }
  }

  @Override public void onRefresh() {
    getRepoDetails("BusWear", "tajchert");
  }
}
