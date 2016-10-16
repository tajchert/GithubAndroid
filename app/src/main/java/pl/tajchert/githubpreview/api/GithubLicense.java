package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mtajc on 16.10.2016.
 */

public class GithubLicense {
  @SerializedName("name") @Expose public String name;
  @SerializedName("path") @Expose public String path;
  @SerializedName("sha") @Expose public String sha;
  @SerializedName("size") @Expose public Long size;
  @SerializedName("url") @Expose public String url;
  @SerializedName("html_url") @Expose public String htmlUrl;
  @SerializedName("git_url") @Expose public String gitUrl;
  @SerializedName("download_url") @Expose public String downloadUrl;
  @SerializedName("type") @Expose public String type;
  @SerializedName("content") @Expose public String content;
  @SerializedName("encoding") @Expose public String encoding;
  @SerializedName("_links") @Expose public Links links;
  @SerializedName("license") @Expose public License license;
}
