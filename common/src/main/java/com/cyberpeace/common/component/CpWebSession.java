package com.cyberpeace.common.component;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CP Web 会话
 *
 * @author SN
 * @date 2024/11/08
 */
public class CpWebSession {

	/**
	 * 会期
	 */
	private WebSocketSession session;

	/**
	 * 锁
	 */
	private ReentrantLock lock;

	/**
	 * CP Web 会话
	 *
	 * @param session 会期
	 */
	public CpWebSession(WebSocketSession session) {
		this.session = session;
		lock = new ReentrantLock();
	}

	/**
	 * 发送消息
	 *
	 * @param message 消息
	 * @throws IOException io异常
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
	 * 获取会期
	 *
	 * @return {@code WebSocketSession }
	 */
	public WebSocketSession getSession() {
		return session;
	}

	/**
	 * 设置会期
	 *
	 * @param session 会期
	 */
	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	/**
	 * 获取属性
	 *
	 * @return {@code Map<String, Object> }
	 */
	public Map<String, Object> getAttributes() {
		return this.session.getAttributes();
	}

	/**
	 * 获取id
	 *
	 * @return {@code String }
	 */
	public String getId() {
		return this.session.getId();
	}

	/**
	 * 是开启
	 *
	 * @return boolean
	 */
	public boolean isOpen() {
		return session.isOpen();
	}

	/**
	 * 关闭
	 *
	 * @throws IOException io异常
	 */
	public void close() throws IOException {
		session.close();
	}

	/**
	 * 等于
	 *
	 * @param obj 对象
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
	 * 散 列代号
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {
		return session.hashCode();
	}
}
