package com.cyberpeace.cr.crbase.service.context.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 行业态势yaml格式
 * @author SN
 * @date 2023/09/13
 */
@Data
public class IndustrySituationConfig implements Serializable {

	private List<IndustrySituationTask> task;

	private List<String> area_path;
}
