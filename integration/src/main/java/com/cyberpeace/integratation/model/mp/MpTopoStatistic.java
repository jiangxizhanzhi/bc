package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

@Data
public class MpTopoStatistic implements Serializable {

	private String csceneId;

	private Integer firewallCount;

	private Integer routerCount;

	private Integer switchCount;

	private Integer terminalCount;
}
