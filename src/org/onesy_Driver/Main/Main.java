package org.onesy_Driver.Main;

import redis.clients.jedis.Jedis;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 数据结构 voteSerialNo\r\r\n\nsign\r\r\n\nmsgKing\r\r\n\nMsg
		Jedis jedis = new Jedis("127.0.0.1", 6379, 5000);
		
	}

}
