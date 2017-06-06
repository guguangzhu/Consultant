package com.toda.consultant.util;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 存放Activity的容器，退出系统
 */
public class ActivityManagerTool {

	/** 当前实例 **/
	private static ActivityManagerTool instance;

	/** LinkedList 装Activity **/
	private List<Activity> activityList = new LinkedList<Activity>();

	/**
	 * 单例，获取当前实例
	 * 
	 * @return ActivityManagerTool实例
	 * @since 2013-7-24 gaobingbing
	 */
	public static ActivityManagerTool getInstance() {
		if (instance == null) {
			instance = new ActivityManagerTool();
		}
		return instance;
	}

	public List<Activity> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {
		this.activityList = activityList;
	}

	/**
	 * 添加Activity到容器中
	 * 
	 * @param activity
	 *            Activity
	 * @since 2013-7-24 gaobingbing
	 */
	public void addActivity(Activity activity) {
		if (!activityList.contains(activity)) {
			activityList.add(activity);
		}
	}

	/**
	 * 将activity从容器里面移除
	 * 
	 * @param activity
	 *            Activity
	 */
	public void removeActivity(Activity activity) {
		if (activityList.contains(activity)) {
			activityList.remove(activity);
		}
	}

	/**
	 * 遍历所有Activity并finish
	 * 
	 * @since 2013-7-24 gaobingbing
	 */
	public void exitSystem() {
		for (Activity activity : activityList) {
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
		System.exit(0);
	}

	public void removeAllActivity(){
		for (Activity activity : activityList) {
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	/**
	 * 获取容器大小
	 * 
	 * @return 容器大小
	 * @since 2013-7-24 gaobingbing
	 */
	public int getActivityCount() {
		return activityList.size();
	}

	/**
	 * 返回到主页面
	 * 
	 * @since 2013-7-24 gaobingbing
	 */
	public void backMain() {
	}

	/**
	 * 返回到主页面
	 * 
	 * @since 2013-7-24 gaobingbing
	 */
	public void regainBackMainActivity() {
	}

	/**
	 * 获取上一个Activity
	 * 
	 * @return Activity
	 * @since 2013-7-24 gaobingbing
	 */
	public Activity getLastActivity() {
		for (int i = activityList.size() - 1; i >= 0; i--) {
			if (!activityList.get(i).isFinishing()) {
				return activityList.get(i);
			}
		}
		return null;
	}

	/**
	 * 获取上一个Activity
	 * 
	 * @return Activity
	 * @since 2013-7-24 gaobingbing
	 */
	public boolean deleteActivity(Class class1) {
		boolean result = false;
		Iterator<Activity> ither = activityList.iterator();
		while (ither.hasNext()) {
			Activity activity = ither.next();
			if (activity.getClass().getName().equals(class1.getName())) {
				activity.finish();
				return true;
			}
		}
		return result;
	}
}
