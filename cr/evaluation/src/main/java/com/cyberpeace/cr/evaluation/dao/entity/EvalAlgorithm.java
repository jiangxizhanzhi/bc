/**
 * 这段代码定义了一个名为 `EvalAlgorithm` 的实体类，用于表示评估算法的信息。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.dao.entity` 
 * - **继承**: 继承自 `BaseTable`，可能是为了实现一些通用的数据库表操作功能。 
 * - **注解**: 
 * - `@TableName("cr_eval_algorithm")`: 指定该实体类对应的数据库表名为 `cr_eval_algorithm`。 
 * ### 主要字段 
 * 1. **resource_id**: 唯一标识符，使用 UUID 生成。 
 * 2. **name**: 算法的名称。 
 * 3. **description**: 算法的基本原理。 
 * 4. **instructions**: 使用说明。 
 * 5. **advantage**: 运用优势。 
 * 6. **applyType**: 算法的应用类型。 
 * 7. **type**: 算法的类型（枚举类型）。 
 * 8. **algorithmLanguage**: 算法使用的编程语言（枚举类型）。 
 * 9. **code**: 算法的具体实现代码。 
 * 10. **state**: 算法的状态（0: 正常，1: 编辑中）。 
 * 11. **params**: 可配置参数。 
 * 12. **paramsNum**: 可配置参数的数量。 
 * 13. **createUserId**: 创建者的用户 ID。 
 * 14. **mode**: 算法模型（枚举类型）。 
 * 15. **builtin**: 是否为内置算法（枚举类型）。 
 * ### 枚举类型 
 * - **Type**: 定义了算法的几种类型： 
 * - `SCORING_ALGORITHM`: 评分算法。 
 * - `FORMAT_ALGORITHM`: 格式化算法。 
 * - `MATCHING_ALGORITHM`: 匹配算法。 
 * - `AGGREGATING_ALGORITHM`: 聚合算法。 
 * - **Mode**: 定义了算法模型的几种类型： 
 * - `SCORING_ALGORITHM`: 其他算法。 
 * - `FORMAT_ALGORITHM`: 层次分析法。 
 * - **Builtin**: 定义了算法是否为内置算法的两种状态： 
 * - `SCORING_ALGORITHM`: 内置算法。 
 * - `FORMAT_ALGORITHM`: 用户自定义算法。 
 * ### 方法 
 * - 枚举类型中的 `valid` 方法用于验证给定的整数是否是有效的枚举值。 
 * - 各个枚举类型的构造函数用于初始化 `code` 和 `desc` 字段。 
 * - `@ApiModelProperty` 注解用于 Swagger 文档生成，描述每个字段的含义。 
 * ### 总结 
 * 这个类主要用来表示和管理评估算法的相关信息，并通过枚举类型定义了一些固定的状态和类型。这些信息可能被用于数据库操作、API 文档生成等场景。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @TableName cr_eval_algorithm
 */
@TableName(value ="cr_eval_algorithm")
@Getter
@Setter
public class EvalAlgorithm extends BaseTable {
    

    @TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     */
	@ApiModelProperty("基本原理")
	private String description;

	@ApiModelProperty("使用说明")
	private String instructions;

	@ApiModelProperty("运用优势")
	private String advantage;

    @ApiModelProperty("算法应用类型")
    private String applyType;

    /**
     * 格式化算法：1，匹配算法：2，评分算法：3，聚合算法：4
     */
    @ApiModelProperty("算法类型")
    private Integer type;

    /**
     * 
     */
    @ApiModelProperty("算法语言")
    private Integer algorithmLanguage;

    /**
     * 算法语言
        1：python
     */
    @ApiModelProperty("算法内容")
    private String code;

	/**
	 * 状态
	 0：代表正常，1：代表编辑过，页面看不到
	 */
	@ApiModelProperty("算法状态。0：代表正常，1：代表编辑过，页面看不到")
	private Integer state = 0;

    /**
     * 
     */
    @ApiModelProperty("算法可配置参数")
    private String params;

    /**
     * 
     */
    @ApiModelProperty("算法可配置参数数量")
    private Integer paramsNum;

    /**
     * 创建人
     */
    @ApiModelProperty("创建者ID")
    private String createUserId;

	public enum Type implements GenericEnum {

		SCORING_ALGORITHM(1, "评分算法"), FORMAT_ALGORITHM(2, "格式化算法"), MATCHING_ALGORITHM(3,
				"模型匹配算法"), AGGREGATING_ALGORITHM(4, "聚合算法");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public static  boolean valid(Integer consumer){
            Type[] values = Type.values();
            for(Type type : values) {
                if(type.getCode().equals(consumer)){
                    return true;
                }
            }
            return false;
        }

	}

    @ApiModelProperty("算法模型")
    private Integer mode;

    public enum Mode implements GenericEnum {

        SCORING_ALGORITHM(0, "其他算法"), FORMAT_ALGORITHM(1, "层次分析法");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        private Mode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }

    @ApiModelProperty("是否为内置算法")
    private Integer builtin;

    public enum Builtin implements GenericEnum {

        SCORING_ALGORITHM(0, "内置算法"), FORMAT_ALGORITHM(1, "用户自定义算法");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        private Builtin(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }
}