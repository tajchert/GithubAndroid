package pl.tajchert.githubpreview;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.tajchert.githubpreview.api.ApiGithub;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mtajc on 16.10.2016.
 */
@Module public class NetModule {
  String mBaseUrl;

  public NetModule(String mBaseUrl) {
    this.mBaseUrl = mBaseUrl;
  }

  @Provides @Singleton public ApiGithub providesApiService() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl).addConverterFactory(GsonConverterFactory.create()).build();

    return retrofit.create(ApiGithub.class);
  }
}