package com.cyberpeace.cr.redvsblue.service.context;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SingleNodeAscription implements Serializable {

	private String nodeIp;

	private List<Integer> ascription;
}
