package com.whootin.whootinfiles_db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.whootin.wf.Login_Activity;
import com.whootin.wf.R;
import com.whootin.whootinfiles_sdcard.SdAdapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.PorterDuff.Mode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Constant {

	public static final String[] zip_tit = { ".zip", ".ZIP" };

	public static final String[] doc_tit = { ".doc", ".DOC" };

	public static final String[] pdf_tit = { ".pdf", ".PDF" };

	public static final String[] ice_tit = { ".ice", ".ICE" };

	public static final String[] wmf_tit = { ".wmf", ".WMF" };

	public static final String[] ivr_tit = { ".ivr", ".IVR" };

	public static final String[] pvu_tit = { ".pvu", ".PVU" };

	public static final String[] chemical_tit = { ".xyz", ".pdb", ".XYZ",
			".PDB" };

	public static final String[] xgl_tit = { ".xgz", ".XGZ", ".xmz", ".XMZ" };

	public static final String[] multi_tit = { ".ustar", ".USTAR", ".gzip",
			".GZIP" };

	public static final String[] message_tit = { ".mime", ".MIME", ".mht",
			".MHT", ".mhtml", ".MHTML" };

	public static final String[] xworld_tit = { ".3dm", ".3DM", ".3dmf",
			".3DMF", ".qd3", ".QD3", ".qd3d", ".QD3D", ".3svr", ".3SVR",
			".vrt", ".VRT" };

	public static final String[] model_tit = { ".vrml", ".VRML", ".pov",
			".POV", ".iges", ".IGES", ".igs", ".IGS", ".wrl", ".WRL", ".wrz",
			".WRZ", ".dwf", ".DWF" };

	public static final String[] ppt_tit = { ".ppt", ".PPT", ".pptx", ".PPTX",
			".pptm", ".PPTM", ".pot", ".POT", ".potx", ".POTX", ".pps", ".PPS",
			".ppsx", ".PPSX", ".ppsm", ".PPSM" };

	public static final String[] xls_tit = { ".xlsx", ".XLSX", ".xls", ".XLS",
			".xlam", ".XLAM", ".xltm", ".XLTM", ".xlsm", ".XLSM", ".xlsb",
			".XLSB", ".xl", ".xla", ".xlb", ".xlc", ".XL", ".XLA", ".XLB",
			".XLC", ".xld", ".xlk", ".xll", ".xlm", ".XLD", ".XLK", ".XLL",
			".XLM", ".xlt", ".xlv", ".xlw", ".XLT", ".XLV", ".XLW" };

	public static final String[] html_tit = { ".html", ".HTML", ".shtml",
			".SHTML", ".acgi", ".ACGI", ".htm", ".HTM", ".htmls", ".HTMLS",
			".htt", ".HTT", ".htx", ".HTX" };

	public static final String[] aud_tit = { ".mp3", ".MP3", ".mp2", ".MP2",
			".wav", ".WAV", ".aac", ".AAC", ".ac3", ".AC3", ".aif", ".AIF",
			".aifc", ".AIFC", ".aiff", ".AIFF", ".funk", ".au", ".gsd", ".gsm",
			".FUNK", ".AU", ".GSD", ".GSM", ".it", ".jam", ".la", ".lam",
			".IT", ".JAM", ".LA", ".LAM", ".kar", ".lma", ".m2a", ".mid",
			".KAR", ".LMA", ".M2U", ".MID", ".midi", ".m3u", ".mjf", ".rm",
			".MIDI", ".M3U", ".MJF", ".RM", ".rmm", ".rmp", ".rpm", ".s3m",
			".RMM", ".RMP", ".RPM", ".S3M", ".sid", ".snd", ".tsi", ".voc,",
			".SID", ".SND", ".TSI", ".VOC", ".vox", ".vqe", ".vqf", ".vql",
			".VOX", ".VQE", ".VQF", ".VQL", ".xm", ".tsp", ".mod", ".mpa",
			".XM", ".TSP", ".MOD", ".MPA", ".my", ".mpg", ".mpga", ".pfunk",
			".MY", ".MPG", ".MPGA", "PFUNK", ".ra", ".qcp", ".ram", ".RA",
			".QCP", ".RAM" };

	public static final String[] vid_tit = { ".afl", ".AFL", ".mp4", ".MP4",
			".avi", ".AVI", ".3gp", ".3GP", ".mpeg", ".MPEG", ".flv", ".FLV",
			".mkv", ".MKV", ".mov", ".MOV", ".wmv", ".WMV", ".asf", ".ASF",
			".moov", ".mov", ".movie", ".mpe", ".MOOV", ".MOV", ".MOVIE",
			".MPE", ".mv", ".qt", ".qtc", ".gl", ".MV", ".QT", ".QTC", ".GL",
			".isu", ".rv", ".vdo", ".viv", ".ISU", ".RV", ".VDO", ".VIV",
			".vivo", ".vos", "xsr", ".xdr", ".VIVO", ".VOS", ".XSR", ".XDR",
			".scm", ".m1v", ".m2v", ".mjpg", ".SCM", ".M1V", ".M2V", ".MJPG",
			".fli", ".fmf", ".asf", ".asx", ".FLI", ".FMF", ".ASF", ".ASX",
			".avs", ".afl", ".dl", ".dif", "AVS", ".AFL", ".DL", ".DIF", ".dv",
			".DV" };

	public static final String[] txt_tit = { ".abc", ".ABC", ".acgi", "ACGI",
			".ksh", ".jav", ".java", ".lsp", ".KSH", ".JAV", ".JAVA", ".LSP",
			".lsx", ".lst", ".m", ".p", ".LSX", ".LST", ".M", ".P", ".pas",
			".pl", ".pm", ".mar", ".PAS", ".PL", ".PM", ".MAR", ".js", ".list",
			".log", ".mcf", ".JS", ".LIST", ".LOG", "MCF", ".rexx", ".sdml",
			".rtx", ".scm", ".REXX", ".SDML", ".RTX", ".SCM", ".s", ".rt",
			".rtf", "sgm", ".S", ".RT", ".RTF", ".SGM", ".sgml", ".spc",
			".ssi", ".talk", ".SGML", ".SPC", ".SSI", ".TALK", ".tcl", ".tcsh",
			".text", ".tsv", ".TCL", ".TCSH", ".TEXT", "TSV", ".txt", ".uil",
			".uni", ".unis", ".TXT", ".UIL", ".UNI", ".UNIS", ".uri", ".uris",
			".uu", ".uue", ".URI", ".URIS", ".UU", ".UUE", ".wml", ".wmls",
			".zsh", ".xml", ".WML", ".WMLS", ".ZSH", ".XML", ".wsc", ".vcs",
			".sh", ".WSC", ".VCS", ".SH", ".py", ".aip", ".asm", ".PY", ".AIP",
			".ASM", ".asp", ".c", ".c++", ".cc", ".ASP", ".C", ".C++", ".CC",
			".com", ".conf", ".cpp", ".csh", ".COM", ".CONF", ".CPP", ".CSH",
			".cxx", ".css", ".def", ".el", ".CXX", ".CSS", ".DEF", ".EL",
			".etx", ".f", ".f77", ".f90", ".ETX", ".F", "F77", "F90", ".hh",
			".hlb", ".htc", ".g", ".HH", ".HLB", ".HTC", ".G", ".h", ".idc",
			".flx", ".for", ".H", ".IDC", ".FLX", ".FOR" };

	public static final String[] img_tit = { ".png", ".jpg", ".jpeg", ".bmp",
			".svg", ".PNG", ".JPG", ".JPEG", ".BMP", ".SVG", ".ras", ".rast",
			".wbmp", "xwd", ".RAS", ".RAST", "WBMP", ".XWD", ".xpm", ".x-png",
			".xbm", ".rf", ".XPM", ".X-PNG", ".XBM", ".RF", ".rgb", ".tif",
			".tiff", ".svf", ".RGB", ".TIF", ".TIFF", ".SVF", ".rp", ".xif",
			".turbot", ".pgm", ".RP", ".XIF", ".TURBOT", ".PGM", ".pic",
			".pict", ".pm", ".pnm", ".PIC", ".PICT", ".PM", ".PNM", ".nap",
			".naplps", ".nif", ".niff", ".NAP", ".NAPLPS", ".NIF", ".NIFF",
			".pbm", ".pct", ".pcx", ".ico", ".PBM", ".PCT", ".PCX", ".ICO",
			".ief", ".iefs", ".dwg", ".dxf", ".IEF", ".IEFS", ".DWG", ".DXF",
			".art", ".bm", ".bmp", ".fif", ".ART", ".BM", ".BMP", ".FIF",
			".flo", ".fpx", ".g3", ".FLO", ".FPX", ".G3", ".FLO", ".gif",
			".jfif", ".jfif-tbnl", ".jpe", ".GIF", ".JFIF", ".JFIF-TBNL",
			".jps", ".jut", ".qif", ".qti", ".JPS", ".JUT", ".QIF", ".QTI",
			".ppm", ".PPM" };

	public static final String[] app_tit = { ".a", ".aab", ".aam", ".aas",
			".A", ".AAB", ".AAM", ".AAS", ".ai", ".aim", ".ani", ".aos", ".AI",
			".AIM", ".ANI", ".AOS", ".aps", ".arc", ".arj", ".asx", ".APS",
			".ARC", ".ARJ", ".ASX", ".bcpio", ".bin", ".boo", ".book",
			".BCPIO", ".BIN", ".BOO", ".BOOK", ".boz", ".bsh", ".bz", ".bz2",
			".BOZ", ".BSH", ".BZ", ".BZ2", ".cat", ".ccad", ".cco", ".cdf",
			".CAT", ".CCAD", ".CCO", ".CDF", ".cer", ".cha", ".chat", ".class",
			".CER", ".CHA", ".CHAT", ".CLASS",

			".der", ".dir", ".cpio", ".dcr", ".DER", ".DIR", ".CPIO", ".DCR",
			".deepv", ".css", ".cpt", ".crl", ".DEEPV", ".CSS", ".CPT", ".CRL",
			".crt", ".csh", ".dot", ".dp", ".CRT", ".CSH", ".DOT", ".DP",
			".drw", ".dump", ".dvi", ".elc", ".DRW", ".DUMP", ".DVI", ".ELC",
			".env", ".eps", ".es", ".dxf", ".ENV", ".EPS", ".ES", ".DXF",
			".evy", ".exe", ".dxf", "dwg", ".EVY", ".EXE", ".DXF", ".DWG",

			".gsp", ".gss", ".gtar", ".gz", ".GSP", ".GSS", ".GTAR", ".GZ",
			".gzip", ".hlp", ".hpg", ".hpgl", ".GZIP", ".HLP", ".HPG", ".HPGL",
			".hqx", ".hta", ".mpp", ".mpt", ".HQX", ".HTA", ".MPP", ".MPT",
			".mpv", ".mpx", ".mrc", ".ms", ".MPV", ".MPX", ".MRC", ".MS",
			".p10", ".p12", ".p7a", ".p7c", ".P10", ".P12", ".P7A", ".P7C",

			".p7m", ".p7r", ".p7s", ".part", ".P7M", ".P7R", ".P7S", ".PART",
			".nix", ".nsc", ".nvd", ".o", ".NIX", ".NSC", ".NVD", ".O", ".oda",
			".omc", ".omcd", ".omcr", ".ODA", ".OMX", ".OMCD", ".OMCR", ".vsd",
			".vst", ".vsw", ".w60", ".VSD", ".VST", ".VSW", ".W60", ".w61",
			".w6w", ".wb1", ".web", ".W61", ".W6W", ".WB1", ".WEB", ".wiz",
			".wk1", ".wmlc", ".wsrc", ".WIZ", ".WK1", ".WMLC", ".WSRC", ".wtk",
			".wmlsc", ".word", ".wp", ".WTK", ".WMLSC", ".WORLK", ".WP",
			".wp5", ".wp6", ".wpd", ".wq1", "WP5", ".WP6", ".WPD", ".WQ1",

			".wri", ".wrl", ".z", ".zoo", ".WRI", ".WRL", ".Z", ".ZOO", ".rm",
			".spl", ".spr", ".sprite", ".RM", ".SPL", ".SPR", ".SPRITE",
			".src", ".ssm", ".sst", ".step", ".SRC", ".SSM", ".SST", ".STEP",
			".stl", ".stp", ".sv4cpio", ".sv4crc", ".STL", ".STP", ".SV4CPIO",
			".SV4CRC", ".svr", ".sol", ".spc", ".tar", ".SVR", ".SOL", ".SPC",
			".TAR", ".tbk", ".tcl", ".swf", ".t", ".TBK", ".TCL", ".SWF", ".T",
			".TBK", ".tgz", ".tex", "texi", ".texinfo", ".TGZ", ".TEX",
			".TEXI", "TEXINFO", ".text", ".tr", ".ustar", ".vmd", ".TEXT",
			".TR", ".USTAR", ".VMD", ".vmf", ".vrml", ".vew", ".vda", ".VMF",
			".VRML", ".VEW", ".VDA", ".unv", ".uu", ".vcd", ".tsp", ".UNV",
			".UU", ".VCD", ".TSP", ".rtx", ".igs", ".iges", ".ima", ".RTX",
			".IGS", ".IGES", "IMA",

			".imap", ".inf", ".ins", "ima", ".IMAP", ".INF", ".INS", ".IMA",
			".inf", ".ins", ".ip", ".iv", "INF", ".INS", ".IP", ".IV", ".mid",
			".lsp", ".hdf", ".help", ".MID", ".LSP", ".HDF", ".HELP", ".hgl",
			".frl", ".fdf", ".fif", ".HGL", ".FRL", ".FDF", "FIF", ".rtf",
			".pcl", ".pm4", ".pm5", ".RTF", ".PCL", ".PM4", ".PM5", ".pnm",
			".plx", ".pot", ".pwz", ".PNM", ".PLX", ".POT", ".PWZ", ".ppa",
			".pyc", ".pkg", ".pko", ".PPA", ".PYC", ".PKG", ".PKO", ".nc",
			".ncm", ".mzz", ".mm", ".NC", ".NCM", ".MZZ", ".MM", ".mme",
			".midi", ".mpc", ".mif", ".MME", ".MIDI", ".MPC", ".MIF", ".ras",
			".shar", ".sh", ".sdp", ".RAS", ".SHAR", ".SH", ".SDP", ".sdr",
			".sea", ".sit", ".ppt", ".SDR", ".SDR", ".SIT", ".PPT", ".pps",
			".smil", ".smi", ".sl", ".PPS", ".SMIL", ".SMI", "SL", ".skt",
			".skm", ".skp", ".skd", ".SKT", ".SKM", ".SKP", ".SKD", ".ppz",
			".set", ".pre", ".prt", ".PPZ", ".SET", ".PRE", ".PRT", ".ps",
			".psd", ".jcm", ".ivy", ".PS", ".PSD", ".JCM", ".IVY", ".js",
			".latex", ".lha", ".lhx", ".JS", ".LATEX", ".LHA", ".LHX", ".man",
			".map", ".ltx", ".lzh", ".MAN", ".MAP", ".LTX", ".LZH", ".lzx",
			".ksh", ".mbd", ".mc$", ".LZX", ".KSH", ".MBD", ".MC$", ".mcd",
			".mcp", ".me", ".rng", ".MCD", ".MCP", ".ME", ".RNG", ".rnx",
			".roff", ".saveme", ".sbk", ".ROFF", ".SAVEME", ".SBK", ".scm",
			".SCM", ".png", ".jpg", ".jpeg", ".bmp", ".svg", ".PNG", ".JPG",
			".JPEG", ".BMP", ".SVG", ".ras", ".rast", ".wbmp", "xwd", ".RAS",
			".RAST", "WBMP", ".XWD", ".xpm", ".x-png", ".xbm", ".rf", ".XPM",
			".X-PNG", ".XBM", ".RF", ".rgb", ".tif", ".tiff", ".svf", ".RGB",
			".TIF", ".TIFF", ".SVF", ".rp", ".xif", ".turbot", ".pgm", ".RP",
			".XIF", ".TURBOT", ".PGM", ".pic", ".pict", ".pm", ".pnm", ".PIC",
			".PICT", ".PM", ".PNM", ".nap", ".naplps", ".nif", ".niff", ".NAP",
			".NAPLPS", ".NIF", ".NIFF", ".pbm", ".pct", ".pcx", ".ico", ".PBM",
			".PCT", ".PCX", ".ICO", ".ief", ".iefs", ".dwg", ".dxf", ".IEF",
			".IEFS", ".DWG", ".DXF", ".art", ".bm", ".bmp", ".fif", ".ART",
			".BM", ".BMP", ".FIF", ".flo", ".fpx", ".g3", ".FLO", ".FPX",
			".G3", ".FLO", ".gif", ".jfif", ".jfif-tbnl", ".jpe", ".GIF",
			"JFIF", ".JFIF-TBNL", ".jps", ".jut", ".qif", ".qti", ".JPS",
			".JUT", ".QIF", ".QTI", ".ppm", ".PPM", ".html", ".HTML", ".shtml",
			".SHTML", ".acgi", ".ACGI", ".htm", ".HTM", ".htmls", ".HTMLS",
			".htt", ".HTT", ".htx", ".HTX"

	};

	public static String else_tit[] = { ".a", ".aab", ".aam", ".aas", ".A",

	".AAM", ".AAS", ".ai", ".aim", ".ani", ".aos", ".AI", ".AIM", ".ANI",
			".AOS", ".aps", ".arc", ".arj", ".asx", ".APS", ".ARC", ".ARJ",
			".ASX", ".bcpio", ".bin", ".boo", ".book", ".BCPIO", ".BIN",
			".BOO", ".BOOK", ".boz", ".bsh", ".bz", ".bz2", ".BOZ", ".BSH",
			".BZ", ".BZ2", ".cat", ".ccad", ".cco", ".cdf",

			".CAT", ".CCAD", ".CCO", ".CDF", ".cer", ".cha", ".chat", ".class",
			".CER", ".CHA", ".CHAT", ".CLASS", ".der", ".dir", ".cpio", ".dcr",
			".DER", ".DIR", ".CPIO", ".DCR", ".deepv", ".css", ".cpt", ".crl",
			".DEEPV", ".CSS", ".CPT", ".CRL", ".crt", ".csh", ".dot", ".dp",
			".CRT", ".CSH", ".DOT", ".DP", ".drw", ".dump", ".dvi", ".elc",

			".DRW", ".DUMP", ".DVI", ".ELC", ".env", ".eps", ".es", ".dxf",
			".ENV", ".EPS", ".ES", ".DXF", ".evy", ".exe", ".dxf", "dwg",
			".EVY", ".EXE", ".DXF", ".DWG", ".gsp", ".gss", ".gtar", ".gz",
			".GSP", ".GSS", ".GTAR", ".GZ", ".gzip", ".hlp", ".hpg", ".hpgl",
			".GZIP", ".HLP", ".HPG", ".HPGL", ".hqx", ".hta", ".mpp", ".mpt",

			".HQX", ".HTA", ".MPP", ".MPT", ".mpv", ".mpx", ".mrc", ".ms",
			".MPV", ".MPX", ".MRC", ".MS", ".p10", ".p12", ".p7a", ".p7c",
			".P10", ".P12", ".P7A", ".P7C", ".p7m", ".p7r", ".p7s", ".part",
			".nix", ".nsc", ".nvd", ".o", ".NIX", ".NSC", ".NVD", ".O", ".oda",
			".omc", ".omcd", ".omcr", ".ODA", ".OMX", ".OMCD", ".OMCR", ".vsd",

			".vst", ".vsw", ".w60", ".VSD", ".VST", ".VSW", ".W60", ".w61",
			".w6w", ".wb1", ".web", ".W61", ".W6W", ".WB1", ".WEB", ".wiz",
			".wk1", ".wmlc", ".wsrc", ".WIZ", ".WK1", ".WMLC", ".WSRC", ".wtk",
			".wmlsc", ".word", ".wp", ".WTK", ".WMLSC", ".WORLK", ".WP",
			".wp5", ".wp6", ".wpd", ".wq1", "WP5", ".WP6", ".WPD", ".WQ1",

			".wri", ".wrl", ".z", ".zoo", ".WRI", ".WRL", ".Z", ".ZOO", ".rm",
			".spl", ".spr", ".sprite", ".RM", ".SPL", ".SPR", ".SPRITE",
			".AAB", ".src", ".ssm", ".sst", ".step", ".SRC", ".SSM", ".SST",
			".STEP", ".stl", ".stp", ".sv4cpio", ".sv4crc", ".STL", ".STP",
			".SV4CPIO", ".SV4CRC", ".svr", ".sol", ".spc", ".tar", ".SVR",
			".SOL", ".SPC",

			".TAR", ".tbk", ".tcl", ".swf", ".t", ".TBK", ".TCL", ".SWF", ".T",
			".TBK", ".tgz", ".tex", "texi", ".texinfo", ".TGZ", ".TEX",
			".TEXI", "TEXINFO", ".text", ".tr", ".ustar", ".vmd", ".TEXT",
			".TR", ".USTAR", ".VMD", ".vmf", ".vrml", ".vew", ".vda", ".VMF",
			".VRML", ".VEW", ".VDA", ".unv", ".uu", ".vcd", ".tsp", ".UNV",

			".UU", ".VCD", ".TSP", ".rtx", ".igs", ".iges", ".ima", ".RTX",
			".IGS", ".IGES", "IMA", ".P7M", ".P7R", ".P7S", ".PART", ".imap",
			".inf", ".ins", "ima", ".IMAP", ".INF", ".INS", ".IMA", ".inf",
			".ins", ".ip", ".iv", "INF", ".INS", ".IP", ".IV", ".mid", ".lsp",
			".hdf", ".help", ".MID", ".LSP", ".HDF", ".HELP", ".hgl",

			".frl", ".fdf", ".fif", ".HGL", ".FRL", ".FDF", "FIF", ".rtf",
			".pcl", ".pm4", ".pm5", ".RTF", ".PCL", ".PM4", ".PM5", ".pnm",
			".plx", ".pot", ".pwz", ".PNM", ".PLX", ".POT", ".PWZ", ".ppa",
			".pyc", ".pkg", ".pko", ".PPA", ".PYC", ".PKG", ".PKO", ".nc",
			".ncm", ".mzz", ".mm", ".NC", ".NCM", ".MZZ", ".MM", ".mme",

			".midi", ".mpc", ".mif", ".MME", ".MIDI", ".MPC", ".MIF", ".ras",
			".shar", ".sh", ".sdp", ".RAS", ".SHAR", ".SH", ".SDP", ".sdr",
			".sea", ".sit", ".ppt", ".SDR", ".SDR", ".SIT", ".PPT", ".pps",
			".smil", ".smi", ".sl", ".PPS", ".SMIL", ".SMI", "SL", ".skt",
			".skm", ".skp", ".skd", ".SKT", ".SKM", ".SKP", ".SKD", ".ppz",

			".set", ".pre", ".prt", ".PPZ", ".SET", ".PRE", ".PRT", ".ps",
			".psd", ".jcm", ".ivy", ".PS", ".PSD", ".JCM", ".IVY", ".js",
			".latex", ".lha", ".lhx", ".JS", ".LATEX", ".LHA", ".LHX", ".man",
			".map", ".ltx", ".lzh", ".MAN", ".MAP", ".LTX", ".LZH", ".lzx",
			".ksh", ".mbd", ".mc$", ".LZX", ".KSH", ".MBD", ".MC$", ".mcd",

			".mcp", ".me", ".rng", ".MCD", ".MCP", ".ME", ".RNG", ".rnx",
			".roff", ".saveme", ".sbk", ".ROFF", ".SAVEME", ".SBK", ".scm",
			".SCM", ".abc", ".ABC", ".acgi", "ACGI", ".ksh", ".jav", ".java",
			".lsp", ".KSH", ".JAV", ".JAVA", ".LSP", ".lsx", ".lst", ".m",
			".p", ".LSX", ".LST", ".M", ".P", ".pas", ".pl", ".pm", ".mar",

			".PAS", ".PL", ".PM", ".MAR", ".js", ".list", ".log", ".mcf",
			".JS", ".LIST", ".LOG", "MCF", ".rexx", ".sdml", ".rtx", ".scm",
			".REXX", ".SDML", ".RTX", ".SCM", ".s", ".rt", ".rtf", "sgm", ".S",
			".RT", ".RTF", ".SGM", ".sgml", ".spc", ".ssi", ".talk", ".SGML",
			".SPC", ".SSI", ".TALK", ".tcl", ".tcsh", ".text", ".tsv", ".TCL",

			".TCSH", ".TEXT", "TSV", ".txt", ".uil", ".uni", ".unis", ".TXT",
			".UIL", ".UNI", ".UNIS", ".uri", ".uris", ".uu", ".uue", ".URI",
			".URIS", ".UU", ".UUE", ".wml", ".wmls", ".zsh", ".xml", ".WML",
			".WMLS", ".ZSH", ".XML", ".wsc", ".vcs", ".sh", ".WSC", ".VCS",
			".SH", ".py", ".aip", ".asm", ".PY", ".AIP", ".ASM", ".asp", ".c",

			".c++", ".cc", ".ASP", ".C", ".C++", ".CC", ".com", ".conf",
			".cpp", ".csh", ".COM", ".CONF", ".CPP", ".CSH", ".cxx", ".css",
			".def", ".el", ".CXX", ".CSS", ".DEF", ".EL", ".etx", ".f", ".f77",
			".f90", ".ETX", ".F", "F77", "F90", ".hh", ".hlb", ".htc", ".g",
			".HH", ".HLB", ".HTC", ".G", ".h", ".idc", ".flx", ".for", ".H",

			".afl", ".AFL", ".mp4", ".MP4", ".avi", ".AVI", ".3gp", ".3GP",
			".mpeg", ".MPEG", ".flv", ".FLV", ".mkv", ".MKV", ".mov", ".MOV",
			".wmv", ".WMV", ".asf", ".ASF", ".moov", ".mov", ".movie", ".mpe",
			".MOOV", ".MOV", ".MOVIE", ".MPE", ".mv", ".qt", ".qtc", ".gl",
			".MV", ".QT", ".QTC", ".GL", ".isu", ".rv", ".vdo", ".viv", ".ISU",
			".RV", ".VDO", ".VIV", ".vivo", ".vos", "xsr", ".xdr", ".VIVO",
			".VOS", ".XSR", ".XDR", ".scm", ".m1v", ".m2v", ".mjpg", ".SCM",
			".M1V", ".M2V", ".MJPG", ".fli", ".fmf", ".asf", ".asx", ".FLI",
			".FMF", ".ASF", ".ASX", ".avs", ".afl", ".dl", ".dif", "AVS",
			".AFL", ".DL", ".DIF", ".dv", ".DV", ".mp3", ".MP3", ".mp2",
			".MP2", ".wav", ".WAV", ".aac", ".AAC", ".ac3", ".AC3", ".aif",
			".AIF", ".aifc", ".AIFC", ".aiff", ".AIFF", ".funk", ".au", ".gsd",
			".gsm", ".FUNK", ".AU", ".GSD", ".GSM", ".it", ".jam", ".la",
			".lam", ".IT", ".JAM", ".LA", ".LAM", ".kar", ".lma", ".m2a",
			".mid", ".KAR", ".LMA", ".M2U", ".MID", ".midi", ".m3u", ".mjf",
			".rm", ".MIDI", ".M3U", ".MJF", ".RM", ".rmm", ".rmp", ".rpm",
			".s3m", ".RMM", ".RMP", ".RPM", ".S3M", ".sid", ".snd", ".tsi",
			".voc,", ".SID", ".SND", ".TSI", ".VOC", ".vox", ".vqe", ".vqf",
			".vql", ".VOX", ".VQE", ".VQF", ".VQL", ".xm", ".tsp", ".mod",
			".mpa", ".XM", ".TSP", ".MOD", ".MPA", ".my", ".mpg", ".mpga",
			".pfunk", ".MY", ".MPG", ".MPGA", "PFUNK", ".ra", ".qcp", ".ram",
			".RA", ".QCP", ".RAM", ".xlsx", ".XLSX", ".xls", ".XLS", ".xlam",
			".XLAM", ".xltm", ".XLTM", ".xlsm", ".XLSM", ".xlsb", ".XLSB",
			".xl", ".xla", ".xlb", ".xlc", ".XL", ".XLA", ".XLB", ".XLC",
			".xld", ".xlk", ".xll", ".xlm", ".XLD", ".XLK", ".XLL", ".XLM",
			".xlt", ".xlv", ".xlw", ".XLT", ".XLV", ".XLW", ".ppt", ".PPT",
			".pptx", ".PPTX", ".pptm", ".PPTM", ".pot", ".POT", ".potx",
			".POTX", ".pps", ".PPS", ".ppsx", ".PPSX", ".ppsm", ".PPSM",

			".vrml", ".VRML", ".pov", ".POV", ".iges", ".IGES", ".igs", ".IGS",
			".wrl", ".WRL", ".wrz", ".WRZ", ".dwf", ".DWF", ".3dm", ".3DM",
			".3dmf", ".3DMF", ".qd3", ".QD3", ".qd3d", ".QD3D", ".3svr",
			".3SVR", ".vrt", ".VRT", ".mime", ".MIME", ".mht", ".MHT",
			".mhtml", ".MHTML", ".ustar", ".USTAR", ".gzip", ".GZIP", ".xgz",
			".XGZ", ".xmz", ".XMZ", ".xyz", ".pdb", ".XYZ", ".PDB", ".zip",
			".ZIP", ".doc", ".DOC", ".pdf", ".PDF", ".ice", ".ICE", ".wmf",
			".WMF", ".ivr", ".IVR", ".pvu", ".PVU", ".IDC", ".FLX", ".FOR",

	};

	public static String get_img = null;

	public static String con_fldid = null, con_fldnm = null;
	public static boolean con_booup = false;
	public static ArrayList<String> con_list = new ArrayList<String>();
	public static ArrayList<String> con_name = new ArrayList<String>();
	public static String onoff = "On";

	public static boolean boo_add = false;

	public static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu305kaXevuqhlNLdfduqXDid1cA5hppp6rAlxyEyH/09AZqxfId8edW5r4inlk56DbeEcK2X5CWxpdstRiPTxIIO9MqRzVJUKkNHWAUy8MlzaE69GtCDYU8Hbt5bP3F4aqwfmhlCy1h4yPuVzgz7bzG2N0m25hdiJS6ZkGL8a7o88xjcxz/HURWayYwxclf/cGVYyv+5s9584FV5O7k+V8qYYfTKHSB+C1yy6ly2Q18dqvfEmZN9JzOsZnzoJP1uRmZAkhDRldRX7W49bYJHUkaLTRVcKS7BNT7js+QDDgKjBvvw382Teki45CVV1n5wMv0oATEyuYX2k6kY8F2kXwIDAQAB";
	public static final String SKU_GAS = "com.whootin.wf.nua.whootinfiles";
	public static final String SKU_GAS100 = "com.whootin.wf.nua.whootinfiles.100";
	public static final String SKU_GAS1000 = "com.whootin.wf.nua.whootinfiles.1000";
	public static boolean boo_last = false;

	public static Files_Db db;
	public static SQLiteDatabase db_sql;

	public static String chase = null;
	public static ArrayList<String> array_chase = null;
	public static ArrayList<String> sku_chase = null;

	public static Boolean isInternetPresent = false;
	public static ConnectionDetector cd;

	public static SharedPreferences pass = null;
	public static SharedPreferences.Editor passcom = null;

	public static SharedPreferences vall = null;
	public static SharedPreferences.Editor vallcom = null;
	public static String currentdate = null;

	public static SharedPreferences datee = null;
	public static SharedPreferences.Editor datecom = null;
	public static String accesstoken = null;

	public static void setToken(String token) {
		accesstoken = token;
	}

	public static String getToken() {
		return accesstoken;
	}

	public static List<String> whole_path = new ArrayList<String>();
	public static List<String> whole_dlt = new ArrayList<String>();
	public static List<String> whole_re_path = new ArrayList<String>();
	public static List<String> whole_fresh_path = new ArrayList<String>();
	public static List<String> whole_commoh_path = new ArrayList<String>();
	public static boolean whole_boo = true;
	public static ArrayList<HashMap<String, String>> all_contactList = new ArrayList<HashMap<String, String>>();

	public static String get = null;
	public static boolean bool = true;
	public static ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();

	public static String AccessToken(SharedPreferences share) {
		String access_token = null;
		if (share != null && share.contains("access_token")) {
			access_token = "Bearer " + share.getString("access_token", "");
			Log.e("access_token", access_token);
		}
		return access_token;
	}

	public static void hideSoftKeyboard(Activity activity, View v) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if (v != null) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

	public static ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
	public static ArrayList<HashMap<String, String>> fldList = new ArrayList<HashMap<String, String>>();

	public static ArrayList<SdAdapters> galleryList = new ArrayList<SdAdapters>();

	public static Bitmap getCircularBitmap(Bitmap bitmap, Context context2) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffff0000;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawOval(rectF, paint);

		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth((float) 4);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;

	}

}