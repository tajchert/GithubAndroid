package pl.tajchert.githubpreview.api;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
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

  @GET("repos/{ownerName}/{repositoryName}/license") Observable<GithubLicense> getRepoLicense(@Path("ownerName") String ownerName,
      @Path("repositoryName") String repositoryName);

  @GET("repositories/{repositoryId}/contents/{path}") Observable<List<File>> getFile(@Path("repositoryId") Long repositoryId,
      @Path("path") String fileName, @Query("ref") String ref);
}
