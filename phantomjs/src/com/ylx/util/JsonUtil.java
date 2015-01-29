package com.ylx.util;

import java.util.Map;

import com.google.gson.Gson;

public class JsonUtil {
	public static String toJson(Map<String,String> columns) {

			Gson gson=new Gson();
			String json=gson.toJson(columns);
			return json;
	}
}
