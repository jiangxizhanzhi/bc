package com.cyberpeace.config.auth;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.common.constant.FiledExceptionMsg;
import com.cyberpeace.common.util.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.impl.TraceIdContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.I18nException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionFilter implements Filter {

	private String errorPrefix = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.errorPrefix = ConfigUtil.getContextProperty("errorPrefix");
	}

	@Override
	public void destroy() {
	}

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
		}
	}

	private void dealException(HttpServletResponse servletResponse, Exception e) {
		log.error("exceptionfilter catch error", e);

		if (e instanceof ServletException) {
			// 如果拦截到的是spring封装过的异常，获取原始异常信息
			e = (Exception) e.getCause();
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
		} else if (e instanceof I18nException) {
			parseI18nException(servletResponse, (I18nException) e);
		} else {
			dealDefaultError(servletResponse);
		}
	}

	/**
	 * 处理自定义业务异常
	 */
	private void parseI18nException(HttpServletResponse httpServletResponse, I18nException e) {
		flushErrorInfo(httpServletResponse, e.getI18nErrorMsg());
	}

	/**
	 * 处理主键重复异常
	 */
	private void parseDuplicateKeyException(HttpServletResponse httpServletResponse, DuplicateKeyException e) {
		String errorMsg = e.getCause().getMessage();
		String duplicateKey = getDuplicateKey(errorMsg);

		if (CommonContext.isChinese() && "resource_id".equals(duplicateKey)) {
			// 特殊处理编号情况
			duplicateKey = "编号";
		}

		String message = duplicateKey + ErrorCode.DUPLICATE_KEY.getMsg();
		flushErrorInfo(httpServletResponse, ErrorCode.DUPLICATE_KEY, message);
	}

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
	private void parseResourceNotFoundException(HttpServletResponse httpServletResponse, ResourceNotFoundException e) {
		flushErrorInfo(httpServletResponse, ErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
	}

	/**
	 * 处理用户输入参数异常
	 **/
	private void parseInvalidParamException(HttpServletResponse httpServletResponse, InvalidParamException e) {
		flushErrorInfo(httpServletResponse, ErrorCode.METHOD_ARG_NOT_VALID, e.getMessage());
	}

	/**
	 * 处理权限相关的异常
	 */
	private void parseUnauthorizedException(HttpServletResponse httpServletResponse) {
		// 直接返回403
		httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	/**
	 * 参数校验异常处理
	 */
	private void parseArgumentNotValidException(HttpServletResponse httpServletResponse, BindException e) {
		BindingResult br = e.getBindingResult();
		List<FieldError> errorList = br.getFieldErrors();

		for (FieldError error : errorList) {
			String field = error.getField();
			field = LocalStringUtil.snakeCaseFormat(field);

			// 找到第一个错误的字段，直接返回。
			// 不管是否配置了快速失败
			if (StringUtils.isNoneBlank(field)) {
				String desc = EnumUtils.getDescByField(field, FiledExceptionMsg.class);
				flushErrorInfo(httpServletResponse, ErrorCode.METHOD_ARG_NOT_VALID, StringUtils.isNotBlank(desc)? desc : field);

				return;
			}
		}
	}

	private void dealDefaultError(HttpServletResponse httpServletResponse) {
		flushErrorInfo(httpServletResponse, ErrorCode.INTERNAL_ERROR, "");
	}

	/**
	 * 不添加错误码前缀
	 */
	private void flushErrorInfo(HttpServletResponse httpServletResponse, BaseI18nErrorMsg i18nErrorMsg) {
		flushErrorInfo(httpServletResponse, i18nErrorMsg.getErrorCode(), i18nErrorMsg.getMsg());
	}

	/**
	 * 添加错误码前缀 自定义错误消息
	 */
	private void flushErrorInfo(HttpServletResponse httpServletResponse, BaseI18nErrorMsg i18nErrorMsg, String message) {
		String code = i18nErrorMsg.getErrorCode();
		code = this.errorPrefix + code;
		flushErrorInfo(httpServletResponse, code, message);
	}

	private void flushErrorInfo(HttpServletResponse httpServletResponse, String code, String message) {
		BaseRsp<String> errorInfo = new BaseRsp<>();

		errorInfo.setMessage(message);
		errorInfo.setCode(code);

		httpServletResponse.setContentType(WebConstant.CONTENT_TYPE);
		httpServletResponse.setStatus(WebConstant.HTTP_200);

		try {
			httpServletResponse.getWriter().write(JsonUtil.toJsonString(errorInfo));
		} catch (Exception e) {
			log.error("flushErrorInfo error:", e);
		}
	}
}
