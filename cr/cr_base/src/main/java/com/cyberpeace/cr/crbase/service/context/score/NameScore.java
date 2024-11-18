package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 名称得分
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class NameScore implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("分数")
	private Integer score = 0;
}
