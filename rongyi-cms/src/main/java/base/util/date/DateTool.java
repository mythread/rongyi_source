package base.util.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;


/**
 * 日期处理工具。
 * <p>
 * 相关的日期处理工具类为org.apache.commons.lang.time.DateFormatUtils
 * 
 * @since 1.0.0
 */

public class DateTool {

	/** YYY格式 */
	public static final String FORMAT_DATE_YEAR = "yyyy";

	/** YYYY-MM 格式 */
	public static final String FORMAT_DATE_YEAR_MONTH = "yyyy-MM";

	/** YYYY-MM-dd 格式 */
	public static final String FORMAT_DATE = "yyyy-MM-dd";

	/** 默认格式 */
	public static final String FORMAT_TIME = "HH:mm:ss";

	/** 默认日期时间格式 */
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_MICROSECOND = "yyyyMMddHHmmssmmm";
	public static final String FORMAT_DATE_2 = "yyyyMMdd";

	/** 日志 */
	private static Logger logger = Logger.getLogger(DateTool.class);



	/**
	 * 将日期转换成字符格式
	 * 
	 * @param date
	 *            java.util.Date类型
	 * @param format
	 *            如果为null或""，默认为DATE格式
	 * @return 无法成功转换则返回null
	 */
	public static String date2String(java.util.Date date, String format) {
		String result = null;
		if (date == null) {
			return result;
		}
		if (StringUtils.isEmpty(format)) {
			format = FORMAT_DATE;
		}
		try {
			result = DateFormatUtils.format(date, format);
		} catch (Exception ex) {
			logger.warn("日期转换为字符串错误，日期：" + date.toString() + "， 格式：" + format);
		}

		return result;
	}



	/**
	 * 将字符串转换成日期格式
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param format
	 *            相应的转换格式，如果参数为Blank则表示按常规的三种格式转换
	 * @return 如果不能正常转换则返回null
	 */
	public static java.util.Date string2Date(String str, String format) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		Date result = null;
		String[] formats = null;
		if (StringUtils.isEmpty(format)) {
			formats = new String[3];
			formats[0] = FORMAT_DATETIME;
			formats[1] = FORMAT_DATE;
			formats[2] = FORMAT_TIME;
		} else {
			formats = new String[4];
			formats[0] = format;
			formats[1] = FORMAT_DATETIME;
			formats[2] = FORMAT_DATE;
			formats[3] = FORMAT_TIME;
		}
		try {
			result = DateUtils.parseDate(str, formats);
		} catch (Exception ex) {
			logger.warn("日期或时间格式不正确，日期时间字符串：" + str + "， 格式：" + format);

		}

		return result;
	}

	/**
	 * 将字符串转换成sql日期格式
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param format
	 *            相应的转换格式，如果参数为Blank则表示按常规的三种格式转换
	 * @return 如果不能正常转换则返回null
	 */
	public static java.sql.Date string2SqlDate(String str, String format) {
		return new java.sql.Date(string2Date(str, format).getTime());
	}

	/**
	 * 将时间戳转换成字符串格式
	 * 
	 * @param ts
	 *            时间戳
	 * @param format
	 *            日期时间格式
	 * @return 转换后的字符串
	 */
	public static String timestamp2String(Timestamp ts, String format) {
		return ts == null ? null : date2String(
				new java.util.Date(ts.getTime()), format);
	}

	/**
	 * 将字符串转换成时间戳格式
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param format
	 *            相应的转换格式，如果参数为Blank则表示按常规的三种格式转换
	 * @return 如果不能正常转换则返回null
	 * @throws Exception
	 */
	public static Timestamp string2Timestamp(String str, String format) {
		return string2Date(str, format) == null ? null : new Timestamp(
				string2Date(str, format).getTime());
	}

	/**
	 * 添加年。
	 * 
	 * @param date
	 *            日期
	 * @param num
	 *            添加的年数
	 * @return 添加后的日期
	 */
	public static java.util.Date addYears(java.util.Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, num);
		return cal.getTime();
	}

	/**
	 * 添加月份。
	 * 
	 * @param date
	 *            日期
	 * @param num
	 *            添加对月数
	 * @return 添加后的日期
	 */
	public static java.util.Date addMonths(java.util.Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, num);
		return cal.getTime();
	}

	/**
	 * 添加天数。
	 * 
	 * @param date
	 *            日期
	 * @param num
	 *            添加的天数
	 * @return 添加后的日期
	 */
	public static java.util.Date addDays(java.util.Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, num);
		return cal.getTime();
	}

	public static java.util.Date addHours(java.util.Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, num);
		return cal.getTime();
	}

	/**
	 * 得到当年第一天的开始时间。
	 * 
	 * @param date
	 *            日期
	 * @return 当年第一天的开始时间
	 */
	public static java.util.Date getFirstDateOfYear(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR,
				cal.getActualMinimum(Calendar.DAY_OF_YEAR));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到当月第一天的开始时间。
	 * 
	 * @param date
	 *            日期
	 * @return 当月第一天的开始时间
	 */
	public static java.util.Date getFirstDateOfMonth(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static String getFirstDateOfMonth(Date date, String format) {
		Date firstDay = getFirstDateOfMonth(date);
		return date2String(firstDay, format);
	}

	/**
	 * 得到当年的最后一天最后一秒。
	 * 
	 * @param date
	 *            日期
	 * @return 当年最后一天最后一秒
	 */
	public static java.util.Date getLastDateOfYear(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR,
				cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 得到当月的最后一天最后一秒。
	 * 
	 * @param date
	 *            日期
	 * @return 当月最后一天最后一秒
	 */
	public static java.util.Date getLastDateOfMonth(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static String getLastDateOfMonth(Date date, String format) {
		Date lastDay = getLastDateOfMonth(date);
		return date2String(lastDay, format);
	}

	/**
	 * 获得当前的星期的字符串形式，如：星期日，星期一……
	 * 
	 * @param date
	 *            日期
	 * @return 星期*
	 */
	public static String getStringDayOfWeek(Date date) {
		String result = "";
		if (null != date) {
			SimpleDateFormat formatter4 = new SimpleDateFormat("E");
			result = formatter4.format(date);
		}
		return result;
	}

	/**
	 * get Calendar
	 * 
	 * @return calendar
	 */
	public static Map<String, String> getyyyyMMddDateMap(String date){
		if (date.length() == 8) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("year", date.substring(0,4));
			map.put("month", date.substring(4,6));
			map.put("day", date.substring(6,8));
			return map;
		}else{
			return null;
		}

	}

	/**
	 * 格式化当前时间  返回格式HHmmssSSS
	 * @return
	 */
	public static String getCurrentHMSSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	/**
	 * 格式化当前时间  返回格式yyyyMMdd
	 * @return
	 */
	public static String getCurrentDateYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	
	/**
	 * 格式化当前时间  返回格式yyyy-MM-dd
	 * @return
	 */
	public static String getCurrentDateYY_MM_DD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	
	/**
	 * 比较两个String日期之间的大小
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compareDate(String DATE1, String DATE2) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        Date dt1 = df.parse(DATE1);
	        Date dt2 = df.parse(DATE2);
	        if (dt1.getTime() > dt2.getTime()) {
	            return 1;
	        } else if (dt1.getTime() < dt2.getTime()) {
	            return -1;
	        } else {
	            return 0;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return 0;
	}
	
}
