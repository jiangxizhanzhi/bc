package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

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
    @ApiModelProperty("描述")
    private String description;

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