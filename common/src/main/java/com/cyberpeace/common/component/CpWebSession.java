package com.cyberpeace.common.component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * cp web会话
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class CpWebSession {

	private WebSocketSession session;

	private ReentrantLock lock;

	/**
	 * cp web会话
	 *
	 * @param session 会话
	 */
	public CpWebSession(WebSocketSession session) {
		this.session = session;
		lock = new ReentrantLock();
	}

	/**
	 * 发送消息
	 *
	 * @param message 消息
	 * @throws IOException ioexception
	 */
	public void sendMessage(TextMessage message) throws IOException {
		try {
			lock.lock();
			session.sendMessage(message);
		} catch (IOException e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 获得会话
	 *
	 * @return {@link WebSocketSession}
	 */
	public WebSocketSession getSession() {
		return session;
	}

	/**
	 * 设置会话
	 *
	 * @param session 会话
	 */
	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	/**
	 * 得到属性
	 *
	 * @return {@link Map}<{@link String}, {@link Object}>
	 */
	public Map<String, Object> getAttributes() {
		return this.session.getAttributes();
	}

	/**
	 * 得到id
	 *
	 * @return {@link String}
	 */
	public String getId() {
		return this.session.getId();
	}

	/**
	 * 是否开启
	 *
	 * @return boolean
	 */
	public boolean isOpen() {
		return session.isOpen();
	}

	/**
	 * 关闭
	 *
	 * @throws IOException ioexception
	 */
	public void close() throws IOException {
		session.close();
	}

	/**
	 *  比较大小
	 *
	 * @param obj obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CpWebSession)) {
			return false;
		}

		CpWebSession other = (CpWebSession) obj;
		return session.equals(other);
	}

	/**
	 * 散列码
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {
		return session.hashCode();
	}
}
