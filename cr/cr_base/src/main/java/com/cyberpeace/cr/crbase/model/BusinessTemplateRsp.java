package com.cyberpeace.cr.crbase.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;

import lombok.Data;

/**
 * 靶场模板公共信息模型
 * */
@Data
@TableName("cr_business_template")
public class BusinessTemplateRsp extends BusinessTemplate {

	private Integer nodesNum;

}
