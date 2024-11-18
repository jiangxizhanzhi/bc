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
 * 评估任务
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_eval_task")
public class EvalTask extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 模板id
	 *
	 * @see String
	 */
	private String templateId;

	/**
	 * 模板名称
	 *
	 * @see String
	 */
	private String templateName;

	/**
	 * 任务名称
	 *
	 * @see String
	 */
	private String taskName;

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	private String localSceneId;

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	private String mpSceneId;

	/**
	 * 已复制拓扑id
	 *
	 * @see String
	 */
	private String copyedTopoId;

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

	/**
	 * 启用录制
	 *
	 * @see Boolean
	 */
	private Boolean enableRecording;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	public enum Status implements GenericEnum {

		RUNNING(1, "运行中"), FINISHED(2, "已结束");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		Status(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
