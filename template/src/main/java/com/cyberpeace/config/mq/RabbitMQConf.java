package com.cyberpeace.config.mq;

import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cs.service.impl.AuthMessageListener;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.service.impl.LogConfigMessageListener;
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

@Configuration
public class RabbitMQConf {

    /**
     * 对于mp而言，这个值是不变的
     */
    private final String EXCHANGE = "cn.cyberpeace.mp.cscene";

    /**
     * mp用户组织删除的交换机
     */
    private final String AUTH_EXCHANGE = "cn.cyberpeace.mp.auth";

    /**
     * 日志配置的交换机
     */
    private final String LOG_CONFIG_EXCHANGE = "cn.cyberpeace.mp.sysconfig";

    /**
     * 日志配置路由
     */
    private final String LOG_CONFIG_ROUTING_KEY = "cn.cyberpeace.mp.sysconfiglog_max_history.update";

    /**
     * 用户路由
     */
    private final String AUTH_USER_ROUTING_KEY = "cn.cyberpeace.mp.auth.*.delete";

    /**
     * 用户路由
     */
    private final String AUTH_ROLE_ROUTING_KEY = "cn.cyberpeace.mp.auth.#.modify";

    /**
     * 场景状态数据路由
     */
    private final String SCENE_STATUS_ROUTING_KEY = "cn.cyberpeace.mp.cscene_report.*";

    /**
     * 场景事件变化路由。
     * <p>
     * 目前包括场景、以及节点对事件变化。
     */
    private final String SCENE_EVENT_ROUTING_KEY = "cn.cyberpeace.mp.cscene_scene_report.*";

    private String defaultProjectName = "defaultTest1";

    private String queuePrefix = "cr.";

    private List<String> queues = Lists.newArrayList();

    private List<String> authQueues = Lists.newArrayList();

    private List<String> logConfigQueues = Lists.newArrayList();

    @Autowired
    @Qualifier("messageListener")
    private MessageListener messageListner;

    @Autowired
    @Qualifier("authMessageListener")
    private AuthMessageListener authMessageListner;

    @Autowired
    @Qualifier("logConfigMessageListener")
    private LogConfigMessageListener logConfigMessageListener;

    @Bean
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    public Exchange topicAuthExchange() {
        return ExchangeBuilder.topicExchange(AUTH_EXCHANGE).durable(true).build();
    }

    @Bean
    public Exchange topicLogConfigExchange() {
        return ExchangeBuilder.topicExchange(LOG_CONFIG_EXCHANGE).durable(true).build();
    }

    /**
     * 场景状态变更事件。
     * <p>
     * 目前只处理场景删除。
     *
     * @return
     */
    @Bean
    public Queue sceneStatusQueue() {
        String sceneStatusQueue = queuePrefix + getProjectName() + ".scene.status";

        queues.add(sceneStatusQueue);

        return QueueBuilder.durable(sceneStatusQueue).build();
    }

    /**
     * 场景运行过程中的动态消息。
     * <p>
     * 如场景启动、删除涉及到的ws消息。
     *
     * @return
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
     */
    @Bean
    public Queue AuthDeleteMessageQueue() {
        String sceneMessageQueue = queuePrefix + getProjectName() + ".auth.message";

        authQueues.add(sceneMessageQueue);

        return QueueBuilder.nonDurable(sceneMessageQueue).build();
    }

    @Bean
    public Queue logConfigMessageQueue() {
        String logConfigMessageQueue = queuePrefix + getProjectName() + ".log.config.message";

        logConfigQueues.add(logConfigMessageQueue);

        return QueueBuilder.nonDurable(logConfigMessageQueue).build();
    }

    @Bean
    Binding bindingSceneStatus() {
        return BindingBuilder.bind(sceneStatusQueue()).to(topicExchange())
                .with(SCENE_STATUS_ROUTING_KEY).noargs();
    }

    @Bean
    Binding bindingSceneMessage() {
        return BindingBuilder.bind(sceneMessageQueue()).to(topicExchange())
                .with(SCENE_EVENT_ROUTING_KEY).noargs();
    }

    @Bean
    Binding bindingUserMessage() {
        return BindingBuilder.bind(AuthDeleteMessageQueue()).to(topicAuthExchange())
                .with(AUTH_USER_ROUTING_KEY).noargs();
    }

    @Bean
    Binding bindingRoleMessage() {
        return BindingBuilder.bind(AuthDeleteMessageQueue()).to(topicAuthExchange())
                .with(AUTH_ROLE_ROUTING_KEY).noargs();
    }

    @Bean
    Binding bindingLogConfigMessage() {
        return BindingBuilder.bind(logConfigMessageQueue()).to(topicLogConfigExchange())
                .with(LOG_CONFIG_ROUTING_KEY).noargs();
    }

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

    @Bean
    public SimpleMessageListenerContainer
    simpleLogConfigMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(
                connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        container.setQueueNames(getQueues(logConfigQueues));
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setExposeListenerChannel(true);
        container.setMessageListener(logConfigMessageListener);

        return container;
    }

    public String getDeleteQueryName() {
        return queuePrefix + getProjectName() + ".scene.element";
    }

    private String getProjectName() {
        String projectName = SystemPropUtil.getProp("PROJECT_NAME");

        if (StringUtils.isBlank(projectName)) {
            projectName = defaultProjectName;
        }

        return projectName;
    }

    private String[] getQueues(List<String> queues) {
        String[] result = new String[queues.size()];
        for (int i = 0; i < queues.size(); ) {
            result[i] = queues.get(i++);
        }

        return result;
    }
}
