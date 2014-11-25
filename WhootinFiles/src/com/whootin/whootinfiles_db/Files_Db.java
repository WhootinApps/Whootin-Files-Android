package com.whootin.whootinfiles_db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.whootin.wf.Login_Activity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


public class Files_Db extends SQLiteOpenHelper{

	public static final String Table = "sendfiles";
	
	public ArrayList<HashMap<String, String>>  getAllTranslates(String table, Boolean main) {
		Constant.wordList.clear();
		
		if(main == true){
			HashMap<String, String> map = new HashMap<String, String>();
			map.clear();
			map.put(Files_Db.Auto_id, "1");
			map.put(Files_Db.File_id, "1" );
			map.put(Files_Db.File_name, "  Gallery Uploads");
			map.put(Files_Db.File_createat, Constant.currentdate);
			map.put(Files_Db.File_size,  "123456");
			map.put(Files_Db.File_folder, "folder" );
			map.put(Files_Db.File_thumb, "");
			map.put(Files_Db.File_url, "");
			Constant.wordList.add(map);
		}else{
			HashMap<String, String> map = new HashMap<String, String>();
			map.clear();
			map.put(Files_Db.Auto_id, "1");
			map.put(Files_Db.File_id, "1" );
			map.put(Files_Db.File_name, "  Up to Whootin Files");
			map.put(Files_Db.File_createat,  Constant.currentdate);
			map.put(Files_Db.File_size,  "123456");
			map.put(Files_Db.File_folder, "folder" );
			map.put(Files_Db.File_thumb, "");
			map.put(Files_Db.File_url, "");
			Constant.wordList.add(map);
		}
		
		String selectQuery = "SELECT  * FROM " + table;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.clear();
				Log.d("log", cursor.getString(2));
				map.put(Files_Db.Auto_id, String.valueOf(cursor.getString(0)));
				map.put(Files_Db.File_id,  cursor.getString(1));
				map.put(Files_Db.File_name, cursor.getString(2));
				map.put(Files_Db.File_createat, cursor.getString(3));
				map.put(Files_Db.File_size,  cursor.getString(4));
				map.put(Files_Db.File_folder, cursor.getString(5));
				map.put(Files_Db.File_thumb, cursor.getString(6));
				map.put(Files_Db.File_url, cursor.getString(7));
				Constant.wordList.add(map);
			} while (cursor.moveToNext());
		}
		Log.d("all", "allArrayList");
		Log.d("sizeof wordList", String.valueOf(Constant.wordList.size()));
		cursor.close();
		db.close();
		return Constant.wordList ;
	}
	
	public static final String db_path = "/data/data/com.send.whootinfiles/databases/";
	public static final String db_name = "whootinfiles.db";
	private String connect = db_path + db_name;
	public static final int db_version = 2;
	private Context context;

	public static final String Auto_id = "auto_id", File_id = "id", File_name = "name", File_createat = "created_at",
			File_size = "file_size",File_folder = "type", File_thumb = "short_url",
			File_url = "url";
	public static final String File_Book = "create table " + Table
			+ " (" + Auto_id + " integer primary key autoincrement, "
			+ File_id + " text," + File_name + " text,"
			+ File_createat + " text," + File_size + " text,"
			+ File_folder + " text," + File_thumb + " text,"
			+ File_url + " text" + ");";
	
	public Files_Db(Context context) {
		super(context, db_name, null, db_version);
		this.context = context;
	}
	
	public boolean openDataBase() throws SQLException {
		Log.d("oppn", "open");

		boolean exist = false;
		boolean store = this.exists(Table);
		
		if (store == false) {
			try {
				Constant.db_sql = this.getWritableDatabase();
				Constant.db_sql.execSQL(File_Book);
				Constant.db_sql.close();
				Log.d("sucess_db", "Success_create table" + Table);
			} catch (Exception e) {
				Log.e("Table", "Table", e);
			}
		}
		return exist;
	}

	public boolean exists(String TABLE_CONTACTS) {
		try {
			Constant.db_sql = this.getReadableDatabase();
			Constant.db_sql.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
			Constant.db_sql.close();
			Log.d("sucess_db", "Success_create table" + TABLE_CONTACTS);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void copyDb() {
		Log.d("oppn", "copy");

		try {
			InputStream input = context.getAssets().open("whootinfiles.db");
			OutputStream output = new FileOutputStream("/data/data/com.whootin.whootinfiles/databases/whootinfiles.db");
			byte[] buffer = new byte[1024];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			output.flush();
			output.close();
			input.close();
			Log.d("db", "successful created db");
		} catch (Exception e) {
			Log.e("copy_db", e.toString());
		}

	}

	public boolean Checkdb() {
		File file = context.getDatabasePath(db_name);
		Log.d("log", file.toString());
		if (file.exists()) {
			Log.d("db", "db exists");
		} else {
			Log.d("db", "db couldnt find");
		}
		return file.exists() ? true : false;
	}

	
	@Override
	public synchronized void close() {
		if (Constant.db_sql != null) {
			Constant.db_sql.close();
		}
		super.close();
	}
	
	public void createDataBase() {
		boolean db_exist = Checkdb();
		if (db_exist) {

		} else {
			this.getReadableDatabase();
			try {
				copyDb();
				Log.d("oppn", "copy");
				openDataBase();
			} catch (Exception e) {
				Log.d("db_create", e.toString());
			}
		}
	}
	
	public List<ContentValue> getAllList() {
		
		List<ContentValue> list = new ArrayList<ContentValue>();
		list.clear();
		Constant.db_sql = this.getWritableDatabase();
		String querty = "SELECT * FROM " + Table;
		Cursor cursor = Constant.db_sql.rawQuery(querty, null);

		if (cursor.moveToFirst()) {
			do {
				Log.d("log", cursor.getString(2));
				ContentValue contentvalue = new ContentValue();
				contentvalue.setAuto_id(Integer.valueOf(cursor.getString(0)));
				contentvalue.setFile_id(cursor.getString(1));
				contentvalue.setFile_name(cursor.getString(2));
				contentvalue.setFile_createat(cursor.getString(3));
				contentvalue.setFile_size(cursor.getString(4));
				contentvalue.setFile_folder(cursor.getString(5));
				contentvalue.setFile_thumb(cursor.getString(6));
				contentvalue.setFile_url(cursor.getString(7));
				Log.d("type", "type1");
				list.add(contentvalue);
			} while (cursor.moveToNext());
		}
		cursor.close();
		Constant.db_sql.close();
		return list;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
