package pl.tajchert.githubpreview.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.parceler.Parcels;
import pl.tajchert.githubpreview.AppGithubPreview;
import pl.tajchert.githubpreview.R;
import pl.tajchert.githubpreview.api.ApiGithub;
import pl.tajchert.githubpreview.api.File;
import pl.tajchert.githubpreview.api.GithubIssue;
import pl.tajchert.githubpreview.api.GithubRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.mikepenz.iconics.Iconics.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIssues extends Fragment {

  @BindView(R.id.recyclerViewFolders) RecyclerView recyclerViewFolders;
  private AdapterIssues mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private GithubRepository githubRepository;
  @Inject ApiGithub apiService;
  private ArrayList<GithubIssue> githubIssues = new ArrayList<>();

  public static FragmentIssues newInstance(GithubRepository githubRepository) {
    Bundle args = new Bundle();
    FragmentIssues fragment = new FragmentIssues();
    args.putParcelable("githubRepository", Parcels.wrap(githubRepository));
    fragment.setArguments(args);
    return fragment;
  }

  public FragmentIssues() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_issues, container, false);
    ButterKnife.bind(this, v);
    AppGithubPreview.getAppInstance(getActivity()).getAppComponent().inject(this);
    if (getArguments().containsKey("githubRepository")) {
      githubRepository = Parcels.unwrap(getArguments().getParcelable("githubRepository"));
      if (getArguments().containsKey("githubIssues")) {
        ArrayList<Parcelable> arr = getArguments().getParcelableArrayList("githubIssues");
        if (arr != null && arr.size() > 0) {
          for (Parcelable parc : arr) {
            GithubIssue fileUnwrapped = Parcels.unwrap(parc);
            if (fileUnwrapped != null) {
              githubIssues.add(fileUnwrapped);
            }
          }
        }
      } else {
        if (githubRepository != null) {
          if (githubRepository.owner != null && githubRepository.owner.login != null && githubRepository.getName() != null) {
            getIssues(githubRepository.owner.login, githubRepository.getName());
          }
        }
      }
    }
    mLayoutManager = new LinearLayoutManager(getContext());
    recyclerViewFolders.setLayoutManager(mLayoutManager);
    mAdapter = new AdapterIssues(githubIssues);
    recyclerViewFolders.setAdapter(mAdapter);
    return v;
  }

  private void getIssues(String ownerName, String repoName) {
    githubIssues.clear();
    if (mAdapter != null) {
      mAdapter.notifyDataSetChanged();
    }
    apiService.getIssues(ownerName, repoName).doOnSubscribe(() -> {
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Func1<List<GithubIssue>, Observable<?>>() {
      @Override public Observable<?> call(List<GithubIssue> githubIssues) {
        return Observable.from(githubIssues);
      }
    }).subscribe(issue -> {
      Log.d(TAG, "getIssues: ");
      if (githubIssues != null && issue != null && issue instanceof File && !githubIssues.contains(issue)) {
        githubIssues.add((GithubIssue) issue);
        if (mAdapter != null) {
          mAdapter.notifyDataSetChanged();
        }
      }
    }, e -> {
      Log.d(TAG, "getIssues: error: " + e.getLocalizedMessage());
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    ArrayList<Parcelable> arr = new ArrayList<>();
    for (GithubIssue issue : githubIssues) {
      arr.add(Parcels.wrap(issue));
    }
    outState.putParcelableArrayList("githubIssues", arr);
    outState.putParcelable("githubRepository", Parcels.wrap(githubRepository));
    super.onSaveInstanceState(outState);
  }
}
