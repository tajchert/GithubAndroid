package pl.tajchert.githubpreview;

import android.app.Application;
import android.content.Context;
import android.util.Log;
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
      Timber.plant(new CrashReportingTree());
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

  private static class CrashReportingTree extends Timber.Tree {
    @Override protected void log(int priority, String tag, String message, Throwable t) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return;
      }
      //Crashlytics.log(priority, tag, message);
      if (t != null && priority >= Log.WARN) {
        //Crashlytics.logException(t);
      }
    }
  }
}
