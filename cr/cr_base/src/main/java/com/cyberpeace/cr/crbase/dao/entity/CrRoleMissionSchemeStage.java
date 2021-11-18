package com.cyberpeace.cr.crbase.dao.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CrRoleMissionSchemeStage implements Serializable {

	private String stage;

	private List<String> missions;

	private List<String> dynamic;
}
