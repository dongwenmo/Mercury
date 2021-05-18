package com.cn.momo.config;

/**
 * 数据库信息
 * dongwenmo 2020-07-20
 */
public enum DBConfig {
	LOCALHOST("com.mysql.cj.jdbc.Driver","localhost:3306/mercury","root","123456"),
	DOP("com.mysql.cj.jdbc.Driver","172.17.0.82:3306/dop","root","Dareway@2018"),// dop开放平台正式库
	DOP_TEST("com.mysql.cj.jdbc.Driver","10.30.0.114:3306/dop","root","dareway2018"),// dop开放平台开发库
	STORYCHAIN("com.mysql.cj.jdbc.Driver","localhost:3306/storychain","root","123456"),
	HSDEP("oracle.jdbc.OracleDriver","jdbc:oracle:thin:@10.1.50.24:1521:ORCL","hsdep","hsdep"),// hsdep地纬数据交换平台开发库;
	HSU("oracle.jdbc.OracleDriver","jdbc:oracle:thin:@10.1.50.30:1521:ORCL","system","oracledba"),// hsu开发库
	MISP("oracle.jdbc.OracleDriver","jdbc:oracle:thin:@10.1.60.119:1521:ORCL","system","oracledba"),// 公共服务开发库
	;
	private String driver;
	private String url;
	private String user;
	private String pass;

	DBConfig(String driver, String url, String user, String pass) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pass = pass;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "DBConfig{" +
				"driver='" + driver + '\'' +
				", url='" + url + '\'' +
				", user='" + user + '\'' +
				", pass='" + pass + '\'' +
				'}';
	}
}
