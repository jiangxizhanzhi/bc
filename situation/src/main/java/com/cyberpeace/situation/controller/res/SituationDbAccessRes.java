package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SituationDbAccessRes extends SituationComponentsAnimationAddReq {

	@ApiModelProperty()
	private String resourceId;

	@ApiModelProperty("数据库名称")
	private String dbName;

	@ApiModelProperty("表名")
	private String tableName;

	@ApiModelProperty("操作类型")
	private String operationType;

	@ApiModelProperty("操作时间")
	private String creatTime;

	@ApiModelProperty("操作详情")
	private String operationDetail;

}
