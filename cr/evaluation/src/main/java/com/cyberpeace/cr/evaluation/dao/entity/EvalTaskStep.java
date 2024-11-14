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
 * 测评任务步骤
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_eval_task_step")
public class EvalTaskStep extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 阶段
	 */
	private Integer stage;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 过程
	 */
	private String process;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 启动时间
	 */
	private Date startTime;

	/**
	 * 结束时间
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
