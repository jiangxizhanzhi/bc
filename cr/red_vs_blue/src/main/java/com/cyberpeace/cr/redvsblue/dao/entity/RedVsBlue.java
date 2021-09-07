package com.cyberpeace.cr.redvsblue.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("ce_red_vs_blue_redvsblue")
public class RedVsBlue {



	private String name;

	private String description;

	private Integer category;

	private String preSkill;

	private String trainSkill;

	private Date startTime;

	private Date endTime;

	private Integer difficult;

	private Date sceneStartTime;

	private Date sceneEndTime;

	private String template;

	private String sceneConfig;

	private String sceneFile;

	private String scene;

	private Integer status;

	private String createUser;

	private Date createTime;

	private String modifyUser;

	private Date modifyTime;

	private String agentStatus;
}
