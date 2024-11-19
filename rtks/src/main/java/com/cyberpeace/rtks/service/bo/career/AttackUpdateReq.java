package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AttackUpdateReq implements Serializable {

	/**
	 * ATT&CK id
	 *
	 * @see String
	 */
	@ApiModelProperty("ATT&CK id")
	@NotNull
	private String resourceId;

	/**
	 * 场景模板id
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("场景模板id")
	@NotNull
	private List<String> businessTemplateIds;
}
