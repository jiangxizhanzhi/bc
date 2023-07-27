package com.cyberpeace.bs.service.bo.colletion;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为集合列表响应
 *
 * @author SN
 * @date 2023/07/27
 */
@Data
public class BehaviorCollectionListBo extends BehaviorCollectionBase implements Serializable {

	@ApiModelProperty(value = "行为数")
	private Integer behaviorCount;

	@ApiModelProperty(value = "步骤数")
	private Integer stepCount;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;
}
