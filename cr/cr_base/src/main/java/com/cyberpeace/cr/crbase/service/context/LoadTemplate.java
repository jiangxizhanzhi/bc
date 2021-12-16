package com.cyberpeace.cr.crbase.service.context;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class LoadTemplate {

	private List<LoadTemplateData> items;

	private List<LoadMission> templateQuestions;

	private Map<String, String> file;

}
