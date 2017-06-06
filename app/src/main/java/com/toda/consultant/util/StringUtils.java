package com.toda.consultant.util;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 判断字符串是否为空
	 * 
	 * <pre>
	 * isBlank(null) = true;
	 * isBlank(&quot;&quot;) = true;
	 * isBlank(&quot;  &quot;) = true;
	 * isBlank(&quot;a&quot;) = false;
	 * isBlank(&quot;a &quot;) = false;
	 * isBlank(&quot; a&quot;) = false;
	 * isBlank(&quot;a b&quot;) = false;
	 * </pre>
	 * 
	 * @param str
	 * @return if string is null or its size is 0 or it is made by space, return
	 *         true, else return false.
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * is null or its length is 0
	 * 
	 * <pre>
	 * isEmpty(null) = true;
	 * isEmpty(&quot;&quot;) = true;
	 * isEmpty(&quot;  &quot;) = false;
	 * </pre>
	 * 
	 * @param str
	 * @return if string is null or its size is 0, return true, else return
	 *         false.
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.length() == 0);
	}


	/**
	 * null string to empty string
	 * 
	 * <pre>
	 * nullStrToEmpty(null) = &quot;&quot;;
	 * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
	 * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String nullStrToEmpty(String str) {
		return (str == null ? "" : str);
	}

	/**
	 * capitalize first letter
	 * 
	 * <pre>
	 * capitalizeFirstLetter(null)     =   null;
	 * capitalizeFirstLetter("")       =   "";
	 * capitalizeFirstLetter("2ab")    =   "2ab"
	 * capitalizeFirstLetter("a")      =   "A"
	 * capitalizeFirstLetter("ab")     =   "Ab"
	 * capitalizeFirstLetter("Abc")    =   "Abc"
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalizeFirstLetter(String str) {
		if (isEmpty(str)) {
			return str;
		}

		char c = str.charAt(0);
		return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
				: new StringBuilder(str.length())
						.append(Character.toUpperCase(c))
						.append(str.substring(1)).toString();
	}

	/**
	 * encoded in utf-8
	 * 
	 * <pre>
	 * utf8Encode(null)        =   null
	 * utf8Encode("")          =   "";
	 * utf8Encode("aa")        =   "aa";
	 * utf8Encode("??????")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
	 * </pre>
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 *             if an error occurs
	 */
	public static String utf8Encode(String str) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(
						"UnsupportedEncodingException occurred. ", e);
			}
		}
		return str;
	}

	/**
	 * encoded in utf-8, if exception, return defultReturn
	 * 
	 * @param str
	 * @param defultReturn
	 * @return
	 */
	public static String utf8Encode(String str, String defultReturn) {
		if (!isEmpty(str) && str.getBytes().length != str.length()) {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return defultReturn;
			}
		}
		return str;
	}

	/**
	 * get innerHtml from href
	 * 
	 * <pre>
	 * getHrefInnerHtml(null)                                  = ""
	 * getHrefInnerHtml("")                                    = ""
	 * getHrefInnerHtml("mp3")                                 = "mp3";
	 * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
	 * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
	 * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
	 * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
	 * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
	 * </pre>
	 * 
	 * @param href
	 * @return <ul>
	 *         <li>if href is null, return ""</li>
	 *         <li>if not match regx, return source</li>
	 *         <li>return the last string that match regx</li>
	 *         </ul>
	 */
	public static String getHrefInnerHtml(String href) {
		if (isEmpty(href)) {
			return "";
		}

		String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
		Pattern hrefPattern = Pattern
				.compile(hrefReg, Pattern.CASE_INSENSITIVE);
		Matcher hrefMatcher = hrefPattern.matcher(href);
		if (hrefMatcher.matches()) {
			return hrefMatcher.group(1);
		}
		return href;
	}

/**
     * process special char in html
     * 
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     * 
     * @param source
     * @return
     */
	public static String htmlEscapeCharsToString(String source) {
		return StringUtils.isEmpty(source) ? source : source
				.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
	}

	/**
	 * transform half width char to full width char
	 * 
	 * <pre>
	 * fullWidthToHalfWidth(null) = null;
	 * fullWidthToHalfWidth("") = "";
	 * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
	 * fullWidthToHalfWidth("锛??锛??锛??) = "!\"#$%&";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String fullWidthToHalfWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == 12288) {
				source[i] = ' ';
				// } else if (source[i] == 12290) {
				// source[i] = '.';
			} else if (source[i] >= 65281 && source[i] <= 65374) {
				source[i] = (char) (source[i] - 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * transform full width char to half width char
	 * 
	 * <pre>
	 * halfWidthToFullWidth(null) = null;
	 * halfWidthToFullWidth("") = "";
	 * halfWidthToFullWidth(" ") = new String(new char[] {12288});
	 * halfWidthToFullWidth("!\"#$%&) = "锛??锛??锛??";
	 * </pre>
	 * 
	 * @param s
	 * @return
	 */
	public static String halfWidthToFullWidth(String s) {
		if (isEmpty(s)) {
			return s;
		}

		char[] source = s.toCharArray();
		for (int i = 0; i < source.length; i++) {
			if (source[i] == ' ') {
				source[i] = (char) 12288;
				// } else if (source[i] == '.') {
				// source[i] = (char)12290;
			} else if (source[i] >= 33 && source[i] <= 126) {
				source[i] = (char) (source[i] + 65248);
			} else {
				source[i] = source[i];
			}
		}
		return new String(source);
	}

	/**
	 * 截取uri地址最后一个/后边的字符串
	 * 
	 * @param uri
	 * @return 如 "http://www.baidu.com/popgame/test.jpg"; 返回test.jpg
	 */
	public static String subString(String uri) {
		return uri.substring(uri.lastIndexOf("/"));
	}

	/**
	 * 截取uri最后一个/后到最后一个.之间的字符串
	 * 
	 * @param uri
	 * @return 如 "http://www.baidu.com/popgame/test.jpg"; 返回test
	 */
	public static String subStringName(String uri) {
		return uri.substring(uri.lastIndexOf("/"), uri.lastIndexOf("."));
	}

	/**
	 * 清空字符串中的空字符
	 */
	public static String clearSpace(String str) {
		str = str.replace(" ", "");
		return str;
	}

	/**
	 * 清楚空格的情况下判断是否为empty
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isAbsEmpty(String string) {
		if (string != null && string.length() > 0) {
			String tempStr = clearSpace(string);
			if (tempStr != null && tempStr.length() > 0) {
				return false;
			}
		}
		return true;
	}

	/***
	 * 判断字符串是否为纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/***
	 * 获取格式化后的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getFormatedPnum(String str) {
		double num = 0;
		if (!TextUtils.isEmpty(str)) {
			try {
				num = Double.parseDouble(str);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return getFormatedPnum(num);
	}

	/***
	 * 获取格式化后的字符串
	 * 
	 * @param num
	 * @return
	 */
	public static String getFormatedPnum(double num) {
		BigDecimal bd = new BigDecimal(num + "");
		num = bd.setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
		DecimalFormat df = new DecimalFormat("#0.00");
		String result = df.format(num);
		return result;
	}

	/***
	 * 将金额字符串转换为double
	 * 
	 * @param price
	 * @return
	 */
	public static double getPrice(String price) {
		return getPrice(price, BigDecimal.ROUND_FLOOR);
	}

	/***
	 * 将金额字符串转换为double
	 * 
	 * @param price
	 * @param mode
	 *            取舍规则
	 * @return
	 */
	public static double getPrice(String price, int mode) {
		double account = 0;
		if (!TextUtils.isEmpty(price)) {
			try {
				BigDecimal bd = new BigDecimal(price);
				account = bd.setScale(2, mode).doubleValue();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

		return account;
	}

	/**
	 * 将价格字符串添加逗号分隔符
	 * 
	 * @param str
	 * @return
	 */
	public static String getPriceText(String str) {
		return getPriceText("###,##0.00", str);
	}

	/***
	 * 将价格字符串添加特定的格式
	 *
	 * @param format
	 * @param str
	 * @return
	 */
	public static String getPriceText(String format, String str) {
		return getPriceText(format, str, BigDecimal.ROUND_FLOOR);
	}

	/**
	 * 以特定的舍入方式将价格字符串添加特定的格式
	 * @param format
	 * @param str
	 * @param mode
	 * @return
	 */
	public static String getPriceText(String format, String str,int mode) {
		if ("".equals(str) || "null".equals(str) || str == null)
			return "";
		try {
			BigDecimal bd = new BigDecimal(str);
			double f1 = bd.setScale(2, mode).doubleValue();
			DecimalFormat df = new DecimalFormat(format);
			String price = df.format(f1);
			return price;
		} catch (Exception e) {
			return "";
		}
	}

	/***
	 * 获取清除小数点后末位为0的数字
	 * @param price
	 * @return
	 */
    public static String getPriceWipeZero(String price){
		return getPriceText("###,###.##",price);
	}

	/***
	 * string转double
	 * 
	 * @param str
	 * @return
	 */
	public static double string2Double(String str) {
		if (TextUtils.isEmpty(str)) {
			return 0;
		}
		double db;
		try {
			db = Double.parseDouble(str);
		} catch (Exception e) {
			db = 0;
		}
		return db;
	}
	/***
	 * String 转 int
	 * @param str
	 * @return
	 */
	public static int string2Integer(String str){
		int num=0;
		if(!TextUtils.isEmpty(str)){
			try{
				num=(int)string2Double(str);
			}catch(Exception e){
				num=0;
			}
		}
		return num;
		
	}

	/***
	 * string转boolean
	 * @param str
	 * @return
	 */
	public static boolean string2Boolean(String str){
		return "1".equals(str);
	}

	/***
	 * int转boolean
	 * @param num
	 * @return
	 */
	public static boolean int2Boolean(int num){
		return 1==num;
	}

	/***
	 * 手机号或银行卡号中间部分替换
	 * 
	 * @param str
	 * @param startNum
	 *            保留的初始位数
	 * @param endNum
	 *            保留的末尾位数
	 * @param replaceStr
	 *            替换后的字符
	 * @return
	 */
	public static String getReplaceString(String str, int startNum, int endNum,
			String replaceStr) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		if (startNum + endNum >= str.length()) {
			return str;
		}
		String start = str.substring(0, startNum);
		String end = str.substring(str.length() - endNum);
		return start + replaceStr + end;
	}

	/***
	 * 将url与请求数据拼接为字符串
	 * 
	 * @param url
	 * @param map
	 * @return
	 */
	public static String getQueryUrl(String url, Map<String, String> map) {
		final StringBuilder result = new StringBuilder();
		if(map==null){
			return url;
		}
		for (Entry<String, String> parameter : map.entrySet()) {
			try {
				final String encodedName = URLEncoder.encode(parameter.getKey(), "utf-8");
				final String encodedValue = URLEncoder.encode(parameter.getValue(), "utf-8");
				if (result.length() > 0) {
					result.append("&");
				}
				result.append(encodedName);
				if (encodedValue != null) {
					result.append("=");
					result.append(encodedValue);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		if(TextUtils.isEmpty(result.toString())){
			return url;
		}
		return url + "?" + result.toString();
	}


	/**
	 * 根据json字符串返回Map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String json) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObj = parser.parse(json).getAsJsonObject();
		return jsonToMap(jsonObj);
	}

	/**
	 * 将JSONObjec对象转换成Map-List集合
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(JsonObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<Entry<String, JsonElement>> entrySet = json.entrySet();
		for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter
				.hasNext();) {
			Entry<String, JsonElement> entry = iter.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof JsonArray)
				map.put((String) key, toList((JsonArray) value));
			else if (value instanceof JsonObject)
				map.put((String) key, jsonToMap((JsonObject) value));
			else
				map.put((String) key, value);
		}
		return map;
	}

	/**
	 * 将JSONArray对象转换成List集合
	 * 
	 * @param json
	 * @return
	 */
	public static List<Object> toList(JsonArray json) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < json.size(); i++) {
			Object value = json.get(i);
			if (value instanceof JsonArray) {
				list.add(toList((JsonArray) value));
			} else if (value instanceof JsonObject) {
				list.add(jsonToMap((JsonObject) value));
			} else {
				list.add(value);
			}
		}
		return list;
	}

	public static String getKOne(){
		return "";
	}

}
