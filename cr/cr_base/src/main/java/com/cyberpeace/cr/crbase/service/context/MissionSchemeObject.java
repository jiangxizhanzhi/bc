package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MissionSchemeObject<T> implements Serializable {

	private String stage;

	private List<T> missions;

	private List<String> dynamic;
}
