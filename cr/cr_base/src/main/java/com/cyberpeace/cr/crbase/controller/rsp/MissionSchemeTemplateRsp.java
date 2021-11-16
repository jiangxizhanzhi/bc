package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;

import lombok.Data;

@Data
public class MissionSchemeTemplateRsp {

	private Integer color;

	private Integer template;

	private List<MissionSchemeStage> combatConfig;

	private String config;

	private String createUser;

	private String description;

	private String repName;

	private String resourceId;

	private String title;
}
