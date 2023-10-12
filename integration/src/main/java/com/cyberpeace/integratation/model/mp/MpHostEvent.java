package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MpHostEvent implements Serializable {

	private String eventName;

	private String triggerType;

	private String actionType;

	private String targetNodeId;

	private String targetNodeName;

	private String targetNodeIp;

	private List<String> att_cks;

	private List<String> att_ck_names;

	private String hostEventType;

	private String hostEventDesc;

	private Date time;

}
