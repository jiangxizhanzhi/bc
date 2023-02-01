package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SingleNodeAscription implements Serializable {

	private String nodeIp;

	private ArrayList<Integer> ascription;
}
