package com.cyberpeace.cr.crbase.controller.req.resource;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 批量加载Markdown请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@Slf4j
public class BatchLoadMdReq implements Serializable {

	/**
	 * env reqs
	 *
	 * @see Map<String, String>
	 */
	@ApiModelProperty(value = "环境参数")
	@NotEmpty
	private Map<String, String> envReqs;

	/**
	 * 路径
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资管文件路径")
	@NotEmpty
	private String path;

	public void validate() {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			log.error(String.format("invalid path [%s]", path));
			throw new BusinessException(ErrorCode.INVALID_FILE_PATH);
		}
	}
}
