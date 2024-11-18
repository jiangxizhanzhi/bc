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
 * 测评变量裁判
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_eval_variable_ref")
public class EvalVariableRef extends BaseTable {
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
     * 变量项得分
     *
     * @see Integer
     */
    private Integer score;


    /**
     * 变量项得分
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 场景主机节点ID
     *
     * @see String
     */
    private String deviceId;

    /**
     * 主机使用探针ID
     *
     * @see String
     */
    private String probeId;

    /**
     * 评估任务ID
     *
     * @see String
     */
    private String recordId;

    /**
     * 格式化算法ID
     *
     * @see String
     */
    private String formatAlgorithmId;

    /**
     * 格式化算法其它参数
     *
     * @see String
     */
    private String formatParams;

    /**
     * 匹配算法ID
     *
     * @see String
     */
    private String matchAlgorithmId;

    /**
     * 匹配算法其它参数
     *
     * @see String
     */
    private String matchParams;

    /**
     * 评分算法ID
     *
     * @see String
     */
    private String markAlgorithmId;

    /**
     * 评分算法其它参数
     *
     * @see String
     */
    private String markParams;

    /**
     * 进度
     *
     * @see Integer
     */
    private Integer progress;

    /**
     * 格式化开始时间
     *
     * @see Date
     */
    private Date formatStartTime;

    /**
     * 格式化结束时间
     *
     * @see Date
     */
    private Date formatEndTime;

    /**
     * 匹配开始时间
     *
     * @see Date
     */
    private Date matchStartTime;

    /**
     * 匹配结束时间
     *
     * @see Date
     */
    private Date matchEndTime;

    /**
     * 评分开始时间
     *
     * @see Date
     */
    private Date markStartTime;

    /**
     * 评分结束时间
     *
     * @see Date
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
