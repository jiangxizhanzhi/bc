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
@TableName("cr_eval_record")
public class EvalRecord extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private String description;

    /**
     * 评估任务状态
     */
    private Integer status;

    /**
     * 评估任务得分
     */
    private Integer score;

    /**
     * 评估任务指标分数
     */
    private Integer indexScore;
    /**
     * 评估任务最终分数
     */
    private Integer finalScore;

    /**
     * 引用指标体系ID
     */
    private String systemId;

    /**
     * 靶场测评实例ID
     */
    private String taskId;

    /**
     * 测评模板ID
     */
    private String  templateId;

    /**
     * 评估任务进行中的错误信息收集
     */
    private String message;

    /**
     * 开始时间（评估任务采集数据时间范围）
     */
    private Date startTime;

    /**
     * 结束时间（评估任务采集数据时间范围）
     */
    private Date endTime;

    /**
     * 评估开始时间
     */
    private Date evalTime;

    /**
     * 评估结束时间
     */
    private Date evalEndTime;

    /**
     * 评估任务类型 (1:新增 2:导入 )
     */
    private Integer type;

    /**
     * 评分等级
     */
    private String grade;

    public enum GradeType implements GenericEnum {

        PROFESSIONAL_ABILITY(1, "专业能力"), TEAMWORK(2, "团队协作"), EXECUTION(3,
                "执行力"), LEADERSHIP(4, "领导力"),COMPREHENSIVE_EVALUATION(5, "综合评价");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        GradeType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }


    public enum Type implements GenericEnum {
        ADD(1, "新增"),
        IMPORT(2, "导入");
        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        Type(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Status implements GenericEnum {
        NOT_START(1, "未评估"),
        RUNNING(2, "评估中"),
        END(3, "已评估"),
        EXCEPTION(4, "异常");

        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum InvokeType implements GenericEnum {
        INDEX(1, "指标项"), VARIABLE(2, "变量项");

        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        InvokeType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
