package com.cyberpeace.cr.crbase.service.context.score;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 名称得分
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class NameScore implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
