package com.cyberpeace.config.redis;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class RedisMessageListener implements MessageListener {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RbCombatLogService rbCombatLogService;

	@SneakyThrows
	@Override
	public void onMessage(Message message, byte[] pattern) {
		// 获取消息
		byte[] messageBody = message.getBody();
		// 使用值序列化器转换
		Object msg = redisTemplate.getValueSerializer()
								  .deserialize(messageBody);
		// 获取监听的频道
		byte[] channelByte = message.getChannel();
		// 使用字符串序列化器转换
		Object channel = redisTemplate.getStringSerializer()
									  .deserialize(channelByte);
		// 渠道名称转换
		String patternStr = new String(pattern);
		System.out.println("---消息内容---: " + msg);
		rbCombatLogService.handlerLog(msg.toString().replaceAll("\\\\",""));
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(mapper);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		//使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}


}
