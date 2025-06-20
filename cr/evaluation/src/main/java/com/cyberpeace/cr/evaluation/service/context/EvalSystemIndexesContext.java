package com.cyberpeace.cr.evaluation.service.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * eval 系统索引上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSystemIndexesContext` 的 Java 类，该类用于表示评估系统中的索引上下文。以下是对此代码的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**: `EvalSystemIndexesContext` 
 * - **实现接口**: `Serializable`，表明该类的对象可以序列化。 
 * ### 注解 
 * - **Lombok 注解**: 
 * - `@Getter`: 自动生成所有字段的 getter 方法。 
 * - `@Setter`: 自动生成所有字段的 setter 方法。 
 * - **Jackson 注解**: 
 * - `@JsonInclude(JsonInclude.Include.NON_NULL)`: 指示 Jackson 序列化时忽略值为 `null` 的属性。 
 * - **Swagger 注解**: 
 * - `@ApiModelProperty`: 为每个字段提供描述信息。 
 * ### 字段 
 * - **resourceId**: 字符串类型，表示资源 ID。 
 * - **name**: 字符串类型，表示名称。 
 * - **score**: 整数类型，表示得分。 
 * - **children**: `List<EvalSystemIndexesContext>` 类型，表示子级索引上下文列表。 
 * - **type**: 整数类型，表示评分方式。 
 * - **deviceId**: 字符串类型，表示装置 ID（主机节点 ID）。 
 * - **probeId**: 字符串类型，表示探针 ID。 
 * - **specialist**: `EvalRecordSpecialistContext` 类型，表示专家评分配置。 
 * ### 总结 
 * - 这个类主要用于表示评估系统中的索引上下文，并且支持序列化。 
 * - 它包含多个字段，每个字段都有相应的注解来提供描述信息或控制序列化行为。 
 * - 通过 Lombok 注解，简化了 getter 和 setter 方法的编写。 
 * - `children` 字段表示树形结构中的子节点，这使得该类适用于表示具有层次结构的数据。 
 * 这个类的设计目的是为了便于管理和操作评估系统的数据结构。 
**/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalSystemIndexesContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 得分
     */
    @ApiModelProperty("得分")
    private Integer score;

    /**
     * 子级
     */
    @ApiModelProperty("下一级")
    private List<EvalSystemIndexesContext> children;

    /**
     * 类型
     */
    @ApiModelProperty("评分方式")
    private Integer type;

    /**
     * 装置id
     */
    @ApiModelProperty("主机节点ID")
    private String deviceId;

    /**
     * 探针id
     */
    @ApiModelProperty("探针ID")
    private String probeId;

    /**
     * 专家
     */
    @ApiModelProperty("指标项专家评分配置")
    private EvalRecordSpecialistContext specialist;
}

