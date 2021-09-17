package com.cyberpeace.evaluate.dao.entity;

import java.util.Date;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class EstimateVariableModelOut extends BaseTable {

	String variableModelOutId;

	/**场景id*/
	String sceneId;

	/**主机id*/
	String hostId;

	/**变量项id*/
	String variableId;

	/**评估实例id*/
	String estimateInstanceId;

	/**发生时间（周期事件记录周期结束时间，单点时间记录时间点）*/
	Date recordTime;

	/**类型（周期、单点）*/
	String recordType;

	Integer value;

}
