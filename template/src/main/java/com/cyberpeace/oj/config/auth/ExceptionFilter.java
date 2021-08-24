package com.cyberpeace.oj.config.auth;

import com.cyberpeace.oj.common.constant.ErrorCode;
import com.cyberpeace.oj.common.constant.WebConstant;
import com.cyberpeace.oj.common.exception.InvalidParamException;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.common.model.BaseRsp;
import com.cyberpeace.oj.common.model.UserInfoContext;
import com.cyberpeace.oj.common.util.ConfigUtil;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.common.util.LocalStringUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.impl.TraceIdContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ExceptionFilter implements Filter {

	private Map<String, String> cache = Maps.newHashMap();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			dealException((HttpServletRequest) servletRequest,
					(HttpServletResponse) servletResponse, e);
		} finally {
			UserInfoContext.removeUserContext();

			TraceIdContext.removeTraceId();
		}
	}

	private void dealException(HttpServletRequest servletRequest,
			HttpServletResponse servletResponse, Exception e) {
		log.error("exceptionfilter catch error", e);

		if (e instanceof NestedServletException || e instanceof ServletException) {
			// 如果拦截到的是spring封装过的异常，获取原始异常信息
			e = (Exception) e.getCause();
		}

		if (e instanceof MethodArgumentNotValidException || e instanceof BindException) {
			parseArgumentNotValidException(servletRequest, servletResponse, e);
		} else if (e instanceof UnauthorizedException) {
			parseUnauthorizedException(servletRequest, servletResponse, e);
		} else if (e instanceof ResourceNotFoundException) {
			parseResourceNotFoundException(servletResponse, (ResourceNotFoundException) e);
		} else if (e instanceof InvalidParamException) {
			parseInvalidParamException(servletResponse, (InvalidParamException) e);
		} else if (e instanceof AuthenticationException || e instanceof UnauthenticatedException) {
			parseAuthenticationException(servletResponse);
		} else if (e instanceof DuplicateKeyException) {
			parseDuplicateKeyException(servletResponse, (DuplicateKeyException) e);
		} else {
			dealDefaultError(servletResponse);
		}
	}

	/**
	 * 处理主键重复异常
	 * @param httpServletResponse
	 */
	private void parseDuplicateKeyException(HttpServletResponse httpServletResponse,
			DuplicateKeyException e) {
		String errorMsg = e.getCause().getMessage();
		String duplicateKey = getDuplicateKey(errorMsg);

		BaseRsp<String> errorInfo = new BaseRsp<>();
		errorInfo.setMessage(duplicateKey);
		errorInfo.setCode(getErrorCode(ErrorCode.DUPLICATE_KEY));

		flushErrorInfo(httpServletResponse, errorInfo);
	}

	private String getDuplicateKey(String errorMsg) {
		String duplicateKey = "";
		String rgex = "key '(.*?)'";
		Pattern pattern = Pattern.compile(rgex);
		Matcher matcher = pattern.matcher(errorMsg);
		while (matcher.find()) {
			duplicateKey = matcher.group(0);
			duplicateKey = StringUtils.substring(duplicateKey, 5, duplicateKey.length() - 1)
					.concat("不能重复");
		}
		return duplicateKey;
	}

	/**
	 * 登陆失败返回401
	 *
	 * @param httpServletResponse
	 */
	private void parseAuthenticationException(HttpServletResponse httpServletResponse) {
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	/**
	 * 处理根据resource_id找不到对应资源时的异常
	 **/
	private void parseResourceNotFoundException(HttpServletResponse httpServletResponse,
			ResourceNotFoundException e) {
		BaseRsp<String> errorInfo = new BaseRsp<>();
		errorInfo.setMessage(e.getMessage());
		errorInfo.setCode(getErrorCode(ErrorCode.RESOURCE_NOT_FOUND));
		flushErrorInfo(httpServletResponse, errorInfo);
	}

	/**
	 * 处理用户输入参数异常
	 **/
	private void parseInvalidParamException(HttpServletResponse httpServletResponse,
			InvalidParamException e) {
		BaseRsp<String> errorInfo = new BaseRsp<>();
		errorInfo.setMessage(e.getMessage());
		errorInfo.setCode(getErrorCode(ErrorCode.METHOD_ARG_NOT_VALID));
		flushErrorInfo(httpServletResponse, errorInfo);
	}

	/**
	 * 处理权限相关的异常
	 */
	private void parseUnauthorizedException(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception e) {
		// 直接返回403
		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * 参数校验异常处理
	 */
	private void parseArgumentNotValidException(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Exception e) {
		BindingResult br = null;

		if (e instanceof MethodArgumentNotValidException) {
			br = ((MethodArgumentNotValidException) e).getBindingResult();
			((MethodArgumentNotValidException) e).getBindingResult();
		} else if (e instanceof BindException) {
			br = ((BindException) e).getBindingResult();
		}

		List<FieldError> errorList = br.getFieldErrors();

		for (FieldError error : errorList) {
			String field = error.getField();
			field = LocalStringUtil.snakeCaseFormat(field);

			// 找到第一个错误的字段，直接返回。
			// 不管是否配置了快速失败
			if (StringUtils.isNoneBlank(field)) {
				BaseRsp<String> errorInfo = new BaseRsp<>();
				errorInfo.setMessage(field);
				errorInfo.setCode(getErrorCode(ErrorCode.METHOD_ARG_NOT_VALID));

				flushErrorInfo(httpServletResponse, errorInfo);

				return;
			}
		}
	}

	private void dealDefaultError(HttpServletResponse httpServletResponse) {
		BaseRsp<String> defaultError = new BaseRsp<>();
		defaultError.setCode(getErrorCode(ErrorCode.INTERNAL_ERROR));

		flushErrorInfo(httpServletResponse, defaultError);
	}

	private void flushErrorInfo(HttpServletResponse httpServletResponse, BaseRsp errorInfo) {
		httpServletResponse.setContentType(WebConstant.CONTENT_TYPE);
		httpServletResponse.setStatus(WebConstant.HTTP_200);
		try {
			httpServletResponse.getWriter().write(JsonUtil.toJsonString(errorInfo));
		} catch (Exception e) {
			log.error("flushErrorInfo error:", e);
		}
	}

	private String getErrorCode(String code) {
		if (!cache.containsKey(code)) {
			cache.put(code, ConfigUtil.getContextProperty("errorPrefix") + code);
		}

		return cache.get(code);
	}
}
