package com.cyberpeace.evaluate.dao.entity;

import java.util.Date;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class TestInstance extends BaseTable {

	/**
	 * 评估实例状态：运行中
	 */
	public static final int STATUS_RUNNING = 1;

	/**
	 * 评估实例状态：结束
	 */
	public static final int STATUS_FINISH = 2;

	/**
	 * 评估实例状态：放弃此次测试
	 */
	public static final int STATUS_ABORT = 3;

	private String instanceId;

	private String infoId;

	private String sceneId;

	private Date startTime;

	private Date endTime;

	private Integer status;;

	private String result;

	private String resultName;

	private String resultDesc;
}
