package com.toda.consultant.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {
	private static String logPath = "";
	private static final int logExpiredDay = 1;

	/***
	 * 将log写入文件中
	 * @param log
	 */
	public static void writeLog(final String log) {
		if(!LogUtils.sIsLogEnabled){
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				writeLogBySync(log);
			}
		}).start();
	}
	
	/***
	 * 将log写入文件中（同步）
	 * @param log
	 */
	public synchronized static void writeLogBySync(String log){
		try {
			File file = new File(getLogPath());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				//记录创建时间
//				ShareData.setShareLongData(file.getPath(), System.currentTimeMillis());
			}
			FileWriter fw = new FileWriter(file, true);
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			fw.write((format.format(new Date(System.currentTimeMillis()))
					+ "—" + log + "\n\n"));
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件的路径
	 * "SpiFile" == IConstant.SPI_FILE
	 * @param path
	 *            最后的路径
	 * @return 绝对的文件路径
	 */
	public static String getFilePath(String path) {
		String dir = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			dir = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ IConfig.WATCH_HOUSE_FILE + path;
		} else {
			dir = Environment.getDataDirectory().getAbsolutePath()
					+ IConfig.WATCH_HOUSE_FILE+ path;
		}
		return dir;
	}

	public static String getLogPath() {
		if (TextUtils.isEmpty(logPath)) {
			logPath = getFilePath("/log");
		}
		return logPath;
	}

	/**
	 * 清除文件或缓存
	 * 
	 * @param path
	 *            要清除的目录或文件的路径
	 * @return 清除的文件个数
	 */
	public static int clearFile(String path) {
		int deletedFiles = 0;
		if (path == null) {
			return deletedFiles;
		}
		File file = new File(path);
		if (!file.exists()) {
			return deletedFiles;
		}
		deletedFiles = clearFile(file);
		return deletedFiles;
	}

	/**
	 * 清除文件或缓存
	 * 
	 * @param file
	 *            要清除的目录或文件
	 * @return 清除的文件个数
	 */
	public static int clearFile(File file) {
		int deletedFiles = 0;
		try {
			if (file == null || !file.exists()) {
				return deletedFiles;
			}

			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearFile(child);
					}

					if (child.delete()) {
						deletedFiles++;
					}
				}
			} else {
				if (file.delete()) {
					deletedFiles++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deletedFiles;
	}

	/***
	 * 清除log缓存
	 * @return
	 */
	public static boolean removeLogExpiredCashe() {
		return removeExpiredCache(getLogPath(), logExpiredDay);
	}

	/***
	 * 清除文件缓存
	 * @param path
	 * @param expiredDay
	 * @return
	 */
	public static boolean removeExpiredCache(String path, int expiredDay) {
		try {
			File file = new File(path);
			long currentTime = System.currentTimeMillis();
			if (file.isDirectory()) {
				File subFile[] = file.listFiles();
				for (int i = 0; i < subFile.length; i++) {
//					long day = currentTime - ShareData.getShareLongData(subFile[i].getPath());
//					if (day > expiredDay * 1000 * 60 * 60 * 24) { // 如果大于设置日期或者负值
//						subFile[i].delete();
//						ShareData.deleShareData(subFile[i].getPath());
//					}
				}
			} else {
				if (file.exists()) {
//					long day = currentTime - ShareData.getShareLongData(file.getPath());
//					if (day > expiredDay * 1000 * 60 * 60 * 24) { // 如果大于设置日期或者负值
//						if (day > expiredDay) { // 如果大于设置日期或者负值
//							file.delete();
//							ShareData.deleShareData(file.getPath());
//						}
//					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/***
	 * 通过系统或第三方软件打开文件
	 * 
	 * @param context
	 * @param file
	 * @return
	 */
	public static Intent openFile(Context context, File file) {
		if (!file.exists()) {
			Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
			return null;
		}
		try {
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 设置intent的Action属性
			intent.setAction(Intent.ACTION_VIEW);
			// 获取文件file的MIME类型
			String type = FileUtils.getMIMEType(file);
			// 设置intent的data和Type属性。
			intent.setDataAndType(/* uri */Uri.fromFile(file), type);
			// 跳转
			// context.startActivity(intent);
			return intent;
			// Intent.createChooser(intent, "请选择对应的软件打开该附件！");
		} catch (ActivityNotFoundException e) {
			// TODO: handle exception
			Toast.makeText(context, "文件不能打开，请下载相关软件！", Toast.LENGTH_SHORT)
					.show();
		}
		return null;
	}

	/***
	 * 根据文件获取到MIME类型
	 */
	public static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {

			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	/***
	 * 根据后缀获取MIME类型
	 */
	private static String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{ ".3gp", "video/3gpp" },
			{ ".apk", "application/vnd.android.package-archive" },
			{ ".asf", "video/x-ms-asf" },
			{ ".avi", "video/x-msvideo" },
			{ ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" },
			{ ".c", "text/plain" },
			{ ".class", "application/octet-stream" },
			{ ".conf", "text/plain" },
			{ ".cpp", "text/plain" },
			{ ".doc", "application/msword" },
			{ ".docx",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
			{ ".xls", "application/vnd.ms-excel" },
			{ ".xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
			{ ".exe", "application/octet-stream" },
			{ ".gif", "image/gif" },
			{ ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" },
			{ ".h", "text/plain" },
			{ ".htm", "text/html" },
			{ ".html", "text/html" },
			{ ".jar", "application/java-archive" },
			{ ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" },
			{ ".jpg", "image/jpeg" },
			{ ".js", "application/x-javascript" },
			{ ".log", "text/plain" },
			{ ".m3u", "audio/x-mpegurl" },
			{ ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" },
			{ ".m4p", "audio/mp4a-latm" },
			{ ".m4u", "video/vnd.mpegurl" },
			{ ".m4v", "video/x-m4v" },
			{ ".mov", "video/quicktime" },
			{ ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" },
			{ ".mp4", "video/mp4" },
			{ ".mpc", "application/vnd.mpohun.certificate" },
			{ ".mpe", "video/mpeg" },
			{ ".mpeg", "video/mpeg" },
			{ ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" },
			{ ".mpga", "audio/mpeg" },
			{ ".msg", "application/vnd.ms-outlook" },
			{ ".ogg", "audio/ogg" },
			{ ".pdf", "application/pdf" },
			{ ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" },
			{ ".ppt", "application/vnd.ms-powerpoint" },
			{ ".pptx",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" },
			{ ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
			{ ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
			{ ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
			{ ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
			{ ".z", "application/x-compress" },
			{ ".zip", "application/x-zip-compressed" }, { "", "*/*" } };
}
