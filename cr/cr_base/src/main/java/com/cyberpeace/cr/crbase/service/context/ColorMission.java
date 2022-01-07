package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class ColorMission<T> {

	private Integer color;

	private List<MissionSchemeObject<T>> schemeData;

	private Integer template;

}
