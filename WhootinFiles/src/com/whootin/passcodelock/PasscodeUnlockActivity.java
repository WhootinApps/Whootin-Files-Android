package com.whootin.passcodelock;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.whootin.wf.R;

public class PasscodeUnlockActivity extends AbstractPasscodeKeyboardActivity {
    
    @Override
    public void onBackPressed() {
    	Toast.makeText(getApplicationContext(), "PASSCODEUNLOCK", Toast.LENGTH_LONG).show();
    	if(AppLockManager.getInstance().getCurrentAppLock() != null){
    		AppLockManager.getInstance().getCurrentAppLock().forcePasswordLock();
            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            this.startActivity(i);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
    		getParent().finish();
    		System.exit(0);
    	}else{
    		finish();
            android.os.Process.killProcess(android.os.Process.myPid());
    		getParent().finish();
    		System.exit(0);
    	}
    }


    @Override
    protected void onPinLockInserted() {
        String passLock = pinCodeField1.getText().toString() + pinCodeField2.getText().toString() +
                pinCodeField3.getText().toString() + pinCodeField4.getText();
        if( AppLockManager.getInstance().getCurrentAppLock().verifyPassword(passLock) ) {
            setResult(RESULT_OK);
            finish();
        } else {
            Thread shake = new Thread() {
                public void run() {
                    Animation shake = AnimationUtils.loadAnimation(PasscodeUnlockActivity.this, R.anim.shake);
                    findViewById(R.id.AppUnlockLinearLayout1).startAnimation(shake);
                    showPasswordError();
                    pinCodeField1.setText("");
                    pinCodeField2.setText("");
                    pinCodeField3.setText("");
                    pinCodeField4.setText("");
                    pinCodeField1.requestFocus();
                }
            };
            runOnUiThread(shake);
        }
    }
}