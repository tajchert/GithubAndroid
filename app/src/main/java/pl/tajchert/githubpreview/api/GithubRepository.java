package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mtajc on 16.10.2016.
 */

public class GithubRepository extends GithubResponse {

  @SerializedName("id") @Expose public Long id;
  @SerializedName("full_name") @Expose public String fullName;
  @SerializedName("owner") @Expose public Owner owner;
  @SerializedName("private") @Expose public Boolean _private;
  @SerializedName("description") @Expose public String description;
  @SerializedName("fork") @Expose public Boolean fork;
  @SerializedName("forks_url") @Expose public String forksUrl;
  @SerializedName("keys_url") @Expose public String keysUrl;
  @SerializedName("collaborators_url") @Expose public String collaboratorsUrl;
  @SerializedName("teams_url") @Expose public String teamsUrl;
  @SerializedName("hooks_url") @Expose public String hooksUrl;
  @SerializedName("issue_events_url") @Expose public String issueEventsUrl;
  @SerializedName("events_url") @Expose public String eventsUrl;
  @SerializedName("assignees_url") @Expose public String assigneesUrl;
  @SerializedName("branches_url") @Expose public String branchesUrl;
  @SerializedName("tags_url") @Expose public String tagsUrl;
  @SerializedName("blobs_url") @Expose public String blobsUrl;
  @SerializedName("git_tags_url") @Expose public String gitTagsUrl;
  @SerializedName("git_refs_url") @Expose public String gitRefsUrl;
  @SerializedName("trees_url") @Expose public String treesUrl;
  @SerializedName("statuses_url") @Expose public String statusesUrl;
  @SerializedName("languages_url") @Expose public String languagesUrl;
  @SerializedName("stargazers_url") @Expose public String stargazersUrl;
  @SerializedName("contributors_url") @Expose public String contributorsUrl;
  @SerializedName("subscribers_url") @Expose public String subscribersUrl;
  @SerializedName("subscription_url") @Expose public String subscriptionUrl;
  @SerializedName("commits_url") @Expose public String commitsUrl;
  @SerializedName("git_commits_url") @Expose public String gitCommitsUrl;
  @SerializedName("comments_url") @Expose public String commentsUrl;
  @SerializedName("issue_comment_url") @Expose public String issueCommentUrl;
  @SerializedName("contents_url") @Expose public String contentsUrl;
  @SerializedName("compare_url") @Expose public String compareUrl;
  @SerializedName("merges_url") @Expose public String mergesUrl;
  @SerializedName("archive_url") @Expose public String archiveUrl;
  @SerializedName("downloads_url") @Expose public String downloadsUrl;
  @SerializedName("issues_url") @Expose public String issuesUrl;
  @SerializedName("pulls_url") @Expose public String pullsUrl;
  @SerializedName("milestones_url") @Expose public String milestonesUrl;
  @SerializedName("notifications_url") @Expose public String notificationsUrl;
  @SerializedName("labels_url") @Expose public String labelsUrl;
  @SerializedName("releases_url") @Expose public String releasesUrl;
  @SerializedName("deployments_url") @Expose public String deploymentsUrl;
  @SerializedName("created_at") @Expose public String createdAt;
  @SerializedName("updated_at") @Expose public String updatedAt;
  @SerializedName("pushed_at") @Expose public String pushedAt;
  @SerializedName("ssh_url") @Expose public String sshUrl;
  @SerializedName("clone_url") @Expose public String cloneUrl;
  @SerializedName("svn_url") @Expose public String svnUrl;
  @SerializedName("homepage") @Expose public String homepage;
  @SerializedName("stargazers_count") @Expose public Long stargazersCount;
  @SerializedName("watchers_count") @Expose public Long watchersCount;
  @SerializedName("language") @Expose public String language;
  @SerializedName("has_issues") @Expose public Boolean hasIssues;
  @SerializedName("has_downloads") @Expose public Boolean hasDownloads;
  @SerializedName("has_wiki") @Expose public Boolean hasWiki;
  @SerializedName("has_pages") @Expose public Boolean hasPages;
  @SerializedName("forks_count") @Expose public Long forksCount;
  @SerializedName("mirror_url") @Expose public Object mirrorUrl;
  @SerializedName("open_issues_count") @Expose public Long openIssuesCount;
  @SerializedName("forks") @Expose public Long forks;
  @SerializedName("open_issues") @Expose public Long openIssues;
  @SerializedName("watchers") @Expose public Long watchers;
  @SerializedName("default_branch") @Expose public String defaultBranch;
  @SerializedName("network_count") @Expose public Long networkCount;
  @SerializedName("subscribers_count") @Expose public Long subscribersCount;

  private transient String name;//TODO for some reason it crashes app when it is a field for GSON
  public transient GithubLicense license;
  public transient Long commitsCount;
  public transient Long contributorsCount;

  public GithubRepository(String name, Owner owner) {
    this.name = name;
    this.owner = owner;
  }

  public String getName() {
    if (name == null) {
      if (fullName != null && fullName.contains("/")) {
        String[] arr = fullName.split("/");
        if (arr.length >= 1) {
          name = arr[1];
        }
      }
    }
    return name;
  }

  public String getCommitsCount() {
    if (commitsCount == null) {
      commitsCount = 0L;
    }
    return Long.toString(commitsCount);
  }

  public String getContributorsCount() {
    if (contributorsCount == null) {
      contributorsCount = 0L;
    }
    return Long.toString(contributorsCount);
  }

  public String getSubscribersCount() {
    if (subscribersCount == null) {
      subscribersCount = 0L;
    }
    return Long.toString(subscribersCount);
  }

  public String getStargazersCount() {
    if (stargazersCount == null) {
      stargazersCount = 0L;
    }
    return Long.toString(stargazersCount);
  }

  public String getForksCount() {
    if (forksCount == null) {
      forksCount = 0L;
    }
    return Long.toString(forksCount);
  }
}
