package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mtajc on 16.10.2016.
 */

public class GithubUser {

  @SerializedName("login") @Expose public String login;
  @SerializedName("id") @Expose public Long id;
  @SerializedName("avatar_url") @Expose public String avatarUrl;
  @SerializedName("gravatar_id") @Expose public String gravatarId;
  @SerializedName("url") @Expose public String url;
  @SerializedName("html_url") @Expose public String htmlUrl;
  @SerializedName("followers_url") @Expose public String followersUrl;
  @SerializedName("following_url") @Expose public String followingUrl;
  @SerializedName("gists_url") @Expose public String gistsUrl;
  @SerializedName("starred_url") @Expose public String starredUrl;
  @SerializedName("subscriptions_url") @Expose public String subscriptionsUrl;
  @SerializedName("organizations_url") @Expose public String organizationsUrl;
  @SerializedName("repos_url") @Expose public String reposUrl;
  @SerializedName("events_url") @Expose public String eventsUrl;
  @SerializedName("received_events_url") @Expose public String receivedEventsUrl;
  @SerializedName("type") @Expose public String type;
  @SerializedName("site_admin") @Expose public Boolean siteAdmin;
  @SerializedName("name") @Expose public String name;
  @SerializedName("company") @Expose public String company;
  @SerializedName("blog") @Expose public String blog;
  @SerializedName("location") @Expose public String location;
  @SerializedName("email") @Expose public String email;
  @SerializedName("hireable") @Expose public Boolean hireable;
  @SerializedName("bio") @Expose public String bio;
  @SerializedName("public_repos") @Expose public Long publicRepos;
  @SerializedName("public_gists") @Expose public Long publicGists;
  @SerializedName("followers") @Expose public Long followers;
  @SerializedName("following") @Expose public Long following;
  @SerializedName("created_at") @Expose public String createdAt;
  @SerializedName("updated_at") @Expose public String updatedAt;
}
