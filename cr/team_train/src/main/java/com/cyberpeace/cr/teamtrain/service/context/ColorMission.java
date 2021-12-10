package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;

import lombok.Data;

@Data
public class ColorMission<T> {

	private Integer color;

	private List<MissionSchemeObject<T>> schemeData;

	private Integer template;

}
