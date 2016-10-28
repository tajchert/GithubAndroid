package pl.tajchert.githubpreview.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mikepenz.iconics.IconicsDrawable;
import java.util.ArrayList;
import pl.tajchert.githubpreview.R;
import pl.tajchert.githubpreview.api.File;

/**
 * Created by mtajc on 17.10.2016.
 */

public class AdapterFiles extends RecyclerView.Adapter<AdapterFiles.ViewHolder> {
  private ArrayList<File> mDataset;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fileName) TextView fileName;
    @BindView(R.id.fileType) ImageView fileType;

    public ViewHolder(View v) {
      super(v);
      ButterKnife.bind(this, v);
    }
  }

  public AdapterFiles(ArrayList<File> myDataset) {
    mDataset = myDataset;
  }

  @Override public AdapterFiles.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    File file = mDataset.get(position);
    if (file == null) {
      return;
    }
    if (file.name != null) {
      holder.fileName.setText(file.name);
    }
    if (file.type != null) {
      if ("dir".equals(file.type)) {
        holder.fileType.setImageDrawable(new IconicsDrawable(holder.fileType.getContext(), "oct-file-directory").colorRes(R.color.black_gray));
      } else {
        holder.fileType.setImageDrawable(new IconicsDrawable(holder.fileType.getContext(), "oct-file-text").colorRes(R.color.black_gray));
      }
    }
  }

  @Override public int getItemCount() {
    return mDataset.size();
  }
}
