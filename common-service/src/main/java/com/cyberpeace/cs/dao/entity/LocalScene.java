package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

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
	private Integer businessCode;

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
	private Date destroyTime;

	/**
	 * 0=手动；1=自动
	 */
	private Integer destroyMode;

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
	private Date pauseTime;

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

	/**
	 * 场景节点数量
	 * 记录该值，避免重复请求mp
	 */
	private Integer nodeCount;

	/**
	 * 场景名
	 */
	private String sceneName;

	private Integer sceneAttr;

	/**
	 * 是否开启录屏功能，默认为 null
	 */
	@TableField(exist = false)
	private Boolean record = null;

	/**
	 * json格式。
	 * 目前保存appResourceId
	 * {
	 * "app_resource_id":"abcd"
	 * }
	 * <p>
	 * app_resource_id用于前端页面场景小球的路由。对于靶场而言=业务实例id；
	 * 实训习题=习题编号
	 * 实训课时=course_id/lesson_id
	 * 实训课后联系=course_id/lesson_id/paper_id
	 */
	private String meta;

	public enum DestroyMode implements GenericEnum {
		/***/
		MANUAL(1, "手动销毁"),
		AUTO(2, "自动销毁");

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		DestroyMode(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
