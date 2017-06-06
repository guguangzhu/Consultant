package com.toda.consultant.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.toda.consultant.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilTools {

	/**
	 * 对网络连接状态进行判断
	 * 
	 * @return true, 可用； false， 不可用
	 */
	public static boolean isOpenNetwork(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}

		return false;

	}

	public static  String getFromAssets(Context con, String fileName){
		try {
			InputStreamReader inputReader = new InputStreamReader(con
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取文件的路径
	 * IConstant.SPI_FILE = "SpiFile"
	 * @param path
	 *            最后的路径
	 * @return 绝对的文件路径
	 */
	public static File getFileDir(String path) {
		File dir;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			dir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "SpiFile" + path);
		} else {
			dir = new File(Environment.getDataDirectory().getAbsolutePath()
					+ "SpiFile" + path);
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	/**
	 * 
	 * 方法名称: getSex ；
	 * 方法描述: 获取性别 ；
	 * 返回类型: String ；
	 * 创建人：fujijin  ；
	 * 创建时间：2015年1月30日 下午2:45:10；
	 * @throws
	 */
	public static String getSex(String flag){
		String sex = null;
		switch (Integer.parseInt(flag)) {
		case 1:
			sex = "男";
			break;
		case 2:
			sex = "女";
			break;
		}
		
		return sex;
	}
	 
	/**
	 * 
	 * 方法名称: isEmail ； 方法描述: 验证邮箱 ； 返回类型: boolean ； 创建人：fujijin ；
	 * 创建时间：2015年2月10日 下午6:57:05；
	 * 
	 * @throws
	 */
	public static boolean isEmail(String strEmail) {
//		String strPattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//		Pattern p = Pattern.compile(strPattern);
//		Matcher m = p.matcher(strEmail);
//		if (m.matches()) {
//			return true;
//		} else {
//			return false;
//		}
		String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();

	}
	/**
	 * 
	 * 方法名称: isCellphone ；
	 * 方法描述:  验证电话号码；
	 * 返回类型: boolean ；
	 * 创建人：fujijin  ；
	 * 创建时间：2015年2月10日 下午6:58:01；
	 * @throws
	 */
	public static boolean isCellphone(String str) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * 方法名称: isPassWord ；
	 * 方法描述: 是否符合密码规则   ；
	 * 返回类型: boolean ；
	 * 创建人：fujijin  ；
	 * 创建时间：2015年3月2日 下午5:53:20；
	 * @throws
	 */
	public static boolean isPassWord(String password){
		
		String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";		
		
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(password);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	/***
	 * 方法名称: isUserName ；
	 * 方法描述: 是否符合用户名规则，中文、数字、下划线、字母   ；
	 * 返回类型: boolean ；
	 * @param user
	 * @return
	 */
	public static boolean isUserName(String user){
		String reg = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]+$";
		Pattern pattern=Pattern.compile(reg);
		Matcher matcher = pattern.matcher(user);
		if(user.getBytes().length<3||user.getBytes().length>20){
			return false;
		}
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/***
	 * 方法名称: isIDNum ；
	 * 方法描述: 是否符合身份证号码规则  ；
	 * 返回类型: boolean ；
	 * @param str
	 * @return
	 */
	public static boolean isIDNum(String str){
				Pattern p = Pattern
						.compile("^((\\d{14})|(\\d{17}))(\\d|X|x)$");
		Matcher m = p.matcher(str);
				return m.matches();
			}
	
	/**
	 * 上次点击时间
	 */
	private static long lastClickTime;
	/**
	 * 上次点击view id
	 */
	private static long lastClickId;

	public static boolean isFastDoubleClick(int id) {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (lastClickId==id&&(0 < timeD && timeD < 1000)) {
			return true;
		}
		lastClickId=id;
		lastClickTime = time;
		return false;
	}

	public static void toast(Context context,int img, String str) {
		Toast toast = Toast.makeText(context.getApplicationContext(),
				str, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setBackgroundResource(R.drawable.bg_trans_conner);
		TextView tv=(TextView)toastView.getChildAt(0);
		tv.setBackgroundDrawable(null);
//        toastView.setMinimumWidth(DeviceUtils.dip2px(context, 120));
		toastView.setPadding(DeviceUtils.dip2px(context, 16), DeviceUtils.dip2px(context, 8), DeviceUtils.dip2px(context, 16), DeviceUtils.dip2px(context,8));
		ImageView imageCodeProject = new ImageView(context.getApplicationContext());
		imageCodeProject.setImageResource(img);
		toastView.addView(imageCodeProject, 0);
		toast.show();
	}

	public static void toast(Context context, String str) {
		toast(context, str, Toast.LENGTH_SHORT);
	}

	public static void toast(Context context, String str,int duration) {
		Toast toast = Toast.makeText(context.getApplicationContext(),
				str, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		LinearLayout toastView = (LinearLayout) toast.getView();
		toastView.setPadding(DeviceUtils.dip2px(context, 16), DeviceUtils.dip2px(context, 8), DeviceUtils.dip2px(context, 16), DeviceUtils.dip2px(context,8));
		toastView.setBackgroundResource(R.drawable.bg_trans_conner);
		TextView tv=(TextView)toastView.getChildAt(0);
		tv.setBackgroundDrawable(null);
		toast.show();
	}
	
}
