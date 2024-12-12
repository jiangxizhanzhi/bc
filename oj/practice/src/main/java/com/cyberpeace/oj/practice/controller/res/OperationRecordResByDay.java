package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 实操记录结果由日
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationRecordResByDay implements Serializable {

	/**
	 * 时间
	 *
	 * @see String
	 */
	@ApiModelProperty("时间")
	private String time;

	/**
	 * 记录列表
	 *
	 * @see List<OperationRecordRes>
	 */
	@ApiModelProperty("解出记录列表")
	private List<OperationRecordRes> recordList;
}
