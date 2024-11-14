package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 试卷记录总结结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PaperRecordSummaryRes implements Serializable {

    /**
     * 组卷id
     */
    @ApiModelProperty("组卷id")
	private String paperId;

    /**
     * 组卷时间
     */
    @ApiModelProperty("组卷时间")
	private String paperTime;

    /**
     * 题目数量
     */
    @ApiModelProperty("题目数量")
	private Integer count;

    /**
     * 是否已完成
     */
    @ApiModelProperty("是否已完成")
	private Boolean isFinish;

    /**
     * 答对数量
     */
    @ApiModelProperty("答对数量")
	private Integer right;

    /**
     * 答错数量
     */
    @ApiModelProperty("答错数量")
	private Integer wrong;

    /**
     * 未答数量
     */
    @ApiModelProperty("未答数量")
	private Integer notStart;

    /**
     * 正确率
     */
    @ApiModelProperty("正确率")
	private Integer accuracyRate;
}
