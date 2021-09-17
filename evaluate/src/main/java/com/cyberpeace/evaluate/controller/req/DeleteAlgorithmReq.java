package com.cyberpeace.evaluate.controller.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class DeleteAlgorithmReq implements Serializable {

	/**删除的算法编号 英文逗号分割 */
	@ApiModelProperty("删除的算法编号 英文逗号分割")
	private String algorithm_ids;

	public String getAlgorithmIds() {
		return algorithm_ids;
	}

	public void setAlgorithm_ids(String algorithm_ids) {
		this.algorithm_ids = algorithm_ids;
	}
}
