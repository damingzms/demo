package net.zhping.web.util;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NumberUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(NumberUtil.class);
	
	/**
	 * 四舍五入
	 * @param number
	 * @param scale 小数位
	 * @return
	 */
	public static double round(Double number, int scale) {
		if (number == null) {
			return 0.0;
		}
		try {
			BigDecimal bigDecimal = BigDecimal.valueOf(number);
			return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			LOG.error("Parameter 'number' in CommonUtils.round is invalid, its value is: " + number, e);
			return 0.0;
		}
	}

	/**
	 * 格式化double数据
	 * @param num
	 * @param minDigits 最小小数位数
	 * @param maxDigits 最大小数位数
	 * @param groupingUsed 整数部分是否每三位用逗号隔开
	 * @return
	 */
	public static String formatNumber(double num, int minDigits, int maxDigits, boolean groupingUsed) {
		if (Double.isNaN(num)) {
			return "N/A";
		}
		
		java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(maxDigits);
		nf.setMinimumFractionDigits(minDigits);
		nf.setGroupingUsed(groupingUsed);
		
		return nf.format(round(num, maxDigits));
	}

	/**
	 * 格式化double数据
	 * @param num
	 * @param minDigits 最小小数位数
	 * @param maxDigits 最大小数位数
	 * @param groupingUsed 整数部分是否每三位用逗号隔开
	 * @return
	 */
	public static double formatDouble(double num, int maxDigits) {
		if (Double.isNaN(num)) {
			return Double.NaN;
		}
		try {
			return Double.valueOf(formatNumber(num, 0, maxDigits, false));
		} catch (NumberFormatException ne) {
			LOG.error("Exception when formatting double in CommonUtils.formatDouble, its value is: " + num, ne);
			return num;
		}
	}
	
	public static boolean booleanValue(Object b) {
		if (b instanceof Boolean)
			return ((Boolean) b).booleanValue();
		return false;
	}
	
	public static boolean isInfiniteOrNaN(double b) {
		return Double.isInfinite(b) || Double.isNaN(b);
	}
}
