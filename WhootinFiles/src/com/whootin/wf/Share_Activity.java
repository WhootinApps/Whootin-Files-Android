package com.whootin.wf;

import java.util.ArrayList;
import java.util.HashSet;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class Share_Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rough);
		Intent intent = getIntent();
		String action = intent.getAction();
		Bundle extras = intent.getExtras();
		String type = intent.getType();
		action = action + "1";
		type = type + "2";
		boolean ran = false;
		if (Intent.ACTION_SEND.equals(intent.getAction())) {
			Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
			if (uri != null) {
				String get = getPathfromUri(uri);
				String path = parseUriToFilename(uri);
				Log.d("path", path);
				Log.d("get", get);
				ran = true;
			}
		} else if (Intent.ACTION_SEND_MULTIPLE.equals(intent.getAction())) {
			ArrayList<Uri> uris = intent
					.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
			if (uris != null) {
				for (Uri uri : uris) {
					ran = true;
					String get = getPathfromUri(uri);
					String path = parseUriToFilename(uri);
					Log.d("path", path);
					Log.d("get", get);
				}
			}
		}

	}
	
	public String getPathfromUri(Uri uri) {
		 if(uri.toString().startsWith("file://")) 
		       return uri.getPath();
		 String[] projection = { MediaStore.Images.Media.DATA };
		 Cursor cursor = managedQuery(uri, projection, null, null, null);
		 startManagingCursor(cursor);
		 int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		 cursor.moveToFirst();
		 String path= cursor.getString(column_index);
		 //cursor.close();
		 return path;
		}
	
	public String parseUriToFilename(Uri uri) {
		  String selectedImagePath = null;
		  String filemanagerPath = uri.getPath();

		  String[] projection = { MediaStore.Images.Media.DATA };
		  Cursor cursor = managedQuery(uri, projection, null, null, null);

		  if (cursor != null) {
		    // Here you will get a null pointer if cursor is null
		    // This can be if you used OI file manager for picking the media
		    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		    cursor.moveToFirst();
		    selectedImagePath = cursor.getString(column_index);
		  }

		  if (selectedImagePath != null) {
		    return selectedImagePath;
		  }
		  else if (filemanagerPath != null) {
		    return filemanagerPath;
		  }
		   return null;
		} 

}
