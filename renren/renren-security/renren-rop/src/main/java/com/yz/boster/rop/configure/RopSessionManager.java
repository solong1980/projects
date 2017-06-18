package com.yz.boster.rop.configure;

import com.rop.session.Session;
import com.rop.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RopSessionManager implements SessionManager {

	protected final Logger LOGGER = LoggerFactory.getLogger(RopSessionManager.class);

	private final Map<String, Session> sessionHolder = new ConcurrentHashMap<String, Session>(32, 0.75f, 8);

	public void addSession(String sessionId, Session session) {
		LOGGER.debug("add sessionId:" + sessionId);
		sessionHolder.put(sessionId, session);
	}

	public Session getSession(String sessionId) {
		return sessionHolder.get(sessionId);
	}

	public void removeSession(String sessionId) {
		LOGGER.debug("remove sessionId:" + sessionId);
		sessionHolder.remove(sessionId);
	}
}
