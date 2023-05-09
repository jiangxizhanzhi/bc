package com.cyberpeace.bs.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.HttpClientUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 命令执行器
 *
 * @author SN
 * @date 2023/05/08
 */
@Slf4j
public abstract class CommandExecutor {

	/**
	 * 默认错误消息
	 */
	final String defaultErrorMessage;

	/**
	 * url
	 */
	final String url;

	/**
	 * 任务id
	 */
	final String taskId;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 消息
	 */
	private String message;

	protected CommandExecutor(String taskId, String url, String message) {
		this.taskId = taskId;
		this.url = url;
		this.defaultErrorMessage = message;
	}

	/**
	 * 具体执行函数
	 */
	public void execute() {
		// 默认成功
		state = 1;
		message = "";
		try {
			doBusiness();
		} catch (Exception e) {
			// 错误代码2
			state = 2;
			// 输出默认错误信息
			message = defaultErrorMessage;
			// 捕获businessException，获取错误信息并覆盖
			if (e instanceof BusinessException) {
				message = ((BusinessException) e).getErrorMessage();
			}
			// 抛出错误
			throw e;
		} finally {
			try {
				// 不论执行是否成功，执行回调
				callback();
			} catch (Exception e1) {
				log.error("failed to callback: ", e1);
			}
		}
	}

	/**
	 * 具体业务接口
	 */
	protected abstract void doBusiness();

	/**
	 * 回调
	 */
	private void callback() {
		CallbackBo callbackBo = new CallbackBo();
		callbackBo.setTask_id(taskId);
		callbackBo.setState(state);
		callbackBo.setMessage(message);
		HttpClientUtil.post(url, callbackBo, String.class);
	}
}

/**
 * 回调数据
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
class CallbackBo implements Serializable {

	/**
	 * 对应config_task_id
	 */
	private String task_id;

	/**
	 * 1：成功；2：错误
	 */
	private Integer state;

	/**
	 * 错误信息
	 */
	private String message;

	/**
	 * 任务执行进度
	 */
	private Integer percentage;
}
