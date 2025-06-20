package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;


/**
 * 靶场通知请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CrNoticeReq` 的 Java 类，用于表示靶场通知请求体。以下是对其的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`CrNoticeReq` 
 * - 包名：`com.cyberpeace.cr.crbase.controller.req` 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 2. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Javax Validation 的注解（如 `@NotNull`, `@NotBlank`, `@Size`）来定义字段的约束条件。 
 * - 使用了 Hibernate Validator 的 `@Length` 注解。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档生成提供字段描述。 
 * 3. **字段说明**： 
 * - `id`: 整数类型，标识唯一性。 
 * - `resourceId`: 字符串类型，UUID 标识资源。 
 * - `createUserId`: 字符串类型，表示创建者的用户 ID。 
 * - `context`: 字符串类型，存储公告内容。 
 * - `role`: 整数类型，指定公告的目标对象。 
 * - `roomId`: 字符串类型，关联的房间 ID。 
 * - `isTop`: 整数类型，指示是否置顶。 
 * - `isRb`: 布尔类型，指示是否与红蓝团队相关。 
 * 4. **用途**： 
 * - 该类主要用于接收和传递靶场通知的相关信息，适用于 RESTful API 的请求体。 
 * 总结：这是一个简洁且功能明确的数据传输对象（DTO），通过注解增强了数据验证和文档生成的能力。 
**/
@Getter
@Setter
public class CrNoticeReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Integer id;
    /**
     * uuid
     */
    @ApiModelProperty("uuid")
    private String resourceId;
    /**
     * 创建人id
     */
    @ApiModelProperty("创建人id")
    private String createUserId;
    /**
     * 公告内容
     */
    @ApiModelProperty("公告内容")
    private String context;
    /**
     * 公告对象
     */
    @ApiModelProperty("公告对象:1全部 2红方 3蓝方")
    private Integer role;
    /**
     * 房间id
     */
    @ApiModelProperty("房间id")
    private String roomId;

    /**
     * 在顶部
     */
    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    /**
     * 是 RB
     */
    @ApiModelProperty("是否红蓝 0否 1是")
    private Boolean isRb;

}

