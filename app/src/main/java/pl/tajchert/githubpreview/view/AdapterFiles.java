package pl.tajchert.githubpreview.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import pl.tajchert.githubpreview.R;

/**
 * Created by mtajc on 17.10.2016.
 */

public class AdapterFiles extends RecyclerView.Adapter<AdapterFiles.ViewHolder> {
  private ArrayList<String> mDataset;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public ViewHolder(View v) {
      super(v);
      //mTextView = (TextView) v.findViewById(R.id.textView);
    }
  }

  public AdapterFiles(ArrayList<String> myDataset) {
    mDataset = myDataset;
  }

  @Override public AdapterFiles.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    //holder.mTextView.setText(mDataset[position]);
  }

  @Override public int getItemCount() {
    return mDataset.size();
  }
}
