package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @Author xuxz
 * @Date 3/11/2022 上午9:58
 */
@Data
@TableName("situation_datasource_drive")
public class DataSourceTemplate implements Serializable {

	/**
	 * 数据库类型
	 */
	private String type;

	/**
	 * 数据源
	 */
	private String dataSource;

	/**
	 * 连接url
	 */
	private String connectUrl;

	private String connectDBUrl;

}
