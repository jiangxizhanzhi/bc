package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Data;

@Data
public class LoadTemplate {

	private List<LoadTemplateData> items;

	private List<LoadMission> templateQuestions;

	private Object file;

}
