package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ColorMission<T> implements Serializable {

	private Integer color;

	private List<MissionSchemeObject<T>> schemeData;

	private Integer template;

}
