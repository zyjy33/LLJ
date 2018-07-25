package com.ctrip.openapi.java.base;

// Http ����
public class HttpRequestProperty 
{
	private String name;
	
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public HttpRequestProperty(String name, String value) 
	{
		this.name = name;
		this.value = value;
	}
}
