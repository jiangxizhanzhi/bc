package com.cyberpeace.evaluate.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.controller.res.EstimateVariableConfigBody;
import com.google.common.base.Preconditions;

import com.google.common.collect.Lists;
import lombok.Data;

@Data
public class EstimateInfoBody implements Serializable {

	/**评估名称 */
	private String name;

	/**评估描述 */
	private String desc;

	/**logo*/
	private String logo;

	/**拓扑id */
	private String topoId;

	/**评估方案id */
	private String estimateSchemeId;

	/**变量项配置*/
	private List<EstimateVariableConfigBody> variableConfigs = Lists.newArrayList();

	public void validate() {
		Preconditions.checkArgument(null != this.name, "名称 null");
		Preconditions.checkArgument(null != this.desc, "描述 null");
		Preconditions.checkArgument(null != this.logo, "logoid null");
		Preconditions.checkArgument(null != this.topoId, "topoId null");
		Preconditions.checkArgument(null != this.estimateSchemeId, "测评方案id null");
		Preconditions.checkArgument(0 != variableConfigs.size(),"变量配置 null");
	}

}
