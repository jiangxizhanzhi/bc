package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class EvalTestInstance extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 试验id
	 */
	private String testId;

	/**
	 * 试验名称
	 */
	private String testName;

	/**
	 * 测评实例名称
	 */
	private String instanceName;

	/**
	 * 启动场景id
	 */
	private String localSceneId;

	/**
	 * 中台场景id
	 */
	private String mpSceneId;

	/**
	 * 拓扑id
	 */
	private String copyedTopoId;

	/**
	 * 开始时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 创建人id
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