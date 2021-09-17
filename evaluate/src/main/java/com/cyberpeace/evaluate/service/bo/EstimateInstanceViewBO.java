package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cyberpeace.evaluate.dao.entity.NameValueItem;

import lombok.Data;

@Data
public class EstimateInstanceViewBO implements Serializable {

	/**评估实例id */
	private String estimateInstanceId;

	/**评估id */
	private String estimateId;

	/**场景id */
	private String sceneId;

	/**评估开始时间 */
	private Date startTime;

	/**评估结束时间 */
	private Date endTime;

	/**评估结果*/
	private List<NameValueItem> resultList;

	/**状态（评估中、评估停止） */
	private Integer status;

	/**名称*/
	private String estimateName;

	/**描述*/
	private String estimateDesc;
}
