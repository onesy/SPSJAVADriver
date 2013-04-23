package org.onesy.driver.ExchangeArea;

import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderQ {
	public static ConcurrentLinkedQueue<String> orderQ = new ConcurrentLinkedQueue<String>();
	
	public static void AddOrder(String order){
		if (order == null) {
			new NullPointerException();
		}
		orderQ.add(order);
	}
	/**
	 * here may return null,watch out! 
	 */
	public static String getOrder(){
		return orderQ.poll();
	}
}
