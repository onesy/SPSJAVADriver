package org.onesy_Driver.Main;

import org.onesy.driver.Communicate.PubD;
import org.onesy.driver.Communicate.SubD;
import org.onesy.driver.Method4User.JCuckoo;

public class Main {
	
	public static long ii = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 数据结构 voteSerialNo\r\r\n\nsign\r\r\n\nmsgKing\r\r\n\nMsg
		/**
		long i = ii;
		ii ++;
		System.out.println("i = " + i + ", ii = " + ii);
		*/
		// 初始化jcuckoo的参数
		JCuckoo jCuckoo = JCuckoo.getInstance("127.0.0.1", 0, 6379, 10198, 10199);
		jCuckoo.initParams(10201, 10200);
		// 启动监听和发送县城
		SubD subd = new SubD();
		Thread listenTh = new Thread(subd);
		PubD pubd = new PubD();
		Thread sendTh = new Thread(pubd);
		listenTh.start();
		sendTh.start();
		// 设置值
		jCuckoo.GetV("123");
		
	}

}
