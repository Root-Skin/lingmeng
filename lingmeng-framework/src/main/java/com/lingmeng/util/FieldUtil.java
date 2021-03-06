package com.lingmeng.util;

import com.baomidou.mybatisplus.annotation.TableField;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


/**
 * @author tanghc
 */
public class FieldUtil {
    private FieldUtil() {
	}

	private static final String DOT = ".";
    
	/**
	 * 过滤"."
	 * 
	 * @param field 字段名
	 * @return 过滤‘.’符号
	 */
	public static String dotFilter(String field) {
		if (isNotEmpty(field) && field.indexOf(DOT) > -1) {
			String[] words = field.split("\\.");
			StringBuilder ret = new StringBuilder();
			for (String str : words) {
				ret.append(upperFirstLetter(str));
			}
			return ret.toString();
		}
		return field;
	}

	/**
	 * 将第一个字母转换成大写
	 * 
	 * @param str 内容
	 * @return 返回原字符串且第一个字符大写
	 */
	public static String upperFirstLetter(String str) {
		if (isNotEmpty(str)) {
			String firstUpper = String.valueOf(str.charAt(0)).toUpperCase();
			str = firstUpper + str.substring(1);
		}
		return str;
	}

	/**
	 * 将第一个字母转换成小写
	 * 
	 * @param str 内容
	 * @return 返回原字符串且第一个字母小写
	 */
	public static String lowerFirstLetter(String str) {
		if (isNotEmpty(str)) {
			String firstLower = String.valueOf(str.charAt(0)).toLowerCase();
			str = firstLower + str.substring(1);
		}
		return str;
	}
	
	public static final char UNDERLINE = '_';

	/**
	 * 驼峰转下划线
	 * @param param 内容
	 * @return 返回转换后的字符串
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			int preIndex = i - 1;
			int nextIndex = i + 1;
			// 是否需要变为小写字母
			boolean needToLower = (
					Character.isUpperCase(c) 
					&& preIndex > 0
					&& Character.isLowerCase(param.charAt(preIndex))
				)
				||
				(
						Character.isUpperCase(c) 
						&& nextIndex < len 
						&& Character.isLowerCase(param.charAt(nextIndex))
				);
			
			if (needToLower) {
				if(i > 0) {
					sb.append(UNDERLINE);
				}
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下划线转驼峰
	 * @param param 内容
	 * @return 返回转换后的字符串
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 字段是否被transient关键字修饰或有@Transient注解
	 * @param field
	 * @return 是返回true
	 */
	public static boolean isTransientField(Field field) {
		TableField transientAnno = field.getAnnotation(TableField.class);
		return transientAnno != null ? true : Modifier.isTransient(field.getModifiers());
	}

	private static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
	
	private static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

}
