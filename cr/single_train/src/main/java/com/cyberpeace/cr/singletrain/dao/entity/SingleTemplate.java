package com.cyberpeace.cr.singletrain.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_single_template")
public class SingleTemplate extends BaseTable {

	private String businessTemplateId;

	private String preSkills;

	private String trainSkills;

	private String subType;

	private String role;

	private String missionSchemeId;

}
