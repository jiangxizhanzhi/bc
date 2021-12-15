package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import lombok.Data;

@Data
public class CrRoleNodesData implements Serializable {

	private List<CrRoleNode> assetNodes;

	private List<CrRoleNode> visibleNodes;
}
