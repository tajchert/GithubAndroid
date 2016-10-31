package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * Created by mtajc on 01.11.2016.
 */
@Parcel public class Milestone {

  @SerializedName("url") @Expose public String url;
  @SerializedName("html_url") @Expose public String htmlUrl;
  @SerializedName("labels_url") @Expose public String labelsUrl;
  @SerializedName("id") @Expose public Long id;
  @SerializedName("number") @Expose public Long number;
  @SerializedName("title") @Expose public String title;
  @SerializedName("description") @Expose public String description;
  @SerializedName("creator") @Expose public User creator;
  @SerializedName("open_issues") @Expose public Long openIssues;
  @SerializedName("closed_issues") @Expose public Long closedIssues;
  @SerializedName("state") @Expose public String state;
  @SerializedName("created_at") @Expose public String createdAt;
  @SerializedName("updated_at") @Expose public String updatedAt;

  public Milestone() {
  }
}
