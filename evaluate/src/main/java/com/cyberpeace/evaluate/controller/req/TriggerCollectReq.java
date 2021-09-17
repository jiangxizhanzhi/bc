package com.cyberpeace.evaluate.controller.req;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public class TriggerCollectReq implements Serializable {

	public void validate() {
		Preconditions.checkArgument(StringUtils.isNotBlank(instanceId), "instance_id null");
		Preconditions.checkArgument(StringUtils.isNotBlank(toolId), "tool_id null");
		Preconditions.checkArgument(StringUtils.isNotBlank(itemId), "item_id null");
	}

	private String instanceId;

	private String toolId;

	private String itemId;
}
