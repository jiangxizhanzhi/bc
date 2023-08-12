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
		factory.setHost(SystemPropUtil.getProp("MQ_HOST"));
		//端口
		factory.setPort(Integer.parseInt(SystemPropUtil.getProp("MQ_PORT")));
		//设置账号信息，用户名、密码、vhost
		factory.setVirtualHost("/");//设置虚拟机，一个mq服务可以设置多个虚拟机，每个虚拟机就相当于一个独立的mq
		factory.setUsername(SystemPropUtil.getProp("MQ_USERNAME"));
		factory.setPassword(SystemPropUtil.getProp("MQ_SK"));
		// 通过工厂获取连接
		Connection connection = factory.newConnection();
		return connection;
	}
}
