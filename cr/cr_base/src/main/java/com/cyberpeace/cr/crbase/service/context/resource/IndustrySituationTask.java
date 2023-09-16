package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 行业态势yaml任务
 * @author SN
 * @date 2023/09/13
 */
@Data
public class IndustrySituationTask implements Serializable {

	private String name;

	private Integer ts_id;

	private String ts_type;

	private String ts_desc;

	private List<String> target_device;

	private String source_device;

	private String area;

	private String extra_effect;
}
