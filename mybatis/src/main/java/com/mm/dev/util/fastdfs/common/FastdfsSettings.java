package com.mm.dev.util.fastdfs.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix="fastdfs")
public class FastdfsSettings {

	private int connect_timeout = 1000; //millisecond
	private int network_timeout = 3000; //millisecond
	private String charset = "utf-8";
	private int tracker_http_port = 8001;
	private boolean anti_steal_token = false;  //if anti-steal token
	private String secret_key = "FastDFS1234567890";   //generage token secret key
	@Value("${tracker_server}")
	private String tracker_server;
	public int getConnect_timeout() {
		return connect_timeout;
	}
	public void setConnect_timeout(int connect_timeout) {
		this.connect_timeout = connect_timeout;
	}
	public int getNetwork_timeout() {
		return network_timeout;
	}
	public void setNetwork_timeout(int network_timeout) {
		this.network_timeout = network_timeout;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public int getTracker_http_port() {
		return tracker_http_port;
	}
	public void setTracker_http_port(int tracker_http_port) {
		this.tracker_http_port = tracker_http_port;
	}
	
	public boolean isAnti_steal_token() {
		return anti_steal_token;
	}
	public void setAnti_steal_token(boolean anti_steal_token) {
		this.anti_steal_token = anti_steal_token;
	}
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public String getTracker_server() {
		return tracker_server;
	}
	public void setTracker_server(String tracker_server) {
		this.tracker_server = tracker_server;
	}
}
