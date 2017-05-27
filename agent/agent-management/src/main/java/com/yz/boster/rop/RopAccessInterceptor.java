package com.yz.boster.rop;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rop.AbstractInterceptor;
import com.rop.RopRequestContext;
import com.rop.session.Session;

/**
 * <pre>
 *    该拦截器仅对method为“user.add”进行拦截，你可以在{@link #isMatch(com.rop.RopRequestContext)}方法中定义拦截器的匹配规则。
 *  你可以通过{@link com.rop.RopRequestContext#getServiceMethodDefinition()}获取服务方法的注解信息，通过这些信息进行拦截匹配规则
 *  定义。
 * </pre>
 */

@Component
public class RopAccessInterceptor extends AbstractInterceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(RopAccessInterceptor.class);

	/**
	 * 在数据绑定后，服务方法调用前执行该拦截方法
	 *
	 * @param ropRequestContext
	 */
	public void beforeService(RopRequestContext ropRequestContext) {
		LOGGER.debug("before repRequest:" + ropRequestContext);
		// if match the rule,check session exist
		if (isMatch(ropRequestContext)) {
			Session session = ropRequestContext.getSession();
			if (session == null) {
				// return notify to login
				RopResponse ropResponse = new RopResponse();
				ropResponse.setCode(RopResponseCodes.USER_NOT_LOGIN);
				ropResponse.setMessage("Not Login");
				ropRequestContext.setRopResponse(ropResponse);
			} else {
				// Get subject to check permission from ROP session
				Subject user = (Subject) ropRequestContext.getSession().getAttribute(ropRequestContext.getSessionId());
				try {
					user.checkPermission(ropRequestContext.getMethod());
				} catch (AuthorizationException e) {
					RopResponse ropResponse = new RopResponse();
					ropResponse.setCode(RopResponseCodes.NO_PERMISSION);
					ropResponse.setMessage("No permission");
					ropRequestContext.setRopResponse(ropResponse);
				}
			}
		}
	}

	/**
	 * 在服务执行完成后，响应返回前执行该拦截方法
	 *
	 * @param ropRequestContext
	 */
	public void beforeResponse(RopRequestContext ropRequestContext) {
		// do something before send response
	}

	/**
	 * 对method为user.add的方法进行拦截，你可以通过methodContext中的信息制定拦截方案
	 *
	 * @param ropRequestContext
	 * @return
	 */
	public boolean isMatch(RopRequestContext ropRequestContext) {
		String method = ropRequestContext.getMethod();
		// if is a login request do not checking if session exist
		if ("agent.login".equals(method))
			return false;
		if ("agent.notExist".equals(method))
			return false;
		if ("agent.regist".equals(method))
			return false;
		return true;
	}
}
