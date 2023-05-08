package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 装饰组件分页查询请求
 *
 * @Author xuxz
 * @Date 4/1/2023 上午11:47
 */

@Data
public class SituationDecorationPageReq extends PageReqBase implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private String type;
}
