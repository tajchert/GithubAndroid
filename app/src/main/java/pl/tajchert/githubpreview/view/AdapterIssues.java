package pl.tajchert.githubpreview.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import pl.tajchert.githubpreview.R;
import pl.tajchert.githubpreview.api.GithubIssue;

/**
 * Created by mtajc on 17.10.2016.
 */

public class AdapterIssues extends RecyclerView.Adapter<AdapterIssues.ViewHolder> {
  private ArrayList<GithubIssue> mDataset;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.issueTitle) TextView issueName;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }
  }

  public AdapterIssues(ArrayList<GithubIssue> myDataset) {
    mDataset = myDataset;
  }

  @Override public AdapterIssues.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_issue, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    GithubIssue githubIssue = mDataset.get(position);
    if (githubIssue == null) {
      return;
    }
    if (githubIssue.title != null) {
      holder.issueName.setText(githubIssue.title);
    }
  }

  @Override public int getItemCount() {
    return mDataset.size();
  }
}
