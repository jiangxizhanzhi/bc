package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 纸记录结果
 *
 * @author SN
 * @see Serializable
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/08
 */
@Data
public class PaperRecordRes implements Serializable {

	/**
     * Paper Time （造纸时间）
     *
     * @see String
     */
	@ApiModelProperty("组卷时间")
	private String paperTime;

	/**
     * 结束了
     *
     * @see Boolean
     */
	@ApiModelProperty("试卷是否已完成")
	private Boolean isFinish;

	/**
     * 纸id
     *
     * @see String
     */
	@ApiModelProperty("组卷id")
	private String paperId;
}
