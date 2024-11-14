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
 * 测评任务
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_eval_task")
public class EvalTask extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 模板id
	 */
	private String templateId;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 当地场景id
	 */
	private String localSceneId;

	/**
	 * 中台场景id
	 */
	private String mpSceneId;

	/**
	 * 已复制拓扑id
	 */
	private String copyedTopoId;

	/**
	 * 启动时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 启用录制
	 */
	private Boolean enableRecording;

	/**
	 * 创建用户id
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
