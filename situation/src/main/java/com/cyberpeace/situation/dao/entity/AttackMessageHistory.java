package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("situation_attack_message_history")
public class AttackMessageHistory extends AttackMessage{

	private Integer isHistory;

	private Integer defenseLevel;

}
