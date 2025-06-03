package com.cyberpeace.iov.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("iov_vehicle_type")
public class VehicleType extends BaseTable {

	@ApiModelProperty("自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

}
