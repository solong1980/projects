package com.yz.boster.rop.configure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.security.AppSecretManager;

public class RopAppSecretManager implements AppSecretManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(RopAppSecretManager.class);

	private static Map<String, String> appKeySecretMap = new HashMap<String, String>();

	static {
		try {
			String url = RopAppSecretManager.class.getClassLoader().getResource("config/rop.properties").getPath();
			PropertiesConfiguration configuration = new PropertiesConfiguration(url);
			Iterator<String> keys = configuration.getKeys("v");
			while (keys.hasNext()) {
				String appKey = keys.next();
				String securityKey = configuration.getString(appKey);
				LOGGER.info("ropappsecretmanager put appKey:" + appKey + ",securityKey:" + securityKey);
				appKeySecretMap.put(appKey, securityKey);
			}
			appKeySecretMap.put("v00001","03e4079b565ab2a47a2eff7f42ae45b8");
			
		} catch (ConfigurationException e) {
			LOGGER.error("init rop app/security key failure", e);
			e.printStackTrace();
		}
	}

	public String getSecret(String appKey) {
		LOGGER.debug("use appKey:" + appKey);
		return appKeySecretMap.get(appKey);
	}

	public boolean isValidAppKey(String appKey) {
		return getSecret(appKey) != null;
	}
}
