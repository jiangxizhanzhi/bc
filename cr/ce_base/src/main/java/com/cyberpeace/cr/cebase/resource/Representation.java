package com.cyberpeace.cr.cebase.resource;

import lombok.Data;

/**
 * 单个场景模板待外部数据模型
 * CrEvent的字段中去掉 "id", "resource_id", "type", "create_user", "create_time"
 */
@Data
public class Representation {

	private String name;

	private String description;

	private Integer category;

	private Integer difficult;

}
