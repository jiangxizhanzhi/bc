/**
 * 这段代码定义了一个名为 `SpaceAptReportBo` 的 Java 类，继承自 `SpaceBoBase`，用于表示空间易受攻击报告的业务对象（BO）。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - 该类是 `SpaceBoBase` 的子类，主要用于封装空间易受攻击报告的相关信息。 
 * - 它通过注解 `@Data` 自动生成了 getter 和 setter 方法。 
 * 2. **字段说明**： 
 * - `resourceId`：资源ID，用作唯一标识符。 
 * - `name`：报告名称。 
 * - `aptOrg`：APT组织，描述相关的网络威胁组织。 
 * - `publicTime`：公共时间，表示报告发布时间。 
 * - `publicCompany`：发布厂商，即生成报告的公司或机构。 
 * - `type`：报告类型，整数值，区分不同类型的报告（如APT组织分析或典型攻击行动分析）。 
 * - `reportPath`：报告PDF文件的存储路径。 
 * - `builtin`：布尔值，表示该报告是否为内置报告。 
 * 3. **方法说明**： 
 * - `bo2Entity()`：将当前业务对象（BO）转换为实体对象（`SpaceAptReport`），使用工具类 `BeanUtils` 进行属性复制。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Data` 注解，简化了代码，避免手动编写 getter 和 setter 方法。 
 * - Swagger 注解 `@ApiModelProperty` 用于描述每个字段的意义，便于文档生成和接口测试。 
 * 5. **设计特点**： 
 * - 该类遵循了典型的 BO 模式，用于在业务层与数据层之间传递数据。 
 * - 提供了与实体类的双向转换能力，便于持久化操作。 
 * 总结：这是一个典型的业务对象类，用于封装空间易受攻击报告的信息，并提供了与实体类的转换方法。代码结构清晰，功能明确，适合在业务逻辑中使用。 
**/
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间容易报告Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Data
public class SpaceAptReportBo extends SpaceBoBase {

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
	@ApiModelProperty(value = "报告名称")
	private String name;

	/**
	 * apt 组织
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "APT组织")
	private String aptOrg;

	/**
	 * 公共时间
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "发现时间")
	private String publicTime;

	/**
	 * 上市公司
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "发布厂商")
	private String publicCompany;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "报告类型， 1：APT组织分析， 2：典型攻击行动分析")
	private Integer type;

	/**
	 * 报告路径
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "报告pdf路径")
	private String reportPath;
	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否内置")
	private Boolean builtin;

	/**
	 * BO2 实体
	 *
	 * @return Spaceapt报告
	 * @throws
	 * @see SpaceAptReport
	 */
	@Override
	public SpaceAptReport bo2Entity() {
        SpaceAptReport entity = BeanUtils.copy(this, SpaceAptReport.class);
        return entity;
	}
}
