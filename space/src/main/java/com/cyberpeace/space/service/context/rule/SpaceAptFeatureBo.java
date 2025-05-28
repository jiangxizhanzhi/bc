/**
 * ### 代码分析 
 * #### 类的功能 
 * `SpaceAptFeatureBo` 是一个基于 `SpaceBoBase` 的扩展类，用于表示空间易受攻击特征的业务对象（BO）。它封装了与空间攻击特征相关的多个属性，并提供了将该业务对象转换为实体对象的方法。 
 * --- 
 * #### 主要特性 
 * 1. **继承关系** 
 * - 继承自 `SpaceBoBase`。 
 * - 使用 Lombok 注解简化代码： 
 * - `@Getter` 和 `@Setter`：自动生成字段的 getter 和 setter 方法。 
 * - `@ApiModelProperty`：为 Swagger 提供 API 文档注释。 
 * 2. **属性定义** 
 * - 包含多个字段，如 `resourceId`、`name`、`source`、`firstTime` 等，分别表示资源 ID、名称、疑似来源、最早活动时间等信息。 
 * - 特殊字段： 
 * - `historyReport`：存储历史报告列表，使用泛型 `List<AptReportHistory>`。 
 * - `builtin`：布尔值，表示是否为内置对象。 
 * - `logo`：存储图标路径。 
 * 3. **方法** 
 * - `bo2Entity()`：将当前业务对象（BO）转换为实体对象（`SpaceAptFeature`）。 
 * - 使用工具类 `BeanUtils.copy` 进行浅拷贝。 
 * - 将 `historyReport` 转换为 JSON 字符串并设置到实体对象中。 
 * 4. **依赖工具类** 
 * - `BeanUtils.copy`：用于对象之间的字段复制。 
 * - `JsonUtil.arrayToString`：将 `List<AptReportHistory>` 转换为 JSON 格式的字符串。 
 * --- 
 * #### 总结 
 * - **用途**：`SpaceAptFeatureBo` 是一个典型的业务对象类，用于封装与空间攻击特征相关的信息。 
 * - **优点**： 
 * - 使用 Lombok 减少了冗余代码。 
 * - 提供了清晰的 API 文档注释。 
 * - 提供了便捷的 BO-to-Entity 转换方法。 
 * - **潜在改进点**： 
 * - 如果 `historyReport` 的 JSON 转换逻辑复杂，可以考虑封装到独立的方法中以提高可读性。 
 * - 如果需要支持深拷贝或更复杂的对象映射，可以引入更高级的工具（如 MapStruct 或 ModelMapper）。 
**/

package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间容易特征Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceAptFeatureBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 别名
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "别名")
	private String alias;

	/**
	 * 攻击者类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "攻击者类型")
	private Integer attackerType;

	/**
	 * 技能
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "技术能力")
	private Integer skill;

	/**
	 * 源
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "疑似来源")
	private String source;

	/**
	 * 第一次
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "最早活动时间")
	private String firstTime;

	/**
	 * 最新时间
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "最近活动时间")
	private String latestTime;

	/**
	 * 影响
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "影响国家和地区")
	private String effected;

	/**
	 * 工业
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "影响行业")
	private String industry;

	/**
	 * 方法
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "攻击方式")
	private String method;

	/**
	 * 频率
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "攻击频率")
	private Integer frequency;

	/**
	 * 语言
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "常用语言")
	private String language;

	/**
	 * 组织
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "组织概述")
	private String organization;

	/**
	 * 战法
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "技战法")
	private String tactic;

	/**
	 * 历史报告
	 *
	 * @see List<AptReportHistory>
	 */
	@ApiModelProperty(value = "历史报告")
	private List<AptReportHistory> historyReport;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "logo")
	private String logo;
	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "内置")
	private Boolean builtin;

	/**
	 * BO2 实体
	 *
	 * @return SpaceAPT功能
	 * @throws
	 * @see SpaceAptFeature
	 */
	public SpaceAptFeature bo2Entity() {
		SpaceAptFeature entity = BeanUtils.copy(this, SpaceAptFeature.class);
		entity.setHistoryReport(JsonUtil.arrayToString(this.historyReport));
		return entity;
	}

}
    