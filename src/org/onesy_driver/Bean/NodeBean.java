package org.onesy_driver.Bean;

public class NodeBean extends BeanBase {
	
	private String host;
	
	private int subPort;
	
	private int pubPort;
	
	private int db;
	
	private int redisport;
	
	private String sign;
	
	public NodeBean(String host, int subport, int pubport, int db, int redisport){
		this.host = host;
		this.subPort = subport;
		this.pubPort = pubport;
		this.db = db;
		this.redisport = redisport;
		this.sign = MsgToSign();
	}
	
	private String MsgToSign(){
		return this.host + "_" + this.redisport + "_sub_pub_" +this.db;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSign() {
		return sign;
	}

}
