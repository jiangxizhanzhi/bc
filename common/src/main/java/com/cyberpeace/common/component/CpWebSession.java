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
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CpWebSession` 的类，用于封装和扩展 `WebSocketSession` 的功能。以下是代码的主要分析和总结： 
 * --- 
 * ### **核心功能** 
 * 1. **封装 WebSocketSession** 
 * - 类通过持有 `WebSocketSession` 对象，提供了对 WebSocket 会话的基本操作。 
 * - 封装了 `WebSocketSession` 的常用方法（如发送消息、获取属性、判断是否打开等），并增加了额外的功能（如加锁机制）。 
 * 2. **线程安全** 
 * - 使用 `ReentrantLock` 实现了对 `WebSocketSession` 操作的线程安全性，确保在多线程环境下对 `session` 的操作不会冲突。 
 * 3. **扩展功能** 
 * - 提供了自定义的方法（如 `sendMessage` 带有锁保护的发送消息功能）以及覆盖的 `equals` 和 `hashCode` 方法，增强了类的可复用性和一致性。 
 * --- 
 * ### **主要方法解析** 
 * 1. **构造方法** 
 * - `CpWebSession(WebSocketSession session)`：接受一个 `WebSocketSession` 对象，并初始化内部锁。 
 * 2. **发送消息** 
 * - `sendMessage(TextMessage message)`：使用 `ReentrantLock` 加锁后调用 `WebSocketSession` 的 `sendMessage` 方法发送消息。 
 * 3. **获取和设置会话** 
 * - `getSession()`：返回当前的 `WebSocketSession`。 
 * - `setSession(WebSocketSession session)`：更新会话对象。 
 * 4. **其他功能** 
 * - `getAttributes()`：获取会话的属性。 
 * - `getId()`：获取会话的唯一标识符。 
 * - `isOpen()`：检查会话是否处于打开状态。 
 * - `close()`：关闭会话。 
 * 5. **重写方法** 
 * - `equals` 和 `hashCode`：基于 `WebSocketSession` 的实现，确保 `CpWebSession` 对象可以正确比较和存储。 
 * --- 
 * ### **优点** 
 * 1. **封装性**：将 `WebSocketSession` 的操作封装在一个类中，便于统一管理。 
 * 2. **线程安全**：通过锁机制保护关键操作，避免并发问题。 
 * 3. **扩展性**：支持自定义逻辑（如加锁机制），方便后续功能扩展。 
 * --- 
 * ### **潜在改进点** 
 * 1. **异常处理** 
 * - 在 `sendMessage` 方法中，`try-finally` 块虽然保证了锁的释放，但未对 `IOException` 进行详细处理或日志记录，可能需要进一步增强异常处理逻辑。 
 * 2. **锁粒度** 
 * - 当前锁的粒度较粗，可能会影响性能。如果某些操作不需要加锁，可以优化为细粒度锁。 
 * 3. **泛型支持** 
 * - `sendMessage` 方法仅支持 `TextMessage`，若需要支持其他类型的消息（如二进制消息），可以考虑泛型化。 
 * --- 
 * ### **适用场景** 
 * 该类适用于基于 WebSocket 的应用场景，特别是需要对多个客户端进行并发通信且需要保证线程安全的场景。例如： 
 * - 即时通讯系统 
 * - 实时数据推送服务 
 * 总结来说，这是一个设计合理、功能清晰的工具类，能够有效简化 WebSocket 的操作与管理。 
**/
public class CpWebSession {

	/**
	 * 会期
	 *
	 * @see WebSocketSession
	 */
	private WebSocketSession session;

	/**
	 * 锁
	 *
	 * @see ReentrantLock
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

