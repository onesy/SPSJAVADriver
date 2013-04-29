package org.onesy.driver.ExchangeArea;

import java.util.HashMap;

import org.onesy_driver.Bean.RTNBean;

public class RTNWindow {
	
	private static HashMap<Long, RTNBean> RTNWindows = new HashMap<Long, RTNBean>();

	public static RTNBean addRTNBean(long Transaction, long timeout){
		RTNBean rtn = RTNBean.getInstance(Transaction, System.currentTimeMillis(), timeout);
		RTNWindows.put(Transaction, rtn);
		return rtn;
	}
	
	public static void ContentRTN(long Transanction, String content){
		RTNWindows.get(Transanction).fillContent(content);
	}
	
	public static void DelRTNBean(long Transanction){
		RTNWindows.remove(Transanction);
	}
}
