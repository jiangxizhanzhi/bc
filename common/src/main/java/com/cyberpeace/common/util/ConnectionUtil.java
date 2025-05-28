/**
 * 该代码是一个用于建立与 RabbitMQ 消息队列服务连接的工具类 `ConnectionUtil`。以下是代码的简要分析： 
 * 1. **功能概述**： 
 * - 提供了一个静态方法 `getConnection()`，用于创建并返回与 RabbitMQ 的连接对象。 
 * 2. **实现细节**： 
 * - 使用了 RabbitMQ 官方提供的 Java 客户端库 (`com.rabbitmq.client`)。 
 * - 创建了一个 `ConnectionFactory` 对象来配置连接参数。 
 * - 从系统属性中读取 RabbitMQ 的连接信息（如主机名、端口、用户名、密码和虚拟主机），使用 `SystemPropUtil` 工具类获取这些值。 
 * - 设置了 RabbitMQ 的虚拟主机为根路径 `/`。 
 * - 调用 `factory.newConnection()` 方法创建实际的连接，并将其返回。 
 * 3. **潜在问题**： 
 * - 如果系统属性未正确配置，可能会抛出异常。 
 * - 连接未提供关闭机制，可能导致资源泄漏，建议在使用后手动关闭连接。 
 * 4. **适用场景**： 
 * - 适用于需要频繁访问 RabbitMQ 的应用场景，封装了连接逻辑以便复用。 
 * 总结：该代码实现了 RabbitMQ 连接的基本功能，但需注意连接管理以避免资源浪费。 
**/
package com.cyberpeace.common.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	/**
	 * 建立与RabbitMQ的连接
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		//定义连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置服务地址
		factory.setHost(SystemPropUtil.getProp("MQ.HOST"));
		//端口
		factory.setPort(Integer.parseInt(SystemPropUtil.getProp("MQ.PORT")));
		//设置账号信息，用户名、密码、vhost
		factory.setVirtualHost("/");//设置虚拟机，一个mq服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
		factory.setUsername(SystemPropUtil.getProp("MQ.USERNAME"));
		factory.setPassword(SystemPropUtil.getProp("MQ.PASSWORD"));
		// 通过工厂获取连接
		Connection connection = factory.newConnection();
		return connection;
	}
}
