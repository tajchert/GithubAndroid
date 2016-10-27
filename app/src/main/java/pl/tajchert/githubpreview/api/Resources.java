package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mtajc on 27.10.2016.
 */

public class Resources {
  @SerializedName("core") @Expose public Core core;
  @SerializedName("search") @Expose public Search search;
}
