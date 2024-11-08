package com.cyberpeace.cr.crbase.controller.req.resource;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 批量转 储请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class BatchDumpReq implements Serializable {

	/**
	 * id列表
	 */
	@ApiModelProperty("模板id列表")
	List<String> ids;
}
