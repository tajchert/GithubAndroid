package pl.tajchert.githubpreview;

import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by mtajc on 16.10.2016.
 */

@Singleton @Component(modules = { ApplicationModule.class, NetModule.class }) public interface ApplicationComponent {

  void inject(AppGithubPreview app);

  void inject(MainActivity activity);

  @SuppressWarnings("unused")
  final class Initializer {
    static ApplicationComponent init(AppGithubPreview app) {
      return DaggerApplicationComponent.builder()
          .applicationModule(new ApplicationModule(app))
          .netModule(new NetModule("https://api.github.com/"))
          .build();
    }

    private Initializer() {
    } // No instances.
  }
}