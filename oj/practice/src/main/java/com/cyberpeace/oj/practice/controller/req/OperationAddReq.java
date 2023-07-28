package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.CustomValidate;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.baseinterface.Create;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static com.cyberpeace.cr.crbase.utils.CrCommonValidator.validateLength;

@Slf4j
@Data
@ApiModel
public class OperationAddReq implements Serializable, CustomValidate {

	@ApiModelProperty("标题")
	@NotBlank(groups = { Create.class })
	private String title;

	@ApiModelProperty(name = "practice_set_id", value = "习题集")
	@NotBlank(groups = { Create.class })
	private String practiceSetId;

	@ApiModelProperty(name = "category_id", value = "方向")
	@NotBlank(groups = { Create.class })
	private String categoryId;

	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty("难度")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

	@ApiModelProperty("题目描述")
	@NotBlank(groups = { Create.class })
	private String content;

	@ApiModelProperty(name = "attachment_id", value = "附件编号")
	private String attachmentId;

	@ApiModelProperty(name = "dynamic_env", value = "是否为动态环境")
	@NotNull(groups = { Create.class })
	private Boolean dynamicEnv;

	@ApiModelProperty(name = "scene_config_name", value = "场景配置名称")
	private String sceneConfigName;

	@ApiModelProperty(name = "scene_config_data", value = "场景配置")
	private String sceneConfigData;

	@ApiModelProperty(name = "scene_share_mode", value = "场景共享类型 1=个人 2=共享")
	private Integer sceneShareMode;

	@ApiModelProperty(name = "dynamic_flag", value = "是否为动态flag 0=静态 1=动态 2=随机")
	private Integer dynamicFlag;

	@ApiModelProperty(name = "destroy_delay", value = "场景存活时间")
	@NotNull(groups = { Create.class })
	private Integer destroyDelay;

	@ApiModelProperty("答案")
	private String answer;

	@ApiModelProperty("分数")
	@NotNull(groups = { Create.class })
	private Double score;

	@ApiModelProperty(name = "is_public", value = "是否公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	@ApiModelProperty(name = "writeup_public", value = "题解是否公开")
	@NotNull(groups = { Create.class })
	private Boolean writeupPublic;

	@ApiModelProperty("题解")
	private String writeup;

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setDynamic_env(Boolean dynamicEnv) {
		this.dynamicEnv = dynamicEnv;
	}

	public void setScene_config_name(String sceneConfigName) {
		this.sceneConfigName = sceneConfigName;
	}

	public void setScene_config_data(String ojSceneConfig) {
		this.sceneConfigData = ojSceneConfig;
	}

	public void setScene_share_mode(Integer sceneShareMode) {
		this.sceneShareMode = sceneShareMode;
	}

	public void setDynamic_flag(Integer dynamicFlag) {
		this.dynamicFlag = dynamicFlag;
	}

	public void setDestroy_delay(Integer destroyDelay) {
		this.destroyDelay = destroyDelay;
	}

	public void setIs_public(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setWriteup_public(Boolean writeupPublic) {
		this.writeupPublic = writeupPublic;
	}

	public void setAttachment_id(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	@Override
	public void validate() {
		if (null != dynamicFlag && dynamicFlag.intValue() == PracticeOperation.DynamicFlag.RANDOM
				.getCode().intValue()) {

			List<String> answers = JsonUtil.toJsonObjectArray(answer, String.class);
			String[] files = StringUtils.split(attachmentId, CommonConstant.CODE_COMMA);
			List<String> notEmptyFile = StreamApiUtils.filter(Arrays.asList(files),
					file -> StringUtils.isNotBlank(file));

			if (answers.size() != notEmptyFile.size()) {
				log.error("answers.size() != notEmptyFile.size(),answers.size() ={}，notEmptyFile.size()={}", answers.size(), notEmptyFile.size());
				throw new BusinessException(
						OjBusinessErrorCode.PRACTICE_ATTACHMENT_ANSWER_INCONSISTENT);
			}
		}
		validateLength(title, CommonConstant.ONE, CommonConstant.TWO_HUNDRED_AND_FIFTY_FIVE,OjBusinessErrorCode.ANSWER_TOO_LONG);

	}
}
