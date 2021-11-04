package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import lombok.Data;

@Data
public class BusinessTemplateContext extends BusinessTemplate {

	private Integer nodesNum;
}
