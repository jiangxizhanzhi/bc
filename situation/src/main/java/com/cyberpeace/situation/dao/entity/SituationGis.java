package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 27/2/2023 上午11:58
 */
@Data
@TableName("situation_gis_config")
public class SituationGis extends BaseTable implements Serializable {

	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String config;

	private String templateId;

	private String thumbnail;
}
