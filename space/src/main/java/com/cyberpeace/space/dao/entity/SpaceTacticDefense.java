package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间战法防御
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceTacticAttack
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_tactic_defense")
public class SpaceTacticDefense extends SpaceTacticAttack {

	@TableId(type = IdType.ASSIGN_ID)
	private String resourceId;

}
