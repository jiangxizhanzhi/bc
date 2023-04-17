package com.cyberpeace.situation.model;

import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.controller.res.LinkStateRes;
import lombok.Data;

import java.io.Serializable;

@Data
public class AverageSceneData implements Serializable {

	private LinkStateRes linkStateRes;

	private ElementStateRes elementStateRes;
}
