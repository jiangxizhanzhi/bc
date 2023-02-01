package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

@Data
public class NodeAscription implements Serializable {

	private String sceneId;

	private Map<String, String> nodeData;

	private ArrayList<SingleNodeAscription> nodeAscriptionList;

	public ArrayList<Integer> getAscription(String nodeIp){
		for (SingleNodeAscription singleNodeAscription: nodeAscriptionList){
			if (singleNodeAscription.getNodeIp().equals(nodeIp)){
				return singleNodeAscription.getAscription();
			}
		}
		return null;
	}
}
