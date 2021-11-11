package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import lombok.Data;

@Data
public class MissionSchemeObject {

	String stage;

	List<Mission> missions;

	List<String> dynamic;
}
