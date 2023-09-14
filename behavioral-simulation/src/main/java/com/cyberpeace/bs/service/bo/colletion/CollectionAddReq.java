package com.cyberpeace.bs.service.bo.colletion;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为集合新增请求体
 *
 * @author SN
 * @date 2023/07/27
 */
@Data
public class CollectionAddReq implements Serializable {

	@ApiModelProperty(value = "集合名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "录制设备")
	private Integer accessOs;

	@ApiModelProperty(value = "行为列表")
	private List<IdStep> behaviorList = Lists.newArrayList();

	public Integer countStep() {
		if (CollectionUtils.isEmpty(this.behaviorList)) {
			return 0;
		} else {
			return behaviorList.stream().filter(idStep -> idStep.getStepCount() != null)
					.mapToInt(IdStep::getStepCount).sum();
		}
	}

	public void validate() {
		CrCommonValidator.validateNameLen(this.name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(this.description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		for (IdStep idStep : behaviorList) {
			if (!idStep.getAccessOs().equals(this.getAccessOs())) {
				throw new BusinessException(BsErrorCode.ILLEGAL_BEHAVIOR_OS);
			}
		}
	}

	public List<String> getBehaviorIds() {
		if (this.behaviorList.isEmpty()) {
			return Lists.newArrayList();
		}
		return this.behaviorList.stream().map(IdStep::getId).collect(Collectors.toList());
	}
}

@Data
class IdStep implements Serializable {

	/**
	 * 行为id
	 */
	@ApiModelProperty(value = "编号")
	private String id;

	/**
	 * 步骤数
	 */
	@ApiModelProperty(value = "行为步骤")
	private Integer stepCount;

	/**
	 * 设备类型
	 */
	private Integer accessOs;

}
