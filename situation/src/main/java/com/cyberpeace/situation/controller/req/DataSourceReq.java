package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据源请求
 *
 * @Author xuxz
 * @Date 5/12/2022 下午5:24
 */

@Data
public class DataSourceReq implements Serializable {

	/**
	 * 查询sql
	 */
	@ApiModelProperty("查询sql")
	private String sql;

	/**
	 * 数据源id
	 */
	@ApiModelProperty(value = "数据源id", required = true)
	private String resourceId;

	/**
	 * 表名称
	 */
	@ApiModelProperty("表名称")
	private String tableName;

	/**
	 * 数据库名称
	 */
	@ApiModelProperty("数据库名称")
	private String dbName;

	/**
	 * 自定义参数列表
	 */
	@ApiModelProperty("自定义参数列表")
	private String customParams;

	/**
	 * 构造函数
	 *
	 * @param sql          查询sql
	 * @param resourceId   资源id
	 * @param dbName       数据库名称
	 * @param tableName    表名称
	 * @param customParams 自定义参数列表
	 */
	public DataSourceReq(String sql, String resourceId, String dbName, String tableName,
			String customParams) {
		this.sql = sql;
		this.dbName = dbName;
		this.resourceId = resourceId;
		this.tableName = tableName;
		this.customParams = customParams;
	}
}
