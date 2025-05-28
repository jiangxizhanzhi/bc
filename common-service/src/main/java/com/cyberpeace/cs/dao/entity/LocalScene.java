/**
 * ### 代码分析 
 * #### 类概述 
 * `LocalScene` 是一个实体类，定义了与“本地场景”相关的数据结构和逻辑。它继承自 `BaseTable`，并使用了 MyBatis-Plus 的注解来映射数据库表和字段。 
 * #### 主要功能 
 * 1. **数据字段**： 
 * - 包含多个字段描述场景的状态、属性、时间和事件信息。 
 * - 如 `resourceId`（资源ID）、`sceneStatus`（场景状态）、`businessCode`（业务代号）等。 
 * 2. **枚举类**： 
 * - `SceneStatusEnum`：定义了场景的不同状态（如未启动、启动、删除、暂停），并提供了状态转换逻辑。 
 * - `BusinessCode`：定义了业务代码的枚举值及其对应的中文和英文描述。 
 * - `DestroyMode`：定义了销毁模式（手动或自动）。 
 * 3. **数据库映射**： 
 * - 使用了 `@TableName` 注解将类映射到数据库表 `local_scene`。 
 * - 字段通过 `@TableId` 和 `@TableField` 注解与数据库字段一一对应。 
 * 4. **扩展性**： 
 * - 提供了 `record` 字段（虽然未存储在数据库中）用于记录录屏功能的状态。 
 * - `meta` 字段以 JSON 格式存储额外信息。 
 * #### 总结 
 * `LocalScene` 是一个典型的 ORM 实体类，结合了 MyBatis-Plus 的注解和 Java 枚举类，用于管理本地场景的数据和状态。其设计清晰，便于扩展和维护。 
**/
package com.cyberpeace.cs.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.constant.BaseI18n;
import com.cyberpeace.cs.constant.SceneEvent;
import com.cyberpeace.cs.constant.SceneEventStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 当地场景
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("local_scene")
public class LocalScene extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * mp的场景id，只有在场景启动后才会有该值
	 */
	private String mpSceneId;

	/**
	 * 业务代号
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

	/**
	 * 用户id
	 */
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

	/**
	 * 场景attr
	 */
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

	public enum SceneStatusEnum implements GenericEnum {

		/***/
		INIT(0, "未启动", SceneEvent.DEFAULT, SceneEventStatus.INITIALIZE),
		START(1, "启动", SceneEvent.CREATE, SceneEventStatus.FINISH),
		DESTROY(2, "删除", SceneEvent.CLOSE, SceneEventStatus.FINISH),
		PAUSE(3, "暂停", SceneEvent.PAUSE, SceneEventStatus.FINISH);

		@Getter
		private final Integer code;

		@Getter
		private final String desc;

		private final SceneEvent event;

		private final SceneEventStatus eventStatus;

		SceneStatusEnum(Integer code, String desc, SceneEvent event, SceneEventStatus eventStatus) {
			this.code = code;
			this.desc = desc;
			this.event = event;
			this.eventStatus = eventStatus;
		}

		public Boolean isCurrent(Integer event, Integer eventStatus) {
			return event.intValue() == this.event.getCode().intValue()
					&& eventStatus.intValue() == this.eventStatus.getCode().intValue();
		}

		public Boolean isClose() {
			return this.code.intValue() == DESTROY.code.intValue();
		}

	}

	public enum BusinessCode implements GenericEnum, BaseI18n {
		/***/
		DEFAULT(0, "默认"),
		PRACTICE(110, "练习"),
		LESSON(120, "课程"),
		CLASSROOM(121, "课程表"),
		EXAM(130, "考试"),
		LESSON_PRACTICE(140, "课程练习"),
		SINGLE_TRAINING(211, "单兵训练", "Individual Training"),
		LIVE_VULN(212, "漏洞"),
		TEAM_TRAINING(213, "团队训练", "Team Training"),
		RED_VS_BLUE(214, "红蓝对抗", "Cyber Wargame"),
		WEAPON_TEST(216, "武器"),
		SYSTEM_TEST(217, "系统"),
		SOFTWARE_TEST(221, "软件测试"),
		TEST_SCENE(222, "后台测试"),
		COURSE_TRAINING(150, "课程训练"),
		SKILLS_ASSESSMENT(231, "技能评估"),
		TACTICAL_ASSESSMENT(232, " 战术评估"),
		COMPREHENSIVE_ASSESSMENT(233, "综合评估"),
		TACTICAL_TRAINING(234, "战术训练"),
		COMPREHENSIVE_TRAINING(235, "综合训练"),
		RANGE_EVALUATION(236, "靶场测评");

		@Getter
		private final Integer code;

		@Getter
		private final String chinese;

		@Getter
		private final String english;

		@Override
		public String getDesc() {
			return getMsg();
		}

		BusinessCode(Integer code, String chinese) {
			this.code = code;
			this.chinese = chinese;
			this.english = this.name();
		}

		BusinessCode(Integer code, String chinese, String english) {
			this.code = code;
			this.chinese = chinese;
			this.english = english;
		}

	}

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
