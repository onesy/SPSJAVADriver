package org.onesy.driver.Method4User;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.onesy_driver.Bean.NodeBean;

public class JCuckoo {

	private static JCuckoo jCuckoo = null;

	private static NodeBean nodeBean = null;

	public static final String SEPERATOR = "\r\r\r";
	
	public static final String EQUALSEPERATOR = "\r:\r";

	/**
	 * 选举序列号
	 */
	private static long VoteSerialNo = -1;

	// ------------------发送相关
	// 目前驱动监听端口是在10997号发送端口可以自行决定
	// 服务器的host
	private static String host;

	// 服务器的监听端口
	private static int port;

	// 消息的种类
	private static String msgCategory;

	// 消息本身
	private static String msg;

	// ------------------驱动相关

	// 本节点的transactionNo
	private static long transationNo;

	private static int listenPort;

	private static int sendPort;

	private static String localhost;

	/**
	 * 
	 */
	private JCuckoo(String host, int db, int redisport, int listenPort,
			int sendPort) {
		transationNo = 0l;
		JCuckoo.listenPort = listenPort;
		JCuckoo.sendPort = sendPort;
		try {
			JCuckoo.localhost = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JCuckoo.nodeBean = new NodeBean(host, listenPort, sendPort, db,
				redisport);
	}

	/**
	 * 驱动实例仅仅允许存在一个
	 * 
	 * @param listenPort
	 * @param sendPort
	 * @return
	 */
	public synchronized static JCuckoo getInstance(String host, int db,
			int redisport, int listenPort, int sendPort) {
		if (JCuckoo.jCuckoo == null) {
			JCuckoo.jCuckoo = new JCuckoo(host, db, redisport, listenPort,
					sendPort);
		}
		return JCuckoo.jCuckoo;
	}

	public synchronized static void SetKV(Object key, Object value) {
		JCuckoo.msgCategory = "SetKV";
		String setQuery  = key.toString() + EQUALSEPERATOR + value.toString();
		String Query = MsgBuildUper(VoteSerialNo, JCuckoo.nodeBean.getSign(), JCuckoo.msgCategory, transationNo, 0, setQuery);
		

	}

	// 数据结构
	// voteSerialNo\r\r\n\nsign\r\r\rmsgKing\r\r\rTransactionnNo\r\r\risOrigin\r\r\rMsg
	public synchronized static String MsgBuildUper(long voteNo, String sign,
			String MsgCategory, long TransactionNo, int isOrigin, String Msg) {
		return voteNo + SEPERATOR + sign + SEPERATOR + MsgCategory + SEPERATOR
				+ TransactionNo + SEPERATOR + isOrigin + SEPERATOR + msg;
	}

}
