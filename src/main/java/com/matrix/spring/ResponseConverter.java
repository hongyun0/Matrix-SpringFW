package com.matrix.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ResponseConverter {

	public Map<String, String> getSucceed() {
		Map<String, String> result = new HashMap<>();
		result.put("result", "succeed");
		return result;
	}

	public Map<String, String> getBoolean(boolean bool) {
		Map<String, String> result = new HashMap<>();
		result.put("result", String.valueOf(bool));
		return result;
	}
	
}
