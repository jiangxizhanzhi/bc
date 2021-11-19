package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.AddTemplateMissionScheme;

import lombok.Data;

@Data
public class ColorMission {

	private Integer color;

	private List<AddTemplateMissionScheme> schemeData;

	private Integer template;

}
