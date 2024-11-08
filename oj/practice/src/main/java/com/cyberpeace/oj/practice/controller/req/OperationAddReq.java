package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.CustomValidate;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static com.cyberpeace.cr.crbase.utils.CrCommonValidator.validateLength;

/**
 * 实操新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Data
@ApiModel
public class OperationAddReq implements Serializable, CustomValidate {

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	@NotBlank(groups = { Create.class })
	private String title;

//	@ApiModelProperty(name = "practice_set_id", value = "习题集")
//	private String practiceSetId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(name = "category_id", value = "方向")
	@NotBlank(groups = { Create.class })
	private String categoryId;

	/**
	 * 知识
	 */
	@ApiModelProperty("知识点")
	private String knowledge;

	/**
	 * 困难
	 */
	@ApiModelProperty("难度")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

	/**
	 * 内容
	 */
	@ApiModelProperty("题目描述")
	@NotBlank(groups = { Create.class })
	private String content;

	/**
	 * 附件id
	 */
	@ApiModelProperty(name = "attachment_id", value = "附件编号")
	private String attachmentId;

	/**
	 * 动态环境
	 */
	@ApiModelProperty(name = "dynamic_env", value = "是否为动态环境")
	@NotNull(groups = { Create.class })
	private Boolean dynamicEnv;

	/**
	 * 场景配置名称
	 */
	@ApiModelProperty(name = "scene_config_name", value = "场景配置名称")
	private String sceneConfigName;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty(name = "scene_config_data", value = "场景配置")
	private String sceneConfigData;

	/**
	 * 场景共享模式
	 */
	@ApiModelProperty(name = "scene_share_mode", value = "场景共享类型 1=个人 2=共享")
	private Integer sceneShareMode;

	/**
	 * 动态标志
	 */
	@ApiModelProperty(name = "dynamic_flag", value = "是否为动态flag 0=静态 1=动态 2=随机")
	private Integer dynamicFlag;

	/**
	 * 销毁延迟
	 */
	@ApiModelProperty(name = "destroy_delay", value = "场景存活时间")
	private Integer destroyDelay;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
	private String answer;

	/**
	 * 得分
	 */
	@ApiModelProperty("分数")
	@NotNull(groups = { Create.class })
	private Double score;

	/**
	 * 是公共
	 */
	@ApiModelProperty(name = "is_public", value = "是否公开")
	private Boolean isPublic;

	/**
	 * 公开报道
	 */
	@ApiModelProperty(name = "writeup_public", value = "题解是否公开")
	@NotNull(groups = { Create.class })
	private Boolean writeupPublic;

	/**
	 * 撰写
	 */
	@ApiModelProperty("题解")
	private String writeup;

	/**
	 * 目标
	 */
	@ApiModelProperty("靶标列表，包含镜像id，附件id，命令")
	private String target;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型，常量type=49，1：攻击，2：防守，3：其他")
	private Integer type;

//	public void setPractice_set_id(String practiceSetId) {
//		this.practiceSetId = practiceSetId;
//	}

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
		if (null != dynamicFlag) {
			if (dynamicFlag.intValue() == PracticeOperation.DynamicFlag.RANDOM.getCode()
					.intValue()) {
				List<String> answers = JsonUtil.toJsonObjectArray(answer, String.class);
				String[] files = StringUtils.split(attachmentId, CommonConstant.CODE_COMMA);
				List<String> notEmptyFile = StreamApiUtils.filter(Arrays.asList(files), file -> StringUtils.isNotBlank(file));
				if (answers.size() != notEmptyFile.size()) {
					log.error("answers.size() != notEmptyFile.size(),answers.size() ={}，notEmptyFile.size()={}",
							answers.size(), notEmptyFile.size());
					throw new BusinessException(OjBusinessErrorCode.PRACTICE_ATTACHMENT_ANSWER_INCONSISTENT);
				}
			} else if (dynamicFlag.intValue() == PracticeOperation.DynamicFlag.NOT.getCode().intValue()) {
				// validate not flag
				if (StringUtils.isBlank(this.writeup)) {
					throw new BusinessException(OjBusinessErrorCode.WRITEUP_INVALID);
				}
			}
		}
		validateLength(title, CommonConstant.ONE, CommonConstant.TWO_HUNDRED_AND_FIFTY_FIVE,
				OjBusinessErrorCode.ANSWER_TOO_LONG);
	}
}
