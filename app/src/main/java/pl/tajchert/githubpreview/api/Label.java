package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * Created by mtajc on 31.10.2016.
 */
@Parcel public class Label {

  @SerializedName("id") @Expose public Long id;
  @SerializedName("url") @Expose public String url;
  @SerializedName("name") @Expose public String name;
  @SerializedName("color") @Expose public String color;
  @SerializedName("default") @Expose public Boolean _default;

  public Label() {
  }
}
