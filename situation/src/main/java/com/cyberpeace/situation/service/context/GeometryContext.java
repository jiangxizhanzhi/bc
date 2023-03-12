package com.cyberpeace.situation.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GeometryContext implements Serializable {

	private List<List<List<List<Float>>>> coordinates;

}
