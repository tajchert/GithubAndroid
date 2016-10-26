package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * Created by mtajc on 16.10.2016.
 */
@Parcel public class License {
  @SerializedName("key") @Expose public String key;
  @SerializedName("name") @Expose public String name;
  @SerializedName("url") @Expose public String url;
  @SerializedName("featured") @Expose public Boolean featured;
}