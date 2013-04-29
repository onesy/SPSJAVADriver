package org.onesy.driver.Method4User;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.onesy.driver.ExchangeArea.OrderQ;
import org.onesy.driver.ExchangeArea.RTNWindow;
import org.onesy_driver.Bean.NodeBean;

public class JCuckoo {

	private static JCuckoo jCuckoo = null;

	private static NodeBean nodeBean = null;

	/**
	 * 通信结束符
	 */
	public static final String ConnectEND = "\r\r\rEND";

	public static final String SEPERATOR = "\r\r\r";

	public static final String EQUALSEPERATOR = "\r:\r";

	public static final String PAIRSEPERATOR = "\n:\n";

	/**
	 * 选举序列号
	 */
	private static long VoteSerialNo = -1;

	// ------------------发送相关
	// 目前驱动监听端口是在10997号发送端口可以自行决定
	// 服务器的host
	public static String host;

	// 服务器的监听端口
	public static int lport;

	// 驱动发送的端口
	public static int pport;

	// 消息的种类
	private static String msgCategory;

	// 消息本身
	private static String msg;

	// ------------------驱动相关

	// 本节点的transactionNo
	private static long transationNo;

	public static int listenPort;

	public static int sendPort;

	private static String localhost;

	private static String sign;

	/**
	 * 
	 */
	private JCuckoo(String host, int db, int redisport, int listenPort,
			int sendPort) {
		JCuckoo.sign = host + "_" + listenPort + "_" + sendPort;
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
	
	public synchronized void initParams(int lport,int pport){
		JCuckoo.lport = lport;
		JCuckoo.pport = pport;
	}

	public synchronized void SetKV(Object key, Object value) {
		JCuckoo.msgCategory = "SetKV";
		String setQuery = key.toString() + EQUALSEPERATOR + value.toString();
		String Query = MsgBuildUper(VoteSerialNo, JCuckoo.nodeBean.getSign(),
				JCuckoo.msgCategory, transationNo, 0, setQuery);
		OrderQ.AddOrder(Query);
		// 调用完成，并不保证每个都成功,但是只要进入了集群，集群就会想办法插入这个值。
	}

	public synchronized String GetV(Object key) {
		String getQuery = null;
		String rtn = null;
		long tran = 0;
		JCuckoo.msgCategory = "GetV";
		getQuery = "key" + JCuckoo.EQUALSEPERATOR + key.toString()
				+ JCuckoo.PAIRSEPERATOR + "Client_Info"
				+ JCuckoo.EQUALSEPERATOR + JCuckoo.sign;
		String Order = MsgBuildUper(VoteSerialNo, JCuckoo.nodeBean.getSign(),
				JCuckoo.msgCategory, transationNo, 0, getQuery);
		OrderQ.AddOrder(Order);
		tran = transationNo;
		transationNo++;
		rtn = RTNWindow.addRTNBean(tran, -1).getRTNConten(-1);
		System.out.println("rtn 返回");
		return rtn;
	}


	// private static Object

	// 数据结构
	// voteSerialNo\r\r\n\nsign\r\r\rmsgKing\r\r\rTransactionnNo\r\r\risOrigin\r\r\rMsg
	public synchronized static String MsgBuildUper(long voteNo, String sign,
			String MsgCategory, long TransactionNo, int isOrigin, String Msg) {
		return voteNo + SEPERATOR + sign + SEPERATOR + MsgCategory + SEPERATOR
				+ TransactionNo + SEPERATOR + isOrigin + SEPERATOR + Msg;
	}

}
