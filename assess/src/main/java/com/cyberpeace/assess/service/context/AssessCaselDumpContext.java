package com.cyberpeace.assess.service.context;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.assess.dao.entity.AssessCase;

import lombok.Data;

/**
 * 资源导出中间模型
 */
@Data
public class AssessCaselDumpContext<T> implements Serializable {

	private AssessCase assessCase;

	private Map<String, String> files;

	private Integer assessMaterialCount;
}
