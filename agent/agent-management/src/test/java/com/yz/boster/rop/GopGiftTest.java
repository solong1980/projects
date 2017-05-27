package com.yz.boster.rop;

import java.util.Map;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.rop.utils.RopUtils;

public class GopGiftTest {
	public static final String SERVER_URL = "http://localhost:8080/agent-management/router/rop";
	public static final String APP_KEY = "v00001";
	public static final String APP_SECRET = "03e4079b565ab2a47a2eff7f42ae45b8";

	private String buildGetUrl(String serverURL, Map<String, String> form) {
		StringBuilder requestUrl = new StringBuilder();
		requestUrl.append(serverURL);
		requestUrl.append("?");
		String joinChar = "";
		for (Map.Entry<String, String> entry : form.entrySet()) {
			requestUrl.append(joinChar);
			requestUrl.append(entry.getKey());
			requestUrl.append("=");
			requestUrl.append(entry.getValue());
			joinChar = "&";
		}
		return requestUrl.toString();
	}

	private void sendLoginRequest(MultiValueMap<String, String> paramValues) {
		paramValues.add("appKey", APP_KEY);
		paramValues.add("appSecret", APP_SECRET);
		paramValues.add("v", "1.0");
		paramValues.add("format", "json");
		paramValues.add("rememberMe", "1");
		String sign = RopUtils.sign(paramValues.toSingleValueMap(), APP_SECRET);
		paramValues.add("sign", sign);
		paramValues.add("password", "test");
		String buildGetUrl = buildGetUrl(SERVER_URL, paramValues.toSingleValueMap());
		System.out.println(buildGetUrl);
		String responseContent = new RestTemplate().getForObject(buildGetUrl, String.class, paramValues);
		System.out.println(responseContent);
	}

	@Test
	public void testLogin() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("userName", "admin");
		map.add("method", "agent.login");
		map.add("from", "3");
		sendLoginRequest(map);
	}

	@Test
	public void testInfo() {
		MultiValueMap<String, String> paramValues = new LinkedMultiValueMap<String, String>();
		paramValues.add("method", "agent.info");
		paramValues.add("userId", "1");
		paramValues.add("from", "3");
		paramValues.add("appKey", APP_KEY);
		paramValues.add("appSecret", APP_SECRET);
		paramValues.add("v", "1.0");
		paramValues.add("format", "json");
		paramValues.add("sessionId", "83db8fcc-3cfb-401d-90cf-847dcd10d884");
		String sign = RopUtils.sign(paramValues.toSingleValueMap(), APP_SECRET);
		paramValues.add("sign", sign);
		
		String buildGetUrl = buildGetUrl(SERVER_URL, paramValues.toSingleValueMap());
		System.out.println(buildGetUrl);
		String responseContent = new RestTemplate().getForObject(buildGetUrl, String.class, paramValues);
		System.out.println(responseContent);
	}

	@Test
	public void testNotExist() {
		MultiValueMap<String, String> paramValues = new LinkedMultiValueMap<String, String>();
		paramValues.add("method", "agent.notExist");
		paramValues.add("loginName", "longlh");
		paramValues.add("userName", "longlh");
		paramValues.add("sex", "0");
		paramValues.add("age", "0");
		paramValues.add("password", "");
		paramValues.add("organization", "");
		
		paramValues.add("from", "3");
		paramValues.add("appKey", APP_KEY);
		paramValues.add("appSecret", APP_SECRET);
		paramValues.add("v", "1.0");
		paramValues.add("format", "json");
		String sign = RopUtils.sign(paramValues.toSingleValueMap(), APP_SECRET);
		paramValues.add("sign", sign);
		
		String buildGetUrl = buildGetUrl(SERVER_URL, paramValues.toSingleValueMap());
		System.out.println(buildGetUrl);
		String responseContent = new RestTemplate().getForObject(buildGetUrl, String.class, paramValues);
		System.out.println(responseContent);
	}
	
	@Test
	public void testRegist() {
		MultiValueMap<String, String> paramValues = new LinkedMultiValueMap<String, String>();
		paramValues.add("method", "agent.regist");
		paramValues.add("loginName", "longlh");
		paramValues.add("userName", "longlh");
		paramValues.add("sex", "1");
		paramValues.add("age", "30");
		paramValues.add("password", "123456");
		paramValues.add("organization", "longlh");
		                 
		paramValues.add("from", "3");
		paramValues.add("appKey", APP_KEY);
		paramValues.add("appSecret", APP_SECRET);
		paramValues.add("v", "1.0");
		paramValues.add("format", "json");
		String sign = RopUtils.sign(paramValues.toSingleValueMap(), APP_SECRET);
		paramValues.add("sign", sign);
		
		String buildGetUrl = buildGetUrl(SERVER_URL, paramValues.toSingleValueMap());
		System.out.println(buildGetUrl);
		String responseContent = new RestTemplate().getForObject(buildGetUrl, String.class, paramValues);
		System.out.println(responseContent);
	}
}
