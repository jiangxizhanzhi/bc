package com.cyberpeace.rtks.controller.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 技能类别或系统新增请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel
public class SkillCategoryOrSystemAddReq implements Serializable {

	/**
	 * 内容
	 */
	@NotBlank
	private String content;
}
