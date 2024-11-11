package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 实操记录结果由日
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationRecordResByDay implements Serializable {

	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private String time;

	/**
	 * 记录列表
	 */
	@ApiModelProperty("解出记录列表")
	private List<OperationRecordRes> recordList;
}
