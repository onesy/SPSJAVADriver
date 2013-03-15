package org.onesy_Driver.Publisher;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class NIOSocketClient {
	private static final int CLIENT_PORT = 10200;
	private static final int SERVER_PORT = 10201;

	/**
	 * 命令String
	 */
	public static ArrayList<String> orders = new ArrayList<String>();

	/**
	 * 
	 */
	public static void main(String[] args) {
		NIOSocketClient nio = new NIOSocketClient();
		loadOrder();
		for (int i = 0; i < orders.size(); i++) {
			try {
				Thread.sleep(10);
				
				nio.pub(orders.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
//				System.out.println("retry");
//				i--;
			}// catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			/* } */catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void pub(String msg) throws IOException {

		SocketChannel sc = SocketChannel.open();
		Selector sel = Selector.open();
		try {
			sc.configureBlocking(false);
			sc.socket().setReuseAddress(true);
			sc.socket().bind(new InetSocketAddress(CLIENT_PORT));
			sc.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE
					| SelectionKey.OP_CONNECT);
			int i = 0;
			boolean done = false;
			String encoding = System.getProperty("file.encoding");
			Charset cs = Charset.forName(encoding);
			ByteBuffer buf = ByteBuffer.allocate(256);
			while (!done) {
				sel.select();
				Iterator it = sel.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					it.remove();
					// 获取创建通道选择器事件键的套接字通道
					sc = (SocketChannel) key.channel();
					// 当前通道选择器产生连接已经准备就绪事件，并且客户端套接字
					// 通道尚未连接到服务端套接字通道
					if (key.isConnectable() && !sc.isConnected()) {
						InetAddress addr = InetAddress.getByName(null);
						// 客户端套接字通道向服务端套接字通道发起非阻塞连接
						boolean success = sc.connect(new InetSocketAddress(
								addr, SERVER_PORT));
						// 如果客户端没有立即连接到服务端，则客户端完成非立即连接操作
						if (!success)
							sc.finishConnect();
					}
					// 如果通道选择器产生读取操作已准备好事件，且已经向通道写入数据
					if (key.isReadable()) {
						if (sc.read((ByteBuffer) buf.clear()) > 0) {
							// 从套接字通道中读取数据
							String response = cs
									.decode((ByteBuffer) buf.flip()).toString();
							System.out.println(response);
							if (response.indexOf("\r\r\rEND\r\r\r") != -1)
								done = true;
						}
					}
					// 如果通道选择器产生写入操作已准备好事件，并且尚未想通道写入数据
					if (key.isWritable()) {
						// 向套接字通道中写入数据
						sc.write(ByteBuffer.wrap(new String(msg
								+ "\r\r\rEND\r\r\r").getBytes()));
//						System.err.println(new String(msg
//								+ "\r\r\rEND\r\r\r"));
						/*
						 * 下面被注释的这么一句话告诉了我们一个真谛，有话，一次性说完
						 * sc.write(ByteBuffer.wrap(new
						 * String("\r\r\rEND\r\r\r") .getBytes()));
						 */
						i++;
						done = true;
						sc.finishConnect();
					}
				}
			}
		} finally {
			sc.close();
			sel.close();
		}
	}

	public static void loadOrder() {
		// 数据结构 voteSerialNo\r\r\n\nsign\r\r\r\rmsgKing\r\r\rTransactionnNo\r\r\risOrigin\r\r\rMsg
		// 127.0.0.1_6379_pub_sub_0
//		orders.add(0,"0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rConfirmOrder\r\r\r0\r\r\r1\r\r\rmyname\r:\rbinp");
		for(int i = 0 ; i < 1; i ++ ){
			orders.add(i,"0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\r" + i+"\r\r\r0\r\r\rhengl1\r:\rluoheng" + i);
		}
//		orders.add(1,"0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\r0\r\r\r1\r\r\rhengl\r:\rluoheng");
//		orders.add(2,"0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\r0\r\r\r1\r\r\rbinp\r:\rpangbing");
		
//		orders.add(3,"END");
//		orders.add(3,"0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\r0\r\r\r1\r\r\rNO\r:\r2");

	}
}