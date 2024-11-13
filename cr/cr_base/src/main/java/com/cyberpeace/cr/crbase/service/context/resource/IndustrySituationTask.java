package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 行业态势yaml任务
 *
 * @author SN
 * @date 2023/09/13
 */
@Data
public class IndustrySituationTask implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 态势id
	 */
	private Integer ts_id;

	/**
	 * 态势类型
	 */
	private String ts_type;

	/**
	 * 态势描述
	 */
	private String ts_desc;

	/**
	 * 目标设备
	 */
	private List<String> target_device;

	/**
	 * 源设备
	 */
	private String source_device;

	/**
	 * 面积
	 */
	private String area;

	/**
	 * 额外动效
	 */
	private String extra_effect;
}
