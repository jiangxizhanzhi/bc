package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.model.CommonContext;
import lombok.Data;

@Data
@TableName("situation_attack_type")
public class AttackType extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	private Integer actionType;

	private String cnDesc;

	private String enDesc;

	private String keyWord;

	private Boolean isParalysis;

	private Boolean isExploit;

	private Boolean isVirus;

	public String getMsg() {
		return CommonContext.isChinese() ? cnDesc : enDesc;
	}
}
