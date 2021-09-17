package com.cyberpeace.evaluate.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.evaluate.dao.entity.EstimateAlgorithm;

import lombok.Data;

@Data
@ApiModel("分页查询算法列表信息")
public class AlgorithmPageReq extends PageReqBase implements Serializable {

	/**算法类型 */
	@ApiModelProperty("分类：1=格式化算法；2=检测模型算法；3=评分算法；4=聚合算法（指标项算法、评估项算法)")
	private String type;

	public void validate() {
		if (StringUtils.isBlank(type)) {
			type = EstimateAlgorithm.ALL;
		}
	}
}
