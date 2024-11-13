package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 房间底信息
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class RoomBaseInfo {

	/**
	 * 团队模板id
	 */
	private String teamTemplateId;

	/**
	 * 团队模板名称
	 */
	private String teamTemplateName;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 子类型
	 */
	@ApiModelProperty("训练类型")
	private Integer subType;

	/**
	 * 子类型字符串
	 */
	private String subTypeString;

	/**
	 * 预科技能
	 */
	private String preSkills;

	/**
	 * 训练技能
	 */
	private String trainSkills;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 持续时间
	 */
	@ApiModelProperty("推荐时长")
	private Integer durationTime;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 难度字符串
	 */
	private String difficultyString;

	/**
	 * 训练状态
	 */
	private Integer trainingStatus;

	/**
	 * 时间线配置
	 */
	private String timeLineConfig;

	/**
	 * 开始时间字符串
	 */
	@ApiModelProperty(value = "训练时间")
	private String startTimeString;

	/**
	 * 耗时
	 */
	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	/**
	 * 预技能列表
	 */
	private List<String> preSkillList;

	/**
	 * 训练技能列表
	 */
	private List<String> trainSkillList;

	public void initExportString() {
		String duration = TimeUtil.getTimeInterval(this.getStartTime(), this.getEndTime());
		this.timeCost = duration;
		this.subTypeString = EnumUtils.getEnumDescByCode(this.getSubType(), TrainTypeConvert.class);
		this.difficultyString = EnumUtils.getEnumDescByCode(this.getDifficulty(),
				DifficultyConvert.class);
		this.startTimeString = DateUtil.DateToString(startTime, "");
		this.preSkillList = StringUtils.isBlank(preSkills) ? new ArrayList<>()
				: JsonUtil.toJsonObjectArray(preSkills, String.class);
		this.trainSkillList = StringUtils.isBlank(trainSkills) ? new ArrayList<>()
				: JsonUtil.toJsonObjectArray(trainSkills, String.class);
	}
}
