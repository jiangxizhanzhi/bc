package com.cyberpeace.common.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 连接实用程序
 *
 * @author SN
 * @date 2024/11/07
 */
public class ConnectionUtil {
	/**
	 * 建立与RabbitMQ的连接
	 *
	 * @return
	 * @throws Exception 例外
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
