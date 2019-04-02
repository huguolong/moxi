package com.moxi.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * 字符串工具类。
 * @version 1.0
 */
public class StringUtil {
	/**
	 * 
	 * 从输入的字符串中从首字符开始截取指定的长度
	 * @param inputString
	 * @param len
	 * @return 截取后的字符串
	 */
	public static String subStrByCodePoints(final String inputString, final int len) {
        String s = inputString;
        if (s.codePointCount(0, s.length()) > len) {
            s = s.substring(0,s.offsetByCodePoints(0, len));
        }
        return s;
    }
    /**
     * 
     *  从输入的字符串中从首字符开始截取指定的长度，如果原字符串长度大于截取长度在截取的字符串后面加“。。。”
     * @param inputString
     * @param len
     * @return 截取后的字符串
     */
    public static String subStrByCodePointsWithMore(final String inputString, final int len) {
        String s = inputString;
        if (s.codePointCount(0, s.length()) > len) {
            s = s.substring(0,s.offsetByCodePoints(0, len))+"...";
        }
        return s;
    }
    /**
     * 
     * 截取指定开始和结束位置的字符串
     * @param inputString
     * @param start
     * @param end
     * @return 截取后的字符串
     */
    public static String subStringByCodePoints(final String inputString, final int start, final int end) {
        final String s1 = subStrByCodePoints(inputString,start);
        final String s2 = subStrByCodePoints(inputString,end);
        return s2.substring(s1.length(),s2.length());
    }
    
    /**
     * 生成 UUID
     * @param content
     * @return 32位的UUID
     */
    public static String getOnlyKey(){
        return String.valueOf(UUID.randomUUID()).replace("-", "");
    }
//    /**
//     * 
//     * 把字符串中所有超链接内容转换成html编码
//     * @param input
//     * @return 转换后的字符串
//     */
//    public static String  xssAndAddHyperLinkStr(final String input){
//        final String str = XSSUtil.htmlEncode(input);
//        
//        final String webStartRegex = "([f|F][t|T][p|P]|[h|H][t|T][t|T][p|P]|[h|H][t|T][t|T][p|P][s|S])(&#58;&#47;&#47;)";
//        //\w.-_/:?%&=
//        final String httpRegex="(("+webStartRegex+")|((?!"+webStartRegex+")[w|W]{3}\\.))(\\w|\\.|\\-|_|(&#47;)|(&#58;)|(&#63;)|(&#37;)|(&#38;)|(&#61;))+";
//        final Pattern httpPattern = Pattern.compile(httpRegex);
//        final Matcher httpMatcher = httpPattern.matcher(str);  
//        
//        final StringBuffer sb = new StringBuffer();
//        while (httpMatcher.find()) {
//            final String matchStr = httpMatcher.group();
//            if(matchStr.toLowerCase(Locale.getDefault()).indexOf("www")==0){
//                httpMatcher.appendReplacement(sb, "<a href='http://"+matchStr+"' target='blank' >"+matchStr+"</a>");
//            }else{
//                httpMatcher.appendReplacement(sb, "<a href='"+matchStr+"' target='blank' >"+matchStr+"</a>");
//            }
//        }
//        httpMatcher.appendTail(sb);
//        
//        return sb.toString();
//    }
//    /**
//     * 
//     * 获取HTML格式的字符串无超链接
//     * @param s
//     * @param escapeXml
//     * @return 字符串
//     */
// 
//    public static String getHtmlString(final String s,final boolean escapeXml){
//    	return getHtmlString(s,escapeXml,false);
//    }
//    /**
//     * 
//     *	获取HTML格式的字符串
//     * @param s 输入字符串
//     * @param escapeXml 
//     * @param addHyperLink
//     * @return  字符串
//     */
//    public static String getHtmlString(final String s,final boolean escapeXml,final boolean addHyperLink){
//    	String str;
//    	if(escapeXml){
//    		if(addHyperLink){
//    			str = xssAndAddHyperLinkStr(s);
//    		}else{
//    			str = XSSUtil.htmlEncode(s);
//    		}
//    		
//    		str = str.replaceAll("&#13;&#10;", "<br>");
//        	str = str.replaceAll("&#10;", "<br>");
//        	str = str.replaceAll("&#13;", "<br>");
//    	}else{
//    		str = s;
//    		str = str.replaceAll("\\x0D\\x0A", "<br>");
//        	str = str.replaceAll("\\x0A", "<br>");
//        	str = str.replaceAll("\\x0D", "<br>");
//    	}
//	
//    	return str;    	
//    }
    
    /**
	 * 检验输入值是否为空或NUL.
	 * 
	 * @param value 输入检验值
	 *           
	 * @return 如果输入值为真返回true,否则返回false
	 */
	public static boolean isNull(final String value) {
		return (null == value || value.trim().equals(""));
	}
	
	/**
	 * 检验输入值是否为空或NUL.
	 * 
	 * @param value 输入检验值
	 *           
	 * @return 如果输入值为真返回true,否则返回false
	 */
	public static boolean isNull(final BigDecimal value) {
		return (null == value || value.toString().trim().equals("") || value.compareTo(new BigDecimal(0)) < 1);
	}
	
	/**
	 * 检验输入值是否为空或NUL.
	 * 
	 * @param value 输入检验值
	 *           
	 * @return 如果输入值为真返回true,否则返回false
	 */
	public static boolean isNull(final Integer value) {
		return (null == value || Integer.toString(value).trim().equals(""));
	}
	
	/**
	 * 检验输入值是否为空或NUL.
	 * 
	 * @param value 输入检验值
	 *           
	 * @return 如果输入值为真返回true,否则返回false
	 */
	public static boolean isNull(final Long value) {
		return (null == value || Long.toString(value).trim().equals(""));
	}
	
	/**
	 * 检验输入值是否为空或NUL.
	 * 
	 * @param value 输入检验值
	 *           
	 * @return 如果输入值为真返回true,否则返回false
	 */
	public static boolean isNull(final Double value) {
		return (null == value || Double.toString(value).trim().equals(""));
	}
	
	/**
	 * 检验输入值是否为空或NUL.
	 * 
	 * @param value 输入检验值
	 *           
	 * @return 如果输入值为真返回true,否则返回false
	 */
	public static boolean isNull(final Date value) {
		return (null == value || value.toString().trim().equals(""));
	}
	
	/**
	 *  检验输入字符是否为数字
	 * 
	 * @param c 字符
	 *            
	 * @return 如果输入字符为真返回true,否则返回false
	 */
	public static boolean isDigit(final char c) {
		return c >= '0' && c <= '9';
	}

	/**
	 *  检验输入字符串是否为日期格式字符串
	 * 
	 * @param date 输入检验字符串
	 *           
	 * @param pattern 日期格式如：yyyy-mm-dd HH:MM:ss
	 *            
	 * @return 如果是指定格式的日期字符串则返加true,否则返回false
	 */
	public static boolean isDate(final String date, final String pattern) {
		boolean result = true;
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
			sdf.parse(date);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	/**
	 * 把字符串中strFrom替换成strTo
	 * 
	 * @param paramStrSource 输入字串
	 *            
	 * @param strFrom 要被替换是字符串
	 *            
	 * @param strTo 替换字符
	 *            
	 * @return 替换成功后的字符串
	 */
	public static String replace(final String paramStrSource, final String strFrom,
	        final String strTo) {
		String strSource = paramStrSource;
		final StringBuffer strDest = new StringBuffer("");
		final int intFromLen = strFrom.length();
		int intPos;

		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest.append(strSource.substring(0, intPos));
			strDest.append(strTo);
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest.append(strSource);

		return strDest.toString();
	}

	/**
	 *  把字符串中from替换成to,如果标志位ignoreCase是True，则忽略大小进行替换
	 * 
	 * @param value  输入字符串       
	 * @param from 被替换字符串
	 * @param to 替换字符串
	 * @param ignoreCase 是否要忽略大小
	 * @return 替换后的字符串
	 */
	public static String replaceAll(final String value, final String from, final String to,
	        final boolean ignoreCase) {
		String result = null;
		if (null != value) {
			final String activeValue = ignoreCase ? value.toLowerCase(Locale.ENGLISH) : value;
			final String fromValue = ignoreCase ? from.toLowerCase(Locale.ENGLISH) : from;

			final List<Integer> occurences = findOccurences(activeValue, fromValue);

			final StringBuffer buf = new StringBuffer();
			buf.append(value);
			int offset = 0;
			for (final Iterator<Integer> it = occurences.iterator(); it.hasNext();) {
				final Integer i = (Integer) it.next();
				final int index = i.intValue() + offset;
				buf.replace(index, index + from.length(), to);
				offset += to.length() - from.length();
			}
			result = buf.toString();
		}
		return result;
	}
	
	/**
	 * 分割字符串
	 * 
	 * @param s 字符串
	 *           
	 * @param patterns 分割符
	 *            
	 * @return 分割后的数组
	 */
	public static List<String> split(final String s, final String[] patterns) {
		final Set<String> checkedPatterns = new HashSet<String>();
		Arrays.sort(patterns);
		List<String> result = null;
		for (int i = patterns.length - 1; i >= 0; i--) {
			if (i == patterns.length - 1) {
				result = split(s, patterns[i], true);
			} else {
				int count = 0;
				while (true) {
					if (count == result.size()) {
						break;
					}
					final String temp = (String) result.get(count);
					if (checkedPatterns.contains(temp)) {
						count++;
					} else {
						final List<String> newResult = split(temp, patterns[i], true);
						if (newResult.size() == 1) {
							count++;
						} else {
							result.remove(count);
							result.addAll(count, newResult);
							count += newResult.size();
						}
					}
				}
			}
			checkedPatterns.add(patterns[i]);
		}
		return result;
	}

	/**
	 * 根据正则表达式分割字符数组
	 * 
	 * @param target 源字符串
	 *           
	 * @param regex 正则表达式
	 * 
	 * @return 分割后的数据
	 */
	public static List<String> splitByTrim(final String target, final String regex) {
		final List<String> list = new ArrayList<String>();
		boolean start = true;

		String s = "";
		if (null != target) {
			s = target;
		}

		while (start) {
			s = s.trim();
			if (s.indexOf(regex) == -1) {
				start = false;
				list.add(s);
			} else {
				final int beginIndex = s.indexOf(regex);
				list.add(s.substring(0, beginIndex));
				
				s = s.substring(beginIndex + 1).trim();
			}
		}

		return list;
	}

	/**
	 * Split.
	 * 
	 * @param s
	 *            the s
	 * @param pattern
	 *            the pattern
	 * @return the list
	 */
	public static List<String> split(final String s, final String pattern) {
		return split(s, pattern, false);
	}

	/**
	 * According to the patterns Completed s is split.
	 * 
	 * @param s
	 *            line
	 * @param pattern
	 *            the pattern
	 * @param includePattern
	 *            includePattern
	 * @return split of list
	 */
	public static List<String> split(final String s, final String pattern,
	        final boolean includePattern) {
		final List<String> result = new LinkedList<String>();
		int pos = 0;
		while (true) {
			if (pos == s.length()) {
				break;
			}

			final int index = s.indexOf(pattern, pos);
			if (index == -1) {
				result.add(s.substring(pos, s.length()));
				break;
			} else {
				result.add(s.substring(pos, index));
				if (includePattern) {
					result.add(s.substring(index, index + pattern.length()));
				}
				pos = index + pattern.length();
			}
		}
		return result;
	}
	
	/**
     * Splits a string at the specified character.
     */
    public static String[] split(final String s, final char c) {
        int i, b, e;
        int cnt;
        String res[];
        final int ln = s.length();

        i = 0;
        cnt = 1;
        while ((i = s.indexOf(c, i)) != -1) {
            cnt++;
            i++;
        }
        res = new String[cnt];

        i = 0;
        b = 0;
        while (b <= ln) {
            e = s.indexOf(c, b);
            if (e == -1){ 
            	e = ln;
            }
            res[i++] = s.substring(b, e);
            b = e + 1;
        }
        return res;
    }

	
	/**
	 * 按长度截取字符串
	 * 
	 * @param inputString 输入字符串
	 *            
	 * @param len 截取长度
	 *            
	 * @return 截取后的字符串
	 */
	public static String subStr(final String inputString, final int len) {
		String s = inputString;
		if (s.length() > len) {
			s = s.substring(0, len);
		}
		return s;
	}
	
	/**
	 * 检验是否是数字字符串，包含小数
	 * 
	 * @param number 输入字符串
	 *            
	 * @return 如果是的返回true,否则返回false
	 */
	public static boolean checkIsDecimal(final String number) {
		boolean result = true;
		String number1 = "";
		if ((null == number) || ("".equals(number.trim()))) {
			result = false;
		} else {
			final int indexFu = number.indexOf('.');
			if (indexFu > 0) {
				result = false;
			} else if (indexFu == 0) {
				number1 = number.substring(1);
				final int index = number1.indexOf('.');
				if (index < 0) {
					result = isNumeric(number1);
				} else {
					final String num1 = number1.substring(0, index);
					final String num2 = number1.substring(index + 1);
					result = isNumeric(num1) && isNumeric(num2);
				}
			}
		}
		return result;

	}

	/**
	 * 检验字符串字符是否字都为是数字（0-9）
	 * 
	 * @param 输入字符串
	 * @return 如果是返回true，否则返false
	 */
	public static boolean isNumeric(final String str) {
		boolean result = true;
		if (null == str) {
			result = false;
		} else {
			final int sz = str.length();
			for (int i = 0; i < sz; i++) {
				if (!Character.isDigit(str.charAt(i))) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * 检验字符串字符是否都为整数字符
	 * 
	 * @param str 输入字符串
	 *            
	 * @return  如果是返回true，否则返false
	 */
	public static boolean isInteger(final String str) {
		boolean result = false;
		try {
			Integer.parseInt(str);
			result = true;
		} catch (Exception e) {
			result = false;
		}	
		return result;
	}
	
	/**
	 * 判断字符串是否为正整数
	 * @param strVal
	 * @return
	 */
	public static boolean isInt(String strVal){
		boolean flag = false;
		if(!isNull(strVal)){
			boolean f = strVal.matches("[0-9]+"); //验证字符串是否只包含数字
			if(f && strVal.length() <= String.valueOf(Integer.MAX_VALUE).length()){
				long val = Long.parseLong(strVal);
				if(val <= Integer.MAX_VALUE) 
					flag = true;
			}
		}
		return flag;
	}
	 
	/**
	 * 字符串小于指定长度右补指定字符
	 * 
	 * @param target 输入字符串
	 * @param pad 被位字符
	 * @param maxLength 输出字符长度
	 * @return 输出字符长度
	 */
	public static String rightPad(final String target, final String pad, final int maxLength) {
		final StringBuffer targetValue = new StringBuffer();
		targetValue.append(null == target ? "" : target);

		while (true) {
			if (targetValue.length() < maxLength) {
				targetValue.append(pad);
			} else {
				break;
			}
		}
		return targetValue.toString();
	}

	/**
	 * 字符串小开指定长度左右指定字符
	 * 
	 * @param target 输入字符串
	 * @param pad 补位符
	 * @param maxLength 长度
	 * @return 输出字符串
	 */
	public static String leftPad(final String target, final String pad, final int maxLength) {
		final StringBuffer targetValue = new StringBuffer();
		targetValue.append(null == target ? "" : target);

		while (true) {
			if (targetValue.length() < maxLength) {
				final String s = pad + targetValue.toString();
				targetValue.setLength(0);
				targetValue.append(s);
			} else {
				break;
			}
		}
		return targetValue.toString();
	}
	/**
	 * 
	 * 把字符串数组合并成一个字符串并插入指定字符串
	 * @param ls 合并字符串数组
	 * @param pattern  插入字符串
	 * @return 输入出字符串
	 */
	public static String listToString(final List<String> ls,final String pattern){
		final StringBuffer sb = new StringBuffer(100);
		if(ls!=null){
			for(int i=0;i<ls.size();i++){
				if(i==0){
					sb.append(ls.get(i));
				}else{
					sb.append(pattern).append(ls.get(i));
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 
	 * 把array数组合并成一个字符串并插入指定字符串
	 * @param array 合并数组
	 * @param pattern 插入字符串
	 * @return 输出字符串
	 */
	public static String arrayToString(final String[] array,final String pattern){
		final StringBuffer sb = new StringBuffer(100);
		if(array!=null){
			for(int i=0;i<array.length;i++){
				if(i==0){
					sb.append(array[i]);
				}else{
					sb.append(pattern).append(array[i]);
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 查找出一个字符串在输入字符串中的所有位置
	 * 
	 * @param content 输入字符串
	 *         
	 * @param matchValue 查找字符串
	 * @return 查到的位置的集合
	 */
	private static List<Integer> findOccurences(final String content, final String matchValue) {
		final List<Integer> occurences = new LinkedList<Integer>();

		int pos = 0;
		while (pos < content.length()) {
			final int index = content.indexOf(matchValue, pos);
			if (index == -1) {
				break;
			}
			occurences.add(Integer.valueOf(String.valueOf(index)));
			pos = index + matchValue.length();
		}

		return occurences;
	}
	/**
	 * 
	 * 检验字符串是匹配
	 * @param pattern 格式
	 * @param s 检验字符串
	 * @return  检验结果：true,false
	 */
	public static boolean validFormat(final String pattern,final String s){
		final Pattern p=Pattern.compile(pattern);
		final Matcher m=p.matcher(s);
		return m.matches();	
	}
	
	/**
	 * 检验输入字符串是否包含指定字符串被分割符“;”分割出来的任一字符串
	 * 
	 * @param string 输入字符串
	 * @param str 被分割的字符
	 * @return 如果包含返回true,否则返回false;
	 */
	public static boolean checkStringContainsStr(final String string, final String str) {
		return checkStringContainsStr(string, str, ";");
	}

	/**
	 * 检验输入字符串是否包含指定字符串被正则表式分割出来的任一字符串
	 * 
	 * @param string  输入字符串
	 * @param str  被分割的字符
	 * @param regex 侵害正刚表达式
	 * @return 如果包含返回true,否则返回false;
	 */
	public static boolean checkStringContainsStr(final String string, final String str,
			final String regex) {
		boolean temp = false;
		if (!StringUtil.isNull(string) && !StringUtil.isNull(str)) {
			final List<String> dataList = StringUtil.splitByTrim(str, regex);
			for (String data : dataList) {
				if (string.equals(data)) {
					temp = true;
					break;
				}
			}
		}
		return temp;
	}
	
	/**
	 * 删除字符中的("\r","\n").
	 * 
	 * @param s 输入字符串
	 *            
	 * @return 删除后的字符
	 */
	public static String removeCR(final String s) {
		String result = "";
		if (null != s && !"".equals(s)) {
			final String s1 = s.replaceAll("\r", "");
			result = s1.replaceAll("\n", "");
		}
		return result;
	}

	/**
	 * 删除字符中的 CR("\r","
	 * <p>
	 * ","\n").
	 * 
	 * @param s 输入字符串
	 *            
	 * @return 删除后的字符串
	 */
	public static String replaceCR(final String s) {
		String result = "";
		if (null != s && !"".equals(s)) {
			final String s1 = s.replaceAll("\r", "<p>");
			result = s1.replaceAll("\n", "");
		}
		return result;
	}
	
	/**
	 * 检验输入字符串和指定的长度是否相等
	 * 
	 * @param sEntry 输入字符串
	 *          
	 * @param iLength 指定长度
	 *            
	 * @return 如字符长度小于指定长度返回true，否则返加 false；
	 */
	public static boolean checkLength(final String sEntry, final int iLength) {
		boolean result = true;
		if (null == sEntry) {
			result = false;
		} else {
			if (sEntry.length() < iLength) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 如果字符为NULL，则字符串赋一个“”值
	 * 
	 * @param sString 输入字符串
	 *            
	 * @return 字符串
	 */
	public static String removeNull(final String sString) {
		String s = "";
		if (null != sString) {
			s = sString;
		}
		return s;
	}

	/**
	 * 如果字符为NULL，则字符串赋一个空字符（“”），否则删除所有空格
	 * 
	 * @param sString 输入字符串
	 *           
	 * @return 删除空格后的字符串
	 */
	public static String removeNullTrim(final String sString) {
		String s = "";
		if (null != sString) {
			s = sString.trim();
		}
		return s;
	}
	/**
	 * 删除所有空格
	 * 
	 * @param sString 输入字符串
	 *           
	 * @return 删除空格后的字符串
	 */
	public static String trim(final String sString) {
		String s = null;
		if (null != sString) {
			s = sString.trim();
		}
		return s;
	}

	/**
	 * 把字符串转化成整数
	 * 
	 * @param sValue 输入字符串
	 *            
	 * @return 整数
	 */
	public static int parseInt(final String sValue) {
		int i;
		try {
			i = Integer.parseInt(sValue);
		} catch (NumberFormatException e) {
			i = 0;
		}
		return i;
	}

	/**
	 * 把字符串转化成double
	 * 
	 * @param sEntry
	 *            the s entry
	 * @return the double
	 */
	public static double convertToDouble(final String sEntry) {
		int iSign, iLength;
		boolean bNegative = false;
		double dValue;
		double result = 0;

		if (null != sEntry) {
			String sNoSignNum = sEntry;

			iLength = sEntry.trim().length();
			// Quit if empty string is passed
			if (null == sEntry || iLength == 0) {
				result = 0;
			} else {
				try {
					iSign = sEntry.indexOf('-') + 1;

					// Check for negative sign
					if (iSign > 0) {
						bNegative = true;
						sNoSignNum = sEntry.replace('-', ' ');
					}

					dValue = Double.parseDouble(sNoSignNum);

					if (bNegative) {
						result = (dValue * -1);
					} else {
						result = dValue;
					}
				} catch (NumberFormatException e) {
					result = 0;
				}
			}
		}
		return result;
	}
	/**
	 * 
	 * 把IP转化成长整型
	 * @param ip
	 * @return long
	 */

	public static Long convertIpToLong(final String ip){
		final String[] ips = StringUtil.split(ip, '.');
		double ipDouble = Math.pow(2, 24)*Double.valueOf(ips[0])+Math.pow(2, 16)*Double.valueOf(ips[1])+Math.pow(2, 8)*Double.valueOf(ips[2])+Double.valueOf(ips[3]);
		
		return Double.valueOf(ipDouble).longValue();
	}
	
	/**
	 * 字符串解码
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getDecodeAsUTF8(String str) throws UnsupportedEncodingException{
		String temp = "";
		if(!isNull(str)){
			temp = URLDecoder.decode(str, "utf-8");
		}
		return temp;
	}
}
