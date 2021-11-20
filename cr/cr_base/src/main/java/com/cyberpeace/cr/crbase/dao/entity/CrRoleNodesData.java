package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CrRoleNodesData implements Serializable {

	private List<CrRoleNode> assetNodes;

	private List<CrRoleNode> visibleNodes;
}
