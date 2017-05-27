package com.yz.boster.rop;

import org.apache.shiro.subject.Subject;

import com.rop.AbstractRopRequest;
import com.rop.RopRequestContext;
import com.rop.session.Session;
import com.rop.session.SimpleSession;
import com.yz.boster.model.User;

public abstract class BaseRopService {

	public static final String VERSION_1 = "1.0";
	public static final String SESSION_USER = "SESSION_USER";

	protected void putSession(Subject user,RopRequestContext context) {
		Session ropSession = context.getSession();
		String sessionId = user.getSession().getId().toString();
		if(ropSession==null){
			ropSession = new SimpleSession();
			ropSession.setAttribute(sessionId, user);
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
