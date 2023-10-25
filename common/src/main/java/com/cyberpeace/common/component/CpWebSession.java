package com.cyberpeace.common.component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class CpWebSession {

	private WebSocketSession session;

	private ReentrantLock lock;

	public CpWebSession(WebSocketSession session) {
		this.session = session;
		lock = new ReentrantLock();
	}

	public void sendMessage(TextMessage message) throws IOException {
		try {
			lock.lock();
			session.sendMessage(message);
		}  finally {
			lock.unlock();
		}
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	public Map<String, Object> getAttributes() {
		return this.session.getAttributes();
	}

	public String getId() {
		return this.session.getId();
	}

	public boolean isOpen() {
		return session.isOpen();
	}

	public void close() throws IOException {
		session.close();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CpWebSession)) {
			return false;
		}

		CpWebSession other = (CpWebSession) obj;
		return session.equals(other);
	}

	@Override
	public int hashCode() {
		return session.hashCode();
	}
}
