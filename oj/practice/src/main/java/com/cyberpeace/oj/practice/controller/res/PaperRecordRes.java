package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class PaperRecordRes implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
