/**
 * 这段代码定义了一个名为 `CollectionEventPageContext` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示收集事件的分页上下文。下面是对这段代码的简要分析： 
 * ### 类和包声明 
 * - **包声明**：`com.cyberpeace.integratation.model.scene` 
 * - 这个类位于 `com.cyberpeace.integratation.model.scene` 包中。 
 * - **类声明**：`public class CollectionEventPageContext implements Serializable` 
 * - `CollectionEventPageContext` 类实现了 `Serializable` 接口，表明它可以被序列化。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的 getter 和 setter 方法的生成。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述 API 中使用的字段。 
 * ### 属性 
 * - `private String id;` 
 * - 场景ID。 
 * - `private String nodeIds;` 
 * - 节点ID列表。 
 * - `private String probeId;` 
 * - 探针ID。注释提到内置探针为 "001" 到 "011"。 
 * - `private Date startTime;` 
 * - 事件开始时间。 
 * - `private Date endTime;` 
 * - 事件结束时间。 
 * - `private Long page;` 
 * - 分页信息，在 ES 查询时无意义。 
 * - `private Long pageSize;` 
 * - 分页数据量信息，在 ES 查询时相同的 `requestId` 仅第一次传入有效。 
 * - `private String requestId;` 
 * - 用于 ES 查询，当数据量大于 10000 时使用。 
 * ### 总结 
 * - `CollectionEventPageContext` 类主要用于处理收集事件的分页逻辑，包含了一些与事件相关的属性如 ID、节点 ID、探针 ID、时间范围等。 
 * - 通过 Lombok 的注解减少了样板代码，使类更加简洁。 
 * - 一些属性（如 `page` 和 `pageSize`）在 ES 查询时可能没有实际用途，这些属性可能在其他场景下有用。 
 * 这个类的设计是为了提供一个结构化的对象来存储和传递与收集事件相关的分页信息。 
**/
package com.cyberpeace.integratation.model.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 收集事件分页上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CollectionEventPageContext implements Serializable {
    /**
     * id
     */
    @ApiModelProperty("场景ID")
    private String id;

    /**
     * 节点id列表
     */
    private String nodeIds;

    /**
     * 探针id
     */// 探针id, 内置探针为001 ~ 011
    private String probeId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 分页信息, es查询时无意义
     */
    private Long page;

    /**
     * 分页数据量信息, es查询时相同`requestId`仅第一次传入有效
     */
    private Long pageSize;

    /**
     * es查询使用, 供数据量>10000时获取
     */
    private String requestId;
}
