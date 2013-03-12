package org.onesy_Driver.Publisher;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Client {
	
	private static final int CLIENT_PORT = 10200;
	private static final int SERVER_PORT = 10201;
	
	private static final String serverAddress = "127.0.0.1";

	/**
	 * 命令String
	 */
	public static ArrayList<String> orders = new ArrayList<String>();
	
	
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	/**
	 * 
	 */
	public static void main(String[] args) {
		loadOrder();
		for (int i = 0; i < orders.size(); i++) {
			Client pulbic_client = new Client();
			pulbic_client.pub(orders.get(i));
		}
	}
	public static void loadOrder() {
		// 数据结构 voteSerialNo\r\r\rsign\r\r\rmsgKing\r\r\rMsg
		// 127.0.0.1_6379_pub_sub_0
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
		orders.add("0\r\r\r127.0.0.1_6379_pub_sub_0\r\r\rSetKVOrder\r\r\rNO\r:\r1");
	}
	
	public void pub(String order) {
		try {
			socket = new Socket(serverAddress, SERVER_PORT);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader line = new BufferedReader(new InputStreamReader(
					System.in));
			out.println(line.readLine()+"\r\r\rEND\r\r\r");
			line.close();
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
		}
	}
}