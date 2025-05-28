/**
 * 这段代码定义了一个名为 `OperationPageReq` 的 Java 类，用于处理操作（实践）的分页请求。以下是该类的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.gateway.controller.req` 
 * - **类名**: `OperationPageReq` 
 * - **继承**: 继承自 `PageReqBase` 
 * ### 类属性 
 * - **category**: 字符串类型，表示所属分类查询。 
 * - **practiceSet**: 字符串类型，表示所属习题集查询。 
 * - **difficulty**: 整型，表示难度级别（0 表示初级，1 表示中级，2 表示高级）。 
 * - **userId**: 字符串类型，表示用户编号，并且必须提供（使用了 `@NotBlank` 注解）。 
 * ### 注解 
 * - **@Getter 和 @Setter**: Lombok 注解，用于生成 getter 和 setter 方法。 
 * - **@ApiModelProperty**: Swagger 注解，用于描述 API 接口中的字段信息。 
 * - **@NotBlank**: JSR 380 标准的注解，用于验证字段不能为空或空白字符串。 
 * ### 功能 
 * 这个类主要用于构建一个包含分页参数的操作请求对象，其中包含了类别、习题集、难度和用户ID等信息。它适用于需要对操作进行分页查询的场景，例如在某个应用中查看用户的操作记录时，可以根据这些参数来过滤和展示数据。 
 * ### 总结 
 * `OperationPageReq` 类是一个数据传输对象（DTO），用于接收前端传递过来的分页请求参数，并且通过各种注解确保了数据的有效性。 
**/
package com.cyberpeace.gateway.controller.req;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 实操分页请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class OperationPageReq extends PageReqBase {

    /**
     * 类别
     */
    @ApiModelProperty("所属分类查询")
    private String category;

    /**
     * 实践设置
     */
    @ApiModelProperty("所属习题集查询")
    private String practiceSet;

    /**
     * 难度
     */
    @ApiModelProperty("难度查询(0-初级, 1-中级, 2-高级)")
    private Integer difficulty;

    /**
     * 用户id
     */
    @ApiModelProperty("用户编号")
    @NotBlank(message = ErrorCode.USER_INFO_MISSING)
    private String userId;
}
