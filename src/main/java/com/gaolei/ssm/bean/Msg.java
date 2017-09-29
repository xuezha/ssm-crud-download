package com.gaolei.ssm.bean;

import java.util.HashMap;
import java.util.Map;

public class Msg {
	private int code;
	private String msg;
	private Map<String, Object>map=new HashMap<>();
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public static Msg success(){
		Msg res=new Msg();
		res.setCode(100);
		res.setMsg("success!");
		return res;
	}
	public static Msg fail(){
		Msg res=new Msg();
		res.setCode(200);
		res.setMsg("fail!");
		return res;
	}
	public Msg add(String key,Object value){
		this.map.put(key, value);
		return this;
	}
}
