package com.whootin.wf;

import com.whootin.passcodelock.AppLockManager;
import com.whootin.whootinfiles_db.Constant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class Whootin_Activity extends Activity{
	
	private int duration = 800;
	private Boolean splash_boo = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Constant.pass = getSharedPreferences("fun", MODE_WORLD_WRITEABLE);
		Constant.passcom = Constant.pass.edit();
		Constant.passcom.putString("up", "true");
		Constant.passcom.commit();
		Handler hand = new Handler();
		hand.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				finish();
				if(!splash_boo){
					Intent intent = new Intent(Whootin_Activity.this, Login_Activity.class);
					startActivity(intent);
				}
			}
		}, duration);
	}
	
	@Override
	public void onBackPressed() {
		splash_boo = true;
		super.onBackPressed();
	}
	
	
}
/*void saveData() {
	
Constant.pass = getSharedPreferences("fun", MODE_WORLD_WRITEABLE);
String ups = null;
if (Constant.pass != null && Constant.pass.contains("up")) {
	ups = Constant.pass.getString("up", "");
	//Log.d("access_token", ups);
}

if(ups == null){
	
}
Constant.pass = getSharedPreferences("fun", MODE_WORLD_WRITEABLE);
Constant.passcom = Constant.pass.edit();
Constant.passcom.putString("up", "false");
Constant.passcom.commit();
}*/