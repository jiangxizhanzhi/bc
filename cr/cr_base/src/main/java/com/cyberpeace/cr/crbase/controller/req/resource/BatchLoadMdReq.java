package com.cyberpeace.cr.crbase.controller.req.resource;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.Serializable;
import java.util.Map;

/**
 * 批量负荷医学博士请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@Slf4j
public class BatchLoadMdReq implements Serializable {

	/**
	 * env reqs
	 */
	@ApiModelProperty(value = "环境参数")
	@NotEmpty
	private Map<String, String> envReqs;

	/**
	 * 路径
	 */
	@ApiModelProperty(value = "资管文件路径")
	@NotEmpty
	private String path;

	/**
	 * 验证
	 */
	public void validate() {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			log.error(String.format("invalid path [%s]", path));
			throw new BusinessException(ErrorCode.INVALID_FILE_PATH);
		}
	}
}
