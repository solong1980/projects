package com.yz.boster.rop;

import io.renren.entity.UserEntity;

import org.apache.shiro.subject.Subject;

import com.rop.AbstractRopRequest;
import com.rop.RopRequestContext;
import com.rop.session.Session;
import com.rop.session.SimpleSession;

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

	protected UserEntity getCurrentUser(AbstractRopRequest ropRequest) {
		Session session = ropRequest.getRopRequestContext().getSession();
		if (session == null)
			return null;
		UserEntity user = (UserEntity) session.getAttribute(SESSION_USER);
		return user;
	}

	protected Long getCurrentUserId(AbstractRopRequest ropRequest) {
		UserEntity user = getCurrentUser(ropRequest);
		if (user != null) {
			return user.getUserId();
		} else {
			return null;
		}
	}
}
