package com.cyberpeace.cr.redvsblue.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("cr_rb_instance")
public class RbRoom extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String templateId;

	private Integer status;

	private String name;

	private String createUserId;

	private String sceneId;

	private Integer subType;

	private String description;

	private String preSkill;

	private String trainSkill;

	private Integer difficulty;

	private Date startTime;

	private Date endTime;

	private String sceneManagerId;
}
