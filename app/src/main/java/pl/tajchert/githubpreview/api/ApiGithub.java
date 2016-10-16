package pl.tajchert.githubpreview.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mtajc on 16.10.2016.
 */

public interface ApiGithub {
  @GET("repos/{ownerName}/{repositoryName}") Call<GithubRepository> getRepositoryDetails(@Path("ownerName") String ownerName,
      @Path("repositoryName") String repositoryName);
}
