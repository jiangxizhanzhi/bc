package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.service.bo.behavior.Behavior;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户模型新增请求体
 * 新增用户行为模型
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class UserModelAddBo extends UserModelBase implements Serializable {

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "简介")
	private String description;

	/**
	 * 行为
	 */
	@ApiModelProperty(value = "访问行为")
	private List<Behavior> behaviors;

	/**
	 * 是否限制带宽
	 */
	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	/**
	 * 访问带宽(Mbps)
	 */
	@ApiModelProperty(value = "访问带宽(Mbps)")
	private Integer bandwidth;

	/**
	 * 校验传入数据
	 */
	public void validate() {
		// 校验名称长度
		CrCommonValidator.validateNameLen(this.getUserName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		// 校验描述长度
		CrCommonValidator.validateDescLen(this.description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		if (behaviors == null) {
			behaviors = Lists.newArrayList();
		} else {
			behaviors.forEach(behavior -> {
				// 行为必须设置开始时间
				if (behavior.getStartTime() == null) {
					throw new BusinessException(BsErrorCode.START_TIME_NOT_CONFIGURED);
				}
			});
		}
		// 访问习惯不可为null
		if(this.getAccessHabit() == null){
			this.setAccessHabit(Lists.newArrayList());
		}
		// 不允许有不同操作系统的行为
		for (Behavior behavior : this.behaviors) {
			if (!behavior.getAccessOs().equals(this.getAccessOs())) {
				throw new BusinessException(BsErrorCode.ILLEGAL_BEHAVIOR_OS_UM);
			}
		}
	}
}
