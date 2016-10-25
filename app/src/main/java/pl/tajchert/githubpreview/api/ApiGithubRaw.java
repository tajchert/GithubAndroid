package pl.tajchert.githubpreview.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by mtajc on 16.10.2016.
 */

public interface ApiGithubRaw {
  @GET("{ownerName}/{repositoryName}/{branchName}/{fileName}") Observable<ResponseBody> getFile(@Path("ownerName") String ownerName,
      @Path("repositoryName") String repositoryName, @Path("branchName") String branchName, @Path("fileName") String fileName);
}
