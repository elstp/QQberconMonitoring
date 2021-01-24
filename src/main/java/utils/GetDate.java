package utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDate {

	    public static String GetMDate() {
	    	   int y,m,d,h,s,f;      
	    	    Calendar cal= Calendar.getInstance();
	    	    y=cal.get(Calendar.YEAR);
	    	    m=cal.get(Calendar.MONTH);
	    	    d=cal.get(Calendar.DATE);
	    	    h=cal.get(Calendar.HOUR_OF_DAY);
	    	    f=cal.get(Calendar.MINUTE);
	    	    s=cal.get(Calendar.SECOND);
	    	   String DATEM = "[系统时间："+y+"年"+m+"月"+d+"日"+h+"时"+f+"分"+s+"秒] ";
			return DATEM;
	    }

	public static String GetMUDate() {
		int h,s,f;
		Calendar cal= Calendar.getInstance();
		h=cal.get(Calendar.HOUR_OF_DAY);
		f=cal.get(Calendar.MINUTE);
		s=cal.get(Calendar.SECOND);
		String DATEM = "["+h+":"+f+":"+s+"] ";
		return DATEM;
	}

		/**
		 * 将Date转换成String
		 * @param date 
		 * @return
		 */
		public static String date2String(Date date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sdf.format(date);
			return dateStr;
		}

	    
		/**
		 * 将Timestamp转换成String
		 * 用于数据库中字段类型为datetime
		 * @param timestamp
		 * @return
		 */
		public static String time2String(Timestamp timestamp) {
			Date date = new Date(timestamp.getTime());
			String dateStr = date2String(date);
			return dateStr;
		}

	
}




