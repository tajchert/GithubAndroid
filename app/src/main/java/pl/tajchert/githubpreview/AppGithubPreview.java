package pl.tajchert.githubpreview;

import android.app.Application;
import android.content.Context;
import timber.log.Timber;

/**
 * Created by mtajc on 16.10.2016.
 */

public class AppGithubPreview extends Application {
  ApplicationComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();
    createDaggerInjections();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
      //TODO Crashlytics or other
    }
  }

  public ApplicationComponent getAppComponent() {
    return appComponent;
  }

  public static AppGithubPreview getAppInstance(Context context) {
    return (AppGithubPreview) context.getApplicationContext();
  }

  private void createDaggerInjections() {
    appComponent = ApplicationComponent.Initializer.init(this);
    appComponent.inject(this);
  }
}
