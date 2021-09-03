package com.cyberpeace.cr.singletrain.model.template;

import com.cyberpeace.cr.cebase.model.crevent.CrEventModifyInput;

import lombok.Data;

@Data
public class TemplateModifyInput extends TemplateInput {

	private CrEventModifyInput crEvent;
}
