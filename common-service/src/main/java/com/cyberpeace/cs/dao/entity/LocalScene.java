package com.cyberpeace.cs.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("local_scene")
public class LocalScene extends BaseTable {

	/**
	 *
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * mp的场景id，只有在场景启动后才会有该值
	 */
	private String mpSceneId;

	/**
	 *
	 */
	private String businessCode;

	/**
	 * mp场景启动时间，只有在场景启动后才会有该值
	 */
	private Date startTime;

	/**
	 *
	 */
	private Date endTime;

	/**
	 *
	 */
	private Date destoryTime;

	/**
	 *
	 */
	private String destoryMode;

	/**
	 *
	 */
	private String businessInstanceId;

	/**
	 *
	 */
	private Integer sceneStatus;

	/**
	 *
	 */
	private String pauseTime;

	private String topoId;

	private String userId;

	public enum SceneStatusEnum implements GenericEnum {

		INIT(0, "未启动"), START(1, "启动");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private SceneStatusEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
