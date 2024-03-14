package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.evaluation.service.impl.bo.BaseTemplateData;
import com.cyberpeace.cr.evaluation.service.impl.bo.TechniqueWarfare;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EvalTemTemplateReq implements Serializable {

	@ApiModelProperty("基础数据")
	@NotNull
	private BaseTemplateData baseData;

	@ApiModelProperty("场景拓扑")
	private SceneConfigData sceneConfigData;

	@ApiModelProperty("任务方案")
	private TechniqueWarfare techniqueWarfare;
}
