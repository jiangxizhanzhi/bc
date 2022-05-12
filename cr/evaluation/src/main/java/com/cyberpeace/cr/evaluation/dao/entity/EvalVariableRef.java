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
@TableName("cr_eval_variable_ref")
public class EvalVariableRef extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    /**
     * 变量项得分
     */
    private Integer score;

    /**
     * 场景主机节点ID
     */
    private String deviceId;

    /**
     * 主机使用探针ID
     */
    private String probeId;

    /**
     * 评估任务ID
     */
    private String recordId;

    /**
     * 原变量项ID
     */
    private String originVariableId;

    /**
     * 进度
     */
    private Integer progress;

    /**
     * 格式化开始时间
     */
    private Date formatStartTime;

    /**
     * 格式化结束时间
     */
    private Date formatEndTime;

    /**
     * 匹配开始时间
     */
    private Date matchStartTime;

    /**
     * 匹配结束时间
     */
    private Date matchEndTime;

    /**
     * 评分开始时间
     */
    private Date markStartTime;

    /**
     * 评分结束时间
     */
    private Date markEndTime;

    public enum Progress implements GenericEnum {
        NOT_START(1, "未开始"),
        START(2, "开始/数据同步中"),
        FORMATTING(3, "格式化进行中"),
        MATCHING(4, "匹配进行中"),
        MARKING(5, "匹配进行中"),
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
