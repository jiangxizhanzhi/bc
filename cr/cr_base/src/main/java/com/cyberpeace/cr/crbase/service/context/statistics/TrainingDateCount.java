package com.cyberpeace.cr.crbase.service.context.statistics;

import lombok.Data;

import java.io.Serializable;

/**
 * 训练统计中间体
 */
@Data
public class TrainingDateCount implements Serializable {

	private String date;

	private Integer completed = 0;

	private Integer all = 0;

}
