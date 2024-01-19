package com.cyberpeace.scene.service.bo.scene;

import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.scene.service.bo.TopologyElement;

import lombok.Data;

@Data
public class ElementStatistics {

	private List<TopologyElement> networks = new ArrayList<>();

	private List<TopologyElement> nodes = new ArrayList<>();

}
