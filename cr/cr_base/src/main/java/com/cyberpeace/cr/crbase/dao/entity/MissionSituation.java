package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_mission_situation")
public class MissionSituation extends BaseTable implements Serializable {

    @TableId(type = IdType.NONE, value = "mission_template_id")
	private String missionTemplateId;

	private String name;

	private Integer tsId;

	private String tsType;

	private String tsDesc;

	private String targetDevice;

	private String sourceDevice;

	private String area;

	private String extraEffect;
}
