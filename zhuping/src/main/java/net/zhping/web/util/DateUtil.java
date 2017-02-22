package net.zhping.web.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName DateHelper
 * @author SAM
 * @Description 日期时间工具类
 * @Date 2014-8-12 下午3:00:04
 * @version
 */
public class DateUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);

	public static DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	// 日期
	public static final String YEAR_TO_DAY = "yyyy-MM-dd";
	// 24小时制，精确到秒
	public static final String YEAR_TO_SEC = "yyyy-MM-dd HH:mm:ss";
	// 24小时制，精确到分
	public static final String YEAR_TO_MINUTE = "yyyy-MM-dd HH:mm";

	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String yyyyMMddHHmm = "yyyyMMddHHmm";

	public static String DDMMMYYYY = "ddMMMyyyy";

	public static String DDMMM = "ddMMM";

	public static String HHmm = "HH:mm";

	public static String yyyy = "yyyy";
	public static String yyyy_MM = "yyyy-MM";

	/** yyyyMMdd */
	public static String yyyyMMdd = "yyyyMMdd";

	public static String yy = new SimpleDateFormat("yy", Locale.CHINESE).format(new Date());

	public static final int oneMonthChangeToSecond = 2592000;

	public static final String MMDD = "mm-dd";

	public static final String yyyyMMdd2 = "YYYY年-MM月-dd日";

	public final static long MINUTE_MSEL = 60000L; // 分钟

	public final static int oneDayChangeToSecond = 86400;

	/**
	 * 返回当前时间字符串 YYYYMMDDHHMMSS
	 * 
	 * @return
	 */
	public static String getNow() {
		return formatDateByFormat(new Date(), YEAR_TO_SEC);
	}

	public static String getNow(String format) {
		return formatDateByFormat(new Date(), format);
	}

	public static Date getToday() {
		Calendar now = Calendar.getInstance();
		return getYYYYMMDD(formatDateByFormat(now.getTime(), YEAR_TO_DAY));
	}

	public static Date getSomedayFromToday(int field, int amount) {
		Calendar now = Calendar.getInstance();
		now.add(field, amount);
		return getYYYYMMDD(formatDateByFormat(now.getTime(), YEAR_TO_DAY));
	}

	/**
	 * <p>
	 * Description:将字符串转化为日期
	 * </p>
	 * 
	 * @param dateString
	 *            日期字符串
	 * @return yyyy-MM-dd
	 * @throws Exception
	 */
	public static Date getYYYYMMDD(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(YEAR_TO_DAY);
		Date dateTime = null;
		try {
			dateTime = dateFormat.parse(dateString);
		} catch (ParseException e) {
			dateTime = null;
		} // END TRY
		return dateTime;
	}

	public static Date getYYYYMMDDHHMMSS(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(YEAR_TO_SEC);
		Date dateTime = null;
		try {
			dateTime = dateFormat.parse(dateString);
		} catch (ParseException e) {
			dateTime = null;
		} // END TRY
		return dateTime;
	}

	/**
	 * Get Date from String "yyyy-MM-dd HH:mm"
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date getYYYYMMDDHHmm(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(YEAR_TO_MINUTE);
		Date dateTime = null;
		try {
			dateTime = dateFormat.parse(dateString);
		} catch (ParseException e) {
			dateTime = null;
		} // END TRY
		return dateTime;
	}

	/**
	 * 将yyyy-MM-dd时间格式转换成yyyyMMdd
	 * 
	 * @param dateString
	 * @return
	 */
	public static String stringToDateYyyyMMdd(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
		try {

			Date dateTime = sf.parse(dateString);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	/**
	 * 将yyyy-MM-dd时间格式转换成ddMMMyy
	 * 
	 * @param dateString
	 * @return
	 */
	public static String stringToDateddMMMyy(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy", Locale.US);
		try {

			Date dateTime = sf.parse(dateString);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	/**
	 * 将yyyy-MM-dd时间格式转换成MM-dd
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getMMDD(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.US);
		try {

			Date dateTime = sf.parse(dateString);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	/**
	 * 将yyyy-MM-dd时间格式转换成ddMMM
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getDDMMM(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMM", Locale.US);
		try {

			Date dateTime = sf.parse(dateString);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	/**
	 * 两个 format 日期格式 天数差
	 * 
	 * @param dateString1
	 * @param dateString2
	 * @return
	 */
	public static long minusDateDay(String startDateStr, String endDateStr, String format) {
		long quot = 0;
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			Date startDate = sf.parse(startDateStr);
			Date endDate = sf.parse(endDateStr);

			quot = endDate.getTime() - startDate.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			throw new RuntimeException("Not a date!");
		}

		return quot;
	}

	/**
	 * 两个 format 日期格式 天数差
	 * 
	 * @param dateString1
	 * @param dateString2
	 * @return
	 */
	public static long minusDateHours(String startDateStr, String endDateStr, String format) {
		long quot = 0;
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			Date startDate = sf.parse(startDateStr);
			Date endDate = sf.parse(endDateStr);

			quot = endDate.getTime() - startDate.getTime();
			quot = quot / 1000 / 60 / 60;
		} catch (ParseException e) {
			throw new RuntimeException("Not a date!");
		}

		return quot;
	}

	/**
	 * 两个 format 日期格式 天数差
	 * 
	 * @param dateString1
	 * @param dateString2
	 * @return
	 */
	public static long minusDateMinutes(String startDateStr, String endDateStr, String format) {
		long quot = 0;
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			Date startDate = sf.parse(startDateStr);
			Date endDate = sf.parse(endDateStr);

			quot = endDate.getTime() - startDate.getTime();
			quot = quot / 1000 / 60;
		} catch (ParseException e) {
			throw new RuntimeException("Not a date!");
		}

		return quot;
	}

	/**
	 * 获取 yyyy-MM-dd HH:mm:ss 里面的 HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String getHHmmFromDate(String date) {
		SimpleDateFormat sf = new SimpleDateFormat(YEAR_TO_SEC);
		String hhmm = "", hour = "", minute = "";
		try {
			Date dateTime = sf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateTime);
			if (cal.get((Calendar.HOUR_OF_DAY)) < 10) {
				hour += "0" + cal.get((Calendar.HOUR_OF_DAY));
			} else {
				hour += +cal.get((Calendar.HOUR_OF_DAY));
			}

			if (cal.get((Calendar.MINUTE)) < 10) {
				minute += "0" + cal.get((Calendar.MINUTE));
			} else {
				minute += cal.get((Calendar.MINUTE));
			}
			hhmm += hour + ":" + minute;
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return hhmm;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss 转化为 yyyy-MM-dd HH:mm
	 * 
	 * @param dateString
	 * @return
	 */
	public static String yearToSecToYearToMinute(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat(YEAR_TO_SEC);
		SimpleDateFormat sdf = new SimpleDateFormat(YEAR_TO_MINUTE);

		try {
			Date dateTime = sf.parse(dateString);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	/**
	 * 将yyyyMMdd时间格式转换成ddMMMyy
	 * 
	 * @param dateString
	 * @return
	 */
	public static String yyMMddToDateddMMMyy(String dateString) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyy", Locale.US);
		try {

			Date dateTime = sf.parse(dateString);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	public static String yyyyMMddToDateFormat(String dateString, String format) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date dateTime = sf.parse(dateString);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	public static String ddMMMyyToDateFormat(String dateString, String format) {
		SimpleDateFormat sf = new SimpleDateFormat("ddMMMyy", Locale.US);
		try {
			Date dateTime = sf.parse(dateString);
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	public static String ddMMMToDateFormat(String dateString, String format) {
		SimpleDateFormat sf = new SimpleDateFormat("ddMMM", Locale.US);
		try {
			Date dateTime = sf.parse(dateString);
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dateString = sdf.format(dateTime);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}
		return dateString;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Date getNowDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int getNowYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回此 Calendar 的时间值，以毫秒为单位。
	 * 
	 * @return
	 */
	public static long getTimeInMillis() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}

	/**
	 * weekCount -1:上周, 1:下周
	 * 
	 * @param weekCount
	 * @return
	 * @throws ParseException
	 */
	public static Date getMonday(int weekCount) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		if ("星期日".equals(getWeekOfDay(calendar.getTime()))) {
			weekCount--;
		}
		calendar.add(Calendar.DATE, weekCount * 7);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date result = format.parse(format.format(calendar.getTime()));
		return result;
	};

	/**
	 * 获取Date 所在的周开始日期(-1:上周,0:本周,1:下周)
	 * 
	 * @param date
	 * @param weekCount
	 * @return
	 * @throws ParseException
	 */
	public static Date getMonday(Date date, int weekCount) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if ("星期日".equals(getWeekOfDay(calendar.getTime()))) {
			weekCount--;
		}
		calendar.add(Calendar.DATE, weekCount * 7);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date result = format.parse(format.format(calendar.getTime()));
		return result;
	};

	/**
	 * weekCount -1:上周, 1:下周
	 * 
	 * @param weekCount
	 * @return
	 * @throws ParseException
	 */
	public static Date getSunday(int weekCount) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, (weekCount + 1) * 7);// 周一为本周第一天
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date result = format.parse(format.format(calendar.getTime()));
		return result;
	};

	/**
	 * 获取Date 所在的周结束日期(-1:上周,0:本周,1:下周)
	 * 
	 * @param date
	 * @param weekCount
	 * @return
	 * @throws ParseException
	 */
	public static Date getSunday(Date date, int weekCount) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, (weekCount + 1) * 7);// 周一为本周第一天
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date result = format.parse(format.format(calendar.getTime()));
		return result;
	};

	/**
	 * 
	 * @Title: getFristDay @Description: 获取指定日期年的第一天 @param date @return @return
	 * Date @throws
	 */
	public static Date getFirstDayOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Title: getlastDayOfYear @Description: 获取指定日期年的最后一天 @param
	 * date @return @throws ParseException @return Date @throws
	 */
	public static Date getlastDayOfYear(Date date) throws ParseException {
		String dateStr = format.format(date);
		dateStr = dateStr.substring(0, 4).concat("-12-31");
		Date result = format.parse(dateStr);
		return result;
	}

	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		int x = cal.get(Calendar.DAY_OF_YEAR);
		return x;
	}

	public static String getYearSub(Date date) {
		String str = formatDateByFormat(date, "yyyy");
		return str;
	}

	public static String getYearSubStr(String date) {
		String str = StringUtils.substringBefore(date, "-");
		return str;
	}

	public static String getMonthSubStr(String date) {
		String str = StringUtils.substringBetween(date, "-");
		return str;
	}

	public static String getDaySubStr(String date) {
		String str = StringUtils.substringAfterLast(date, "-");
		return str;
	}

	/**
	 * 
	 * @Title: getlastDayOfPreviousYear @Description: 获取上年最后一天 @param
	 * date @return @throws ParseException @return Date @throws
	 */
	public static Date getlastDayOfPreviousYear(Date date) throws ParseException {
		String dateStr = format.format(date);
		int year = Integer.valueOf(dateStr.substring(0, 4)) - 1;
		dateStr = year + "-12-31";
		Date result = format.parse(dateStr);
		return result;
	}

	/**
	 * 
	 * @Title: getFriDateOfMonth @Description: 获取本月第一天 @param
	 * date @return @return Date @throws
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * @Title: getLastDateOfMonth @Description: 获取本月的最后一天 @param
	 * date @return @return Date @throws
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		System.out.println(calendar.getMaximum(Calendar.DATE) + " " + calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	public static enum Pattern2 {
		YYYYMMDD("yyyy-MM-dd"), YYYYMMDD_HHMMSS("yyyy-MM-dd hh:mm:ss");

		Pattern2(String name) {
			this.name = name;
		}

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	/**
	 * @Title: getWeekOfDay @Description: 获取指定日期是周几 @param date @return @return
	 * String @throws
	 */
	public static String getWeekOfDay(Date date) {
		DateFormat format = new SimpleDateFormat("EEEE");
		return format.format(date);
	}

	/**
	 * 获取指定日期是周几
	 * 
	 * @param dt
	 * @return
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 常用的格式化日期
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String formatDate(Date date) {
		// 添加判断
		if (null == date) {
			return "";
		}
		return formatDateByFormat(date, YEAR_TO_SEC);
	}

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			result = sdf.format(date);
		}
		return result;
	}

	/**
	 * 
	 * 方法作用说明 Examples: 列举一些调用的例子
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 * @return: 返回值说明 @exception： 异常的说明
	 * @throws ParseException
	 * 
	 */
	public static Date parseDate(String dateStr, String format) throws ParseException {
		Date date = null;

		java.text.DateFormat df = new java.text.SimpleDateFormat(format);
		String dt = dateStr;
		date = (Date) df.parse(dt);
		return date;
	}

	/**
	 * @Description: 将日期转成字符串
	 * @date: 2014年11月5日
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateToString(Date date, String format) throws ParseException {

		java.text.DateFormat df = new java.text.SimpleDateFormat(format);
		String dateStr = "";
		dateStr = df.format(date);
		return dateStr;
	}

	/**
	 * @Description: 对指定日期增加相应的月份
	 * @param @param
	 *            date 当前需操作日期
	 * @param @param
	 *            number 增加数
	 * @param @return
	 * @return Date
	 */
	public static Date addMonth(Date date, int number) {

		Calendar endDate = Calendar.getInstance();
		endDate.setTime(date);
		endDate.add(Calendar.MONTH, number);// 增加一月
		return endDate.getTime();
	}

	/**
	 * @param nowDate
	 * @param anyDate
	 * @return nowDate-anyDate的差值（单位秒）
	 */
	public static long getDifferenceSecond(Date nowDate, Date anyDate) {
		long result = 0;
		result = (nowDate.getTime() - anyDate.getTime());
		if (result != 0) {
			result = result / 1000;
		}
		return result;
	}

	/**
	 * 将微信传入的long型的创建时间格式化为常见 格式
	 * 
	 * @param createTime
	 * @return
	 */
	public static Date formatWeixinCreateTime(long createTime) {
		// 将微信传入的CreateTime转换成long类型，再乘以1000,因为微信默认会进行毫秒整除
		long msgCreateTime = createTime * 1000L;
		Date date = null;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = format.format(new Date(msgCreateTime));

			date = format.parse(dateStr);
		} catch (ParseException e) {

			throw new RuntimeException("Not a date!");
		}

		return date;
	}

	/**
	 * 将TimeInMillis 转为Date
	 * 
	 * @param createTime
	 * @param format
	 * @return
	 */
	public static Date formatTimeInMillis(long createTime) {
		Date date = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(createTime);
		date = cal.getTime();
		return date;
	}

	/**
	 * 在日期格式上 加上天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		return calendar.getTime();
	}

	/**
	 * 获取某年某季度的第一天
	 * 
	 * @param quarter
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfQuarter(int quarter, Date date) {
		Date target = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		try {
			switch (quarter) {
			case 1:
				target = parseDate(year + "-01-01", YEAR_TO_DAY);
				break;
			case 2:
				target = parseDate(year + "-04-01", YEAR_TO_DAY);
				break;
			case 3:
				target = parseDate(year + "-07-01", YEAR_TO_DAY);
				break;
			case 4:
				target = parseDate(year + "-10-01", YEAR_TO_DAY);
				break;
			}
		} catch (ParseException e) {
			LOG.error("DateHelper======getFirstDayOfQuarter异常", e);
		}
		return target;
	}

	/**
	 * 获取某年某季度的最后一天
	 * 
	 * @param quarter
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfQuarter(int quarter, Date date) {
		Date target = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		try {
			switch (quarter) {
			case 1:
				target = parseDate(year + "-03-31 23:59:59", YEAR_TO_SEC);
				break;
			case 2:
				target = parseDate(year + "-06-30 23:59:59", YEAR_TO_SEC);
				break;
			case 3:
				target = parseDate(year + "-09-30 23:59:59", YEAR_TO_SEC);
				break;
			case 4:
				target = parseDate(year + "-12-31 23:59:59", YEAR_TO_SEC);
				break;
			}
		} catch (ParseException e) {
			LOG.error("DateHelper===getLastDayOfQuarter异常", e);
		}
		return target;
	}

	/**
	 * @Description: TODO用来得到两个时间相差多少分钟
	 * @param startDate
	 * @param stopDate
	 * @return double
	 */
	public static long getOffsetMinute(Date startDate, Date stopDate) {
		if (startDate == null || stopDate == null) {
			return 0;
		}
		return stopDate.getTime() - startDate.getTime();
	}

	/**
	 * 字符串转日期
	 * 
	 * @param sDate
	 * @param sDateFormat
	 * @return Date
	 */
	public final static Date str2Date(String sDate, String sDateFormat) {
		if (StringUtils.isBlank(sDate)) {
			return null;
		}
		try {
			SimpleDateFormat oSimpleDateFormat = new SimpleDateFormat(sDateFormat);
			return oSimpleDateFormat.parse(sDate);
		} catch (Exception oException) {
			oException.getMessage();
			return null;
		}
	}

	/**
	 * 日期转为字符串
	 * 
	 * @param date
	 *            处理Date对象
	 * @param dateFormat
	 *            格式化格式
	 * @return String
	 */
	public final static String date2Str(Date date, String dateFormat) {
		if (null == date) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		String temp = "";
		try {
			temp = simpleDateFormat.format(date);
		} catch (Exception e) {
			LOG.error("DateHelper===date2Str异常", e);
		}
		return temp;
	}

	/**
	 * 
	 * @Description: 获取时间的小时
	 * @param time
	 * @return String
	 * @date 2014年11月24日 下午3:45:55
	 */
	public static String getHourFromTime(long time) {
		String dateStr = date2Str(new Date(time), "HH:mm:ss");
		String hour = dateStr.substring(0, 2);
		return Integer.parseInt(hour) + "";
	}

	/**
	 * 
	 * @Description: 获取时间的分钟
	 * @param time
	 * @return String
	 * @date 2014年11月24日 下午3:45:55
	 */
	public static String getMinuteFromTime(long time) {
		String dateStr = date2Str(new Date(time), "HH:mm:ss");
		String minute = dateStr.substring(3, 5);
		return Integer.parseInt(minute) + "";
	}

	/**
	 * 
	 * @Description: 获取时间的秒
	 * @param time
	 * @return String
	 * @date 2014年11月24日 下午3:45:55
	 */
	public static String getSecondFromTime(long time) {
		String dateStr = date2Str(new Date(time), "HH:mm:ss");
		String second = dateStr.substring(6, 8);
		return Integer.parseInt(second) + "";
	}

}
