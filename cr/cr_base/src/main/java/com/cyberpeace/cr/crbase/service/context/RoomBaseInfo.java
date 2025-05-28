/**
 * ### 分析与总结 
 * #### 类的功能： 
 * `RoomBaseInfo` 是一个 Java Bean 类，用于表示房间或场景的基础信息。它包含了一系列字段来描述房间的属性，例如名称、描述、开始时间、结束时间、难度等。 
 * #### 主要功能： 
 * 1. **数据存储**：类中定义了多个字段（如 `name`, `description`, `startTime`, `endTime` 等），用于存储房间的基本信息。 
 * 2. **数据转换**：提供了 `initExportString()` 方法，用于将某些字段（如时间、难度等）转换为友好的格式（如字符串形式）。 
 * 3. **依赖外部工具**：类依赖了一些外部工具类（如 `JsonUtil`, `DateUtil`, `TimeUtil`, `EnumUtils` 等）来完成复杂的数据处理任务。 
 * #### 关键点解析： 
 * - **字段设计**： 
 * - 使用了 `@ApiModelProperty` 注解来标注字段，方便在接口文档中生成说明。 
 * - 包含了多种类型的数据（如 `Integer`, `String`, `Date` 等），以及一些集合类型（如 `List<String>`）。 
 * - **初始化方法**： 
 * - `initExportString()` 方法负责将原始数据转换为更易读的形式，例如将时间戳转换为字符串、将预技能和训练技能的 JSON 字符串解析为列表。 
 * - **依赖的工具类**： 
 * - `TimeUtil.getTimeInterval()`：计算两个时间点之间的时间间隔。 
 * - `DateUtil.DateToString()`：将日期对象格式化为字符串。 
 * - `JsonUtil.toJsonObjectArray()`：将 JSON 字符串解析为对象数组。 
 * - `EnumUtils.getEnumDescByCode()`：根据枚举值获取对应的描述信息。 
 * #### 总结： 
 * `RoomBaseInfo` 是一个典型的 Java Bean 类，主要用于封装和管理房间的基本信息。通过依赖外部工具类，它能够灵活地处理复杂的数据格式转换任务，从而满足业务需求。 
**/
package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 房间底信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
	 * difficulty 字符串
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
	 * 时间成本
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
