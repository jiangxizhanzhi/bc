package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("space_tactic_defense")
public class SpaceTacticDefense extends SpaceTacticAttack {

	@TableId(type = IdType.ASSIGN_ID)
	private String resourceId;

}
