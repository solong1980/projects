package com.yz.boster.rop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.rop.AbstractRopRequest;
import com.rop.RopRequestContext;
import com.rop.session.Session;
import com.rop.session.SimpleSession;
import com.yz.boster.model.User;

public abstract class BaseRopService {

	public static final String VERSION_1 = "1.0";
	public static final String SESSION_USER = "SESSION_USER";

	protected void putSession(RopRequestContext context) {
		Session ropSession = context.getSession();
		if(ropSession==null){
			HttpServletRequest request = (HttpServletRequest) context.getRawRequestObject();
			HttpSession session = request.getSession();
			String sessionId = (String) session.getId();
			ropSession = new SimpleSession();
			ropSession.setAttribute(sessionId, session);
			context.addSession(sessionId, ropSession);
		}
	}

	protected User getCurrentUser(AbstractRopRequest ropRequest) {
		Session session = ropRequest.getRopRequestContext().getSession();
		if (session == null)
			return null;
		User user = (User) session.getAttribute(SESSION_USER);
		return user;
	}

	protected Long getCurrentUserId(AbstractRopRequest ropRequest) {
		User user = getCurrentUser(ropRequest);
		if (user != null) {
			return user.getId();
		} else {
			return null;
		}
	}
}
