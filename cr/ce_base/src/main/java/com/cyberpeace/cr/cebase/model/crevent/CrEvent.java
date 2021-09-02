package com.cyberpeace.cr.cebase.model.crevent;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.suit.BaseEntity;

import lombok.Data;

/**
 * 靶场模板公共信息模型
 * */
@Data
@TableName("cr_event")
public class CrEvent extends BaseEntity {

	private String name;

	private String description;

	private Integer category;

	/**
	 * 原名type 这个字段不由前端输入
	 * */
	private Integer ceType;

	/**
	 * 难度
	 * 原名difficult
	 * */
	private Integer difficulty;

	// duration_time = models.PositiveIntegerField("时长", null=True, blank=True)
	// people_number = models.PositiveIntegerField("人数", null=True, blank=True)
	// pre_skill = models.CharField("前置技能", max_length=200, blank=True, null=True)
	// train_skill = models.CharField("训练技能", max_length=200, blank=True, null=True)
	// create_user = models.CharField(max_length=64, null=True, blank=True)
	// shared_users = models.TextField(verbose_name="共享用户", blank=True)

	public interface CeType {

		int TRAINING_SINGLE = 1;

		int VULN_VALIDATE = 2;

		int TRAINING_TEAM = 3;

		int RED_VS_BLUE = 4;

		int WEAPON_ASSESS = 6;

		int SYSTEM_ASSESS = 7;
	}
}
