package org.onesy_driver.Bean;

public class RTNBean extends BeanBase {

	public long transactionNo;

	public String RTNContent;

	public long sendTime;

	public long receiveTime;
	// -1 意味着没有过期时间
	public long timeout;
	// 处理达到
	public boolean exception;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.RTNContent;
	}

	private RTNBean(long transaction, long sendtime, long timeout) {
		this.transactionNo = transaction;
		this.sendTime = sendtime;
		this.timeout = timeout;
	}

	public static RTNBean getInstance(long transaction, long sendtime,
			long timeout) {
		return new RTNBean(transaction, sendtime, timeout);
	}

	public void fillContent(String rtnContent) {
		this.RTNContent = rtnContent;
		this.receiveTime = System.currentTimeMillis();
	}

	/**
	 * restTime millisecond
	 * 
	 * @param restTime
	 * @return
	 */
	public String getRTNConten(int restTime) {
		for (;;) {
			if (this.RTNContent == null) {
				try {
					if (restTime != -1)
						Thread.sleep(restTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			return this.RTNContent;
		}
	}
	
	/**
	 * 直接处理返回的数据
	 */
	public static void processRTNContent(String receiveMsg){
		/** 第一步，分离出content
		 *  第二步，分离出transaction
		 *  第三步，向RTNWindow中添加这个transaction对应的数据。
		 *  第四步，退出
		 */
		
		
	}

}
