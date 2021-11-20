package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CrRoleJsonStructure extends CrRoleNodesData implements Serializable {

	private String name;

	private List<CrRoleMissionSchemeStage> missionsScheme;
}