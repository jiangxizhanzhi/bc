package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 名称得分
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class NameScore implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 得分
	 */
	@ApiModelProperty("分数")
	private Integer score = 0;
}
