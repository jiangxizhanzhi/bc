package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

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
 * @Date 3/11/2022 上午10:01
 */
@Data
@TableName("situation_datasource_db")
public class DataSourceRecord extends BaseTable implements Serializable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("数据源名称")
	@NotBlank(groups = { Create.class, Update.class })
	private String name;

	@ApiModelProperty("数据源类型")
	@NotBlank(groups = { Create.class, Update.class })
	private String type;

	@ApiModelProperty("数据源连接地址")
	@NotBlank(groups = { Create.class, Update.class })
	private String connectAddress;

	@ApiModelProperty("数据源连接端口")
	@NotBlank(groups = { Create.class, Update.class })
	private String port;

	@ApiModelProperty("数据库用户")
	private String userName;

	@ApiModelProperty("数据库密码")
	private String password;

	@ApiModelProperty("创建了")
	private String createUserId;

	@ApiModelProperty("连接状态")
	private Boolean connectStatus;
}
