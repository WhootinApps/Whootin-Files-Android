package com.whootin.whootinfiles_db;

public class ContentValue {
	
	public static String id, name, createat, filesize, folder, thumb, url;
	public static int auto_id;

	public void setaudo_id(int uname) {
		this.auto_id = uname;
	}

	public void setid(String mail) {
		this.id = mail;
	}

	public void setname(String password) {
		this.name = password;
	}

	public void setothers(String others) {
		this.createat = others;
	}

	public void setremarks(String remark) {
		this.filesize = remark;
	}

	public void setfolder(String account) {
		this.folder = account;
	}
	
	public void setthumb(String thumb) {
		this.thumb = thumb;
	}

	public void seturl(String opassword) {
		this.url = opassword;
	}
	

	public void setAuto_id(Integer valueOf) {
		this.auto_id = valueOf ;
		
	}

	public void setFile_id(String string) {
		this.id = string;
		
	}

	public void setFile_name(String string) {
		this.name = string;
	}

	public void setFile_createat(String string) {
		 this.createat = string;
	}

	public void setFile_size(String string) {
		 this.filesize = string;
	}

	public void setFile_folder(String string) {
		this.folder = string;
	}

	public void setFile_thumb(String string) {
		this.thumb = string;
	}

	public void setFile_url(String string) {
		this.url = string;
	}
	
	public static String geurl() {
		return url;
	}

	public static String getthumb() {
		return thumb;
	}

	public static String getfolder() {
		return folder;
	}

	public static String getfilesize() {
		return filesize;
	}
	
	public static String getcreateat() {
		return createat;
	}

	public static String getname() {
		return name;
	}

	public static String gettd() {
		return id;
	}

	public static int getauto_id() {
		return auto_id;
	}
	
	
	

}
