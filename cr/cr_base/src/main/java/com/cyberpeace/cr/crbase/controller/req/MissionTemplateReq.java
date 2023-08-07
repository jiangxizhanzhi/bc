package com.cyberpeace.cr.crbase.controller.req;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.MissionBase;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
public class MissionTemplateReq implements Serializable {

	@ApiModelProperty(value = "标题", required = true)
	private String title;

	@ApiModelProperty(value = "描述", required = true)
	private String description;

	@ApiModelProperty(value = "分值", required = true)
	private Integer score;

	@ApiModelProperty(value = "技能点", required = true)
	private List<Integer> skillPoints;

	@ApiModelProperty(value = "标准答案", required = true)
	private String answer;

	@ApiModelProperty(value = "选择题选项，非选择题为\"null\"", required = true)
	private String option;

	@ApiModelProperty(value = "任务类型（单选、填空等）", required = true)
	private Integer type;

	@ApiModelProperty(value = "任务类别（密码破解、网络侦查等）", required = true)
	private String category;

	@ApiModelProperty(value = "题解", required = true)
	private String writeUp;

	public void validate() {

		int answerSize = Integer.parseInt(ConfigUtil.getContextProperty("cr.answer.size"));
		int minLen = Integer.parseInt(ConfigUtil.getContextProperty("cr.str.len.min"));
		int answerLen = Integer.parseInt(ConfigUtil.getContextProperty("cr.answer.len"));

		// 验证名称长度
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.INVALID_MISSION_NAME);
		// validate desc
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		//校验题解长度
		CrCommonValidator.validateTextLen(writeUp, CrBusinessErrorCode.ILLEGAL_WRITEUP_LEN);

		// validate skill points
		CrCommonValidator.validateSize(skillPoints, CrCommonConstants.SKILL_POINTS_SIZE,
				CrBusinessErrorCode.INVALID_SKILL_POINTS_SIZE);
		for (Integer skillPoint : skillPoints) {
			CrCommonValidator.validateRange(skillPoint, CrCommonConstants.SKILL_POINT_MIN_VALUE,
					CrCommonConstants.SKILL_POINT_MAX_VALUE,
					CrBusinessErrorCode.INVALID_SKILL_POINT_VALUE);
		}

		// validate score
		CrCommonValidator.validateScore(score, CrCommonConstants.SCORE_MAX_VALUE,
				CrBusinessErrorCode.SCORE_OUT_OF_RANGE);

		// validate answer
		if (MissionBase.MissionType.ANSWER_NULLABLE_TYPE_LIST.contains(type) && answer == null) {
			throw new BusinessException(CrBusinessErrorCode.ANSWER_NOT_NULLABLE);
		}
		// 攻击/防御可多个答案,用|间隔，最多5个答案，总长度限制500字符
		if (MissionBase.MissionType.ATTACK_DEFENCE_TYPE_LIST.contains(type)) {
			CrCommonValidator.validateRange(answer.length(), minLen, answerLen,
					CrBusinessErrorCode.ANSWER_LEN_OUT_OF_RANGE);
			List<String> answerList = Arrays.asList(answer.split("[|]"));
			CrCommonValidator.validateSize(answerList, 1, answerSize,
					CrBusinessErrorCode.ANSWER_SIZE_OUT_OF_RANGE);
		}
		// validate choice
		if (MissionBase.MissionType.HAS_OPTION_TYPE_LIST.contains(type)) {
			if (answer.equals(CommonConstant.EMPTY_LIST_JSON) || answer.isEmpty()) {
				throw new BusinessException(CrBusinessErrorCode.ANSWER_NOT_NULLABLE);
			}
			if (option.equals(CommonConstant.EMPTY_LIST_JSON) || option.isEmpty()) {
				throw new BusinessException(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE);
			}
			// validate option
			List<JSONObject> options = JsonUtil.toJsonObjectArray(option, JSONObject.class);
			List<String> realOptions = Lists.newArrayList();
			for (JSONObject option : options) {
				String optionValue = option.getString("optionValue");
				if (optionValue == null) {
					throw new BusinessException(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE);
				}
				String realOption = StringUtils.strip(optionValue);
				CrCommonValidator.validateDescLen(realOption, CrBusinessErrorCode.ILLEGAL_OPTION_LEN);
				if (realOption.isEmpty()) {
					throw new BusinessException(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE);
				}
				realOptions.add(realOption);
			}
			CrCommonValidator.checkStringListDuplicate(realOptions,
					CrBusinessErrorCode.OPTIONS_DUPLICATE);
		}

		if (MissionBase.MissionType.FLAG.getCode().equals(type)) {
			MissionFlagQuery missionFlagQuery = JSON.parseObject(answer, MissionFlagQuery.class);
			Integer flagType = missionFlagQuery.getFlagType();
			if (!MissionFlagQuery.FlagType.FLAG_TYPE_LIST.contains(flagType)) {
				throw new BusinessException(CrBusinessErrorCode.ILLEGAL_FLAG_TYPE);
			}
			if (MissionFlagQuery.FlagType.STATIC_FLAG.getCode().equals(flagType) && StringUtils.isBlank(missionFlagQuery.getFlagValue())) {
				throw new BusinessException(CrBusinessErrorCode.ILLEGAL_FLAG_TYPE);
			} else if (MissionFlagQuery.FlagType.DYNAMIC_FLAG.getCode().equals(flagType) && CollectionUtils.isEmpty(missionFlagQuery.getAttachmentList())) {
				throw new BusinessException(CrBusinessErrorCode.ILLEGAL_FLAG_TYPE);
			}
		}
	}
}
