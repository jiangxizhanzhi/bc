package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.AptReportHistory;
import com.cyberpeace.space.service.context.rule.SpaceAptFeatureBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Space APT 功能
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAptFeature` 的 Java 类，它继承自 `SpaceBaseTable` 并使用了 MyBatis-Plus 的注解来映射数据库表 `space_apt_feature`。以下是对代码的简要分析： 
 * 1. **类与注解**： 
 * - `@TableName("space_apt_feature")`：指定该类对应的数据库表名为 `space_apt_feature`。 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * 2. **字段与注解**： 
 * - 各个字段通过 `@ApiModelProperty` 注解描述其用途（如别名、攻击者类型等），并使用 `@TableField` 指定数据库中的字段名。 
 * - 字段类型包括字符串 (`String`)、整数 (`Integer`) 和日期时间相关的字符串 (`String`)。 
 * 3. **方法**： 
 * - `entity2Bo` 方法将当前对象转换为 `SpaceAptFeatureBo` 对象，并处理了 `historyReport` 字段的特殊转换，将其从 JSON 格式解析为 `AptReportHistory` 对象列表。 
 * 4. **依赖的工具与模型**： 
 * - 使用了 `BeanUtils.copy` 方法进行对象之间的复制。 
 * - 使用了 `JsonUtil.toJsonObjectArray` 方法将 JSON 字符串解析为特定类型的对象集合。 
 * 5. **功能**： 
 * - 该类主要用于表示空间APT（高级持续性威胁）的功能特性，并提供了一种方式将其数据模型转换为业务逻辑层所需的形式。 
 * 总体而言，这段代码实现了数据传输对象（DTO）到业务对象（BO）的转换，同时保持了良好的可读性和结构化设计。 
**/
@Getter
@Setter
@TableName("space_apt_feature")
public class SpaceAptFeature extends SpaceBaseTable {

	@ApiModelProperty(value = "别名")
	@TableField("`alias`")
	private String alias;

	@ApiModelProperty(value = "攻击者类型")
	private Integer attackerType;

	@ApiModelProperty(value = "技术能力")
	private Integer skill;

	@ApiModelProperty(value = "疑似来源")
	private String source;

	@ApiModelProperty(value = "最早活动时间")
	private String firstTime;

	@ApiModelProperty(value = "最近活动时间")
	private String latestTime;

	@ApiModelProperty(value = "影响国家和地区")
	private String effected;

	@ApiModelProperty(value = "影响行业")
	private String industry;

	@ApiModelProperty(value = "攻击方式")
	private String method;

	@ApiModelProperty(value = "攻击频率")
	private Integer frequency;

	@ApiModelProperty(value = "常用语言")
	private String language;

	@ApiModelProperty(value = "组织概述")
	private String organization;

	@ApiModelProperty(value = "技战法")
	private String tactic;

	@ApiModelProperty(value = "历史报告")
	private String historyReport;

	private String logo;


	public SpaceAptFeatureBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAptFeatureBo bo = BeanUtils.copy(this, SpaceAptFeatureBo.class);
		bo.setHistoryReport(JsonUtil.toJsonObjectArray(this.historyReport, AptReportHistory.class));
		return bo;
	}
}

