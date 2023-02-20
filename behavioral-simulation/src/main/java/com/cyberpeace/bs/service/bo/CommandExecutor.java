package com.cyberpeace.bs.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.HttpClientUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CommandExecutor {

	final String defaultErrorMessage;

	final String url;

	final String taskId;

	private Integer state;

	private String message;

	protected CommandExecutor(String taskId, String url, String message) {
		this.taskId = taskId;
		this.url = url;
		this.defaultErrorMessage = message;
	}

	public void execute() {
		state = 1;
		message = "";
		try {
			doBusiness();
		} catch (Exception e) {
			state = 2;
			message = defaultErrorMessage;
			if (e instanceof BusinessException) {
				message = ((BusinessException) e).getErrorMessage();
			}
			throw e;
		} finally {
			try {
				callback();
			} catch (Exception e1) {
				log.error("failed to callback: ", e1);
			}
		}
	}

	/**
	 * 具体业务
	 */
	protected void doBusiness() {

	}

	private void callback() {
		CallbackBo callbackBo = new CallbackBo();
		callbackBo.setTask_id(taskId);
		callbackBo.setState(state);
		callbackBo.setMessage(message);
		HttpClientUtil.post(url, callbackBo, String.class);
	}
}

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
