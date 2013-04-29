package org.onesy.driver.Communicate;

import java.io.IOException;

public class SubD implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		NIOSocketServer nioss = new NIOSocketServer();
		for(;;){
			try {
				nioss.listen();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
