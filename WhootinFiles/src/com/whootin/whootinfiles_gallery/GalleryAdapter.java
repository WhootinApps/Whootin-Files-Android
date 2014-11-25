package com.whootin.whootinfiles_gallery;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.whootin.wf.R;
import com.whootin.whootinfiles_db.Constant;

public class GalleryAdapter extends BaseAdapter {

	private ArrayList<CustomGallery> data = new ArrayList<CustomGallery>();
	private Context mContext;
	private LayoutInflater infalter;
	private Boolean ListView = false;
	ImageLoader imageLoader;
	private boolean isActionMultiplePick;

	public GalleryAdapter(Context c, ImageLoader imageLoader2, Boolean _list) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		ListView = _list;
		this.imageLoader = imageLoader2;
		clearCache();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public CustomGallery getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setMultiplePick(boolean isMultiplePick) {
		this.isActionMultiplePick = isMultiplePick;
	}

	public void selectAll(boolean selection) {
		for (int i = 0; i < data.size(); i++) {
			data.get(i).isSeleted = selection;

		}
		notifyDataSetChanged();
	}

	public boolean isAllSelected() {
		boolean isAllSelected = true;

		for (int i = 0; i < data.size(); i++) {
			if (!data.get(i).isSeleted) {
				isAllSelected = false;
				break;
			}
		}

		return isAllSelected;
	}

	public boolean isAnySelected() {
		boolean isAnySelected = false;

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isSeleted) {
				isAnySelected = true;
				break;
			}
		}

		return isAnySelected;
	}

	public ArrayList<CustomGallery> getSelected() {
		ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).isSeleted) {
				dataT.add(data.get(i));
			}
		}

		return dataT;
	}

	public void addAll(ArrayList<CustomGallery> files) {

		try {
			this.data.clear();
			this.data.addAll(files);

		} catch (Exception e) {
			e.printStackTrace();
		}

		notifyDataSetChanged();
	}

	public void changeSelection(View v, int position) {

		if (data.get(position).isSeleted) {
			data.get(position).isSeleted = false;
		} else {
			data.get(position).isSeleted = true;
		}

		((ViewHolder) v.getTag()).imgQueueMultiSelected.setSelected(data
				.get(position).isSeleted);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		try {

			if (ListView == true) {

				if (convertView == null) {
					holder = new ViewHolder();
					convertView = infalter.inflate(R.layout.gallery_item, null);
					holder.imgQueue = (ImageView) convertView
							.findViewById(R.id.imgQueue);

					holder.imgQueueMultiSelected = (ImageView) convertView
							.findViewById(R.id.imgQueueMultiSelected);

					if (isActionMultiplePick) {
						holder.imgQueueMultiSelected
								.setVisibility(View.VISIBLE);
					} else {
						holder.imgQueueMultiSelected.setVisibility(View.GONE);
					}

					convertView.setTag(holder);

				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.imgQueue.setTag(position);

				holder.imgQueue.setImageResource(R.drawable.ic_list);

				Log.d("filepath", data.get(position).sdcardPath);
				String split = data.get(position).sdcardPath;
				if (split.contains(".mp4")) {
					Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(split,
							MediaStore.Video.Thumbnails.MINI_KIND);
					holder.imgQueue.setImageBitmap(bitmap);
				} else {
					imageLoader.displayImage("file://"
							+ data.get(position).sdcardPath, holder.imgQueue);
				}

			} else {

				if (convertView == null) {
					
					holder = new ViewHolder();
					convertView = infalter.inflate(R.layout.gallery_item_list, null);

					holder.imgQueue = (ImageView) convertView
							.findViewById(R.id.imgQueue_list);

					holder.imgQueueMultiSelected = (ImageView) convertView
							.findViewById(R.id.imgQueueMultiSelected_list);
					
					holder.imgQueueTextView = (TextView) convertView
							.findViewById(R.id.imgQueue_textview);

					if (isActionMultiplePick) {
						holder.imgQueueMultiSelected
								.setVisibility(View.VISIBLE);
					} else {
						holder.imgQueueMultiSelected.setVisibility(View.GONE);
					}

					convertView.setTag(holder);

				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.imgQueue.setTag(position);

				holder.imgQueue.setImageResource(R.drawable.ic_list);

				Log.d("filepath", data.get(position).sdcardPath);
				String split = data.get(position).sdcardPath;
				if (split.contains(".mp4")) {
					Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(split,
							MediaStore.Video.Thumbnails.MINI_KIND);
					holder.imgQueue.setImageBitmap(bitmap);
				} else {
					imageLoader.displayImage("file://"
							+ data.get(position).sdcardPath, holder.imgQueue);
				}
				String[] _split = split.split("/");
				holder.imgQueueTextView.setText(_split[_split.length-1]);
				
				
			}

			if (isActionMultiplePick) {

				holder.imgQueueMultiSelected
						.setSelected(data.get(position).isSeleted);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertView;
	}

	public class ViewHolder {
		ImageView imgQueue;
		ImageView imgQueueMultiSelected;
		TextView imgQueueTextView;
	}

	public void clearCache() {
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();
	}

	public void clear() {
		data.clear();
		notifyDataSetChanged();
	}
}
