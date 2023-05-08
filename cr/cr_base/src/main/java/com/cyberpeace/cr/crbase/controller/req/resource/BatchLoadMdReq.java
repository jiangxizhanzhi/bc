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
 * 批量装载md请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@Slf4j
public class BatchLoadMdReq implements Serializable {

	@ApiModelProperty(value = "环境参数")
	@NotEmpty
	private Map<String, String> envReqs;

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
