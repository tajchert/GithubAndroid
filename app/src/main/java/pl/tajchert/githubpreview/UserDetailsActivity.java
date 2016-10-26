package pl.tajchert.githubpreview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import org.parceler.Parcels;
import pl.tajchert.githubpreview.api.ApiGithub;
import pl.tajchert.githubpreview.api.Owner;

public class UserDetailsActivity extends AppCompatActivity {
  public static final String TAG = UserDetailsActivity.class.getCanonicalName();
  @Inject SharedPreferences sharedPreferences;
  @Inject Picasso picasso;
  @Inject ApiGithub apiService;

  public static Intent getInstance(Context context, Owner owner) {
    Intent intent = new Intent(context, UserDetailsActivity.class);
    if (owner == null) {
      return intent;
    }
    intent.putExtra("owner", Parcels.wrap(owner));
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_details);
    AppGithubPreview.getAppInstance(this).getAppComponent().inject(this);
    if (getIntent().getExtras() != null) {
      if (getIntent().getExtras().containsKey("owner")) {
        Owner owner = Parcels.unwrap(getIntent().getExtras().getParcelable("owner"));
        Log.d(TAG, "onCreate starting with Owner");
      }
    }
  }
}
