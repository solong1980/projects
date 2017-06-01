package com.yz.boster.controller.invition;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yz.boster.commons.base.BaseController;
import com.yz.boster.commons.utils.InvitionCodeUtil;

@Controller
@RequestMapping("/invite")
public class AgentInviteController extends BaseController {

	@GetMapping("/inviteAgent")
	public String inviteAgent(ModelAndView modelAndView) {
		Long userId = getUserId();
		String genCode = genCode();
		Long time = System.currentTimeMillis();
		String genInviteURL = genInviteURL(UUID.randomUUID().toString(), time);

		// add invite recode
		modelAndView.addObject("invitionCode", genCode);
		modelAndView.addObject("inviteURL", genInviteURL);
		return "inviteAgent";
	}

	private String genInviteURL(String uuid, Long time) {
		// 存数据库
		return "/invite?rud=" + uuid + "&salt=" + time;
	}

	@GetMapping("/becomeAgent")
	public String becomeAgent(String phoneNumber, String uuid, String salt,
			Long time, Long shortCode, String invitionCode) {
		return "becomeAgent";
	}

	@GetMapping("/genCode")
	@ResponseBody
	public String genCode() {
		Long userId = getUserId();
		return InvitionCodeUtil.toSerialCode(1L);
	}
}
