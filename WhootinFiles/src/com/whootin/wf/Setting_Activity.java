package com.whootin.wf;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.whootin.passcodelock.AppLockManager;
import com.whootin.passcodelock.PasscodePreferencesActivity;
import com.whootin.whootinfiles_db.Constant;
import com.whootin.whootinfiles_db.UserProfileImagew;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Setting_Activity extends Activity implements OnClickListener {

	private String user_url = null, user_uname = null, user_plan = null,
			user_size = null, stng_sztxt = null, stng_free = "Free",
			stng_good = "Good", stng_better = "Better", stng_best = "Best";

	int stng_cal = 0;
	private Context context;
	private Button btn_sgnout, btn_privacy, btn_frnds, btn_help, btn_lock,
			btn_gb, btn_percentage, btn_sttngback,
			btn_privacyback, btn_privacyok, btn_done;
	private RelativeLayout rl_click;
	private TextView txt_onoff;
	private TextView edt_stnm;
	private ViewFlipper vf_cb, vf_mn;
	private ImageView img_prf;
	private WebView webpage_stng;
	private TextView[] txt_like;
	private Double convertedValue = null;

	public static ArrayList<HashMap<String, String>> view_page = new ArrayList<HashMap<String, String>>();

	@Override
	public void onBackPressed() {
		if (vf_mn.getDisplayedChild() == 1 || vf_mn.getDisplayedChild() == 2) {
			vf_mn.setDisplayedChild(0);
		} else {
			super.onBackPressed();
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		context = this;
		Bundle bundle = getIntent().getExtras();
		user_uname = bundle.getString("uname");
		user_url = bundle.getString("url");
		user_plan = bundle.getString("plan");
		user_size = bundle.getString("size");
		Log.d("sie", user_size + " si " + user_plan);
		if (user_plan.contentEquals(stng_best)) {
			stng_sztxt = " of 1TB";
			stng_cal = 1024 * 1024;
		} else if (user_plan.contentEquals(stng_better)) {
			stng_sztxt = " of 100GB";
			stng_cal = 1024 * 100;
		} else if (user_plan.contentEquals(stng_free)) {
			stng_sztxt = " of 2GB";
			stng_cal = 1024 * 2;
		} else if (user_plan.contentEquals(stng_good)) {
			stng_sztxt = " of 20GB";
			stng_cal = 1024 * 20;
		}

		findViewById();
		declaration();
	}

	private void findViewById() {
		btn_frnds = (Button) findViewById(R.id.stng_friend);
		btn_help = (Button) findViewById(R.id.stng_help);
		btn_lock = (Button) findViewById(R.id.stng_passcode);
		btn_percentage = (Button) findViewById(R.id.stng_per);
		btn_privacy = (Button) findViewById(R.id.stng_privacy);
		btn_sgnout = (Button) findViewById(R.id.stng_singout);
		btn_sttngback = (Button) findViewById(R.id.stng_back);
		btn_gb = (Button) findViewById(R.id.stng_gb);
		vf_cb = (ViewFlipper) findViewById(R.id.stng_aniviewflipper);
		img_prf = (ImageView) findViewById(R.id.stng_prfimg);
		edt_stnm = (TextView) findViewById(R.id.stng_edtname);
		vf_mn = (ViewFlipper) findViewById(R.id.stng_viewflipper);
		btn_done = (Button) findViewById(R.id.stng1_done);
		txt_onoff = (TextView) findViewById(R.id.stng1_onoff);
		txt_onoff.setText(Constant.onoff);
		rl_click = (RelativeLayout) findViewById(R.id.stng1_namerl);

		btn_privacyback = (Button) findViewById(R.id.stng_policyback);
		btn_privacyok = (Button) findViewById(R.id.stng_policyok);
		webpage_stng = (WebView) findViewById(R.id.stng_webpage);

	}

	private void declaration() {
		if (user_url != null) {
			new UserProfileImagew(img_prf, user_url, context).execute();
		}

		if (user_uname != null) {
			edt_stnm.setText(user_uname);
		}

		btn_sgnout.setOnClickListener(this);
		btn_privacy.setOnClickListener(this);
		btn_frnds.setOnClickListener(this);
		btn_help.setOnClickListener(this);
		btn_lock.setOnClickListener(this);
		btn_percentage.setOnClickListener(this);
		btn_sttngback.setOnClickListener(this);
		btn_privacyback.setOnClickListener(this);
		btn_privacyok.setOnClickListener(this);
		btn_done.setOnClickListener(this);
		rl_click.setOnClickListener(this);
		btn_gb.setOnClickListener(this);

		WebSettings webSetting = webpage_stng.getSettings();
		webpage_stng.getSettings().setBuiltInZoomControls(true);
		webpage_stng.getSettings().setSupportZoom(true);
		webpage_stng.setBackgroundColor(Color.TRANSPARENT);
		webpage_stng.getSettings().setJavaScriptEnabled(true);
		webpage_stng.setWebViewClient(new AppWebViewClients());
		view_page.clear();
		for (int i = 0; i < 2; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			String txts = null;
			Log.d("tx", stng_sztxt);
			if (i == 0) {
				txts = bytes();
			} else if (i == 1) {
				txts = percentage();
			}
			if (user_plan.contentEquals(stng_best)) {
				stng_sztxt = " of 1TB";
				map.put("txt", txts + stng_sztxt);
				btn_gb.setText("TB");
			} else if (user_plan.contentEquals(stng_better)) {
				stng_sztxt = " of 100GB";
				map.put("txt", txts + stng_sztxt);
				btn_gb.setText("GB");
			} else if (user_plan.contentEquals(stng_free)) {
				stng_sztxt = " of 2GB";
				map.put("txt", txts + stng_sztxt);
				btn_gb.setText("GB");
			} else if (user_plan.contentEquals(stng_good)) {
				stng_sztxt = " of 20GB";
				map.put("txt", txts + stng_sztxt);
				btn_gb.setText("GB");
			}

			view_page.add(map);
		}

		txt_like = new TextView[view_page.size()];
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		for (int index = 0; index < view_page.size(); index++) {
			final View itemView = inflater.inflate(R.layout.inflate_vfmp, null);
			txt_like[index] = (TextView) itemView.findViewById(R.id.ivmp_txt);
			String up = view_page.get(index).get("txt");
			txt_like[index].setText(up);
			vf_cb.addView(itemView);
		}
		vf_cb.setDisplayedChild(0);

	}

	private String bytes() {
		String get = null;
		int multiplyFactor = 0;
		convertedValue = new Double(user_size);
		while (convertedValue > 1024) {
			convertedValue /= 1024;
			multiplyFactor++;
		}

		String nets = String.valueOf(convertedValue);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		Log.d("percentage1", String.valueOf(nf.format(convertedValue)));
		String y = String.valueOf(nf.format(convertedValue));
		double z = Double.valueOf(y);
		DecimalFormat df = new DecimalFormat("#,###,##0");
		String gets = String.valueOf(df.format(z));
		Log.d("percentage3", String.valueOf(df.format(z)));
		Log.d("nets", nets);

		if (multiplyFactor == 0) {
			get = String.valueOf(gets) + "bytes";
		} else if (multiplyFactor == 1) {
			get = String.valueOf(gets) + "KB";
		} else if (multiplyFactor == 2) {
			get = String.valueOf(gets) + "MB";
		} else if (multiplyFactor == 3) {
			get = String.valueOf(gets) + "GB";
		} else if (multiplyFactor == 4) {
			get = String.valueOf(gets) + "TB";
		}

		return get;
	}

	private String percentage() {
		Double not = null;

		if (user_plan.contentEquals(stng_best)) {
			not = (convertedValue / stng_cal) * 100;
		} else if (user_plan.contentEquals(stng_better)) {
			not = (convertedValue / stng_cal) * 100;
		} else if (user_plan.contentEquals(stng_free)) {
			not = (convertedValue / stng_cal) * 100;
		} else if (user_plan.contentEquals(stng_good)) {
			not = (convertedValue / stng_cal) * 100;
		}

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(3);
		Log.d("percentage1", String.valueOf(nf.format(not)));
		String gets = String.valueOf(nf.format(not));
		return gets + "%";
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
			webpage_stng.loadUrl("file:///android_asset/agreement.html");

		}
	}

	public class AppWebViewClients extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public void onClick(View v) {

		if (v == btn_sgnout) {
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
					setResult(RESULT_OK);
					finish();
				}
			});

			alert_dialog.show();

		} else if (v == btn_frnds) {
			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_SUBJECT, "Whootin Files");
			String stng = "";
			if (user_uname != null) {
				stng = user_uname + ",";
			}
			email.putExtra(
					Intent.EXTRA_TEXT,
					stng
							+ "\r\n"
							+ "\r\n"
							+ "I've been using Whootin and thought you might like it. it's a free way to bring all your anywhere and share them easily http://whootin.com"
							+ "\r\n" + "\r\n" + "\r\n"
							+ "Sent By Nua Trans Media");
			email.setType("text/plain");
			startActivity(Intent.createChooser(email,
					"Choose an Email client :"));
		} else if (v == btn_help) {

		} else if (v == btn_lock) {
			vf_mn.setDisplayedChild(2);
			txt_onoff.setText(Constant.onoff);
		} else if (v == btn_percentage) {
			btn_percentage.setVisibility(View.GONE);
			btn_gb.setVisibility(View.VISIBLE);
			vf_cb.setInAnimation(AnimationUtils.loadAnimation(context,
					R.anim.slide_in_up));
			vf_cb.setOutAnimation(AnimationUtils.loadAnimation(context,
					R.anim.slide_out_up));
			vf_cb.showNext();
		} else if (v == btn_gb) {
			btn_gb.setVisibility(View.GONE);
			btn_percentage.setVisibility(View.VISIBLE);
			vf_cb.setInAnimation(AnimationUtils.loadAnimation(context,
					R.anim.slide_in_up));
			vf_cb.setOutAnimation(AnimationUtils.loadAnimation(context,
					R.anim.slide_out_up));
			vf_cb.showPrevious();
		} else if (v == btn_sttngback) {
			finish();
		} else if (v == btn_privacy) {
			vf_mn.setDisplayedChild(1);
			new doInBackGround2().execute();
		} else if (v == btn_privacyback) {
			vf_mn.setDisplayedChild(0);
		} else if (v == btn_privacyok) {
			vf_mn.setDisplayedChild(0);
		} else if (v == rl_click) {
			Intent ints = new Intent(Setting_Activity.this,
					PasscodePreferencesActivity.class);
			startActivityForResult(ints, 121);
		} else if (v == btn_done) {
			vf_mn.setDisplayedChild(0);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 121) {
			if (resultCode == RESULT_OK) {
				txt_onoff.setText(Constant.onoff);
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (AppLockManager.getInstance().getCurrentAppLock() != null) {
			if (AppLockManager.getInstance().getCurrentAppLock()
					.isPasswordLocked()) {
				Constant.onoff = "Off";
			} else {
				Constant.onoff = "On";
			}
			txt_onoff.setText(Constant.onoff);
		}
	}

}
