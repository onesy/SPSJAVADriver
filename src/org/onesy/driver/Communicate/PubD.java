package org.onesy.driver.Communicate;

import java.io.IOException;

import org.onesy.driver.ExchangeArea.OrderQ;

public class PubD implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		NIOSocketClient niosc = new NIOSocketClient();
		int bufferSize = 1024;
		for(;;){
			String order = OrderQ.getOrder();
			if(order == null){
				continue;
			}
			try {
				System.out.println(order + "已经区出缓冲区：1");
				niosc.pub(order, bufferSize);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
