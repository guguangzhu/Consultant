package com.toda.consultant.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.toda.consultant.model.ErrorCode;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.ErrorTips;
import com.toda.consultant.view.CommonListView;
import com.toda.consultant.view.ErrLayout;
import com.toda.consultant.view.NormalEmptyView;


/***
 * 网络请求返回信息处理
 * 
 * @author zhaohaibin
 * 
 */
public class HandlerRequestErr {
	public static int HAS_LOAD=1001;
	/***
	 * 判断网络请求是否成功及错误处理，错误信息会提示
	 * 
	 * @param context
	 * @param data
	 * @return code为200或0000返回true，否则返回false
	 */
	public static boolean handlerRequestErr(Context context, ResultData data) {
		if (TextUtils.isEmpty(data.getMsg())) {
			data.setMsg(ErrorTips.ERR_NO_MSG);
		}
		return handlerRequestErr(context, data, true);
	}

	/***
	 * 判断网络请求是否成功及错误处理，错误信息会提示
	 * @param context
	 * @param data
	 * @param defaultStr 如果提示信息为空，默认赋予的提示信息
	 * @return
	 */
	public static boolean handlerRequestErr(Context context,
											ResultData data, String defaultStr) {
		if (TextUtils.isEmpty(data.getMsg())) {
			if (!TextUtils.isEmpty(defaultStr)) {
				data.setMsg(defaultStr);
			} else {
				data.setMsg(ErrorTips.ERR_NO_MSG);
			}
		}
		return handlerRequestErr(context, data, true);
	}

	/***
	 * 获取常规网络错误提示
	 * 
	 * @param code
	 * @return
	 */
	public static String getLocalErrMsg(String code) {
		String msg = "";
		if (ErrorCode.CONNECT_TIME_OUT.equals(code)) {
			msg = ErrorTips.CONNECT_TIME_OUT;
		} else if (ErrorCode.SOCKET_TIME_OUT.equals(code)) {
			msg = ErrorTips.SOCKET_TIME_OUT;
		} else if (ErrorCode.UNKNOW_HOST_ERR.equals(code)) {
			msg = ErrorTips.UNKNOW_HOST_ERR;
		} else if (ErrorCode.CONNECT_ERR.equals(code)) {
			msg = ErrorTips.CONNECT_ERR;
		} else if (ErrorCode.JSON_ERR.equals(code)) {
			msg = ErrorTips.DATA_ERR;
		} else if (ErrorCode.SYSTEM_ERR.equals(code)) {
			msg = ErrorTips.ERR_NO_MSG;
		}
		return msg;
	}

	/***
	 * 获取常规网络错误提示
	 *
	 * @param code
	 * @return
	 */
	public static String getNetErrMsg(String code) {
		String msg = "";
		if (ErrorCode.CONNECT_TIME_OUT.equals(code)) {
			msg = ErrorTips.CONNECT_TIME_OUT;
		} else if (ErrorCode.SOCKET_TIME_OUT.equals(code)) {
			msg = ErrorTips.SOCKET_TIME_OUT;
		} else if (ErrorCode.UNKNOW_HOST_ERR.equals(code)) {
			msg = ErrorTips.UNKNOW_HOST_ERR;
		} else if (ErrorCode.CONNECT_ERR.equals(code)) {
			msg = ErrorTips.CONNECT_ERR;
		}
		return msg;
	}

	/***
	 * 判断网络请求是否成功及错误处理，刷新列表使用，与SolarBaoListView绑定
	 * 
	 * @param context
	 * @param listView
	 * @param data
	 * @return
	 */
	public static boolean handlerListViewErr(Context context,
											 CommonListView listView, ResultData data) {
		String code = data.getCode();
		String msg = data.getMsg();

		if (ErrorCode.SUCCESS.equals(code)
				|| ErrorCode.SUCCESS_CODE.equals(code)) {
			return true;
		}

		String netMsg = getLocalErrMsg(code);
		if (!TextUtils.isEmpty(netMsg)) {
			msg = netMsg;
		}
		if (TextUtils.isEmpty(msg)) {
			msg = ErrorTips.ERR_NO_MSG;
		}

		if (listView == null) {
			UtilTools.toast(context, msg, Toast.LENGTH_SHORT);
			return false;
		}

		if (ErrorCode.NO_DATA.equals(code)) {

			if (listView.isRefresh()) {// 201，如果为刷新，则显示emptyview，否则只提示
				listView.setEmptyType(NormalEmptyView.EMPTY_TYPE_NOCONTENT);
			} else {
				UtilTools.toast(context, ErrorTips.NO_MORE_DATA, Toast.LENGTH_SHORT);
			}

		} else if (ErrorCode.CONNECT_TIME_OUT.equals(code)
				|| ErrorCode.CONNECT_ERR.equals(code)) {

			// 网络连接失败，若当前listview无数据显示emptyview，否则只做提示
			if (listView.getList() == null || listView.getList().size() == 0) {
				listView.setEmptyType(NormalEmptyView.EMPTY_TYPE_NONET);
			} else {
				UtilTools.toast(context, msg, Toast.LENGTH_SHORT);
			}

		} else {

			// 其他错误，若当前listview无数据显示emptyview，都做提示
			if (listView.getList() == null || listView.getList().size() == 0) {
				listView.setEmptyType(NormalEmptyView.EMPTY_TYPE_ERROR);
			}
			UtilTools.toast(context, msg, Toast.LENGTH_SHORT);

		}
		return false;
	}


	/***
	 * 判断网络请求是否成功及错误处理，初始页面为空白时使用，需要传入操作的布局
	 *
	 * @param context
	 * @param layout
	 * @param data
	 * @return
	 */
	public static boolean handlerEmptyViewErr(Context context,
											  ErrLayout layout, ResultData data) {
		if(layout==null){
			return false;
		}
		String code = data.getCode();
		String msg = data.getMsg();

		if (ErrorCode.SUCCESS.equals(code)
				|| ErrorCode.SUCCESS_CODE.equals(code)) {
			layout.setHasLoad(true);
			layout.hideErrView();
			return true;
		}

		boolean hasLoad=layout.isHasLoad();

		String netMsg = getLocalErrMsg(code);
		if (!TextUtils.isEmpty(netMsg)) {
			msg = netMsg;
		}
		if (TextUtils.isEmpty(msg)) {
			msg = ErrorTips.ERR_NO_MSG;
		}
		data.setMsg(msg);

		if (hasLoad) {
			layout.hideErrView();
			UtilTools.toast(context, msg, Toast.LENGTH_SHORT);
			return false;
		}

		if (ErrorCode.CONNECT_TIME_OUT.equals(code)
				|| ErrorCode.CONNECT_ERR.equals(code)) {

			// 网络连接失败，若当前listview无数据显示emptyview，否则只做提示
			layout.showErrView(NormalEmptyView.EMPTY_TYPE_NONET);

		} else {

			// 其他错误，若当前listview无数据显示emptyview，都做提示
			layout.showErrView(NormalEmptyView.EMPTY_TYPE_ERROR);
			UtilTools.toast(context, msg, Toast.LENGTH_SHORT);

		}
		return false;
	}

	/***
	 * 判断网络请求是否成功及错误处理
	 * 
	 * @param context
	 * @param data
	 * @param isTips
	 *            是否提示错误信息
	 * @return code为200或0000返回true，否则返回false
	 */
	public static boolean handlerRequestErr(Context context,
											ResultData data, boolean isTips) {
		String code = data.getCode();
		String msg = data.getMsg();

		if (ErrorCode.SUCCESS.equals(code)
				|| ErrorCode.SUCCESS_CODE.equals(code)) {
			return true;
		}

		String netMsg = getLocalErrMsg(code);
		if (!TextUtils.isEmpty(netMsg)) {
			msg = netMsg;
		}
		if(TextUtils.isEmpty(msg)){
			msg=ErrorTips.ERR_NO_MSG;
		}
		data.setMsg(msg);

		if (context == null || !isTips || TextUtils.isEmpty(msg)) {
			return false;
		}

		UtilTools.toast(context, msg, Toast.LENGTH_SHORT);

		return false;
	}
	
	/***
	 * 根据code获取错误信息，为空返回ErrorTips.ERR_NO_MSG
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String getErrMsg(String code, String msg){
		String netMsg= getLocalErrMsg(code);
		if(!TextUtils.isEmpty(netMsg)){
			msg=netMsg;
		}
		if(TextUtils.isEmpty(msg)){
			msg=ErrorTips.ERR_NO_MSG;
		}
		return msg;
	}
}
