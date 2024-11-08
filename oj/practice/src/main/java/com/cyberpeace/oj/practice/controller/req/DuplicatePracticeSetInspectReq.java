package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 重复实践设置检查请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class DuplicatePracticeSetInspectReq implements Serializable {

	/**
	 * 文件名
	 */
	@ApiModelProperty("待检查习题集名称")
	@NotEmpty
	private List<String> fileNames;

}
