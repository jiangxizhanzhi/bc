package com.cyberpeace.config.auth;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
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
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志记录过滤器类
 * 该类实现了Filter接口，用于处理和记录异常信息
 * 它使用了slf4j库进行日志记录，以提供更灵活的日志处理能力
 */
@Slf4j
public class ExceptionFilter implements Filter {

	// 缓存映射，用于存储需要被缓存的数据，键和值都是字符串类型
	private Map<String, String> cache = Maps.newHashMap();

	// 错误前缀，用于标识或处理错误信息
	private String errorPrefix = null;

	/**
	 * 初始化过滤器
	 *
	 * @param filterConfig 过滤器的配置信息，可以通过它获取到过滤器的初始化参数等信息
	 * @throws ServletException 如果初始化过程中发生错误，抛出此异常
	 *
	 * 此方法主要用于初始化过滤器，从上下文中获取错误前缀并赋值给errorPrefix变量
	 * 这样做是为了在过滤器的生命周期中能够统一处理错误信息
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	    this.errorPrefix = ConfigUtil.getContextProperty("errorPrefix");
	}

	/**
	 * 销毁过滤器
	 *
	 * 此方法目前为空实现，因为它在过滤器销毁时不需要执行任何清理操作
	 * 如果未来需要在过滤器销毁时添加一些资源释放或清理工作，可以在此方法中添加相应逻辑
	 */
	@Override
	public void destroy() {}

	/**
	 * 执行过滤操作
	 *
	 * @param servletRequest Servlet请求对象
	 * @param servletResponse Servlet响应对象
	 * @param filterChain 过滤链对象
	 * @throws IOException 如果发生I/O错误
	 * @throws ServletException 如果发生Servlet错误
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
	        FilterChain filterChain) throws IOException, ServletException {
	    try {
	        filterChain.doFilter(servletRequest, servletResponse);
	    } catch (Exception e) {
	        dealException((HttpServletResponse) servletResponse, e);
	    } finally {
	        // 在finally块中移除请求相关的上下文信息，确保每个请求的上下文信息不互相干扰
	        UserInfoContext.removeUserContext();
	        TraceIdContext.removeTraceId();
	        CommonContext.removeLanguage();
	        CommonContext.removeIp();
	        CommonContext.removeAuthorization();
	    }
	}

	/**
	 * 处理异常
	 *
	 * @param servletResponse Servlet响应对象，用于向客户端发送响应
	 * @param e 捕获到的异常对象
	 */
	private void dealException(HttpServletResponse servletResponse, Exception e) {
	    log.error("exceptionfilter catch error", e);

	    // 不支持的请求 e.getCause() is null
	    if (e instanceof ServletException && e.getCause() != null) {
	        // 如果拦截到的是spring封装过的异常，获取原始异常信息
	        e = (Exception) e.getCause();
	    }

	    // 前端输入反序列化错误
	    if (e instanceof HttpMessageNotReadableException) {
	        e = (Exception) e.getCause().getCause();
	    }

	    // 特定错误码处理
	    if (e.getMessage()!=null&& e.getMessage().contains("104021")) {
	        e = new BusinessException("104021");
	    }

	    // 根据不同的异常类型，调用不同的处理方法
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
	    } else if (e instanceof BusinessException) {
	        parseBusinessException(servletResponse, (BusinessException) e);
	    } else if (e instanceof HttpRequestMethodNotSupportedException) {
	        notSupportedException(servletResponse);
	    }
	    else {
	        dealDefaultError(servletResponse);
	    }
	}

	/**
	 * 处理自定义业务异常
	 * @param httpServletResponse HTTP响应对象，用于向客户端返回错误信息
	 * @param e 业务异常对象，包含错误代码和错误消息
	 */
	private void parseBusinessException(HttpServletResponse httpServletResponse,
			BusinessException e) {
		BaseRsp<String> errorInfo = new BaseRsp<>();
		errorInfo.setCode(getErrorCode(e.getMessage()));
		errorInfo.setMessage(e.getErrorMessage());
		flushErrorInfo(httpServletResponse, errorInfo);
	}

	/**
	 * 调用第三方服务错误处理
	 * 当调用第三方服务失败时调用此方法来构建适当的错误响应
	 * @param httpServletResponse HTTP响应对象，用于向客户端返回错误信息
	 * @param e 远程服务异常对象，包含可能的错误消息
	 */
	private void parseRemoteServiceException(HttpServletResponse httpServletResponse,
			RemoteServiceException e) {
		// mp返回msg则采用mp的msg，否则使用默认的msg
		BusinessException businessException;
		if (StringUtils.isBlank(e.getMessage())) {
			businessException = new BusinessException(ErrorCode.REMOTE_SERVICE_EXCEPTION);
		} else {
			businessException = new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION,
					e.getMessage());
		}
		parseBusinessException(httpServletResponse, businessException);
	}

	/**
	 * 处理DuplicateKeyException异常
	 * 当数据库中存在重复键时抛出此异常
	 *
	 * @param httpServletResponse HTTP响应对象，用于向客户端返回错误信息
	 * @param e                   重复键异常对象，包含具体错误信息
	 */
	private void parseDuplicateKeyException(HttpServletResponse httpServletResponse,
	        DuplicateKeyException e) {
	    // 获取错误信息
	    String errorMsg = e.getCause().getMessage();
	    // 提取重复的键名
	    String duplicateKey = getDuplicateKey(errorMsg);

	    // 如果是中文环境且重复的键名为"resource_id"，则将其显示为"编号"
	    if (CommonContext.isChinese() && "resource_id".equals(duplicateKey)) {
	        // 特殊处理编号情况
	        duplicateKey = "编号";
	    }

	    // 构造错误提示信息
	    String message = duplicateKey
	            + BusinessException.getMessageByLanguage(ErrorCode.DUPLICATE_KEY);
	    // 向客户端返回错误信息
	    flushErrorInfo(httpServletResponse, ErrorCode.DUPLICATE_KEY, message);
	}

	/**
	 * 从错误信息中提取重复的键名
	 *
	 * @param errorMsg 错误信息，包含重复键的详细描述
	 * @return 重复的键名
	 */
	private String getDuplicateKey(String errorMsg) {
	    String duplicateKey = "";
	    // 定义正则表达式匹配重复的键名
	    String rgex = "key '(.*?)'";
	    Pattern pattern = Pattern.compile(rgex);
	    Matcher matcher = pattern.matcher(errorMsg);
	    // 查找匹配的键名并提取
	    while (matcher.find()) {
	        duplicateKey = matcher.group(0);
	        // 去除"key '"和末尾的 "'"，仅保留键名
	        duplicateKey = StringUtils.substring(duplicateKey, 5, duplicateKey.length() - 1);
	    }
	    return duplicateKey;
	}

	/**
	 * 处理AuthenticationException异常
	 * 当用户未通过身份验证时抛出此异常
	 *
	 * @param httpServletResponse HTTP响应对象，用于设置未授权状态码
	 */
	private void parseAuthenticationException(HttpServletResponse httpServletResponse) {
	    // 设置HTTP状态码为未授权
	    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	}

	/**
	 * 处理不支持的操作异常
	 * 当请求的操作不被支持时调用此方法
	 * @param httpServletResponse HTTP响应对象，用于设置响应状态码
	 */
	private void notSupportedException(HttpServletResponse httpServletResponse) {
	    httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * 处理资源未找到异常
	 * 当请求的资源不存在时调用此方法
	 * @param httpServletResponse HTTP响应对象，用于返回错误信息
	 * @param e 资源未找到异常对象，包含错误详细信息
	 */
	private void parseResourceNotFoundException(HttpServletResponse httpServletResponse,
	        ResourceNotFoundException e) {
	    flushErrorInfo(httpServletResponse, ErrorCode.RESOURCE_NOT_FOUND, e.getMessage());
	}

	/**
	 * 处理无效参数异常
	 * 当请求参数不合法时调用此方法
	 * @param httpServletResponse HTTP响应对象，用于返回错误信息
	 * @param e 无效参数异常对象，包含错误详细信息
	 */
	private void parseInvalidParamException(HttpServletResponse httpServletResponse,
	        InvalidParamException e) {
	    flushErrorInfo(httpServletResponse, ErrorCode.METHOD_ARG_NOT_VALID, e.getMessage());
	}

	/**
	 * 处理未授权异常
	 * 当用户未授权访问请求的资源时调用此方法
	 * @param httpServletResponse HTTP响应对象，用于返回错误信息
	 */
	private void parseUnauthorizedException(HttpServletResponse httpServletResponse) {
	    // 直接返回403
	    BusinessException businessException = new BusinessException(
	            ErrorCode.INTERFACE_UNAUTHORIZED);
	    parseBusinessException(httpServletResponse, businessException);
	}

	/**
	 * 解析绑定异常并返回第一个错误的字段信息
	 *
	 * @param httpServletResponse HTTP响应对象，用于向客户端返回错误信息
	 * @param e 绑定异常对象，包含验证错误的详细信息
	 */
	private void parseArgumentNotValidException(HttpServletResponse httpServletResponse,
	        BindException e) {
	    // 获取绑定结果对象，用于提取验证错误信息
	    BindingResult br = e.getBindingResult();
	    // 获取所有字段错误信息列表
	    List<FieldError> errorList = br.getFieldErrors();

	    for (FieldError error : errorList) {
	        // 获取错误字段名
	        String field = error.getField();
	        // 将字段名转换为蛇形命名法
	        field = LocalStringUtil.snakeCaseFormat(field);

	        // 找到第一个错误的字段，直接返回。
	        // 不管是否配置了快速失败
	        if (StringUtils.isNoneBlank(field)) {
	            // 根据错误消息的默认值获取国际化信息
	            String desc = CommonContext.isChinese()
	                    ? ConfigUtil.getContextProperty(error.getDefaultMessage() + ".cn")
	                    : ConfigUtil.getContextProperty(error.getDefaultMessage() + ".en");
	            // 向客户端返回错误信息并结束方法执行
	            flushErrorInfo(httpServletResponse, ErrorCode.METHOD_ARG_NOT_VALID,
	                    StringUtils.isNotBlank(desc) ? desc : field);

	            return;
	        }
	    }
	}

	/**
	 * 处理默认错误响应
	 * 当发生内部错误时，使用此方法向HTTP响应中填充错误信息
	 *
	 * @param httpServletResponse HTTP响应对象，用于向客户端返回错误信息
	 */
	private void dealDefaultError(HttpServletResponse httpServletResponse) {
	    // 调用方法以设置响应的内容类型、状态码，并写入错误信息
	    flushErrorInfo(httpServletResponse, ErrorCode.INTERNAL_ERROR,
	            BusinessException.getMessageByLanguage(ErrorCode.INTERNAL_ERROR));
	}

	/**
	 * 将错误信息刷新到HTTP响应中
	 * 此方法用于将错误信息设置到HTTP响应中，以便客户端可以了解错误详情
	 *
	 * @param httpServletResponse HTTP响应对象，用于向客户端返回错误信息
	 * @param errorInfo 错误信息对象，包含错误详情
	 */
	private void flushErrorInfo(HttpServletResponse httpServletResponse, BaseRsp errorInfo) {
	    // 设置响应的内容类型
	    httpServletResponse.setContentType(WebConstant.CONTENT_TYPE);
	    // 设置响应的状态码
	    httpServletResponse.setStatus(WebConstant.HTTP_200);
	    try {
	        // 将错误信息对象转换为JSON字符串，并写入响应体中
	        httpServletResponse.getWriter().write(JsonUtil.toJsonString(errorInfo));
	    } catch (Exception e) {
	        // 如果在写入响应过程中发生异常，记录错误日志
	        log.error("flushErrorInfo error:", e);
	    }
	}

	/**
	 * 向HTTP响应中写入错误信息
	 *
	 * @param httpServletResponse HTTP响应对象，用于向客户端返回数据
	 * @param code 错误代码，用于标识特定的错误
	 * @param message 错误消息，描述错误的详细信息
	 */
	private void flushErrorInfo(HttpServletResponse httpServletResponse, String code,
	        String message) {
	    // 创建一个错误信息对象，用于封装错误代码和消息
	    BaseRsp<String> errorInfo = new BaseRsp<>();

	    // 设置错误消息
	    errorInfo.setMessage(message);
	    // 设置错误代码，通过getErrorCode方法获取统一格式的错误代码
	    errorInfo.setCode(getErrorCode(code));

	    // 设置HTTP响应的内容类型
	    httpServletResponse.setContentType(WebConstant.CONTENT_TYPE);
	    // 设置HTTP响应的状态码
	    httpServletResponse.setStatus(WebConstant.HTTP_200);

	    // 尝试将错误信息对象转换为JSON字符串并写入HTTP响应中
	    try {
	        httpServletResponse.getWriter().write(JsonUtil.toJsonString(errorInfo));
	    } catch (Exception e) {
	        // 如果在写入HTTP响应时发生异常，记录错误日志
	        log.error("flushErrorInfo error:", e);
	    }
	}

	/**
	 * 获取统一格式的错误代码
	 * 如果错误代码不在缓存中，则将其添加到缓存中并返回格式化后的错误代码
	 *
	 * @param code 错误代码，用于标识特定的错误
	 * @return 返回统一格式的错误代码
	 */
	private String getErrorCode(String code) {
	    // 检查缓存中是否已存在该错误代码
	    if (!cache.containsKey(code)) {
	        // 如果不存在，将错误代码与错误代码前缀拼接后存入缓存
	        cache.put(code, errorPrefix + code);
	    }

	    // 从缓存中获取并返回格式化后的错误代码
	    return cache.get(code);
	}

}
