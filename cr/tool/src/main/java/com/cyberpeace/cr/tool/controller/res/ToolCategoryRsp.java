package com.cyberpeace.cr.tool.controller.res;
import lombok.Data;

import java.io.Serializable;

@Data
public class ToolCategoryRsp implements Serializable {

	private String cnName;

	private String enName;

	private String resourceId;

}
