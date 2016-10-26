package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

/**
 * Created by mtajc on 16.10.2016.
 */
@Parcel public class Links {

  @SerializedName("self") @Expose public String self;
  @SerializedName("git") @Expose public String git;
  @SerializedName("html") @Expose public String html;
}
