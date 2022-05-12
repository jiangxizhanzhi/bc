package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@TableName("cr_eval_index_ref")
public class EvalIndexRef extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    /**
     * 指标项类型, 用于设置默认分值
     */
    private Integer type;

    /**
     * 指标项得分
     */
    private Integer score;

    /**
     * 指标等级
     */
    private Integer level;

    /**
     * 评估任务ID
     */
    private String recordId;

    /**
     * 使用的聚合算法ID
     */
    private String algorithmId;

    /**
     * 当前指标项下一级ID, 为json字符串
     */
    private String nextIds;

    /**
     * 计算开始时间
     */
    private Date startTime;

    /**
     * 计算结束时间
     */
    private Date endTime;

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


}
