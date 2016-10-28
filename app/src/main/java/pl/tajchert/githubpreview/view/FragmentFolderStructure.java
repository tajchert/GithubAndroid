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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import org.parceler.Parcels;
import pl.tajchert.githubpreview.AppGithubPreview;
import pl.tajchert.githubpreview.R;
import pl.tajchert.githubpreview.api.ApiGithub;
import pl.tajchert.githubpreview.api.File;
import pl.tajchert.githubpreview.api.GithubRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.mikepenz.iconics.Iconics.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFolderStructure extends Fragment {

  @BindView(R.id.recyclerViewFolders) RecyclerView recyclerViewFolders;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  private GithubRepository githubRepository;
  @Inject ApiGithub apiService;
  private ArrayList<File> filesList = new ArrayList<>();

  public static FragmentFolderStructure newInstance(GithubRepository githubRepository) {
    Bundle args = new Bundle();
    FragmentFolderStructure fragment = new FragmentFolderStructure();
    args.putParcelable("githubRepository", Parcels.wrap(githubRepository));
    fragment.setArguments(args);
    return fragment;
  }

  public FragmentFolderStructure() {
    // Required empty public constructor
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_folder_structure, container, false);
    ButterKnife.bind(this, v);
    AppGithubPreview.getAppInstance(getActivity()).getAppComponent().inject(this);
    if (getArguments().containsKey("githubRepository")) {
      githubRepository = Parcels.unwrap(getArguments().getParcelable("githubRepository"));
      if (getArguments().containsKey("filesList")) {
        ArrayList<Parcelable> arr = getArguments().getParcelableArrayList("filesList");
        if (arr != null && arr.size() > 0) {
          for (Parcelable parc : arr) {
            File fileUnwrapped = Parcels.unwrap(parc);
            if (fileUnwrapped != null) {
              filesList.add(fileUnwrapped);
            }
          }
        }
      } else {
        if (githubRepository != null) {
          if (githubRepository.id != null) {
            getFileStructure(githubRepository.id, "");
          }
        }
      }
    }
    mLayoutManager = new LinearLayoutManager(getContext());
    recyclerViewFolders.setLayoutManager(mLayoutManager);
    mAdapter = new AdapterFiles(filesList);
    recyclerViewFolders.setAdapter(mAdapter);
    return v;
  }

  private void getFileStructure(Long repoId, String file) {
    filesList.clear();
    if (mAdapter != null) {
      mAdapter.notifyDataSetChanged();
    }
    apiService.getFile(repoId, file, null).doOnSubscribe(() -> {
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Func1<List<File>, Observable<?>>() {
      @Override public Observable<?> call(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
          @Override public int compare(File file1, File file2) {
            if (file1 != null && file1.type != null) {
              int res = file1.type.compareToIgnoreCase(file2.type);
              if (res == 0) {
                if (file1.name != null) {
                  return file1.name.compareToIgnoreCase(file2.name);
                }
              }
              return res;
            } else {
              return 0;
            }
          }
        });
        return Observable.from(files);
      }
    }).subscribe(files -> {
      Log.d(TAG, "getFileStructure: ");
      if (filesList != null && files != null && files instanceof File && !filesList.contains(files)) {
        filesList.add((File) files);
        if (mAdapter != null) {
          mAdapter.notifyDataSetChanged();
        }
      }
    }, e -> {
      Log.d(TAG, "getFileStructure: error: " + e.getLocalizedMessage());
    });
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    ArrayList<Parcelable> arr = new ArrayList<>();
    for (File file : filesList) {
      arr.add(Parcels.wrap(file));
    }
    outState.putParcelableArrayList("filesList", arr);
    outState.putParcelable("githubRepository", Parcels.wrap(githubRepository));
    super.onSaveInstanceState(outState);
  }
}
