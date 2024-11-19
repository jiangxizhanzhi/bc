package com.cyberpeace.config.aspects;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.annoations.BehaviorRecord;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorResultEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.config.wrapper.ContentCacheRequestWrapper;
import com.cyberpeace.cs.dao.entity.BehaviorRecordEntity;
import com.cyberpeace.cs.service.BehaviorRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * 行为记录方面
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/05/21
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
@Order(10)
public class BehaviorRecordAspect {

	/**
	 * 行为记录Service
	 *
	 * @see BehaviorRecordService
	 */
	@Autowired
	private BehaviorRecordService behaviorRecordService;

	/**
	 * 记录
	 *
	 * @throws
	 */
	@Pointcut("@annotation(com.cyberpeace.common.annoations.BehaviorRecord)")
	public void record() {
		// Do nothing
	}

	/**
	 * Web日志周围
	 *
	 * @param joinPoint 加入点
	 * @return {@code Object }
	 * @throws @throws Throwable 可投掷
	 * @see ProceedingJoinPoint
	 * @see Object
	 */
	@Around(value = "record()")
	public Object webLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

		Object object = joinPoint.proceed();

		try {
			BehaviorRecord record = ((MethodSignature) joinPoint.getSignature()).getMethod()
					.getAnnotation(BehaviorRecord.class);
			saveRecord(record, request, object);
		} catch (Exception e) {
			log.error("failed to record user behavior, uri:[{}]", request.getRequestURI(), e);
		}

		return object;
	}

	/**
	 * 获取目标从请求
	 *
	 * @param request 请求
	 * @param record  记录
	 * @return {@code String }
	 * @throws @throws UnsupportedEncodingException 不支持编码异常
	 * @see HttpServletRequest
	 * @see BehaviorRecord
	 * @see String
	 */
	private String getTargetFromRequest(HttpServletRequest request, BehaviorRecord record)
			throws UnsupportedEncodingException {
		String target;
		if (request.getMethod().equals(WebConstant.GET)) {
			target = request.getParameterValues(record.target())[0];
		} else if (request.getMethod().equals(WebConstant.POST) && request.getContentType() != null
				&& request.getContentType().contains(WebConstant.JSON_TYPE)) {
			// 经过debounce filter的request可以获取请求体
			ContentCacheRequestWrapper requestWrapper = (ContentCacheRequestWrapper) ((ShiroHttpServletRequest) request)
					.getRequest();
			String requestContent = new String(requestWrapper.getBody(),
					requestWrapper.getCharacterEncoding());
			JSONObject jsonObject = JSON.parseObject(requestContent);
			target = jsonObject.getString(record.target());
		} else {
			log.error("failed to get target id, uri:[{}]", request.getRequestURI());
			target = "";
		}
		return target;
	}

	/**
	 * 保存记录
	 *
	 * @param record   记录
	 * @param request  请求
	 * @param response 响应
	 * @throws @throws Exception 例外
	 * @see BehaviorRecord
	 * @see HttpServletRequest
	 * @see Object
	 */
	private void saveRecord(BehaviorRecord record, HttpServletRequest request, Object response)
			throws Exception {
		BehaviorModuleEnum module = record.module();
		BehaviorTypeEnum type = record.action();
		String desc = getDesc(record, response);
		String userId = UserInfoContext.getUserContext().getUserId();
		String link = getLink(request);
		String target = getTargetFromRequest(request, record);

		BehaviorRecordEntity entity = new BehaviorRecordEntity();
		entity.setModule(module.getCode());
		entity.setAction(type.getCode());
		entity.setUserId(userId);
		entity.setTarget(target);
		entity.setResult(getResult(record, response));
		entity.setLink(link);
		entity.setDescription(desc);
		entity.setTime(new Date());

		behaviorRecordService.save(entity);
	}

	/**
	 * 获取描述
	 *
	 * @param record   记录
	 * @param response 响应
	 * @return {@code String }
	 * @throws
	 * @see BehaviorRecord
	 * @see Object
	 * @see String
	 */
	private String getDesc(BehaviorRecord record, Object response) {
		String user = UserInfoContext.getUserContext().getName();
		String action = record.action().getDesc();
		String module = record.module().getDesc();
		String target = getFromResponse(response, record.name(), String.class);

		String targetTemplate = "[%s]";
		String descriptionTemplate = "用户[%s] 在 %s 页面 %s%s";
		String targetStr = null == target ? "" : String.format(targetTemplate, target);
		return String.format(descriptionTemplate, user, module, action, targetStr);

	}

	/**
	 * 获取链接
	 *
	 * @param request 请求
	 * @return {@code String }
	 * @throws
	 * @see HttpServletRequest
	 * @see String
	 */
	private String getLink(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		if (StringUtils.isBlank(query)) {
			return uri;
		} else {
			return uri + "?" + query;
		}
	}

	/**
	 * 获取结果
	 *
	 * @param record   记录
	 * @param response 响应
	 * @return {@code Integer }
	 * @throws
	 * @see BehaviorRecord
	 * @see Object
	 * @see Integer
	 */
	private Integer getResult(BehaviorRecord record, Object response) {
		if (StringUtils.isNotBlank(record.result())) {
			if (record.module().equals(BehaviorModuleEnum.THEORY)) {
				Integer result = getFromResponse(response, record.result(), Integer.class);
				return result;
			} else if (record.module().equals(BehaviorModuleEnum.OPERATION)) {
				Boolean br = getFromResponse(response, record.result(), Boolean.class);
				if (Boolean.TRUE.equals(br)) {
					return BehaviorResultEnum.CORRECT.getCode();
				} else {
					return BehaviorResultEnum.WRONG.getCode();
				}
			} else {
				return BehaviorResultEnum.DEFAULT.getCode();
			}
		} else {
			return BehaviorResultEnum.DEFAULT.getCode();
		}
	}

	/**
	 * 获取从响应
	 *
	 * @param response 响应
	 * @param name     名称
	 * @param clazz    克拉兹
	 * @return {@code T }
	 * @throws
	 * @see Object
	 * @see String
	 * @see Class<T>
	 * @see T
	 */
	private <T> T getFromResponse(Object response, String name, Class<T> clazz) {
		JSONObject o = JsonUtil.java2JsonObject(response).getJSONObject("data");
		String[] fields = name.split("\\.");
		int size = fields.length;
		for (int i = 0; i < size - 1; i++) {
			if (null == o) {
				return null;
			}
			o = o.getJSONObject(fields[i]);
		}
		return o.getObject(fields[size - 1], clazz);
	}
}
