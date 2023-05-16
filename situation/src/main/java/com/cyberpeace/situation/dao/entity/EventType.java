package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_event_type")
public class EventType extends BaseTable implements Serializable {

	@ApiModelProperty("编码")
	private String code;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("父节点")
	private String parentNode;

}
