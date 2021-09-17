package com.cyberpeace.integratation.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.exception.evaluate.JwtTokenInvalidException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.model.evaluation.NamekoConstant;
import com.cyberpeace.integratation.service.UserCenterService;
import com.cyberpeace.integratation.util.NamekoRpcClient;
import com.cyberpeace.integratation.util.RabbitmqConnectionData;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.model.user.UserInfo;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserCenterServiceImpl implements UserCenterService {

	private static final String SERVICE_NAME = "sv_auth.";

	private NamekoRpcClient client;

	private String VALUE = "value";

	private String GLOBAL = "__global";

	private String ERROR_MESSAGE = "message";

	private String USER = "user";

	private LoadingCache<String, String> cache;

	@PostConstruct
	public void init() {
		String rabbitmqHost = ConfigUtil.getContextProperty("rabbitmq.host");
		int rabbitmqPort = Integer.parseInt(ConfigUtil.getContextProperty("rabbitmq.port"));
		String exchangeName = ConfigUtil.getContextProperty("rabbitmq.exchange.name");
		String user = ConfigUtil.getContextProperty("rabbitmq.user");
		String pwd = ConfigUtil.getContextProperty("rabbitmq.pwd");

		RabbitmqConnectionData data = RabbitmqConnectionData.builder().exchangeName(exchangeName)
				.host(rabbitmqHost).port(rabbitmqPort).user(user).pwd(pwd).build();

		client = NamekoRpcClient.getInstance(data);

		cache = CacheBuilder.newBuilder()
				// 设置并发级别为8，并发级别是指可以同时写缓存的线程数
				.concurrencyLevel(8)
				// 设置缓存容器的初始容量为10
				.initialCapacity(300)
				// 设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
				.maximumSize(300)
				// 设置写缓存后n秒钟过期
				.expireAfterWrite(60, TimeUnit.SECONDS)
				// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
				.build(new CacheLoader<String, String>() {

					@Override
					public String load(String key) throws Exception {
						validateToken(key);

						return key;
					}
				});
	}

	@Override
	public Boolean validateToken(String jwtToken) {
//		try {
//			cache.get(jwtToken);
//		} catch (ExecutionException e) {
//			throw new JwtTokenInvalidException();
//		}

		String paramString = JSON.toJSONString(getParams(jwtToken));

		String routingKey = SERVICE_NAME + "authenticate_token";

		long startTime = System.currentTimeMillis();

		String result = null;
		try {
			result = client.call(routingKey, paramString);
		} catch (Exception e) {
			long timeCost = System.currentTimeMillis() - startTime;
			log.error("call routingKey=[{}], param=[{}], time=[{}] error:",
					new Object[] { routingKey, paramString, timeCost }, e);
		}

		long timeCost = System.currentTimeMillis() - startTime;
		log.info("call routingKey=[{}], param=[{}], result=[{}], time=[{}]",
				new Object[] { routingKey, paramString, result, timeCost });

		JSONObject jsonObject = JSON.parseObject(result);

		JSONObject error = jsonObject.getJSONObject(NamekoConstant.ERROR);

		if (null == error) {
			JSONObject resultObject = jsonObject.getJSONObject("result");
			if (null != resultObject) {
				JSONObject userObject = resultObject.getJSONObject(USER);
				UserInfo userInfo = userObject.toJavaObject(UserInfo.class);
				UserInfoContext.setUserContext(userInfo);
			}

			return true;
		}

		String errorMessage = null;
		JSONObject errorValue = error.getJSONObject(VALUE);
		if (null != errorValue) {
			JSONObject errorGlobal = errorValue.getJSONObject(GLOBAL);
			if (null != errorGlobal) {
				errorMessage = errorGlobal.getString(ERROR_MESSAGE);
			}
		}

		throw new JwtTokenInvalidException(errorMessage);
	}

	private static Map<String, Object> getParams(String jwtToken) {
		Map<String, Object> params = Maps.newHashMap();

		params.put("args", Arrays.asList(jwtToken));
		params.put("kwargs", new HashMap());

		return params;
	}

}