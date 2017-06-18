package com.yz.boster.rop.service;

import io.renren.entity.SysUserEntity;
import io.renren.entity.UserEntity;
import io.renren.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rop.RopRequestContext;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.yz.boster.commons.utils.InvitionCodeUtil;
import com.yz.boster.rop.BaseRopRequest;
import com.yz.boster.rop.BaseRopService;
import com.yz.boster.rop.RopResponse;
import com.yz.boster.rop.RopResponseCodes;
import com.yz.boster.rop.request.AgentInfoRequest;
import com.yz.boster.rop.request.AgentRegistRequest;
import com.yz.boster.rop.request.LoginRequest;

@ServiceMethodBean
public class AgentService extends BaseRopService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AgentService.class);

	@Autowired
	private UserService userService;

	@ServiceMethod(method = "agent.login", version = VERSION_1, needInSession = NeedInSessionType.NO)
	public Object login(LoginRequest loginRequest) {
		LOGGER.info("POST请求登录");
		String username = loginRequest.getUserName();
		String password = loginRequest.getPassword();
		int rememberMe = loginRequest.getRememberMe();
		// 改为全部抛出异常，避免ajax csrf token被刷新
		if (StringUtils.isEmpty(username)) {
			throw new RuntimeException("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)) {
			throw new RuntimeException("密码不能为空");
		}
		// if (StringUtils.isBlank(captcha)) {
		// throw new RuntimeException("验证码不能为空");
		// }
		// if (!dreamCaptcha.validate(request, response, captcha)) {
		// throw new RuntimeException("验证码错误");
		// }
		Subject user = SecurityUtils.getSubject();
		password = new Sha256Hash(password).toHex();
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		// 设置记住密码
		token.setRememberMe(1 == rememberMe);

		RopResponse ropResponse = new RopResponse();

		try {
			user.login(token);

			RopRequestContext ropRequestContext = loginRequest
					.getRopRequestContext();
			putSession(user, ropRequestContext);
			SysUserEntity principal = (SysUserEntity) user.getPrincipal();
			ropResponse.setSessionId(ropRequestContext.getSessionId());
			ropResponse.setModelVo(principal);

			return ropResponse;
		} catch (UnknownAccountException e) {
			LOGGER.error("账号不存在！", e);
			ropResponse.setCode(RopResponseCodes.LOGIN_USER_NOT_EXIST);
			ropResponse.setMessage("账号不存在！");
			return ropResponse;
		} catch (DisabledAccountException e) {
			LOGGER.error("账号未启用！", e);
			ropResponse.setCode(RopResponseCodes.LOGIN_USER_NOT_ACTIVE);
			ropResponse.setMessage("账号未启用！");
			return ropResponse;
		} catch (IncorrectCredentialsException e) {
			LOGGER.error("密码错误！", e);
			ropResponse.setCode(RopResponseCodes.LOGIN_PASSWORD_ERROR);
			ropResponse.setMessage("密码错误！");
			return ropResponse;
		} catch (Throwable e) {
			LOGGER.error("Login Error!", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@ServiceMethod(method = "agent.notExist", version = VERSION_1, needInSession = NeedInSessionType.NO)
	public Object notExist(AgentRegistRequest agentRegistRequest) {
		RopResponse ropResponse = new RopResponse();

		String loginName = agentRegistRequest.getLoginName();
		if (null == loginName) {
			ropResponse.setCode(RopResponseCodes.PARAMETER_ERROR);
			ropResponse.setMessage("请输入帐号！");
			return ropResponse;
		}
		UserEntity userByName = userService.queryByAccount(loginName);

		if (userByName != null) {
			ropResponse.setCode(RopResponseCodes.ACCOUNT_EXIST);
			ropResponse.setMessage("帐号已存在！");
			return ropResponse;
		} else {
			ropResponse.setCode(RopResponseCodes.SUCCESS);
			return ropResponse;
		}
	}

	@ServiceMethod(method = "agent.regist", version = VERSION_1, needInSession = NeedInSessionType.NO)
	@Transactional
	public Object regiest(AgentRegistRequest agentRegistRequest) {
		UserEntity userByName = userService.queryByAccount(agentRegistRequest
				.getLoginName());
		RopResponse ropResponse = new RopResponse();
		if (userByName != null) {
			ropResponse.setCode(RopResponseCodes.ACCOUNT_EXIST);
			ropResponse.setMessage("帐号已存在！");
			return ropResponse;
		}
		UserEntity user = agentRegistRequest.getUser();
		try {
			userService.saveUser(user);
			ropResponse.setCode(RopResponseCodes.SUCCESS);
			ropResponse.setModelVo(user);
		} catch (Throwable t) {
			ropResponse.setCode(RopResponseCodes.ADD_USER_ERROR);
			ropResponse.setMessage("添加你的帐号时出现错误！");
		}
		return ropResponse;

	}

	@ServiceMethod(method = "agent.info", version = VERSION_1, needInSession = NeedInSessionType.YES)
	public Object info(AgentInfoRequest agentInfoRequest) {
		Long userId = agentInfoRequest.getUserId();
		UserEntity user = userService.queryObject(userId);
		RopResponse ropResponse = new RopResponse();
		ropResponse.setModelVo(user);
		return ropResponse;
	}

	@ServiceMethod(method = "agent.genCode", version = VERSION_1, needInSession = NeedInSessionType.YES)
	public Object genCode(BaseRopRequest request) {
		Long userId = getCurrentUserId(request);
		RopResponse ropResponse = new RopResponse();
		ropResponse.setModelVo(InvitionCodeUtil.toSerialCode(userId));
		return ropResponse;
	}

}
