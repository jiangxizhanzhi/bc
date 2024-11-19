package com.cyberpeace.config.mq;

import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.service.impl.AuthMessageListener;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 兔子 mqconf
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Configuration
public class RabbitMQConf {

	/**
	 * 对于mp而言，这个值是不变的
	 *
	 * @see String
	 */
	private final String EXCHANGE = "cn.cyberpeace.mp.cscene";

	/**
	 * mp用户组织删除的交换机
	 *
	 * @see String
	 */
	private final String AUTH_EXCHANGE = "cn.cyberpeace.mp.auth";

	/**
	 * 用户路由
	 *
	 * @see String
	 */
	private final String AUTH_USER_ROUTING_KEY = "cn.cyberpeace.mp.auth.*.delete";

	/**
	 * 用户路由
	 *
	 * @see String
	 */
	private final String AUTH_ROLE_ROUTING_KEY = "cn.cyberpeace.mp.auth.#.modify";

	/**
	 * 场景状态数据路由
	 *
	 * @see String
	 */
	private final String SCENE_STATUS_ROUTING_KEY = "cn.cyberpeace.mp.cscene_report.*";

	/**
	 * 场景事件变化路由。
	 * 目前包括场景、以及节点对事件变化。
	 *
	 * @see String
	 */
	private final String SCENE_EVENT_ROUTING_KEY = "cn.cyberpeace.mp.cscene_scene_report.*";

	/**
	 * 默认项目名称
	 *
	 * @see String
	 */
	private String defaultProjectName = "defaultTest1";

	/**
	 * 队列前缀
	 *
	 * @see String
	 */
	private String queuePrefix = "cr.";

	/**
	 * 队列
	 *
	 * @see List<String>
	 */
	private List<String> queues = Lists.newArrayList();

	/**
	 * 身份验证队列
	 *
	 * @see List<String>
	 */
	private List<String> authQueues = Lists.newArrayList();

	/**
	 * 消息列表
	 *
	 * @see MessageListener
	 */
	@Autowired
	@Qualifier("messageListener")
	private MessageListener messageListner;

	/**
	 * auth message listner
	 *
	 * @see AuthMessageListener
	 */
	@Autowired
	@Qualifier("authMessageListener")
	private AuthMessageListener authMessageListner;

	/**
	 * 主题交流
	 *
	 * @return {@code Exchange }
	 * @throws
	 * @see Exchange
	 */
	@Bean
	public Exchange topicExchange() {
		return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
	}

	/**
	 * 主题身份验证交换
	 *
	 * @return {@code Exchange }
	 * @throws
	 * @see Exchange
	 */
	@Bean
	public Exchange topicAuthExchange() {
		return ExchangeBuilder.topicExchange(AUTH_EXCHANGE).durable(true).build();
	}

	/**
	 * 场景状态变更事件。
	 * 目前只处理场景删除。
	 *
	 * @return
	 * @throws
	 * @see Queue
	 */
	@Bean
	public Queue sceneStatusQueue() {
		String sceneStatusQueue = queuePrefix + getProjectName() + ".scene.status";

		queues.add(sceneStatusQueue);

		return QueueBuilder.durable(sceneStatusQueue).build();
	}

	/**
	 * 场景运行过程中的动态消息。
	 * 如场景启动、删除涉及到的ws消息。
	 *
	 * @return
	 * @throws
	 * @see Queue
	 */
	@Bean
	public Queue sceneMessageQueue() {
		String sceneMessageQueue = queuePrefix + getProjectName() + ".scene.message";

		queues.add(sceneMessageQueue);

		return QueueBuilder.nonDurable(sceneMessageQueue).build();
	}

	/**
	 * 中台删除用户、组织、角色的动态消息。
	 *
	 * @return
	 * @throws
	 * @see Queue
	 */
	@Bean
	public Queue AuthDeleteMessageQueue() {
		String sceneMessageQueue = queuePrefix + getProjectName() + ".auth.message";

		authQueues.add(sceneMessageQueue);

		return QueueBuilder.nonDurable(sceneMessageQueue).build();
	}

	/**
	 * 捆绑场景状态
	 *
	 * @return {@code Binding }
	 * @throws
	 * @see Binding
	 */
	@Bean
	Binding bindingSceneStatus() {
		return BindingBuilder.bind(sceneStatusQueue()).to(topicExchange())
				.with(SCENE_STATUS_ROUTING_KEY).noargs();
	}

	/**
	 * 捆绑场景消息
	 *
	 * @return {@code Binding }
	 * @throws
	 * @see Binding
	 */
	@Bean
	Binding bindingSceneMessage() {
		return BindingBuilder.bind(sceneMessageQueue()).to(topicExchange())
				.with(SCENE_EVENT_ROUTING_KEY).noargs();
	}

	/**
	 * 绑定用户消息
	 *
	 * @return {@code Binding }
	 * @throws
	 * @see Binding
	 */
	@Bean
	Binding bindingUserMessage() {
		return BindingBuilder.bind(AuthDeleteMessageQueue()).to(topicAuthExchange())
				.with(AUTH_USER_ROUTING_KEY).noargs();
	}

	/**
	 * 捆绑岗位消息
	 *
	 * @return {@code Binding }
	 * @throws
	 * @see Binding
	 */
	@Bean
	Binding bindingRoleMessage() {
		return BindingBuilder.bind(AuthDeleteMessageQueue()).to(topicAuthExchange())
				.with(AUTH_ROLE_ROUTING_KEY).noargs();
	}

	/**
	 * 简单消息监听器容器
	 *
	 * @param connectionFactory 连接工厂
	 * @return {@code SimpleMessageListenerContainer }
	 * @throws
	 * @see ConnectionFactory
	 * @see SimpleMessageListenerContainer
	 */
	@Bean
	public SimpleMessageListenerContainer
			simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
				connectionFactory);
		container.setConcurrentConsumers(2);
		container.setMaxConcurrentConsumers(2);
		container.setQueueNames(getQueues(queues));
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setExposeListenerChannel(true);
		container.setMessageListener(messageListner);

		return container;
	}

	/**
	 * 简单认证消息监听器容器
	 *
	 * @param connectionFactory 连接工厂
	 * @return {@code SimpleMessageListenerContainer }
	 * @throws
	 * @see ConnectionFactory
	 * @see SimpleMessageListenerContainer
	 */
	@Bean
	public SimpleMessageListenerContainer
			simpleAuthMessageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
				connectionFactory);
		container.setConcurrentConsumers(1);
		container.setMaxConcurrentConsumers(1);
		container.setQueueNames(getQueues(authQueues));
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setExposeListenerChannel(true);
		container.setMessageListener(authMessageListner);

		return container;
	}

	/**
	 * 获取删除查询名称
	 *
	 * @return {@code String }
	 * @throws
	 * @see String
	 */
	public String getDeleteQueryName() {
		return queuePrefix + getProjectName() + ".scene.element";
	}

	/**
	 * 获取项目名称
	 *
	 * @return {@code String }
	 * @throws
	 * @see String
	 */
	private String getProjectName() {
		String projectName = SystemPropUtil.getProp("PROJECT_NAME");

		if (StringUtils.isBlank(projectName)) {
			projectName = defaultProjectName;
		}

		return projectName;
	}

	/**
	 * 获取队列
	 *
	 * @param queues 队列
	 * @return {@code String[] }
	 * @throws
	 * @see List<String>
	 * @see String[]
	 */
	private String[] getQueues(List<String> queues) {
		String[] result = new String[queues.size()];
		for (int i = 0; i < queues.size(); i++) {
			result[i] = queues.get(i);
		}

		return result;
	}
}
