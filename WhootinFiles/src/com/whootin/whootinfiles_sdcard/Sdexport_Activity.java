package com.whootin.whootinfiles_sdcard;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.whootin.wf.Folder_LoginActivity;
import com.whootin.wf.Login_Activity;
import com.whootin.wf.R;
import com.whootin.whootinfiles_db.ConnectionDetector;
import com.whootin.whootinfiles_db.Constant;
import com.whootin.whootinfiles_db.Files_Db;
import com.whootin.whootinfiles_gallery.Action;
import com.whootin.whootinfiles_sdcard.Sd_Folder.SdAdapter;
import com.whootin.whootinfiles_sdcard.Sd_Folder.SdAdapter.ViewHolder;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Sdexport_Activity extends Activity implements OnClickListener {

	private File root;
	ImageView sde_imgNoMedia;
	Button btn_back, btn_export, btn_cancel;
	private String[] all_path = null;
	String action, common = null, folder_name = null, get_fldid = null;
	private ImageLoader imageLoader;
	ProgressDialog prg_bar;
	public File imgtemp = null;
	Context context;
	GridView sde_gridGallery;
	Handler handler;
	SdAdapter sde_adapter;
	Boolean back_pressed = false;
	private ArrayList<String> sde_list = null;
	String sde = null;
	Boolean dwn_load = false;

	private String url_url = null, url_nm = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exports);
		context = this;
		Bundle bundle = getIntent().getExtras();
		url_nm = bundle.getString("name");
		url_url = bundle.getString("url");
		Log.d("fld", url_url);
		boolean bool = true;

		ArrayList<HashMap<String, String>> act_listcv = Constant.db
				.getAllTranslates(Files_Db.Table, bool);
		for (int i = 0; i < act_listcv.size(); i++) {
			Log.d("url", act_listcv.get(i).get(Files_Db.File_name));
			Log.d("url", act_listcv.get(i).get(Files_Db.File_url));
		}

		sde_list = new ArrayList<String>();
		action = Action.ACTION_MULTIPLE_PICK;

		root = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		Log.d("root-directory", root.toString());
		Log.d("root-directory", String.valueOf(sde_list.size()));
		sde_list.add(root.toString());
		Log.d("root-directory", String.valueOf(sde_list.size()));
		initImageLoader();
		init(root);

	}

	AdapterView.OnItemClickListener sdItemMulClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			File spt = null;
			ArrayList<SdAdapters> dot = null;

			if (position == 0) {
				if (sde_list != null && sde_list.size() > 1) {
					if (sde_list.size() == 1) {
						int dex = sde_list.size() - 1;
						Log.d("dex", String.valueOf(sde_list.size()));
						String sd = String.valueOf(sde_list.get(dex));
						Log.d("dex", String.valueOf(sde_list.get(dex) + dex));
						File root = new File(sd);
						initImageLoader();
						init(root);
						back_pressed = true;
					} else if (sde_list.size() > 1) {
						int dex = sde_list.size() - 2;
						Log.d("dex", String.valueOf(sde_list.size()));
						String sd = String.valueOf(sde_list.get(dex));
						Log.d("dex", String.valueOf(sde_list.get(dex) + dex));
						File root = new File(sd);
						initImageLoader();
						init(root);
						back_pressed = true;
					}
				} else {
					Sdexport_Activity.this.finish();
				}
			} else {
				if (sde_list != null) {
					if (sde_list.size() == 0) {
						root = new File(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath());
						dot = getGalleryPhotos(root);
						Log.d("on_click_position", String.valueOf(position));
						Log.d("on_click_event", dot.get(position).sdcardPath);
						String split = dot.get(position).sdcardPath;
						spt = new File(split);
					} else if (sde_list.size() > 0) {
						Log.d("sd_list_size", String.valueOf(sde_list.size()));
						String sd_get = sde_list.get(sde_list.size() - 1);
						Log.d("sd_lsit", sd_get);
						File update_path = new File(sd_get);
						dot = getGalleryPhotos(update_path);
						Log.d("on_click_event",
								dot.get(position).sdcardPath.toString());
						String split = dot.get(position).sdcardPath;
						spt = new File(split);
						Log.d("sd_list_position", String.valueOf(position));
					}
				}

				if (spt != null) {
					if (spt.getName().endsWith(".png")
							|| spt.getName().endsWith(".jpg")
							|| spt.getName().endsWith(" .jpg")
							|| spt.getName().endsWith(".jpeg")
							|| spt.getName().endsWith(".bmp")
							|| spt.getName().endsWith(".svg")
							|| spt.getName().endsWith(".gif")
							|| spt.getName().endsWith(".mp4")
							|| spt.getName().endsWith(".avi")
							|| spt.getName().endsWith(".flv")
							|| spt.getName().endsWith(".3gp")
							|| spt.getName().endsWith(".mpeg")
							|| spt.getName().endsWith(".mkv")
							|| spt.getName().endsWith(".asf")
							|| spt.getName().endsWith(".wmv")
							|| spt.getName().endsWith(".mov")
							|| spt.getName().endsWith(".mp3")
							|| spt.getName().endsWith(".mp2")
							|| spt.getName().endsWith(".wav")
							|| spt.getName().endsWith(".aac")
							|| spt.getName().endsWith(".ac3")
							|| spt.getName().endsWith(".doc")
							|| spt.getName().endsWith(".apk")
							|| spt.getName().endsWith(".txt")
							|| spt.getName().endsWith(".zip")
							|| spt.getName().endsWith(".pdf")) {

						toastsettext("Selete Folder");

					} else if (spt.isDirectory()) {

						Log.d("file_click_entry", dot.get(position).sdcardPath);
						File file[] = spt.listFiles();

						if (file != null) {
							Log.d("Files", "Size: " + file.length);
							if (file.length >= 1) {
								initImageLoader();
								init(spt);
								Log.d("root-directory", spt.toString());
								Log.d("root-directory",
										String.valueOf(sde_list.size()));
								sde_list.add(spt.toString());
								if (file.length == 0) {
									toastsettext("This folder is empty");
								}
							} else {
								initImageLoader();
								init(spt);
								Log.d("root-directory", spt.toString());
								Log.d("root-directory",
										String.valueOf(sde_list.size()));
								sde_list.add(spt.toString());
								Log.d("root-directory",
										String.valueOf(sde_list.size()));
							}
						}
					} else {
						toastsettext("Selete Folder");
					}
				}
			}
		}
	};

	private void initImageLoader() {

		btn_export = (Button) findViewById(R.id.actexpt_btn_exprt);
		btn_cancel = (Button) findViewById(R.id.actexpt_btn_cancel);

		btn_back = (Button) findViewById(R.id.export_list_back);
		sde_gridGallery = (GridView) findViewById(R.id.export_list_act);
		sde_imgNoMedia = (ImageView) findViewById(R.id.export_imgNoMedia_list);

		btn_export.setOnClickListener(this);

		btn_cancel.setOnClickListener(this);
		btn_back.setOnClickListener(fit);
		sde_gridGallery.setFastScrollEnabled(true);
		sde_gridGallery.setOnItemClickListener(sdItemMulClickListener);
		sde_gridGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("{export", "export");

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		all_path = null;
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).memoryCache(
				new WeakMemoryCache());

		ImageLoaderConfiguration config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private OnClickListener fit = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (sde_list != null && sde_list.size() > 0) {
				if (sde_list.size() == 1) {
					finish();
				} else if (sde_list.size() > 1) {
					int dex = sde_list.size() - 2;
					Log.d("dex", String.valueOf(sde_list.size()));
					String sd = String.valueOf(sde_list.get(dex));
					Log.d("dex", String.valueOf(sde_list.get(dex) + dex));
					File root = new File(sd);
					initImageLoader();
					init(root);
					back_pressed = true;
				}
			} else {
				finish();
			}

		}
	};

	private void init(final File root) {

		handler = new Handler();
		sde_adapter = new SdAdapter(getApplicationContext(), imageLoader);
		sde_adapter.setMultiplePick(true);
		sde_gridGallery.setAdapter(sde_adapter);
		new Thread() {

			@Override
			public void run() {
				Looper.prepare();
				handler.post(new Runnable() {

					@Override
					public void run() {

						sde_adapter.addAll(getGalleryPhotos(root));

						checkImageStatus();
						if (back_pressed) {
							back_pressed = false;
							Log.d("root-directory",
									String.valueOf(sde_list.size()));
							sde_list.remove(sde_list.size() - 1);
							Log.d("root-directory",
									String.valueOf(sde_list.size()));
						}
					}

				});
				Looper.loop();
			};

		}.start();

	}

	private ArrayList<SdAdapters> getGalleryPhotos(File root2) {

		Constant.galleryList.clear();
		try {

			File listFile[] = root2.listFiles();
			if (listFile != null && listFile.length <= 1) {
				SdAdapters item = new SdAdapters();
				for (int i = 0; i < listFile.length + 1; i++) {
					if (i == 0) {
						item.sdcardPath = "Up to Whootin Files";
						Constant.galleryList.add(item);
					} else {
						if (listFile[i - 1].getName().endsWith(".png")
								|| listFile[i - 1].getName().endsWith(".jpg")
								|| listFile[i - 1].getName().endsWith(".jpeg")
								|| listFile[i - 1].getName().endsWith(".bmp")
								|| listFile[i - 1].getName().endsWith(".svg")
								|| listFile[i - 1].getName().endsWith(".gif"))

						{
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("image", item.sdcardPath.toString());

						} else if (listFile[i - 1].getName().endsWith(".mp4")
								|| listFile[i - 1].getName().endsWith(".avi")
								|| listFile[i - 1].getName().endsWith(".flv")
								|| listFile[i - 1].getName().endsWith(".3gp")
								|| listFile[i - 1].getName().endsWith(".mpeg")
								|| listFile[i - 1].getName().endsWith(".mkv")
								|| listFile[i - 1].getName().endsWith(".asf")
								|| listFile[i - 1].getName().endsWith(".wmv")
								|| listFile[i - 1].getName().endsWith(".mov")) {
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("video", item.sdcardPath.toString());
						} else if (listFile[i - 1].getName().endsWith(".mp3")
								|| listFile[i - 1].getName().endsWith(".mp2")
								|| listFile[i - 1].getName().endsWith(".wav")
								|| listFile[i - 1].getName().endsWith(".aac")
								|| listFile[i - 1].getName().endsWith(".ac3")) {
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("audio", item.sdcardPath.toString());

						} else if (listFile[i - 1].getName().endsWith(".doc")
								|| listFile[i - 1].getName().endsWith(".apk")
								|| listFile[i - 1].getName().endsWith(".txt")
								|| listFile[i - 1].getName().endsWith(".zip")
								|| listFile[i - 1].getName().endsWith(".pdf")) {
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("others", item.sdcardPath.toString());

						} else {
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("others-part", item.sdcardPath.toString());

						}
					}
				}

			} else {

				if (listFile != null && listFile.length > 0) {
					for (int i = 0; i < listFile.length + 1; i++) {
						SdAdapters item = new SdAdapters();
						if (i == 0) {
							item.sdcardPath = "Up to Whootin Files";
							Constant.galleryList.add(item);
						} else {
							if (listFile[i - 1].isDirectory()) {

								item.sdcardPath = listFile[i - 1]
										.getAbsolutePath();
								Constant.galleryList.add(item);

							} else {

								if (listFile[i - 1].getName().endsWith(".png")
										|| listFile[i - 1].getName().endsWith(
												".jpg")
										|| listFile[i - 1].getName().endsWith(
												".jpeg")
										|| listFile[i - 1].getName().endsWith(
												".bmp")
										|| listFile[i - 1].getName().endsWith(
												".svg")
										|| listFile[i - 1].getName().endsWith(
												".gif"))

								{
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("image", item.sdcardPath.toString());

								} else if (listFile[i - 1].getName().endsWith(
										".mp4")
										|| listFile[i - 1].getName().endsWith(
												".avi")
										|| listFile[i - 1].getName().endsWith(
												".flv")
										|| listFile[i - 1].getName().endsWith(
												".3gp")
										|| listFile[i - 1].getName().endsWith(
												".mpeg")
										|| listFile[i - 1].getName().endsWith(
												".mkv")
										|| listFile[i - 1].getName().endsWith(
												".asf")
										|| listFile[i - 1].getName().endsWith(
												".wmv")
										|| listFile[i - 1].getName().endsWith(
												".mov")) {
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("video", item.sdcardPath.toString());
								} else if (listFile[i - 1].getName().endsWith(
										".mp3")
										|| listFile[i - 1].getName().endsWith(
												".mp2")
										|| listFile[i - 1].getName().endsWith(
												".wav")
										|| listFile[i - 1].getName().endsWith(
												".aac")
										|| listFile[i - 1].getName().endsWith(
												".ac3")) {
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("audio", item.sdcardPath.toString());

								} else if (listFile[i - 1].getName().endsWith(
										".doc")
										|| listFile[i - 1].getName().endsWith(
												".apk")
										|| listFile[i - 1].getName().endsWith(
												".txt")
										|| listFile[i - 1].getName().endsWith(
												".zip")
										|| listFile[i - 1].getName().endsWith(
												".pdf")) {
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("others", item.sdcardPath.toString());

								} else {
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("others-part",
											item.sdcardPath.toString());

								}
							}
						}
					}
				}

				for (int k = 0; k < Constant.galleryList.size(); k++) {
					Log.d("k=0", Constant.galleryList.get(k).sdcardPath);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.galleryList;
	}

	private void checkImageStatus() {
		if (sde_adapter.isEmpty()) {
			sde_imgNoMedia.setVisibility(View.VISIBLE);
		} else {
			sde_imgNoMedia.setVisibility(View.GONE);
		}

	}

	public class SdAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater infalter;
		private ArrayList<SdAdapters> data = new ArrayList<SdAdapters>();
		ImageLoader imageLoader;
		private boolean isActionMultiplePick;

		public SdAdapter(Context c, ImageLoader imageLoader2) {
			infalter = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mContext = c;
			this.imageLoader = imageLoader2;
			clearCache();
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public SdAdapters getItem(int position) {
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

		public ArrayList<SdAdapters> getSelected() {
			ArrayList<SdAdapters> dataT = new ArrayList<SdAdapters>();

			for (int i = 0; i < data.size(); i++) {
				if (data.get(i).isSeleted) {
					dataT.add(data.get(i));
				}
			}

			return dataT;
		}

		public void addAll(ArrayList<SdAdapters> files) {

			try {
				this.data.clear();
				this.data.addAll(files);

			} catch (Exception e) {
				e.printStackTrace();
			}

			notifyDataSetChanged();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			try {
				if (convertView == null) {

					holder = new ViewHolder();
					infalter = (LayoutInflater) mContext
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = infalter.inflate(R.layout.inflate_sdexport,
							null);

					holder.sdQueue = (ImageView) convertView
							.findViewById(R.id.x_Queue_list);

					holder.sdQueueTextView = (TextView) convertView
							.findViewById(R.id.x_Queue_textview);

					convertView.setTag(holder);

				} else {
					holder = (ViewHolder) convertView.getTag();
				}

				holder.sdQueue.setTag(position);
				holder.sdQueue.setImageResource(R.drawable.ic_list);

				String split = data.get(position).sdcardPath;
				File spt = new File(split);
				holder.sdQueueTextView.setText(spt.getName());

				if (split.contentEquals("Up to Whootin Files")) {
					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.btn_back);
					holder.sdQueue.setImageBitmap(bit);
				} else if (spt.isDirectory()) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.folder_ic);
					holder.sdQueue.setImageBitmap(bit);
					holder.sdQueueTextView.setTextColor(Color.WHITE);
				} else if (split.contains(".mp4") || split.contains(".avi")
						|| split.contains(".mpeg") || split.contains(".flv")
						|| split.contains(".mkv") || split.contains(".3gp")
						|| split.contains(".wmv") || split.contains(".asf")
						|| split.contains(".mp3") || split.contains(".mp2")
						|| split.contains(".aac") || split.contains(".wav")
						|| split.contains(".ac3") || split.contains(".png")
						|| split.contains(".jpes") || split.contains(".jpg")
						|| split.contains(".bmp") || split.contains(".jpeg")
						|| split.contains(" .jpg") || split.contains(" .png")
						|| split.contains(".txt") || split.contains(".doc")
						|| split.contains(".pdf") || split.contains(".zip")) {
					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_dimmy);
					holder.sdQueue.setImageBitmap(bit);
					holder.sdQueueTextView.setTextColor(Color.DKGRAY);

				} else {
					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_dimmy);
					holder.sdQueue.setImageBitmap(bit);
					holder.sdQueueTextView.setTextColor(Color.DKGRAY);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return convertView;
		}

		public class ViewHolder {
			ImageView sdQueue;
			TextView sdQueueTextView;
			// LinearLayout xport;
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

	public class ShareProgress extends AsyncTask<String, String, String> {
		String pathe = null;

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (dwn_load) {
				toastsettext("Successfully exported " + url_nm);
				Log.d("success", "successfully exported");
				initImageLoader();
				String path = null;
				path = pathe;
				File pathe = new File(path);
				init(pathe);
				finish();
				setResult(RESULT_OK);
			}
		}

		private void export(String path) throws IOException {

			try {
				Log.d("url", url_url);
				URL url = new URL(url_url);
				URLConnection urlConnection = url.openConnection();
				urlConnection
						.setRequestProperty("Transfer-Encoding", "chunked");
				urlConnection.setUseCaches(false);
				urlConnection.setDoInput(true);
				urlConnection.setDoOutput(true);
				urlConnection.connect();
				// connection.setDoOutput(true);
				int fileLength = urlConnection.getContentLength();
				File save_Image, temp = null;

				save_Image = new File(path);
				if (!save_Image.exists()) {
					Log.d("fileexist",
							"save_image file exitsts to makde" + url.getPath()
									+ save_Image.toString());
					save_Image.mkdir();
				} else {
					Log.d("deleted", "save_image not file exitsts to makde"
							+ url.getPath() + save_Image.toString());
					save_Image.delete();
					save_Image = new File(path);
					save_Image.mkdir();
				}
				temp = new File(save_Image, url_nm);
				if (!temp.exists()) {
					Log.d("new", "new entry" + temp.toString());
					save_Image = temp;
					OutputStream output = new FileOutputStream(save_Image);
					InputStream input = new BufferedInputStream(
							url.openStream());
					byte data[] = new byte[1024];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
						total += count;
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
					dwn_load = true;
					Log.d("success", "successfully exported");
					initImageLoader();
					File pathe = new File(path);
					init(pathe);

				} else {
					temp.delete();
					temp = new File(save_Image, url_nm);
					save_Image = temp;
					Log.e("deleted_temp", save_Image.getAbsolutePath());
					OutputStream output = new FileOutputStream(save_Image);
					InputStream input = new BufferedInputStream(
							url.openStream());

					byte data[] = new byte[1024];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1) {
						total += count;
						output.write(data, 0, count);
					}

					output.flush();
					output.close();
					input.close();
				}
				dwn_load = true;

			} catch (Exception e) {
				Log.e("error", String.valueOf(e));
				prg_bar.setCancelable(true);
				prg_bar.dismiss();
				prg_bar.cancel();
			} finally {

			}
			prg_bar.setCancelable(true);
			prg_bar.dismiss();
			prg_bar.cancel();

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prg_bar = new ProgressDialog(Sdexport_Activity.this);
			prg_bar.setMessage("please wait...");
			prg_bar.setIndeterminate(false);
			prg_bar.setCancelable(false);
			prg_bar.setMax(100);
			prg_bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			prg_bar.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String s = params[0];
			pathe = s;
			Log.d("s", s);
			try {
				export(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}


	public void toastsettext(String string1) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_activity,
				(ViewGroup) findViewById(R.id.toast_rl));
		TextView txt = (TextView) layout.findViewById(R.id.toast_txt);
		txt.setText(string1);
		Toast tst = new Toast(getApplicationContext());
		tst.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		tst.setDuration(Toast.LENGTH_SHORT);
		tst.setView(layout);
		tst.show();
	}

	@Override
	public void onBackPressed() {

		if (sde_list != null && sde_list.size() > 0) {
			if (sde_list.size() == 1) {

				int dex = sde_list.size() - 1;
				Log.d("dex", String.valueOf(sde_list.size()));
				String sd = String.valueOf(sde_list.get(dex));
				Log.d("dex", String.valueOf(sde_list.get(dex) + dex));
				File root = new File(sd);
				initImageLoader();
				init(root);
				back_pressed = true;
			} else if (sde_list.size() > 1) {
				int dex = sde_list.size() - 2;
				Log.d("dex", String.valueOf(sde_list.size()));
				String sd = String.valueOf(sde_list.get(dex));
				Log.d("dex", String.valueOf(sde_list.get(dex) + dex));
				File root = new File(sd);
				initImageLoader();
				init(root);
				back_pressed = true;
			}
		} else {
			super.onBackPressed();
			Sdexport_Activity.this.finish();
		}

	}

	@Override
	public void onClick(View v) {
		if (v == btn_export) {
			if (sde_list != null && sde_list.size() > 0) {
				if (sde_list.size() >= 0) {
					int dex = sde_list.size() - 1;
					Log.d("sdd", String.valueOf(sde_list.size()));
					String sd = String.valueOf(sde_list.get(dex));
					Log.d("sdd", String.valueOf(sde_list.get(dex) + dex));
					new ShareProgress().execute(sd.toString());
				}
			} else {
				toastsettext("Try again later");
			}
		} else if (v == btn_cancel) {
			Log.d("finish", "finish");
			finish();
		}

	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		Constant.pass = getSharedPreferences("fun", MODE_WORLD_WRITEABLE);
		String ups = null;
		if (Constant.pass != null && Constant.pass.contains("up")) {
			ups = Constant.pass.getString("up", "");
		}

	}
	

}
