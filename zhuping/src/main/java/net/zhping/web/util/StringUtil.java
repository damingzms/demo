package net.zhping.web.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Description:字符串工具
 * </p>
 * 
 * @author xumiao
 * @version 1.0 创建日期: 2011-11-22
 */
public class StringUtil {
    /**
     * <p>
     * Description:将字符串首字母置为小写字母
     * </p>
     * 
     * @author xumiao
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if ( Character.isLowerCase( s.charAt( 0 ) ) ) {
            return s;
        } else {
            return (new StringBuilder()).append( Character.toLowerCase( s.charAt( 0 ) ) ).append( s.substring( 1 ) ).toString();
        }
    }

    /**
     * <p>
     * Description:将字符串首字母置为大写字母
     * </p>
     * 
     * @author xumiao
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if ( Character.isUpperCase( s.charAt( 0 ) ) ) {
            return s;
        } else {
            return (new StringBuilder()).append( Character.toUpperCase( s.charAt( 0 ) ) ).append( s.substring( 1 ) ).toString();
        }
    }

    /**
     * <p>
     * Description:去掉字符串前后空白字符
     * </p>
     * 
     * @author xumiao
     * @param s
     * @return
     */
    public static String trimString(String s) {
        if ( null == s ) {
            s = "";
        } else {
            s = s.trim();
        }
        return s;
    }

    public static String object2String(Object obj){
    	if(null == obj){
    		return "";
    	}else{
    		return obj.toString();
    	}
    }
    /**
     * <p>
     * Description:将字符串转为整数
     * </p>
     * 
     * @author xumiao
     * @param date
     * @return
     */
    public static Integer string2Integer(String integerString) {
        Integer result;
        integerString = StringUtil.trimString( integerString );
        if ( "".equals( integerString ) ) {
            result = Integer.parseInt( "0" );
        } else {
            result = Integer.parseInt( integerString );
        }// END IF-ELSE
        return result;
    }

    /**
     * <p>
     * Description:将字符串转为长整型数
     * </p>
     * 
     * @author xumiao
     * @param date
     * @return
     */
    public static Long string2Long(String longString) {
        Long result;
        longString = StringUtil.trimString( longString );
        if ( "".equals( longString ) ) {
            result = Long.parseLong( "0" );
        } else {
            result = Long.parseLong( longString );
        }// END IF-ELSE
        return result;
    }

    /**
     * <p>
     * Description:将字符串转为浮点数
     * </p>
     * 
     * @author xumiao
     * @param date
     * @return
     */
    public static Double string2Double(String doubleString) {
        Double result;
        doubleString = StringUtil.trimString( doubleString );
        if ( "".equals( doubleString ) ) {
            result = Double.parseDouble( "0" );
        } else {
            result = Double.parseDouble( doubleString );
        }// END IF-ELSE
        return result;
    }

    /**
     * 判断字符串是否空或者NULL
     * @param str
     * @return  空或者NULL 返回True，否则false
     */
    public static boolean isEmpty(String str){
    	if(str==null || "".equals(str) || "null".equalsIgnoreCase(str)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 字符串类型价格相加
     * @param price1
     * @param price2
     * @return
     */
    public static String priceAdd(String price1,String price2){
    	BigDecimal decimalPrice1 = string2BigDecimal(price1);
    	BigDecimal decimalPrice2 = string2BigDecimal(price2);
    	BigDecimal result = decimalPrice1.add(decimalPrice2);
    	return result.toPlainString();
    }
    
    /**
     * 两个begDecimal相乘
     * @param d1
     * @param d2
     * @return
     */
    public static String bigDecimalMultiply(String d1,String d2){
    	BigDecimal decimalPrice1 = string2BigDecimal(d1);
    	BigDecimal decimalPrice2 = string2BigDecimal(d2);
    	BigDecimal result = decimalPrice1.multiply(decimalPrice2);
    	return result.toPlainString();
    }
    
    public static String bigDecimalDivide(String d1,String d2){
    	BigDecimal decimalPrice1 = string2BigDecimal(d1);
    	BigDecimal decimalPrice2 = string2BigDecimal(d2);
    	BigDecimal result = decimalPrice1.divide(decimalPrice2,decimalPrice1.scale()==0?2:decimalPrice1.scale(),BigDecimal.ROUND_HALF_UP);
    	return result.toPlainString();
    }
    /**
     * 字符串转成bigDecimal
     * @param bigDecimalStr
     * @return
     */
    public static BigDecimal string2BigDecimal(String bigDecimalStr){
    	BigDecimal result = null;
    	bigDecimalStr = StringUtil.trimString( bigDecimalStr );
    	if(StringUtil.isEmpty(bigDecimalStr)){
    		result = new BigDecimal(0);
    	}else{
    		result = new BigDecimal( bigDecimalStr );
    	}
    	return result;
    }
    
    public static List<String> split(String str,String regex){
    	str = StringUtil.trimString(str);
    	return Arrays.asList(str.split(regex));
    }
    
    public static String getIndexOfSplit(String str,String regex,int index){
    	str = StringUtil.trimString(str);
    	String [] temp = str.split(regex);
    	if(index >= 0 && index < temp.length){
    		return temp[index];
    	}else{
    		return "";
    	}
    }
    
    public static void main(String []arsg){
    	//System.out.println(StringUtil.priceAdd("123.1", "100.004"));
    	
    	System.out.println(StringUtil.split("123", ";"));
    }
}
