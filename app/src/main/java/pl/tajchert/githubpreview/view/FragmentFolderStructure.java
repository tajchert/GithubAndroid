package pl.tajchert.githubpreview.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import pl.tajchert.githubpreview.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFolderStructure extends Fragment {

  @BindView(R.id.recyclerViewFolders) RecyclerView recyclerViewFolders;
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  public static FragmentFolderStructure newInstance(int page) {
    Bundle args = new Bundle();
    FragmentFolderStructure fragment = new FragmentFolderStructure();
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
    mLayoutManager = new LinearLayoutManager(getContext());
    recyclerViewFolders.setLayoutManager(mLayoutManager);
    ArrayList<String> arrayList = new ArrayList<>();
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    arrayList.add("");
    mAdapter = new AdapterFiles(arrayList);
    recyclerViewFolders.setAdapter(mAdapter);
    return v;
  }
}
