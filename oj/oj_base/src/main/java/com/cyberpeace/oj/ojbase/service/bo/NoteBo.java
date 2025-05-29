package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 注意 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `NoteBo` 的 Java 类，属于 `com.cyberpeace.oj.ojbase.service.bo` 包。这个类的主要目的是封装关于“笔记”（note）的相关信息，并且使用 Lombok 库来简化 getter 和 setter 方法的生成。 
 * ### 主要特点和功能 
 * 1. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：这两个注解会为该类中的所有属性自动生成 getter 和 setter 方法，简化了代码编写。 
 * 2. **Swagger 注解**： 
 * - `@ApiModel`：用于描述该对象是一个模型，通常在 Swagger 文档中使用。 
 * - `@ApiModelProperty`：用于给模型的属性添加描述，有助于生成 API 文档。 
 * 3. **实现 Serializable 接口**： 
 * - `implements Serializable`：表明该类的对象可以被序列化，即可以转换成字节流以便于存储或在网络上传输。 
 * ### 属性说明 
 * - `resourceId`：笔记资源编号，字符串类型。 
 * - `noteType`：笔记类型，整型，1 表示笔记，2 表示实验报告。 
 * - `businessType`：资源类型，整型，分别表示课程、课时、练习和课后练习。 
 * - `content`：笔记内容，字符串类型。 
 * - `userId`：创建笔记的用户 ID，字符串类型。 
 * - `businessId`：对应的资源编号，字符串类型。 
 * - `createTime`：创建时间，日期类型。 
 * - `updateTime`：更新时间，日期类型。 
 * - `isSubmit`：实验报告是否已提交，布尔类型。 
 * ### 总结 
 * `NoteBo` 类主要用于存储和传输与笔记相关的数据，包括笔记的基本信息、类型、创建者、以及与之关联的资源信息等。通过使用 Lombok 和 Swagger 相关注解，提高了代码的可读性和维护性，同时便于文档生成和 API 接口测试。 
**/
@Getter
@Setter
@ApiModel
public class NoteBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("笔记资源编号")
    private String resourceId;

    /**
     * 注释类型
     *
     * @see Integer
     */
    @ApiModelProperty("笔记类型 1=笔记 2=实验报告")
    private Integer noteType;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty("资源类型 1=课程 2=课时 3=练习 4=课后练习")
    private Integer businessType;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty("笔记内容")
    private String content;

    /**
     * 用户id
     *
     * @see String
     */
    @ApiModelProperty("创建人")
    private String userId;

    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty("对应的资源编号")
    private String businessId;

    /**
     * 创建时间
     *
     * @see Date
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     *
     * @see Date
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 是提交
     *
     * @see Boolean
     */
    @ApiModelProperty("实验报告是否提交")
    private Boolean isSubmit;

}

