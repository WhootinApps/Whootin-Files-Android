package com.whootin.wf;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.Destroyable;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.devsmart.android.ui.HorizontalListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.whootin.passcodelock.AppLockManager;
import com.whootin.passcodelock.PasscodeUnlockActivity;
import com.whootin.wf.Folder_LoginActivity.SimAdapter;
import com.whootin.whootinfiles_db.ConnectionDetector;
import com.whootin.whootinfiles_db.Constant;
import com.whootin.whootinfiles_db.ContentValue;
import com.whootin.whootinfiles_db.Files_Db;
import com.whootin.whootinfiles_db.HttpConnection;
import com.whootin.whootinfiles_db.UserProfileImagew;
import com.whootin.whootinfiles_gallery.CustomGalleryAct;
import com.whootin.whootinfiles_gallery.CustomGalleryActivityGrid;
import com.whootin.whootinfiles_gallery.CustomGalleryAct.delete1;
import com.whootin.whootinfiles_gallery.CustomGalleryAct.delete2;
import com.whootin.whootinfiles_gallery.CustomGalleryActivityGrid.SimAdapteredpg;
import com.whootin.whootinfiles_gallery.CustomGalleryActivityGrid.download1;
import com.whootin.whootinfiles_gallery.CustomGalleryActivityGrid.download2;
import com.whootin.whootinfiles_quickaction.ActionItem;
import com.whootin.whootinfiles_quickaction.QuickAction;
import com.whootin.whootinfiles_sdcard.Sd_Folder;
import com.whootin.whootinfiles_sdcard.Sdexport_Activity;
import com.whootin.whootinfiles_sdcard.Subfolder_Activity;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent.OnFinished;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore;
import android.provider.MediaStore.Video;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressWarnings("unused")
public class Login_Activity extends Activity implements OnClickListener {

	static final int RC_REQUEST = 10001;
	static final String TAG = "TrivialDrive";
	private String sets = null;

	private HorizontalListView lv_hmp, lv_hadd;
	private String user_uurl = null, user_uname = null, user_usize = null,
			user_plan = null;
	boolean boo_stng = false;


	private Button btn_az, btn_time, btn_name, btn_visible;
	private RelativeLayout rl_black;
	private String valu = "0";

	String un_edit = null;

	private ArrayList<String> all_path = null;
	private List<String> fresh_path = null;
	private List<String> re_path = null;
	private List<String> dlt_path = null;

	private CheckBox signup_checkbox1;
	private WebView webpage_signup1, webpage_signup2;
	private Button btn_okterm, signup_checkbox2, btn_okpolicy, btn_backterm,
			btn_backpolicy, btn_select, btn_privacy;
	private DatePicker setdPSignUp;

	private boolean common = false, dlt = false, new_fold = false,
			boo_dlt = false;
	private String get_url = null, dlt_stng = null;

	public File imgtemp = null;
	public WakeLock wakeLock;
	public File vidtemp = null;
	private File file;
	private String get = null;
	private int fld_pos = 0;
	private int sdkVersion = Build.VERSION.SDK_INT;
	private final ArrayList<ManagedCursor> mManagedCursors = new ArrayList<ManagedCursor>();
	private ArrayList<HashMap<String, String>> contactList_fld = new ArrayList<HashMap<String, String>>();
	String Table_name = null;
	private List<String> item = null;
	private String root = "/sdcard";
	private boolean shared = false;
	private String folder_name = null;
	private String get_fldid = null;

	private SocialAuthAdapter adapter;
	private Boolean fb_boo = false, twit_boo = false, mail_boo = false;
	private String fld_url = null, return_fld_url = null, fld_nm = null,
			flt_typ = null, fld_fetc = null;

	private ShareActionProvider mShareActionProvider;

	// private ListView list_sd;

	private static Cursor cursor_high = null;
	private static Cursor cursor_low = null;
	public static InputStream inputStreamVideo = null;
	private InputStream inputStream = null;
	public Uri mVideoURI = null;
	public Bitmap curThumb = null;
	private String videoPath, picturePath;
	private String lineEnd = "\r\n";
	private Date dateTaken;
	private Boolean bool = true;

	private ViewFlipper vf_lgn, vf_lgn2, vf_lgn1;

	private ListView free_lv, lv_pg;
	private TextView free_txt, txt_pg, txt_pgmssg, txt_pgsucs;
	private int h = 0;
	private SeekBar seekbar_pg;
	private Dialog d;
	private ListAdapter list_adaptpgm;
	private ArrayList<HashMap<String, String>> re_contactListpg = new ArrayList<HashMap<String, String>>();
	private boolean bo_pg = false;

	private ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> re_contactList = new ArrayList<HashMap<String, String>>();
	private ArrayList<HashMap<String, String>> re_contactList2 = new ArrayList<HashMap<String, String>>();

	private static ArrayList<HashMap<String, String>> order1 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order2 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order3 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order4 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order5 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order6 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order7 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order8 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order9 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order10 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order11 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order12 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order13 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order14 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order15 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order16 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order17 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order18 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order19 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order20 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order21 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order22 = new ArrayList<HashMap<String, String>>();
	private static ArrayList<HashMap<String, String>> order23 = new ArrayList<HashMap<String, String>>();

	private Boolean insert = false;
	private static final int DATE_DIALOG_ID = 999;
	private Calendar myCalendar = Calendar.getInstance();
	private EditText edt_unamel, edt_pswdl;
	private Button btn_lgn, btn_plus, btn_sgn, btn_sgns, btn_bcks, btn_logout,
			btn_newfolder;
	private String get_usrnm, get_pswd;
	private Context context;
	private ProgressDialog prg_bar, prg_bar1;
	// private boolean fld_booler = false;
	private int year, month, day;
	private ListView listview_add;
	private ListAdapter list_adapt;
	private String url_url = null;

	private EditText edt_unames, edt_pswds, edt_emails, edt_rpswds, edt_names,
			edt_dobSignUp, edt_lnames;

	private QuickAction mQuickAction_folder = null;
	private QuickAction mQuickAction_file = null;
	private QuickAction mQuickAction_btn = null;

	private boolean boo_main = false;
	public static String currentDateandTime = null;

	// delete file or folder string declaration
	private String dlt_id = null, fld_typ = null;
	private boolean dlt_bln = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById();
		declaration();
	};

	private void findViewById() {
		// list_sd = (ListView) findViewById(R.id.sd_list);

		lv_hmp = (HorizontalListView) findViewById(R.id.mnpg_hlistview);
		lv_hadd = (HorizontalListView) findViewById(R.id.add_hlistview);

		vf_lgn2 = (ViewFlipper) findViewById(R.id.lgn_viewflipper2);
		vf_lgn = (ViewFlipper) findViewById(R.id.lgn_viewflipper);
		vf_lgn1 = (ViewFlipper) findViewById(R.id.lgn_viewflipper3);
		edt_unamel = (EditText) findViewById(R.id.lgn_usrnm);
		edt_pswdl = (EditText) findViewById(R.id.lgn_pswd);
		btn_lgn = (Button) findViewById(R.id.lgn_login);
		btn_sgns = (Button) findViewById(R.id.lgn_reg);
		btn_newfolder = (Button) findViewById(R.id.mnpg_minusbtn);

		edt_names = (EditText) findViewById(R.id.sgn_setName);
		edt_dobSignUp = (EditText) findViewById(R.id.sgn_setdob);
		edt_lnames = (EditText) findViewById(R.id.sgn_setFName);
		edt_unames = (EditText) findViewById(R.id.sgn_setUname);
		edt_emails = (EditText) findViewById(R.id.sgn_setEmail);
		edt_pswds = (EditText) findViewById(R.id.sgn_setpassword);
		edt_rpswds = (EditText) findViewById(R.id.sgn_setReenterPassword);
		setdPSignUp = (DatePicker) findViewById(R.id.datePickerSignUp);
		signup_checkbox1 = (CheckBox) findViewById(R.id.checkbox1);
		signup_checkbox2 = (Button) findViewById(R.id.checkbox2);
		webpage_signup1 = (WebView) findViewById(R.id.webpage_terms);
		webpage_signup2 = (WebView) findViewById(R.id.webpage_policy);
		btn_backpolicy = (Button) findViewById(R.id.btn_policyback);
		btn_backterm = (Button) findViewById(R.id.btn_termsback);
		btn_okpolicy = (Button) findViewById(R.id.btn_policyok);
		btn_okterm = (Button) findViewById(R.id.btn_termsok);
		btn_select = (Button) findViewById(R.id.selectdate);
		btn_sgn = (Button) findViewById(R.id.sgn_success);
		btn_bcks = (Button) findViewById(R.id.sgn_bckbtn);
		btn_logout = (Button) findViewById(R.id.mnpg_lgtbtn);
		btn_plus = (Button) findViewById(R.id.mnpg_plusbtn);
		listview_add = (ListView) findViewById(R.id.mnpg_listview);

		btn_az = (Button) findViewById(R.id.mnpg_az);
		btn_time = (Button) findViewById(R.id.mnpg_time);
		btn_name = (Button) findViewById(R.id.mnpg_name);
		rl_black = (RelativeLayout) findViewById(R.id.mnpg_blckrl);
		btn_visible = (Button) findViewById(R.id.mnpg_alv);

		btn_cncladd = (Button) findViewById(R.id.add_btn_cancel);
		btn_addadd = (Button) findViewById(R.id.add_btn_add);
		btn_fldadd = (Button) findViewById(R.id.add_btn_fldadd);
		btn_backadd = (Button) findViewById(R.id.add_back);
		lv_add = (ListView) findViewById(R.id.add_listView1);
		tx_add = (TextView) findViewById(R.id.add_txt_hd);

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
			arrow.setVisibility(View.GONE);
			if(Constant.con_name.get(position).contentEquals("  Home  ")){
				title.setText("Home");
			}else{
				title.setText(Constant.con_name.get(position));
			}
			

			return retval;
		}

	};

	private void declaration() {
		context = this;

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		AppLockManager.getInstance().enableDefaultAppLockIfAvailable(
				getApplication());

		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
				"no sleep");
		wakeLock.acquire();

		Constant.con_fldid = get_fldid;

		prg_bar = new ProgressDialog(Login_Activity.this);
		prg_bar.setMessage("please wait...");
		prg_bar.setIndeterminate(false);
		prg_bar.setCancelable(false);
		prg_bar.setMax(100);
		prg_bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		prg_bar1 = new ProgressDialog(Login_Activity.this);
		prg_bar1.setMessage("please wait...");
		prg_bar1.setIndeterminate(false);
		prg_bar1.setCancelable(false);
		prg_bar1.setMax(100);
		prg_bar1.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		WebSettings webSettings = webpage_signup1.getSettings();
		webpage_signup1.getSettings().setBuiltInZoomControls(true);
		webpage_signup1.getSettings().setSupportZoom(true);
		webpage_signup1.setBackgroundColor(Color.TRANSPARENT);
		webpage_signup1.getSettings().setJavaScriptEnabled(true);
		webpage_signup1.setWebViewClient(new AppWebViewClients());

		WebSettings webSetting = webpage_signup2.getSettings();
		webpage_signup2.getSettings().setBuiltInZoomControls(true);
		webpage_signup2.getSettings().setSupportZoom(true);
		webpage_signup2.setBackgroundColor(Color.TRANSPARENT);
		webpage_signup2.getSettings().setJavaScriptEnabled(true);
		webpage_signup2.setWebViewClient(new AppWebViewClients());

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR) - 25;
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		setdPSignUp.init(year, month, day, null);

		btn_sgn.setOnClickListener(this);
		btn_bcks.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		btn_lgn.setOnClickListener(this);
		btn_sgns.setOnClickListener(this);
		btn_plus.setOnClickListener(this);
		btn_newfolder.setOnClickListener(this);

		btn_addadd.setOnClickListener(this);
		btn_fldadd.setOnClickListener(this);
		btn_backadd.setOnClickListener(this);
		btn_cncladd.setOnClickListener(this);

		btn_backpolicy.setOnClickListener(this);
		btn_backterm.setOnClickListener(this);
		btn_okpolicy.setOnClickListener(this);
		btn_okterm.setOnClickListener(this);
		edt_names.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edt_lnames.requestFocus();
			}
		});

		signup_checkbox1
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						Log.d("tag", "msg");

						if (signup_checkbox1.isChecked()) {
							vf_lgn.setVisibility(View.GONE);
							vf_lgn1.setVisibility(View.VISIBLE);
							vf_lgn1.setDisplayedChild(0);
							new doInBackGround1().execute();
						}

					}
				});

		btn_az.setOnClickListener(this);
		btn_time.setOnClickListener(this);
		btn_name.setOnClickListener(this);
		btn_visible.setOnClickListener(this);
		signup_checkbox2.setOnClickListener(this);
		btn_select.setOnClickListener(this);

		list_adapt = new SimAdaptered(getApplicationContext());
		Log.d("thread", "thread");
		Constant.db = new Files_Db(context);
		Constant.db.createDataBase();

		Intent intent = getIntent();
		String action = intent.getAction();
		Bundle extras = intent.getExtras();
		String type = intent.getType();
		all_path = new ArrayList<String>();
		all_path.clear();

		if (Intent.ACTION_SEND.equals(intent.getAction())) {
			Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
			if (uri != null) {
				// String get = getPathfromUri(uri);
				String path = parseUriToFilename(uri);
				all_path.add(path);
				Log.d("path", path);
				ran = true;
			}
		} else if (Intent.ACTION_SEND_MULTIPLE.equals(intent.getAction())) {
			ArrayList<Uri> uris = intent
					.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
			if (uris != null) {
				for (Uri uri : uris) {
					ran = true;
					// String get = getPathfromUri(uri);
					String path = parseUriToFilename(uri);
					all_path.add(path);
					Log.d("path", path);
				}
			}
		}

		ActionItem azz = new ActionItem(id_az, "A-Z", getResources()
				.getDrawable(R.drawable.ico_az_align));
		ActionItem timee = new ActionItem(id_time, "Time", getResources()
				.getDrawable(R.drawable.ico_date_align));
		ActionItem namee = new ActionItem(id_name, "Name", getResources()
				.getDrawable(R.drawable.ico_type_align));
		ActionItem set = new ActionItem(id_set, "set", getResources()
				.getDrawable(R.drawable.btn_setting));

		ActionItem shareItem = new ActionItem(id_share, "Share", getResources()
				.getDrawable(R.drawable.btn_popup_share));
		ActionItem deleteItem = new ActionItem(id_delete, "Delete",
				getResources().getDrawable(R.drawable.btn_popup_delete));
		ActionItem renameItem = new ActionItem(id_rename, "Rename",
				getResources().getDrawable(R.drawable.btn_popup_rename));
		ActionItem moreItem = new ActionItem(id_more, "More", getResources()
				.getDrawable(R.drawable.btn_export1));

		mQuickAction_folder = new QuickAction(this);
		mQuickAction_file = new QuickAction(this);
		mQuickAction_btn = new QuickAction(this);

		// mQuickAction_folder.addActionItem(shareItem);
		mQuickAction_folder.addActionItem(deleteItem);
		mQuickAction_folder.addActionItem(renameItem);
		// mQuickAction_folder.addActionItem(moreItem);

		mQuickAction_btn.addActionItem(azz);
		mQuickAction_btn.addActionItem(timee);
		mQuickAction_btn.addActionItem(namee);
		mQuickAction_btn.addActionItem(set);

		mQuickAction_file.addActionItem(shareItem);
		mQuickAction_file.addActionItem(deleteItem);
		mQuickAction_file.addActionItem(renameItem);
		mQuickAction_file.addActionItem(moreItem);

		// setup the action item click listener
		mQuickAction_folder
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);
						Constant.cd = new ConnectionDetector(context);
						Constant.isInternetPresent = Constant.cd
								.isConnectingToInternet();
						if (Constant.isInternetPresent) {
							if (actionId == id_delete) {
								delete();
							} else if (actionId == id_rename) {
								rename();
							}
						} else {
							toastsettext("No Internet Connection");
						}

					}

				});

		mQuickAction_folder
				.setOnDismissListener(new QuickAction.OnDismissListener() {
					@Override
					public void onDismiss() {

					}
				});

		mQuickAction_btn
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						ActionItem actionItem = quickAction.getActionItem(pos);
						Constant.cd = new ConnectionDetector(context);
						Constant.isInternetPresent = Constant.cd
								.isConnectingToInternet();
						if (Constant.isInternetPresent) {
							if (actionId == id_az) {
								Constant.vallcom = Constant.vall.edit();
								Constant.vallcom.putString("values", "0");
								Constant.vallcom.commit();
								valu = "0";
								new download().execute(valu);
							} else if (actionId == id_time) {
								Constant.vallcom = Constant.vall.edit();
								Constant.vallcom.putString("values", "1");
								Constant.vallcom.commit();
								valu = "1";
								new download().execute(valu);
							} else if (actionId == id_name) {
								Constant.vallcom = Constant.vall.edit();
								Constant.vallcom.putString("values", "2");
								Constant.vallcom.commit();
								valu = "2";
								new download().execute(valu);
							} else if (actionId == id_set) {
								boo_stng = true;
								whootin_user();
							}
						} else {
							toastsettext("No Internet Connection");
						}

					}

				});

		mQuickAction_btn
				.setOnDismissListener(new QuickAction.OnDismissListener() {
					@Override
					public void onDismiss() {

					}
				});

		mQuickAction_file
				.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
					@Override
					public void onItemClick(QuickAction quickAction, int pos,
							int actionId) {
						Constant.cd = new ConnectionDetector(context);
						Constant.isInternetPresent = Constant.cd
								.isConnectingToInternet();
						if (Constant.isInternetPresent) {
							if (actionId == id_delete) {
								delete();
							} else if (actionId == id_more) {
								export();
							} else if (actionId == id_share) {
								boolean share = true;
								share(share);
							} else if (actionId == id_rename) {
								rename();
							}
						} else {
							toastsettext("No Internet Connection");
						}
					}
				});

		mQuickAction_file
				.setOnDismissListener(new QuickAction.OnDismissListener() {
					@Override
					public void onDismiss() {

					}
				});

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
	public void onClick(View v) {

		if (v == btn_newfolder) {
			Intent intent = new Intent(context, Subfolder_Activity.class);
			Bundle bun = new Bundle();
			bun.putBoolean("boo_up", true);
			bun.putString("new_fold", "fold");
			bun.putString("fld_nm", "Home  --> ");
			bun.putString("fld_id", "item");
			intent.putExtras(bun);
			startActivityForResult(intent, 222);
		} else if (v == btn_addadd) {
			if (new_fold) {
				new_fold = false;
				if (all_path.size() >= 1) {

					if (Constant.isInternetPresent) {
						dialog();
					} else {
						toastsettext("No Internet Connection");
					}
				} else {
					super.finish();
					toastsettext("Select anyone to add Whootin Files ");
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
								if (Constant.isInternetPresent) {
									d_delete();
								} else {
									toastsettext("No Internet Connection");
								}
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
									Log.d("get",
											String.valueOf(all_path.size()));
									if (all_path.size() >= 1) {
										if (Constant.isInternetPresent) {
											dialog();
										} else {
											toastsettext("No Internet Connection");
										}
									} else {

										toastsettext("Select anyone to add Whootin Files ");
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
					toastsettext("Select anyone to add Whootin Files ");
				}
			}

		} else if (v == btn_az) {
			valu = "0";
			rl_black.setVisibility(View.GONE);
			if (Constant.isInternetPresent) {
				new download().execute(valu);
			} else {
				toastsettext("No Internet Connection");
			}
		} else if (v == btn_backpolicy) {
			backy();
		} else if (v == signup_checkbox2) {
			vf_lgn.setVisibility(View.GONE);
			vf_lgn1.setVisibility(View.VISIBLE);
			vf_lgn1.setDisplayedChild(1);
			new doInBackGround2().execute();
		} else if (v == btn_select) {
			showDialog(DATE_DIALOG_ID);
		} else if (v == btn_backterm) {
			signup_checkbox1.setChecked(false);
			backy();
		} else if (v == btn_okpolicy) {
			backy();
		} else if (v == btn_okterm) {
			backy();
			signup_checkbox1.setChecked(true);
		} else if (v == btn_time) {
			rl_black.setVisibility(View.GONE);
			valu = "1";
			if (Constant.isInternetPresent) {
				new download().execute(valu);
			} else {
				toastsettext("No Internet Connection");
			}
		} else if (v == btn_visible) {
			mQuickAction_btn.show(v);
		} else if (v == btn_name) {
			valu = "2";
			rl_black.setVisibility(View.GONE);
			if (Constant.isInternetPresent) {
				new download().execute(valu);
			} else {
				toastsettext("No Internet Connection");
			}
		} else if (v == btn_backadd) {
			ran = false;
			Login_Activity.this.finish();

		} else if (v == btn_fldadd) {
			common = true;
			btn_folder();
		} else if (v == btn_cncladd) {
			ran = false;
			Login_Activity.this.finish();

		} else if (v == btn_plus) {

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
					if (mQuickAction_file != null) {
						mQuickAction_file.dismiss();
					}
					if (mQuickAction_folder != null) {
						mQuickAction_folder.dismiss();
					}
					if (mQuickAction_btn != null) {
						mQuickAction_btn.dismiss();
					}
					Constant.con_fldid = null;
					Constant.con_fldnm = null;
					if (Constant.con_name.size() > 0) {
						Constant.con_name.clear();
					}
					if (Constant.con_list.size() > 0) {
						Constant.con_list.clear();
					}
					Intent intent = new Intent(Login_Activity.this,
							Sd_Folder.class);
					Bundle bundle = new Bundle();
					bundle.putString("new_fold", "fold");
					intent.putExtras(bundle);
					startActivityForResult(intent, 789);
				}
			});

			gallery.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					alert_dialog.dismiss();
					if (mQuickAction_file != null) {
						mQuickAction_file.dismiss();
					}
					if (mQuickAction_folder != null) {
						mQuickAction_folder.dismiss();
					}
					if (mQuickAction_btn != null) {
						mQuickAction_btn.dismiss();
					}

					Constant.con_fldid = null;
					Constant.con_fldnm = null;
					if (Constant.con_name.size() > 0) {
						Constant.con_name.clear();
					}
					if (Constant.con_list.size() > 0) {
						Constant.con_list.clear();
					}
					Intent intent = new Intent(Login_Activity.this,
							CustomGalleryAct.class);
					Bundle bundle = new Bundle();
					bundle.putString("new_fold", "fold");
					intent.putExtras(bundle);
					startActivityForResult(intent, 789);
				}
			});

			alert_dialog.show();

		} else if (v == btn_sgns) {
			Constant.hideSoftKeyboard(Login_Activity.this, v);
			vf_lgn2.setVisibility(View.GONE);
			vf_lgn.setVisibility(View.VISIBLE);
			vf_lgn.setDisplayedChild(1);
			edt_emails.setText("");
			edt_names.setText("");
			edt_unames.setText("");
			edt_dobSignUp.setText("");
			edt_lnames.setText("");
			edt_rpswds.setText("");
			edt_pswds.setText("");
			signup_checkbox1.setChecked(false);
		} else if (v == btn_bcks) {
			Constant.hideSoftKeyboard(Login_Activity.this, v);
			edt_emails.setText("");
			edt_names.setText("");
			edt_unames.setText("");
			edt_dobSignUp.setText("");
			edt_lnames.setText("");
			edt_rpswds.setText("");
			edt_pswds.setText("");
			edt_unamel.setText("");
			edt_pswdl.setText("");
			signup_checkbox1.setChecked(false);
			vf_lgn2.setVisibility(View.GONE);
			vf_lgn.setVisibility(View.VISIBLE);
			vf_lgn.setDisplayedChild(0);
			btn_bcks.setBackgroundResource(R.drawable.btn_back_normal);

		} else if (v == btn_sgn) {

			Constant.hideSoftKeyboard(Login_Activity.this, v);

			String name = edt_names.getText().toString().trim();
			String uname = edt_unames.getText().toString().trim();
			String email = edt_emails.getText().toString().trim();
			String password = edt_pswds.getText().toString().trim();
			String repassword = edt_rpswds.getText().toString().trim();
			String lname = edt_lnames.getText().toString().trim();
			String dob = edt_dobSignUp.getText().toString().trim();

			Boolean invalid = false;
			invalid = isEmailValid(email);

			if (name.length() == 0) {
				toastsettext("Please enter your First Name");
			} else if (lname.length() == 0) {
				toastsettext("Please enter your Last Name");
			} else if (uname.length() == 0) {
				toastsettext("Please enter Username");
			} else if (email.length() == 0) {
				toastsettext("Please enter your Email");
			} else if (!invalid) {
				toastsettext("Invalid the E-mail id");
			} else if (dob.length() == 0) {
				toastsettext("Please select your dob");
			} else if (password.length() == 0) {
				toastsettext("Please enter your Password");
			} else if (repassword.length() == 0) {
				toastsettext("Please reenter your Confirm Password");
			} else if (signup_checkbox1.isChecked() == false) {
				toastsettext("Please accept terms and conditions");
			} else if (name.length() == 0 || uname.length() == 0
					|| email.length() == 0 || password.length() == 0
					|| repassword.length() == 0) {
				toastsettext("Please enter the all values");
			} else if (password.equals(repassword)) {
				if (name.length() != 0 || uname.length() != 0
						|| email.length() != 0 || password.length() != 0
						|| repassword.length() != 0) {

					name = name + " " + lname;

					Log.d("name", name);

					name = name.replace(" ", "&nbsp;");

					Constant.cd = new ConnectionDetector(context);
					Constant.isInternetPresent = Constant.cd
							.isConnectingToInternet();
					if (Constant.isInternetPresent) {
						if (prg_bar1.isShowing()) {
							prg_bar1.dismiss();
							prg_bar1.cancel();
						} else {
							prg_bar1.show();
						}

						HttpClient client = new DefaultHttpClient();
						HttpPost post = new HttpPost(
								"http://whootin.com/api/v1/users/new.json?username="
										+ uname + "&name=" + name + "&email="
										+ email + "&password=" + password);
						post.addHeader("Accept", "application/json");
						post.addHeader("Content-type", "multipart/form-data");
						post.addHeader("Content-type",
								"applicaton/x-www-form-urlencoded; charset=utf-8");
						HttpResponse response = null;
						try {
							response = client.execute(post);
							Log.d("response from http response",
									response.toString());
							HttpEntity entity = response.getEntity();
							InputStream input = entity.getContent();
							String json = convertStreamToString(input);
							JSONObject obj = new JSONObject(json);
							Log.d("log", "log2");
							if (obj.isNull("error")) {
								get_accesstoken(uname, password);
							} else {

								String sng = "email "
										+ obj.getJSONObject("details")
												.getJSONArray("email")
												.getString(0);
								Log.e("feaaails", sng);
								toastsettext(sng);
								String error = "";
								if (obj.getJSONObject("details").isNull(
										"username")) {
									error = error
											+ "\n Username"
											+ obj.getJSONObject("details")
													.getJSONArray("username")
													.getString(0);
									String alread = "Username "
											+ obj.getJSONObject("details")
													.getJSONArray("username")
													.getString(0);
									toastsettext(alread);
									edt_emails.setError(error);
									error = "";

								}
								if (obj.getJSONObject("details")
										.isNull("email")) {
									error = error
											+ "\n email"
											+ obj.getJSONObject("details")
													.getJSONArray("email")
													.getString(0);
									edt_emails.setError(error);
									String alread = "Email Id "
											+ obj.getJSONObject("details")
													.getJSONArray("email")
													.getString(0);
									toastsettext(alread);
									error = "";
								}

								Toast.makeText(getApplicationContext(),
										obj.getString("error"),
										Toast.LENGTH_SHORT).show();
								if (prg_bar1.isShowing()) {
									prg_bar1.setCancelable(true);
									prg_bar1.dismiss();
									prg_bar1.cancel();
								}
								toastsettext("Try again later");
							}

							AsyncHttpClient clientid = new AsyncHttpClient();
							clientid.addHeader("Content-type",
									"multipart/formdata");
							clientid.addHeader("Content-type",
									"appliction/x-www-form-urlencoded; charset=utf-8");
							clientid.addHeader("Accept", "application/json");
							clientid.addHeader("Content-Transfer-Encoding",
									"binary");
							clientid.post(
									"http://www.salmonellabase.com/ptphp/oven_app_insert.php?email="
											+ email,
									new AsyncHttpResponseHandler() {

										public void onSuccess(String arg0) {
											super.onSuccess(arg0);
										};

										public void onFinish() {
											super.onFinish();
											if (prg_bar1.isShowing()) {
												prg_bar1.setCancelable(true);
												prg_bar1.dismiss();
												prg_bar1.cancel();
											}
										};

										public void onFailure(Throwable arg0,
												String arg1) {
											super.onFailure(arg0, arg1);
											Log.e("error at asynt singup ",
													arg0.toString() + "\n"
															+ arg1.toString());

											if (prg_bar1.isShowing()) {
												prg_bar1.setCancelable(true);
												prg_bar1.dismiss();
												prg_bar1.cancel();
											}
											toastsettext("Try again later");
										};

									});

						} catch (Exception e) {
							Log.e("signup-catch", e.toString());
							if (prg_bar1.isShowing()) {
								prg_bar1.setCancelable(true);
								prg_bar1.dismiss();
								prg_bar1.cancel();
							}
							toastsettext("Try again later");
						}
					} else {
						toastsettext("No Internet Connection");
					}
				}

			} else {
				toastsettext("Please enter the same password");
			}

		} else if (v == btn_lgn) {

			Constant.hideSoftKeyboard(Login_Activity.this, v);
			String user_name = edt_unamel.getText().toString().trim();
			String password = edt_pswdl.getText().toString().trim();
			Log.d("user", user_name + password);

			if (user_name.length() == 0) {
				toastsettext("Please enter the Username");
			} else if (password.length() == 0) {
				toastsettext("Please enter the Password");
			} else if (user_name.length() == 0 && password.length() == 0) {
				toastsettext("Please enter the all values ");
			} else if (user_name.length() != 0 && password.length() != 0) {
				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					prg_bar1.show();
					Handler handler = new Handler() {
						public void handleMessage(Message message) {

							switch (message.what) {

							case HttpConnection.DID_START: {
								Log.d("log", "log");
								break;
							}

							case HttpConnection.DID_SUCCEED: {
								String response = (String) message.obj;
								Log.d("succeed_response", response);
								JSONObject json = null;
								try {
									json = new JSONObject(response);
									if (json.isNull("error")) {
										Constant.datecom = Constant.datee
												.edit();
										Constant.datecom.putString(
												"access_token",
												json.getString("access_token"));
										Constant.datecom.putString("username",
												edt_unamel.getText().toString()
														.trim());
										Constant.datecom.putString("password",
												edt_pswdl.getText().toString()
														.trim());
										String token = json
												.getString("access_token");
										Constant.setToken(token);
										Constant.datecom.commit();
										Constant.vallcom = Constant.vall.edit();
										Constant.vallcom.putString("values",
												"0");
										Constant.vallcom.commit();
										deleteAll();
										Log.d("log", token);
										if (ran) {
											vf_lgn.setVisibility(View.GONE);
											vf_lgn2.setVisibility(View.VISIBLE);
											vf_lgn2.setDisplayedChild(3);
											new download().execute(valu);
										} else {

											vf_lgn2.setVisibility(View.GONE);
											vf_lgn.setVisibility(View.VISIBLE);
											vf_lgn.setDisplayedChild(2);

											new download().execute(valu);
										}
										toastsettext("Sign in Successfully");

										AsyncHttpClient client = new AsyncHttpClient();
										client.addHeader("content-type",
												"application/x-www-form-encoded; charset=utf-8");
										client.addHeader("content-type",
												"multipart/form-data");
										client.addHeader("Accept",
												"application/json");
										client.addHeader(
												"Authorization",
												"Bearer "
														+ json.getString("access_token"));
										client.post(
												"http://whootin.com/api/v1/new.json",
												new AsyncHttpResponseHandler() {
													public void onSuccess(
															String arg0) {
														super.onSuccess(arg0);
														Log.d("login_success",
																arg0);

													};

													public void onFinish() {
														super.onFinish();

														edt_unamel.getText()
																.clear();
														edt_pswdl.getText()
																.clear();

													};

													public void onFailure(
															Throwable arg0,
															String arg1) {
														super.onFailure(arg0,
																arg1);

														prg_bar1.setCancelable(true);
														prg_bar1.dismiss();
														prg_bar1.cancel();

													};
												});
									} else {
										prg_bar1.setCancelable(true);
										prg_bar1.dismiss();
										prg_bar1.cancel();
										toastsettext("Try again later");
									}
								} catch (Exception e) {
									Log.e("did_succeed", e.toString());
									prg_bar1.setCancelable(true);
									prg_bar1.dismiss();
									prg_bar1.cancel();
									toastsettext("Try again later");
								}

								break;
							}

							case HttpConnection.DID_ERROR: {
								Exception e = (Exception) message.obj;
								Log.e("signin_errro", e.toString());
								prg_bar1.setCancelable(true);
								prg_bar1.dismiss();
								prg_bar1.cancel();
								toastsettext("Try again later");
								break;

							}

							}
						}

					};

					new HttpConnection(handler)
							.get("http://whootin.com/oauth/token?grant_type=password&client_id=Kh6rnrXM9WabVGf5VoWhFXB69l9gseGdFrcTNnIh&client_secret=QTyxEA2wBHdgocccv7o5ZSYmuVx3Q9zgSlQ1ujZK&username="
									+ user_name + "&password=" + password);
				} else {
					toastsettext("No Internet Connection");
				}
			}

		} else if (v == btn_logout) {
			final Button cancel_btn, logout_btn;
			AlertDialog.Builder build_dialog;
			final AlertDialog alert_dialog;

			build_dialog = new AlertDialog.Builder(context);
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.altdialog_logout,
					(ViewGroup) findViewById(R.id.lgt_sample));
			cancel_btn = (Button) layout.findViewById(R.id.lgt_btn_cancel);
			logout_btn = (Button) layout.findViewById(R.id.lgt_btn_ok);
			build_dialog.setView(layout);
			alert_dialog = build_dialog.create();
			alert_dialog.setView(layout, 0, 0, 0, 0);

			cancel_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					alert_dialog.dismiss();
				}
			});

			logout_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Constant.datee = getSharedPreferences("datefun",
							MODE_WORLD_WRITEABLE);
					Constant.datecom = Constant.datee.edit();
					Constant.datecom.remove("access_token");
					Constant.datecom.remove("username");
					Constant.datecom.remove("password");
					Constant.datecom.remove("avatar_url");
					Constant.datecom.remove("");
					Constant.datecom.clear();
					Constant.datecom.commit();

					Constant.vall = getSharedPreferences("want",
							MODE_WORLD_WRITEABLE);
					Constant.vallcom = Constant.vall.edit();
					Constant.vallcom.remove("values");

					Constant.vallcom.remove("");
					Constant.vallcom.clear();
					Constant.vallcom.commit();
					valu = "0";

					boolean isSpUsernameDefined = Constant.datee
							.contains("access_token");
					Log.d("acc", Constant.datee.getString("access_token", ""));
					if (isSpUsernameDefined == true) {
						Log.d("clear", "yes");
						// toastsettext("null");
					} else {
						Log.d("clear", "no");
						String access_token = "";
						Constant.datee = getSharedPreferences("datefun",
								MODE_WORLD_WRITEABLE);
						if (Constant.datee != null
								&& Constant.datee.contains("access_token")) {
							access_token = Constant.datee.getString(
									"access_token", "");
							// Log.d("access", access_token);
						}
						// toastsettext("not null");
					}
					deleteAll();
					alert_dialog.dismiss();
					vf_lgn2.setVisibility(View.GONE);
					vf_lgn.setVisibility(View.VISIBLE);
					vf_lgn.setDisplayedChild(0);

				}
			});

			alert_dialog.show();

		}
	}

	private void get_accesstoken(final String uname, final String password) {
		Handler handler = new Handler() {
			public void handleMessage(Message message) {

				switch (message.what) {

				case HttpConnection.DID_START: {
					Log.d("log", "log");
					break;
				}

				case HttpConnection.DID_SUCCEED: {
					String response = (String) message.obj;
					Log.d("succeed_response", response);
					JSONObject json = null;
					try {
						json = new JSONObject(response);
						if (json.isNull("error")) {
							Constant.datecom = Constant.datee.edit();
							Constant.datecom.putString("access_token",
									json.getString("access_token"));
							Constant.datecom.putString("username", uname
									.toString().trim());
							Constant.datecom.putString("password", password
									.toString().trim());
							String token = json.getString("access_token");
							Constant.setToken(token);
							Constant.datecom.commit();
							Constant.vallcom = Constant.vall.edit();
							Constant.vallcom.putString("values", "0");
							Constant.vallcom.commit();
							toastsettext("Sign up successfully");
							deleteAll();
							if (prg_bar1.isShowing()) {
								prg_bar1.dismiss();
								prg_bar1.cancel();
							}
							if (ran) {
								vf_lgn.setVisibility(View.GONE);
								vf_lgn2.setVisibility(View.VISIBLE);
								vf_lgn2.setDisplayedChild(3);

								new download().execute(valu);
							} else {

								vf_lgn2.setVisibility(View.GONE);
								vf_lgn.setVisibility(View.VISIBLE);
								vf_lgn.setDisplayedChild(2);

								new download().execute(valu);
							}
							edt_emails.setText("");
							edt_names.setText("");
							edt_unames.setText("");
							edt_rpswds.setText("");
							edt_pswds.setText("");
							signup_checkbox1.setChecked(false);

							AsyncHttpClient client = new AsyncHttpClient();
							client.addHeader("content-type",
									"application/x-www-form-encoded; charset=utf-8");
							client.addHeader("content-type",
									"multipart/form-data");
							client.addHeader("Accept", "application/json");
							client.addHeader("Authorization",
									"Bearer " + json.getString("access_token"));
							client.post("http://whootin.com/api/v1/new.json",
									new AsyncHttpResponseHandler() {
										public void onSuccess(String arg0) {
											super.onSuccess(arg0);
											Log.d("login_success", arg0);

										};

										public void onFinish() {
											super.onFinish();

										};

										public void onFailure(Throwable arg0,
												String arg1) {
											super.onFailure(arg0, arg1);

											if (prg_bar1.isShowing()) {
												prg_bar1.setCancelable(true);
												prg_bar1.dismiss();
												prg_bar1.cancel();
											}

										};
									});
						} else {
							if (prg_bar1.isShowing()) {
								prg_bar1.setCancelable(true);
								prg_bar1.dismiss();
								prg_bar1.cancel();
							}
							toastsettext("Try again later");
						}
					} catch (Exception e) {
						Log.e("did_succeed", e.toString());
						if (prg_bar1.isShowing()) {
							prg_bar1.setCancelable(true);
							prg_bar1.dismiss();
							prg_bar1.cancel();
						}
						toastsettext("Try again later");
					}

					break;
				}

				case HttpConnection.DID_ERROR: {
					Exception e = (Exception) message.obj;
					Log.e("signin_errro", e.toString());
					prg_bar1.setCancelable(true);
					prg_bar1.dismiss();
					prg_bar1.cancel();
					toastsettext("Try again later");
					break;

				}

				}
			}

		};

		new HttpConnection(handler)
				.get("http://whootin.com/oauth/token?grant_type=password&client_id=Kh6rnrXM9WabVGf5VoWhFXB69l9gseGdFrcTNnIh&client_secret=QTyxEA2wBHdgocccv7o5ZSYmuVx3Q9zgSlQ1ujZK&username="
						+ uname + "&password=" + password);
	}

	public class SimAdaptered extends BaseAdapter {

		Context sim_context;
		LayoutInflater inflater;
		ArrayList<HashMap<String, String>> stg_list;
		int pos = 0;
		String folder = null, name = null, size = null, size1 = null,
				date = null;

		public SimAdaptered(Context context,
				ArrayList<HashMap<String, String>> gallery_list) {
			this.sim_context = context;
			this.stg_list = gallery_list;
			Log.d("MAS", "LIST1");

		}

		public SimAdaptered(Context applicationContext) {
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
			TextView txt_nm, txt_date;
			ImageView img_folder;
			Button btn_quick;
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
				holder.txt_nm = (TextView) view.findViewById(R.id.list_nmtxt);
				holder.btn_quick = (Button) view.findViewById(R.id.list_szbtn);
				holder.two = (LinearLayout) view.findViewById(R.id.two_text);
				holder.txt_date = (TextView) view.findViewById(R.id.list_sztxt);
				holder.img_folder = (ImageView) view
						.findViewById(R.id.list_img);

				if (ran) {
					holder.btn_quick.setVisibility(View.GONE);
				} else {
					holder.btn_quick.setVisibility(View.VISIBLE);
				}

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			/** To set id to given ListView of Objects */
			holder.txt_nm.setId(pos);
			holder.btn_quick.setId(pos);
			holder.txt_date.setId(pos);
			holder.img_folder.setId(pos);
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
			holder.txt_date.setText(date);
			String dayss = null;
			if (stng_date_split.length == 3) {
				dayss = DateToDay(stng_date_split[2], stng_date_split[1],
						stng_date_split[0], stng_hour_split[0],
						stng_hour_split[1], three);
			}

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
					Log.d("percentage1", String.valueOf(nf.format(x)));
					String y = String.valueOf(nf.format(x));
					double z = Double.valueOf(y);
					DecimalFormat df = new DecimalFormat("#,###,##0");
					holder.txt_date.setText(String.valueOf(df.format(z))
							+ "KB, modified " + dayss);
					Log.d("percentage3", String.valueOf(df.format(z)));
				} else if (count <= 12) {
					double xx = (double) (index / 1000);
					double x = xx / 1000;
					NumberFormat nf = NumberFormat.getInstance();
					nf.setMaximumFractionDigits(1);
					Log.d("percentage", String.valueOf(nf.format(x)));
					String y = String.valueOf(nf.format(x));
					double z = Double.valueOf(y);
					DecimalFormat df = new DecimalFormat("#,###,##0");
					holder.txt_date.setText(String.valueOf(df.format(z))
							+ "MB, modified " + dayss);
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
					holder.txt_date.setText(String.valueOf(df.format(z))
							+ "GB, modified " + dayss);
					Log.d("zzz", String.valueOf(df.format(z)));
				}
			}

			folder = stg_list.get(pos).get(Files_Db.File_folder);

			holder.two.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					rl_black.setVisibility(View.GONE);
					int pos = v.getId();

					if (pos == 0) {
						if (mQuickAction_file != null) {
							mQuickAction_file.dismiss();
						}
						if (mQuickAction_folder != null) {
							mQuickAction_folder.dismiss();
						}
						if (mQuickAction_btn != null) {
							mQuickAction_btn.dismiss();
						}
						Constant.con_fldid = null;
						Constant.con_fldnm = null;
						if (Constant.con_name.size() > 0) {
							Constant.con_name.clear();
						}
						if (Constant.con_list.size() > 0) {
							Constant.con_list.clear();
						}
						Intent intent = new Intent(Login_Activity.this,
								CustomGalleryActivityGrid.class);
						Bundle bundle = new Bundle();
						bundle.putString("new_fold", "fold");
						intent.putExtras(bundle);
						startActivityForResult(intent, 789);

					} else {

						fld_typ = stg_list.get(pos).get(Files_Db.File_folder);
						dlt_id = stg_list.get(pos).get(Files_Db.File_id);
						fld_nm = stg_list.get(pos).get(Files_Db.File_name);
						fld_url = stg_list.get(pos).get(Files_Db.File_url);
						fld_fetc = stg_list.get(pos).get(Files_Db.File_thumb);

						String folder = stg_list.get(pos).get(
								Files_Db.File_folder);
						String folder_id = stg_list.get(pos).get(
								Files_Db.File_id);
						String folder_name = stg_list.get(pos).get(
								Files_Db.File_name);
						String folder_url = stg_list.get(pos).get(
								Files_Db.File_url);
						Constant.con_fldid = folder_id;
						Constant.con_fldnm = folder_name;

						if (folder.contentEquals("folder")) {
							if (mQuickAction_file != null) {
								mQuickAction_file.dismiss();
							}
							if (mQuickAction_folder != null) {
								mQuickAction_folder.dismiss();
							}
							if (mQuickAction_btn != null) {
								mQuickAction_btn.dismiss();
							}

							if (ran) {
								Intent intent = new Intent(context,
										Folder_LoginActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("fld_id", Constant.con_fldid);
								bundle.putString("fld_nm", Constant.con_fldnm);
								bundle.putStringArrayList("array_id",
										Constant.con_list);
								bundle.putStringArrayList("array_nm",
										Constant.con_name);
								bundle.putStringArrayList("array", all_path);
								bundle.putInt("position", pos);
								bundle.putBoolean("ran", ran);
								bundle.putString("values", valu);
								bundle.putString("new", "new");
								intent.putExtras(bundle);
								startActivityForResult(intent, 1234);
							} else {
								Intent intent = new Intent(context,
										Folder_LoginActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("fld_id", Constant.con_fldid);
								bundle.putString("fld_nm", Constant.con_fldnm);
								bundle.putStringArrayList("array_id",
										Constant.con_list);
								bundle.putStringArrayList("array_nm",
										Constant.con_name);
								bundle.putStringArrayList("array", all_path);
								bundle.putInt("position", pos);
								bundle.putBoolean("ran", ran);
								bundle.putString("values", valu);
								bundle.putString("new", "new");
								intent.putExtras(bundle);
								startActivityForResult(intent, 1234);
							}

						} else {
							if (mQuickAction_file != null) {
								mQuickAction_file.dismiss();
							}
							if (mQuickAction_folder != null) {
								mQuickAction_folder.dismiss();
							}
							if (mQuickAction_btn != null) {
								mQuickAction_btn.dismiss();
							}
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
						Log.d("fld_fetc1", fld_fetc);
						fld_pos = pos;

						Log.d("url", fld_fetc);
						Log.d("others", "" + dlt_id + fld_nm);

						if (fld_typ.contentEquals("folder")) {
							mQuickAction_folder.show(v);
							Log.d("folder", "folder" + fld_fetc);
						} else if (fld_typ.contentEquals("file")) {
							mQuickAction_file.show(v);
							Log.d("folder", fld_fetc);
						}
					}
				}
			});

			if (folder.contentEquals("folder")) {
				holder.txt_date.setVisibility(View.GONE);
				if (pos == 0) {
					holder.img_folder
							.setBackgroundResource(R.drawable.ico_gallery);
				} else {
					holder.img_folder
							.setBackgroundResource(R.drawable.folder_ic);
				}
			} else if (folder.contentEquals("file")) {

				Log.d("name", name);
				holder.txt_date.setVisibility(View.VISIBLE);
				if (name.endsWith(".zip") || name.endsWith(".ZIP")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_zip);

				} else if (name.endsWith(".pdf") || name.endsWith(".PDF")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_pdf);

				} else if (name.endsWith(".doc") || name.endsWith(".DOC")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_doc);

				} else if (name.endsWith(".ice") || name.endsWith(".ICE")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_conf);

				} else if (name.endsWith(".wmf") || name.endsWith(".WMF")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_wmf);

				} else if (name.endsWith(".ivr") || name.endsWith(".IVR")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_ivr);

				} else if (name.endsWith(".pvu") || name.endsWith(".PVU")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_pvu);

				} else if (name.endsWith(".xyz") || name.endsWith(".XYZ")
						|| name.endsWith(".pdb") || name.endsWith(".PDB")) {

					holder.img_folder
							.setBackgroundResource(R.drawable.ic_chemical);

				} else if (name.endsWith(".xgz") || name.endsWith(".XGZ")
						|| name.endsWith(".xmz") || name.endsWith(".XMZ")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_xgl);

				} else if (name.endsWith(".ustar") || name.endsWith(".USTAR")
						|| name.endsWith(".gzip") || name.endsWith(".GZIP")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_mht);

				} else if (name.endsWith(".mime") || name.endsWith(".MIME")
						|| name.endsWith(".mht") || name.endsWith(".MTH")
						|| name.endsWith(".mhtml") || name.endsWith(".MHTML")) {

					holder.img_folder
							.setBackgroundResource(R.drawable.ic_model);

				} else if (name.endsWith(".3dm") || name.endsWith(".3DM")
						|| name.endsWith(".3dmf") || name.endsWith(".3DMF")
						|| name.endsWith(".qd3") || name.endsWith(".QD3")
						|| name.endsWith(".qd3d") || name.endsWith(".QD3D")
						|| name.endsWith(".3svr") || name.endsWith(".3SVR")
						|| name.endsWith(".vrt") || name.endsWith(".VRT")) {

					holder.img_folder
							.setBackgroundResource(R.drawable.ic_xworld);

				} else if (name.endsWith(".vrml") || name.endsWith(".IVR")
						|| name.endsWith(".pov") || name.endsWith(".POV")
						|| name.endsWith(".iges") || name.endsWith(".IGES")
						|| name.endsWith(".igs") || name.endsWith(".IGS")
						|| name.endsWith(".wrl") || name.endsWith(".WRL")
						|| name.endsWith(".wrz") || name.endsWith(".WRZ")
						|| name.endsWith(".dwf") || name.endsWith(".DWF")) {

					holder.img_folder
							.setBackgroundResource(R.drawable.ic_model);

				} else if (name.endsWith(".html") || name.endsWith(".HTML")
						|| name.endsWith(".shtml") || name.endsWith(".SHTML")
						|| name.endsWith(".acgi") || name.endsWith(".ACGI")
						|| name.endsWith(".htm") || name.endsWith(".HTM")
						|| name.endsWith(".htmls") || name.endsWith(".HTMLS")
						|| name.endsWith(".htt") || name.endsWith(".HTT")
						|| name.endsWith(".htx") || name.endsWith(".HTX")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_html);

				} else if (name.endsWith(".ppt") || name.endsWith(".PPT")
						|| name.endsWith(".pptx") || name.endsWith(".PPTX")
						|| name.endsWith(".pptm") || name.endsWith(".PPTM")
						|| name.endsWith(".pot") || name.endsWith(".POT")
						|| name.endsWith(".potx") || name.endsWith(".POTX")
						|| name.endsWith(".pps") || name.endsWith(".PPS")
						|| name.endsWith(".ppsx") || name.endsWith(".PPSX")
						|| name.endsWith(".ppsm") || name.endsWith(".PPSM")) {

					holder.img_folder.setBackgroundResource(R.drawable.ic_ppt);

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

					holder.img_folder.setBackgroundResource(R.drawable.ic_xls);

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

					holder.img_folder
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
						|| name.endsWith(".JPE") || name.endsWith(".jps")
						|| name.endsWith(".JPS") || name.endsWith(".jut")
						|| name.endsWith(".JUT") || name.endsWith(".qif")
						|| name.endsWith(".QIF") || name.endsWith(".qti")
						|| name.endsWith(".QTI")) {

					holder.img_folder
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

					holder.img_folder.setBackgroundResource(R.drawable.ic_txt);

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

					holder.img_folder
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
						|| name.endsWith(".TEXINFO") || name.endsWith(".tr")
						|| name.endsWith(".TR") || name.endsWith(".vmd")
						|| name.endsWith(".VMD") || name.endsWith(".vmf")
						|| name.endsWith(".VMF") || name.endsWith(".vrml")
						|| name.endsWith(".VRML") || name.endsWith(".vew")
						|| name.endsWith(".VEW") || name.endsWith(".vda")
						|| name.endsWith(".VDA") || name.endsWith(".unv")
						|| name.endsWith(".UNV") || name.endsWith(".vcd")
						|| name.endsWith(".VCD") || name.endsWith(".rtx")
						|| name.endsWith(".RTX") || name.endsWith(".ima")
						|| name.endsWith(".IMA") || name.endsWith(".imap")
						|| name.endsWith(".IMAP") || name.endsWith(".inf")
						|| name.endsWith(".INF") || name.endsWith(".ins")
						|| name.endsWith(".INS") || name.endsWith(".ima")
						|| name.endsWith(".IMA") || name.endsWith(".inf")
						|| name.endsWith(".INF") || name.endsWith(".ins")
						|| name.endsWith(".INS") || name.endsWith(".ip")
						|| name.endsWith(".IP") || name.endsWith(".iv")
						|| name.endsWith(".IV") || name.endsWith(".hdf")
						|| name.endsWith(".HDF") || name.endsWith(".help")
						|| name.endsWith(".HELP") || name.endsWith(".hgl")
						|| name.endsWith(".HGL") || name.endsWith(".frl")
						|| name.endsWith(".FRL") || name.endsWith(".fdf")
						|| name.endsWith(".FDF") || name.endsWith(".pcl")
						|| name.endsWith(".PCL") || name.endsWith(".pm4")
						|| name.endsWith(".PM4") || name.endsWith(".pm5")
						|| name.endsWith(".PM5") || name.endsWith(".plx")
						|| name.endsWith(".PLX") || name.endsWith(".pwz")
						|| name.endsWith(".PWZ") || name.endsWith(".ppa")
						|| name.endsWith(".PPA") || name.endsWith(".pyc")
						|| name.endsWith(".PYC") || name.endsWith(".pkg")
						|| name.endsWith(".PKG") || name.endsWith(".pko")
						|| name.endsWith(".PKO") || name.endsWith(".nc")
						|| name.endsWith(".NC") || name.endsWith(".ncm")
						|| name.endsWith(".NCM") || name.endsWith(".mzz")
						|| name.endsWith(".MZZ") || name.endsWith(".mm")
						|| name.endsWith(".MM") || name.endsWith(".mme")
						|| name.endsWith(".MME") || name.endsWith(".mpc")
						|| name.endsWith(".MPC") || name.endsWith(".mif")
						|| name.endsWith(".MIF") || name.endsWith(".shar")
						|| name.endsWith(".SHAR") || name.endsWith(".sdp")
						|| name.endsWith(".SDP") || name.endsWith(".sdr")
						|| name.endsWith(".SDR") || name.endsWith(".sea")
						|| name.endsWith(".SEA") || name.endsWith(".sit")
						|| name.endsWith(".SIT") || name.endsWith(".smil")
						|| name.endsWith(".SMIL") || name.endsWith(".smi")
						|| name.endsWith(".SMI") || name.endsWith(".sl")
						|| name.endsWith(".SL") || name.endsWith(".skt")
						|| name.endsWith(".SKT") || name.endsWith(".skm")
						|| name.endsWith(".SKM") || name.endsWith(".skp")
						|| name.endsWith(".SKP") || name.endsWith(".skd")
						|| name.endsWith(".SKD") || name.endsWith(".ppz")
						|| name.endsWith(".PPZ") || name.endsWith(".set")
						|| name.endsWith(".SET") || name.endsWith(".pre")
						|| name.endsWith(".PRE") || name.endsWith(".prt")
						|| name.endsWith(".PRT") || name.endsWith(".ps")
						|| name.endsWith(".PS") || name.endsWith(".psd")
						|| name.endsWith(".PSD") || name.endsWith(".jcm")
						|| name.endsWith(".JCM") || name.endsWith(".ivy")
						|| name.endsWith(".IVY") || name.endsWith(".latex")
						|| name.endsWith(".LATEX") || name.endsWith(".lha")
						|| name.endsWith(".LHA") || name.endsWith(".lhx")
						|| name.endsWith(".LHX") || name.endsWith(".man")
						|| name.endsWith(".MAN") || name.endsWith(".map")
						|| name.endsWith(".MAP") || name.endsWith(".ltx")
						|| name.endsWith(".ITX") || name.endsWith(".lzh")
						|| name.endsWith(".LZH") || name.endsWith(".lzx")
						|| name.endsWith(".LZX") || name.endsWith(".mdb")
						|| name.endsWith(".MDB") || name.endsWith(".mc$")
						|| name.endsWith(".MC$") || name.endsWith(".mcd")
						|| name.endsWith(".MCD") || name.endsWith(".mcp")
						|| name.endsWith(".MCP") || name.endsWith(".me")
						|| name.endsWith(".ME") || name.endsWith(".rng")
						|| name.endsWith(".RNG") || name.endsWith(".rnx")
						|| name.endsWith(".RNX") || name.endsWith(".roff")
						|| name.endsWith(".ROFF") || name.endsWith(".saveme")
						|| name.endsWith(".SAVEME") || name.endsWith(".sbk")
						|| name.endsWith(".SBK")) {

					holder.img_folder
							.setBackgroundResource(R.drawable.ic_application);

				} else {
					holder.img_folder.setBackgroundResource(R.drawable.ic_list);
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

	private void downloadFile_listener(final String result) {
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
		client.get("http://whootin.com/api/v1/files.json?count=30",
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String arg0) {
						super.onSuccess(arg0);
						Log.e("from response", arg0);
						get = arg0;

						JSONArray valuejson;
						try {
							valuejson = new JSONArray(get);
							if (contactList.size() > 0) {
								contactList.clear();
							}
							jsonList(valuejson);

							if (contactList.size() > 0) {
								contactListInsert();
							} else {
								toastsettext("There is no data");
								if (re_contactList2.size() > 0) {
									re_contactList2.clear();
								}
								if (ran) {
									lv_add = (ListView) findViewById(R.id.add_listView1);
									Log.e("from response finish", "finish");
									list_adapt = new SimAdaptered(
											getApplicationContext(),
											re_contactList2);
									lv_add.setAdapter(list_adapt);
									Log.e("from response finish", "finishhhhh");
								} else {
									listview_add = (ListView) findViewById(R.id.mnpg_listview);
									Log.e("from response finish", "finishs");
									list_adapt = new SimAdaptered(
											getApplicationContext(),
											re_contactList2);
									listview_add.setAdapter(list_adapt);
									Log.e("from response finish", "finishhhhh");
								}

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					private void jsonList(JSONArray valuejson)
							throws JSONException {
						for (int i = 0; i < valuejson.length(); i++) {
							HashMap<String, String> map = new HashMap<String, String>();
							JSONObject val = valuejson.getJSONObject(i);
							String id = val.getString(Files_Db.File_id);
							String name = val.getString(Files_Db.File_name);
							String size = val.getString(Files_Db.File_size);
							String folder = val.getString(Files_Db.File_folder);
							String create = val
									.getString(Files_Db.File_createat);

							Log.d("download", id + name + size + folder
									+ create);
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
							contactList.add(map);
						}
					}

					private void contactListInsert() {

						ArrayList<HashMap<String, String>> act_listcv = Constant.db
								.getAllTranslates(Files_Db.Table, bool);
						List<String> list = new ArrayList<String>(act_listcv
								.size());
						for (int h = 0; h < act_listcv.size(); h++) {
							String _id = act_listcv.get(h)
									.get(Files_Db.File_id);
							list.add(_id);
						}
						Constant.db_sql = Constant.db.getWritableDatabase();
						for (String and : list) {
							int add = 0;
							for (int h = 0; h < contactList.size(); h++) {
								String File_id = contactList.get(h).get(
										Files_Db.File_id);
								if (!and.contentEquals(File_id)) {
									add++;
								}
							}
							if (add == contactList.size()) {
								Constant.db_sql.delete(Files_Db.Table,
										Files_Db.File_id + "=?",
										new String[] { and });
								Log.d("success", "deleted");
							}
						}

						String File_id = null, File_name = null;
						for (int j = 0; j < contactList.size(); j++) {

							final ContentValues valuesNew = new ContentValues();

							File_id = contactList.get(j).get(Files_Db.File_id);
							if (contactList.get(j).get(Files_Db.File_id) != null) {
								if (!contactList.get(j).get(Files_Db.File_id)
										.isEmpty()) {
									valuesNew.put(Files_Db.File_id, File_id);
								} else {
									valuesNew.put(Files_Db.File_id, "");
								}
							} else {
								valuesNew.put(Files_Db.File_id, "");
							}

							File_name = contactList.get(j).get(
									Files_Db.File_name);
							if (contactList.get(j).get(Files_Db.File_name) != null) {
								if (!contactList.get(j).get(Files_Db.File_name)
										.isEmpty()) {
									valuesNew
											.put(Files_Db.File_name, File_name);
								} else {
									valuesNew.put(Files_Db.File_name, "");
								}
							} else {
								valuesNew.put(Files_Db.File_id, "");
							}

							String File_createat = contactList.get(j).get(
									Files_Db.File_createat);
							if (contactList.get(j).get(Files_Db.File_createat) != null) {
								if (!contactList.get(j)
										.get(Files_Db.File_createat).isEmpty()) {
									valuesNew.put(Files_Db.File_createat,
											File_createat);
								} else {
									valuesNew.put(Files_Db.File_createat, "");
								}
							} else {
								valuesNew.put(Files_Db.File_createat, "");
							}

							String File_size = contactList.get(j).get(
									Files_Db.File_size);
							if (contactList.get(j).get(Files_Db.File_size) != null) {
								if (!contactList.get(j).get(Files_Db.File_size)
										.isEmpty()) {
									valuesNew
											.put(Files_Db.File_size, File_size);
								} else {
									valuesNew.put(Files_Db.File_size, "");
								}
							} else {
								valuesNew.put(Files_Db.File_size, "");
							}

							String File_folder = contactList.get(j).get(
									Files_Db.File_folder);
							if (contactList.get(j).get(Files_Db.File_folder) != null) {
								if (!contactList.get(j)
										.get(Files_Db.File_folder).isEmpty()) {
									valuesNew.put(Files_Db.File_folder,
											File_folder);
								} else {
									valuesNew.put(Files_Db.File_folder, "");
								}
							} else {
								valuesNew.put(Files_Db.File_folder, "");
							}

							String File_url = contactList.get(j).get(
									Files_Db.File_url);
							if (File_url != null) {
								Log.d("db_url", File_url);
							}
							if (contactList.get(j).get(Files_Db.File_url) != null) {
								if (!contactList.get(j).get(Files_Db.File_url)
										.isEmpty()) {
									valuesNew.put(Files_Db.File_url, File_url);
								} else {
									valuesNew.put(Files_Db.File_url, "");
								}
							} else {
								valuesNew.put(Files_Db.File_url, "");
							}

							String File_thumb = contactList.get(j).get(
									Files_Db.File_thumb);
							if (contactList.get(j).get(Files_Db.File_thumb) != null) {
								if (!contactList.get(j)
										.get(Files_Db.File_thumb).isEmpty()) {
									valuesNew.put(Files_Db.File_thumb,
											File_thumb);
								} else {
									valuesNew.put(Files_Db.File_thumb, "");
								}
							} else {
								valuesNew.put(Files_Db.File_thumb, "");
							}

							Log.d("sizeof contactList",
									String.valueOf(contactList.size())
											+ String.valueOf(act_listcv.size()));

							String is_select = "SELECT " + Files_Db.File_id
									+ " FROM " + Files_Db.Table + " WHERE "
									+ Files_Db.File_id + " = '" + File_id + "'";
							int jac = isUserAvailable(is_select);
							Log.d("jac", String.valueOf(jac) + File_name);
							if (jac == 1) {
								Constant.db_sql.update(Files_Db.Table,
										valuesNew, Files_Db.File_id + "=?",
										new String[] { File_id });
								Log.d("success", "updated");
							} else if (jac == 0) {
								Constant.db_sql.insert(Files_Db.Table, null,
										valuesNew);
								Log.d("success", "inserted");
							}

						}
						Constant.db_sql.close();

						if (result.contentEquals("0")) {
							result3();
						} else if (result.contentEquals("1")) {
							result2();
						} else if (result.contentEquals("2")) {
							result1();
						}
					}

					@Override
					public void onFinish() {
						super.onFinish();
						Log.e("from response",
								String.valueOf(re_contactList2.size()));
						Log.e("from response finish", "finish");

						if (prg_bar1.isShowing()) {
							prg_bar1.setCancelable(true);
							prg_bar1.dismiss();
							prg_bar1.cancel();
						}

						if (prg_bar.isShowing()) {
							prg_bar.setCancelable(true);
							prg_bar.dismiss();
							prg_bar.cancel();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						super.onFailure(arg0, arg1);
					}

				});

	}

	private void result1() {
		re_contactList2.clear();
		order1.clear();
		order2.clear();
		order3.clear();
		order4.clear();
		order5.clear();
		order6.clear();
		order7.clear();
		order8.clear();
		order9.clear();
		order10.clear();
		order11.clear();
		order12.clear();
		order13.clear();
		order14.clear();
		order15.clear();
		order16.clear();
		order17.clear();
		order18.clear();
		order19.clear();
		order20.clear();
		order21.clear();
		order22.clear();
		order23.clear();

		re_contactList = Constant.db.getAllTranslates(Files_Db.Table, bool);

		if (re_contactList.size() > 0) {
			re_contactList2.clear();

			for (int j = 0; j < re_contactList.size(); j++) {
				int z = 1;
				String fld = re_contactList.get(j).get(Files_Db.File_folder);
				String fldname = re_contactList.get(j).get(Files_Db.File_name);
				if (fld.contentEquals("folder")) {
					add(j, z);

				}
			}

			for (int j = 0; j < re_contactList.size(); j++) {
				String fld = re_contactList.get(j).get(Files_Db.File_folder);
				String fldname = re_contactList.get(j).get(Files_Db.File_name);
				if (fld.contentEquals("file")) {

					Log.d("fldfldname", fldname);
					if (fldname.endsWith(".zip") || fldname.endsWith(".ZIP")) {

						add(j, 2);

					} else if (fldname.endsWith(".pdf")
							|| fldname.endsWith(".PDF")) {

						add(j, 3);

					} else if (fldname.endsWith(".doc")
							|| fldname.endsWith(".DOC")) {

						add(j, 4);

					} else if (fldname.endsWith(".ice")
							|| fldname.endsWith(".ICE")) {

						add(j, 5);

					} else if (fldname.endsWith(".wmf")
							|| fldname.endsWith(".WMF")) {

						add(j, 6);

					} else if (fldname.endsWith(".ivr")
							|| fldname.endsWith(".IVR")) {

						add(j, 7);

					} else if (fldname.endsWith(".pvu")
							|| fldname.endsWith(".PVU")) {

						add(j, 8);

					} else if (fldname.endsWith(".xyz")
							|| fldname.endsWith(".XYZ")
							|| fldname.endsWith(".pdb")
							|| fldname.endsWith(".PDB")) {

						add(j, 9);

					} else if (fldname.endsWith(".xgz")
							|| fldname.endsWith(".XGZ")
							|| fldname.endsWith(".xmz")
							|| fldname.endsWith(".XMZ")) {

						add(j, 10);

					} else if (fldname.endsWith(".ustar")
							|| fldname.endsWith(".USTAR")
							|| fldname.endsWith(".gzip")
							|| fldname.endsWith(".GZIP")) {

						add(j, 11);

					} else if (fldname.endsWith(".mime")
							|| fldname.endsWith(".MIME")
							|| fldname.endsWith(".mht")
							|| fldname.endsWith(".MTH")
							|| fldname.endsWith(".mhtml")
							|| fldname.endsWith(".MHTML")) {

						add(j, 12);

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

						add(j, 13);

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

						add(j, 14);

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

						add(j, 15);

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

						add(j, 16);

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

						add(j, 17);

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

						add(j, 18);

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

						add(j, 19);

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

						add(j, 20);

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

						add(j, 21);

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

						add(j, 22);
					} else {
						add(j, 23);
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
			Collections.sort(order3, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order4, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order5, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order6, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order7, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order8, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order9, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order10, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order11, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order12, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order13, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order14, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order15, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order16, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order17, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});
			Collections.sort(order18, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order19, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order20, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order21, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order22, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_name).compareTo(
							two.get(Files_Db.File_name));
				}
			});

			Collections.sort(order23, new Comparator<Map<String, String>>() {
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
			for (int j = 0; j < order3.size(); j++) {
				Log.d("sort", order3.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order4.size(); j++) {
				Log.d("sort", order4.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order5.size(); j++) {
				Log.d("sort", order5.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order6.size(); j++) {
				Log.d("sort", order6.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order7.size(); j++) {
				Log.d("sort", order7.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order8.size(); j++) {
				Log.d("sort", order8.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order9.size(); j++) {
				Log.d("sort", order9.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order10.size(); j++) {
				Log.d("sort", order10.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order11.size(); j++) {
				Log.d("sort", order11.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order12.size(); j++) {
				Log.d("sort", order12.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order13.size(); j++) {
				Log.d("sort", order13.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order14.size(); j++) {
				Log.d("sort", order14.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order15.size(); j++) {
				Log.d("sort", order15.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order16.size(); j++) {
				Log.d("sort", order16.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order17.size(); j++) {
				Log.d("sort", order17.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order18.size(); j++) {
				Log.d("sort", order18.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order19.size(); j++) {
				Log.d("sort", order19.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order20.size(); j++) {
				Log.d("sort", order20.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order21.size(); j++) {
				Log.d("sort", order21.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order22.size(); j++) {
				Log.d("sort", order22.get(j).get(Files_Db.File_name));
			}
			for (int j = 0; j < order23.size(); j++) {
				Log.d("sort", order23.get(j).get(Files_Db.File_name));
			}
			re_contactList2.clear();

			re_contactList2.addAll(order1);
			re_contactList2.addAll(order2);
			re_contactList2.addAll(order3);
			re_contactList2.addAll(order4);
			re_contactList2.addAll(order5);
			re_contactList2.addAll(order6);
			re_contactList2.addAll(order7);
			re_contactList2.addAll(order8);
			re_contactList2.addAll(order9);
			re_contactList2.addAll(order10);
			re_contactList2.addAll(order11);
			re_contactList2.addAll(order12);
			re_contactList2.addAll(order13);
			re_contactList2.addAll(order14);
			re_contactList2.addAll(order15);
			re_contactList2.addAll(order16);
			re_contactList2.addAll(order17);
			re_contactList2.addAll(order18);
			re_contactList2.addAll(order19);
			re_contactList2.addAll(order20);
			re_contactList2.addAll(order21);
			re_contactList2.addAll(order22);
			re_contactList2.addAll(order23);

			if (ran) {
				lv_add = (ListView) findViewById(R.id.add_listView1);
				Log.e("from response finish", "finish");
				list_adapt = new SimAdaptered(getApplicationContext(),
						re_contactList2);
				lv_add.setAdapter(list_adapt);
				Log.e("from response finish", "finishhhhh");
			} else {
				listview_add = (ListView) findViewById(R.id.mnpg_listview);
				Log.e("from response finish", "finishs");
				list_adapt = new SimAdaptered(getApplicationContext(),
						re_contactList2);
				listview_add.setAdapter(list_adapt);
				Log.e("from response finish", "finishhhhh");
			}

		}

	}

	public static void add(int j, int l) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Files_Db.File_id, re_contactList.get(j).get(Files_Db.File_id));
		map.put(Files_Db.File_name,
				re_contactList.get(j).get(Files_Db.File_name));
		Log.d("name_name", re_contactList.get(j).get(Files_Db.File_name));
		Log.d("l", String.valueOf(l));
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
		} else if (l == 3) {
			order3.add(map);
		} else if (l == 4) {
			order4.add(map);
		} else if (l == 5) {
			order5.add(map);
		} else if (l == 6) {
			order6.add(map);
		} else if (l == 7) {
			order7.add(map);
		} else if (l == 8) {
			order8.add(map);
		} else if (l == 9) {
			order9.add(map);
		} else if (l == 10) {
			order10.add(map);
		} else if (l == 11) {
			order11.add(map);
		} else if (l == 12) {
			order12.add(map);
		} else if (l == 13) {
			order13.add(map);
		} else if (l == 14) {
			order14.add(map);
		} else if (l == 15) {
			order15.add(map);
		} else if (l == 16) {
			order16.add(map);
		} else if (l == 17) {
			order17.add(map);
		} else if (l == 18) {
			order18.add(map);
		} else if (l == 19) {
			order19.add(map);
		} else if (l == 20) {
			order20.add(map);
		} else if (l == 21) {
			order21.add(map);
		} else if (l == 22) {
			order22.add(map);
		} else if (l == 23) {
			order23.add(map);
		}

	}

	private void result2() {
		re_contactList2.clear();
		order1.clear();
		order2.clear();
		Log.d("double", "double");
		re_contactList = Constant.db.getAllTranslates(Files_Db.Table, bool);

		if (re_contactList.size() > 0) {
			re_contactList2.clear();

			for (int j = 0; j < re_contactList.size(); j++) {
				int z = 1;
				String fld = re_contactList.get(j).get(Files_Db.File_folder);
				String fldname = re_contactList.get(j).get(Files_Db.File_name);
				if (fld.contentEquals("folder")) {
					add2(j, z);

				}
			}

			for (int j = 0; j < re_contactList.size(); j++) {
				String fld = re_contactList.get(j).get(Files_Db.File_folder);
				String fldname = re_contactList.get(j).get(Files_Db.File_name);
				if (fld.contentEquals("file")) {

					Log.d("fldfldname", fldname);
					if (fldname.endsWith(".zip") || fldname.endsWith(".ZIP")) {

						add2(j, 2);

					} else if (fldname.endsWith(".pdf")
							|| fldname.endsWith(".PDF")) {

						add2(j, 2);

					} else if (fldname.endsWith(".doc")
							|| fldname.endsWith(".DOC")) {

						add2(j, 2);

					} else if (fldname.endsWith(".ice")
							|| fldname.endsWith(".ICE")) {

						add2(j, 2);

					} else if (fldname.endsWith(".wmf")
							|| fldname.endsWith(".WMF")) {

						add2(j, 2);

					} else if (fldname.endsWith(".ivr")
							|| fldname.endsWith(".IVR")) {

						add2(j, 2);

					} else if (fldname.endsWith(".pvu")
							|| fldname.endsWith(".PVU")) {

						add2(j, 2);

					} else if (fldname.endsWith(".xyz")
							|| fldname.endsWith(".XYZ")
							|| fldname.endsWith(".pdb")
							|| fldname.endsWith(".PDB")) {

						add2(j, 2);

					} else if (fldname.endsWith(".xgz")
							|| fldname.endsWith(".XGZ")
							|| fldname.endsWith(".xmz")
							|| fldname.endsWith(".XMZ")) {

						add2(j, 2);

					} else if (fldname.endsWith(".ustar")
							|| fldname.endsWith(".USTAR")
							|| fldname.endsWith(".gzip")
							|| fldname.endsWith(".GZIP")) {

						add2(j, 2);

					} else if (fldname.endsWith(".mime")
							|| fldname.endsWith(".MIME")
							|| fldname.endsWith(".mht")
							|| fldname.endsWith(".MTH")
							|| fldname.endsWith(".mhtml")
							|| fldname.endsWith(".MHTML")) {

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);

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

						add2(j, 2);
					} else {
						add2(j, 2);
					}

				}
			}

			Collections.sort(order1, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_createat).compareTo(
							two.get(Files_Db.File_createat));
				}
			});

			Collections.sort(order2, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> one,
						Map<String, String> two) {
					return one.get(Files_Db.File_createat).compareTo(
							two.get(Files_Db.File_createat));
				}
			});

			for (int j = 0; j < order1.size(); j++) {
				Log.d("time", order1.get(j).get(Files_Db.File_name));
			}

			for (int j = 0; j < order2.size(); j++) {
				Log.d("time", order2.get(j).get(Files_Db.File_name));
			}

			Collections.reverse(order1);
			Collections.reverse(order2);

			for (int j = 0; j < order1.size(); j++) {
				Log.d("time", order1.get(j).get(Files_Db.File_name));
			}

			for (int j = 0; j < order2.size(); j++) {
				Log.d("time", order2.get(j).get(Files_Db.File_name));
			}

			re_contactList2.clear();

			re_contactList2.addAll(order1);
			re_contactList2.addAll(order2);

			if (ran) {
				lv_add = (ListView) findViewById(R.id.add_listView1);
				Log.e("from response finish", "finish");
				list_adapt = new SimAdaptered(getApplicationContext(),
						re_contactList2);
				lv_add.setAdapter(list_adapt);
				Log.e("from response finish", "finishhhhh");
			} else {
				listview_add = (ListView) findViewById(R.id.mnpg_listview);
				Log.e("from response finish", "finishs");
				list_adapt = new SimAdaptered(getApplicationContext(),
						re_contactList2);
				listview_add.setAdapter(list_adapt);
				Log.e("from response finish", "finishhhhh");
			}

		}
	}

	private void add2(int j, int l) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Files_Db.File_id, re_contactList.get(j).get(Files_Db.File_id));
		map.put(Files_Db.File_name,
				re_contactList.get(j).get(Files_Db.File_name));
		Log.d("name_name", re_contactList.get(j).get(Files_Db.File_name));
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

	private void result3() {
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

			if (ran) {
				lv_add = (ListView) findViewById(R.id.add_listView1);
				Log.e("from response finish", "finish");
				list_adapt = new SimAdaptered(getApplicationContext(),
						re_contactList2);
				lv_add.setAdapter(list_adapt);
				Log.e("from response finish", "finishhhhh");
			} else {
				listview_add = (ListView) findViewById(R.id.mnpg_listview);
				Log.e("from response finish", "finishs");
				list_adapt = new SimAdaptered(getApplicationContext(),
						re_contactList2);
				listview_add.setAdapter(list_adapt);
				Log.e("from response finish", "finishhhhh");
			}

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

	public class folder extends AsyncTask {
		@Override
		protected Object doInBackground(Object... arg0) {

			return arg0;

		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// prg_bar.show();
		}

	}

	private static final int id_share = 1;
	private static final int id_delete = 2;
	private static final int id_rename = 3;
	private static final int id_move = 4;
	private static final int id_more = 5;

	private static final int id_az = 1;
	private static final int id_time = 2;
	private static final int id_name = 3;
	private static final int id_set = 4;

	private boolean ran = false;
	private Button btn_cncladd, btn_addadd, btn_fldadd, btn_backadd;
	private ListView lv_add;
	private TextView tx_add;

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	@Override
	protected void onDestroy() {
		if (cursor_low != null) {
			cursor_low.close();
		}
		// close any cursors we are managing.
		int numCursors = mManagedCursors.size();
		for (int i = 0; i < numCursors; i++) {
			ManagedCursor c = mManagedCursors.get(i);
			if (c != null) {
				c.mCursor.close();
			}
		}

		super.onDestroy();

	}

	int get_dd = 0;

	public String parseUriToFilename(Uri uri) {
		String selectedImagePath = null;
		String filemanagerPath = uri.getPath();

		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);

		if (cursor != null) {
			// Here you will get a null pointer if cursor is null
			// This can be if you used OI file manager for picking the media
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			selectedImagePath = cursor.getString(column_index);
		}

		if (selectedImagePath != null) {
			return selectedImagePath;
		} else if (filemanagerPath != null) {
			return filemanagerPath;
		}
		return null;
	}

	private void btn_folder() {
		final EditText edt_fldr;
		final Button yes_btn, no_btn;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_fldname,
				(ViewGroup) findViewById(R.id.name_sample));
		no_btn = (Button) layout.findViewById(R.id.nm_btn_cancel);
		yes_btn = (Button) layout.findViewById(R.id.nm_btn_ok);
		edt_fldr = (EditText) layout.findViewById(R.id.nm_txt_hd);
		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		yes_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Constant.hideSoftKeyboard(Login_Activity.this, v);
				folder_name = edt_fldr.getText().toString();
				alert_dialog.dismiss();
				if (folder_name.length() != 0) {
					if (common) {
						check(folder_name);
					}
				} else {
					toastsettext("Enter FolderName");
				}
			}

		});

		no_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Constant.hideSoftKeyboard(Login_Activity.this, v);
				alert_dialog.dismiss();
			}
		});

		alert_dialog.show();

	}

	private void check(String folder_name2) {
		String get_name = null;
		String get_id = null;

		ArrayList<HashMap<String, String>> contact = Constant.db
				.getAllTranslates(Files_Db.Table, bool);

		for (int j = 0; j < contact.size(); j++) {
			String fld = contact.get(j).get(Files_Db.File_name);
			String flid = contact.get(j).get(Files_Db.File_id);
			if (folder_name2.contentEquals(fld)) {
				Log.d("s-2", fld);
				Log.d("flid", flid);
				folder_name = fld;
				get_fldid = flid;
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
			whootin_multi_post_fold();
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
					alert_dialog.dismiss();
				}

			});

			no_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
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

	private void whootin_multi_post_fold() {
		if (prg_bar.isShowing()) {

		} else {
			prg_bar.show();
		}
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
			params.put("name", folder_name);

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
							Log.d("create success folder", arg0);
							try {
								JSONObject obj3 = new JSONObject(arg0);
								get_fldid = obj3.getString(Files_Db.File_id);
								folder_name = obj3
										.getString(Files_Db.File_name);
								Log.d("id", String.valueOf(get_fldid));
								Constant.con_fldnm = folder_name;
								Constant.con_fldid = get_fldid;

								Intent intent = new Intent(context,
										Folder_LoginActivity.class);
								Bundle bundle = new Bundle();
								bundle.putString("new", "new");
								bundle.putString("fld_id", Constant.con_fldid);
								bundle.putString("fld_nm", Constant.con_fldnm);
								bundle.putStringArrayList("array_id",
										Constant.con_list);
								bundle.putStringArrayList("array_nm",
										Constant.con_name);
								bundle.putInt("position", 0);
								bundle.putStringArrayList("array", all_path);
								bundle.putBoolean("ran", ran);
								bundle.putString("values", valu);
								intent.putExtras(bundle);
								startActivityForResult(intent, 4444);

							} catch (JSONException e) {
								e.printStackTrace();
							}
							Log.d("from success", "from success");

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

	public String getPathfromUri(Uri uri) {
		if (uri.toString().startsWith("file://"))
			return uri.getPath();
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		startManagingCursor(cursor);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(column_index);
		// cursor.close();
		return path;
	}

	private void export() {
		final Button cancel, ok;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_export,
				(ViewGroup) findViewById(R.id.export_sample));
		cancel = (Button) layout.findViewById(R.id.expt_btn_sd);
		ok = (Button) layout.findViewById(R.id.expt_btn_mail);
		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				Log.d("url url url", fld_url);
				Log.d("url url url", String.valueOf(fld_pos));
				String fld_url = null;
				for (int j = 0; j < re_contactList2.size(); j++) {
					String fld = re_contactList2.get(j).get(Files_Db.File_name);
					if (fld.contentEquals(fld_nm)) {
						fld_url = re_contactList2.get(j).get(Files_Db.File_url);
						Log.d("url url url", fld_url);
						break;
					}
				}
				Intent intent = new Intent(context, Sdexport_Activity.class);
				Bundle bundle = new Bundle();
				bundle.putString("name", fld_nm);
				bundle.putString("url", fld_url);
				Log.d("fld", fld_url);
				intent.putExtras(bundle);
				startActivityForResult(intent, 000);

			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					mail();
				} else {
					toastsettext("No Internet Connection");
				}
			}

		});

		alert_dialog.show();

	}

	private void mail() {
		final Button cancel, ok;
		final EditText txt;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_mail,
				(ViewGroup) findViewById(R.id.mail_sample));
		cancel = (Button) layout.findViewById(R.id.mail_btn_cancel);
		ok = (Button) layout.findViewById(R.id.mail_btn_ok);
		txt = (EditText) layout.findViewById(R.id.mail_txt_hd);
		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();

			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					String mail_id = txt.getText().toString();
					Boolean boo = isEmailValid(mail_id);
					if (boo) {
						if (fld_typ.contentEquals("folder")) {
							// mail_boo = true;
							// new ShareProgress().execute();
							postfile(mail_id);
						} else {
							postfile(mail_id);
						}
					} else {
						toastsettext("Invalid email id");
					}
				} else {
					toastsettext("No Internet Connection");
				}
			}
		});

		alert_dialog.show();

	}

	private void postfile(String mail_id2) {

		Constant.datee = getSharedPreferences("datefun", MODE_WORLD_WRITEABLE);
		String access_token = "";
		if (Constant.datee != null && Constant.datee.contains("access_token")) {
			access_token = "Bearer "
					+ Constant.datee.getString("access_token", "");
			Log.d("access_token", access_token);
		}

		if (dlt_id != null) {
			Log.d("flod", dlt_id);

			AsyncHttpClient client1 = new AsyncHttpClient();
			client1.addHeader("Content-type", "multipart/form-data");
			client1.addHeader("Content-type",
					"application/x-www-form-urlencoded; charset=utf-8");
			client1.addHeader("Accept", "appliction/json");
			client1.addHeader("Content-Transfer-Encoding", "binary");
			client1.addHeader("Authorization", access_token);
			client1.post("http://whootin.com/api/v1/files/share/" + dlt_id
					+ ".json?email=" + mail_id2, /* , params, */
					new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(String arg0) {
							super.onSuccess(arg0);
							Log.d("from response", arg0);
							get = null;
							get = arg0;

							try {
								JSONObject obj3 = new JSONObject(arg0);
								String get_fldid = obj3.getString("success");
								Log.d("id", String.valueOf(get_fldid));
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFinish() {
							super.onFinish();
							Log.e("from response finish", "finish");

							if (prg_bar != null) {
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
		} else {
			toastsettext("Try again later");
		}

	}

	private void rename() {
		final Button cancel, ok;
		final EditText txt, untxt;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_rename,
				(ViewGroup) findViewById(R.id.rn_sample));
		cancel = (Button) layout.findViewById(R.id.rn_btn_cancel);
		ok = (Button) layout.findViewById(R.id.rn_btn_ok);
		txt = (EditText) layout.findViewById(R.id.rn_txt_hd);
		untxt = (EditText) layout.findViewById(R.id.rn_untxt);
		Log.d("name", fld_nm);
		if (fld_typ.contains("folder")) {
			untxt.setVisibility(View.GONE);
			txt.setText(fld_nm);
		} else if (fld_typ.contains("file")) {
			untxt.setVisibility(View.VISIBLE);
			String get[] = fld_nm.split("\\.");
			Log.d("get", String.valueOf(get.length));
			if (get.length == 2) {
				txt.setText(get[0].toString());
				un_edit = "." + get[1].toString();
				untxt.setText(un_edit);
			} else if (get.length == 1) {
				untxt.setVisibility(View.GONE);
				txt.setText(fld_nm);
			}

		}

		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();

			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String get[] = fld_nm.split("\\.");
				String text = txt.getText().toString().trim();
				String final_text = null;
				if (fld_typ.contains("folder")) {
					final_text = text;
					Log.d("text", text);
				} else if (fld_typ.contains("file")) {
					if (get.length == 1) {
						final_text = text;
						Log.d("text", text);
					} else {
						text = text.replace(".", "");
						text = text.replace(" ", "");
						final_text = text + un_edit;
						Log.d("untext", un_edit + "\r\n" + final_text);
					}
				}

				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					if (text.length() != 0) {
						alert_dialog.dismiss();
						new renameProgress().execute(final_text);
						Log.d("enter", "enter");
					} else {
						if (fld_typ.contains("folder")) {
							toastsettext("Enter reaname of file");
						} else if (fld_typ.contains("file")) {
							toastsettext("Enter reaname of folder");
						}

					}
				} else {
					toastsettext("No Internet Connection");
				}
			}
		});

		alert_dialog.show();

	}

	public class renameProgress extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String s = params[0];
			return s;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("enter", result);
			postrename(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			prg_bar.show();
		}

	}

	private void postrename(String s) {
		Constant.datee = getSharedPreferences("datefun", MODE_WORLD_WRITEABLE);
		String access_token = "";
		if (Constant.datee != null && Constant.datee.contains("access_token")) {
			access_token = "Bearer "
					+ Constant.datee.getString("access_token", "");
			Log.d("access_token", access_token);
		}

		// s = s.replace(" ","&nbsp;");

		RequestParams params = new RequestParams();
		Log.d("flod", s + dlt_id);
		params.put("name", s);

		AsyncHttpClient client1 = new AsyncHttpClient();
		client1.addHeader("Content-type", "multipart/form-data");
		client1.addHeader("Content-type",
				"application/x-www-form-urlencoded; charset=utf-8");
		client1.addHeader("Accept", "appliction/json");
		client1.addHeader("Content-Transfer-Encoding", "binary");
		client1.addHeader("Authorization", access_token);
		String sng = "http://whootin.com/api/v1/files/rename/" + dlt_id
				+ ".json?";// name=" + s;
		Log.d("sng", sng);

		client1.put(sng, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String arg0) {
				super.onSuccess(arg0);
				Log.d("from", "header");
				Log.d("from response", arg0);
				get = null;
				get = arg0;

				try {
					JSONObject obj3 = new JSONObject(arg0);
					String get_fldid = obj3.getString(Files_Db.File_id);
					Log.d("id", String.valueOf(get_fldid));
					toastsettext("Successfully Renamed");
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFinish() {
				super.onFinish();
				Log.e("from response finish", "finish");
				new download().execute(valu);
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				super.onFailure(arg0, arg1);
				Log.e("from response", arg1);
			}

		});

	}

	private void share(boolean share) {

		shared = share;

		final Button fb, twit, mail, cancel;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_share,
				(ViewGroup) findViewById(R.id.share_sample));
		fb = (Button) layout.findViewById(R.id.shr_btn_fb);
		twit = (Button) layout.findViewById(R.id.shr_btn_twit);
		mail = (Button) layout.findViewById(R.id.shr_btn_mail);
		cancel = (Button) layout.findViewById(R.id.shr_btn_cancel);
		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		fb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					fb_boo = true;
					new ShareProgress().execute();
				} else {
					toastsettext("No Internet Connection");
				}
				alert_dialog.dismiss();
			}
		});

		twit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					twit_boo = true;
					new ShareProgress().execute();
				} else {
					toastsettext("No Internet Connection");
				}
				alert_dialog.dismiss();

			}
		});

		mail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					mail_boo = true;
					new ShareProgress().execute();
				} else {
					toastsettext("No Internet Connection");
				}
				alert_dialog.dismiss();

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
			}
		});

		alert_dialog.show();
	}

	private final class ResponseListener implements DialogListener {

		@Override
		public void onBack() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onComplete(Bundle arg0) {
			try {
				String etext = null;
				Bitmap bit = BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_launcher);
				if (return_fld_url != null) {
					etext = "Whootin Files  : " + "\r\n" + return_fld_url;
					adapter.uploadImageAsync(etext, "WhootinFiles.jpg", bit,
							100, new UploadImageListener());
					// adapter.updateStatus(etext, new UploadImageListener(),
					// true);
				} else {
					etext = "Whootin Files  : The file is empty";
					adapter.uploadImageAsync(etext, "WhootinFiles.jpg", bit,
							100, new UploadImageListener());
					// adapter.updateStatus(etext, new UploadImageListener(),
					// true);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onError(SocialAuthError arg0) {

		}

	}

	private final class UploadImageListener implements
			SocialAuthListener<Integer> {

		@Override
		public void onExecute(String provider, Integer t) {

			Integer status = t;
			Log.d("status", "stus");
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)// || status.intValue() == 403)
				toastsettext("Link Uploaded");
			else
				toastsettext("Link is not Uploaded");
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}

	public class ShareProgress extends AsyncTask {

		@Override
		protected Object doInBackground(Object... params) {

			// return_fld_url = fld_fetc;
			Log.d("fld_fetc", fld_fetc);
			if (shared) {
				try {
					postvoicetel();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				foldertel();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (fb_boo) {
				fb_boo = false;
				adapter = new SocialAuthAdapter(new ResponseListener());
				adapter.addProvider(Provider.FACEBOOK, R.drawable.btn_fb);
				adapter.authorize(Login_Activity.this,
						SocialAuthAdapter.Provider.FACEBOOK);
			} else if (twit_boo) {
				twit_boo = false;
				adapter = new SocialAuthAdapter(new ResponseListener());
				adapter.addProvider(Provider.TWITTER, R.drawable.btn_twitter);
				adapter.authorize(Login_Activity.this,
						SocialAuthAdapter.Provider.TWITTER);
			} else if (mail_boo) {
				mail_boo = false;
				Intent shareIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/html");
				shareIntent.putExtra(Intent.EXTRA_TEXT, "Whootin Files  : "
						+ "\r\n" + return_fld_url);
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Whootin Files ");
				startActivity(shareIntent);
				boo_main = true;
				Log.d("sai", "return");
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			/*
			 * if (!fld_booler) { prg_bar.show(); }
			 */
		}

	}

	private void postvoicetel() throws IOException, JSONException {

		Log.d("url url url", String.valueOf(fld_pos));
		for (int j = 0; j < re_contactList2.size(); j++) {
			String fld = re_contactList2.get(j).get(Files_Db.File_name);
			if (fld.contentEquals(fld_nm)) {
				fld_url = re_contactList2.get(j).get(Files_Db.File_url);
				Log.d("url url url", fld_url);
				break;
			}
		}

		try {
			HttpPost postt = new HttpPost("http://fetc.hn");
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("url", fld_url));
			postt.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpClient clientt = new DefaultHttpClient();
			HttpResponse responsee = clientt.execute(postt);
			Header[] headers = responsee.getAllHeaders();
			for (Header header : headers) {
				System.out.println("Key : " + header + " ,Value : "
						+ header.getValue());

			}

			System.out.println("\nGet Response Header By Key ...\n");
			return_fld_url = responsee.getFirstHeader("Location").getValue();

			if (return_fld_url == null) {
				System.out.println("Key 'Location' is not found!");
			} else {
				System.out.println("" + return_fld_url);
			}

			System.out.println("\n Done");
		} catch (ClientProtocolException e) {
		}

	}

	private void foldertel() {

		Constant.datee = getSharedPreferences("datefun", MODE_WORLD_WRITEABLE);
		String access_token = "";
		if (Constant.datee != null && Constant.datee.contains("access_token")) {
			access_token = "Bearer "
					+ Constant.datee.getString("access_token", "");
			Log.d("access_token", access_token);
		}

		AsyncHttpClient client1 = new AsyncHttpClient();
		client1.addHeader("Accept", "appliction/json");
		client1.addHeader("Authorization", access_token);
		String sng = "http://whootin.com/api/v1/file?name=" + fld_nm;
		Log.d("sng", sng);
		client1.get(sng, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String arg0) {
				super.onSuccess(arg0);
				Log.e("from response", arg0);
				get = arg0;

				JSONArray valuejson;
				/*
				 * try { valuejson = new JSONArray(get); contactList.clear();
				 * jsonList(valuejson); } catch (JSONException e) {
				 * e.printStackTrace(); }
				 */

			}

			private void jsonList(JSONArray valuejson) throws JSONException {
				for (int i = 0; i < valuejson.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
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
					contactList.add(map);
				}
			}

			@Override
			public void onFinish() {
				super.onFinish();
				Log.e("from response finish", "finish");
				new download().execute(valu);
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				super.onFailure(arg0, arg1);
				Log.e("from response", arg1);
			}

		});

	}

	public static String getResponseBody(HttpResponse response) {

		String response_text = null;

		HttpEntity entity = null;

		try {

			entity = response.getEntity();

			response_text = _getResponseBody(entity);

		} catch (ParseException e) {

			e.printStackTrace();

		} catch (IOException e) {

			if (entity != null) {

				try {

					entity.consumeContent();

				} catch (IOException e1) {

				}

			}

		}

		return response_text;

	}

	public static String _getResponseBody(final HttpEntity entity)
			throws IOException, ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		InputStream instream = entity.getContent();

		if (instream == null) {
			return "";
		}

		if (entity.getContentLength() > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(

			"HTTP entity too large to be buffered in memory");
		}

		String charset = getContentCharSet(entity);

		if (charset == null) {

			charset = HTTP.DEFAULT_CONTENT_CHARSET;

		}

		Reader reader = new InputStreamReader(instream, charset);

		StringBuilder buffer = new StringBuilder();

		try {

			char[] tmp = new char[1024];

			int l;

			while ((l = reader.read(tmp)) != -1) {

				buffer.append(tmp, 0, l);

			}

		} finally {

			reader.close();

		}

		return buffer.toString();

	}

	public static String getContentCharSet(final HttpEntity entity)
			throws ParseException {

		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}

		String charset = null;

		if (entity.getContentType() != null) {

			HeaderElement values[] = entity.getContentType().getElements();

			if (values.length > 0) {

				NameValuePair param = values[0].getParameterByName("charset");

				if (param != null) {

					charset = param.getValue();

				}

			}

		}

		return charset;

	}

	private void delete() {
		final Button cancel, ok;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_delete,
				(ViewGroup) findViewById(R.id.delete_sample));
		cancel = (Button) layout.findViewById(R.id.dlt_btn_cancel);
		ok = (Button) layout.findViewById(R.id.dlt_btn_dlt);
		TextView txt = (TextView) layout.findViewById(R.id.dlt_txt_hd);
		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		if (fld_nm != null) {
			txt.setText(fld_nm);
		}

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
			}
		});

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				Constant.cd = new ConnectionDetector(context);
				Constant.isInternetPresent = Constant.cd
						.isConnectingToInternet();
				if (Constant.isInternetPresent) {
					dlt = false;
					if (prg_bar.isShowing()) {

					} else {
						prg_bar.show();
					}
					d_delete();
				} else {
					toastsettext("No Internet Connection");
				}
			}
		});

		alert_dialog.show();

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
		} else {
			dlt = false;
			if (fld_typ != null) {
				if (fld_typ.contentEquals("folder")) {

					String del = "http://whootin.com/api/v1/folders/destroy/"
							+ dlt_id;
					new delete1().execute(del);
					Log.d("files", fld_typ + dlt_id);
				} else {
					String del = "http://whootin.com/api/v1/files/destroy/"
							+ dlt_id;
					new delete1().execute(del);
					Log.d("files", fld_typ + dlt_id);
				}
			} else {
				toastsettext("There is no file");
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
			if (dlt) {
				Log.d("back", "back2");
				if (dlt_path.size() == 0) {
					Log.d("back", "yes");
					dlt_excepted();
				} else {

					Log.d("back", "no");
					dlt_unexcepted(result);
				}
			} else {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Log.d("feree", "success deleted");
				new download().execute(valu);
				toastsettext("Successfully deleted");

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
				if (prg_bar1.isShowing()) {
					prg_bar1.setCancelable(true);
					prg_bar1.dismiss();
					prg_bar1.cancel();
				}
			}

		});

	}

	public class download extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			String s = params[0];
			return s;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("s", result);
			downloadFile_listener(result);

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (dlt_bln) {
				dlt_bln = false;
			} else {
				if (prg_bar.isShowing()) {

				} else {
					if (prg_bar != null) {
						prg_bar.show();
					}
				}
			}

		}

	}

	@Override
	protected void onStart() {
		super.onStart();

		Constant.cd = new ConnectionDetector(context);
		Constant.isInternetPresent = Constant.cd.isConnectingToInternet();
		if (Constant.isInternetPresent) {

		} else {
			toastsettext("No Internet Connection");
		}
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			if ((myCalendar.get(Calendar.YEAR) - 13) >= selectedYear) {
				edt_dobSignUp.setText(new StringBuilder().append(month + 1)
						.append("-").append(day).append("-").append(year)
						.append(" "));
			} else {
				edt_dobSignUp.setText("");
				toastsettext("Age Must be greater than 13");
			}

			setdPSignUp.init(year, month, day, null);
		}

	};

	private void backy() {
		vf_lgn1.setVisibility(View.GONE);
		vf_lgn.setVisibility(View.VISIBLE);
		vf_lgn.setDisplayedChild(1);
	}

	public void deleteAll() {
		Constant.db_sql = Constant.db.getWritableDatabase();
		Constant.db_sql.delete(Constant.db.Table, null, null);
		Constant.db_sql.close();
	}

	private Boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	private String convertStreamToString(InputStream input) throws IOException {
		Log.d("log", "log1");
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	@Override
	public void onBackPressed() {
		final Button cancel_btn, logout_btn;
		AlertDialog.Builder build_dialog;
		final AlertDialog alert_dialog;

		build_dialog = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.altdialog_exit,
				(ViewGroup) findViewById(R.id.exit_sample));
		cancel_btn = (Button) layout.findViewById(R.id.ext_btn_cancel);
		logout_btn = (Button) layout.findViewById(R.id.ext_btn_ok);
		build_dialog.setView(layout);
		alert_dialog = build_dialog.create();
		alert_dialog.setView(layout, 0, 0, 0, 0);

		cancel_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();

			}
		});

		logout_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert_dialog.dismiss();
				ran = false;
				Login_Activity.this.finish();

			}
		});

		alert_dialog.show();
	}

	private static final class ManagedCursor {
		ManagedCursor(Cursor cursor) {
			mCursor = cursor;
			mReleased = false;
			mUpdated = false;
		}

		private final Cursor mCursor;
		private boolean mReleased;
		private boolean mUpdated;
	}

	@Override
	public void startManagingCursor(Cursor c) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			mManagedCursors.add(new ManagedCursor(c));
			super.startManagingCursor(c);
		}
	}

	@Override
	public void stopManagingCursor(Cursor c) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			final int N = mManagedCursors.size();
			for (int i = 0; i < N; i++) {
				ManagedCursor mc = mManagedCursors.get(i);
				if (mc.mCursor == c) {
					mManagedCursors.remove(i);
					break;
				}
			}
			super.stopManagingCursor(c);
		}
	}

	@Override
	protected void onStop() {
		if (cursor_low != null) {
			cursor_low.close();
		}
		// wakeLock.release();
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onStop();
	}

	@Override
	protected void onPause() {

		if (cursor_low != null) {
			cursor_low.close();
		}
		rl_black.setVisibility(View.GONE);
		super.onPause();
	}

	private void whootin_user() {
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
		client.get("http://whootin.com/api/v1/user.json",
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String arg0) {
						super.onSuccess(arg0);
						Log.d("set", arg0);
						try {
							JSONObject val = new JSONObject(arg0);
							user_uname = val.getString("username");
							user_uurl = val.getString("avatar_url");
							user_usize = val.getString("uploads_total_size");
							JSONObject sel = val.getJSONObject("plan");
							user_plan = sel.getString("name");
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFinish() {
						super.onFinish();
						if (boo_stng == true) {
							boo_stng = false;
							if (user_plan != null && user_uname != null
									& user_usize != null & user_uurl != null) {
								Intent ints = new Intent(Login_Activity.this,
										Setting_Activity.class);
								Bundle bundle = new Bundle();
								bundle.putString("uname", user_uname);
								bundle.putString("url", user_uurl);
								bundle.putString("size", user_usize);
								bundle.putString("plan", user_plan.toString()
										.trim());
								ints.putExtras(bundle);
								startActivityForResult(ints, 786);
							} else {
								toastsettext("Try again later");
							}
						}
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						super.onFailure(arg0, arg1);
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

				holder.img_file.setBackgroundResource(R.drawable.ic_chemical);

			} else if (name.endsWith(".xgz") || name.endsWith(".XGZ")
					|| name.endsWith(".xmz") || name.endsWith(".XMZ")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_xgl);

			} else if (name.endsWith(".ustar") || name.endsWith(".USTAR")
					|| name.endsWith(".gzip") || name.endsWith(".GZIP")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_mht);

			} else if (name.endsWith(".mime") || name.endsWith(".MIME")
					|| name.endsWith(".mht") || name.endsWith(".MTH")
					|| name.endsWith(".mhtml") || name.endsWith(".MHTML")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_model);

			} else if (name.endsWith(".3dm") || name.endsWith(".3DM")
					|| name.endsWith(".3dmf") || name.endsWith(".3DMF")
					|| name.endsWith(".qd3") || name.endsWith(".QD3")
					|| name.endsWith(".qd3d") || name.endsWith(".QD3D")
					|| name.endsWith(".3svr") || name.endsWith(".3SVR")
					|| name.endsWith(".vrt") || name.endsWith(".VRT")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_xworld);

			} else if (name.endsWith(".vrml") || name.endsWith(".IVR")
					|| name.endsWith(".pov") || name.endsWith(".POV")
					|| name.endsWith(".iges") || name.endsWith(".IGES")
					|| name.endsWith(".igs") || name.endsWith(".IGS")
					|| name.endsWith(".wrl") || name.endsWith(".WRL")
					|| name.endsWith(".wrz") || name.endsWith(".WRZ")
					|| name.endsWith(".dwf") || name.endsWith(".DWF")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_model);

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

				holder.img_file.setBackgroundResource(R.drawable.ic_video);

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
					|| name.endsWith(".JPE") || name.endsWith(".jps")
					|| name.endsWith(".JPS") || name.endsWith(".jut")
					|| name.endsWith(".JUT") || name.endsWith(".qif")
					|| name.endsWith(".QIF") || name.endsWith(".qti")
					|| name.endsWith(".QTI")) {

				holder.img_file.setBackgroundResource(R.drawable.ic_images);

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

				holder.img_file.setBackgroundResource(R.drawable.ic_audio);

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
					|| name.endsWith(".sv4cpio") || name.endsWith(".SV4CPIO")
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
					|| name.endsWith(".texinfo") || name.endsWith(".TEXINFO")
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

				holder.img_file
						.setBackgroundResource(R.drawable.ic_application);

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
		toastsettext("Added to Whootin Files successfully");
		Login_Activity.this.finish();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		if (requestCode == 786) {
			if (resultCode == RESULT_OK) {
				Login_Activity.this.finish();

			}
		}

		if (requestCode == 000) {
			if (resultCode == RESULT_OK) {
				dlt_bln = true;
			}
		}

		if (requestCode == 4444) {
			if (resultCode == RESULT_OK) {
				if (Constant.boo_add) {
					Login_Activity.this.finish();

				} else {
					// ran = false;
				}
			}
		}

		if (requestCode == 1234) {
			if (resultCode == RESULT_OK) {
				if (Constant.boo_add) {
					Login_Activity.this.finish();

				} else {
					// ran = false;
				}
			}
		}

		if (requestCode == 222) {
			if (resultCode == RESULT_CANCELED) {
				ran = false;
			} else if (resultCode == RESULT_OK) {
				ran = false;
			}
		}

		if (requestCode == 789) {
			ran = false;
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	public class AppWebViewClients extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	class doInBackGround1 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			webpage_signup1.loadUrl("file:///android_asset/terms.html");

		}
	}

	class doInBackGround2 extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			webpage_signup2.loadUrl("file:///android_asset/agreement.html");

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
						if (all_path.get(h).toString()
								.contains(Constant.vid_tit[i])) {
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
					toastsettext("Select anyone to upload Whootin Files");
					Login_Activity.this.finish();

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

	void complain(String message) {
		Log.e("TAG", "**** TrivialDrive Error: " + message);
	}

	ArrayList<String> all_chase = new ArrayList<String>();

	void skuData() throws JSONException {
		String mItemType;
		String mSku;
		String mType;
		String mPrice;
		String mTitle;
		String mDescription;
		String mJson;

		boolean boo_same = false;

		ArrayList<String> array_chase = Constant.sku_chase;

		if (array_chase.size() == 0) {
			Log.d("size", "0");
		} else {
			for (String thisResponse : array_chase) {
				JSONObject o = new JSONObject(thisResponse);
				mSku = o.optString("productId");
				mType = o.optString("type");
				mPrice = o.optString("price");
				mTitle = o.optString("title");
				mDescription = o.optString("description");
				
				if (mSku != null && user_plan != null) {
					Log.d("get", mSku);
					all_chase.add(mSku);
					if (user_plan.contentEquals("Good")) {
						if (mSku.contentEquals(Constant.SKU_GAS)) {
							sets = "1";
							boo_same = true;
							update();
							Log.d("sets", "1");
						}
					} else if (user_plan.contentEquals("Better")) {
						if (mSku.contentEquals(Constant.SKU_GAS100)) {
							sets = "2";
							boo_same = true;
							update();
							Log.d("sets", "2");
						}
					} else if (user_plan.contentEquals("Best")) {
						if (mSku.contentEquals(Constant.SKU_GAS1000)) {
							sets = "3";
							boo_same = true;
							update();
							Log.d("sets", "3");
						}
					}
				}
			}

			if (all_chase.size() == 0) {
			}

		}
	}


	private void update() {
		Constant.pass = getSharedPreferences("fun", MODE_WORLD_WRITEABLE);
		Constant.passcom = Constant.pass.edit();
		Constant.passcom.remove("up");
		Constant.passcom.remove("");
		Constant.passcom.clear();
		Constant.passcom.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();

		currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date());
		currentDateandTime = currentDateandTime.replace(" ", "T");
		Log.d("date", currentDateandTime);
		currentDateandTime = currentDateandTime + "Z";
		Constant.currentdate = currentDateandTime;
		Log.d("date", currentDateandTime);

		if (Constant.con_name != null && Constant.con_name.size() == 0) {
			Constant.con_name.add("  Home  ");
		} else {
			Constant.con_name.clear();
			mAdapter.notifyDataSetChanged();
			Constant.con_name.add("  Home  ");
		}

		if (Constant.con_list != null && Constant.con_list.size() == 0) {
			Constant.con_list.add("exit");
		} else {
			Constant.con_list.clear();
			mAdapter.notifyDataSetChanged();
			Constant.con_list.add("exit");
		}

		lv_hmp.setAdapter(mAdapter);
		lv_hadd.setAdapter(mAdapter);

		if (boo_main) {
			boo_main = false;
		} else {

			Constant.datee = getSharedPreferences("datefun",
					MODE_WORLD_WRITEABLE);
			String get_text = null;
			if (Constant.datee != null
					&& Constant.datee.contains("access_token")) {

				get_text = Constant.datee.getString("access_token", null);
				Log.d("login", get_text);
			}

			Constant.vall = getSharedPreferences("want", MODE_WORLD_WRITEABLE);
			if (Constant.vall != null && Constant.vall.contains("values")) {

				valu = Constant.vall.getString("values", null);
				Log.d("valu", valu);
			}

			if (get_text != null) {

				whootin_user();

				Constant.pass = getSharedPreferences("fun",
						MODE_WORLD_WRITEABLE);
				String ups = null;
				if (Constant.pass != null && Constant.pass.contains("up")) {
					ups = Constant.pass.getString("up", "");
					Log.d("ups", ups);
				}

				if (ran) {
					vf_lgn1.setVisibility(View.GONE);
					vf_lgn.setVisibility(View.GONE);
					vf_lgn2.setVisibility(View.VISIBLE);
					vf_lgn2.setDisplayedChild(3);
					Log.d("intent", "intent");
					if (Constant.isInternetPresent) {
						new download().execute(valu);
					} else {
						toastsettext("No Internet Connection");
					}

				} else {
					ran = false;
					vf_lgn1.setVisibility(View.GONE);
					vf_lgn2.setVisibility(View.GONE);
					vf_lgn.setVisibility(View.VISIBLE);
					vf_lgn.setDisplayedChild(2);
					Log.d("intent", "not intent");
					if (Constant.isInternetPresent) {
						new download().execute(valu);
					} else {
						toastsettext("No Internet Connection");
					}
				}

			} else {
				vf_lgn1.setVisibility(View.GONE);
				vf_lgn2.setVisibility(View.GONE);
				vf_lgn.setVisibility(View.VISIBLE);
				vf_lgn.setDisplayedChild(0);

			}
		}
	}

}

// btn_termsbacky
