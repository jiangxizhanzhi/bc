package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务技能评估
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_mission_skillevaluation")
public class MissionSkillEvaluation {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 学员id
	 */
	private String studentId;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 技能
	 */
	private String skill;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 创建时间
	 */
	private Date createTime;

}
