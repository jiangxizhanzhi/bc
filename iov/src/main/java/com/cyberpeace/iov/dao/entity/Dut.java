package com.cyberpeace.iov.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 被测件
 * @author SN
 * @date 2024/08/06
 */
@Data
@TableName("iov_dut")
public class Dut extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String name;

	/**
	 * ecu单元
	 */
	private Integer ecu;

	/**
	 * 车型
	 */
	private Integer model;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 域类型
	 */
	private Integer area;

	/**
	 * 接口类型
	 */
	private Integer interfaceType;

	/**
	 * 接口参数
	 */
	private String interfaceParam;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 是否启用
	 */
	private Boolean enabled;

}
