package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import lombok.Data;

/**
 * 培训统计
 * 训练统计中间体
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TrainingCount implements Serializable {

	private Integer completed = 0;

	private Integer uncompleted = 0;
}
