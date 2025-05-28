/**
 * 这段代码定义了一个名为 `Course` 的实体类，用于表示课程信息。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.dao.entity` 
 * - **继承**: 继承自 `AuthTable`，可能用于权限管理。 
 * - **注解**: 
 * - `@TableName("oj_course")`: 表明该实体类对应的数据库表名是 `oj_course`。 
 * - `@Getter` 和 `@Setter`: 自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **resourceId**: 课程资源 ID，使用 UUID 生成。 
 * - **builtin**: 是否为内置课程。 
 * - **name**: 课程名称。 
 * - **description**: 课程描述。 
 * - **categoryId**: 分类编号。 
 * - **logo**: 课程图标路径。 
 * - **outline**: 课程大纲。 
 * - **difficulty**: 课程难度（枚举类型）。 
 * - **rank**: 课程等级。 
 * - **attack**: 攻击知识点。 
 * - **defense**: 防御知识点。 
 * - **createUserId**: 创建人编号。 
 * - **modifyUserId**: 修改人编号。 
 * - **isPublic**: 是否公开。 
 * - **courseType**: 课程类型。 
 * - **heat**: 热度。 
 * - **lecturer**: 讲师。 
 * - **score**: 学分。 
 * - **courseNum**: 课程编号。 
 * - **role**: 岗位。 
 * - **lessonCount**: 课时数量（非持久化字段）。 
 * ### 枚举 
 * - **Difficulty**: 定义了课程难度的枚举类型，包含三个值：`PRIMARY`、`MIDDLE` 和 `HIGH`，分别对应初级、中级和高级。 
 * ### 总结 
 * 该类主要用于表示课程信息，并提供了详细的属性来描述课程的各种特征。通过继承 `AuthTable` 可能是为了增加一些权限相关的功能。枚举 `Difficulty` 提供了课程难度的标准，使得课程难度的表示更加规范和易于维护。 
**/
package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see AuthTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_course")
public class Course extends AuthTable {

    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课程编号")
	private String resourceId;

    /**
     * 内置
     *
     * @see Boolean
     */
    @ApiModelProperty("是否内置")
	private Boolean builtin;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
	private String name;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty("描述")
	private String description;

    /**
     * 类别id
     *
     * @see String
     */
    @ApiModelProperty("分类编号")
	private String categoryId;

    /**
     * 图标
     *
     * @see String
     */
    @ApiModelProperty("图片路径")
	private String logo;

    /**
     * 大纲
     *
     * @see String
     */
    @ApiModelProperty("大纲")
	private String outline;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
	private Integer difficulty;

    /**
     * 等级
     *
     * @see Integer
     */
	@ApiModelProperty("等级")
	private Integer rank;

	@ApiModelProperty("攻击知识点")
	private String attack;

	@ApiModelProperty("防御知识点")
	private String defense;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建人编号")
	private String createUserId;

    /**
     * 修改用户id
     *
     * @see String
     */
    @ApiModelProperty("修改人编号")
	private String modifyUserId;

    /**
     * 是否公开
     *
     * @see Boolean
     */
    @TableField("public")
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

    /**
     * 课程类型
     *
     * @see Integer
     */
    @ApiModelProperty("课程类型")
	private Integer courseType;

    /**
     * 热
     *
     * @see Integer
     */
    @ApiModelProperty("热度")
	private Integer heat;

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
     * 课程数量
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
	private String courseNum;

    /**
     * 岗位
     *
     * @see Integer
     */
    private Integer role;

    /**
     * 课计数
     *
     * @see Integer
     */
    @TableField(exist = false)
	@ApiModelProperty("课时数量")
	private Integer lessonCount;

	public enum Difficulty implements GenericEnum {

		PRIMARY(0, "初级"), MIDDLE(1, "中级"), HIGH(2, "高级");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Difficulty(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
