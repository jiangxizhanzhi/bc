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
@TableName("iov_last_raw_packet")
public class LastRawPacket extends BaseTable {

	@ApiModelProperty("自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String userId;

	private String raw;

	private Integer type;
}
