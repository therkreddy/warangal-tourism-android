
package com.rkreddy.warangalTourism;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.app.Activity;

public class DestinationListFragment extends Fragment {

  private int[] mImageResIds;
  private String[] mNames;
  private String[] mDescriptions;
  private String[] mUrls;
  private onDestinationSelected mListener;

  public static DestinationListFragment newInstance() {
    return new DestinationListFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (context instanceof onDestinationSelected) {
      mListener = (onDestinationSelected) context;
    } else {
      throw new ClassCastException(context.toString() + " must implement onDestinationSelected.");
    }

    // Get destination names and details.
    final Resources resources = context.getResources();
    mNames = resources.getStringArray(R.array.names);
    mDescriptions = resources.getStringArray(R.array.descriptions);
    mUrls = resources.getStringArray(R.array.urls);

    // Get destination images.
    final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
    final int imageCount = mNames.length;
    mImageResIds = new int[imageCount];
    for (int i = 0; i < imageCount; i++) {
      mImageResIds[i] = typedArray.getResourceId(i, 0);
    }
    typedArray.recycle();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_destination_list, container, false);

    final Activity activity = getActivity();
    final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
    recyclerView.setAdapter(new DestinationListAdapter(activity));
    return view;
  }

  class DestinationListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;

    public DestinationListAdapter(Context context) {
      mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      return new ViewHolder(mLayoutInflater
          .inflate(R.layout.recycler_item_destination, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
      final int imageResId = mImageResIds[position];
      final String name = mNames[position];
      final String description = mDescriptions[position];
      final String url = mUrls[position];
      viewHolder.setData(imageResId, name);

      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onDestinationSelected(imageResId, name, description, url);
        }
      });
    }

    @Override
    public int getItemCount() {
      return mNames.length;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    // Views
    private ImageView mImageView;
    private TextView mNameTextView;

    private ViewHolder(View itemView) {
      super(itemView);

      // Get references to image and name.
      mImageView = (ImageView) itemView.findViewById(R.id.destination_image);
      mNameTextView = (TextView) itemView.findViewById(R.id.name);
    }

    private void setData(int imageResId, String name) {
      mImageView.setImageResource(imageResId);
      mNameTextView.setText(name);
    }
  }

  public interface onDestinationSelected {
    void onDestinationSelected(int imageResId, String name,
                             String description, String url);
  }
}
