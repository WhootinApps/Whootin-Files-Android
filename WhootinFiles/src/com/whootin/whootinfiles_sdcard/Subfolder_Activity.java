package com.whootin.whootinfiles_sdcard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.devsmart.android.ui.HorizontalListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.whootin.wf.Folder_LoginActivity;
import com.whootin.wf.Login_Activity;
import com.whootin.wf.R;
import com.whootin.whootinfiles_db.ConnectionDetector;
import com.whootin.whootinfiles_db.Constant;
import com.whootin.whootinfiles_db.Files_Db;
import com.whootin.whootinfiles_gallery.CustomGalleryAct;
import com.whootin.whootinfiles_sdcard.Sd_Folder.SimAdapteredpg;
import com.whootin.whootinfiles_sdcard.Sd_Folder.download1;
import com.whootin.whootinfiles_sdcard.Sd_Folder.download2;
import com.whootin.wf.Folder_LoginActivity.download;
import com.whootin.wf.Login_Activity.SimAdaptered;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class Subfolder_Activity extends Activity implements OnClickListener {

	private HorizontalListView lv_hsub;



	private boolean boo_sce = false, boo_back_new = false, boo_fold = false,
			boo_main = false, dlt_bln = false, boo_item = false, boo_up,
			boo_items = false;
	private String get_fold = null, get_boo = null, boo_stay = null;

	private Button free_ok1, free_ok2, free_back1, free_done1;
	private Button free_upload1, free_cancel1;
	private List<String> fresh_path = null;
	private List<String> re_path = null;
	private List<String> dlt_path = null;
	private boolean common = false, dlt = false;
	private ArrayList<String> all_path = null;
	public File imgtemp = null;
	private boolean ran = false, new_fold = false, boo_dlt = false;
	private String list = null;
	private Boolean bool = false;
	public WakeLock wakeLock;
	private Context context;
	private ListAdapter list_adapt_fld;
	private int fld_pos = 0;
	private Boolean boo_back = false, back_pressed = false, boo_upld = false;
	private ProgressDialog prg_bar;
	private String Table_name = null, folder_name = null, folder_url,
			fld_fetc = null, dlt_stng = null;
	private ListView free_lv, lv_pg;
	private TextView free_txt, txt_pg, txt_pgmssg, txt_pgsucs;
	private int h = 0;
	private SeekBar seekbar_pg;
	private Dialog d;
	private ListAdapter list_adaptpgm;
	private ArrayList<HashMap<String, String>> re_contactListpg = new ArrayList<HashMap<String, String>>();
	private boolean bo_pg = false;
	private ArrayList<HashMap<String, String>> contactList_fld = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> re_contactList = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> re_contactList2 = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> order1 = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> order2 = new ArrayList<HashMap<String, String>>();
	
	private String dlt_id = null, fld_typ = null, fld_url = null,
			fld_nm = null;

	private String get = null, stng_enter = null;

	private RelativeLayout rl_trans, rl_subbottom2, rl_subbottom1;

	@Override
	public void onBackPressed() {

		if (Constant.con_list != null && Constant.con_list.size() > 0) {
			if (Constant.con_list.size() == 1) {
				int dex = Constant.con_list.size() - 1;
				Log.d("dex", String.valueOf(Constant.con_list.size()));
				String folder = String.valueOf(Constant.con_list.get(dex));
				String folder_name = String.valueOf(Constant.con_name.get(dex));
				Log.d("dex", String.valueOf(Constant.con_list.get(dex) + dex));
				if (folder.contentEquals("exit")) {
					setResult(RESULT_OK);
					finish();
				} else {
					new download(folder_name).execute(folder);
					Constant.con_fldid = folder;
					Constant.con_fldnm = folder_name;
					boo_back = false;
					back_pressed = true;
				}
			} else {
				int dex = Constant.con_list.size() - 2;
				Log.d("dex", String.valueOf(Constant.con_list.size()));
				String folder = String.valueOf(Constant.con_list.get(dex));
				String folder_name = String.valueOf(Constant.con_name.get(dex));
				Log.d("dex", String.valueOf(Constant.con_list.get(dex) + dex));
				if (folder.contentEquals("exit")) {
					setResult(RESULT_OK);
					finish();
				} else {
					new download(folder_name).execute(folder);
					Constant.con_fldid = folder;
					Constant.con_fldnm = folder_name;
					boo_back = false;
					back_pressed = true;
				}
			}
		} else {
			super.onBackPressed();
			setResult(RESULT_OK);
			finish();
			ran = false;

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

	public void deleteAll(String table1) {
		Constant.db_sql = Constant.db.getWritableDatabase();
		Constant.db_sql.delete(table1, null, null);
		Constant.db_sql.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (free_txt.getText().length() > 0) {
			free_txt.setText("");
		}
		Constant.pass = getSharedPreferences("fun", MODE_WORLD_WRITEABLE);
		String ups = null;
		if (Constant.pass != null && Constant.pass.contains("up")) {
			ups = Constant.pass.getString("up", "");
		}

		if (boo_main) {
			boo_main = false;
		} else {
			Constant.cd = new ConnectionDetector(context);
			Constant.isInternetPresent = Constant.cd.isConnectingToInternet();
			if (Constant.isInternetPresent) {
				dlt_bln = true;
				new download(Constant.con_fldnm).execute(Constant.con_fldid);
				boo_back = true;
			} else {
				toastsettext("No Internet Connection");
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subfolder);
		context = this;
		Bundle bndl = getIntent().getExtras();
		stng_enter = bndl.getString("enter");

		boo_up = bndl.getBoolean("boo_up");
		get_boo = bndl.getString("new_fold");
		boo_stay = bndl.getString("new_fold");

		if (String.valueOf(boo_up) != null) {
		}

		list = bndl.getString("list");
		boo_item = bndl.getBoolean("item");
		boo_items = bndl.getBoolean("item");
		all_path = bndl.getStringArrayList("array");
		String _name = bndl.getString("fld_nm");
		String _id = bndl.getString("fld_id");
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
			Log.d("size of new_list", String.valueOf(Constant.con_list.size()));
		}

		ArrayList<String> new_named = bndl.getStringArrayList("array_nm");
		if (new_named != null && new_named.size() > 0) {
			Constant.con_name = new_named;
			Log.d("size of new_named", String.valueOf(Constant.con_name.size()));
		}

		findViewById();
		declaration();
		Constant.con_booup = false;
		Table_name = Files_Db.Table;

	}

	private BaseAdapter mAdapter = new BaseAdapter() {

		@Override
		public int getCount() {
			return Constant.con_name.size();
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
			View retval = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.inflate_hlv, null);
			TextView title = (TextView) retval.findViewById(R.id.hlv_title);
			ImageView fld_img = (ImageView) retval.findViewById(R.id.hlv_fld);
			ImageView arrow = (ImageView) retval.findViewById(R.id.hlv_next);

			if (Constant.con_name.size() == 1) {
				if (position == 0) {
					arrow.setVisibility(View.GONE);
					fld_img.setBackgroundResource(R.drawable.h_home);
				}
			} else if (Constant.con_name.size() == 2) {
				if (position == 0) {
					arrow.setVisibility(View.VISIBLE);
					fld_img.setBackgroundResource(R.drawable.h_home);
				} else if (position == 1) {
					arrow.setVisibility(View.GONE);
					fld_img.setBackgroundResource(R.drawable.h_ofld);
				}
			} else {
				if (position == 0) {
					arrow.setVisibility(View.VISIBLE);
					fld_img.setBackgroundResource(R.drawable.h_home);
				} else if (position < (getCount() - 1)) {
					arrow.setVisibility(View.VISIBLE);
					fld_img.setBackgroundResource(R.drawable.h_fld);
				} else {
					arrow.setVisibility(View.GONE);
					fld_img.setBackgroundResource(R.drawable.h_ofld);
				}
			}

			if(Constant.con_name.get(position).contentEquals("  Home  ")){
				title.setText("Home");
			}else{
				title.setText(Constant.con_name.get(position));
			}

			return retval;
		}

	};

	private void declaration() {

		free_txt.addTextChangedListener(new TextWatcher() {
			Integer textlength1 = 0;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textlength1 = free_txt.getText().length();
				if (textlength1 >= 1) {
					rl_trans.setVisibility(View.VISIBLE);
				}
				if (textlength1 == 0) {
					rl_trans.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (textlength1 >= 1) {
					rl_trans.setVisibility(View.VISIBLE);
				}
				if (textlength1 == 0) {
					rl_trans.setVisibility(View.GONE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (textlength1 >= 1) {
					rl_trans.setVisibility(View.VISIBLE);
				}
				if (textlength1 == 0) {
					rl_trans.setVisibility(View.GONE);
				}
			}
		});

		lv_hsub.setAdapter(mAdapter);

		lv_hsub.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				int tot = position + 1;
				ArrayList<String> _list = new ArrayList<String>(tot);
				ArrayList<String> _name = new ArrayList<String>(tot);
				Log.d("selected item", String.valueOf(_list.size()));
				for (int i = 0; i < tot; i++) {
					String folder = String.valueOf(Constant.con_list.get(i));
					String folder_name = String.valueOf(Constant.con_name
							.get(i));
					_list.add(folder);
					_name.add(folder_name);
				}
				Log.d("selected item", String.valueOf(_list.size()));
				Constant.con_list = _list;
				Constant.con_name = _name;
				Log.d("selected item", String.valueOf(Constant.con_list.size()));

				String folder = String.valueOf(Constant.con_list.get(position));
				String folder_name = Constant.con_name.get(position);
				if (folder.contentEquals("exit")
						|| folder.contentEquals("item")) {
					setResult(RESULT_OK);
					finish();
				} else {
					new download(folder_name).execute(folder);
				}
				Constant.con_fldid = folder;
				Constant.con_fldnm = folder_name;
				dlt_bln = true;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		prg_bar = new ProgressDialog(Subfolder_Activity.this);
		prg_bar.setMessage("please wait...");
		prg_bar.setIndeterminate(false);
		prg_bar.setCancelable(false);
		prg_bar.setMax(100);
		prg_bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		free_back1.setOnClickListener(this);
		free_ok1.setOnClickListener(this);
		free_ok2.setOnClickListener(this);
		free_cancel1.setOnClickListener(this);
		free_done1.setOnClickListener(this);
		free_upload1.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v == free_back1) {

			if (free_txt.getText().length() > 0) {
				free_txt.setText("");
			}

			if (Constant.con_list != null && Constant.con_list.size() > 0) {
				if (Constant.con_list.size() == 1) {
					int dex = Constant.con_list.size() - 1;
					Log.d("dex", String.valueOf(Constant.con_list.size()));
					String folder = String.valueOf(Constant.con_list.get(dex));
					String folder_name = String.valueOf(Constant.con_name
							.get(dex));
					if (folder.contentEquals("exit")) {
						setResult(RESULT_OK);
						finish();
					} else {
						new download(folder_name).execute(folder);
						Constant.con_fldid = folder;
						Constant.con_fldnm = folder_name;
						boo_back = false;
						back_pressed = true;
					}

				} else {
					int dex = Constant.con_list.size() - 2;
					Log.d("dex", String.valueOf(Constant.con_list.size()));
					String folder = String.valueOf(Constant.con_list.get(dex));
					String folder_name = String.valueOf(Constant.con_name
							.get(dex));
					Log.d("dex",
							String.valueOf(Constant.con_list.get(dex) + dex));
					if (folder.contentEquals("exit")) {
						setResult(RESULT_OK);
						finish();
					} else {
						new download(folder_name).execute(folder);
						Constant.con_fldid = folder;
						Constant.con_fldnm = folder_name;
						boo_back = false;
						back_pressed = true;
					}
				}
			} else {
				setResult(RESULT_OK);
				finish();
				ran = false;
			}
		} else if (v == free_ok1) {
			boo_upld = true;
			Constant.hideSoftKeyboard(Subfolder_Activity.this, v);
			folder_name = free_txt.getText().toString();
			if (folder_name.length() != 0) {
				check(folder_name);
				rl_trans.setVisibility(View.GONE);
			} else {
				toastsettext("Enter FolderName");
			}
			free_txt.setText("");
		} else if(v == free_ok2) {
			rl_subbottom1.setVisibility(View.VISIBLE);
			rl_subbottom2.setVisibility(View.GONE);	
		} else if (v == free_upload1) {

			if (all_path == null) {
				gallery();
			} else {
				if (all_path.size() > 0) {
					boo_upld = true;
					Constant.hideSoftKeyboard(Subfolder_Activity.this, v);
					stack();
					rl_trans.setVisibility(View.GONE);
				} else {
					gallery();
				}
			}
		} else if (v == free_done1) {
			if (free_txt.getText().length() > 0) {
				free_txt.setText("");
			}
			Intent i = new Intent();
			setResult(RESULT_CANCELED, i);
			finish();
		} else if (v == free_cancel1){
			rl_subbottom1.setVisibility(View.GONE);
			rl_subbottom2.setVisibility(View.VISIBLE);
		}

	};

	private void gallery() {

		final Button gallery, sd_card;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_select,
				(ViewGroup) findViewById(R.id.select_sample));
		gallery = (Button) layout.findViewById(R.id.select_cncl);
		sd_card = (Button) layout.findViewById(R.id.select_lgt);
		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		sd_card.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				setResult(RESULT_OK);
				finish();
				Intent intent = new Intent(Subfolder_Activity.this,
						Sd_Folder.class);
				Bundle bun = new Bundle();
				bun.putStringArrayList("array_id", Constant.con_list);
				bun.putStringArrayList("array_nm", Constant.con_name);
				bun.putString("fld_id", Constant.con_fldid);
				bun.putString("fld_nm", Constant.con_fldnm);
				intent.putExtras(bun);
				startActivityForResult(intent, 77);
			}
		});

		gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				setResult(RESULT_OK);
				finish();
				Intent intent = new Intent(Subfolder_Activity.this,
						CustomGalleryAct.class);
				Bundle bun = new Bundle();
				bun.putStringArrayList("array_id", Constant.con_list);
				bun.putStringArrayList("array_nm", Constant.con_name);
				bun.putString("fld_id", Constant.con_fldid);
				bun.putString("fld_nm", Constant.con_fldnm);
				intent.putExtras(bun);
				startActivityForResult(intent, 77);
			}
		});

		alert_dialog.show();

	}

	private void findViewById() {
		free_cancel1 = (Button) findViewById(R.id.sub_cancel_btn);
		rl_subbottom2 = (RelativeLayout) findViewById(R.id.subbottom2);
		rl_subbottom2.setVisibility(View.GONE);
		rl_subbottom1 = (RelativeLayout) findViewById(R.id.sub_bottom);
		rl_subbottom1.setVisibility(View.VISIBLE);
		rl_trans = (RelativeLayout) findViewById(R.id.sub_trans);
		lv_hsub = (HorizontalListView) findViewById(R.id.sub_hlistview);
		free_back1 = (Button) findViewById(R.id.sub_back);
		free_ok1 = (Button) findViewById(R.id.sub_ok_btn);
		free_ok2 = (Button) findViewById(R.id.sub_ok_btnnew);
		free_done1 = (Button) findViewById(R.id.sub_done);
		free_upload1 = (Button) findViewById(R.id.sub_upload_btn);
		free_txt = (TextView) findViewById(R.id.sub_txt_txt);
		free_lv = (ListView) findViewById(R.id.sub_lv);
	}

	public class SimAdapter extends BaseAdapter {

		Context sim_context;
		LayoutInflater inflater;
		ArrayList<HashMap<String, String>> stg_list;
		int pos = 0;
		String folder = null, name = null, size = null, size1 = null,
				date = null;

		public SimAdapter(Context context,
				ArrayList<HashMap<String, String>> gallery_list) {
			this.sim_context = context;
			this.stg_list = gallery_list;
			Log.d("MAS", "LIST1");

		}

		public SimAdapter(Context applicationContext) {
			this.sim_context = applicationContext;
		}

		@Override
		public int getCount() {
			return stg_list.size();
		}

		@Override
		public Object getItem(int position) {
			return stg_list.get(position);

		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			TextView txt_nm, txt_sz;
			Button btn_quick;
			ImageView img_file;
			LinearLayout two;
			int id;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			final ViewHolder holder;
			pos = position;
			if (view == null && stg_list.size() != 0) {
				inflater = (LayoutInflater) sim_context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.twotextt, null);
				holder = new ViewHolder();
				holder.two = (LinearLayout) view.findViewById(R.id.two_text);
				holder.txt_nm = (TextView) view.findViewById(R.id.list_nmtxt);
				holder.txt_sz = (TextView) view.findViewById(R.id.list_sztxt);
				holder.btn_quick = (Button) view.findViewById(R.id.list_szbtn);
				holder.img_file = (ImageView) view.findViewById(R.id.list_img);

				holder.btn_quick.setVisibility(View.GONE);

				view.setTag(holder);

			} else {
				holder = (ViewHolder) view.getTag();
			}

			/** To set id to given ListView of Objects */
			holder.txt_nm.setId(pos);
			holder.txt_sz.setId(pos);
			holder.btn_quick.setId(pos);
			holder.img_file.setId(pos);
			holder.two.setId(pos);

			/** To set date of file to ListView */
			String dumy_date = stg_list.get(pos).get(Files_Db.File_createat);
			Log.d("dumy", dumy_date);
			String[] stng_date = dumy_date.split("T");
			String[] stng_date_split = stng_date[0].split("-");
			date = stng_date_split[2] + "-" + stng_date_split[1] + "-"
					+ stng_date_split[0];
			String[] stng_hour_split = stng_date[1].split(":");
			String three = stng_hour_split[2];
			three = three.replace("Z", "");
			Log.d("3", three);
			holder.txt_sz.setText(date);
			String dayss = null;
			if (stng_date_split.length == 3) {
				dayss = DateToDay(stng_date_split[2], stng_date_split[1],
						stng_date_split[0], stng_hour_split[0],
						stng_hour_split[1], three);
			}
			// holder.btn_quick.setText(date);

			/** To set name of file to ListView */
			name = stg_list.get(pos).get(Files_Db.File_name);
			holder.txt_nm.setText(name);

			/** To set size of file to ListView */
			size = stg_list.get(pos).get(Files_Db.File_size);
			if (size.contentEquals("null")) {
			} else {
				int count = size.length();
				Log.d("pos", String.valueOf(count));
				double index = Double.valueOf(size);
				Log.d("pos", String.valueOf(index));
				if (count <= 6) {
					double x = (double) (index / 1000);
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(1);
					Log.d("percentage", String.valueOf(nf.format(x)));
					String y = String.valueOf(nf.format(x));
					double z = Double.valueOf(y);
					DecimalFormat df = new DecimalFormat("#,###,##0");
					holder.txt_sz.setText(String.valueOf(df.format(z))
							+ "KB, Modified " + dayss);
					Log.d("percentage", String.valueOf(df.format(z)));
				} else if (count <= 12) {
					double xx = (double) (index / 1000);
					double x = xx / 1000;
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(1);
					Log.d("percentage", String.valueOf(nf.format(x)));
					String y = String.valueOf(nf.format(x));
					double z = Double.valueOf(y);
					DecimalFormat df = new DecimalFormat("#,###,##0");
					holder.txt_sz.setText(String.valueOf(df.format(z))
							+ "MB, Modified " + dayss);
					Log.d("yy", String.valueOf(df.format(z)));
				} else if (count <= 18) {
					double xxx = (double) (index / 1000);
					double xx = xxx / 1000;
					double x = xx / 1000;
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(1);
					Log.d("percentage", String.valueOf(nf.format(x)));
					String y = String.valueOf(nf.format(x));
					double z = Double.valueOf(y);
					DecimalFormat df = new DecimalFormat("#,###,##0");
					holder.txt_sz.setText(String.valueOf(df.format(z))
							+ "GB, Modified " + dayss);
					Log.d("zzz", String.valueOf(df.format(z)));
				}
			}

			folder = stg_list.get(pos).get(Files_Db.File_folder);

			holder.two.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int pos = v.getId();

					if (pos == 0) {
						if (Constant.con_list != null
								&& Constant.con_list.size() > 0) {
							if (Constant.con_list.size() == 1) {
								int dex = Constant.con_list.size() - 1;
								Log.d("dex", String.valueOf(Constant.con_list
										.size()));
								String folder = String
										.valueOf(Constant.con_list.get(dex));
								String folder_name = String
										.valueOf(Constant.con_name.get(dex));
								Log.d("dex",
										String.valueOf(Constant.con_list
												.get(dex) + dex));
								if (folder.contentEquals("exit")) {
									setResult(RESULT_OK);
									finish();
								} else {
									new download(folder_name).execute(folder);
									Constant.con_fldid = folder;
									Constant.con_fldnm = folder_name;
									boo_back = false;
									back_pressed = true;
								}

							} else {
								int dex = Constant.con_list.size() - 2;
								Log.d("dex", String.valueOf(Constant.con_list
										.size()));
								String folder = String
										.valueOf(Constant.con_list.get(dex));
								String folder_name = String
										.valueOf(Constant.con_name.get(dex));
								Log.d("dex",
										String.valueOf(Constant.con_list
												.get(dex) + dex));
								if (folder.contentEquals("exit")) {
									setResult(RESULT_OK);
									finish();
								} else {
									new download(folder_name).execute(folder);
									Constant.con_fldid = folder;
									Constant.con_fldnm = folder_name;
									boo_back = false;
									back_pressed = true;
								}
							}
						} else {
							setResult(RESULT_OK);
							finish();
							ran = false;
						}
					} else {
						fld_typ = stg_list.get(pos).get(Files_Db.File_folder);
						dlt_id = stg_list.get(pos).get(Files_Db.File_id);
						fld_nm = stg_list.get(pos).get(Files_Db.File_name);
						fld_url = stg_list.get(pos).get(Files_Db.File_url);
						fld_fetc = stg_list.get(pos).get(Files_Db.File_thumb);
						String foldereds = stg_list.get(pos).get(
								Files_Db.File_folder);
						String folder_id = stg_list.get(pos).get(
								Files_Db.File_id);
						String folder_name = stg_list.get(pos).get(
								Files_Db.File_name);
						String folder_url = stg_list.get(pos).get(
								Files_Db.File_url);
						if (foldereds.contentEquals("folder")) {
							Constant.con_fldid = folder_id;
							Constant.con_fldnm = folder_name;
							deleteAll(Files_Db.Table);
							Table_name = Files_Db.Table;
							new download(Constant.con_fldnm)
									.execute(Constant.con_fldid);
							boo_items = false;
							boo_back = true;
							if (folder_id.contentEquals("exit")) {
								get_boo = "fold";
							} else {
								get_boo = null;
							}

							Log.d("download new ", Table_name + folder + " ");
						} else {
							Constant.con_fldid = folder_id;
							Constant.con_fldnm = folder_name;
							Constant.con_list.add(folder_id);
							Constant.con_name.add(folder_name);
							Log.d("download new else", Table_name + folder
									+ " ");
						}
					}
				}
			});

			holder.btn_quick.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int pos = v.getId();
					if (pos == 0) {

					} else {

						ArrayList<HashMap<String, String>> act_listcvv = Constant.db
								.getAllTranslates(Files_Db.Table, bool);
						for (int i = 0; i < act_listcvv.size(); i++) {
							Log.d("url",
									act_listcvv.get(i).get(Files_Db.File_name));
							Log.d("url",
									act_listcvv.get(i).get(Files_Db.File_url));
						}
						dlt_id = stg_list.get(pos).get(Files_Db.File_id);
						fld_url = stg_list.get(pos).get(Files_Db.File_url);
						fld_nm = stg_list.get(pos).get(Files_Db.File_name);
						fld_typ = stg_list.get(pos).get(Files_Db.File_folder);
						fld_fetc = stg_list.get(pos).get(Files_Db.File_thumb);
						fld_pos = pos;
						Constant.con_fldid = dlt_id;
						Constant.con_fldnm = fld_nm;

						Log.d("url", fld_url);
						Log.d("others", "" + dlt_id + fld_nm);

					}
				}
			});

			if (folder.contentEquals("folder")) {
				holder.txt_sz.setVisibility(View.GONE);
				if (pos == 0) {
					holder.img_file.setBackgroundResource(R.drawable.btn_back);
				} else {
					holder.img_file.setBackgroundResource(R.drawable.folder_ic);
				}
			} else if (folder.contentEquals("file")) {
				holder.txt_sz.setVisibility(View.VISIBLE);
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
			}

			holder.id = pos;
			return view;
		}

		private String DateToDay(String stng_date, String stng_date1,
				String stng_date2, String stng_date3, String stng_date4,
				String stng_date5) {
			String days = null;

			// Creates two calendars instances
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();

			// Set the date for both of the calendar instance
			cal1.set(Integer.parseInt(stng_date2),
					Integer.parseInt(stng_date1), Integer.parseInt(stng_date),
					Integer.parseInt(stng_date3), Integer.parseInt(stng_date4),
					Integer.parseInt(stng_date5));
			int year_next = cal2.get(Calendar.YEAR);
			int month_next = cal2.get(Calendar.MONTH) + 1;
			int day_next = cal2.get(Calendar.DAY_OF_MONTH);
			int hour_next = cal2.get(Calendar.HOUR_OF_DAY) - 5;
			int minute_next = cal2.get(Calendar.MINUTE) - 30;
			int second_next = cal2.get(Calendar.SECOND) - 5;

			Log.d("for",
					String.valueOf(year_next) + "/"
							+ String.valueOf(month_next) + "/"
							+ String.valueOf(day_next) + "/"
							+ String.valueOf(hour_next) + "/"
							+ String.valueOf(minute_next) + "/"
							+ String.valueOf(second_next));
			Log.d("for", stng_date + "/" + stng_date1 + "/" + stng_date2 + "/"
					+ stng_date3 + "/" + stng_date4 + "/" + stng_date5);
			cal2.set(year_next, month_next, day_next, hour_next, minute_next,
					second_next);

			// Get the represented date in milliseconds
			long milis1 = cal1.getTimeInMillis();
			long milis2 = cal2.getTimeInMillis();

			// Calculate difference in milliseconds
			long diff = milis2 - milis1;

			// Calculate difference in seconds
			long diffSeconds = diff / 1000;

			// Calculate difference in minutes
			long diffMinutes = diff / (60 * 1000);

			// Calculate difference in hours
			long diffHours = diff / (60 * 60 * 1000);

			// Calculate difference in days
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.println("In milliseconds: " + diff + " milliseconds.");
			System.out.println("In seconds: " + diffSeconds + " seconds.");
			System.out.println("In minutes: " + diffMinutes + " minutes.");
			System.out.println("In hours: " + diffHours + " hours.");
			System.out.println("In days: " + diffDays + " days.");

			if (diffSeconds < 3) {
				days = "1 second ago";
			} else if (diffSeconds < 60) {
				days = String.valueOf(diffSeconds) + "seconds ago";
			} else {
				if (diffMinutes < 3) {
					days = "1 minute ago";
				} else if (diffMinutes < 60) {
					days = String.valueOf(diffMinutes) + "minutes ago";
				} else {
					if (diffHours < 3) {
						days = "1 hour ago";
					} else if (diffHours < 24) {
						days = String.valueOf(diffHours) + "hours ago";
					} else {
						if (diffDays == 1) {
							days = "1 day ago";
						} else if (diffDays < 7) {
							days = String.valueOf(diffDays) + "days ago";
						} else if (diffDays < 14) {
							days = "1 week ago";
						} else if (diffDays < 29) {
							int d = (int) diffDays / 7;
							days = String.valueOf(d) + "weeks ago";
						} else if (diff < 32 || diff < 31 || diff < 30) {
							days = "1 month ago";
						} else if (diffDays < 63 || diff < 60 || diff < 61
								|| diff < 62) {
							days = "2 months ago";
						} else if (diffDays < 364) {
							int d = (int) diffDays / 30;
							days = String.valueOf(d) + "months ago";
						} else if (diffDays < 730) {
							days = "1 year ago";
						} else if (363 < diffDays) {
							int d = (int) diffDays / 365;
							days = String.valueOf(d) + "years ago";
						}
					}
				}
			}
			return days;
		}
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

	private void expected() {

		// seekbar_pg.setVisibility(View.GONE);
		// txt_pg.setVisibility(View.GONE);
		// txt_pgmssg.setVisibility(View.GONE);
		// txt_pgsucs.setVisibility(View.GONE);
		d.dismiss();
		all_path.clear();
		toastsettext("Uploaded Successfully");
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setResult(RESULT_OK);
		finish();

		if (Constant.con_booup) {
			Constant.con_booup = false;
			Log.d("size of store", String.valueOf(Constant.con_list.size()));
			Intent intent = new Intent(this, Folder_LoginActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("fld_id", Constant.con_fldid);
			bundle.putString("fld_nm", Constant.con_fldnm);
			bundle.putBoolean("ran", false);
			bundle.putString("new_fold", "true");
			bundle.putString("back", "false");
			bundle.putStringArrayList("array_id", Constant.con_list);
			bundle.putStringArrayList("array_nm", Constant.con_name);
			intent.putExtras(bundle);
			startActivityForResult(intent, 999);
		} else {

			if (Constant.con_fldid == null
					|| Constant.con_fldid.contentEquals("item")) {

			} else {
				Intent intent = new Intent(this, Folder_LoginActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("ran", false);
				bundle.putString("new_fold", "true");
				bundle.putString("back", "false");
				bundle.putString("fld_id", Constant.con_fldid);
				bundle.putString("fld_nm", Constant.con_fldnm);
				bundle.putStringArrayList("array_id", Constant.con_list);
				bundle.putStringArrayList("array_nm", Constant.con_name);
				intent.putExtras(bundle);
				startActivityForResult(intent, 999);

			}
		}
	}

	public class download extends AsyncTask<String, String, String> {
		String get = null, named = null;
		boolean boo_new = false;
		boolean boo_exit = false;

		public download(String new_fldnm) {
			named = new_fldnm;
		}

		@Override
		protected String doInBackground(String... params) {
			String s = params[0];
			return s;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (boo_sce) {
				boo_sce = false;
				toastsettext("Successfully created folder");
				rl_subbottom1.setVisibility(View.GONE);
				rl_subbottom2.setVisibility(View.VISIBLE);
			}

			downloadFile_listener(result);
		}

		@SuppressWarnings("unused")
		private void downloadFile_listener(final String result) {

			if (boo_fold) {
				boo_fold = false;
				get_fold = result;
			}
			Constant.datee = getSharedPreferences("datefun",
					MODE_WORLD_WRITEABLE);
			String access_token = "";

			if (Constant.datee != null
					&& Constant.datee.contains("access_token")) {
				access_token = "Bearer "
						+ Constant.datee.getString("access_token", "");
				Log.d("access_token", access_token);
			}

			String urls = null;

			if (boo_item) {
				boo_item = false;
				dlt_bln = true;
				urls = "http://whootin.com/api/v1/files.json?count=30";
				boo_items = true;
				if (Constant.con_list != null) {
					Constant.con_list.clear();
				}
				if (Constant.con_name != null) {
					Constant.con_name.clear();
				}
			} else {
				if (result == null) {
					dlt_bln = true;
					boo_items = true;
					urls = "http://whootin.com/api/v1/files.json?count=30";
					boo_new = true;
					if (Constant.con_list != null) {
						Constant.con_list.clear();
					}

					if (Constant.con_name != null) {
						Constant.con_name.clear();
					}
				} else if (result.contentEquals("item")) {
					dlt_bln = true;
					boo_items = true;
					boo_exit = true;
					urls = "http://whootin.com/api/v1/files.json?count=30";
					if (Constant.con_list != null) {
						Constant.con_list.clear();
					}
					if (Constant.con_name != null) {
						Constant.con_name.clear();
					}
				} else {
					urls = "http://whootin.com/api/v1/files.json?count=30&folder_id="
							+ result;
				}
			}

			AsyncHttpClient client = new AsyncHttpClient();
			client.addHeader("Accept", "appliction/json");
			client.addHeader("Authorization", access_token);
			client.get(urls, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(String arg0) {
					super.onSuccess(arg0);
					Log.e("from response", arg0);
					get = arg0;

					JSONArray valuejson;
					try {
						valuejson = new JSONArray(get);
						contactList_fld.clear();
						jsonList(valuejson);

						if (contactList_fld.size() >= 0) {
							contactListInsert();

							if (contactList_fld.size() == 0) {
							}

							if (dlt_bln) {
								dlt_bln = false;
								if (boo_new) {
									boo_new = false;
									Constant.con_list.clear();
									Constant.con_name.clear();
									Constant.con_list.add("item");
									Constant.con_name.add("  Home  ");
								}

								if (boo_exit) {
									boo_exit = false;
									Constant.con_list.clear();
									Constant.con_name.clear();
									Constant.con_list.add("exit");
									Constant.con_name.add("  Home  ");
								}
							} else {

								if (boo_back_new) {
									boo_back_new = false;
									if (Constant.con_list.size() > 1
											&& Constant.con_name.size() > 1) {
										Constant.con_list
												.remove(Constant.con_list
														.size() - 1);
										Constant.con_name
												.remove(Constant.con_name
														.size() - 1);
									}
									if (Constant.con_list.size() == 0) {
										setResult(RESULT_OK);
										finish();
										ran = false;
									}
								} else {
									if (boo_back) {
										Constant.con_list.add(result);
										Constant.con_name.add(named);
									} else {
										Log.d("boo",
												"added"
														+ String.valueOf(Constant.con_list
																.size()));
										if (Constant.con_list.size() > 1
												&& Constant.con_name.size() > 1) {
											Constant.con_list
													.remove(Constant.con_list
															.size() - 1);
											Constant.con_name
													.remove(Constant.con_name
															.size() - 1);
										}
										if (Constant.con_list.size() == 0) {
											setResult(RESULT_OK);
											finish();
											ran = false;
										}
									}
								}
							}

							mAdapter.notifyDataSetChanged();
							lv_hsub.setAdapter(mAdapter);

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}

				}

				private void jsonList(JSONArray valuejson) throws JSONException {
					for (int i = 0; i < valuejson.length(); i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.clear();
						JSONObject val = valuejson.getJSONObject(i);
						String id = val.getString(Files_Db.File_id);
						String name = val.getString(Files_Db.File_name);
						String size = val.getString(Files_Db.File_size);
						String folder = val.getString(Files_Db.File_folder);
						String create = val.getString(Files_Db.File_createat);

						Log.d("download", id + name + size + folder + create);
						map.put(Files_Db.File_id, id);
						map.put(Files_Db.File_name, name);
						map.put(Files_Db.File_createat, create);
						map.put(Files_Db.File_size, size);
						map.put(Files_Db.File_folder, folder);

						String url = null;
						String thumb = null;
						if (folder.contentEquals("file")) {
							url = val.getString(Files_Db.File_url);
							map.put(Files_Db.File_url, url);
							Log.d("db_url_1", name + url);
							thumb = val.getString(Files_Db.File_thumb);
							map.put(Files_Db.File_thumb, thumb);
							Log.d("thumb", thumb);
						} else if (folder.contentEquals("folder")) {
							map.put(Files_Db.File_url, "");
							thumb = val.getString(Files_Db.File_thumb);
							map.put(Files_Db.File_thumb, "");
							Log.d("thumb", thumb);
						}
						contactList_fld.add(map);
					}
				}

				private void contactListInsert() {

					ArrayList<HashMap<String, String>> act_listcv = Constant.db
							.getAllTranslates(Table_name, bool);
					List<String> list = new ArrayList<String>(act_listcv.size());
					for (int h = 0; h < act_listcv.size(); h++) {
						String _id = act_listcv.get(h).get(Files_Db.File_id);
						list.add(_id);
					}
					Constant.db_sql = Constant.db.getWritableDatabase();
					for (String and : list) {
						int add = 0;
						for (int h = 0; h < contactList_fld.size(); h++) {
							String File_id = contactList_fld.get(h).get(
									Files_Db.File_id);
							if (!and.contentEquals(File_id)) {
								add++;
							}
						}
						if (add == contactList_fld.size()) {
							Constant.db_sql.delete(Table_name, Files_Db.File_id
									+ "=?", new String[] { and });
							Log.d("success", "deleted");
						}
					}

					String File_id = null, File_name = null;
					for (int j = 0; j < contactList_fld.size(); j++) {

						final ContentValues valuesNew = new ContentValues();

						File_id = contactList_fld.get(j).get(Files_Db.File_id);
						if (contactList_fld.get(j).get(Files_Db.File_id) != null) {
							if (!contactList_fld.get(j).get(Files_Db.File_id)
									.isEmpty()) {
								valuesNew.put(Files_Db.File_id, File_id);
							} else {
								valuesNew.put(Files_Db.File_id, "");
							}
						} else {
							valuesNew.put(Files_Db.File_id, "");
						}

						File_name = contactList_fld.get(j).get(
								Files_Db.File_name);
						if (contactList_fld.get(j).get(Files_Db.File_name) != null) {
							if (!contactList_fld.get(j).get(Files_Db.File_name)
									.isEmpty()) {
								valuesNew.put(Files_Db.File_name, File_name);
							} else {
								valuesNew.put(Files_Db.File_name, "");
							}
						} else {
							valuesNew.put(Files_Db.File_id, "");
						}

						String File_createat = contactList_fld.get(j).get(
								Files_Db.File_createat);
						if (contactList_fld.get(j).get(Files_Db.File_createat) != null) {
							if (!contactList_fld.get(j)
									.get(Files_Db.File_createat).isEmpty()) {
								valuesNew.put(Files_Db.File_createat,
										File_createat);
							} else {
								valuesNew.put(Files_Db.File_createat, "");
							}
						} else {
							valuesNew.put(Files_Db.File_createat, "");
						}

						String File_size = contactList_fld.get(j).get(
								Files_Db.File_size);
						if (contactList_fld.get(j).get(Files_Db.File_size) != null) {
							if (!contactList_fld.get(j).get(Files_Db.File_size)
									.isEmpty()) {
								valuesNew.put(Files_Db.File_size, File_size);
							} else {
								valuesNew.put(Files_Db.File_size, "");
							}
						} else {
							valuesNew.put(Files_Db.File_size, "");
						}

						String File_folder = contactList_fld.get(j).get(
								Files_Db.File_folder);
						if (contactList_fld.get(j).get(Files_Db.File_folder) != null) {
							if (!contactList_fld.get(j)
									.get(Files_Db.File_folder).isEmpty()) {
								valuesNew
										.put(Files_Db.File_folder, File_folder);
							} else {
								valuesNew.put(Files_Db.File_folder, "");
							}
						} else {
							valuesNew.put(Files_Db.File_folder, "");
						}

						String File_url = contactList_fld.get(j).get(
								Files_Db.File_url);
						if (contactList_fld.get(j).get(Files_Db.File_url) != null) {
							if (!contactList_fld.get(j).get(Files_Db.File_url)
									.isEmpty()) {
								valuesNew.put(Files_Db.File_url, File_url);
							} else {
								valuesNew.put(Files_Db.File_url, "");
							}
						} else {
							valuesNew.put(Files_Db.File_url, "");
						}

						String File_thumb = contactList_fld.get(j).get(
								Files_Db.File_thumb);
						if (contactList_fld.get(j).get(Files_Db.File_thumb) != null) {
							if (!contactList_fld.get(j)
									.get(Files_Db.File_thumb).isEmpty()) {
								valuesNew.put(Files_Db.File_thumb, File_thumb);
							} else {
								valuesNew.put(Files_Db.File_thumb, "");
							}
						} else {
							valuesNew.put(Files_Db.File_thumb, "");
						}

						Log.d("sizeof contactList",
								String.valueOf(contactList_fld.size())
										+ String.valueOf(act_listcv.size()));

						String is_select = "SELECT " + Files_Db.File_id
								+ " FROM " + Table_name + " WHERE "
								+ Files_Db.File_id + " = '" + File_id + "'";
						int jac = isUserAvailable(is_select);
						Log.d("jac", String.valueOf(jac) + File_name);
						if (jac == 1) {
							Constant.db_sql.update(Table_name, valuesNew,
									Files_Db.File_id + "=?",
									new String[] { File_id });
							Log.d("success", "updated");
						} else if (jac == 0) {
							Constant.db_sql.insert(Table_name, null, valuesNew);
							Log.d("success", "inserted");
						}

					}
					Constant.db_sql.close();

					result1();

				}

				@Override
				public void onFinish() {
					super.onFinish();
					Log.e("from response finish", "finish");
					if (prg_bar.isShowing()) {
						prg_bar.setCancelable(true);
						prg_bar.dismiss();
						prg_bar.cancel();
					}
				}

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					super.onFailure(arg0, arg1);
					Log.e("from response", arg1);
				}

			});

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (prg_bar.isShowing()) {

			} else {
				prg_bar.show();
			}

		}

	}

	private void result1() {
		re_contactList2.clear();
		order1.clear();
		order2.clear();
		Log.d("thrid", "thrid");

		re_contactList = Constant.db.getAllTranslates(Files_Db.Table, bool);

		if (re_contactList.size() > 0) {
			re_contactList2.clear();

			for (int j = 0; j < re_contactList.size(); j++) {
				int z = 1;
				String fld = re_contactList.get(j).get(Files_Db.File_folder);
				String fldname = re_contactList.get(j).get(Files_Db.File_name);
				if (fld.contentEquals("folder")) {
					add1(j, z);

				}
			}

			for (int j = 0; j < re_contactList.size(); j++) {
				String fld = re_contactList.get(j).get(Files_Db.File_folder);
				String fldname = re_contactList.get(j).get(Files_Db.File_name);
				if (fld.contentEquals("file")) {

					Log.d("fldfldname", fldname);
					if (fldname.endsWith(".zip") || fldname.endsWith(".ZIP")) {

						add1(j, 2);

					} else if (fldname.endsWith(".pdf")
							|| fldname.endsWith(".PDF")) {

						add1(j, 2);

					} else if (fldname.endsWith(".doc")
							|| fldname.endsWith(".DOC")) {

						add1(j, 2);

					} else if (fldname.endsWith(".ice")
							|| fldname.endsWith(".ICE")) {

						add1(j, 2);

					} else if (fldname.endsWith(".wmf")
							|| fldname.endsWith(".WMF")) {

						add1(j, 2);

					} else if (fldname.endsWith(".ivr")
							|| fldname.endsWith(".IVR")) {

						add1(j, 2);

					} else if (fldname.endsWith(".pvu")
							|| fldname.endsWith(".PVU")) {

						add1(j, 2);

					} else if (fldname.endsWith(".xyz")
							|| fldname.endsWith(".XYZ")
							|| fldname.endsWith(".pdb")
							|| fldname.endsWith(".PDB")) {

						add1(j, 2);

					} else if (fldname.endsWith(".xgz")
							|| fldname.endsWith(".XGZ")
							|| fldname.endsWith(".xmz")
							|| fldname.endsWith(".XMZ")) {

						add1(j, 2);

					} else if (fldname.endsWith(".ustar")
							|| fldname.endsWith(".USTAR")
							|| fldname.endsWith(".gzip")
							|| fldname.endsWith(".GZIP")) {

						add1(j, 2);

					} else if (fldname.endsWith(".mime")
							|| fldname.endsWith(".MIME")
							|| fldname.endsWith(".mht")
							|| fldname.endsWith(".MTH")
							|| fldname.endsWith(".mhtml")
							|| fldname.endsWith(".MHTML")) {

						add1(j, 2);

					} else if (fldname.endsWith(".3dm")
							|| fldname.endsWith(".3DM")
							|| fldname.endsWith(".3dmf")
							|| fldname.endsWith(".3DMF")
							|| fldname.endsWith(".qd3")
							|| fldname.endsWith(".QD3")
							|| fldname.endsWith(".qd3d")
							|| fldname.endsWith(".QD3D")
							|| fldname.endsWith(".3svr")
							|| fldname.endsWith(".3SVR")
							|| fldname.endsWith(".vrt")
							|| fldname.endsWith(".VRT")) {

						add1(j, 2);

					} else if (fldname.endsWith(".vrml")
							|| fldname.endsWith(".IVR")
							|| fldname.endsWith(".pov")
							|| fldname.endsWith(".POV")
							|| fldname.endsWith(".iges")
							|| fldname.endsWith(".IGES")
							|| fldname.endsWith(".igs")
							|| fldname.endsWith(".IGS")
							|| fldname.endsWith(".wrl")
							|| fldname.endsWith(".WRL")
							|| fldname.endsWith(".wrz")
							|| fldname.endsWith(".WRZ")
							|| fldname.endsWith(".dwf")
							|| fldname.endsWith(".DWF")) {

						add1(j, 2);

					} else if (fldname.endsWith(".html")
							|| fldname.endsWith(".HTML")
							|| fldname.endsWith(".shtml")
							|| fldname.endsWith(".SHTML")
							|| fldname.endsWith(".acgi")
							|| fldname.endsWith(".ACGI")
							|| fldname.endsWith(".htm")
							|| fldname.endsWith(".HTM")
							|| fldname.endsWith(".htmls")
							|| fldname.endsWith(".HTMLS")
							|| fldname.endsWith(".htt")
							|| fldname.endsWith(".HTT")
							|| fldname.endsWith(".htx")
							|| fldname.endsWith(".HTX")) {

						add1(j, 2);

					} else if (fldname.endsWith(".ppt")
							|| fldname.endsWith(".PPT")
							|| fldname.endsWith(".pptx")
							|| fldname.endsWith(".PPTX")
							|| fldname.endsWith(".pptm")
							|| fldname.endsWith(".PPTM")
							|| fldname.endsWith(".pot")
							|| fldname.endsWith(".POT")
							|| fldname.endsWith(".potx")
							|| fldname.endsWith(".POTX")
							|| fldname.endsWith(".pps")
							|| fldname.endsWith(".PPS")
							|| fldname.endsWith(".ppsx")
							|| fldname.endsWith(".PPSX")
							|| fldname.endsWith(".ppsm")
							|| fldname.endsWith(".PPSM")) {

						add1(j, 2);

					} else if (fldname.endsWith(".xls")
							|| fldname.endsWith(".XLS")
							|| fldname.endsWith(".xlsx")
							|| fldname.endsWith(".XLSX")
							|| fldname.endsWith(".xlam")
							|| fldname.endsWith(".XLAM")
							|| fldname.endsWith(".xltm")
							|| fldname.endsWith(".XLTM")
							|| fldname.endsWith(".xlsm")
							|| fldname.endsWith(".XLSM")
							|| fldname.endsWith(".xlsb")
							|| fldname.endsWith(".XLSB")
							|| fldname.endsWith(".xl")
							|| fldname.endsWith(".XL")
							|| fldname.endsWith(".xla")
							|| fldname.endsWith(".XLA")
							|| fldname.endsWith(".xlb")
							|| fldname.endsWith(".XLB")
							|| fldname.endsWith(".xlc")
							|| fldname.endsWith(".XLC")
							|| fldname.endsWith(".xld")
							|| fldname.endsWith(".XLD")
							|| fldname.endsWith(".xlk")
							|| fldname.endsWith(".XLK")
							|| fldname.endsWith(".xll")
							|| fldname.endsWith(".XLL")
							|| fldname.endsWith(".xlm")
							|| fldname.endsWith(".XLM")
							|| fldname.endsWith(".xlt")
							|| fldname.endsWith(".XLT")
							|| fldname.endsWith(".xlv")
							|| fldname.endsWith(".XLV")
							|| fldname.endsWith(".xlw")
							|| fldname.endsWith(".XLW")) {

						add1(j, 2);

					} else if (fldname.endsWith(".mp4")
							|| fldname.endsWith(".MP4")
							|| fldname.endsWith(".avi")
							|| fldname.endsWith(".AVI")
							|| fldname.endsWith(".afl")
							|| fldname.endsWith(".AFL")
							|| fldname.endsWith(".3gp")
							|| fldname.endsWith(".3GP")
							|| fldname.endsWith(".mkv")
							|| fldname.endsWith(".MKV")
							|| fldname.endsWith(".flv")
							|| fldname.endsWith(".FLV")
							|| fldname.endsWith(".wmv")
							|| fldname.endsWith(".WMV")
							|| fldname.endsWith(".asf")
							|| fldname.endsWith(".ASF")
							|| fldname.endsWith(".mpeg")
							|| fldname.endsWith(".MPEG")
							|| fldname.endsWith(".dv")
							|| fldname.endsWith(".DV")
							|| fldname.endsWith(".dl")
							|| fldname.endsWith(".DL")
							|| fldname.endsWith(".mov")
							|| fldname.endsWith(".MOV")
							|| fldname.endsWith(".avs")
							|| fldname.endsWith(".AVS")
							|| fldname.endsWith(".af")
							|| fldname.endsWith(".AF")
							|| fldname.endsWith(".asf")
							|| fldname.endsWith(".ASF")
							|| fldname.endsWith(".dif")
							|| fldname.endsWith(".DIF")
							|| fldname.endsWith(".fli")
							|| fldname.endsWith(".FLI")
							|| fldname.endsWith(".fmf")
							|| fldname.endsWith(".FMF")
							|| fldname.endsWith(".asx")
							|| fldname.endsWith(".ASX")
							|| fldname.endsWith(".mjpg")
							|| fldname.endsWith(".MJPG")
							|| fldname.endsWith(".m2v")
							|| fldname.endsWith(".M2V")
							|| fldname.endsWith(".m1v")
							|| fldname.endsWith(".M1V")
							|| fldname.endsWith(".scm")
							|| fldname.endsWith(".SCM")
							|| fldname.endsWith(".vivo")
							|| fldname.endsWith(".VIVO")
							|| fldname.endsWith(".vos")
							|| fldname.endsWith(".VOS")
							|| fldname.endsWith(".xsr")
							|| fldname.endsWith(".XSR")
							|| fldname.endsWith(".xdr")
							|| fldname.endsWith(".XDR")
							|| fldname.endsWith(".gl")
							|| fldname.endsWith(".GL")
							|| fldname.endsWith(".isu")
							|| fldname.endsWith(".ISU")
							|| fldname.endsWith(".rv")
							|| fldname.endsWith(".RV")
							|| fldname.endsWith(".vdo")
							|| fldname.endsWith(".VDO")
							|| fldname.endsWith(".viv")
							|| fldname.endsWith(".VIV")
							|| fldname.endsWith(".qtc")
							|| fldname.endsWith(".QTC")
							|| fldname.endsWith(".qt")
							|| fldname.endsWith(".QT")
							|| fldname.endsWith(".mv")
							|| fldname.endsWith(".MV")
							|| fldname.endsWith(".moov")
							|| fldname.endsWith(".MOOV")
							|| fldname.endsWith(".mov")
							|| fldname.endsWith(".MOV")
							|| fldname.endsWith(".movie")
							|| fldname.endsWith(".MOVIE")
							|| fldname.endsWith(".mpe")
							|| fldname.endsWith(".MPE")) {

						add1(j, 2);

					} else if (fldname.endsWith(".jpg")
							|| fldname.endsWith(".JPG")
							|| fldname.endsWith(".jpeg")
							|| fldname.endsWith(".JPEG")
							|| fldname.endsWith(".bmp")
							|| fldname.endsWith(".BMP")
							|| fldname.endsWith(".svg")
							|| fldname.endsWith(".SVG")
							|| fldname.endsWith(".png")
							|| fldname.endsWith(".PNG")
							|| fldname.endsWith(".ras")
							|| fldname.endsWith(".RAS")
							|| fldname.endsWith(".rast")
							|| fldname.endsWith(".RAST")
							|| fldname.endsWith(".wbmp")
							|| fldname.endsWith(".WBMP")
							|| fldname.endsWith(".xwd")
							|| fldname.endsWith(".XWD")
							|| fldname.endsWith(".xpm")
							|| fldname.endsWith(".XPM")
							|| fldname.endsWith(".x-png")
							|| fldname.endsWith(".X-PNG")
							|| fldname.endsWith(".xbm")
							|| fldname.endsWith(".XBM")
							|| fldname.endsWith(".rf")
							|| fldname.endsWith(".RF")
							|| fldname.endsWith(".rgb")
							|| fldname.endsWith(".RGB")
							|| fldname.endsWith(".tif")
							|| fldname.endsWith(".TIF")
							|| fldname.endsWith(".tiff")
							|| fldname.endsWith(".TIFF")
							|| fldname.endsWith(".svf")
							|| fldname.endsWith(".SVF")
							|| fldname.endsWith(".rp")
							|| fldname.endsWith(".RP")
							|| fldname.endsWith(".xif")
							|| fldname.endsWith(".XIF")
							|| fldname.endsWith(".turbot")
							|| fldname.endsWith(".TURBOT")
							|| fldname.endsWith(".pgm")
							|| fldname.endsWith(".PGM")
							|| fldname.endsWith(".pic")
							|| fldname.endsWith(".PIC")
							|| fldname.endsWith(".pict")
							|| fldname.endsWith(".PICT")
							|| fldname.endsWith(".pm")
							|| fldname.endsWith(".PM")
							|| fldname.endsWith(".pnm")
							|| fldname.endsWith(".PNM")
							|| fldname.endsWith(".nap")
							|| fldname.endsWith(".NAP")
							|| fldname.endsWith(".naplps")
							|| fldname.endsWith(".NAPLPS")
							|| fldname.endsWith(".nif")
							|| fldname.endsWith(".NIF")
							|| fldname.endsWith(".niff")
							|| fldname.endsWith(".NIFF")
							|| fldname.endsWith(".pbm")
							|| fldname.endsWith(".PBM")
							|| fldname.endsWith(".pct")
							|| fldname.endsWith(".PCT")
							|| fldname.endsWith(".pcx")
							|| fldname.endsWith(".PCX")
							|| fldname.endsWith(".ico")
							|| fldname.endsWith(".ICO")
							|| fldname.endsWith(".ief")
							|| fldname.endsWith(".IEF")
							|| fldname.endsWith(".iefs")
							|| fldname.endsWith(".IEFS")
							|| fldname.endsWith(".dwg")
							|| fldname.endsWith(".DWG")
							|| fldname.endsWith(".dxf")
							|| fldname.endsWith(".DXF")
							|| fldname.endsWith(".art")
							|| fldname.endsWith(".ART")
							|| fldname.endsWith(".bm")
							|| fldname.endsWith(".BM")
							|| fldname.endsWith(".bmp")
							|| fldname.endsWith(".BMP")
							|| fldname.endsWith(".fif")
							|| fldname.endsWith(".FIF")
							|| fldname.endsWith(".flo")
							|| fldname.endsWith(".FLO")
							|| fldname.endsWith(".fpx")
							|| fldname.endsWith(".FPX")
							|| fldname.endsWith(".g3")
							|| fldname.endsWith(".G3")
							|| fldname.endsWith(".gif")
							|| fldname.endsWith(".GIF")
							|| fldname.endsWith(".jfif")
							|| fldname.endsWith(".JFIF")
							|| fldname.endsWith(".jfif-tbnl")
							|| fldname.endsWith(".JFIF-TBNL")
							|| fldname.endsWith(".jpe")
							|| fldname.endsWith(".JPE")
							|| fldname.endsWith(".jps")
							|| fldname.endsWith(".JPS")
							|| fldname.endsWith(".jut")
							|| fldname.endsWith(".JUT")
							|| fldname.endsWith(".qif")
							|| fldname.endsWith(".QIF")
							|| fldname.endsWith(".qti")
							|| fldname.endsWith(".QTI")) {

						add1(j, 2);

					} else if (fldname.endsWith(".ab")
							|| fldname.endsWith(".AB")
							|| fldname.endsWith(".acgi")
							|| fldname.endsWith(".ACGI")
							|| fldname.endsWith(".ksh")
							|| fldname.endsWith(".KSH")
							|| fldname.endsWith(".jav")
							|| fldname.endsWith(".JAV")
							|| fldname.endsWith(".java")
							|| fldname.endsWith(".JAVA")
							|| fldname.endsWith(".lsp")
							|| fldname.endsWith(".LSP")
							|| fldname.endsWith(".lsx")
							|| fldname.endsWith(".LSX")
							|| fldname.endsWith(".lst")
							|| fldname.endsWith(".LST")
							|| fldname.endsWith(".m") || fldname.endsWith(".M")
							|| fldname.endsWith(".p") || fldname.endsWith(".P")
							|| fldname.endsWith(".pas")
							|| fldname.endsWith(".PAS")
							|| fldname.endsWith(".pl")
							|| fldname.endsWith(".PL")
							|| fldname.endsWith(".pm")
							|| fldname.endsWith(".PM")
							|| fldname.endsWith(".mar")
							|| fldname.endsWith(".MAR")
							|| fldname.endsWith(".js")
							|| fldname.endsWith(".JS")
							|| fldname.endsWith(".list")
							|| fldname.endsWith(".LIST")
							|| fldname.endsWith(".log")
							|| fldname.endsWith(".LOG")
							|| fldname.endsWith(".mcf")
							|| fldname.endsWith(".MCF")
							|| fldname.endsWith(".rexx")
							|| fldname.endsWith(".REXX")
							|| fldname.endsWith(".sdml")
							|| fldname.endsWith(".SDML")
							|| fldname.endsWith(".rtx")
							|| fldname.endsWith(".RTX")
							|| fldname.endsWith(".scm")
							|| fldname.endsWith(".SCM")
							|| fldname.endsWith(".s") || fldname.endsWith(".S")
							|| fldname.endsWith(".rt")
							|| fldname.endsWith(".RT")
							|| fldname.endsWith(".rtf")
							|| fldname.endsWith(".RTF")
							|| fldname.endsWith(".sgm")
							|| fldname.endsWith(".SGM")
							|| fldname.endsWith(".sgml")
							|| fldname.endsWith(".SGML")
							|| fldname.endsWith(".spc")
							|| fldname.endsWith(".SPC")
							|| fldname.endsWith(".ssi")
							|| fldname.endsWith(".SSI")
							|| fldname.endsWith(".talk")
							|| fldname.endsWith(".TALK")
							|| fldname.endsWith(".tcl")
							|| fldname.endsWith(".TCL")
							|| fldname.endsWith(".tcsh")
							|| fldname.endsWith(".TCSH")
							|| fldname.endsWith(".text")
							|| fldname.endsWith(".TEXT")
							|| fldname.endsWith(".tsv")
							|| fldname.endsWith(".TSV")
							|| fldname.endsWith(".txt")
							|| fldname.endsWith(".TXT")
							|| fldname.endsWith(".uil")
							|| fldname.endsWith(".UIL")
							|| fldname.endsWith(".uni")
							|| fldname.endsWith(".UNI")
							|| fldname.endsWith(".unis")
							|| fldname.endsWith(".UNIS")
							|| fldname.endsWith(".uri")
							|| fldname.endsWith(".URI")
							|| fldname.endsWith(".uris")
							|| fldname.endsWith(".URIS")
							|| fldname.endsWith(".uu")
							|| fldname.endsWith(".UU")
							|| fldname.endsWith(".uue")
							|| fldname.endsWith(".UUE")
							|| fldname.endsWith(".wml")
							|| fldname.endsWith(".WML")
							|| fldname.endsWith(".wmls")
							|| fldname.endsWith(".WMLS")
							|| fldname.endsWith(".zsh")
							|| fldname.endsWith(".ZSH")
							|| fldname.endsWith(".xml")
							|| fldname.endsWith(".XML")
							|| fldname.endsWith(".wsc")
							|| fldname.endsWith(".WMZ")
							|| fldname.endsWith(".vcs")
							|| fldname.endsWith(".VCS")
							|| fldname.endsWith(".sh")
							|| fldname.endsWith(".SH")
							|| fldname.endsWith(".py")
							|| fldname.endsWith(".PY")
							|| fldname.endsWith(".aip")
							|| fldname.endsWith(".AIP")
							|| fldname.endsWith(".asm")
							|| fldname.endsWith(".ASM")
							|| fldname.endsWith(".asf")
							|| fldname.endsWith(".ASP")
							|| fldname.endsWith(".c") || fldname.endsWith(".C")
							|| fldname.endsWith(".c++")
							|| fldname.endsWith(".C++")
							|| fldname.endsWith(".cc")
							|| fldname.endsWith(".CC")
							|| fldname.endsWith(".com")
							|| fldname.endsWith(".COM")
							|| fldname.endsWith(".conf")
							|| fldname.endsWith(".CONF")
							|| fldname.endsWith(".cpp")
							|| fldname.endsWith(".CPP")
							|| fldname.endsWith(".csh")
							|| fldname.endsWith(".CSH")
							|| fldname.endsWith(".cxx")
							|| fldname.endsWith(".CXX")
							|| fldname.endsWith(".css")
							|| fldname.endsWith(".CSS")
							|| fldname.endsWith(".def")
							|| fldname.endsWith(".DEF")
							|| fldname.endsWith(".el")
							|| fldname.endsWith(".EL")
							|| fldname.endsWith(".etx")
							|| fldname.endsWith(".ETX")
							|| fldname.endsWith(".f") || fldname.endsWith(".F")
							|| fldname.endsWith(".f77")
							|| fldname.endsWith(".F77")
							|| fldname.endsWith(".f90")
							|| fldname.endsWith(".F90")
							|| fldname.endsWith(".hh")
							|| fldname.endsWith(".HH")
							|| fldname.endsWith(".hlb")
							|| fldname.endsWith(".HLB")
							|| fldname.endsWith(".htc")
							|| fldname.endsWith(".HTC")
							|| fldname.endsWith(".g") || fldname.endsWith(".G")
							|| fldname.endsWith(".h") || fldname.endsWith(".H")
							|| fldname.endsWith(".idc")
							|| fldname.endsWith(".IDC")
							|| fldname.endsWith(".flx")
							|| fldname.endsWith(".FLX")
							|| fldname.endsWith(".for")
							|| fldname.endsWith(".FOR")) {

						add1(j, 2);

					} else if (fldname.endsWith(".mp3")
							|| fldname.endsWith(".MP3")
							|| fldname.endsWith(".mp2")
							|| fldname.endsWith(".MP2")
							|| fldname.endsWith(".wav")
							|| fldname.endsWith(".WAV")
							|| fldname.endsWith(".aac")
							|| fldname.endsWith(".AAC")
							|| fldname.endsWith(".ac3")
							|| fldname.endsWith(".AC3")
							|| fldname.endsWith(".aif")
							|| fldname.endsWith(".AIF")
							|| fldname.endsWith(".aifc")
							|| fldname.endsWith(".AIFC")
							|| fldname.endsWith(".aiff")
							|| fldname.endsWith(".AIFF")
							|| fldname.endsWith(".funk")
							|| fldname.endsWith(".FUNK")
							|| fldname.endsWith(".au")
							|| fldname.endsWith(".AU")
							|| fldname.endsWith(".gsd")
							|| fldname.endsWith(".GSD")
							|| fldname.endsWith(".gsm")
							|| fldname.endsWith(".GSM")
							|| fldname.endsWith(".it")
							|| fldname.endsWith(".IT")
							|| fldname.endsWith(".jam")
							|| fldname.endsWith(".JAM")
							|| fldname.endsWith(".la")
							|| fldname.endsWith(".LA")
							|| fldname.endsWith(".lam")
							|| fldname.endsWith(".LAM")
							|| fldname.endsWith(".kar")
							|| fldname.endsWith(".KAR")
							|| fldname.endsWith(".lma")
							|| fldname.endsWith(".LMA")
							|| fldname.endsWith(".m2a")
							|| fldname.endsWith(".M2A")
							|| fldname.endsWith(".mid")
							|| fldname.endsWith(".MID")
							|| fldname.endsWith(".midi")
							|| fldname.endsWith(".MIDI")
							|| fldname.endsWith(".m3u")
							|| fldname.endsWith(".M3U")
							|| fldname.endsWith(".mjf")
							|| fldname.endsWith(".MJF")
							|| fldname.endsWith(".rm")
							|| fldname.endsWith(".RM")
							|| fldname.endsWith(".rmm")
							|| fldname.endsWith(".RMM")
							|| fldname.endsWith(".rmp")
							|| fldname.endsWith(".RMP")
							|| fldname.endsWith(".rpm")
							|| fldname.endsWith(".RPM")
							|| fldname.endsWith(".s3m")
							|| fldname.endsWith(".S3M")
							|| fldname.endsWith(".sid")
							|| fldname.endsWith(".SID")
							|| fldname.endsWith(".snd")
							|| fldname.endsWith(".SND")
							|| fldname.endsWith(".tsi")
							|| fldname.endsWith(".TSI")
							|| fldname.endsWith(".voc")
							|| fldname.endsWith(".VOC")
							|| fldname.endsWith(".vox")
							|| fldname.endsWith(".VOX")
							|| fldname.endsWith(".vqe")
							|| fldname.endsWith(".VQE")
							|| fldname.endsWith(".vqf")
							|| fldname.endsWith(".VQF")
							|| fldname.endsWith(".vql")
							|| fldname.endsWith(".VQL")
							|| fldname.endsWith(".xm")
							|| fldname.endsWith(".XM")
							|| fldname.endsWith(".tsp")
							|| fldname.endsWith(".TSP")
							|| fldname.endsWith(".mod")
							|| fldname.endsWith(".MOD")
							|| fldname.endsWith(".mpa")
							|| fldname.endsWith(".MPA")
							|| fldname.endsWith(".my")
							|| fldname.endsWith(".MY")
							|| fldname.endsWith(".mpg")
							|| fldname.endsWith(".MPG")
							|| fldname.endsWith(".pfunk")
							|| fldname.endsWith(".PFUNK")
							|| fldname.endsWith(".mpga")
							|| fldname.endsWith(".MPGA")
							|| fldname.endsWith(".ra")
							|| fldname.endsWith(".RA")
							|| fldname.endsWith(".qcp")
							|| fldname.endsWith(".QCP")
							|| fldname.endsWith(".ram")
							|| fldname.endsWith(".RAM")) {

						add1(j, 2);

					} else if (fldname.endsWith(".a") || fldname.endsWith(".A")
							|| fldname.endsWith(".aab")
							|| fldname.endsWith(".AAB")
							|| fldname.endsWith(".aam")
							|| fldname.endsWith(".AAM")
							|| fldname.endsWith(".aas")
							|| fldname.endsWith(".AAS")
							|| fldname.endsWith(".ai")
							|| fldname.endsWith(".AI")
							|| fldname.endsWith(".aim")
							|| fldname.endsWith(".AIM")
							|| fldname.endsWith(".ani")
							|| fldname.endsWith(".ANI")
							|| fldname.endsWith(".aos")
							|| fldname.endsWith(".AOS")
							|| fldname.endsWith(".aps")
							|| fldname.endsWith(".APS")
							|| fldname.endsWith(".arc")
							|| fldname.endsWith(".ARC")
							|| fldname.endsWith(".arj")
							|| fldname.endsWith(".ARJ")
							|| fldname.endsWith(".bcpio")
							|| fldname.endsWith(".BCPIO")
							|| fldname.endsWith(".bin")
							|| fldname.endsWith(".BIN")
							|| fldname.endsWith(".boo")
							|| fldname.endsWith(".BOO")
							|| fldname.endsWith(".book")
							|| fldname.endsWith(".BOOK")
							|| fldname.endsWith(".boz")
							|| fldname.endsWith(".BOZ")
							|| fldname.endsWith(".bsh")
							|| fldname.endsWith(".BSH")
							|| fldname.endsWith(".bz")
							|| fldname.endsWith(".BZ")
							|| fldname.endsWith(".bz2")
							|| fldname.endsWith(".BZ2")
							|| fldname.endsWith(".cat")
							|| fldname.endsWith(".CAT")
							|| fldname.endsWith(".ccat")
							|| fldname.endsWith(".CCAD")
							|| fldname.endsWith(".cco")
							|| fldname.endsWith(".CCO")
							|| fldname.endsWith(".cdf")
							|| fldname.endsWith(".CDF")
							|| fldname.endsWith(".cer")
							|| fldname.endsWith(".CER")
							|| fldname.endsWith(".cha")
							|| fldname.endsWith(".CHA")
							|| fldname.endsWith(".class")
							|| fldname.endsWith(".CLASS")
							|| fldname.endsWith(".chat")
							|| fldname.endsWith(".CHAT")
							|| fldname.endsWith(".der")
							|| fldname.endsWith(".DER")
							|| fldname.endsWith(".dir")
							|| fldname.endsWith(".DIR")
							|| fldname.endsWith(".cpio")
							|| fldname.endsWith(".CPIO")
							|| fldname.endsWith(".dcr")
							|| fldname.endsWith(".DCR")
							|| fldname.endsWith(".deepv")
							|| fldname.endsWith(".DEEPV")
							|| fldname.endsWith(".cpi")
							|| fldname.endsWith(".CPT")
							|| fldname.endsWith(".crl")
							|| fldname.endsWith(".CRL")
							|| fldname.endsWith(".crt")
							|| fldname.endsWith(".CRT")
							|| fldname.endsWith(".dot")
							|| fldname.endsWith(".DOT")
							|| fldname.endsWith(".dp")
							|| fldname.endsWith(".DP")
							|| fldname.endsWith(".drw")
							|| fldname.endsWith(".DRW")
							|| fldname.endsWith(".dump")
							|| fldname.endsWith(".DUMP")
							|| fldname.endsWith(".dvi")
							|| fldname.endsWith(".DVI")
							|| fldname.endsWith(".elc")
							|| fldname.endsWith(".ELC")
							|| fldname.endsWith(".env")
							|| fldname.endsWith(".ENV")
							|| fldname.endsWith(".eps")
							|| fldname.endsWith(".EPS")
							|| fldname.endsWith(".es")
							|| fldname.endsWith(".ES")
							|| fldname.endsWith(".evy")
							|| fldname.endsWith(".EVY")
							|| fldname.endsWith(".exe")
							|| fldname.endsWith(".EXE")
							|| fldname.endsWith(".gsp")
							|| fldname.endsWith(".GSP")
							|| fldname.endsWith(".gss")
							|| fldname.endsWith(".GSS")
							|| fldname.endsWith(".gtar")
							|| fldname.endsWith(".GTAR")
							|| fldname.endsWith(".gz")
							|| fldname.endsWith(".GZ")
							|| fldname.endsWith(".hlp")
							|| fldname.endsWith(".HLP")
							|| fldname.endsWith(".hpgr")
							|| fldname.endsWith(".HPGR")
							|| fldname.endsWith(".hpgl")
							|| fldname.endsWith(".HPGL")
							|| fldname.endsWith(".hqx")
							|| fldname.endsWith(".HQX")
							|| fldname.endsWith(".hta")
							|| fldname.endsWith(".HTA")
							|| fldname.endsWith(".mpp")
							|| fldname.endsWith(".MPP")
							|| fldname.endsWith(".mpt")
							|| fldname.endsWith(".MPT")
							|| fldname.endsWith(".mpv")
							|| fldname.endsWith(".MPV")
							|| fldname.endsWith(".mpx")
							|| fldname.endsWith(".MPX")
							|| fldname.endsWith(".mrc")
							|| fldname.endsWith(".MRC")
							|| fldname.endsWith(".ms")
							|| fldname.endsWith(".MS")
							|| fldname.endsWith(".p10")
							|| fldname.endsWith(".P10")
							|| fldname.endsWith(".p12")
							|| fldname.endsWith(".P12")
							|| fldname.endsWith(".p7a")
							|| fldname.endsWith(".P7A")
							|| fldname.endsWith(".p7c")
							|| fldname.endsWith(".P7C")
							|| fldname.endsWith(".p7m")
							|| fldname.endsWith(".P7M")
							|| fldname.endsWith(".p7r")
							|| fldname.endsWith(".P7R")
							|| fldname.endsWith(".p7s")
							|| fldname.endsWith(".P7S")
							|| fldname.endsWith(".part")
							|| fldname.endsWith(".PART")
							|| fldname.endsWith(".o") || fldname.endsWith(".O")
							|| fldname.endsWith(".nvd")
							|| fldname.endsWith(".NVD")
							|| fldname.endsWith(".nsc")
							|| fldname.endsWith(".NSC")
							|| fldname.endsWith(".nix")
							|| fldname.endsWith(".NIX")
							|| fldname.endsWith(".omcr")
							|| fldname.endsWith(".OMCR")
							|| fldname.endsWith(".omcd")
							|| fldname.endsWith(".OMCD")
							|| fldname.endsWith(".omc")
							|| fldname.endsWith(".OMC")
							|| fldname.endsWith(".oda")
							|| fldname.endsWith(".ODA")
							|| fldname.endsWith(".w60")
							|| fldname.endsWith(".W60")
							|| fldname.endsWith(".vsw")
							|| fldname.endsWith(".VSW")
							|| fldname.endsWith(".vst")
							|| fldname.endsWith(".VST")
							|| fldname.endsWith(".vsd")
							|| fldname.endsWith(".VSD")
							|| fldname.endsWith(".w61")
							|| fldname.endsWith(".W61")
							|| fldname.endsWith(".w6w")
							|| fldname.endsWith(".W6W")
							|| fldname.endsWith(".wb1")
							|| fldname.endsWith(".WB1")
							|| fldname.endsWith(".web")
							|| fldname.endsWith(".WEB")
							|| fldname.endsWith(".wiz")
							|| fldname.endsWith(".WIZ")
							|| fldname.endsWith(".wk1")
							|| fldname.endsWith(".WK1")
							|| fldname.endsWith(".wmlc")
							|| fldname.endsWith(".WMLC")
							|| fldname.endsWith(".wsrc")
							|| fldname.endsWith(".WSRC")
							|| fldname.endsWith(".wtk")
							|| fldname.endsWith(".WTK")
							|| fldname.endsWith(".wmlsc")
							|| fldname.endsWith(".WMLSC")
							|| fldname.endsWith(".word")
							|| fldname.endsWith(".WORD")
							|| fldname.endsWith(".wp")
							|| fldname.endsWith(".WP")
							|| fldname.endsWith(".wp5")
							|| fldname.endsWith(".WP5")
							|| fldname.endsWith(".wp6")
							|| fldname.endsWith(".WP6")
							|| fldname.endsWith(".wpd")
							|| fldname.endsWith(".WPD")
							|| fldname.endsWith(".wql")
							|| fldname.endsWith(".WQL")
							|| fldname.endsWith(".wri")
							|| fldname.endsWith(".WRI")
							|| fldname.endsWith(".z") || fldname.endsWith(".Z")
							|| fldname.endsWith(".zoo")
							|| fldname.endsWith(".ZOO")
							|| fldname.endsWith(".spl")
							|| fldname.endsWith(".SPL")
							|| fldname.endsWith(".spr")
							|| fldname.endsWith(".SPR")
							|| fldname.endsWith(".sprite")
							|| fldname.endsWith(".SPRITE")
							|| fldname.endsWith(".src")
							|| fldname.endsWith(".SRC")
							|| fldname.endsWith(".ssm")
							|| fldname.endsWith(".SSM")
							|| fldname.endsWith(".sst")
							|| fldname.endsWith(".SST")
							|| fldname.endsWith(".step")
							|| fldname.endsWith(".STEP")
							|| fldname.endsWith(".stl")
							|| fldname.endsWith(".STL")
							|| fldname.endsWith(".stp")
							|| fldname.endsWith(".STP")
							|| fldname.endsWith(".sv4cpio")
							|| fldname.endsWith(".SV4CPIO")
							|| fldname.endsWith(".sv4crc")
							|| fldname.endsWith(".SV4CRC")
							|| fldname.endsWith(".svr")
							|| fldname.endsWith(".SVR")
							|| fldname.endsWith(".sol")
							|| fldname.endsWith(".SOL")
							|| fldname.endsWith(".tar")
							|| fldname.endsWith(".TAR")
							|| fldname.endsWith(".tbk")
							|| fldname.endsWith(".TBK")
							|| fldname.endsWith(".tcl")
							|| fldname.endsWith(".TCL")
							|| fldname.endsWith(".swf")
							|| fldname.endsWith(".SWF")
							|| fldname.endsWith(".t") || fldname.endsWith(".T")
							|| fldname.endsWith(".tgz")
							|| fldname.endsWith(".TGZ")
							|| fldname.endsWith(".tex")
							|| fldname.endsWith(".TEX")
							|| fldname.endsWith(".texi")
							|| fldname.endsWith(".TEXI")
							|| fldname.endsWith(".texinfo")
							|| fldname.endsWith(".TEXINFO")
							|| fldname.endsWith(".tr")
							|| fldname.endsWith(".TR")
							|| fldname.endsWith(".vmd")
							|| fldname.endsWith(".VMD")
							|| fldname.endsWith(".vmf")
							|| fldname.endsWith(".VMF")
							|| fldname.endsWith(".vrml")
							|| fldname.endsWith(".VRML")
							|| fldname.endsWith(".vew")
							|| fldname.endsWith(".VEW")
							|| fldname.endsWith(".vda")
							|| fldname.endsWith(".VDA")
							|| fldname.endsWith(".unv")
							|| fldname.endsWith(".UNV")
							|| fldname.endsWith(".vcd")
							|| fldname.endsWith(".VCD")
							|| fldname.endsWith(".rtx")
							|| fldname.endsWith(".RTX")
							|| fldname.endsWith(".ima")
							|| fldname.endsWith(".IMA")
							|| fldname.endsWith(".imap")
							|| fldname.endsWith(".IMAP")
							|| fldname.endsWith(".inf")
							|| fldname.endsWith(".INF")
							|| fldname.endsWith(".ins")
							|| fldname.endsWith(".INS")
							|| fldname.endsWith(".ima")
							|| fldname.endsWith(".IMA")
							|| fldname.endsWith(".inf")
							|| fldname.endsWith(".INF")
							|| fldname.endsWith(".ins")
							|| fldname.endsWith(".INS")
							|| fldname.endsWith(".ip")
							|| fldname.endsWith(".IP")
							|| fldname.endsWith(".iv")
							|| fldname.endsWith(".IV")
							|| fldname.endsWith(".hdf")
							|| fldname.endsWith(".HDF")
							|| fldname.endsWith(".help")
							|| fldname.endsWith(".HELP")
							|| fldname.endsWith(".hgl")
							|| fldname.endsWith(".HGL")
							|| fldname.endsWith(".frl")
							|| fldname.endsWith(".FRL")
							|| fldname.endsWith(".fdf")
							|| fldname.endsWith(".FDF")
							|| fldname.endsWith(".pcl")
							|| fldname.endsWith(".PCL")
							|| fldname.endsWith(".pm4")
							|| fldname.endsWith(".PM4")
							|| fldname.endsWith(".pm5")
							|| fldname.endsWith(".PM5")
							|| fldname.endsWith(".plx")
							|| fldname.endsWith(".PLX")
							|| fldname.endsWith(".pwz")
							|| fldname.endsWith(".PWZ")
							|| fldname.endsWith(".ppa")
							|| fldname.endsWith(".PPA")
							|| fldname.endsWith(".pyc")
							|| fldname.endsWith(".PYC")
							|| fldname.endsWith(".pkg")
							|| fldname.endsWith(".PKG")
							|| fldname.endsWith(".pko")
							|| fldname.endsWith(".PKO")
							|| fldname.endsWith(".nc")
							|| fldname.endsWith(".NC")
							|| fldname.endsWith(".ncm")
							|| fldname.endsWith(".NCM")
							|| fldname.endsWith(".mzz")
							|| fldname.endsWith(".MZZ")
							|| fldname.endsWith(".mm")
							|| fldname.endsWith(".MM")
							|| fldname.endsWith(".mme")
							|| fldname.endsWith(".MME")
							|| fldname.endsWith(".mpc")
							|| fldname.endsWith(".MPC")
							|| fldname.endsWith(".mif")
							|| fldname.endsWith(".MIF")
							|| fldname.endsWith(".shar")
							|| fldname.endsWith(".SHAR")
							|| fldname.endsWith(".sdp")
							|| fldname.endsWith(".SDP")
							|| fldname.endsWith(".sdr")
							|| fldname.endsWith(".SDR")
							|| fldname.endsWith(".sea")
							|| fldname.endsWith(".SEA")
							|| fldname.endsWith(".sit")
							|| fldname.endsWith(".SIT")
							|| fldname.endsWith(".smil")
							|| fldname.endsWith(".SMIL")
							|| fldname.endsWith(".smi")
							|| fldname.endsWith(".SMI")
							|| fldname.endsWith(".sl")
							|| fldname.endsWith(".SL")
							|| fldname.endsWith(".skt")
							|| fldname.endsWith(".SKT")
							|| fldname.endsWith(".skm")
							|| fldname.endsWith(".SKM")
							|| fldname.endsWith(".skp")
							|| fldname.endsWith(".SKP")
							|| fldname.endsWith(".skd")
							|| fldname.endsWith(".SKD")
							|| fldname.endsWith(".ppz")
							|| fldname.endsWith(".PPZ")
							|| fldname.endsWith(".set")
							|| fldname.endsWith(".SET")
							|| fldname.endsWith(".pre")
							|| fldname.endsWith(".PRE")
							|| fldname.endsWith(".prt")
							|| fldname.endsWith(".PRT")
							|| fldname.endsWith(".ps")
							|| fldname.endsWith(".PS")
							|| fldname.endsWith(".psd")
							|| fldname.endsWith(".PSD")
							|| fldname.endsWith(".jcm")
							|| fldname.endsWith(".JCM")
							|| fldname.endsWith(".ivy")
							|| fldname.endsWith(".IVY")
							|| fldname.endsWith(".latex")
							|| fldname.endsWith(".LATEX")
							|| fldname.endsWith(".lha")
							|| fldname.endsWith(".LHA")
							|| fldname.endsWith(".lhx")
							|| fldname.endsWith(".LHX")
							|| fldname.endsWith(".man")
							|| fldname.endsWith(".MAN")
							|| fldname.endsWith(".map")
							|| fldname.endsWith(".MAP")
							|| fldname.endsWith(".ltx")
							|| fldname.endsWith(".ITX")
							|| fldname.endsWith(".lzh")
							|| fldname.endsWith(".LZH")
							|| fldname.endsWith(".lzx")
							|| fldname.endsWith(".LZX")
							|| fldname.endsWith(".mdb")
							|| fldname.endsWith(".MDB")
							|| fldname.endsWith(".mc$")
							|| fldname.endsWith(".MC$")
							|| fldname.endsWith(".mcd")
							|| fldname.endsWith(".MCD")
							|| fldname.endsWith(".mcp")
							|| fldname.endsWith(".MCP")
							|| fldname.endsWith(".me")
							|| fldname.endsWith(".ME")
							|| fldname.endsWith(".rng")
							|| fldname.endsWith(".RNG")
							|| fldname.endsWith(".rnx")
							|| fldname.endsWith(".RNX")
							|| fldname.endsWith(".roff")
							|| fldname.endsWith(".ROFF")
							|| fldname.endsWith(".saveme")
							|| fldname.endsWith(".SAVEME")
							|| fldname.endsWith(".sbk")
							|| fldname.endsWith(".SBK")) {

						add1(j, 2);
					} else {
						add1(j, 2);
					}

				}
			}

			Collections.sort(order1, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order2, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			for (int j = 0; j < order1.size(); j++) {
				Log.d("sort", order1.get(j).get(Files_Db.File_name));
			}

			for (int j = 0; j < order2.size(); j++) {
				Log.d("sort", order2.get(j).get(Files_Db.File_name));
			}

			re_contactList2.clear();
			re_contactList2.addAll(order1);
			re_contactList2.addAll(order2);


			
			if (re_contactList2.size() != 0) {
				Log.d("from response finish", "finishsssshhhh");

				free_lv = (ListView) findViewById(R.id.sub_lv);
				list_adapt_fld = new SimAdapter(getApplicationContext(),
						re_contactList2);
				free_lv.setAdapter(list_adapt_fld);
				Log.d("from response finish", "finishhhhh");
			}

		}

		if (prg_bar.isShowing()) {
			prg_bar.setCancelable(true);
			prg_bar.cancel();
			prg_bar.dismiss();
		}

	}

	private void add1(int j, int l) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Files_Db.File_id, re_contactList.get(j).get(Files_Db.File_id));
		map.put(Files_Db.File_name,
				re_contactList.get(j).get(Files_Db.File_name));
		Log.d("name_third", re_contactList.get(j).get(Files_Db.File_name));
		Log.d("3", String.valueOf(l));
		map.put(Files_Db.File_createat,
				re_contactList.get(j).get(Files_Db.File_createat));
		map.put(Files_Db.File_size,
				re_contactList.get(j).get(Files_Db.File_size));
		map.put(Files_Db.File_folder,
				re_contactList.get(j).get(Files_Db.File_folder));
		map.put(Files_Db.File_url, re_contactList.get(j).get(Files_Db.File_url));
		map.put(Files_Db.File_thumb,
				re_contactList.get(j).get(Files_Db.File_thumb));
		if (l == 1) {
			order1.add(map);
		} else if (l == 2) {
			order2.add(map);
		}

	}

	public int isUserAvailable(String select_item) {

		int number = 0;
		Cursor cursor = null;
		Log.d("Gallery", "cursor1");
		try {
			cursor = Constant.db_sql.rawQuery(select_item, null);
			number = cursor.getCount();
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("Gallery", "cursor2");
		Log.d("Gallery", String.valueOf(number));
		return number;
	}

	private void stack() {

		if (new_fold) {
			new_fold = false;
			if (all_path.size() >= 1) {
				dialog();
			} else {
				setResult(RESULT_OK);
				finish();
				toastsettext("Select anyone to add SendMyFiles");
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
									setResult(RESULT_OK);
									finish();
									toastsettext("Select anyone to add Whootin Files");
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
				toastsettext("Select anyone to add Whootin Files");
			}
		}

	}

	private void check(String folder_name2) {
		String get_name = null;
		String get_id = null;

		ArrayList<HashMap<String, String>> contact = Constant.db
				.getAllTranslates(Table_name, bool);

		for (int j = 0; j < contact.size(); j++) {
			String fld = contact.get(j).get(Files_Db.File_name);
			String flid = contact.get(j).get(Files_Db.File_id);
			if (folder_name2.contentEquals(fld)) {
				Log.d("s-2", fld);
				Log.d("flid", flid);
				folder_name = fld;
				Constant.con_fldid = fld;
				Constant.con_fldnm = flid;
				get_id = flid;
				get_name = fld;
				break;
			}
		}

		if (get_name == null) {
			if (prg_bar.isShowing()) {

			} else {
				prg_bar.show();
			}
			new_fold = true;
			new post().execute();
			free_txt.setText("");
		} else {
			final TextView alert1;
			final Button yes_btn, no_btn;
			AlertDialog.Builder build_dialog;
			final AlertDialog alert_dialog;
			final ListView lv;

			build_dialog = new AlertDialog.Builder(context);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.altdialog_fldnt,
					(ViewGroup) findViewById(R.id.nf_sample));
			no_btn = (Button) layout.findViewById(R.id.nf_btn_cancel);
			yes_btn = (Button) layout.findViewById(R.id.nf_btn_ok);
			alert1 = (TextView) layout.findViewById(R.id.nf_txt_hd);
			build_dialog.setView(layout);
			alert_dialog = build_dialog.create();
			alert_dialog.setView(layout, 0, 0, 0, 0);

			alert1.setText(folder_name
					+ " is already existed! Do you want to add samefolder?");

			yes_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					free_txt.setText("");
					if (boo_upld) {

					} else {
						stack();
					}
					alert_dialog.dismiss();
				}

			});

			no_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					alert_dialog.dismiss();
					toastsettext("Enter New foldername");
				}
			});

			alert1.setMovementMethod(new ScrollingMovementMethod());
			alert_dialog.show();
			alert1.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (v.getId() == R.id.exed_txt_hd) {
						v.getParent().requestDisallowInterceptTouchEvent(true);
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
						case MotionEvent.ACTION_UP:
							v.getParent().requestDisallowInterceptTouchEvent(
									false);
							break;
						}
					}
					return false;
				}
			});
		}

	}

	public class post extends AsyncTask<URL, String, Long> {
		@Override
		protected Long doInBackground(URL... arg0) {
			long totalSize = 0;
			return totalSize;

		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			whootin_multi_post_fold();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (prg_bar.isShowing()) {

			} else {
				if (prg_bar != null) {
					prg_bar.show();
				}
			}

		}

	}

	private void whootin_multi_post_fold() {
		if (Constant.isInternetPresent) {

			Constant.datee = getSharedPreferences("datefun",
					MODE_WORLD_WRITEABLE);
			if (Constant.datee != null
					&& Constant.datee.contains("access_token")) {

				Constant.accesstoken = "Bearer "
						+ Constant.datee.getString("access_token", null);
				Log.d("login", Constant.accesstoken);
			}
			RequestParams params = new RequestParams();
			Constant.con_fldnm = folder_name;
			if (Constant.con_fldid == null
					|| Constant.con_fldid.contentEquals("item")) {
				params.put("name", folder_name);
			} else {
				params.put("folder_id", Constant.con_fldid);
				params.put("name", folder_name);
			}

			AsyncHttpClient client = new AsyncHttpClient();
			client.addHeader("Content-type", "multipart/form-data");
			client.addHeader("Content-type",
					"application/x-www-form-urlencoded; charset=utf-8");
			client.addHeader("Accept", "appliction/json");
			client.addHeader("Content-Transfer-Encoding", "binary");
			client.addHeader("Authorization", Constant.accesstoken);
			client.post("http://whootin.com/api/v1/folders/new?", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(String arg0) {
							super.onSuccess(arg0);
							Log.d("create success folder", arg0
									+ "from success");
							String id = null, name = null;
							try {
								JSONObject obj3 = new JSONObject(arg0);
								id = obj3.getString(Files_Db.File_id);
								name = obj3.getString(Files_Db.File_name);
								Log.d("id", String.valueOf(Constant.con_fldnm));
							} catch (JSONException e) {
								e.printStackTrace();
							}
							boo_sce = true;

							try {
								Thread.sleep(6000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							Constant.con_fldid = id;
							Constant.con_fldnm = name;
							Constant.con_booup = true;
							new download(Constant.con_fldnm)
									.execute(Constant.con_fldid);

						}

						@Override
						public void onFinish() {
							super.onFinish();
							Log.d("from finish", "from finish");
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

		} else {
			toastsettext("No Internet Connection");
		}

	}

	/***** ----------------Delete Functions --------------- *****/

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

	private void dlt_excepted() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dialog();
		Log.d("delete", "delete");
	}

	/***** ----------------Delete Whootin Files --------------- *****/

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

	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 999) {
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK);
				finish();
			}
		}

		if (requestCode == 77) {
			if (resultCode == RESULT_OK) {
				setResult(RESULT_OK);
				finish();
			}
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
					all_path.clear();
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

}
