package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("p_metrics_indicator")
public class IndicatorMetrics {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private Integer metrics_id;

	private Integer indicatorId;

	private Integer parentId;
}
