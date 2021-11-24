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
	 * 场景实际停止时间，用户点了停止。
	 */
	private Date endTime;

	/**
	 * 只有限定时间等场景才会有销毁时间，到点自动调用场景销毁。
	 */
	private Date destoryTime;

	/**
	 * 0=手动；1=自动
	 */
	private Integer destoryMode;

	/**
	 * 业务id
	 */
	private String businessInstanceId;

	/**
	 * 场景状态
	 */
	private Integer sceneStatus;

	/**
	 * 计时场景使用，统计用户场景暂停了多久。
	 * 场景恢复后，destroyTime相应顺延。
	 */
	private String pauseTime;

	/**
	 * 场景使用的topoId
	 */
	private String topoId;

	private String userId;

	/**
	 * 场景存活时间（单位分钟）。
	 * 单兵等不限制场景时长等使用场景中，不需要设置该值。
	 */
	private Integer sceneTtl;

	public enum SceneStatusEnum implements GenericEnum {

		INIT(0, "未启动"), START(1, "启动"), DESTROY(2, "删除");

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
