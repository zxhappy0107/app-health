package cn.anshirui.store.appdevelop.common;

import cn.hutool.core.date.DateUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  

/**

* Copyright (C),2018, ZhangXuan info.

* FileName: DatetimeUtils.java

* 日期格式通用类

* @author zx

* @Date    2017年9月20日 下午5:52:21

* @version 1.00

*/
public class DatetimeUtils {
    /** 格式：EEE, d MMM yyyy HH:mm:ss z*/
    public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";  
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";  
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";  
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";  
    public static final String YYYY = "yyyy";  
    public static final String MM = "MM";  
    public static final String DD = "dd";  
    public static final String HH_MM_SS="hh:mm:ss";  
    /**
    * 格式化日期对象
    * @param date 待格式化的日期
    * @param formatStr 想要格式化的日期格式
    * @return 根据formatStr的格式，转化为指定格式的date类型
    * @author hey
    * @Date    2017年9月20日下午5:46:58
    * @version 1.00
    */
    public static Date date2date(Date date, String formatStr) {  
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);  
        String str = sdf.format(date);  
        try {  
            date = sdf.parse(str);  
        } catch (Exception e) {  
            return null;  
        }  
        return date;  
    }  
    
    /**
    * 时间对象转换成字符串
    * @param date 待格式化日期
    * @param formatStr 日期格式化格式
    * @return 根据formatStr转换为指定字符串
    * @author zx
    * @Date    2017年9月20日下午5:47:14
    * @version 1.00
    */
    public static String date2string(Date date, String formatStr) {  
        String strDate = "";  
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);  
        strDate = sdf.format(date);  
        return strDate;  
    }  
    
    /**
    * sql时间对象转换成字符串
    * @param timestamp 待装换的SQL格式日期
    * @param formatStr 格式化格式
    * @return 根据formatStr的格式，转换为指定字符串
    * @author zx
    * @Date    2017年9月20日下午5:47:23
    * @version 1.00
    */
    public static String timestamp2string(Timestamp timestamp, String formatStr) {  
        String strDate = "";  
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);  
        strDate = sdf.format(timestamp);  
        return strDate;  
    }  
    
    /**
    * sql时间对象转换成字符串
    * @param formatStr 格式化格式
    * @return 根据formatStr的格式，转换为指定字符串
    * @author zx
    * @Date    2017年9月20日下午5:47:23
    * @version 1.00
    */
    public static String time2string(Time time, String formatStr) {  
        String strDate = "";  
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);  
        strDate = sdf.format(time);  
        return strDate;  
    }  
   
    /**
    * 字符串转换成时间对象 
    * @param dateString 待装换为date类型的字符串
    * @param formatStr 日期格式化格式
    * @return 根据formatStr的格式，转换为date类型
    * @author zx
    * @Date    2017年9月20日下午5:47:32
    * @version 1.00
    */
    public static Date string2date(String dateString, String formatStr) {  
        Date formateDate = null;  
        DateFormat format = new SimpleDateFormat(formatStr);  
        try {  
            formateDate = format.parse(dateString);  
        } catch (ParseException e) {  
            return null;  
        }  
        return formateDate;  
    }  
    
    /**
    * Date类型转换为Timestamp类型
    * @param date 待转换的date类型
    * @return 返回Timestamp
    * @author zx
    * @Date    2017年9月20日下午5:47:40
    * @version 1.00
    */
    public static Timestamp date2timestamp(Date date) {  
        if (date == null)  
            return null;  
        return new Timestamp(date.getTime());  
    }  
   
    /**
    * 获得当前年份 
    * @return 返回当前年份：YYYY
    * @author zx
    * @Date    2017年9月20日下午5:47:48
    * @version 1.00
    */
    public static String getNowYear() {  
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY);  
        return sdf.format(new Date());  
    }  
    
    /**
    * 获得当前月份 
    * @return 返回当前月份：MM
    * @author zx
    * @Date    2017年9月20日下午5:47:56
    * @version 1.00
    */
    public static String getNowMonth() {  
        SimpleDateFormat sdf = new SimpleDateFormat(MM);  
        return sdf.format(new Date());  
    }  
 
    /**
    * 获得当前日期中的日
    * @return 返回当前年月日中的日：dd
    * @author zx
    * @Date    2017年9月20日下午5:48:26
    * @version 1.00
    */
    public static String getNowDay(){  
        SimpleDateFormat sdf = new SimpleDateFormat(DD);  
        return sdf.format(new Date());  
    }  
    
    /**
    * 将格式为：“HH:mm:ss”的字符串转换为time类型
    * @param time 格式为：“HH:mm:ss”
    * @return 返回此格式的time类型
    * @author zx
    * @Date    2017年9月25日下午7:54:05
    * @version 1.00
    */
    public static Time getTime(String time){
        SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
        Date d=null;
        try{
     	   d=format.parse(time);
        } catch(Exception e){
     	   e.printStackTrace();
        }
        Time timeFormat=new Time(d.getTime());
        return timeFormat;
    }
  
    /**
    * 指定时间距离当前时间的中文信息 
    * @param time 
    * @return
    * @author zx
    * @Date    2017年9月20日下午5:48:46
    * @version 1.00
    */
    public static String getLnow(long time) {  
        Calendar cal = Calendar.getInstance();  
        long timel = cal.getTimeInMillis() - time;  
        if (timel / 1000 < 60) {  
            return "1分钟以内";  
        } else if (timel / 1000 / 60 < 60) {  
            return timel / 1000 / 60 + "分钟前";  
        } else if (timel / 1000 / 60 / 60 < 24) {  
            return timel / 1000 / 60 / 60 + "小时前";  
        } else {  
            return timel / 1000 / 60 / 60 / 24 + "天前";  
        }  
    }  
    
	/**
	* 计算两个日期的时间差
	* @param one 开始时间，要求格式：yyyy-MM-dd HH:mm:ss
	* @param two 结束时间，要求格式：yyyy-MM-dd HH:mm:ss
	* @return 返回的字符串格式：小时：分钟：秒钟
	* @author zx
	* @Date    2017年9月25日下午7:44:29
	* @version 1.00
	*/
	public static String getDistanceTime(Date one, Date two) {  
        long day = 0;  
        long hour = 0;  
        long hour1=0;
        long min = 0;  
        long sec = 0;  
        
        long time1 = one.getTime();  
        long time2 = two.getTime();  
        long diff ;  
        if(time1<time2) {  
            diff = time2 - time1;  
        } else {  
            diff = time1 - time2;  
        }  
        day = diff / (24 * 60 * 60 * 1000);  
        hour = (diff / (60 * 60 * 1000) - day * 24);  
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);  
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);  
            
        hour1=diff / (60 * 60 * 1000);  
        
        return hour1 + ":" + min + ":" + sec;  
    }  
	
	public static boolean compareTime(Date one, Date two) {
		long time1 = one.getTime();
		long time2 = two.getTime();
		if (time1 >= time2) {
			return true;
		}
		return false;
	}
	
	public static boolean compareTime(String one, String oneFormat, String two, String twoFormat) {
		long time1 = string2date(one, oneFormat).getTime();
		long time2 = string2date(two, twoFormat).getTime();
		if (time1 >= time2) {
			return true;
		}
		return false;
	}
	
	/**
	* 获取当前系统时间，返回格式为字符串
	* @return 返回格式：yyyy-MM-dd HH:mm:ss
	* @author hey
	* @Date    2017年9月28日下午2:51:50
	* @version 1.00
	*/
	public static String getNowSystemDatetimeString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}
	
	/**
	* 生成10位时间戳
	* @return
	* @author hey
	* @Date    2017年10月27日下午3:48:08
	* @version 1.00
	*/
	public static String getCurrentTimestamp10() {
		long timeStamp = new Date().getTime() / 1000;
		String timestr = String.valueOf(timeStamp);
		return timestr;
	}

	/**
	* 生成10位时间戳
	* @return
	* @author hey
	* @Date    2017年10月27日下午3:48:08
	* @version 1.00
	*/
	public static String getTimeStamp() {
		int time = (int) (System.currentTimeMillis() / 1000);
		return String.valueOf(time);
	}
	
	/**
	 * 
	 *Title: getStringToDate
	 *Description: 将一串字符转化为正常时间格式
	 * @param stringDate
	 * @return
	 * @throws ParseException 
	 */
	public static String getStringToDate(String stringDate,String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = stringDate;
		Date date = null;
		date = sdf.parse(time);
		return format.format(date);
	}
	
	/**
	 * 
	 *Title: timeAddTenMS
	 *Description: 给一个时间戳减10毫秒
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String timeAddTenMS(String time) throws ParseException {
		Date date = string2date(time, "yyyyMMddHHmmssS");
		Date alterTime = new Date(date.getTime() + 10);
		String newTime = date2string(alterTime, "yyyyMMddHHmmssS");
		if (newTime.length() < 17) {
			int count = 17 - newTime.length();
			String start = newTime.substring(0, 14);
			String end = newTime.substring(14, newTime.length());
			for (int i = 0; i < count; i++) {
				start += "0";
			}
			newTime = start + end;
		}
		return newTime;
	}
	
	/**
	 * 
	 *Title: timeAddTenMS
	 *Description: 给一个时间戳减10毫秒
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String timeAddHundredMS(String time) throws ParseException {
		Date date = string2date(time, "yyyyMMddHHmmssS");
		Date alterTime = new Date(date.getTime() + 100);
		String newTime = date2string(alterTime, "yyyyMMddHHmmssS");
		if (newTime.length() < 17) {
			int count = 17 - newTime.length();
			String start = newTime.substring(0, 14);
			String end = newTime.substring(14, newTime.length());
			for (int i = 0; i < count; i++) {
				start += "0";
			}
			newTime = start + end;
		}
		return newTime;
	}
	
	/**
	 * 
	 *Title: timeNow
	 *Description: 生成一个时间到毫秒
	 * @return
	 * @throws ParseException
	 */
	public static String timeNow() {
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmssS");
		return s.format(new Date());
	}
	
	/**
	 * 
	 *Title: putTime
	 *Description: 往前推迟20分钟时间
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static List<String> putTimeTwenty(String time) throws ParseException{
		List<String> list = new ArrayList<>();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat useTime = new SimpleDateFormat("HH:mm");
		Date dates = timeFormat.parse(time);
		Date alterTime = new Date(dates.getTime() - 1200000);
		for (int i = 0; i < 10; i++) {
			alterTime = new Date(alterTime.getTime() + 60000);
			list.add(useTime.format(alterTime));
			dates = alterTime;
		}
		return list;
	}
	
	/**
	 * 
	 *Title: putTimeStr
	 *Description:
	 * @param time
	 * @param formatStr 
	 * @param number 
	 * @param stance 1为增加0为减少
	 * @return
	 */
	public static List<String> putTimeStr(String time,String formatStr,int number,int stance){
		List<String> list = new ArrayList<>();
		if (stance == 1) {
			Date start = string2date(time, formatStr);
			Date addDate = null;
			for (int i = 0; i < Math.abs(number); i++) {
				list.add(date2string(start, formatStr));
				long add = start.getTime() + 3600000;
				addDate  = new Date(add);
				start = addDate;
			}
		}else {
			Date start = string2date(time, formatStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			calendar.add(calendar.HOUR, number);
			Date end = calendar.getTime();
			Date addDate = null;
			for (int i = 0; i < Math.abs(number); i++) {
				list.add(date2string(end, formatStr));
				long add = end.getTime() + 3600000;
				addDate  = new Date(add);
				end = addDate;
			}
		}
		return list;
	}
	
	/**
	 * 
	 *Title: putTime
	 *Description: 往前推迟10分钟时间
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static List<String> putTime(String time) throws ParseException{
		List<String> list = new ArrayList<>();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat useTime = new SimpleDateFormat("HH:mm");
		Date dates = timeFormat.parse(time);
		Date alterTime = new Date(dates.getTime() - 600000);
		for (int i = 0; i < 10; i++) {
			alterTime = new Date(alterTime.getTime() + 60000);
			list.add(useTime.format(alterTime));
			dates = alterTime;
		}
		return list;
	}
	
	/**
	 * 
	 *Title: putTime
	 *Description: 往前推迟13小时时间
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static List<String> putHourTime(String time) throws ParseException{
		List<String> list = new ArrayList<>();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat useTime = new SimpleDateFormat("HH");
		Date dates = timeFormat.parse(time);
		Date alterTime = new Date(dates.getTime() - 46800000);
		for (int i = 0; i < 13; i++) {
			alterTime = new Date(alterTime.getTime() + 3600000);
			list.add(useTime.format(alterTime));
			dates = alterTime;
		}
		return list;
	}
	
	/**
	 * 
	 *Title: putTimeTen
	 *Description: 一个时间推迟十分钟
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String putTimeTen(String time) throws ParseException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dates = timeFormat.parse(time);
		Date alterTime = new Date(dates.getTime() - 540000);
		return timeFormat.format(alterTime);
	}
	
	/**
	 * 
	 *Title: putTimeTen
	 *Description: 一个时间推迟13个小时
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String putTimeHour(String time) throws ParseException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		Date dates = timeFormat.parse(time);
		Date alterTime = new Date(dates.getTime() - 46800000);
		return timeFormat.format(alterTime);
	}
	
	public static List<String> putAnyHour(String time,Integer hour,boolean result) throws ParseException{
		List<String> list = new ArrayList<>();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH");
		SimpleDateFormat useTime = new SimpleDateFormat("HH");
		Date dates = timeFormat.parse(time);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dates);
		calendar.add(calendar.HOUR, hour);
		Date alterTime = calendar.getTime();
		int add;
		if (result) {
			add = +1;
		}else {
			add = -1;
		}
		for (int i = 0; i < Math.abs(hour); i++) {
			list.add(useTime.format(alterTime));
			Calendar c = Calendar.getInstance();
			c.setTime(alterTime);
			c.add(calendar.HOUR, add);
			alterTime = c.getTime();
		}
		return list;
	}
	
	/**
	 * 
	 *Title: reduceOneDay
	 *Description: 减少一天的时间
	 * @param time
	 * @return YYYY_MM_DD
	 */
	public static String reduceOneDay(String time) {
		Date date = string2date(time, YYYY_MM_DD);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE,-1);
		return date2string(calendar.getTime(), YYYY_MM_DD);
	}
	
	/**
	 * 
	 *Title: reduceDay
	 *Description: 增加或者减少任意天的时间
	 * @param time
	 * @return YYYY_MM_DD
	 */
	public static String reduceDay(String time,int day) {
		Date date = string2date(time, YYYY_MM_DD);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE,day);
		return date2string(calendar.getTime(), YYYY_MM_DD);
	}
	
	/**
	 * 
	 *Title: changeHour
	 *Description: 增加或减少小时的时间
	 * @param time
	 * @return YYYY_MM_DD_HH
	 */
	public static String changeHour(String time,int hour) {
		Date date = string2date(time, YYYY_MM_DD_HH);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.HOUR,hour);
		return date2string(calendar.getTime(), YYYY_MM_DD_HH);
	}
	
	/**
	 * 
	 *Title: changeHour
	 *Description: 增加或减少分钟的时间
	 * @param time
	 * @return
	 */
	public static String changeMin(String time,String formatStr,int min,String resultStr) {
		Date date = string2date(time, formatStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.MINUTE,min);
		return date2string(calendar.getTime(), resultStr);
	}
	
	/**
	 * 
	 *Title: changeHour
	 *Description: 增加或减少秒的时间
	 * @param time
	 * @return
	 */
	public static String changeSecond(String time,String formatStr,int second,String resultStr) {
		Date date = string2date(time, formatStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.SECOND,second);
		return date2string(calendar.getTime(), resultStr);
	}
	
	/**
	 * 
	 *Title: reduceOneDay
	 *Description: 减少任意天的时间
	 * @param time
	 * @return
	 */
	public static List<String> reduceDayList(String time,int day) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i <= Math.abs(day); i++) {
			list.add(time);
			Date date = string2date(time, YYYY_MM_DD);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(calendar.DATE,-1);
			time = date2string(calendar.getTime(), YYYY_MM_DD);
		}
		return list;
	}
	
	/**
	 * 
	 *Title: reduceOneDay
	 *Description: 加一个小时的时间
	 * @param time
	 * @return
	 */
	public static String addOneHour(String time) {
		Date date = string2date(time, YYYY_MM_DD_HH_MM_SS);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.HOUR,+1);
		calendar.add(calendar.SECOND, -1);
		return date2string(calendar.getTime(), YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	 * 
	 *Title: reduceOneDay
	 *Description: 加一秒的时间
	 * @param time
	 * @return
	 */
	public static String addOneSecond(String time) {
		Date date = string2date(time, YYYY_MM_DD_HH_MM_SS);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.SECOND, +1);
		return date2string(calendar.getTime(), YYYY_MM_DD_HH_MM_SS);
	}
	
	/**
	 * 
	 *Title: transformTime
	 *Description: 日期转换
	 * @param time
	 * @return
	 */
	public static String transformTime(String time) {
		Date date = string2date(time, "yyyy年MM月dd日HH时mm分");
		return date2string(date, YYYY_MM_DD_HH_MM);
	}
	
	/**
	 * 
	 *Title: transformTimeYMD
	 *Description: 日期转换
	 * @param time
	 * @return
	 */
	public static String transformTimeYMD(String time) {
		Date date = string2date(time, "yyyy年MM月dd日");
		return date2string(date, YYYY_MM_DD);
	}
	
	/**
	 * 
	 *Title: transformTimeAtWill
	 *Description: 日期转换
	 * @param time
	 * @return
	 */
	public static String transformTimeAtWill(String time,String formatStr) {
		Date date = string2date(time, YYYY_MM_DD);
		return date2string(date, formatStr);
	}
	
	/**
	 * 
	 *Title: transform
	 *Description: 日期转换
	 * @param time
	 * @return
	 */
	public static String transform(String time,String formatStr,String resultStr) {
		Date date = string2date(time, formatStr);
		return date2string(date, resultStr);
	}
	
	/**
	 * 
	 *Title: durationTime
	 *Description: 计算百分比时间段
	 * @param data
	 * @return
	 */
	public static String durationTime(String data,String t) {
		int day = Integer.parseInt(t);
		double time = Double.parseDouble(data) / 100;
		time = time * day;
		int hour = (int) (time / 60) ;
		int min = (int) (time % 60 ) ;
		return hour + "小时" + min + "分钟";
	}

	public static List<String> weekSE(String week,String time) {
		int day = 0;
		switch (week) {
		case "星期一":
			day = -1;
			break;
		case "星期二":
			day = -2;
			break;
		case "星期三":
			day = -3;
			break;
		case "星期四":
			day = -4;
			break;
		case "星期五":
			day = -5;
			break;
		case "星期六":
			day = -6;
			break;
		case "星期日":
			day = -7;
			break;
		}
		List<String> list = new ArrayList<>();
		String start = reduceDay(reduceDay(time, day), -6);
		list.add(start);
		for (int i = 0; i < 6; i++) {
			start = reduceDay(start, +1);
			list.add(start);
		}
		return list;
	}

	/**
	 * 
	 *Title: returnFirstLastMonth
	 *Description: 返回前一个月的第一天和最后一天的日期(yyyy-MM-dd)
	 * @return first last
	 */
	public static Map<String, String> returnFirstLastMonth(){
		Map<String, String> map = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		map.put("first", date2string(calendar.getTime(), DatetimeUtils.YYYY_MM_DD));
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.DAY_OF_MONTH, 0);
		map.put("last", date2string(calendar2.getTime(), DatetimeUtils.YYYY_MM_DD));
		return map;
	}
	
	/**
	 * 
	 *Title: returnFirstLastMonth
	 *Description: 返回前X月的第一天和最后一天的日期(yyyy-MM-dd)
	 * @return first last
	 */
	public static Map<String, String> returnFirstLastMonth(int count){
		Map<String, String> map = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -count);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		map.put("first", date2string(calendar.getTime(), DatetimeUtils.YYYY_MM_DD));
		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.MONTH, -(count - 1));
		calendar2.set(Calendar.DAY_OF_MONTH, 0);
		map.put("last", date2string(calendar2.getTime(), DatetimeUtils.YYYY_MM_DD));
		return map;
	}
	
	/**
	 * 
	 * @Title: reMonthDay   
	 * @Description: TODO(返回这一年这个月有多少天)   
	 * @param: @param year
	 * @param: @param month
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	public static int reMonthDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 0);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static List<String> weekRun(){
		List<String> list = new ArrayList<>();
		String start = reduceDay(DateUtil.today(), -7);
		for (int i = 0, j = 7; i < j ; i++){
			list.add(start + "&&" + transform(start, YYYY_MM_DD, "E").substring(2, 3));
			start = reduceDay(start, 1);
		}
		return  list;
	}

	/**
	 * @Author zhangxuan
	 * @Description //TODO 本周的第一天
	 * @Date 17:30 2019/12/26
	 * @Param []
	 * @return java.lang.String
	 **/
	public static String getWeekStart(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 0);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		Date time=cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	public static void main(String[] args) {
		System.out.println(transform("2019-12-30", "yyyy-MM-dd", "E").split("星期")[1]);
	}

}