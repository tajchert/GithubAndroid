package pl.tajchert.githubpreview.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mtajc on 27.10.2016.
 */

public class Core {
  @SerializedName("limit") @Expose public Long limit;
  @SerializedName("remaining") @Expose public Long remaining;
  @SerializedName("reset") @Expose public Long reset;
}
