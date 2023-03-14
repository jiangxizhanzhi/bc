package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_db_access")
public class SituationDbAccess extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	private String dbName;

	private String tableName;

	private String operationType;

	private String operationTime;

	private String operationDetail;

	private String createUserId;

}
