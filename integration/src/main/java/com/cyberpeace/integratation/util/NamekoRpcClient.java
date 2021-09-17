package com.cyberpeace.integratation.util;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.util.ConfigUtil;
import com.rabbitmq.client.*;

public class NamekoRpcClient {

	private static String EXCHANGE_NAME = "nameko-rpc";

	private static volatile NamekoRpcClient instance;

	private static ConnectionFactory factory;

	private NamekoRpcClient() {}

	private static void init(RabbitmqConnectionData data) {
		factory = new ConnectionFactory();
		factory.setHost(data.getHost());
		factory.setPort(data.getPort());
		factory.setUsername(data.getUser());
		factory.setPassword(data.getPwd());
		if (null != data.getExchangeName() && !"".equals(data.getExchangeName())) {
			EXCHANGE_NAME = data.getExchangeName();
		}

		try {} catch (Exception e) {
			e.printStackTrace();
		}

		instance = new NamekoRpcClient();
	}

	public static NamekoRpcClient getInstance(RabbitmqConnectionData data) {
		if (null == instance) {
			synchronized (NamekoRpcClient.class) {
				if (null == instance) {
					init(data);
				}
			}
		}

		String exchange = ConfigUtil.getContextProperty("rabbitmq.exchange.name");
		if (StringUtils.isNotBlank(exchange)) {
			EXCHANGE_NAME = exchange;
		}

		return instance;
	}

	public String call(String routingKey, String args)
			throws IOException, InterruptedException, TimeoutException {
		Connection connection = null;
		Channel channel = null;
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
			String replyQueueName = channel.queueDeclare().getQueue();
			channel.queueBind(replyQueueName, EXCHANGE_NAME, replyQueueName);

			String corrId = UUID.randomUUID().toString();

			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId)
					.contentType("application/json").contentEncoding("utf-8")
					.replyTo(replyQueueName).build();

			channel.basicPublish(EXCHANGE_NAME, routingKey, props, args.getBytes("UTF-8"));

			final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

			channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {

				@Override
				public void handleDelivery(String consumerTag, Envelope envelope,
						AMQP.BasicProperties properties, byte[] body) throws IOException {
					if (properties.getCorrelationId().equals(corrId)) {
						String resp = new String(body, "UTF-8");
						response.offer(resp);
					}
				}
			});

			String result = response.poll(30, TimeUnit.SECONDS);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != channel) {
				channel.close();
			}

			if (null != connection) {
				connection.close();
			}
		}

		return null;
	}

}
