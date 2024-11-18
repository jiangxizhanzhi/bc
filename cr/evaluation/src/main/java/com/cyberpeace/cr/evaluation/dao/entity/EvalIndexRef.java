package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * eval 索引 ref
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_eval_index_ref")
public class EvalIndexRef extends BaseTable {
    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 指标项类型, 用于设置默认分值
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 指标项得分
     *
     * @see Integer
     */
    private Integer score;

    /**
     * 指标等级
     *
     * @see Integer
     */
    private Integer level;

    /**
     * 评估任务ID
     *
     * @see String
     */
    private String recordId;

    /**
     * 使用的聚合算法ID
     *
     * @see String
     */
    private String algorithmId;

    /**
     * 算法实际额外入参
     *
     * @see String
     */
    private String algorithmParams;

    /**
     * 专家评分设置值（三级指标包含）类型输入
     *
     * @see Integer
     */
    private Integer specialistValue;

    /**
     * 专家评分设置类型（三级指标包含）百分比/周期/整数值
     *
     * @see Integer
     */
    private Integer specialistType;

    /**
     * 专家评分设置属性（三级指标包含）效益/成本/固定/偏离
     *
     * @see Integer
     */
    private Integer specialistAttr;

    /**
     * 专家评分设置中心值（三级指标包含）固定/偏离-中心值
     *
     * @see Integer
     */
    private Integer specialistAttrValue;

    /**
     * 当前指标项下一级ID, 为json字符串
     *
     * @see String
     */
    private String nextIds;

    /**
     * 计算开始时间
     *
     * @see Date
     */
    private Date startTime;

    /**
     * 计算结束时间
     *
     * @see Date
     */
    private Date endTime;

    /**
     * 评估进度
     *
     * @see Integer
     */
    private Integer progress;

    /**
     * 矩阵内容
     *
     * @see String
     */
    private String matrixData;

    public enum Type implements GenericEnum {
        SYSTEM(1, "系统评分"), SPECIALIST(2, "专家评分");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Type(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Level implements GenericEnum {
        AGGREGATION(4, "聚合项"),
        VARIABLE(5, "变量项");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Level(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum DataAttr implements GenericEnum {
        BENEFIT(1, "效益型"), COST(2, "成本型"), FIXED(3, "固定型"), DEVIATE(4, "偏离型");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        DataAttr(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum DataType implements GenericEnum {
        PERCENT(1, "百分比"), CYCLE(2, "周期"), INTEGER(3, "整数型");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        DataType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Progress implements GenericEnum {
        NOT_START(1, "未开始"),
        EXCEPTION(2, "异常"),
        END(10, "结束");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Progress(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
