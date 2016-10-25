package pl.tajchert.githubpreview;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.tajchert.githubpreview.api.ApiGithubRaw;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mtajc on 16.10.2016.
 */
@Module public class NetRawModule {
  String mBaseUrl;

  public NetRawModule(String mBaseUrl) {
    this.mBaseUrl = mBaseUrl;
  }

  @Provides @Singleton public ApiGithubRaw providesApiService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    return retrofit.create(ApiGithubRaw.class);
  }
}