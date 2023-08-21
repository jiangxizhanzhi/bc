package com.cyberpeace.cr.crbase.dao.entity;

import lombok.Data;

/**
 * 靶场模板公共信息类
 * */
@Data
public class BusinessTemplateData extends BusinessTemplate {

	private Integer nodesNum;

	private Integer resourceOccupiedNums;

	private Integer sceneType;

}
