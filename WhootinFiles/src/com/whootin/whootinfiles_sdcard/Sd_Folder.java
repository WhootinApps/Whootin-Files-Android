package com.whootin.whootinfiles_sdcard;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemSelectedListener;

import com.devsmart.android.ui.HorizontalListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.whootin.wf.Folder_LoginActivity;
import com.whootin.wf.R;
import com.whootin.whootinfiles_db.Constant;
import com.whootin.whootinfiles_db.Files_Db;
import com.whootin.whootinfiles_gallery.Action;
import com.whootin.whootinfiles_sdcard.Subfolder_Activity;
import com.whootin.whootinfiles_sdcard.Subfolder_Activity.download;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "InlinedApi", "NewApi" })
public class Sd_Folder extends Activity implements OnClickListener {
	private HorizontalListView lv_hsd;
	private String stng_stay = null, stng_enter = null;
	GridView sd_gridGallery;
	Boolean back_pressed = false;
	private ArrayList<String> sd_list = null;

	View.OnClickListener mOkClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			all_path = null;
			all_path = new ArrayList<String>();
			ArrayList<SdAdapters> selected = null;
			selected = sd_adapter.getSelected();
			for (int i = 0; i < selected.size(); i++) {
				all_path.add(selected.get(i).sdcardPath.toString());
				Log.d("all_path",
						String.valueOf(selected.get(i).sdcardPath.toString()));
			}
			if (new_fold) {
				new_fold = false;
				if (all_path.size() >= 1) {
					dialog();
				} else {
					if (prg_bar.isShowing()) {
						prg_bar.setCancelable(true);
						prg_bar.cancel();
						prg_bar.dismiss();
					}
					toastsettext("Select anyone to upload Whootin Files");
				}
			} else {
				re_path = null;
				dlt_path = null;
				fresh_path = null;
				re_path = new ArrayList<String>();
				dlt_path = new ArrayList<String>();
				fresh_path = new ArrayList<String>();

				ArrayList<HashMap<String, String>> contact = Constant.db
						.getAllTranslates(Files_Db.Table, bool);

				if (all_path != null && all_path.size() > 0) {
					for (int i = 0; i < all_path.size(); i++) {
						String removal = all_path.get(i).toString();
						File remove = new File(removal);
						String red = remove.getName();
						for (int j = 0; j < contact.size(); j++) {
							String fld = contact.get(j).get(Files_Db.File_name);
							String flid = contact.get(j).get(Files_Db.File_id);
							if (red.contentEquals(fld)) {
								Log.d("s-2", red);
								re_path.add(red);
								dlt_path.add(flid);
								fresh_path.add(removal);
								Log.d("re_path", removal + red);
							}
						}
					}

					if (re_path.size() == 0) {
						dialog();
					} else {
						final TextView alert1;
						final Button yes_btn, no_btn;
						AlertDialog.Builder build_dialog;
						final AlertDialog alert_dialog;
						final ListView lv;

						build_dialog = new AlertDialog.Builder(context);
						LayoutInflater inflater = (LayoutInflater) context
								.getSystemService(LAYOUT_INFLATER_SERVICE);
						View layout = inflater.inflate(
								R.layout.altdialog_exited,
								(ViewGroup) findViewById(R.id.exed_sample));
						no_btn = (Button) layout
								.findViewById(R.id.exed_btn_cancel);
						yes_btn = (Button) layout
								.findViewById(R.id.exed_btn_ok);
						alert1 = (TextView) layout
								.findViewById(R.id.exed_txt_hd);
						lv = (ListView) layout.findViewById(R.id.exed_lv);
						build_dialog.setView(layout);
						alert_dialog = build_dialog.create();
						alert_dialog.setView(layout, 0, 0, 0, 0);

						if (re_path.size() == 1) {
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									context,
									android.R.layout.simple_list_item_1,
									re_path);
							lv.setAdapter(adapter);
						} else if (re_path.size() >= 2) {
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									context,
									android.R.layout.simple_list_item_1,
									re_path);
							lv.setAdapter(adapter);
						}

						yes_btn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								dlt = true;
								alert_dialog.dismiss();
								if (prg_bar.isShowing()) {

								} else {
									prg_bar.show();
								}
								d_delete();
							}

						});

						no_btn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								for (int x = 0; x < fresh_path.size(); x++) {
									String get = fresh_path.get(x);
									all_path.remove(get);
								}
								Log.d("get", String.valueOf(all_path.size()));
								if (all_path.size() >= 1) {
									dialog();
								} else {
									toastsettext("Select anyone to upload Whootin Files");
								}
								alert_dialog.dismiss();
							}
						});

						alert1.setMovementMethod(new ScrollingMovementMethod());
						alert_dialog.show();
						alert1.setOnTouchListener(new OnTouchListener() {

							@Override
							public boolean onTouch(View v, MotionEvent event) {
								if (v.getId() == R.id.exed_txt_hd) {
									v.getParent()
											.requestDisallowInterceptTouchEvent(
													true);
									switch (event.getAction()
											& MotionEvent.ACTION_MASK) {
									case MotionEvent.ACTION_UP:
										v.getParent()
												.requestDisallowInterceptTouchEvent(
														false);
										break;
									}
								}
								return false;
							}
						});
					}
				} else {
					toastsettext("Select anyone to upload Whootin Files");
				}
			}

		}
	};

	private ArrayList<SdAdapters> getGalleryPhotos(File root2) {

		Constant.galleryList.clear();
		try {
			mAdapter.notifyDataSetChanged();
			lv_hsd.setAdapter(mAdapter);
			File listFile[] = root2.listFiles();
			if (listFile != null && listFile.length <= 1) {
				SdAdapters item = new SdAdapters();
				for (int i = 0; i < listFile.length + 1; i++) {
					if (i == 0) {
						item.sdcardPath = "Up to Whootin Files";
						Constant.galleryList.add(item);
					} else {
						if (listFile[i - 1].getName().endsWith(".png")
								|| listFile[i - 1].getName().endsWith(".PNG")
								|| listFile[i - 1].getName().endsWith(".jpg")
								|| listFile[i - 1].getName().endsWith(".JPG")
								|| listFile[i - 1].getName().endsWith(".jpeg")
								|| listFile[i - 1].getName().endsWith(".JPEG")
								|| listFile[i - 1].getName().endsWith(".bmp")
								|| listFile[i - 1].getName().endsWith(".BMP")
								|| listFile[i - 1].getName().endsWith(".svg")
								|| listFile[i - 1].getName().endsWith(".SVG")
								|| listFile[i - 1].getName().endsWith(".gif")
								|| listFile[i - 1].getName().endsWith(".GIF"))

						{
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("image", item.sdcardPath.toString());

						} else if (listFile[i - 1].getName().endsWith(".mp4")
								|| listFile[i - 1].getName().endsWith(".MP4")
								|| listFile[i - 1].getName().endsWith(".avi")
								|| listFile[i - 1].getName().endsWith(".AVI")
								|| listFile[i - 1].getName().endsWith(".flv")
								|| listFile[i - 1].getName().endsWith(".FLV")
								|| listFile[i - 1].getName().endsWith(".3gp")
								|| listFile[i - 1].getName().endsWith(".3GP")
								|| listFile[i - 1].getName().endsWith(".mpeg")
								|| listFile[i - 1].getName().endsWith(".MPEG")
								|| listFile[i - 1].getName().endsWith(".mkv")
								|| listFile[i - 1].getName().endsWith(".MKV")
								|| listFile[i - 1].getName().endsWith(".asf")
								|| listFile[i - 1].getName().endsWith(".ASF")
								|| listFile[i - 1].getName().endsWith(".wmv")
								|| listFile[i - 1].getName().endsWith(".WMV")
								|| listFile[i - 1].getName().endsWith(".mov")
								|| listFile[i - 1].getName().endsWith(".MOV")) {
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("video", item.sdcardPath.toString());
						} else if (listFile[i - 1].getName().endsWith(".mp3")
								|| listFile[i - 1].getName().endsWith(".MP3")
								|| listFile[i - 1].getName().endsWith(".mp2")
								|| listFile[i - 1].getName().endsWith(".MP2")
								|| listFile[i - 1].getName().endsWith(".wav")
								|| listFile[i - 1].getName().endsWith(".WAV")
								|| listFile[i - 1].getName().endsWith(".aac")
								|| listFile[i - 1].getName().endsWith(".AAC")
								|| listFile[i - 1].getName().endsWith(".ac3")
								|| listFile[i - 1].getName().endsWith(".AC3")) {
							item = new SdAdapters();
							item.sdcardPath = listFile[i - 1].getAbsolutePath();
							Constant.galleryList.add(item);
							Log.d("audio", item.sdcardPath.toString());

						} else if (listFile[i - 1].getName().endsWith(".doc")
								|| listFile[i - 1].getName().endsWith(".DOC")
								|| listFile[i - 1].getName().endsWith(".apk")
								|| listFile[i - 1].getName().endsWith(".APK")
								|| listFile[i - 1].getName().endsWith(".txt")
								|| listFile[i - 1].getName().endsWith(".TXT")
								|| listFile[i - 1].getName().endsWith(".zip")
								|| listFile[i - 1].getName().endsWith(".ZIP")
								|| listFile[i - 1].getName().endsWith(".pdf")
								|| listFile[i - 1].getName().endsWith(".PDF")) {
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
												".PNG")
										|| listFile[i - 1].getName().endsWith(
												".jpg")
										|| listFile[i - 1].getName().endsWith(
												".JPG")
										|| listFile[i - 1].getName().endsWith(
												".jpeg")
										|| listFile[i - 1].getName().endsWith(
												".JPEG")
										|| listFile[i - 1].getName().endsWith(
												".bmp")
										|| listFile[i - 1].getName().endsWith(
												".BMP")
										|| listFile[i - 1].getName().endsWith(
												".svg")
										|| listFile[i - 1].getName().endsWith(
												".SVG")
										|| listFile[i - 1].getName().endsWith(
												".gif")
										|| listFile[i - 1].getName().endsWith(
												".GIF"))

								{
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("image", item.sdcardPath.toString());

								} else if (listFile[i - 1].getName().endsWith(
										".mp4")
										|| listFile[i - 1].getName().endsWith(
												".MP4")
										|| listFile[i - 1].getName().endsWith(
												".avi")
										|| listFile[i - 1].getName().endsWith(
												".AVI")
										|| listFile[i - 1].getName().endsWith(
												".flv")
										|| listFile[i - 1].getName().endsWith(
												".FLV")
										|| listFile[i - 1].getName().endsWith(
												".3gp")
										|| listFile[i - 1].getName().endsWith(
												".3GP")
										|| listFile[i - 1].getName().endsWith(
												".mpeg")
										|| listFile[i - 1].getName().endsWith(
												".MPEG")
										|| listFile[i - 1].getName().endsWith(
												".mkv")
										|| listFile[i - 1].getName().endsWith(
												".MKV")
										|| listFile[i - 1].getName().endsWith(
												".asf")
										|| listFile[i - 1].getName().endsWith(
												".ASF")
										|| listFile[i - 1].getName().endsWith(
												".wmv")
										|| listFile[i - 1].getName().endsWith(
												".WMV")
										|| listFile[i - 1].getName().endsWith(
												".mov")
										|| listFile[i - 1].getName().endsWith(
												".MOV")) {
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("video", item.sdcardPath.toString());
								} else if (listFile[i - 1].getName().endsWith(
										".mp3")
										|| listFile[i - 1].getName().endsWith(
												".MP3")
										|| listFile[i - 1].getName().endsWith(
												".mp2")
										|| listFile[i - 1].getName().endsWith(
												".MP2")
										|| listFile[i - 1].getName().endsWith(
												".wav")
										|| listFile[i - 1].getName().endsWith(
												".WAV")
										|| listFile[i - 1].getName().endsWith(
												".aac")
										|| listFile[i - 1].getName().endsWith(
												".AAC")
										|| listFile[i - 1].getName().endsWith(
												".ac3")
										|| listFile[i - 1].getName().endsWith(
												".AC3")) {
									item = new SdAdapters();
									item.sdcardPath = listFile[i - 1]
											.getAbsolutePath();
									Constant.galleryList.add(item);
									Log.d("audio", item.sdcardPath.toString());

								} else if (listFile[i - 1].getName().endsWith(
										".doc")
										|| listFile[i - 1].getName().endsWith(
												".DOC")
										|| listFile[i - 1].getName().endsWith(
												".apk")
										|| listFile[i - 1].getName().endsWith(
												".APK")
										|| listFile[i - 1].getName().endsWith(
												".txt")
										|| listFile[i - 1].getName().endsWith(
												".TXT")
										|| listFile[i - 1].getName().endsWith(
												".zip")
										|| listFile[i - 1].getName().endsWith(
												".ZIP")
										|| listFile[i - 1].getName().endsWith(
												".pdf")
										|| listFile[i - 1].getName().endsWith(
												".PDF")) {
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
			}

			for (int k = 0; k < Constant.galleryList.size(); k++) {
				Log.d("k=0", Constant.galleryList.get(k).sdcardPath);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.galleryList;
	}

	private void checkImageStatus() {
		if (sd_adapter.isEmpty()) {
			imgNoMedia_list.setVisibility(View.VISIBLE);
		} else {
			imgNoMedia_list.setVisibility(View.GONE);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		if (requestCode == 000) {
			Log.d("inapp", "inapp");
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK);
				finish();
			}
		}

		if (requestCode == 111) {
			Log.d("inapp", "inapp");
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK);
				finish();
			}
		}

		if (requestCode == 1234) {
			Log.d("inapp", "inapp");
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK);
				finish();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void findViewById() {
		RelativeLayout rl_hsd = (RelativeLayout) findViewById(R.id.sd_hrl);
		rl_hsd.setVisibility(View.VISIBLE);
		lv_hsd = (HorizontalListView) findViewById(R.id.sd_hlistview);
		
		sd_gridGallery = (GridView) findViewById(R.id.gridGallery_list);
		sd_gridGallery.setFastScrollEnabled(true);
		sd_gridGallery.setOnItemClickListener(sdItemMulClickListener);
		butter = (Button) findViewById(R.id.gal_list_back);

		RelativeLayout rl = (RelativeLayout) findViewById(R.id.gallery_navi);
		rl.setBackgroundResource(R.drawable.nav_bar_sdcard);
		free_click = (Button) findViewById(R.id.btn_fldrgal);

		btnGalleryOk_list = (Button) findViewById(R.id.btnGalleryOk_list);

		imgNoMedia_list = (ImageView) findViewById(R.id.imgNoMedia_list);
		findViewById(R.id.llBottomContainer_list).setVisibility(View.VISIBLE);
		txt_nm_gall = (TextView) findViewById(R.id.gal_nmtxt);
		two_gall = (LinearLayout) findViewById(R.id.gal_text);
		two_gall.setVisibility(View.GONE);
		butt = (Button) findViewById(R.id.gal_list);
		butt.setVisibility(View.GONE);
	}

	AdapterView.OnItemClickListener sdItemMulClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> l, View v, int position, long id) {
			File spt = null;
			ArrayList<SdAdapters> dot = null;

			if (position == 0) {
				if (sd_list != null && sd_list.size() > 1) {
					if (sd_list.size() == 1) {
						int dex = sd_list.size() - 1;
						Log.d("dex", String.valueOf(sd_list.size()));
						String sd = String.valueOf(sd_list.get(dex));
						Log.d("dex", String.valueOf(sd_list.get(dex) + dex));
						File root = new File(sd);
						initImageLoader();
						init(root);
						back_pressed = true;
					} else if (sd_list.size() > 1) {
						int dex = sd_list.size() - 2;
						Log.d("dex", String.valueOf(sd_list.size()));
						String sd = String.valueOf(sd_list.get(dex));
						Log.d("dex", String.valueOf(sd_list.get(dex) + dex));
						File root = new File(sd);
						initImageLoader();
						init(root);
						back_pressed = true;
					}
				} else {
					finish();
				}
			} else {

				if (sd_list != null) {
					if (sd_list.size() == 0) {
						root = new File(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath());
						dot = getGalleryPhotos(root);
						Log.d("on_click_position", String.valueOf(position));
						Log.d("on_click_event", dot.get(position).sdcardPath);
						String split = dot.get(position).sdcardPath;
						spt = new File(split);
						
					} else if (sd_list.size() > 0) {
						Log.d("sd_list_size", String.valueOf(sd_list.size()));
						String sd_get = sd_list.get(sd_list.size() - 1);
						Log.d("sd_lsit", sd_get);
						File update_path = new File(sd_get);
						dot = getGalleryPhotos(update_path);
						String split = dot.get(position).sdcardPath;
						spt = new File(split);
						Log.d("sd_list_position", String.valueOf(position));
					}
				}

				if (spt != null) {
					if (spt.getName().endsWith(".zip")
							|| spt.getName().endsWith(".pdf")
							|| spt.getName().endsWith(".ZIP")
							|| spt.getName().endsWith(".PDF")
							|| spt.getName().endsWith(".txt")
							|| spt.getName().endsWith(".doc")
							|| spt.getName().endsWith(".TXT")
							|| spt.getName().endsWith(".DOC")
							|| spt.getName().endsWith(".mp4")
							|| spt.getName().endsWith(".avi")
							|| spt.getName().endsWith(".MP4")
							|| spt.getName().endsWith(".AVI")
							|| spt.getName().endsWith(".mkv")
							|| spt.getName().endsWith(".flv")
							|| spt.getName().endsWith(".MKV")
							|| spt.getName().endsWith(".FLV")
							|| spt.getName().endsWith(".mpeg")
							|| spt.getName().endsWith(".3gp")
							|| spt.getName().endsWith(".MPEG")
							|| spt.getName().endsWith(".3GP")
							|| spt.getName().endsWith(".mov")
							|| spt.getName().endsWith(".wmv")
							|| spt.getName().endsWith(".MOV")
							|| spt.getName().endsWith(".WMV")
							|| spt.getName().endsWith(".asf")
							|| spt.getName().endsWith(".png")
							|| spt.getName().endsWith(".ASF")
							|| spt.getName().endsWith(".PNG")
							|| spt.getName().endsWith(".jpg")
							|| spt.getName().endsWith(".bmp")
							|| spt.getName().endsWith(".JPG")
							|| spt.getName().endsWith(".BMP")
							|| spt.getName().endsWith(".jpeg")
							|| spt.getName().endsWith(".svg")
							|| spt.getName().endsWith(".JPEG")
							|| spt.getName().endsWith(".SVG")
							|| spt.getName().endsWith(".mp3")
							|| spt.getName().endsWith(".aac")
							|| spt.getName().endsWith(".MP3")
							|| spt.getName().endsWith(".AAC")
							|| spt.getName().endsWith(".mp2")
							|| spt.getName().endsWith(".ac3")
							|| spt.getName().endsWith(".MP2")
							|| spt.getName().endsWith(".AC3")
							|| spt.getName().endsWith(".wav")
							|| spt.getName().endsWith(".pot")
							|| spt.getName().endsWith(".WAV")
							|| spt.getName().endsWith(".POT")
							|| spt.getName().endsWith(".pptm")
							|| spt.getName().endsWith(".potx")
							|| spt.getName().endsWith(".PPTM")
							|| spt.getName().endsWith(".POTX")
							|| spt.getName().endsWith(".pps")
							|| spt.getName().endsWith(".ppsm")
							|| spt.getName().endsWith(".PPS")
							|| spt.getName().endsWith(".PPSM")
							|| spt.getName().endsWith(".ppsx")
							|| spt.getName().endsWith(".ppt")
							|| spt.getName().endsWith(".PPSX")
							|| spt.getName().endsWith(".PPT")
							|| spt.getName().endsWith(".pptx")
							|| spt.getName().endsWith(".xlsm")
							|| spt.getName().endsWith(".PPTX")
							|| spt.getName().endsWith(".XLSM")
							|| spt.getName().endsWith(".xlsb")
							|| spt.getName().endsWith(".xlsx")
							|| spt.getName().endsWith(".XLSB")
							|| spt.getName().endsWith(".XLSX")
							|| spt.getName().endsWith(".xlam")
							|| spt.getName().endsWith(".xls")
							|| spt.getName().endsWith(".XLAM")
							|| spt.getName().endsWith(".XLS")
							|| spt.getName().endsWith(".xltm")
							|| spt.getName().endsWith(".XLTM")) {

						sd_adapter.changeSelection(v, position);

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
										String.valueOf(sd_list.size()));
								sd_list.add(spt.toString());
							} else {
								initImageLoader();
								init(spt);
								Log.d("root-directory", spt.toString());
								Log.d("root-directory",
										String.valueOf(sd_list.size()));
								sd_list.add(spt.toString());
								Log.d("root-directory",
										String.valueOf(sd_list.size()));
							}
						}
					} else {
						sd_adapter.changeSelection(v, position);
					}
				}
			}
		}
	};

	private void initImageLoader() {
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

	private void init(final File root) {

		handler = new Handler();
		sd_adapter = new SdAdapter(getApplicationContext(), imageLoader);
		sd_adapter.setMultiplePick(true);
		sd_gridGallery.setAdapter(sd_adapter);
		new Thread() {

			@Override
			public void run() {
				Looper.prepare();
				handler.post(new Runnable() {

					@Override
					public void run() {

						sd_adapter.addAll(getGalleryPhotos(root));

						checkImageStatus();
						if (back_pressed) {
							back_pressed = false;
							Log.d("root-directory",
									String.valueOf(sd_list.size()));
							sd_list.remove(sd_list.size() - 1);
							Log.d("root-directory",
									String.valueOf(sd_list.size()));
						}
					}

				});
				Looper.loop();
			};

		}.start();

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

		public void changeSelection(View v, int position) {

			if (data.get(position).isSeleted) {
				data.get(position).isSeleted = false;
			} else {
				data.get(position).isSeleted = true;
			}

			((ViewHolder) v.getTag()).sdQueueMultiSelected.setSelected(data
					.get(position).isSeleted);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			try {
				if (convertView == null) {

					holder = new ViewHolder();
					holder = new ViewHolder();
					infalter = (LayoutInflater) mContext
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = infalter.inflate(R.layout.gallery_item_list,
							null);

					holder.sdQueue = (ImageView) convertView
							.findViewById(R.id.imgQueue_list);

					holder.sdQueueMultiSelected = (ImageView) convertView
							.findViewById(R.id.imgQueueMultiSelected_list);

					holder.sdQueueTextView = (TextView) convertView
							.findViewById(R.id.imgQueue_textview);

					if (isActionMultiplePick) {
						holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					} else {
						holder.sdQueueMultiSelected.setVisibility(View.GONE);
					}

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
					holder.sdQueueMultiSelected.setVisibility(View.GONE);
					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.btn_back);
					holder.sdQueue.setImageBitmap(bit);
				} else if (spt.isDirectory()) {

					holder.sdQueueMultiSelected.setVisibility(View.GONE);
					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.folder_ic);
					holder.sdQueue.setImageBitmap(bit);

				} else if (split.endsWith(".zip") || split.endsWith(".ZIP")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_zip);

				} else if (split.endsWith(".pdf") || split.endsWith(".PDF")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_pdf);

				} else if (split.endsWith(".doc") || split.endsWith(".DOC")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_doc);

				} else if (split.endsWith(".ice") || split.endsWith(".ICE")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_conf);

				} else if (split.endsWith(".wmf") || split.endsWith(".WMF")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_wmf);

				} else if (split.endsWith(".ivr") || split.endsWith(".IVR")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_ivr);

				} else if (split.endsWith(".pvu") || split.endsWith(".PVU")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_pvu);

				} else if (split.endsWith(".xyz") || split.endsWith(".XYZ")
						|| split.endsWith(".pdb") || split.endsWith(".PDB")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_chemical);

				} else if (split.endsWith(".xgz") || split.endsWith(".XGZ")
						|| split.endsWith(".xmz") || split.endsWith(".XMZ")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_xgl);

				} else if (split.endsWith(".ustar") || split.endsWith(".USTAR")
						|| split.endsWith(".gzip") || split.endsWith(".GZIP")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_mht);

				} else if (split.endsWith(".mime") || split.endsWith(".MIME")
						|| split.endsWith(".mht") || split.endsWith(".MTH")
						|| split.endsWith(".mhtml") || split.endsWith(".MHTML")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_model);

				} else if (split.endsWith(".3dm") || split.endsWith(".3DM")
						|| split.endsWith(".3dmf") || split.endsWith(".3DMF")
						|| split.endsWith(".qd3") || split.endsWith(".QD3")
						|| split.endsWith(".qd3d") || split.endsWith(".QD3D")
						|| split.endsWith(".3svr") || split.endsWith(".3SVR")
						|| split.endsWith(".vrt") || split.endsWith(".VRT")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_xworld);

				} else if (split.endsWith(".vrml") || split.endsWith(".IVR")
						|| split.endsWith(".pov") || split.endsWith(".POV")
						|| split.endsWith(".iges") || split.endsWith(".IGES")
						|| split.endsWith(".igs") || split.endsWith(".IGS")
						|| split.endsWith(".wrl") || split.endsWith(".WRL")
						|| split.endsWith(".wrz") || split.endsWith(".WRZ")
						|| split.endsWith(".dwf") || split.endsWith(".DWF")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_model);

				} else if (split.endsWith(".html") || split.endsWith(".HTML")
						|| split.endsWith(".shtml") || split.endsWith(".SHTML")
						|| split.endsWith(".acgi") || split.endsWith(".ACGI")
						|| split.endsWith(".htm") || split.endsWith(".HTM")
						|| split.endsWith(".htmls") || split.endsWith(".HTMLS")
						|| split.endsWith(".htt") || split.endsWith(".HTT")
						|| split.endsWith(".htx") || split.endsWith(".HTX")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_html);

				} else if (split.endsWith(".ppt") || split.endsWith(".PPT")
						|| split.endsWith(".pptx") || split.endsWith(".PPTX")
						|| split.endsWith(".pptm") || split.endsWith(".PPTM")
						|| split.endsWith(".pot") || split.endsWith(".POT")
						|| split.endsWith(".potx") || split.endsWith(".POTX")
						|| split.endsWith(".pps") || split.endsWith(".PPS")
						|| split.endsWith(".ppsx") || split.endsWith(".PPSX")
						|| split.endsWith(".ppsm") || split.endsWith(".PPSM")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_ppt);

				} else if (split.endsWith(".xls") || split.endsWith(".XLS")
						|| split.endsWith(".xlsx") || split.endsWith(".XLSX")
						|| split.endsWith(".xlam") || split.endsWith(".XLAM")
						|| split.endsWith(".xltm") || split.endsWith(".XLTM")
						|| split.endsWith(".xlsm") || split.endsWith(".XLSM")
						|| split.endsWith(".xlsb") || split.endsWith(".XLSB")
						|| split.endsWith(".xl") || split.endsWith(".XL")
						|| split.endsWith(".xla") || split.endsWith(".XLA")
						|| split.endsWith(".xlb") || split.endsWith(".XLB")
						|| split.endsWith(".xlc") || split.endsWith(".XLC")
						|| split.endsWith(".xld") || split.endsWith(".XLD")
						|| split.endsWith(".xlk") || split.endsWith(".XLK")
						|| split.endsWith(".xll") || split.endsWith(".XLL")
						|| split.endsWith(".xlm") || split.endsWith(".XLM")
						|| split.endsWith(".xlt") || split.endsWith(".XLT")
						|| split.endsWith(".xlv") || split.endsWith(".XLV")
						|| split.endsWith(".xlw") || split.endsWith(".XLW")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_xls);

				} else if (split.endsWith(".mp4") || split.endsWith(".MP4")
						|| split.endsWith(".avi") || split.endsWith(".AVI")
						|| split.endsWith(".afl") || split.endsWith(".AFL")
						|| split.endsWith(".3gp") || split.endsWith(".3GP")
						|| split.endsWith(".mkv") || split.endsWith(".MKV")
						|| split.endsWith(".flv") || split.endsWith(".FLV")
						|| split.endsWith(".wmv") || split.endsWith(".WMV")
						|| split.endsWith(".asf") || split.endsWith(".ASF")
						|| split.endsWith(".mpeg") || split.endsWith(".MPEG")
						|| split.endsWith(".dv") || split.endsWith(".DV")
						|| split.endsWith(".dl") || split.endsWith(".DL")
						|| split.endsWith(".mov") || split.endsWith(".MOV")
						|| split.endsWith(".avs") || split.endsWith(".AVS")
						|| split.endsWith(".af") || split.endsWith(".AF")
						|| split.endsWith(".asf") || split.endsWith(".ASF")
						|| split.endsWith(".dif") || split.endsWith(".DIF")
						|| split.endsWith(".fli") || split.endsWith(".FLI")
						|| split.endsWith(".fmf") || split.endsWith(".FMF")
						|| split.endsWith(".asx") || split.endsWith(".ASX")
						|| split.endsWith(".mjpg") || split.endsWith(".MJPG")
						|| split.endsWith(".m2v") || split.endsWith(".M2V")
						|| split.endsWith(".m1v") || split.endsWith(".M1V")
						|| split.endsWith(".scm") || split.endsWith(".SCM")
						|| split.endsWith(".vivo") || split.endsWith(".VIVO")
						|| split.endsWith(".vos") || split.endsWith(".VOS")
						|| split.endsWith(".xsr") || split.endsWith(".XSR")
						|| split.endsWith(".xdr") || split.endsWith(".XDR")
						|| split.endsWith(".gl") || split.endsWith(".GL")
						|| split.endsWith(".isu") || split.endsWith(".ISU")
						|| split.endsWith(".rv") || split.endsWith(".RV")
						|| split.endsWith(".vdo") || split.endsWith(".VDO")
						|| split.endsWith(".viv") || split.endsWith(".VIV")
						|| split.endsWith(".qtc") || split.endsWith(".QTC")
						|| split.endsWith(".qt") || split.endsWith(".QT")
						|| split.endsWith(".mv") || split.endsWith(".MV")
						|| split.endsWith(".moov") || split.endsWith(".MOOV")
						|| split.endsWith(".mov") || split.endsWith(".MOV")
						|| split.endsWith(".movie") || split.endsWith(".MOVIE")
						|| split.endsWith(".mpe") || split.endsWith(".MPE")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					Bitmap bit = BitmapFactory.decodeResource(getResources(),
							R.drawable.ic_video);
					holder.sdQueue.setImageBitmap(bit);

				} else if (split.endsWith(".jpg") || split.endsWith(".JPG")
						|| split.endsWith(".jpeg") || split.endsWith(".JPEG")
						|| split.endsWith(".bmp") || split.endsWith(".BMP")
						|| split.endsWith(".svg") || split.endsWith(".SVG")
						|| split.endsWith(".png") || split.endsWith(".PNG")
						|| split.endsWith(".ras") || split.endsWith(".RAS")
						|| split.endsWith(".rast") || split.endsWith(".RAST")
						|| split.endsWith(".wbmp") || split.endsWith(".WBMP")
						|| split.endsWith(".xwd") || split.endsWith(".XWD")
						|| split.endsWith(".xpm") || split.endsWith(".XPM")
						|| split.endsWith(".x-png") || split.endsWith(".X-PNG")
						|| split.endsWith(".xbm") || split.endsWith(".XBM")
						|| split.endsWith(".rf") || split.endsWith(".RF")
						|| split.endsWith(".rgb") || split.endsWith(".RGB")
						|| split.endsWith(".tif") || split.endsWith(".TIF")
						|| split.endsWith(".tiff") || split.endsWith(".TIFF")
						|| split.endsWith(".svf") || split.endsWith(".SVF")
						|| split.endsWith(".rp") || split.endsWith(".RP")
						|| split.endsWith(".xif") || split.endsWith(".XIF")
						|| split.endsWith(".turbot") || split.endsWith(".TURBOT")
						|| split.endsWith(".pgm") || split.endsWith(".PGM")
						|| split.endsWith(".pic") || split.endsWith(".PIC")
						|| split.endsWith(".pict") || split.endsWith(".PICT")
						|| split.endsWith(".pm") || split.endsWith(".PM")
						|| split.endsWith(".pnm") || split.endsWith(".PNM")
						|| split.endsWith(".nap") || split.endsWith(".NAP")
						|| split.endsWith(".naplps") || split.endsWith(".NAPLPS")
						|| split.endsWith(".nif") || split.endsWith(".NIF")
						|| split.endsWith(".niff") || split.endsWith(".NIFF")
						|| split.endsWith(".pbm") || split.endsWith(".PBM")
						|| split.endsWith(".pct") || split.endsWith(".PCT")
						|| split.endsWith(".pcx") || split.endsWith(".PCX")
						|| split.endsWith(".ico") || split.endsWith(".ICO")
						|| split.endsWith(".ief") || split.endsWith(".IEF")
						|| split.endsWith(".iefs") || split.endsWith(".IEFS")
						|| split.endsWith(".dwg") || split.endsWith(".DWG")
						|| split.endsWith(".dxf") || split.endsWith(".DXF")
						|| split.endsWith(".art") || split.endsWith(".ART")
						|| split.endsWith(".bm") || split.endsWith(".BM")
						|| split.endsWith(".bmp") || split.endsWith(".BMP")
						|| split.endsWith(".fif") || split.endsWith(".FIF")
						|| split.endsWith(".flo") || split.endsWith(".FLO")
						|| split.endsWith(".fpx") || split.endsWith(".FPX")
						|| split.endsWith(".g3") || split.endsWith(".G3")
						|| split.endsWith(".gif") || split.endsWith(".GIF")
						|| split.endsWith(".jfif") || split.endsWith(".JFIF")
						|| split.endsWith(".jfif-tbnl")
						|| split.endsWith(".JFIF-TBNL") || split.endsWith(".jpe")
						|| split.endsWith(".JPE")
						|| split.endsWith(".jps") || split.endsWith(".JPS")
						|| split.endsWith(".jut") || split.endsWith(".JUT")
						|| split.endsWith(".qif") || split.endsWith(".QIF")
						|| split.endsWith(".qti") || split.endsWith(".QTI")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					imageLoader.displayImage("file://"
							+ data.get(position).sdcardPath, holder.sdQueue);


				} else if (split.endsWith(".ab") || split.endsWith(".AB")
						|| split.endsWith(".acgi") || split.endsWith(".ACGI")
						|| split.endsWith(".ksh") || split.endsWith(".KSH")
						|| split.endsWith(".jav") || split.endsWith(".JAV")
						|| split.endsWith(".java") || split.endsWith(".JAVA")
						|| split.endsWith(".lsp") || split.endsWith(".LSP")
						|| split.endsWith(".lsx") || split.endsWith(".LSX")
						|| split.endsWith(".lst") || split.endsWith(".LST")
						|| split.endsWith(".m") || split.endsWith(".M")
						|| split.endsWith(".p") || split.endsWith(".P")
						|| split.endsWith(".pas") || split.endsWith(".PAS")
						|| split.endsWith(".pl") || split.endsWith(".PL")
						|| split.endsWith(".pm") || split.endsWith(".PM")
						|| split.endsWith(".mar") || split.endsWith(".MAR")
						|| split.endsWith(".js") || split.endsWith(".JS")
						|| split.endsWith(".list") || split.endsWith(".LIST")
						|| split.endsWith(".log") || split.endsWith(".LOG")
						|| split.endsWith(".mcf") || split.endsWith(".MCF")
						|| split.endsWith(".rexx") || split.endsWith(".REXX")
						|| split.endsWith(".sdml") || split.endsWith(".SDML")
						|| split.endsWith(".rtx") || split.endsWith(".RTX")
						|| split.endsWith(".scm") || split.endsWith(".SCM")
						|| split.endsWith(".s") || split.endsWith(".S")
						|| split.endsWith(".rt") || split.endsWith(".RT")
						|| split.endsWith(".rtf") || split.endsWith(".RTF")
						|| split.endsWith(".sgm") || split.endsWith(".SGM")
						|| split.endsWith(".sgml") || split.endsWith(".SGML")
						|| split.endsWith(".spc") || split.endsWith(".SPC")
						|| split.endsWith(".ssi") || split.endsWith(".SSI")
						|| split.endsWith(".talk") || split.endsWith(".TALK")
						|| split.endsWith(".tcl") || split.endsWith(".TCL")
						|| split.endsWith(".tcsh") || split.endsWith(".TCSH")
						|| split.endsWith(".text") || split.endsWith(".TEXT")
						|| split.endsWith(".tsv") || split.endsWith(".TSV")
						|| split.endsWith(".txt") || split.endsWith(".TXT")
						|| split.endsWith(".uil") || split.endsWith(".UIL")
						|| split.endsWith(".uni") || split.endsWith(".UNI")
						|| split.endsWith(".unis") || split.endsWith(".UNIS")
						|| split.endsWith(".uri") || split.endsWith(".URI")
						|| split.endsWith(".uris") || split.endsWith(".URIS")
						|| split.endsWith(".uu") || split.endsWith(".UU")
						|| split.endsWith(".uue") || split.endsWith(".UUE")
						|| split.endsWith(".wml") || split.endsWith(".WML")
						|| split.endsWith(".wmls") || split.endsWith(".WMLS")
						|| split.endsWith(".zsh") || split.endsWith(".ZSH")
						|| split.endsWith(".xml") || split.endsWith(".XML")
						|| split.endsWith(".wsc") || split.endsWith(".WMZ")
						|| split.endsWith(".vcs") || split.endsWith(".VCS")
						|| split.endsWith(".sh") || split.endsWith(".SH")
						|| split.endsWith(".py") || split.endsWith(".PY")
						|| split.endsWith(".aip") || split.endsWith(".AIP")
						|| split.endsWith(".asm") || split.endsWith(".ASM")
						|| split.endsWith(".asf") || split.endsWith(".ASP")
						|| split.endsWith(".c") || split.endsWith(".C")
						|| split.endsWith(".c++") || split.endsWith(".C++")
						|| split.endsWith(".cc") || split.endsWith(".CC")
						|| split.endsWith(".com") || split.endsWith(".COM")
						|| split.endsWith(".conf") || split.endsWith(".CONF")
						|| split.endsWith(".cpp") || split.endsWith(".CPP")
						|| split.endsWith(".csh") || split.endsWith(".CSH")
						|| split.endsWith(".cxx") || split.endsWith(".CXX")
						|| split.endsWith(".css") || split.endsWith(".CSS")
						|| split.endsWith(".def") || split.endsWith(".DEF")
						|| split.endsWith(".el") || split.endsWith(".EL")
						|| split.endsWith(".etx") || split.endsWith(".ETX")
						|| split.endsWith(".f") || split.endsWith(".F")
						|| split.endsWith(".f77") || split.endsWith(".F77")
						|| split.endsWith(".f90") || split.endsWith(".F90")
						|| split.endsWith(".hh") || split.endsWith(".HH")
						|| split.endsWith(".hlb") || split.endsWith(".HLB")
						|| split.endsWith(".htc") || split.endsWith(".HTC")
						|| split.endsWith(".g") || split.endsWith(".G")
						|| split.endsWith(".h") || split.endsWith(".H")
						|| split.endsWith(".idc") || split.endsWith(".IDC")
						|| split.endsWith(".flx") || split.endsWith(".FLX")
						|| split.endsWith(".for") || split.endsWith(".FOR")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_txt);

				} else if (split.endsWith(".mp3") || split.endsWith(".MP3")
						|| split.endsWith(".mp2") || split.endsWith(".MP2")
						|| split.endsWith(".wav") || split.endsWith(".WAV")
						|| split.endsWith(".aac") || split.endsWith(".AAC")
						|| split.endsWith(".ac3") || split.endsWith(".AC3")
						|| split.endsWith(".aif") || split.endsWith(".AIF")
						|| split.endsWith(".aifc") || split.endsWith(".AIFC")
						|| split.endsWith(".aiff") || split.endsWith(".AIFF")
						|| split.endsWith(".funk") || split.endsWith(".FUNK")
						|| split.endsWith(".au") || split.endsWith(".AU")
						|| split.endsWith(".gsd") || split.endsWith(".GSD")
						|| split.endsWith(".gsm") || split.endsWith(".GSM")
						|| split.endsWith(".it") || split.endsWith(".IT")
						|| split.endsWith(".jam") || split.endsWith(".JAM")
						|| split.endsWith(".la") || split.endsWith(".LA")
						|| split.endsWith(".lam") || split.endsWith(".LAM")
						|| split.endsWith(".kar") || split.endsWith(".KAR")
						|| split.endsWith(".lma") || split.endsWith(".LMA")
						|| split.endsWith(".m2a") || split.endsWith(".M2A")
						|| split.endsWith(".mid") || split.endsWith(".MID")
						|| split.endsWith(".midi") || split.endsWith(".MIDI")
						|| split.endsWith(".m3u") || split.endsWith(".M3U")
						|| split.endsWith(".mjf") || split.endsWith(".MJF")
						|| split.endsWith(".rm") || split.endsWith(".RM")
						|| split.endsWith(".rmm") || split.endsWith(".RMM")
						|| split.endsWith(".rmp") || split.endsWith(".RMP")
						|| split.endsWith(".rpm") || split.endsWith(".RPM")
						|| split.endsWith(".s3m") || split.endsWith(".S3M")
						|| split.endsWith(".sid") || split.endsWith(".SID")
						|| split.endsWith(".snd") || split.endsWith(".SND")
						|| split.endsWith(".tsi") || split.endsWith(".TSI")
						|| split.endsWith(".voc") || split.endsWith(".VOC")
						|| split.endsWith(".vox") || split.endsWith(".VOX")
						|| split.endsWith(".vqe") || split.endsWith(".VQE")
						|| split.endsWith(".vqf") || split.endsWith(".VQF")
						|| split.endsWith(".vql") || split.endsWith(".VQL")
						|| split.endsWith(".xm") || split.endsWith(".XM")
						|| split.endsWith(".tsp") || split.endsWith(".TSP")
						|| split.endsWith(".mod") || split.endsWith(".MOD")
						|| split.endsWith(".mpa") || split.endsWith(".MPA")
						|| split.endsWith(".my") || split.endsWith(".MY")
						|| split.endsWith(".mpg") || split.endsWith(".MPG")
						|| split.endsWith(".pfunk") || split.endsWith(".PFUNK")
						|| split.endsWith(".mpga") || split.endsWith(".MPGA")
						|| split.endsWith(".ra") || split.endsWith(".RA")
						|| split.endsWith(".qcp") || split.endsWith(".QCP")
						|| split.endsWith(".ram") || split.endsWith(".RAM")) {

					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_audio);

				} else if (split.endsWith(".a") || split.endsWith(".A")
						|| split.endsWith(".aab") || split.endsWith(".AAB")
						|| split.endsWith(".aam") || split.endsWith(".AAM")
						|| split.endsWith(".aas") || split.endsWith(".AAS")
						|| split.endsWith(".ai") || split.endsWith(".AI")
						|| split.endsWith(".aim") || split.endsWith(".AIM")
						|| split.endsWith(".ani") || split.endsWith(".ANI")
						|| split.endsWith(".aos") || split.endsWith(".AOS")
						|| split.endsWith(".aps") || split.endsWith(".APS")
						|| split.endsWith(".arc") || split.endsWith(".ARC")
						|| split.endsWith(".arj") || split.endsWith(".ARJ")
						|| split.endsWith(".bcpio") || split.endsWith(".BCPIO")
						|| split.endsWith(".bin") || split.endsWith(".BIN")
						|| split.endsWith(".boo") || split.endsWith(".BOO")
						|| split.endsWith(".book") || split.endsWith(".BOOK")
						|| split.endsWith(".boz") || split.endsWith(".BOZ")
						|| split.endsWith(".bsh") || split.endsWith(".BSH")
						|| split.endsWith(".bz") || split.endsWith(".BZ")
						|| split.endsWith(".bz2") || split.endsWith(".BZ2")
						|| split.endsWith(".cat") || split.endsWith(".CAT")
						|| split.endsWith(".ccat") || split.endsWith(".CCAD")
						|| split.endsWith(".cco") || split.endsWith(".CCO")
						|| split.endsWith(".cdf") || split.endsWith(".CDF")
						|| split.endsWith(".cer") || split.endsWith(".CER")
						|| split.endsWith(".cha") || split.endsWith(".CHA")
						|| split.endsWith(".class") || split.endsWith(".CLASS")
						|| split.endsWith(".chat") || split.endsWith(".CHAT")
						|| split.endsWith(".der") || split.endsWith(".DER")
						|| split.endsWith(".dir") || split.endsWith(".DIR")
						|| split.endsWith(".cpio") || split.endsWith(".CPIO")
						|| split.endsWith(".dcr") || split.endsWith(".DCR")
						|| split.endsWith(".deepv") || split.endsWith(".DEEPV")
						|| split.endsWith(".cpi") || split.endsWith(".CPT")
						|| split.endsWith(".crl") || split.endsWith(".CRL")
						|| split.endsWith(".crt") || split.endsWith(".CRT")
						|| split.endsWith(".dot") || split.endsWith(".DOT")
						|| split.endsWith(".dp") || split.endsWith(".DP")
						|| split.endsWith(".drw") || split.endsWith(".DRW")
						|| split.endsWith(".dump") || split.endsWith(".DUMP")
						|| split.endsWith(".dvi") || split.endsWith(".DVI")
						|| split.endsWith(".elc") || split.endsWith(".ELC")
						|| split.endsWith(".env") || split.endsWith(".ENV")
						|| split.endsWith(".eps") || split.endsWith(".EPS")
						|| split.endsWith(".es") || split.endsWith(".ES")
						|| split.endsWith(".evy") || split.endsWith(".EVY")
						|| split.endsWith(".exe") || split.endsWith(".EXE")
						|| split.endsWith(".gsp") || split.endsWith(".GSP")
						|| split.endsWith(".gss") || split.endsWith(".GSS")
						|| split.endsWith(".gtar") || split.endsWith(".GTAR")
						|| split.endsWith(".gz") || split.endsWith(".GZ")
						|| split.endsWith(".hlp") || split.endsWith(".HLP")
						|| split.endsWith(".hpgr") || split.endsWith(".HPGR")
						|| split.endsWith(".hpgl") || split.endsWith(".HPGL")
						|| split.endsWith(".hqx") || split.endsWith(".HQX")
						|| split.endsWith(".hta") || split.endsWith(".HTA")
						|| split.endsWith(".mpp") || split.endsWith(".MPP")
						|| split.endsWith(".mpt") || split.endsWith(".MPT")
						|| split.endsWith(".mpv") || split.endsWith(".MPV")
						|| split.endsWith(".mpx") || split.endsWith(".MPX")
						|| split.endsWith(".mrc") || split.endsWith(".MRC")
						|| split.endsWith(".ms") || split.endsWith(".MS")
						|| split.endsWith(".p10") || split.endsWith(".P10")
						|| split.endsWith(".p12") || split.endsWith(".P12")
						|| split.endsWith(".p7a") || split.endsWith(".P7A")
						|| split.endsWith(".p7c") || split.endsWith(".P7C")
						|| split.endsWith(".p7m") || split.endsWith(".P7M")
						|| split.endsWith(".p7r") || split.endsWith(".P7R")
						|| split.endsWith(".p7s") || split.endsWith(".P7S")
						|| split.endsWith(".part") || split.endsWith(".PART")
						|| split.endsWith(".o") || split.endsWith(".O")
						|| split.endsWith(".nvd") || split.endsWith(".NVD")
						|| split.endsWith(".nsc") || split.endsWith(".NSC")
						|| split.endsWith(".nix") || split.endsWith(".NIX")
						|| split.endsWith(".omcr") || split.endsWith(".OMCR")
						|| split.endsWith(".omcd") || split.endsWith(".OMCD")
						|| split.endsWith(".omc") || split.endsWith(".OMC")
						|| split.endsWith(".oda") || split.endsWith(".ODA")
						|| split.endsWith(".w60") || split.endsWith(".W60")
						|| split.endsWith(".vsw") || split.endsWith(".VSW")
						|| split.endsWith(".vst") || split.endsWith(".VST")
						|| split.endsWith(".vsd") || split.endsWith(".VSD")
						|| split.endsWith(".w61") || split.endsWith(".W61")
						|| split.endsWith(".w6w") || split.endsWith(".W6W")
						|| split.endsWith(".wb1") || split.endsWith(".WB1")
						|| split.endsWith(".web") || split.endsWith(".WEB")
						|| split.endsWith(".wiz") || split.endsWith(".WIZ")
						|| split.endsWith(".wk1") || split.endsWith(".WK1")
						|| split.endsWith(".wmlc") || split.endsWith(".WMLC")
						|| split.endsWith(".wsrc") || split.endsWith(".WSRC")
						|| split.endsWith(".wtk") || split.endsWith(".WTK")
						|| split.endsWith(".wmlsc") || split.endsWith(".WMLSC")
						|| split.endsWith(".word") || split.endsWith(".WORD")
						|| split.endsWith(".wp") || split.endsWith(".WP")
						|| split.endsWith(".wp5") || split.endsWith(".WP5")
						|| split.endsWith(".wp6") || split.endsWith(".WP6")
						|| split.endsWith(".wpd") || split.endsWith(".WPD")
						|| split.endsWith(".wql") || split.endsWith(".WQL")
						|| split.endsWith(".wri") || split.endsWith(".WRI")
						|| split.endsWith(".z") || split.endsWith(".Z")
						|| split.endsWith(".zoo") || split.endsWith(".ZOO")
						|| split.endsWith(".spl") || split.endsWith(".SPL")
						|| split.endsWith(".spr") || split.endsWith(".SPR")
						|| split.endsWith(".sprite") || split.endsWith(".SPRITE")
						|| split.endsWith(".src") || split.endsWith(".SRC")
						|| split.endsWith(".ssm") || split.endsWith(".SSM")
						|| split.endsWith(".sst") || split.endsWith(".SST")
						|| split.endsWith(".step") || split.endsWith(".STEP")
						|| split.endsWith(".stl") || split.endsWith(".STL")
						|| split.endsWith(".stp") || split.endsWith(".STP")
						|| split.endsWith(".sv4cpio")
						|| split.endsWith(".SV4CPIO")
						|| split.endsWith(".sv4crc") || split.endsWith(".SV4CRC")
						|| split.endsWith(".svr") || split.endsWith(".SVR")
						|| split.endsWith(".sol") || split.endsWith(".SOL")
						|| split.endsWith(".tar") || split.endsWith(".TAR")
						|| split.endsWith(".tbk") || split.endsWith(".TBK")
						|| split.endsWith(".tcl") || split.endsWith(".TCL")
						|| split.endsWith(".swf") || split.endsWith(".SWF")
						|| split.endsWith(".t") || split.endsWith(".T")
						|| split.endsWith(".tgz") || split.endsWith(".TGZ")
						|| split.endsWith(".tex") || split.endsWith(".TEX")
						|| split.endsWith(".texi") || split.endsWith(".TEXI")
						|| split.endsWith(".texinfo")
						|| split.endsWith(".TEXINFO")
						|| split.endsWith(".tr") || split.endsWith(".TR")
						|| split.endsWith(".vmd") || split.endsWith(".VMD")
						|| split.endsWith(".vmf") || split.endsWith(".VMF")
						|| split.endsWith(".vrml") || split.endsWith(".VRML")
						|| split.endsWith(".vew") || split.endsWith(".VEW")
						|| split.endsWith(".vda") || split.endsWith(".VDA")
						|| split.endsWith(".unv") || split.endsWith(".UNV")
						|| split.endsWith(".vcd") || split.endsWith(".VCD")
						|| split.endsWith(".rtx") || split.endsWith(".RTX")
						|| split.endsWith(".ima") || split.endsWith(".IMA")
						|| split.endsWith(".imap") || split.endsWith(".IMAP")
						|| split.endsWith(".inf") || split.endsWith(".INF")
						|| split.endsWith(".ins") || split.endsWith(".INS")
						|| split.endsWith(".ima") || split.endsWith(".IMA")
						|| split.endsWith(".inf") || split.endsWith(".INF")
						|| split.endsWith(".ins") || split.endsWith(".INS")
						|| split.endsWith(".ip") || split.endsWith(".IP")
						|| split.endsWith(".iv") || split.endsWith(".IV")
						|| split.endsWith(".hdf") || split.endsWith(".HDF")
						|| split.endsWith(".help") || split.endsWith(".HELP")
						|| split.endsWith(".hgl") || split.endsWith(".HGL")
						|| split.endsWith(".frl") || split.endsWith(".FRL")
						|| split.endsWith(".fdf") || split.endsWith(".FDF")
						|| split.endsWith(".pcl") || split.endsWith(".PCL")
						|| split.endsWith(".pm4") || split.endsWith(".PM4")
						|| split.endsWith(".pm5") || split.endsWith(".PM5")
						|| split.endsWith(".plx") || split.endsWith(".PLX")
						|| split.endsWith(".pwz") || split.endsWith(".PWZ")
						|| split.endsWith(".ppa") || split.endsWith(".PPA")
						|| split.endsWith(".pyc") || split.endsWith(".PYC")
						|| split.endsWith(".pkg") || split.endsWith(".PKG")
						|| split.endsWith(".pko") || split.endsWith(".PKO")
						|| split.endsWith(".nc") || split.endsWith(".NC")
						|| split.endsWith(".ncm") || split.endsWith(".NCM")
						|| split.endsWith(".mzz") || split.endsWith(".MZZ")
						|| split.endsWith(".mm") || split.endsWith(".MM")
						|| split.endsWith(".mme") || split.endsWith(".MME")
						|| split.endsWith(".mpc") || split.endsWith(".MPC")
						|| split.endsWith(".mif") || split.endsWith(".MIF")
						|| split.endsWith(".shar") || split.endsWith(".SHAR")
						|| split.endsWith(".sdp") || split.endsWith(".SDP")
						|| split.endsWith(".sdr") || split.endsWith(".SDR")
						|| split.endsWith(".sea") || split.endsWith(".SEA")
						|| split.endsWith(".sit") || split.endsWith(".SIT")
						|| split.endsWith(".smil") || split.endsWith(".SMIL")
						|| split.endsWith(".smi") || split.endsWith(".SMI")
						|| split.endsWith(".sl") || split.endsWith(".SL")
						|| split.endsWith(".skt") || split.endsWith(".SKT")
						|| split.endsWith(".skm") || split.endsWith(".SKM")
						|| split.endsWith(".skp") || split.endsWith(".SKP")
						|| split.endsWith(".skd") || split.endsWith(".SKD")
						|| split.endsWith(".ppz") || split.endsWith(".PPZ")
						|| split.endsWith(".set") || split.endsWith(".SET")
						|| split.endsWith(".pre") || split.endsWith(".PRE")
						|| split.endsWith(".prt") || split.endsWith(".PRT")
						|| split.endsWith(".ps") || split.endsWith(".PS")
						|| split.endsWith(".psd") || split.endsWith(".PSD")
						|| split.endsWith(".jcm") || split.endsWith(".JCM")
						|| split.endsWith(".ivy") || split.endsWith(".IVY")
						|| split.endsWith(".latex") || split.endsWith(".LATEX")
						|| split.endsWith(".lha") || split.endsWith(".LHA")
						|| split.endsWith(".lhx") || split.endsWith(".LHX")
						|| split.endsWith(".man") || split.endsWith(".MAN")
						|| split.endsWith(".map") || split.endsWith(".MAP")
						|| split.endsWith(".ltx") || split.endsWith(".ITX")
						|| split.endsWith(".lzh") || split.endsWith(".LZH")
						|| split.endsWith(".lzx") || split.endsWith(".LZX")
						|| split.endsWith(".mdb") || split.endsWith(".MDB")
						|| split.endsWith(".mc$") || split.endsWith(".MC$")
						|| split.endsWith(".mcd") || split.endsWith(".MCD")
						|| split.endsWith(".mcp") || split.endsWith(".MCP")
						|| split.endsWith(".me") || split.endsWith(".ME")
						|| split.endsWith(".rng") || split.endsWith(".RNG")
						|| split.endsWith(".rnx") || split.endsWith(".RNX")
						|| split.endsWith(".roff") || split.endsWith(".ROFF")
						|| split.endsWith(".saveme") || split.endsWith(".SAVEME")
						|| split.endsWith(".sbk") || split.endsWith(".SBK")) {
					
					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
					holder.sdQueue.setBackgroundResource(R.drawable.ic_application);

				} else {
					holder.sdQueue.setImageBitmap(null);
					holder.sdQueue.setImageResource(0);
					holder.sdQueue.setImageDrawable(null);
					holder.sdQueue.setBackgroundDrawable(null);
					holder.sdQueue.setBackgroundResource(0);
					holder.sdQueueMultiSelected.setVisibility(View.VISIBLE);
				}


				if (isActionMultiplePick) {

					holder.sdQueueMultiSelected
							.setSelected(data.get(position).isSeleted);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return convertView;
		}

		public class ViewHolder {
			ImageView sdQueue;
			ImageView sdQueueMultiSelected;
			TextView sdQueueTextView;
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

	private File root;
	public static SdAdapter sd_adapter;

	private ArrayList<String> new_list = new ArrayList<String>();
	private ArrayList<String> new_name = new ArrayList<String>();
	private ArrayList<String> new_path = null;

	

	private ListView lv_pg;
	private TextView txt_pg, txt_pgmssg, txt_pgsucs;
	private SeekBar seekbar_pg;
	private Dialog d;
	private ListAdapter list_adaptpgm;
	private ArrayList<HashMap<String, String>> re_contactListpg = new ArrayList<HashMap<String, String>>();

	private int h = 0;

	private String dlt_stng = null;
	private boolean bo_pg = false, new_fold = false, dlt = false,
			boo_dlt = false, boo_item = false;

	Handler handler;

	Context context;
	ImageView imgNoMedia_list;
	Button btnGalleryOk_list, free_click;
	String action, common = null, folder_name = null;
	private ImageLoader imageLoader;
	ProgressDialog prg_bar;
	public File imgtemp = null;
	ArrayAdapter<String> mSpinnerAdapter = null;
	ViewPager mViewPager;
	Button butter, butt;

	private TextView txt_nm_gall;
	private LinearLayout two_gall;
	private Boolean _list = false;
	private List<String> all_path = null;
	private List<String> re_path = null;
	private List<String> fresh_path = null;
	public static List<String> dlt_path = null;

	private boolean bool = true;
	boolean vic = false;

	@Override
	public void onBackPressed() {
		if (sd_list != null && sd_list.size() > 0) {
			if (sd_list.size() == 1) {
				finish();
				int dex = sd_list.size() - 1;
				Log.d("dex", String.valueOf(sd_list.size()));
				String sd = String.valueOf(sd_list.get(dex));
				Log.d("dex", String.valueOf(sd_list.get(dex) + dex));
				File root = new File(sd);
				initImageLoader();
				init(root);
				back_pressed = true;
			} else if (sd_list.size() > 1) {
				int dex = sd_list.size() - 2;
				Log.d("dex", String.valueOf(sd_list.size()));
				String sd = String.valueOf(sd_list.get(dex));
				Log.d("dex", String.valueOf(sd_list.get(dex) + dex));
				File root = new File(sd);
				initImageLoader();
				init(root);
				back_pressed = true;
			}
		} else {
			super.onBackPressed();
			setResult(RESULT_OK);
			finish();
		}
		
	}

	@Override
	public void onClick(View v) {
		if (v == butter) {
			if (sd_list != null && sd_list.size() > 0) {
				if (sd_list.size() == 1) {
					finish();
					int dex = sd_list.size() - 1;
					Log.d("dex", String.valueOf(sd_list.size()));
					String sd = String.valueOf(sd_list.get(dex));
					Log.d("dex", String.valueOf(sd_list.get(dex) + dex));
					File root = new File(sd);
					initImageLoader();
					init(root);
					back_pressed = true;
				} else if (sd_list.size() > 1) {
					int dex = sd_list.size() - 2;
					Log.d("dex", String.valueOf(sd_list.size()));
					String sd = String.valueOf(sd_list.get(dex));
					Log.d("dex", String.valueOf(sd_list.get(dex) + dex));
					File root = new File(sd);
					initImageLoader();
					init(root);
					back_pressed = true;
				}
			} else {
				finish();
			}

		} else if (v == free_click) {
			new_path = null;
			new_path = new ArrayList<String>();
			ArrayList<SdAdapters> selected = null;
			selected = sd_adapter.getSelected();
			for (int i = 0; i < selected.size(); i++) {
				new_path.add(selected.get(i).sdcardPath.toString());
				Log.d("new_path",
						String.valueOf(selected.get(i).sdcardPath.toString()));
			}

			setResult(RESULT_OK);
			finish();
			Intent intent = new Intent(context, Subfolder_Activity.class);
			Bundle bun = new Bundle();
			if (stng_enter != null) {
				bun.putString("enter", stng_enter);
			}
			bun.putBoolean("boo_up", false);
			bun.putStringArrayList("array_id", Constant.con_list);
			bun.putStringArrayList("array_nm", Constant.con_name);
			bun.putString("fld_id", Constant.con_fldid);
			bun.putStringArrayList("array", new_path);
			bun.putString("fld_nm", Constant.con_fldnm);
			intent.putExtras(bun);
			startActivityForResult(intent, 000);

		}

	}

	@Override
	public void 
	onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		context = this;
		Bundle bndl = getIntent().getExtras();
		stng_enter = bndl.getString("enter");
		String _name = bndl.getString("fld_nm");
		String _id = bndl.getString("fld_id");
		stng_stay = bndl.getString("new_fold");
		if (_id != null) {
			Constant.con_fldid = _id;
			Log.d("size of new id", _id);
		}
		if (_name != null) {
			Constant.con_fldnm = _name;
			Log.d("size of new name", _name);
		}

		ArrayList<String> new_list = bndl.getStringArrayList("array_id");
		if (new_list != null && new_list.size() > 0) {
			Constant.con_list = new_list;
			Log.d("size of new_list", String.valueOf(Constant.con_list));
		}

		ArrayList<String> new_named = bndl.getStringArrayList("array_nm");
		if (new_named != null && new_named.size() > 0) {
			Constant.con_name = new_named;
			Log.d("size of new_named", String.valueOf(Constant.con_name));
		}

		findViewById();
		declaration();

	}

	private void declaration() {

		txt_nm_gall.setText("Up to Whootin Files");
		butter.setOnClickListener(this);
		free_click.setOnClickListener(this);
		butt.setOnClickListener(this);

		prg_bar = new ProgressDialog(context);
		prg_bar.setMessage("please wait...");
		prg_bar.setIndeterminate(false);
		prg_bar.setCancelable(false);
		prg_bar.setMax(100);
		prg_bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		root = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		action = Action.ACTION_MULTIPLE_PICK;
		Log.d("root-directory", root.toString());
		sd_list = new ArrayList<String>();
		sd_list.add(root.toString());
		Log.d("root-directory", String.valueOf(sd_list.size()));

		btnGalleryOk_list.setOnClickListener(mOkClickListener);

		initImageLoader();
		init(root);
		
		lv_hsd.setAdapter(mAdapter);
		
		lv_hsd.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				int tot = position + 1;
				ArrayList<String> _list = new ArrayList<String>(tot);
				Log.d("selected item", String.valueOf(_list.size()));
				for(int i = 0; i < tot; i ++){
					String folder = String.valueOf(sd_list.get(i));
					_list.add(folder);
				}
				Log.d("selected item", String.valueOf(_list.size()));
				sd_list = _list;
				Log.d("selected item", String.valueOf(sd_list.size()));
				
				if (sd_list != null && sd_list.size() > 0) {
					if (sd_list.size() == 1) {
						int dex = sd_list.size() - 1;
						Log.d("1", String.valueOf(sd_list.size()));
						String sd = String.valueOf(sd_list.get(dex));
						Log.d("1", String.valueOf(sd_list.get(dex) + dex));
						File root = new File(sd);
						initImageLoader();
						init(root);
						back_pressed = false;
					} else if (sd_list.size() > 1) {
						int dex = sd_list.size() - 2;
						Log.d("dex", String.valueOf(sd_list.size()));
						String sd = String.valueOf(sd_list.get(dex));
						Log.d("dex", String.valueOf(sd_list.get(dex) + dex));
						File root = new File(sd);
						initImageLoader();
						init(root);
						back_pressed = false;
					}
				} else {
					Sd_Folder.this.finish();
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});

	}
	
	private BaseAdapter mAdapter = new BaseAdapter() {

		@Override
		public int getCount() {
			return sd_list.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_hlv, null);
			TextView title = (TextView) retval.findViewById(R.id.hlv_title);
			ImageView fld_img = (ImageView) retval.findViewById(R.id.hlv_fld);
			ImageView arrow = (ImageView) retval.findViewById(R.id.hlv_next);
			
			if(sd_list.size() == 1){
				if(position == 0){
					arrow.setVisibility(View.GONE);
					fld_img.setBackgroundResource(R.drawable.h_sd);
				}
			}else if(sd_list.size() == 2){
				if(position == 0){
					arrow.setVisibility(View.VISIBLE);
					fld_img.setBackgroundResource(R.drawable.h_sd);
				}else if(position == 1){
					arrow.setVisibility(View.GONE);
					fld_img.setBackgroundResource(R.drawable.h_ofld);
				}
			}else {
				if(position == 0){
					arrow.setVisibility(View.VISIBLE);
					fld_img.setBackgroundResource(R.drawable.h_sd);
				}else if(position < (getCount()-1)){
					arrow.setVisibility(View.VISIBLE);
					fld_img.setBackgroundResource(R.drawable.h_fld);
				}else {
					arrow.setVisibility(View.GONE);
					fld_img.setBackgroundResource(R.drawable.h_ofld);
				}
			}
			
			String stng = sd_list.get(position);
			String str[]= stng.split("/");
			if(str.length <= 3 || str[str.length-1].toString().contentEquals("0") || str[str.length-1].toString().contentEquals("sdcard") || sd_list.size() == 1){
				title.setText("SD CARD");
			}else{
				title.setText(str[str.length-1].toString());
			}
			
			
			return retval;
		}
		
	};


	private void d_delete() {
		boo_dlt = true;
		if (dlt) {
			if (dlt_path != null && dlt_path.size() > 0) {
				for (int i = 0; i < dlt_path.size(); i++) {
					dlt_stng = dlt_path.get(i);
					String del = "http://whootin.com/api/v1/files/destroy/"
							+ dlt_stng;
					new delete1().execute(del);
					i = dlt_path.size();
				}
			}
		}

	}

	public class delete1 extends AsyncTask<String, Integer, String> {
		boolean isTrue = true;
		int k = 0;
		String get_urll = null;

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			Log.d("back", "back");
			get_urll = params[0];
			Log.d("call1", get_urll);
			dlt_whootin(get_urll);
			while (isTrue) {
				publishProgress(k++);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (k > 100) {
					isTrue = false;
				}
			}
			return get_urll;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("back", "back2");
			if (dlt_path.size() == 0) {
				Log.d("back", "yes");
				dlt_excepted();
			} else {

				Log.d("back", "no");
				dlt_unexcepted(result);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

	}

	public class delete2 extends AsyncTask<String, Integer, String> {
		boolean isTrue = true;
		int k = 0;
		String get_urll = null;

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			get_urll = params[0];
			dlt_whootin(get_urll);
			while (isTrue) {
				publishProgress(k++);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (k > 100) {
					isTrue = false;
				}
			}
			return get_urll;
		}

		@Override
		protected void onPostExecute(String result) {
			if (dlt_path.size() == 0) {
				dlt_excepted();
			} else {
				Log.d("result", result);
				dlt_unexcepted(result);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

	}

	private void dlt_excepted() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dialog();
		Log.d("delete", "delete");
	}

	private void dlt_unexcepted(String result) {
		Log.d("Size of listview", String.valueOf(dlt_path.size()));
		if (dlt_stng != null) {
			dlt_path.remove(dlt_stng);
		} else {
			dlt_path.remove(0);
		}
		Log.d("Size of listview", String.valueOf(dlt_path.size()));
		if (dlt) {
			if (dlt_path != null && dlt_path.size() > 0) {
				for (int i = 0; i < dlt_path.size(); i++) {
					dlt_stng = dlt_path.get(i);
					String del = "http://whootin.com/api/v1/files/destroy/"
							+ dlt_stng;
					if (boo_dlt) {
						Log.d("delete", "delete2");
						boo_dlt = false;
						new delete2().execute(del);
					} else {
						Log.d("delete", "delete1");
						boo_dlt = true;
						new delete1().execute(del);
					}
					i = dlt_path.size();
				}
			} else {
				dlt_excepted();
			}
		}
	}

	private void dlt_whootin(String del) {
		Constant.datee = getSharedPreferences("datefun", MODE_WORLD_WRITEABLE);
		String access_token = "";
		if (Constant.datee != null && Constant.datee.contains("access_token")) {
			access_token = "Bearer "
					+ Constant.datee.getString("access_token", "");
			Log.d("access_token", access_token);
		}

		AsyncHttpClient client = new AsyncHttpClient();
		client.addHeader("Accept", "appliction/json");
		client.addHeader("Authorization", access_token);
		client.delete(del, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String arg0) {
				super.onSuccess(arg0);
				Log.e("from response", arg0);
			}

			@Override
			public void onFinish() {
				super.onFinish();
				Log.e("from response finish", "finish");

			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				super.onFailure(arg0, arg1);
				Log.e("from response", arg1);
			}

		});

	}

	public class SimAdapteredpg extends BaseAdapter {

		Context sim_context;
		LayoutInflater inflater;
		ArrayList<HashMap<String, String>> list_adptpg;
		int pos = 0;
		String folder = null, name = null, size = null, size1 = null,
				date = null;

		public SimAdapteredpg(Context context,
				ArrayList<HashMap<String, String>> gallery_list) {
			this.sim_context = context;
			this.list_adptpg = gallery_list;
			Log.d("MAS", "LIST1");

		}

		public SimAdapteredpg(Context applicationContext) {
			this.sim_context = applicationContext;
		}

		@Override
		public int getCount() {
			return list_adptpg.size();
		}

		@Override
		public Object getItem(int position) {
			return list_adptpg.get(position);

		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			TextView txt_filename;
			ImageView img_file;
			int id;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			final ViewHolder holder;
			pos = position;
			if (view == null && list_adptpg.size() != 0) {
				inflater = (LayoutInflater) sim_context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.twotextt, null);
				holder = new ViewHolder();
				holder.txt_filename = (TextView) view
						.findViewById(R.id.list_nmtxt);
				holder.img_file = (ImageView) view.findViewById(R.id.list_img);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			/** To set id to given ListView of Objects */
			holder.txt_filename.setId(pos);
			holder.img_file.setId(pos);
			String flnm = list_adptpg.get(pos).get("name");
			File fl = new File(flnm);
			name = fl.getName().toString();
			Log.d("filename", name);
			holder.txt_filename.setText(name);

			if (name.endsWith(".zip") || name.endsWith(".ZIP")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_zip);

			} else if (name.endsWith(".pdf") || name.endsWith(".PDF")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_pdf);

			} else if (name.endsWith(".doc") || name.endsWith(".DOC")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_doc);

			} else if (name.endsWith(".ice") || name.endsWith(".ICE")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_conf);

			} else if (name.endsWith(".wmf") || name.endsWith(".WMF")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_wmf);

			} else if (name.endsWith(".ivr") || name.endsWith(".IVR")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_ivr);

			} else if (name.endsWith(".pvu") || name.endsWith(".PVU")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_pvu);

			} else if (name.endsWith(".xyz") || name.endsWith(".XYZ")
					|| name.endsWith(".pdb") || name.endsWith(".PDB")) {

				holder.img_file
						.setBackgroundResource(R.drawable.ic_chemical);

			} else if (name.endsWith(".xgz") || name.endsWith(".XGZ")
					|| name.endsWith(".xmz") || name.endsWith(".XMZ")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_xgl);

			} else if (name.endsWith(".ustar") || name.endsWith(".USTAR")
					|| name.endsWith(".gzip") || name.endsWith(".GZIP")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_mht);

			} else if (name.endsWith(".mime") || name.endsWith(".MIME")
					|| name.endsWith(".mht") || name.endsWith(".MTH")
					|| name.endsWith(".mhtml") || name.endsWith(".MHTML")) {

				holder.img_file
						.setBackgroundResource(R.drawable.ic_model);

			} else if (name.endsWith(".3dm") || name.endsWith(".3DM")
					|| name.endsWith(".3dmf") || name.endsWith(".3DMF")
					|| name.endsWith(".qd3") || name.endsWith(".QD3")
					|| name.endsWith(".qd3d") || name.endsWith(".QD3D")
					|| name.endsWith(".3svr") || name.endsWith(".3SVR")
					|| name.endsWith(".vrt") || name.endsWith(".VRT")) {

				holder.img_file
						.setBackgroundResource(R.drawable.ic_xworld);

			} else if (name.endsWith(".vrml") || name.endsWith(".IVR")
					|| name.endsWith(".pov") || name.endsWith(".POV")
					|| name.endsWith(".iges") || name.endsWith(".IGES")
					|| name.endsWith(".igs") || name.endsWith(".IGS")
					|| name.endsWith(".wrl") || name.endsWith(".WRL")
					|| name.endsWith(".wrz") || name.endsWith(".WRZ")
					|| name.endsWith(".dwf") || name.endsWith(".DWF")) {

				holder.img_file
						.setBackgroundResource(R.drawable.ic_model);

			} else if (name.endsWith(".html") || name.endsWith(".HTML")
					|| name.endsWith(".shtml") || name.endsWith(".SHTML")
					|| name.endsWith(".acgi") || name.endsWith(".ACGI")
					|| name.endsWith(".htm") || name.endsWith(".HTM")
					|| name.endsWith(".htmls") || name.endsWith(".HTMLS")
					|| name.endsWith(".htt") || name.endsWith(".HTT")
					|| name.endsWith(".htx") || name.endsWith(".HTX")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_html);

			} else if (name.endsWith(".ppt") || name.endsWith(".PPT")
					|| name.endsWith(".pptx") || name.endsWith(".PPTX")
					|| name.endsWith(".pptm") || name.endsWith(".PPTM")
					|| name.endsWith(".pot") || name.endsWith(".POT")
					|| name.endsWith(".potx") || name.endsWith(".POTX")
					|| name.endsWith(".pps") || name.endsWith(".PPS")
					|| name.endsWith(".ppsx") || name.endsWith(".PPSX")
					|| name.endsWith(".ppsm") || name.endsWith(".PPSM")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_ppt);

			} else if (name.endsWith(".xls") || name.endsWith(".XLS")
					|| name.endsWith(".xlsx") || name.endsWith(".XLSX")
					|| name.endsWith(".xlam") || name.endsWith(".XLAM")
					|| name.endsWith(".xltm") || name.endsWith(".XLTM")
					|| name.endsWith(".xlsm") || name.endsWith(".XLSM")
					|| name.endsWith(".xlsb") || name.endsWith(".XLSB")
					|| name.endsWith(".xl") || name.endsWith(".XL")
					|| name.endsWith(".xla") || name.endsWith(".XLA")
					|| name.endsWith(".xlb") || name.endsWith(".XLB")
					|| name.endsWith(".xlc") || name.endsWith(".XLC")
					|| name.endsWith(".xld") || name.endsWith(".XLD")
					|| name.endsWith(".xlk") || name.endsWith(".XLK")
					|| name.endsWith(".xll") || name.endsWith(".XLL")
					|| name.endsWith(".xlm") || name.endsWith(".XLM")
					|| name.endsWith(".xlt") || name.endsWith(".XLT")
					|| name.endsWith(".xlv") || name.endsWith(".XLV")
					|| name.endsWith(".xlw") || name.endsWith(".XLW")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_xls);

			} else if (name.endsWith(".mp4") || name.endsWith(".MP4")
					|| name.endsWith(".avi") || name.endsWith(".AVI")
					|| name.endsWith(".afl") || name.endsWith(".AFL")
					|| name.endsWith(".3gp") || name.endsWith(".3GP")
					|| name.endsWith(".mkv") || name.endsWith(".MKV")
					|| name.endsWith(".flv") || name.endsWith(".FLV")
					|| name.endsWith(".wmv") || name.endsWith(".WMV")
					|| name.endsWith(".asf") || name.endsWith(".ASF")
					|| name.endsWith(".mpeg") || name.endsWith(".MPEG")
					|| name.endsWith(".dv") || name.endsWith(".DV")
					|| name.endsWith(".dl") || name.endsWith(".DL")
					|| name.endsWith(".mov") || name.endsWith(".MOV")
					|| name.endsWith(".avs") || name.endsWith(".AVS")
					|| name.endsWith(".af") || name.endsWith(".AF")
					|| name.endsWith(".asf") || name.endsWith(".ASF")
					|| name.endsWith(".dif") || name.endsWith(".DIF")
					|| name.endsWith(".fli") || name.endsWith(".FLI")
					|| name.endsWith(".fmf") || name.endsWith(".FMF")
					|| name.endsWith(".asx") || name.endsWith(".ASX")
					|| name.endsWith(".mjpg") || name.endsWith(".MJPG")
					|| name.endsWith(".m2v") || name.endsWith(".M2V")
					|| name.endsWith(".m1v") || name.endsWith(".M1V")
					|| name.endsWith(".scm") || name.endsWith(".SCM")
					|| name.endsWith(".vivo") || name.endsWith(".VIVO")
					|| name.endsWith(".vos") || name.endsWith(".VOS")
					|| name.endsWith(".xsr") || name.endsWith(".XSR")
					|| name.endsWith(".xdr") || name.endsWith(".XDR")
					|| name.endsWith(".gl") || name.endsWith(".GL")
					|| name.endsWith(".isu") || name.endsWith(".ISU")
					|| name.endsWith(".rv") || name.endsWith(".RV")
					|| name.endsWith(".vdo") || name.endsWith(".VDO")
					|| name.endsWith(".viv") || name.endsWith(".VIV")
					|| name.endsWith(".qtc") || name.endsWith(".QTC")
					|| name.endsWith(".qt") || name.endsWith(".QT")
					|| name.endsWith(".mv") || name.endsWith(".MV")
					|| name.endsWith(".moov") || name.endsWith(".MOOV")
					|| name.endsWith(".mov") || name.endsWith(".MOV")
					|| name.endsWith(".movie") || name.endsWith(".MOVIE")
					|| name.endsWith(".mpe") || name.endsWith(".MPE")) {

				holder.img_file
						.setBackgroundResource(R.drawable.ic_video);

			} else if (name.endsWith(".jpg") || name.endsWith(".JPG")
					|| name.endsWith(".jpeg") || name.endsWith(".JPEG")
					|| name.endsWith(".bmp") || name.endsWith(".BMP")
					|| name.endsWith(".svg") || name.endsWith(".SVG")
					|| name.endsWith(".png") || name.endsWith(".PNG")
					|| name.endsWith(".ras") || name.endsWith(".RAS")
					|| name.endsWith(".rast") || name.endsWith(".RAST")
					|| name.endsWith(".wbmp") || name.endsWith(".WBMP")
					|| name.endsWith(".xwd") || name.endsWith(".XWD")
					|| name.endsWith(".xpm") || name.endsWith(".XPM")
					|| name.endsWith(".x-png") || name.endsWith(".X-PNG")
					|| name.endsWith(".xbm") || name.endsWith(".XBM")
					|| name.endsWith(".rf") || name.endsWith(".RF")
					|| name.endsWith(".rgb") || name.endsWith(".RGB")
					|| name.endsWith(".tif") || name.endsWith(".TIF")
					|| name.endsWith(".tiff") || name.endsWith(".TIFF")
					|| name.endsWith(".svf") || name.endsWith(".SVF")
					|| name.endsWith(".rp") || name.endsWith(".RP")
					|| name.endsWith(".xif") || name.endsWith(".XIF")
					|| name.endsWith(".turbot") || name.endsWith(".TURBOT")
					|| name.endsWith(".pgm") || name.endsWith(".PGM")
					|| name.endsWith(".pic") || name.endsWith(".PIC")
					|| name.endsWith(".pict") || name.endsWith(".PICT")
					|| name.endsWith(".pm") || name.endsWith(".PM")
					|| name.endsWith(".pnm") || name.endsWith(".PNM")
					|| name.endsWith(".nap") || name.endsWith(".NAP")
					|| name.endsWith(".naplps") || name.endsWith(".NAPLPS")
					|| name.endsWith(".nif") || name.endsWith(".NIF")
					|| name.endsWith(".niff") || name.endsWith(".NIFF")
					|| name.endsWith(".pbm") || name.endsWith(".PBM")
					|| name.endsWith(".pct") || name.endsWith(".PCT")
					|| name.endsWith(".pcx") || name.endsWith(".PCX")
					|| name.endsWith(".ico") || name.endsWith(".ICO")
					|| name.endsWith(".ief") || name.endsWith(".IEF")
					|| name.endsWith(".iefs") || name.endsWith(".IEFS")
					|| name.endsWith(".dwg") || name.endsWith(".DWG")
					|| name.endsWith(".dxf") || name.endsWith(".DXF")
					|| name.endsWith(".art") || name.endsWith(".ART")
					|| name.endsWith(".bm") || name.endsWith(".BM")
					|| name.endsWith(".bmp") || name.endsWith(".BMP")
					|| name.endsWith(".fif") || name.endsWith(".FIF")
					|| name.endsWith(".flo") || name.endsWith(".FLO")
					|| name.endsWith(".fpx") || name.endsWith(".FPX")
					|| name.endsWith(".g3") || name.endsWith(".G3")
					|| name.endsWith(".gif") || name.endsWith(".GIF")
					|| name.endsWith(".jfif") || name.endsWith(".JFIF")
					|| name.endsWith(".jfif-tbnl")
					|| name.endsWith(".JFIF-TBNL") || name.endsWith(".jpe")
					|| name.endsWith(".JPE")
					|| name.endsWith(".jps") || name.endsWith(".JPS")
					|| name.endsWith(".jut") || name.endsWith(".JUT")
					|| name.endsWith(".qif") || name.endsWith(".QIF")
					|| name.endsWith(".qti") || name.endsWith(".QTI")) {

				holder.img_file
						.setBackgroundResource(R.drawable.ic_images);
				

			} else if (name.endsWith(".ab") || name.endsWith(".AB")
					|| name.endsWith(".acgi") || name.endsWith(".ACGI")
					|| name.endsWith(".ksh") || name.endsWith(".KSH")
					|| name.endsWith(".jav") || name.endsWith(".JAV")
					|| name.endsWith(".java") || name.endsWith(".JAVA")
					|| name.endsWith(".lsp") || name.endsWith(".LSP")
					|| name.endsWith(".lsx") || name.endsWith(".LSX")
					|| name.endsWith(".lst") || name.endsWith(".LST")
					|| name.endsWith(".m") || name.endsWith(".M")
					|| name.endsWith(".p") || name.endsWith(".P")
					|| name.endsWith(".pas") || name.endsWith(".PAS")
					|| name.endsWith(".pl") || name.endsWith(".PL")
					|| name.endsWith(".pm") || name.endsWith(".PM")
					|| name.endsWith(".mar") || name.endsWith(".MAR")
					|| name.endsWith(".js") || name.endsWith(".JS")
					|| name.endsWith(".list") || name.endsWith(".LIST")
					|| name.endsWith(".log") || name.endsWith(".LOG")
					|| name.endsWith(".mcf") || name.endsWith(".MCF")
					|| name.endsWith(".rexx") || name.endsWith(".REXX")
					|| name.endsWith(".sdml") || name.endsWith(".SDML")
					|| name.endsWith(".rtx") || name.endsWith(".RTX")
					|| name.endsWith(".scm") || name.endsWith(".SCM")
					|| name.endsWith(".s") || name.endsWith(".S")
					|| name.endsWith(".rt") || name.endsWith(".RT")
					|| name.endsWith(".rtf") || name.endsWith(".RTF")
					|| name.endsWith(".sgm") || name.endsWith(".SGM")
					|| name.endsWith(".sgml") || name.endsWith(".SGML")
					|| name.endsWith(".spc") || name.endsWith(".SPC")
					|| name.endsWith(".ssi") || name.endsWith(".SSI")
					|| name.endsWith(".talk") || name.endsWith(".TALK")
					|| name.endsWith(".tcl") || name.endsWith(".TCL")
					|| name.endsWith(".tcsh") || name.endsWith(".TCSH")
					|| name.endsWith(".text") || name.endsWith(".TEXT")
					|| name.endsWith(".tsv") || name.endsWith(".TSV")
					|| name.endsWith(".txt") || name.endsWith(".TXT")
					|| name.endsWith(".uil") || name.endsWith(".UIL")
					|| name.endsWith(".uni") || name.endsWith(".UNI")
					|| name.endsWith(".unis") || name.endsWith(".UNIS")
					|| name.endsWith(".uri") || name.endsWith(".URI")
					|| name.endsWith(".uris") || name.endsWith(".URIS")
					|| name.endsWith(".uu") || name.endsWith(".UU")
					|| name.endsWith(".uue") || name.endsWith(".UUE")
					|| name.endsWith(".wml") || name.endsWith(".WML")
					|| name.endsWith(".wmls") || name.endsWith(".WMLS")
					|| name.endsWith(".zsh") || name.endsWith(".ZSH")
					|| name.endsWith(".xml") || name.endsWith(".XML")
					|| name.endsWith(".wsc") || name.endsWith(".WMZ")
					|| name.endsWith(".vcs") || name.endsWith(".VCS")
					|| name.endsWith(".sh") || name.endsWith(".SH")
					|| name.endsWith(".py") || name.endsWith(".PY")
					|| name.endsWith(".aip") || name.endsWith(".AIP")
					|| name.endsWith(".asm") || name.endsWith(".ASM")
					|| name.endsWith(".asf") || name.endsWith(".ASP")
					|| name.endsWith(".c") || name.endsWith(".C")
					|| name.endsWith(".c++") || name.endsWith(".C++")
					|| name.endsWith(".cc") || name.endsWith(".CC")
					|| name.endsWith(".com") || name.endsWith(".COM")
					|| name.endsWith(".conf") || name.endsWith(".CONF")
					|| name.endsWith(".cpp") || name.endsWith(".CPP")
					|| name.endsWith(".csh") || name.endsWith(".CSH")
					|| name.endsWith(".cxx") || name.endsWith(".CXX")
					|| name.endsWith(".css") || name.endsWith(".CSS")
					|| name.endsWith(".def") || name.endsWith(".DEF")
					|| name.endsWith(".el") || name.endsWith(".EL")
					|| name.endsWith(".etx") || name.endsWith(".ETX")
					|| name.endsWith(".f") || name.endsWith(".F")
					|| name.endsWith(".f77") || name.endsWith(".F77")
					|| name.endsWith(".f90") || name.endsWith(".F90")
					|| name.endsWith(".hh") || name.endsWith(".HH")
					|| name.endsWith(".hlb") || name.endsWith(".HLB")
					|| name.endsWith(".htc") || name.endsWith(".HTC")
					|| name.endsWith(".g") || name.endsWith(".G")
					|| name.endsWith(".h") || name.endsWith(".H")
					|| name.endsWith(".idc") || name.endsWith(".IDC")
					|| name.endsWith(".flx") || name.endsWith(".FLX")
					|| name.endsWith(".for") || name.endsWith(".FOR")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_txt);

			} else if (name.endsWith(".mp3") || name.endsWith(".MP3")
					|| name.endsWith(".mp2") || name.endsWith(".MP2")
					|| name.endsWith(".wav") || name.endsWith(".WAV")
					|| name.endsWith(".aac") || name.endsWith(".AAC")
					|| name.endsWith(".ac3") || name.endsWith(".AC3")
					|| name.endsWith(".aif") || name.endsWith(".AIF")
					|| name.endsWith(".aifc") || name.endsWith(".AIFC")
					|| name.endsWith(".aiff") || name.endsWith(".AIFF")
					|| name.endsWith(".funk") || name.endsWith(".FUNK")
					|| name.endsWith(".au") || name.endsWith(".AU")
					|| name.endsWith(".gsd") || name.endsWith(".GSD")
					|| name.endsWith(".gsm") || name.endsWith(".GSM")
					|| name.endsWith(".it") || name.endsWith(".IT")
					|| name.endsWith(".jam") || name.endsWith(".JAM")
					|| name.endsWith(".la") || name.endsWith(".LA")
					|| name.endsWith(".lam") || name.endsWith(".LAM")
					|| name.endsWith(".kar") || name.endsWith(".KAR")
					|| name.endsWith(".lma") || name.endsWith(".LMA")
					|| name.endsWith(".m2a") || name.endsWith(".M2A")
					|| name.endsWith(".mid") || name.endsWith(".MID")
					|| name.endsWith(".midi") || name.endsWith(".MIDI")
					|| name.endsWith(".m3u") || name.endsWith(".M3U")
					|| name.endsWith(".mjf") || name.endsWith(".MJF")
					|| name.endsWith(".rm") || name.endsWith(".RM")
					|| name.endsWith(".rmm") || name.endsWith(".RMM")
					|| name.endsWith(".rmp") || name.endsWith(".RMP")
					|| name.endsWith(".rpm") || name.endsWith(".RPM")
					|| name.endsWith(".s3m") || name.endsWith(".S3M")
					|| name.endsWith(".sid") || name.endsWith(".SID")
					|| name.endsWith(".snd") || name.endsWith(".SND")
					|| name.endsWith(".tsi") || name.endsWith(".TSI")
					|| name.endsWith(".voc") || name.endsWith(".VOC")
					|| name.endsWith(".vox") || name.endsWith(".VOX")
					|| name.endsWith(".vqe") || name.endsWith(".VQE")
					|| name.endsWith(".vqf") || name.endsWith(".VQF")
					|| name.endsWith(".vql") || name.endsWith(".VQL")
					|| name.endsWith(".xm") || name.endsWith(".XM")
					|| name.endsWith(".tsp") || name.endsWith(".TSP")
					|| name.endsWith(".mod") || name.endsWith(".MOD")
					|| name.endsWith(".mpa") || name.endsWith(".MPA")
					|| name.endsWith(".my") || name.endsWith(".MY")
					|| name.endsWith(".mpg") || name.endsWith(".MPG")
					|| name.endsWith(".pfunk") || name.endsWith(".PFUNK")
					|| name.endsWith(".mpga") || name.endsWith(".MPGA")
					|| name.endsWith(".ra") || name.endsWith(".RA")
					|| name.endsWith(".qcp") || name.endsWith(".QCP")
					|| name.endsWith(".ram") || name.endsWith(".RAM")) {

				holder.img_file
						.setBackgroundResource(R.drawable.ic_audio);

			} else if (name.endsWith(".a") || name.endsWith(".A")
					|| name.endsWith(".aab") || name.endsWith(".AAB")
					|| name.endsWith(".aam") || name.endsWith(".AAM")
					|| name.endsWith(".aas") || name.endsWith(".AAS")
					|| name.endsWith(".ai") || name.endsWith(".AI")
					|| name.endsWith(".aim") || name.endsWith(".AIM")
					|| name.endsWith(".ani") || name.endsWith(".ANI")
					|| name.endsWith(".aos") || name.endsWith(".AOS")
					|| name.endsWith(".aps") || name.endsWith(".APS")
					|| name.endsWith(".arc") || name.endsWith(".ARC")
					|| name.endsWith(".arj") || name.endsWith(".ARJ")
					|| name.endsWith(".bcpio") || name.endsWith(".BCPIO")
					|| name.endsWith(".bin") || name.endsWith(".BIN")
					|| name.endsWith(".boo") || name.endsWith(".BOO")
					|| name.endsWith(".book") || name.endsWith(".BOOK")
					|| name.endsWith(".boz") || name.endsWith(".BOZ")
					|| name.endsWith(".bsh") || name.endsWith(".BSH")
					|| name.endsWith(".bz") || name.endsWith(".BZ")
					|| name.endsWith(".bz2") || name.endsWith(".BZ2")
					|| name.endsWith(".cat") || name.endsWith(".CAT")
					|| name.endsWith(".ccat") || name.endsWith(".CCAD")
					|| name.endsWith(".cco") || name.endsWith(".CCO")
					|| name.endsWith(".cdf") || name.endsWith(".CDF")
					|| name.endsWith(".cer") || name.endsWith(".CER")
					|| name.endsWith(".cha") || name.endsWith(".CHA")
					|| name.endsWith(".class") || name.endsWith(".CLASS")
					|| name.endsWith(".chat") || name.endsWith(".CHAT")
					|| name.endsWith(".der") || name.endsWith(".DER")
					|| name.endsWith(".dir") || name.endsWith(".DIR")
					|| name.endsWith(".cpio") || name.endsWith(".CPIO")
					|| name.endsWith(".dcr") || name.endsWith(".DCR")
					|| name.endsWith(".deepv") || name.endsWith(".DEEPV")
					|| name.endsWith(".cpi") || name.endsWith(".CPT")
					|| name.endsWith(".crl") || name.endsWith(".CRL")
					|| name.endsWith(".crt") || name.endsWith(".CRT")
					|| name.endsWith(".dot") || name.endsWith(".DOT")
					|| name.endsWith(".dp") || name.endsWith(".DP")
					|| name.endsWith(".drw") || name.endsWith(".DRW")
					|| name.endsWith(".dump") || name.endsWith(".DUMP")
					|| name.endsWith(".dvi") || name.endsWith(".DVI")
					|| name.endsWith(".elc") || name.endsWith(".ELC")
					|| name.endsWith(".env") || name.endsWith(".ENV")
					|| name.endsWith(".eps") || name.endsWith(".EPS")
					|| name.endsWith(".es") || name.endsWith(".ES")
					|| name.endsWith(".evy") || name.endsWith(".EVY")
					|| name.endsWith(".exe") || name.endsWith(".EXE")
					|| name.endsWith(".gsp") || name.endsWith(".GSP")
					|| name.endsWith(".gss") || name.endsWith(".GSS")
					|| name.endsWith(".gtar") || name.endsWith(".GTAR")
					|| name.endsWith(".gz") || name.endsWith(".GZ")
					|| name.endsWith(".hlp") || name.endsWith(".HLP")
					|| name.endsWith(".hpgr") || name.endsWith(".HPGR")
					|| name.endsWith(".hpgl") || name.endsWith(".HPGL")
					|| name.endsWith(".hqx") || name.endsWith(".HQX")
					|| name.endsWith(".hta") || name.endsWith(".HTA")
					|| name.endsWith(".mpp") || name.endsWith(".MPP")
					|| name.endsWith(".mpt") || name.endsWith(".MPT")
					|| name.endsWith(".mpv") || name.endsWith(".MPV")
					|| name.endsWith(".mpx") || name.endsWith(".MPX")
					|| name.endsWith(".mrc") || name.endsWith(".MRC")
					|| name.endsWith(".ms") || name.endsWith(".MS")
					|| name.endsWith(".p10") || name.endsWith(".P10")
					|| name.endsWith(".p12") || name.endsWith(".P12")
					|| name.endsWith(".p7a") || name.endsWith(".P7A")
					|| name.endsWith(".p7c") || name.endsWith(".P7C")
					|| name.endsWith(".p7m") || name.endsWith(".P7M")
					|| name.endsWith(".p7r") || name.endsWith(".P7R")
					|| name.endsWith(".p7s") || name.endsWith(".P7S")
					|| name.endsWith(".part") || name.endsWith(".PART")
					|| name.endsWith(".o") || name.endsWith(".O")
					|| name.endsWith(".nvd") || name.endsWith(".NVD")
					|| name.endsWith(".nsc") || name.endsWith(".NSC")
					|| name.endsWith(".nix") || name.endsWith(".NIX")
					|| name.endsWith(".omcr") || name.endsWith(".OMCR")
					|| name.endsWith(".omcd") || name.endsWith(".OMCD")
					|| name.endsWith(".omc") || name.endsWith(".OMC")
					|| name.endsWith(".oda") || name.endsWith(".ODA")
					|| name.endsWith(".w60") || name.endsWith(".W60")
					|| name.endsWith(".vsw") || name.endsWith(".VSW")
					|| name.endsWith(".vst") || name.endsWith(".VST")
					|| name.endsWith(".vsd") || name.endsWith(".VSD")
					|| name.endsWith(".w61") || name.endsWith(".W61")
					|| name.endsWith(".w6w") || name.endsWith(".W6W")
					|| name.endsWith(".wb1") || name.endsWith(".WB1")
					|| name.endsWith(".web") || name.endsWith(".WEB")
					|| name.endsWith(".wiz") || name.endsWith(".WIZ")
					|| name.endsWith(".wk1") || name.endsWith(".WK1")
					|| name.endsWith(".wmlc") || name.endsWith(".WMLC")
					|| name.endsWith(".wsrc") || name.endsWith(".WSRC")
					|| name.endsWith(".wtk") || name.endsWith(".WTK")
					|| name.endsWith(".wmlsc") || name.endsWith(".WMLSC")
					|| name.endsWith(".word") || name.endsWith(".WORD")
					|| name.endsWith(".wp") || name.endsWith(".WP")
					|| name.endsWith(".wp5") || name.endsWith(".WP5")
					|| name.endsWith(".wp6") || name.endsWith(".WP6")
					|| name.endsWith(".wpd") || name.endsWith(".WPD")
					|| name.endsWith(".wql") || name.endsWith(".WQL")
					|| name.endsWith(".wri") || name.endsWith(".WRI")
					|| name.endsWith(".z") || name.endsWith(".Z")
					|| name.endsWith(".zoo") || name.endsWith(".ZOO")
					|| name.endsWith(".spl") || name.endsWith(".SPL")
					|| name.endsWith(".spr") || name.endsWith(".SPR")
					|| name.endsWith(".sprite") || name.endsWith(".SPRITE")
					|| name.endsWith(".src") || name.endsWith(".SRC")
					|| name.endsWith(".ssm") || name.endsWith(".SSM")
					|| name.endsWith(".sst") || name.endsWith(".SST")
					|| name.endsWith(".step") || name.endsWith(".STEP")
					|| name.endsWith(".stl") || name.endsWith(".STL")
					|| name.endsWith(".stp") || name.endsWith(".STP")
					|| name.endsWith(".sv4cpio")
					|| name.endsWith(".SV4CPIO")
					|| name.endsWith(".sv4crc") || name.endsWith(".SV4CRC")
					|| name.endsWith(".svr") || name.endsWith(".SVR")
					|| name.endsWith(".sol") || name.endsWith(".SOL")
					|| name.endsWith(".tar") || name.endsWith(".TAR")
					|| name.endsWith(".tbk") || name.endsWith(".TBK")
					|| name.endsWith(".tcl") || name.endsWith(".TCL")
					|| name.endsWith(".swf") || name.endsWith(".SWF")
					|| name.endsWith(".t") || name.endsWith(".T")
					|| name.endsWith(".tgz") || name.endsWith(".TGZ")
					|| name.endsWith(".tex") || name.endsWith(".TEX")
					|| name.endsWith(".texi") || name.endsWith(".TEXI")
					|| name.endsWith(".texinfo")
					|| name.endsWith(".TEXINFO")
					|| name.endsWith(".tr") || name.endsWith(".TR")
					|| name.endsWith(".vmd") || name.endsWith(".VMD")
					|| name.endsWith(".vmf") || name.endsWith(".VMF")
					|| name.endsWith(".vrml") || name.endsWith(".VRML")
					|| name.endsWith(".vew") || name.endsWith(".VEW")
					|| name.endsWith(".vda") || name.endsWith(".VDA")
					|| name.endsWith(".unv") || name.endsWith(".UNV")
					|| name.endsWith(".vcd") || name.endsWith(".VCD")
					|| name.endsWith(".rtx") || name.endsWith(".RTX")
					|| name.endsWith(".ima") || name.endsWith(".IMA")
					|| name.endsWith(".imap") || name.endsWith(".IMAP")
					|| name.endsWith(".inf") || name.endsWith(".INF")
					|| name.endsWith(".ins") || name.endsWith(".INS")
					|| name.endsWith(".ima") || name.endsWith(".IMA")
					|| name.endsWith(".inf") || name.endsWith(".INF")
					|| name.endsWith(".ins") || name.endsWith(".INS")
					|| name.endsWith(".ip") || name.endsWith(".IP")
					|| name.endsWith(".iv") || name.endsWith(".IV")
					|| name.endsWith(".hdf") || name.endsWith(".HDF")
					|| name.endsWith(".help") || name.endsWith(".HELP")
					|| name.endsWith(".hgl") || name.endsWith(".HGL")
					|| name.endsWith(".frl") || name.endsWith(".FRL")
					|| name.endsWith(".fdf") || name.endsWith(".FDF")
					|| name.endsWith(".pcl") || name.endsWith(".PCL")
					|| name.endsWith(".pm4") || name.endsWith(".PM4")
					|| name.endsWith(".pm5") || name.endsWith(".PM5")
					|| name.endsWith(".plx") || name.endsWith(".PLX")
					|| name.endsWith(".pwz") || name.endsWith(".PWZ")
					|| name.endsWith(".ppa") || name.endsWith(".PPA")
					|| name.endsWith(".pyc") || name.endsWith(".PYC")
					|| name.endsWith(".pkg") || name.endsWith(".PKG")
					|| name.endsWith(".pko") || name.endsWith(".PKO")
					|| name.endsWith(".nc") || name.endsWith(".NC")
					|| name.endsWith(".ncm") || name.endsWith(".NCM")
					|| name.endsWith(".mzz") || name.endsWith(".MZZ")
					|| name.endsWith(".mm") || name.endsWith(".MM")
					|| name.endsWith(".mme") || name.endsWith(".MME")
					|| name.endsWith(".mpc") || name.endsWith(".MPC")
					|| name.endsWith(".mif") || name.endsWith(".MIF")
					|| name.endsWith(".shar") || name.endsWith(".SHAR")
					|| name.endsWith(".sdp") || name.endsWith(".SDP")
					|| name.endsWith(".sdr") || name.endsWith(".SDR")
					|| name.endsWith(".sea") || name.endsWith(".SEA")
					|| name.endsWith(".sit") || name.endsWith(".SIT")
					|| name.endsWith(".smil") || name.endsWith(".SMIL")
					|| name.endsWith(".smi") || name.endsWith(".SMI")
					|| name.endsWith(".sl") || name.endsWith(".SL")
					|| name.endsWith(".skt") || name.endsWith(".SKT")
					|| name.endsWith(".skm") || name.endsWith(".SKM")
					|| name.endsWith(".skp") || name.endsWith(".SKP")
					|| name.endsWith(".skd") || name.endsWith(".SKD")
					|| name.endsWith(".ppz") || name.endsWith(".PPZ")
					|| name.endsWith(".set") || name.endsWith(".SET")
					|| name.endsWith(".pre") || name.endsWith(".PRE")
					|| name.endsWith(".prt") || name.endsWith(".PRT")
					|| name.endsWith(".ps") || name.endsWith(".PS")
					|| name.endsWith(".psd") || name.endsWith(".PSD")
					|| name.endsWith(".jcm") || name.endsWith(".JCM")
					|| name.endsWith(".ivy") || name.endsWith(".IVY")
					|| name.endsWith(".latex") || name.endsWith(".LATEX")
					|| name.endsWith(".lha") || name.endsWith(".LHA")
					|| name.endsWith(".lhx") || name.endsWith(".LHX")
					|| name.endsWith(".man") || name.endsWith(".MAN")
					|| name.endsWith(".map") || name.endsWith(".MAP")
					|| name.endsWith(".ltx") || name.endsWith(".ITX")
					|| name.endsWith(".lzh") || name.endsWith(".LZH")
					|| name.endsWith(".lzx") || name.endsWith(".LZX")
					|| name.endsWith(".mdb") || name.endsWith(".MDB")
					|| name.endsWith(".mc$") || name.endsWith(".MC$")
					|| name.endsWith(".mcd") || name.endsWith(".MCD")
					|| name.endsWith(".mcp") || name.endsWith(".MCP")
					|| name.endsWith(".me") || name.endsWith(".ME")
					|| name.endsWith(".rng") || name.endsWith(".RNG")
					|| name.endsWith(".rnx") || name.endsWith(".RNX")
					|| name.endsWith(".roff") || name.endsWith(".ROFF")
					|| name.endsWith(".saveme") || name.endsWith(".SAVEME")
					|| name.endsWith(".sbk") || name.endsWith(".SBK")) {
				
				holder.img_file.setBackgroundResource(R.drawable.ic_application);

			} else {
				holder.img_file.setBackgroundResource(R.drawable.ic_list);
			}

			holder.id = pos;
			return view;
		}
	}



	public static File readBytes(InputStream inputStream, String file)
			throws IOException {
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			byteBuffer.write(buffer, 0, len);
		}
		byte[] bytes = byteBuffer.toByteArray();

		// below is the different part
		File someFile = new File(file);
		FileOutputStream fos = new FileOutputStream(someFile);
		fos.write(bytes);
		fos.flush();
		fos.close();

		return someFile;
	}

	private void expected() {
		d.dismiss();
		toastsettext("Uploaded Successfully");
		initImageLoader();
		init(root);
		sd_list.clear();
		sd_adapter.getSelected().clear();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (stng_stay != null && stng_stay.contentEquals("fold")) {
			setResult(RESULT_OK);
			finish();
		} else if (stng_enter != null && stng_enter.contentEquals("enter")) {
			setResult(RESULT_OK);
			finish();
			Intent intent = new Intent(this, Folder_LoginActivity.class);
			Bundle bundle = new Bundle();
			bundle.putBoolean("ran", false);
			bundle.putString("back", "false");
			bundle.putString("fld_id", Constant.con_fldid);
			bundle.putString("fld_nm", Constant.con_fldnm);
			bundle.putStringArrayList("array_id", Constant.con_list);
			bundle.putStringArrayList("array_nm", Constant.con_name);
			intent.putExtras(bundle);
			startActivityForResult(intent, 111);
		} else {
			if (Constant.con_fldid == null
					|| Constant.con_fldid.contentEquals("exit")) {
				setResult(RESULT_OK);
				finish();
			} else {
				setResult(RESULT_OK);
				finish();
				Intent intent = new Intent(this, Folder_LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("ran", false);
				bundle.putString("back", "false");
				bundle.putString("fld_id", Constant.con_fldid);
				bundle.putString("fld_nm", Constant.con_fldnm);
				bundle.putStringArrayList("array_id", Constant.con_list);
				bundle.putStringArrayList("array_nm", Constant.con_name);
				intent.putExtras(bundle);
				startActivityForResult(intent, 111);
			}
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

	private void stack() {
		all_path = null;
		all_path = new ArrayList<String>();
		all_path.clear();
		ArrayList<SdAdapters> selected = null;
		selected = sd_adapter.getSelected();
		for (int i = 0; i < selected.size(); i++) {
			all_path.add(selected.get(i).sdcardPath.toString());
			Log.d("all_path",
					String.valueOf(selected.get(i).sdcardPath.toString()));
		}
		if (new_fold) {
			new_fold = false;
			if (all_path.size() >= 1) {
				dialog();
			} else {
				initImageLoader();
				init(root);
				toastsettext("Select anyone to upload Whootin Files");
			}
		} else {
			re_path = null;
			dlt_path = null;
			fresh_path = null;
			re_path = new ArrayList<String>();
			dlt_path = new ArrayList<String>();
			fresh_path = new ArrayList<String>();
			ArrayList<HashMap<String, String>> contact = Constant.db
					.getAllTranslates(Files_Db.Table, bool);

			if (all_path != null && all_path.size() > 0) {
				for (int i = 0; i < all_path.size(); i++) {
					String removal = all_path.get(i).toString();
					File remove = new File(removal);
					String red = remove.getName();
					for (int j = 0; j < contact.size(); j++) {
						String fld = contact.get(j).get(Files_Db.File_name);
						String flid = contact.get(j).get(Files_Db.File_id);
						if (red.contentEquals(fld)) {
							Log.d("s-2", red);
							re_path.add(red);
							dlt_path.add(flid);
							fresh_path.add(removal);
							Log.d("re_path", removal + red);
						}
					}
				}

				if (re_path.size() == 0) {
					dialog();
				} else {
					final TextView alert1;
					final Button yes_btn, no_btn;
					AlertDialog.Builder build_dialog;
					final AlertDialog alert_dialog;
					final ListView lv;

					build_dialog = new AlertDialog.Builder(context);
					LayoutInflater inflater = (LayoutInflater) context
							.getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.altdialog_exited,
							(ViewGroup) findViewById(R.id.exed_sample));
					no_btn = (Button) layout.findViewById(R.id.exed_btn_cancel);
					yes_btn = (Button) layout.findViewById(R.id.exed_btn_ok);
					alert1 = (TextView) layout.findViewById(R.id.exed_txt_hd);
					lv = (ListView) layout.findViewById(R.id.exed_lv);
					build_dialog.setView(layout);
					alert_dialog = build_dialog.create();
					alert_dialog.setView(layout, 0, 0, 0, 0);

					if (re_path.size() == 1) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								context, android.R.layout.simple_list_item_1,
								re_path);
						lv.setAdapter(adapter);
					} else if (re_path.size() >= 2) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								context, android.R.layout.simple_list_item_1,
								re_path);
						lv.setAdapter(adapter);
					}

					yes_btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dlt = true;
							alert_dialog.dismiss();
							if (prg_bar.isShowing()) {

							} else {
								prg_bar.show();
							}
							d_delete();
						}

					});

					no_btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (all_path != null) {
								for (int x = 0; x < fresh_path.size(); x++) {
									String get = fresh_path.get(x);
									all_path.remove(get);
								}
								Log.d("get", String.valueOf(all_path.size()));
								if (all_path.size() >= 1) {
									dialog();
								} else {
									initImageLoader();
									init(root);
									toastsettext("Select anyone to upload Whootin Files");
								}
							}
							alert_dialog.dismiss();
						}
					});

					alert1.setMovementMethod(new ScrollingMovementMethod());
					alert_dialog.show();
					alert1.setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {
							if (v.getId() == R.id.exed_txt_hd) {
								v.getParent()
										.requestDisallowInterceptTouchEvent(
												true);
								switch (event.getAction()
										& MotionEvent.ACTION_MASK) {
								case MotionEvent.ACTION_UP:
									v.getParent()
											.requestDisallowInterceptTouchEvent(
													false);
									break;
								}
							}
							return false;
						}
					});
				}
			} else {
				toastsettext("Select anyone to upload Whootin Files");
			}
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

	private void unexpected(String result) {
		Log.d("Size of listview", String.valueOf(all_path.size()));
		all_path.remove(result);
		re_contactListpg.clear();
		for (h = 0; h < all_path.size(); h++) {
			HashMap<String, String> map = new HashMap<String, String>();
			String fl = all_path.get(h).toString();
			map.put("name", fl);
			re_contactListpg.add(map);
		}
		Log.e("from response finish", "finish");
		list_adaptpgm = new SimAdapteredpg(getApplicationContext(),
				re_contactListpg);
		lv_pg.setAdapter(list_adaptpgm);
		Log.d("Size of listview", String.valueOf(all_path.size()));
		if (all_path.size() == 0) {
			expected();
			Log.d("first", "third");
		} else {
			int h = 0;
			if (bo_pg) {
				bo_pg = false;
				for (h = 0; h < all_path.size(); h++) {
					File fine = new File(all_path.get(h).toString());
					txt_pg.setText(fine.getName().toString());
					new download2().execute(all_path.get(h).toString());
					Log.d("first", "1");
					h = all_path.size();
				}
			} else {
				for (h = 0; h < all_path.size(); h++) {
					File fine = new File(all_path.get(h).toString());
					txt_pg.setText(fine.getName().toString());
					new download1().execute(all_path.get(h).toString());
					Log.d("first", "1");
					h = all_path.size();
				}
			}
		}

	}

	
	@SuppressWarnings("unused")
	private void dialog() {
		List<String> removed_path = new ArrayList<String>();
		
		if (Constant.isInternetPresent) {

			Constant.datee = getSharedPreferences("datefun",
					MODE_WORLD_WRITEABLE);
			if (Constant.datee != null
					&& Constant.datee.contains("access_token")) {

				Constant.accesstoken = "Bearer "
						+ Constant.datee.getString("access_token", null);
				Log.d("login", Constant.accesstoken);

				if (prg_bar.isShowing()) {
					prg_bar.setCancelable(true);
					prg_bar.dismiss();
					prg_bar.cancel();
				}
				bo_pg = true;

				re_contactListpg.clear();
				int h = 0;
				boolean boo_vde = true;

				for (h = 0; h < all_path.size(); h++) {
					HashMap<String, String> map = new HashMap<String, String>();
					String fl = all_path.get(h).toString();
					for (int i = 0; i < Constant.vid_tit.length; i++) {
						if (all_path.get(h).toString().contains(Constant.vid_tit[i])) {
							boo_vde = false;
							File img = new File(fl);
							long length = img.length();
							Log.d("yy", String.valueOf(length));
							double index = (double) length / (1024 * 1024);
							String val = String.valueOf(index);
							Log.d("yy", String.valueOf(val));
							String[] values = val.split("\\.");
							int max = Integer.valueOf(values[0]);
							Log.d("yy", String.valueOf(max));
							if (max <= 10) {
								Log.d("added", "added");
								map.put("name", fl);
								re_contactListpg.add(map);
							} else {
								removed_path.add(fl);
								Log.d("removed", "removed");
								toastsettext(img.getName()
										+ " file could not be added. Upload Video file upto 10MB");
							}
						}
					}

					if (boo_vde) {
						File img = new File(all_path.get(h).toString());
						long length = img.length();
						Log.d("yy", String.valueOf(length));
						double index = (double) length / (1024 * 1024);
						String val = String.valueOf(index);
						Log.d("yy", String.valueOf(val));
						String[] values = val.split("\\.");
						int max = Integer.valueOf(values[0]);
						Log.d("yy", String.valueOf(max));
						if (max <= 25) {
							Log.d("added below", "added below");
							map.put("name", fl);
							re_contactListpg.add(map);
						} else {
							removed_path.add(fl);
							Log.d("removed all", "removed all");
							toastsettext(img.getName()
									+ " file could not be added. Upload file upto 25MB");
						}
					}
				}

				Log.d("yy", String.valueOf(all_path.size()));
				if (removed_path.size() > 0) {
					for (h = 0; h < removed_path.size(); h++) {
						String fl = removed_path.get(h).toString();
						all_path.remove(fl);
					}
				}
				Log.d("yy", String.valueOf(all_path.size()));

				if (re_contactListpg.size() > 0) {

					d = new Dialog(this,
							android.R.style.Theme_Translucent_NoTitleBar);
					LayoutInflater inflter = getLayoutInflater();
					View v = inflter.inflate(R.layout.altdialog_prgdilg, null);

					txt_pg = (TextView) v.findViewById(R.id.pg_txt);
					lv_pg = (ListView) v.findViewById(R.id.pg_lv);
					txt_pgmssg = (TextView) v.findViewById(R.id.pg_text);
					txt_pgsucs = (TextView) v.findViewById(R.id.pg_path);
					seekbar_pg = (SeekBar) v.findViewById(R.id.pg_seekbar);

					d.setContentView(v);
					d.show();
					list_adaptpgm = new SimAdapteredpg(getApplicationContext());
					list_adaptpgm = new SimAdapteredpg(getApplicationContext(),
							re_contactListpg);
					lv_pg.setAdapter(list_adaptpgm);

					String y = null, z = null;

					if (Constant.con_name.size() > 0) {
						Log.d("size of new_list",
								String.valueOf(Constant.con_name.size())
										+ " "
										+ String.valueOf(Constant.con_list
												.size()));
						for (int i = 0; i < Constant.con_name.size(); i++) {
							y = Constant.con_name.get(i);
							if (y.contentEquals("  Home  ")) {
								y = "Home --> ";
							}
							Log.d("z1", y);
							if (z == null) {
								z = y;
							} else {
								Log.d("z", z);
								z = z + y + " / ";
							}
						}
						if (z != null) {
							txt_pgsucs.setText(z);
						}
					} else {
						txt_pgsucs.setText("Home  -->  ");
					}

					for (h = 0; h < all_path.size(); h++) {
						File fine = new File(all_path.get(h).toString());
						txt_pg.setText(fine.getName().toString());
						new download1().execute(all_path.get(h).toString());
						Log.d("first", "1");
						h = all_path.size();
					}
				} else {
					if (prg_bar.isShowing()) {
						prg_bar.setCancelable(true);
						prg_bar.cancel();
						prg_bar.dismiss();
					}
					seekbar_pg.setVisibility(View.GONE);
					txt_pg.setVisibility(View.GONE);
					txt_pgmssg.setVisibility(View.GONE);
					txt_pgsucs.setVisibility(View.GONE);
					d.dismiss();
					sd_list.clear();
					sd_adapter.getSelected().clear();
					initImageLoader();
					init(root);
					toastsettext("Select anyone to upload Whootin Files");
				}
			} else {
				toastsettext("No Internet Connection");
			}
		}

	}

	public class download1 extends AsyncTask<String, Integer, String> {
		boolean isTrue = true;
		int k = 0;
		String get_url = null;

		@Override
		protected void onProgressUpdate(Integer... values) {
			seekbar_pg.setProgress(values[0]);
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			k = 0;
			get_url = params[0];
			Log.d("i", get_url);
			Constant.con_fldnm = folder_name;

			boolean boo_vde = true;
			for (int i = 0; i < Constant.vid_tit.length; i++) {
				if (get_url.contains(Constant.vid_tit[i])) {
					boo_vde = false;
				}
			}
			try {
				if (Constant.con_fldid == null
						|| Constant.con_fldid.contentEquals("exit")) {
					whootin_multi_post(get_url);
				} else {
					whootin_multi_post_folder(get_url);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (isTrue) {
				publishProgress(k++);
				try {
					if (boo_vde) {
						Thread.sleep(100);
					} else {
						Thread.sleep(300);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (k > 100) {
					isTrue = false;
				}
			}

			return get_url;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("first", "second");
			if (all_path.size() == 0) {
				expected();
				Log.d("first", "third");
			} else {
				unexpected(result);
				Log.d("first", "four");
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

	}

	public class download2 extends AsyncTask<String, Integer, String> {
		boolean isTrue = true;
		int k = 0;
		String get_url = null;

		@Override
		protected void onProgressUpdate(Integer... values) {
			seekbar_pg.setProgress(values[0]);
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			k = 0;
			get_url = params[0];
			Log.d("i", get_url);
			Constant.con_fldnm = folder_name;

			boolean boo_vde = true;
			for (int i = 0; i < Constant.vid_tit.length; i++) {
				if (get_url.contains(Constant.vid_tit[i])) {
					boo_vde = false;
				}
			}
			try {
				if (Constant.con_fldid == null
						|| Constant.con_fldid.contentEquals("exit")) {
					whootin_multi_post(get_url);
				} else {
					whootin_multi_post_folder(get_url);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (isTrue) {
				publishProgress(k++);
				try {
					if (boo_vde) {
						Thread.sleep(100);
					} else {
						Thread.sleep(300);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (k > 100) {
					isTrue = false;
				}
			}

			return get_url;
		}

		@Override
		protected void onPostExecute(String result) {

			Log.d("first", "second");
			if (all_path.size() == 0) {
				expected();
				Log.d("first", "third");
			} else {
				unexpected(result);
				Log.d("first", "four");
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	private void whootin_multi_post_folder(String fold) throws IOException {

		boolean boo_vde = true;
		for (int i = 0; i < Constant.vid_tit.length; i++) {
			if (fold.contains(Constant.vid_tit[i])) {
				boo_vde = false;
				try {
					File img = new File(fold);
					InputStream is = new FileInputStream(img);
					imgtemp = readBytes(is, fold);

					RequestParams params = new RequestParams();
					try {
						Constant.con_fldnm = folder_name;
						params.put("folder_id", Constant.con_fldid);
						params.put("file", imgtemp);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}

					AsyncHttpClient client = new AsyncHttpClient();
					client.setTimeout(8000);
					client.addHeader("Content-type", "video/mp4");
					client.addHeader("Content-type",
							"application/x-www-form-urlencoded; charset=utf-8");
					client.addHeader("Accept", "appliction/json");
					client.addHeader("Content-Transfer-Encoding", "binary");
					client.addHeader("Authorization", Constant.accesstoken);
					client.post("http://whootin.com/api/v1/files/new.json",
							params, new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(String arg0) {
									super.onSuccess(arg0);
									Log.e("from response success", arg0);
									h = 1;
									Log.d("from success", "from success");
								}

								@Override
								public void onFinish() {
									super.onFinish();
									Log.d("from finish", "from finish");
									imgtemp = null;
								}

								@Override
								public void onFailure(Throwable arg0,
										String arg1) {

									if (arg0.getCause() instanceof ConnectTimeoutException) {
										toastsettext("Connection timeout !");
									}
									Log.d("argo", arg1);
									super.onFailure(arg0, arg1);
								}

							});

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		if (boo_vde) {
			imgtemp = new File(fold);

			RequestParams params = new RequestParams();
			try {
				Constant.con_fldnm = folder_name;
				params.put("folder_id", Constant.con_fldid);
				params.put("file", imgtemp);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			AsyncHttpClient client = new AsyncHttpClient();
			client.setTimeout(5000);
			client.addHeader("Content-type",
					"application/x-www-form-urlencoded; charset=utf-8");
			client.addHeader("Accept", "appliction/json");
			client.addHeader("Content-Transfer-Encoding", "binary");
			client.addHeader("Authorization", Constant.accesstoken);
			client.post("http://whootin.com/api/v1/files/new.json", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String arg0) {
							super.onSuccess(arg0);
							Log.e("from response success", arg0);
							h = 1;
							Log.d("from success", "from success");
						}

						@Override
						public void onFinish() {
							super.onFinish();
							Log.d("from finish", "from finish");
							imgtemp = null;
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {

							if (arg0.getCause() instanceof ConnectTimeoutException) {
								toastsettext("Connection timeout !");
							}
							Log.d("argo", arg1);
							super.onFailure(arg0, arg1);
						}

					});

		}

	}

	private void whootin_multi_post(String fold) throws IOException {

		boolean boo_vde = true;
		for (int i = 0; i < Constant.vid_tit.length; i++) {
			if (fold.contains(Constant.vid_tit[i])) {
				boo_vde = false;
				try {
					File img = new File(fold);
					InputStream is = new FileInputStream(img);
					imgtemp = readBytes(is, fold);
					RequestParams params = new RequestParams();
					try {
						params.put("file", imgtemp);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					AsyncHttpClient client = new AsyncHttpClient();
					client.setTimeout(8000);
					client.addHeader("Content-type", "video/mp4");
					client.addHeader("Content-type",
							"application/x-www-form-urlencoded; charset=utf-8");
					client.addHeader("Accept", "appliction/json");
					client.addHeader("Content-Transfer-Encoding", "binary");
					client.addHeader("Authorization", Constant.accesstoken);
					client.post("http://whootin.com/api/v1/files/new.json",
							params, 
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(String arg0) {
									super.onSuccess(arg0);
									Log.e("from response success", arg0);
									h = 1;
									Log.d("from success", "from success");
								}

								@Override
								public void onFinish() {
									super.onFinish();
									Log.d("from finish", "from finish");
									imgtemp = null;
								}

								@Override
								public void onFailure(Throwable arg0,
										String arg1) {

									if (arg0.getCause() instanceof ConnectTimeoutException) {
										toastsettext("Connection timeout !");
									}
									super.onFailure(arg0, arg1);
								}

							});

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		if (boo_vde) {
			imgtemp = new File(fold);
			RequestParams params = new RequestParams();
			try {
				params.put("file", imgtemp);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			AsyncHttpClient client = new AsyncHttpClient();
			client.setTimeout(5000);
			client.addHeader("Content-type",
					"application/x-www-form-urlencoded; charset=utf-8");
			client.addHeader("Accept", "appliction/json");
			client.addHeader("Content-Transfer-Encoding", "binary");
			client.addHeader("Authorization", Constant.accesstoken);
			client.post("http://whootin.com/api/v1/files/new.json", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String arg0) {
							super.onSuccess(arg0);
							Log.e("from response success", arg0);
							h = 1;
							Log.d("from success", "from success");
						}

						@Override
						public void onFinish() {
							super.onFinish();
							Log.d("from finish", "from finish");
							imgtemp = null;
						}

						@Override
						public void onFailure(Throwable arg0, String arg1) {

							if (arg0.getCause() instanceof ConnectTimeoutException) {
								toastsettext("Connection timeout !");
							}
							super.onFailure(arg0, arg1);
						}

					});
		}

	}

}
