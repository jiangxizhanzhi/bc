/**
 * 这段代码定义了一个名为 `SpaceAptReport` 的 Java 类，用于表示空间APT（高级持续性威胁）报告实体。以下是对其的简要分析： 
 * ### 主要功能 
 * - **类定义**：`SpaceAptReport` 继承自 `SpaceBaseTable`，并使用 Lombok 的 `@Data` 注解自动生成 getter 和 setter 方法。 
 * - **数据库表映射**：通过 `@TableName("space_apt_report")` 注解将该类与数据库中的 `space_apt_report` 表进行映射。 
 * - **属性**：包含多个字段如 `aptOrg`, `publicTime`, `publicCompany`, `type`, `reportPath`, 和 `builtin`，这些字段分别代表不同的信息。 
 * - **方法**：重写了 `entity2Bo` 方法，该方法接收一个 `Map<String, IdAndName>` 类型的参数，并通过 `BeanUtils.copy` 方法将当前对象转换为 `SpaceAptReportBo` 对象。 
 * ### 总结 
 * 此代码片段展示了如何创建一个数据访问层实体类，用于处理与数据库中特定表相关的操作。它利用了 MyBatis-Plus 提供的功能来简化数据库操作，并通过工具类完成对象之间的转换。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAptReportBo;
import lombok.Data;

import java.util.Map;

/**
 * Space apt 报告
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Data
@TableName("space_apt_report")
public class SpaceAptReport extends SpaceBaseTable {

	private String aptOrg;

	private String publicTime;

	private String publicCompany;

	private Integer type;

	private String reportPath;

	private Boolean builtin;
	@Override
	public SpaceAptReportBo entity2Bo(Map<String, IdAndName> relatedMap) {
        SpaceAptReportBo bo = BeanUtils.copy(this, SpaceAptReportBo.class);
        return bo;
    }
}
