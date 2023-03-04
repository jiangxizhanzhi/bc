//package com.cyberpeace.config.mq;
//
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.cyberpeace.common.util.SystemPropUtil;
//import com.cyberpeace.cs.service.impl.MessageListener;
//import com.google.common.collect.Lists;
//
//@Configuration
//public class RabbitMQConf {
//
//	/**
//	 * 对于mp而言，这个值是不变的
//	 */
//	private final String EXCHANGE = "cn.cyberpeace.mp.cscene";
//
//	/**
//	 * 场景状态数据路由
//	 */
//	private final String SCENE_STATUS_ROUTING_KEY = "cn.cyberpeace.mp.cscene_report.*";
//
//	/**
//	 * 场景事件变化路由。
//	 *
//	 * 目前包括场景、以及节点对事件变化。
//	 */
//	private final String SCENE_EVENT_ROUTING_KEY = "cn.cyberpeace.mp.cscene_scene_report.*";
//
//	private String defaultProjectName = "defaultTest1";
//
//	private String queuePrefix = "cr.";
//
//	private List<String> queues = Lists.newArrayList();
//
//	@Autowired
//	@Qualifier("messageListener")
//	private MessageListener messageListner;
//
//	@Bean
//	public Exchange topicExchange() {
//		return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
//	}
//
//	/**
//	 * 场景状态变更事件。
//	 *
//	 * 目前只处理场景删除。
//	 * @return
//	 */
//	@Bean
//	public Queue sceneStatusQueue() {
//		String sceneStatusQueue = queuePrefix + getProjectName() + ".scene.status";
//
//		queues.add(sceneStatusQueue);
//
//		return QueueBuilder.durable(sceneStatusQueue).build();
//	}
//
//	/**
//	 * 场景运行过程中的动态消息。
//	 *
//	 * 如场景启动、删除涉及到的ws消息。
//	 * @return
//	 */
//	@Bean
//	public Queue sceneMessageQueue() {
//		String sceneMessageQueue = queuePrefix + getProjectName() + ".scene.message";
//
//		queues.add(sceneMessageQueue);
//
//		return QueueBuilder.nonDurable(sceneMessageQueue).build();
//	}
//
//	@Bean
//	Binding bindingSceneStatus() {
//		return BindingBuilder.bind(sceneStatusQueue()).to(topicExchange())
//				.with(SCENE_STATUS_ROUTING_KEY).noargs();
//	}
//
//	@Bean
//	Binding bindingSceneMessage() {
//		return BindingBuilder.bind(sceneMessageQueue()).to(topicExchange())
//				.with(SCENE_EVENT_ROUTING_KEY).noargs();
//	}
//
//	@Bean
//	public SimpleMessageListenerContainer
//			simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
//				connectionFactory);
//		container.setConcurrentConsumers(2);
//		container.setMaxConcurrentConsumers(2);
//		container.setQueueNames(getQueues());
//		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//		container.setExposeListenerChannel(true);
//		container.setMessageListener(messageListner);
//
//		return container;
//	}
//
//	public String getDeleteQueryName() {
//		return queuePrefix + getProjectName() + ".scene.element";
//	}
//
//	private String getProjectName() {
//		String projectName = SystemPropUtil.getProp("PROJECT_NAME");
//
//		if (StringUtils.isBlank(projectName)) {
//			projectName = defaultProjectName;
//		}
//
//		return projectName;
//	}
//
//	private String[] getQueues() {
//		String[] result = new String[queues.size()];
//		for (int i = 0; i < queues.size();) {
//			result[i] = queues.get(i++);
//		}
//
//		return result;
//	}
//}
