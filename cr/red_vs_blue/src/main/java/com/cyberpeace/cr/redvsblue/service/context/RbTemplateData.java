package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 红蓝模板数据
 */
@Data
public class RbTemplateData implements Serializable {

	private String resourceId;

	private String topoId;

	private String name;

	private Integer category;

	private String description;

	private Integer businessType;

	private String businessTemplateId;

	private String createUserId;

	private Date createTime;

	private Integer difficulty;

	private String preSkill;

	private String trainSkill;

	private String roleConfig;

	private Integer subType;

	private Integer durationTime;

	private String logo;

}
