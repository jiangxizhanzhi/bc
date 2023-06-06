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

@Data
public class RoomBaseInfo {

	private String teamTemplateId;

	private String teamTemplateName;

	private String name;

	private String description;

	@ApiModelProperty("训练类型")
	private Integer subType;

	private String subTypeString;

	private String preSkills;

	private String trainSkills;

	private Date startTime;

	private Date endTime;

	@ApiModelProperty("推荐时长")
	private Integer durationTime;

	@ApiModelProperty("难度")
	private Integer difficulty;

	private String difficultyString;

	private Integer trainingStatus;

	private String timeLineConfig;

	@ApiModelProperty(value = "训练时间")
	private String startTimeString;

	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	private List<String> preSkillList;

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
