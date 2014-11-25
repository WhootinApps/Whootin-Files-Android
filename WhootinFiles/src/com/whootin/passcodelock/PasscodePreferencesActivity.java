package com.whootin.passcodelock;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.whootin.wf.R;
import com.whootin.whootinfiles_db.Constant;

@SuppressLint("ResourceAsColor")
public class PasscodePreferencesActivity extends Activity implements
		OnClickListener {

	static final int ENABLE_PASSLOCK = 0;
	static final int DISABLE_PASSLOCK = 1;
	static final int CHANGE_PASSWORD = 2;

	private Button btn_pcOnOff, btn_chngpc, btn_prfncback;

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Toast.makeText(getApplicationContext(), "PasscodePreferencesActivity", Toast.LENGTH_LONG).show();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preference);
		findViewById();
		declaration();

	}

	@SuppressLint("ResourceAsColor")
	private void declaration() {

		overridePendingTransition(R.anim.slide_up, R.anim.do_nothing);
		if (AppLockManager.getInstance().getCurrentAppLock() != null) {
			if (AppLockManager.getInstance().getCurrentAppLock()
					.isPasswordLocked()) {
				btn_pcOnOff.setText("Turn Passcode Off");
				btn_chngpc.setTextColor(this.getResources().getColor(
						R.color.txt_clr));
				btn_chngpc.setBackgroundDrawable(this.getResources()
						.getDrawable(R.drawable.txt_box_set1));
				btn_chngpc.setEnabled(true);
			} else {
				btn_pcOnOff.setText("Turn Passcode On");
				btn_chngpc.setBackgroundDrawable(this.getResources()
						.getDrawable(R.drawable.txt_box_set2));
				btn_chngpc.setTextColor(this.getResources().getColor(
						R.color.black));
				btn_chngpc.setEnabled(false);
			}
			btn_pcOnOff.setOnClickListener(this);
			btn_prfncback.setOnClickListener(this);
			btn_chngpc.setOnClickListener(this);
		}

		
	}

	private void findViewById() {
		btn_pcOnOff = (Button) findViewById(R.id.prfnc_onoff);
		btn_chngpc = (Button) findViewById(R.id.prfnc_enable);
		btn_prfncback = (Button) findViewById(R.id.prfnc_back);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// ignore orientation change
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case DISABLE_PASSLOCK:
			break;
		case ENABLE_PASSLOCK:
		case CHANGE_PASSWORD:
			if (resultCode == RESULT_OK) {
				toastsettext("Passcode set");
			}
			break;
		default:
			break;
		}
		updateUI();
		finish();
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

	private void updateUI() {
		if (AppLockManager.getInstance().getCurrentAppLock().isPasswordLocked()) {
			btn_pcOnOff.setText("Turn Passcode Off");
			Constant.onoff = "Off";
			btn_chngpc.setBackgroundDrawable(this.getResources().getDrawable(
					R.drawable.txt_box_set1));
			btn_chngpc.setTextColor(this.getResources().getColor(
					R.color.txt_clr));
			btn_chngpc.setEnabled(true);
		} else {
			Constant.onoff = "On";
			btn_chngpc.setBackgroundDrawable(this.getResources().getDrawable(
					R.drawable.txt_box_set2));
			btn_chngpc
					.setTextColor(this.getResources().getColor(R.color.black));
			btn_pcOnOff.setText("Turn Passcode On");
			btn_chngpc.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == btn_pcOnOff) {
			int type = AppLockManager.getInstance().getCurrentAppLock()
					.isPasswordLocked() ? DISABLE_PASSLOCK : ENABLE_PASSLOCK;
			Intent i = new Intent(PasscodePreferencesActivity.this,
					PasscodeManagePasswordActivity.class);
			i.putExtra("type", type);
			startActivityForResult(i, type);
		} else if (v == btn_chngpc) {

			Intent i = new Intent(PasscodePreferencesActivity.this,
					PasscodeManagePasswordActivity.class);
			i.putExtra("type", CHANGE_PASSWORD);
			i.putExtra("message",
					getString(R.string.passcode_enter_old_passcode));
			startActivityForResult(i, CHANGE_PASSWORD);
		} else if (v == btn_prfncback) {
			finish();
		}

	}

}