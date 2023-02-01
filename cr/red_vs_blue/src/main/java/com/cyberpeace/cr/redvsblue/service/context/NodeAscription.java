package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class NodeAscription implements Serializable {

	private String roomId;

	private Map<String, String> nodeData;

	private List<SingleNodeAscription> nodeAscriptionList;

	public List<Integer> getAscription(String nodeIp){
		for (SingleNodeAscription singleNodeAscription: nodeAscriptionList){
			if (singleNodeAscription.getNodeIp().equals(nodeIp)){
				return singleNodeAscription.getAscription();
			}
		}
		return null;
	}
}
