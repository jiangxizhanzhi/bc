package com.cyberpeace.situation.dao.entity;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午9:50
 */
@Data
@TableName("situation_datasource_api")
public class ApiRequest extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@NotBlank(groups = { Create.class, Update.class })
	@ApiModelProperty("api名称")
	private String name;

	@ApiModelProperty("api描述")
	private String description;

	@NotBlank(groups = { Create.class, Update.class })
	@ApiModelProperty("url")
	private String requestUrl;

	@NotBlank(groups = { Create.class, Update.class })
	@ApiModelProperty("请求方法")
	private String requestMethod;

	@ApiModelProperty("连接状态")
	private Boolean connectStatus;

	@ApiModelProperty("请求参数")
	private String requestParams;

	@ApiModelProperty("请求头")
	private String requestHeaders;

	@ApiModelProperty("返回值所属key值")
	private String responseKey;

	@ApiModelProperty("返回参数")
	private String responseParams;

	@ApiModelProperty("返回数据")
	private String responseDemo;

	@NotBlank(groups = { Create.class })
	@ApiModelProperty("创建人")
	private String createUserId;
}
