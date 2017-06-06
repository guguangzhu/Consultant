package com.toda.consultant.model;
/***
 * 错误码
 * @author zhaohaibin
 *
 */
public class ErrorCode {
	/**列表无数据 **/
	public static final String SUCCESS="0";
	/**绑卡、支付成功 **/
	public static final String SUCCESS_CODE="0000";
	/**列表无数据 **/
	public static final String NO_DATA="201";
	/**异端登录 **/
	public static final String HERESY_LOGIN_ERR="404";
	/**未绑卡 **/
	public static final String CARD_UNBIND="317";
	
	
	/**解析错误 **/
	public static final String JSON_ERR="-101";
	/**网络连接超时 **/
	public static final String CONNECT_TIME_OUT="-102";
	/**服务器响应超时 **/
	public static final String SOCKET_TIME_OUT="-103";
	/**网络连接失败 **/
	public static final String CONNECT_ERR="-104";
	/**主机解析失败 **/
	public static final String UNKNOW_HOST_ERR="-105";
	/**系统繁忙、未知错误 **/
	public static final String SYSTEM_ERR="-106";
	
	
	/**SESSION清除 **/
	public static final String SESSION_CLEAR ="a30-100004";
	/**SESSION错误 **/
	public static final String SESSION_ERR ="-9";
	/**强制下线 **/
	public static final String ERROR_FORCE_LOG_OUT="405";

	/**登录验证码错误 **/
	public static final String LOGIN_CODE_ERR="328";
	/**绑卡充值页面充值失败 **/
	public static final String RECHARGE_ERR="a30-140422";
	/**支付密码错误 **/
	public static final String PAY_PWD_ERR="b30-200000";
	/**手慢了 **/
	public static final String PAY_ERR="a30-140424";
	/**订单支付未实名 **/
	public static final String PAY_UNAUTHEN_ERR="b30-200003";
	
}
