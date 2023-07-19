package com.cyberpeace.config.auth;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.*;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.impl.TraceIdContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 异常过滤器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Slf4j
public class ExceptionFilter implements Filter {

	private Map<String, String> cache = Maps.newHashMap();

	private String errorPrefix = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.errorPrefix = ConfigUtil.getContextProperty("errorPrefix");
	}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			dealException((HttpServletResponse) servletResponse, e);
		} finally {
			UserInfoContext.removeUserContext();

			TraceIdContext.removeTraceId();

			CommonContext.removeLanguage();
			CommonContext.removeIp();
			CommonContext.removeUrl();
			CommonContext.removeAuthorization();
		}
	}

	/**
	 * 处理异常
	 *
	 * @param servletResponse servlet响应
	 * @param e               e
	 */
	private void dealException(HttpServletResponse servletResponse, Exception e) {
		log.error("exceptionfilter catch error", e);

		if (e instanceof ServletException) {
			// 如果拦截到的是spring封装过的异常，获取原始异常信息
			e = (Exception) e.getCause();
		}

		// 前端输入反序列化错误
		if (e instanceof HttpMessageNotReadableException) {
			e = (Exception) e.getCause().getCause();
		}

		if (e instanceof BindException) {
			parseArgumentNotValidException(servletResponse, (BindException) e);
		} else if (e instanceof UnauthorizedException) {
			parseUnauthorizedException(servletResponse);
		} else if (e instanceof ResourceNotFoundException) {
			parseResourceNotFoundException(servletResponse, (ResourceNotFoundException) e);
		} else if (e instanceof InvalidParamException) {
			parseInvalidParamException(servletResponse, (InvalidParamException) e);
		} else if (e instanceof AuthenticationException || e instanceof UnauthenticatedException) {
			parseAuthenticationException(servletResponse);
		} else if (e instanceof DuplicateKeyException) {
			parseDuplicateKeyException(servletResponse, (DuplicateKeyException) e);
		} else if (e instanceof RemoteServiceException) {
			parseRemoteServiceException(servletResponse, (RemoteServiceException) e);
		} else if (e instanceof LcServiceException){
			parseLcServiceException(servletResponse, (LcServiceException) e);
		} else if (e instanceof BusinessException) {
			parseBusinessException(servletResponse, (BusinessException) e);
		} else {
			dealDefaultError(servletResponse);
		}
	}

	/**
	 * 处理自定义业务异常
	 * @param httpServletResponse
	 * @param e
	 */
	private void parseBusinessException(HttpServletResponse httpServletResponse,
			BusinessException e) {
		BaseRsp<String> errorInfo = new BaseRsp<>();
		errorInfo.setCode(getErrorCode(e.getMessage()));
		errorInfo.setMessage(e.getErrorMessage());
		flushErrorInfo(httpServletResponse, errorInfo);
	}

	/**
	 * 调用第三方服务错误
	 * @param httpServletResponse
	 */
	private void parseRemoteServiceException(HttpServletResponse httpServletResponse,
			RemoteServiceException e) {
		BusinessException businessException = new BusinessException(
				ErrorCode.REMOTE_SERVICE_EXCEPTION, e.getMessage());
		parseBusinessException(httpServletResponse, businessException);
	}


	/**
	 * 解析LcService异常
	 *
	 * @param httpServletResponse servlet响应
	 * @param e                   e
	 */
	private void parseLcServiceException(HttpServletResponse httpServletResponse, LcServiceException e) {
		BaseRsp<String> errorInfo = new BaseRsp<>();
		errorInfo.setCode("OJ-010000");
		errorInfo.setMessage(e.getMessage());
		flushErrorInfo(httpServletResponse, errorInfo);
	}

	/**
	 *
	 * 处理主键重复异常
	 *
	 * @param httpServletResponse http servlet响应
	 * @param e                   e
	 */
	private void parseDuplicateKeyException(HttpServletResponse httpServletResponse,
			DuplicateKeyException e) {
		String errorMsg = e.getCause().getMessage();
		String duplicateKey = getDuplicateKey(errorMsg);

		if (CommonContext.isChinese() && "resource_id".equals(duplicateKey)) {
			// 特殊处理编号情况
			duplicateKey = "编号";
		}

		String message = duplicateKey
				+ BusinessException.getMessageByLanguage(ErrorCode.DUPLICATE_KEY);
		flushErrorInfo(httpServletResponse, ErrorCode.DUPLICATE_KEY, message);
	}

	/**
	 * 获取重复键
	 *
	 * @param errorMsg 错误信息
	 * @return {@link String}
	 */
	private String getDuplicateKey(String errorMsg) {
		String duplicateKey = "";
		String rgex = "key '(.*?)'";
		Pattern pattern = Pattern.compile(rgex);
		Matcher matcher = pattern.matcher(errorMsg);
		while (matcher.find()) {
			duplicateKey = matcher.group(0);
			duplicateKey = StringUtils.substring(duplicateKey, 5, duplicateKey.length() - 1);
		}
		return duplicateKey;
	}

	/**
	 * 登陆失败返回401
	 */
	private void parseAuthenticationException(HttpServletResponse httpServletResponse) {
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	/**
	 * 处理根据resource_id找不到对应资源时的异常
	 **/
	private void parseResourceNotFoundException(HttpServletResponse httpServletResponse,
			ResourceNotFoundException e) {
		flushErrorInfo(httpServletResponse, ErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
	}

	/**
	 * 处理用户输入参数异常
	 **/
	private void parseInvalidParamException(HttpServletResponse httpServletResponse,
			InvalidParamException e) {
		flushErrorInfo(httpServletResponse, ErrorCode.METHOD_ARG_NOT_VALID, e.getMessage());
	}

	/**
	 * 处理权限相关的异常
	 */
	private void parseUnauthorizedException(HttpServletResponse httpServletResponse) {
		// 直接返回403
		BusinessException businessException = new BusinessException(
				ErrorCode.INTERFACE_UNAUTHORIZED);
		parseBusinessException(httpServletResponse, businessException);
	}

	/**
	 * 参数校验异常处理
	 */
	private void parseArgumentNotValidException(HttpServletResponse httpServletResponse,
			BindException e) {
		BindingResult br = e.getBindingResult();
		List<FieldError> errorList = br.getFieldErrors();

		for (FieldError error : errorList) {
			String field = error.getField();
			field = LocalStringUtil.snakeCaseFormat(field);

			// 找到第一个错误的字段，直接返回。
			// 不管是否配置了快速失败
			if (StringUtils.isNoneBlank(field)) {
				String desc = CommonContext.isChinese()
						? ConfigUtil.getContextProperty(error.getDefaultMessage() + ".cn")
						: ConfigUtil.getContextProperty(error.getDefaultMessage() + ".en");
				flushErrorInfo(httpServletResponse, ErrorCode.METHOD_ARG_NOT_VALID,
						StringUtils.isNotBlank(desc) ? desc : field);

				return;
			}
		}
	}

	/**
	 * 处理默认错误
	 *
	 * @param httpServletResponse http servlet响应
	 */
	private void dealDefaultError(HttpServletResponse httpServletResponse) {
		flushErrorInfo(httpServletResponse, ErrorCode.INTERNAL_ERROR,
				BusinessException.getMessageByLanguage(ErrorCode.INTERNAL_ERROR));
	}

	/**
	 * 刷新错误信息
	 *
	 * @param httpServletResponse http servlet响应
	 * @param errorInfo           错误信息
	 */
	private void flushErrorInfo(HttpServletResponse httpServletResponse, BaseRsp errorInfo) {
		httpServletResponse.setContentType(WebConstant.CONTENT_TYPE);
		httpServletResponse.setStatus(WebConstant.HTTP_200);
		try {
			httpServletResponse.getWriter().write(JsonUtil.toJsonString(errorInfo));
		} catch (Exception e) {
			log.error("flushErrorInfo error:", e);
		}
	}

	/**
	 * 刷新错误信息
	 *
	 * @param httpServletResponse http servlet响应
	 * @param code                代码
	 * @param message             消息
	 */
	private void flushErrorInfo(HttpServletResponse httpServletResponse, String code,
			String message) {
		BaseRsp<String> errorInfo = new BaseRsp<>();

		errorInfo.setMessage(message);
		errorInfo.setCode(getErrorCode(code));

		httpServletResponse.setContentType(WebConstant.CONTENT_TYPE);
		httpServletResponse.setStatus(WebConstant.HTTP_200);

		try {
			httpServletResponse.getWriter().write(JsonUtil.toJsonString(errorInfo));
		} catch (Exception e) {
			log.error("flushErrorInfo error:", e);
		}
	}

	/**
	 * 获取错误代码
	 *
	 * @param code 代码
	 * @return {@link String}
	 */
	private String getErrorCode(String code) {
		if (!cache.containsKey(code)) {
			cache.put(code, errorPrefix + code);
		}

		return cache.get(code);
	}
}
