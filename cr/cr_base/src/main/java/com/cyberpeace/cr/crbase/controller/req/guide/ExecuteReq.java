package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 执行请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ExecuteReq implements Serializable {

	/**
	 * 事件类型
	 */
	@ApiModelProperty("动作类型")
	private Integer eventType;

	/**
	 * 类别列表
	 */
	@ApiModelProperty("攻防类型")
	private List<Integer> categoryList;

	/**
	 * 源
	 */
	@ApiModelProperty("源节点")
	private String source;

	/**
	 * 目标
	 */
	@ApiModelProperty("目标节点")
	private String target;


}
