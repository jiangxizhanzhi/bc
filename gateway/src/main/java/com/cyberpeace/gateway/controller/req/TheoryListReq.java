package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 理论列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class TheoryListReq extends PageReqBase {

	/**
	 * 类别id
	 */
	@ApiModelProperty(name = "category_id", value = "方向")
	private String categoryId;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 理论类型
	 */
	@ApiModelProperty(name = "theory_type", value = "题目类型:0-单选, 1-多选, 2-判断, 3-填空, 4-简答", allowableValues = "0, 1, 2, 3, 4")
	private Integer theoryType;

}
