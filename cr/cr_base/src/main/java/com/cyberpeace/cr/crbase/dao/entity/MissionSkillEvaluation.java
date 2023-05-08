package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 任务技能评估
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@TableName("cr_mission_skillevaluation")
public class MissionSkillEvaluation {

	private Integer id;

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String studentId;

	private String roomId;

	private String skill;

	private Integer score;

	private Date createTime;

}
