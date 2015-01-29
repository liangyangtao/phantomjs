package com.ylx.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	/**
	 * 时间转换为字符串
	 * 
	 * */
	public static String dateToString(Date time) {
		if (time == null) {
			time = new Date();
		}
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = formatter.format(time);
		return ctime;
	}

	/**
	 * 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 * 
	 * @param time
	 *            String 字符串<br>
	 * @return Date 日期<br>
	 */
	public static Date stringToDate(String time) {
		Date ctime = null;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 得到年
		// int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		// int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		// int hour = cal.get(Calendar.HOUR);// 得到小时
		// int minute = cal.get(Calendar.MINUTE);// 得到分钟
		// int second = cal.get(Calendar.SECOND);// 得到秒
		// System.out.println(year);
		// System.out.println(month);
		// System.out.println(day);
		if (time == null) {
			ctime = new Date();
		} else {
			SimpleDateFormat formatter = null;
			time = time.trim();
			String reg = "";
			String format = "";
			if (time.contains("-")) {
				reg = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
				if (isRegex(time, reg)) {
					format = "yyyy-MM-dd HH:mm:ss";
				} else {
					reg = "\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
					if (isRegex(time, reg)) {
						format = "yyyy-MM-dd HH:mm:ss";
						time = year + "-" + time;
					} else {
						reg = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}";
						if (isRegex(time, reg)) {
							format = "yyyy-MM-dd HH:mm";
						} else {
							reg = "\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}";
							if (isRegex(time, reg)) {
								format = "yyyy-MM-dd HH:mm";
								time = year + "-" + time;
							} else {
								reg = "\\d{4}-\\d{1,2}-\\d{1,2}";
								if (isRegex(time, reg)) {
									format = "yyyy-MM-dd";
								} else {
									reg = "\\d{1,2}-\\d{1,2}";
									if (isRegex(time, reg)) {
										format = "yyyy-MM-dd";
										time = year + "-" + time;
									} else {

									}
								}
							}
						}
					}

				}
			} else if (time.contains("/")) {
				reg = "\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
				if (isRegex(time, reg)) {
					format = "yyyy/MM/dd HH:mm:ss";
				} else {
					reg = "\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
					if (isRegex(time, reg)) {
						format = "yyyy/MM/dd HH:mm:ss";
						time = year + "/" + time;
					} else {
						reg = "\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}";
						if (isRegex(time, reg)) {
							format = "yyyy/MM/dd HH:mm";
						} else {
							reg = "\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}";
							if (isRegex(time, reg)) {
								format = "yyyy/MM/dd HH:mm";
								time = year + "/" + time;
							} else {
								reg = "\\d{4}/\\d{1,2}/\\d{1,2}";
								if (isRegex(time, reg)) {
									format = "yyyy/MM/dd";
								} else {
									reg = "\\d{1,2}/\\d{1,2}";
									if (isRegex(time, reg)) {
										format = "yyyy/MM/dd";
										time = year + "/" + time;
									} else {

									}
								}
							}
						}
					}

				}
			} else {
				reg = "\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}:\\d{1,2}:\\d{1,2}";
				if (isRegex(time, reg)) {
					format = "yyyy年MM月dd日 HH:mm:ss";
				} else {
					reg = "\\d{1,2}月\\d{1,2}日 \\d{1,2}:\\d{1,2}:\\d{1,2}";
					if (isRegex(time, reg)) {
						format = "yyyy年MM月dd日 HH:mm:ss";
						time = year + "年" + time;
					} else {
						reg = "\\d{4}年\\d{1,2}月\\d{1,2}日 \\d{1,2}:\\d{1,2}";
						if (isRegex(time, reg)) {
							format = "yyyy年MM月dd日 HH:mm";
						} else {
							reg = "\\d{1,2}月\\d{1,2}日 \\d{1,2}:\\d{1,2}";
							if (isRegex(time, reg)) {
								format = "yyyy年MM月dd日 HH:mm";
								time = year + "年" + time;
							} else {
								reg = "\\d{4}年\\d{1,2}月\\d{1,2}日";
								if (isRegex(time, reg)) {
									format = "yyyy年MM月dd日";
								} else {
									reg = "\\d{1,2}月\\d{1,2}日";
									if (isRegex(time, reg)) {
										format = "yyyy年MM月dd日";
										time = year + "年" + time;
									} else {

									}
								}
							}
						}
					}

				}

			}
			try {
				// System.out.println(format);
				formatter = new SimpleDateFormat(format);
				ctime = formatter.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
				ctime = new Date();
			}
		}
		return ctime;
	}

	/***
	 * 去掉字符串两边的空格
	 * 
	 * @param key
	 *            原始字符串
	 * 
	 * @return 返回结果
	 * @author tao
	 * 
	 * **/
	public static String TrimString(String key) {
		return key.trim();
	}

	/**
	 * URL Encoder
	 * 
	 * 
	 * 
	 * 
	 * */
	public static String URLEncoder(String key, String code) {

		String value = "";
		try {
			value = URLEncoder.encode(key, code);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return value;

	}

	/**
	 * 
	 * URL Decoder
	 * 
	 * 
	 * */
	public static String URLDecoder(String key, String code) {
		String value = "";
		try {
			value = URLDecoder.decode(key, code);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return value;
	}

	public static String getStringByReg(String content, String reg) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(content);
		String result = "";
		if (matcher.find()) {
			result = matcher.group();
		}
		return result;
	}

	public static boolean isRegex(String html, String regex) {
		Pattern patten = Pattern.compile(regex);
		Matcher mat = patten.matcher(html);
		if (mat.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 
	 * @param regex
	 *            正则表达式
	 * @param html
	 *            待匹配的字符串
	 * @return 返回的是匹配的list集合（可能由于正则表达式的不同有多条记录）
	 * **/
	public static List<String> myRegex(String html, String regex, int number) {
		Pattern patten = Pattern.compile(regex);
		Matcher mat = patten.matcher(html);
		List<String> list = new ArrayList<String>();
		while (mat.find()) {
			if (number == -1) {
				list.add(mat.group());
				continue;
			}
			if (number > 0) {
				list.add(mat.group());
				number--;
			} else {
				break;
			}
		}
		return list;
	}

	/**
	 * 取文本之间的字符串
	 * 
	 * @param string
	 *            源字符串
	 * @param start
	 *            开始字符串
	 * @param end
	 *            结束字符串
	 * @return 成功返回中间子串，失败返回null
	 */
	public static String mid(String string, String start, String end) {
		int s = string.indexOf(start) + start.length();
		int e = string.indexOf(end, s);
		if (s > 0 && e > s)
			return string.substring(s, e);
		return null;
	}

	/**
	 * 中文编码转换
	 * 
	 * @param s
	 *            待转换的中文unicode编码
	 * @return 返回中文
	 */
	public static String convert(String s) {
		StringBuffer sb = new StringBuffer();
		int i = -1;
		int pos = 0;
		while ((i = s.indexOf("\\u", pos)) != -1) {
			sb.append(s.substring(pos, i));
			if (i + 5 < s.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(s.substring(i + 2, i + 6), 16));
			}
		}
		sb.append(s.substring(pos));
		return sb.toString();
	}

	/**
	 * 判断是否是数字
	 * 
	 * 
	 * */
	public static boolean isNumeric(String string) {
		if (string == null || string.length() == 0)
			return false;

		int l = string.length();
		for (int i = 0; i < l; i++) {
			if (!Character.isDigit(string.codePointAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 判断是否是空格
	 * 
	 * 
	 * 
	 * */
	public static boolean isWhitespace(int c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
	}

	/**
	 * 
	 * 格式化 含有换行的html
	 * 
	 * */
	public static String normaliseWhitespace(String string) {
		StringBuilder sb = new StringBuilder(string.length());

		boolean lastWasWhite = false;
		boolean modified = false;

		int l = string.length();
		for (int i = 0; i < l; i++) {
			int c = string.codePointAt(i);
			if (isWhitespace(c)) {
				if (lastWasWhite) {
					modified = true;
					continue;
				}
				if (c != ' ')
					modified = true;
				sb.append(' ');
				lastWasWhite = true;
			} else {
				sb.appendCodePoint(c);
				lastWasWhite = false;
			}
		}
		return modified ? sb.toString() : string;
	}

	public static List<String> myRegex(String html, String regex) {
		return myRegex(html, regex, -1);
	}
}
