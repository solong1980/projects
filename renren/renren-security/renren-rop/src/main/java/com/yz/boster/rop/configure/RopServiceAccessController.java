package com.yz.boster.rop.configure;

import com.rop.security.ServiceAccessController;
import com.rop.session.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RopServiceAccessController implements ServiceAccessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RopServiceAccessController.class);

	/**
	 * 记录ACL信息 ,appKey和服务接口名称的访问关系
	 */
	private static final Map<String, List<String>> aclMap = new HashMap<String, List<String>>();
	static {
		@SuppressWarnings("unused")
		ArrayList<String> serviceMethods = new ArrayList<String>();
	}

	public boolean isAppGranted(String appKey, String method, String version) {
		if (aclMap.containsKey(appKey)) {
			LOGGER.debug("check appKey:" + appKey + ",method:" + method + " 's acl");
			List<String> serviceMethods = aclMap.get(appKey);
			return serviceMethods.contains(method);
		} else {
			return true;
		}
	}

	public boolean isUserGranted(Session session, String method, String version) {
		return true;
	}
}
