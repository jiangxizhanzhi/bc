package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 测评记录
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_eval_record")
public class EvalRecord extends BaseTable {
    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
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
     * 引用指标体系ID
     */
    private String systemId;

    /**
     * 靶场测评实例ID
     */
    private String taskId;

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
