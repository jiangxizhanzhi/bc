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
 * eval 变量 ref
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("cr_eval_variable_ref")
public class EvalVariableRef extends BaseTable {
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
     * 变量项得分
     */
    private Integer score;


    /**
     * 变量项得分
     */
    private Integer type;

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
     * 格式化算法ID
     */
    private String formatAlgorithmId;

    /**
     * 格式化算法其它参数
     */
    private String formatParams;

    /**
     * 匹配算法ID
     */
    private String matchAlgorithmId;

    /**
     * 匹配算法其它参数
     */
    private String matchParams;

    /**
     * 评分算法ID
     */
    private String markAlgorithmId;

    /**
     * 评分算法其它参数
     */
    private String markParams;

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

//    /**
//     * 执行轮次
//     */
//    private Integer exeCycle;

    public enum Progress implements GenericEnum {
        NOT_START(1, "未开始"),
        START(2, "开始/数据同步中"),
        FORMATTING(3, "格式化进行中"),
        MATCHING(4, "匹配进行中"),
        MARKING(5, "评分进行中"),
        EXCEPTION(6, "异常"),
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
