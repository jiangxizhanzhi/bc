package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class AlgorithmParamLink extends BaseTable {

	/**关联编号 */
	private String algorithmParamLinkId;

	/**算法编号 */
	private String algorithmId;

	/**参数编号 */
	private String paramId;

	/**参数顺序 */
	private Integer serialNumber;

}
