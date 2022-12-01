package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("situation_attack_message_history")
public class AttackMessageHistory extends AttackMessage{

	//是否已恢复
	private Integer isHistory;

	//防御等级
	private Integer defenseLevel;

}
