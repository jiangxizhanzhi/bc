/**
 * 这段代码定义了一个名为 `CourseWebBo` 的 Java 类，该类用于表示前端课程的基本信息。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **注解**: 
 * - `@Getter` 和 `@Setter`: Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModel`: Swagger 注解，用于描述模型对象，此处描述为“前端课程基本信息”。 
 * ### 属性 
 * - `resourceId`: 资源编号 (String) 
 * - `courseNum`: 课程编号 (String) 
 * - `name`: 课程名称 (String) 
 * - `categoryId`: 课程方向ID (String) 
 * - `directionName`: 课程方向名称 (String) 
 * - `logo`: 课程海报 (String) 
 * - `difficulty`: 课程难度 (Integer) 
 * - `rank`: 等级 (Integer) 
 * - `createUserId`: 创建人ID (String) 
 * - `createUserName`: 创建人名称 (String) 
 * - `lecturer`: 讲师 (String) 
 * - `score`: 学分 (Integer) 
 * - `courseType`: 课程类型 (Integer) 
 * - `ebook`: 电子书 (Integer) 
 * - `lessonCount`: 课程课时数量 (Integer) 
 * - `courseDuration`: 课程课时时长 (Integer) 
 * - `isPublic`: 是否公开 (Boolean) 
 * - `role`: 岗位 (Integer) 
 * - `videoCount`: 视频课数量 (Integer) 
 * - `videoDuration`: 视频长度 (Integer) 
 * - `builtin`: 是否内置 (Boolean) 
 * ### 总结 
 * - **功能**: `CourseWebBo` 类主要用于封装前端展示的课程基本信息。 
 * - **特性**: 使用了 Lombok 注解简化了 getter 和 setter 方法的编写，并通过 Swagger 注解提供了文档说明。 
 * - **用途**: 通常在 Web 应用中作为传输对象（DTO）使用，用于前后端数据交互。 
 * 这段代码设计清晰，结构合理，适用于典型的 Web 应用场景。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程 Web bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("前端课程基本信息")
public class CourseWebBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
	private String resourceId;

    /**
     * 课程数量
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
	private String courseNum;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
	private String name;

    /**
     * 类别id
     *
     * @see String
     */
    @ApiModelProperty("课程方向")
	private String categoryId;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("课程方向名称")
	private String directionName;

    /**
     * 图标
     *
     * @see String
     */
    @ApiModelProperty("课程海报")
	private String logo;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("课程难度，type=51")
	private Integer difficulty;

    /**
     * 等级
     *
     * @see Integer
     */
    @ApiModelProperty("等级, type=9")
    private Integer rank;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("课程创建人")
	private String createUserId;

    /**
     * 创造用户名称
     *
     * @see String
     */
    @ApiModelProperty("创建人名称")
	private String createUserName;

    /**
     * 讲师
     *
     * @see String
     */
    @ApiModelProperty("讲师")
	private String lecturer;

    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty("学分")
	private Integer score;

    /**
     * 课程类型
     *
     * @see Integer
     */
    @ApiModelProperty("课程类型")
	private Integer courseType;

    /**
     * 电子书
     *
     * @see Integer
     */
    @ApiModelProperty("课程类型")
	private Integer ebook;

    /**
     * 课计数
     *
     * @see Integer
     */
    @ApiModelProperty("课程课时数量")
	private Integer lessonCount;

    /**
     * 课程时间
     *
     * @see Integer
     */
    @ApiModelProperty("课程课时时长")
	private Integer courseDuration;

    /**
     * 是否公开
     *
     * @see Boolean
     */
    @ApiModelProperty("是否公开")
	private Boolean isPublic;

    /**
     * 岗位
     *
     * @see Integer
     */
    @ApiModelProperty("岗位")
	private Integer role;

    @ApiModelProperty(value = "视频课数量")
    private Integer videoCount;

    @ApiModelProperty(value = "视频长度")
    private Integer videoDuration;
    /**
     * 内置
     *
     * @see Boolean
     */
    @ApiModelProperty("是否内置")
    private Boolean builtin;

}
