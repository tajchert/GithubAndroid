package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * Created by mtajc on 16.10.2016.
 */
@Parcel public class GithubLicense extends GithubResponse {
  @SerializedName("path") @Expose public String path;
  @SerializedName("sha") @Expose public String sha;
  @SerializedName("download_url") @Expose public String downloadUrl;
  @SerializedName("type") @Expose public String type;
  @SerializedName("content") @Expose public String content;
  @SerializedName("encoding") @Expose public String encoding;
  @SerializedName("_links") @Expose public Links links;
  @SerializedName("license") @Expose public License license;

  public GithubLicense() {
  }
}
