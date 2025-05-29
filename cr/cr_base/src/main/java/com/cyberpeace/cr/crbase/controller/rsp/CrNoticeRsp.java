package com.cyberpeace.cr.crbase.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.UserData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 靶场通知响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CrNoticeRsp` 的 Java 类，用于表示靶场通知的响应数据模型。以下是对其的简要分析： 
 * ### 主要功能 
 * - **目标**：该类旨在封装靶场通知的相关信息，便于前端或其他模块获取和展示通知数据。 
 * - **用途**：作为 API 响应的一部分，传递给调用者。 
 * ### 关键特性 
 * 1. **继承与实现** 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化存储。 
 * 2. **注解** 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - Swagger 的 `@ApiModelProperty` 注解为每个字段提供了描述性信息，有助于生成 API 文档。 
 * 3. **字段说明** 
 * - `context`：公告内容，通过 Swagger 描述为“公告内容”。 
 * - `createTime`：创建时间，记录通知的生成时间。 
 * - `role`：公告对象类型，可选值为 1（全部）、2（红方）、3（蓝方），描述通知的目标群体。 
 * - `isTop`：是否置顶标志，0 表示否，1 表示是。 
 * - `userData`：用户数据对象，包含与用户相关的详细信息。 
 * - `createUserId`：创建用户的唯一标识。 
 * - `resourceId`：资源 ID，可能关联到某个具体的通知来源。 
 * 4. **设计模式** 
 * - **数据传输对象 (DTO)**：此类符合 DTO 模式，专门用于在不同层之间传输数据。 
 * ### 总结 
 * `CrNoticeRsp` 是一个简洁且功能明确的数据传输类，适合用于靶场系统中通知模块的响应数据封装。通过 Lombok 和 Swagger 注解提升了开发效率和文档友好性。 
**/
@Getter
@Setter
public class CrNoticeRsp implements Serializable {

    /**
     * 上下文
     */
    @ApiModelProperty("公告内容")
    private String context;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 岗位
     */
    @ApiModelProperty("公告对象:1全部 2红方 3蓝方")
    private Integer role;

    /**
     * 在顶部
     */
    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    /**
     * 用户数据
     */
    @ApiModelProperty("用户信息")
    private UserData userData;

    /**
     * 创造用户id
     */
    private String createUserId;

    /**
     * 资源id
     */
    private String resourceId;
}

