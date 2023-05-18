package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationRecordResByDay implements Serializable {

	@ApiModelProperty("时间")
	private String time;

	@ApiModelProperty("解出记录列表")
	private List<OperationRecordRes> recordList;
}
