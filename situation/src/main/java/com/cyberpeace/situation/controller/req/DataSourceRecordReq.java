package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据源请求
 *
 * @Author xuxz
 * @Date 3/11/2022 上午10:30
 */
@Data
public class DataSourceRecordReq implements Serializable {

	/**
	 * 数据源id
	 */
	@NotBlank(groups = { Update.class })
	private String resourceId;

	/**
	 * 数据源名称
	 */
	@ApiModelProperty("数据源名称")
	@NotBlank(groups = { Create.class })
	private String name;

	/**
	 * 数据源类型
	 */
	@ApiModelProperty("数据源类型")
	@NotBlank(groups = { Create.class })
	private String type;

	/**
	 * 数据源连接地址
	 */
	@ApiModelProperty("连接url")
	@NotBlank(groups = { Create.class })
	private String connectAddress;

	/**
	 * 数据源连接端口
	 */
	@ApiModelProperty("端口")
	@NotBlank(groups = { Create.class })
	private String port;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
	private String userName;

	/**
	 * 密码
	 */
	@ApiModelProperty("密码")
	private String password;

	/**
	 * 连接状态
	 */
	@ApiModelProperty("连接状态")
	private Boolean connectStatus;
}
