package com.cyberpeace.cr.crbase.controller.req.resource;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 批量转 储请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class BatchDumpReq implements Serializable {

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("模板id列表")
	List<String> ids;
}
