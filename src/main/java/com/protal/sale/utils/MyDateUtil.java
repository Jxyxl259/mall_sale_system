package com.protal.sale.utils;

import java.util.Calendar;
import java.util.Date;

public class MyDateUtil {

	public static Date getFlowDate(int i) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE,i);
		Date time = c.getTime();
		/*String date = time.toString();*/
		return time;
	}
	
	
	
}
