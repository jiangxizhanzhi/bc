package com.cyberpeace.cr.evaluation.dao.entity.rb;

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

import java.util.Date;

@Data
@TableName("cr_eval_local_scene")
public class CrEvalLocalScene extends BaseTable{

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
		RED_TRAINING(1, "红队训练"),
		BLUE_TRAINING(2, "蓝队训练"),
		PRACTICE(110, "练习"),
		LESSON(120, "课程"),
		CLASSROOM(121, "课程表"),
		EXAM(130, "考试"),
		LESSON_PRACTICE(140, "课程练习"),
		SINGLE_TRAINING(211, "单兵训练", "Individual Training"),
		LIVE_VULN(212, "漏洞"),
		TEAM_TRAINING(213, "红队训练", "RED Team Training"),
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
		RANGE_EVALUATION(236, "靶场测评"),
		BLUE_TEAM_TRAINING(237, "蓝队训练", "BLUE Team Training"),
		DAILY_TRAINING(238, "日常练习", "Daily Training");

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
