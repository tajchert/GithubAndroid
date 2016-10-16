package pl.tajchert.githubpreview;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by mtajc on 16.10.2016.
 */

@Module public class ApplicationModule {
  private static final String PREF_NAME = "pl.tajchert.githubpreview";
  private final AppGithubPreview app;

  ApplicationModule(AppGithubPreview app) {
    this.app = app;
  }

  @Provides @Singleton Application application() {
    return app;
  }

  @Singleton @Provides public Picasso getPicasso() {
    return new Picasso.Builder(app).build();
  }

  @Singleton @Provides public SharedPreferences getAppPreferences() {
    return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
  }
}
