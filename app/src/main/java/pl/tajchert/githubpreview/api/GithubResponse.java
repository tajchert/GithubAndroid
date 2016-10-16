package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mtajc on 16.10.2016.
 */

public class GithubResponse {
  @SerializedName("name") @Expose public String name;
  @SerializedName("size") @Expose public Long size;
  @SerializedName("url") @Expose public String url;
  @SerializedName("html_url") @Expose public String htmlUrl;
  @SerializedName("git_url") @Expose public String gitUrl;
}
