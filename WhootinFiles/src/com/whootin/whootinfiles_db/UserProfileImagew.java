package com.whootin.whootinfiles_db;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;


public class UserProfileImagew extends AsyncTask<URL, Void, Bitmap> {

	ImageView tIV;
	String uurl;
	Context context;

	public UserProfileImagew(ImageView iv, String rl, Context con) {
		tIV = iv;
		uurl = rl;
		context = con;
	}

	@Override
	protected Bitmap doInBackground(URL... urls) {

		Bitmap networkBitmap = null;
		InputStream conn = null;
		try {
			if (uurl != null) {
				if (uurl.contains("://")) {
					conn = new URL(uurl).openConnection().getInputStream();
				} else {
					conn = new URL("http://" + uurl).openConnection()
							.getInputStream();
				}
				networkBitmap = BitmapFactory.decodeStream(conn);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return networkBitmap;
	}

	@Override
	protected void onPostExecute(Bitmap newBitmap) {
		if (newBitmap != null) {
			Bitmap scaled = Constant.getCircularBitmap(newBitmap,
				context);
			tIV.setImageBitmap(scaled);
			tIV.setScaleType(ImageView.ScaleType.FIT_XY);
		}
	}

}
