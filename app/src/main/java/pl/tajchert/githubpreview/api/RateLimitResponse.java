package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mtajc on 16.10.2016.
 */

public class RateLimitResponse {
  @SerializedName("resources") @Expose public Resources resources;
  @SerializedName("rate") @Expose public Rate rate;
}
