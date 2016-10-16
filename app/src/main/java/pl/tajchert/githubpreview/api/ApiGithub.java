package pl.tajchert.githubpreview.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mtajc on 16.10.2016.
 */

public interface ApiGithub {
  @GET("users/{userName}/") Observable<GithubUser> getUserDetails(@Path("userName") String userName);

  @GET("repos/{ownerName}/{repositoryName}") Observable<GithubRepository> getRepositoryDetails(@Path("ownerName") String ownerName,
      @Path("repositoryName") String repositoryName);

  @GET("repos/{ownerName}/{repositoryName}/readme") Observable<RepositoryReadme> getReadmeDetails(@Path("ownerName") String ownerName,
      @Path("repositoryName") String repositoryName);

  @GET("repos/{ownerName}/{repositoryName}//license") Observable<GithubLicense> getRepoLicense(@Path("ownerName") String ownerName,
      @Path("repositoryName") String repositoryName);
}
