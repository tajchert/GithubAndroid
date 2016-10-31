package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcel;

/**
 * Created by mtajc on 31.10.2016.
 */

@Parcel public class GithubIssue {
  @SerializedName("url") @Expose public String url;
  @SerializedName("repository_url") @Expose public String repositoryUrl;
  @SerializedName("labels_url") @Expose public String labelsUrl;
  @SerializedName("comments_url") @Expose public String commentsUrl;
  @SerializedName("events_url") @Expose public String eventsUrl;
  @SerializedName("html_url") @Expose public String htmlUrl;
  @SerializedName("id") @Expose public Long id;
  @SerializedName("number") @Expose public Long number;
  @SerializedName("title") @Expose public String title;
  @SerializedName("user") @Expose public User user;
  @SerializedName("labels") @Expose public List<Label> labels = new ArrayList<Label>();
  @SerializedName("state") @Expose public String state;
  @SerializedName("locked") @Expose public Boolean locked;
  @SerializedName("assignee") @Expose public User assignee;
  @SerializedName("assignees") @Expose public List<User> assignees = new ArrayList<User>();
  @SerializedName("milestone") @Expose public Milestone milestone;
  @SerializedName("comments") @Expose public Long comments;
  @SerializedName("created_at") @Expose public String createdAt;
  @SerializedName("updated_at") @Expose public String updatedAt;
  @SerializedName("body") @Expose public String body;

  public GithubIssue() {
  }
}
