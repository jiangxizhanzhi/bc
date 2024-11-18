package com.cyberpeace.cr.evaluation.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 评估任务步骤
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_eval_task_step")
public class EvalTaskStep extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 任务id
	 *
	 * @see String
	 */
	private String taskId;

	/**
	 * 阶段
	 *
	 * @see Integer
	 */
	private Integer stage;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 过程
	 *
	 * @see String
	 */
	private String process;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	public enum Type implements GenericEnum {

		MANUAL(1, "手动"), AUTO(2, "自动");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Type(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
