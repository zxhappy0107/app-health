package cn.anshirui.store.appdevelop.common;

import cn.anshirui.store.appdevelop.api.LentivirusCode;
import cn.anshirui.store.appdevelop.api.WarnCode;
import cn.anshirui.store.appdevelop.entity.AdminUserMain;

import java.lang.reflect.Field;
import java.util.Date;

import static cn.anshirui.store.appdevelop.common.DatetimeUtils.*;

public class SomeWay {
	
	public static Double getDreamRisk(Date spintime, int dreamlength, int deep, int level, int break_block, int highblood) {
		double risk = 0;
		try {
			String today = date2string(spintime, YYYY_MM_DD);
			String yesterday = reduceOneDay(today);
			if (compareTime(spintime, string2date(today + " 00:00:00", YYYY_MM_DD_HH_MM_SS))) {
				risk += 1;
			}else if (compareTime(spintime, string2date(yesterday + " 00:00:00", YYYY_MM_DD_HH_MM_SS))) {
				risk += 0.5;
			}
			if (dreamlength > 5 && dreamlength <= 8) {
				risk += 0.3;
			}else if (dreamlength > 4 && dreamlength <= 5) {
				risk += 0.5;
			}else if (dreamlength > 3 && dreamlength <= 4) {
				risk += 0.8;
			}else if (dreamlength > 0 && dreamlength <= 3) {
				risk += 1;
			}
			if (deep > 0 && deep <= 30) {
				risk += 1;
			}else if (deep > 30 && deep <= 60) {
				risk += 0.5;
			}
			if (level > 15) {
				risk += 1;
			}
			if (level > 3) {
				risk += 1;
			}
			if (highblood == 1) {
				risk += 3;
			}
			if (break_block > 0) {
				risk += 0.5;
			}
			return risk;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int getRisk(double risk) {
		if (risk > 0 && risk <= 9) {
			return 5;
		}else if (risk > 9 && risk <= 20) {
			return 10;
		}else if (risk > 20 && risk < 30) {
			return 15;
		}else if (risk > 30 && risk <= 50) {
			return 20;
		}else if (risk > 50) {
			return 30;
		}else {
			return 0;
		}
	}

	public static int degree(AdminUserMain adminUserMain) throws Exception{
		Class cls = adminUserMain.getClass();
		Field[] fieldps = cls.getDeclaredFields();
		int sign = 0;
		for (int i = 0,j = fieldps.length; i < j; i++){
			Field f = fieldps[i];
			f.setAccessible(true);
			if (!f.getName().equals("umid") && StringUtils.isNotEmpty((String) f.get(adminUserMain))){
				sign += 7;
			}
		}
		return sign >= 98 ? 100 : sign;
	}

	/**
	 * @Author zhangxuan
	 * @Description //TODO
	 * @Date 16:50 2019/12/17
	 * @Param [warnType 1一般预警2健康预警, type预警类型]
	 * @return java.lang.String
	 **/
	public static String resultWarnHint(int warnType, Date start,int type, Integer value){
		if (warnType == 1){
			return "您在" + DatetimeUtils.date2string(start, DatetimeUtils.YYYY_MM_DD) + " " + WarnCode.getMsg(type) + "，请您关注。";
		}else {
			if (type == 1){
				return "您在" + DatetimeUtils.date2string(start, DatetimeUtils.YYYY_MM_DD_HH_MM_SS) + " 发生" + LentivirusCode.getMsg(type) + "持续时间" + value + "秒，请您关注。";
			}else {
				return "您在" + DatetimeUtils.date2string(start, DatetimeUtils.YYYY_MM_DD) + " 发生" + LentivirusCode.getMsg(type) + "，请您关注。";
			}
		}
	}
	
}
