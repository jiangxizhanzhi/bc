package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 任务技能评估
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@TableName("cr_mission_skillevaluation")
public class MissionSkillEvaluation {

	/**
	 * id
	 *
	 * @see Integer
	 */
	private Integer id;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 学员id
	 *
	 * @see String
	 */
	private String studentId;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

	/**
	 * 技能
	 *
	 * @see String
	 */
	private String skill;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	private Integer score;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	private Date createTime;

}
