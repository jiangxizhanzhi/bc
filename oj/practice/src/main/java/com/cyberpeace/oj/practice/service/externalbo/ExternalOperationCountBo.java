package com.cyberpeace.oj.practice.service.externalbo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cyberpeace.oj.practice.entity.PracticeOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 外部实操计数Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ExternalOperationCountBo implements Serializable {

	public ExternalOperationCountBo(Map<Integer, List<PracticeOperation>> typeMap) {
		initStatic(typeMap);
		initDynamic(typeMap);
		initRandom(typeMap);
	}

	public ExternalOperationCountBo() {
		this.dynamics = 0;
		this.statics = 0;
		this.randoms = 0;
	}

	/**
	 * 静态习题数量
	 */
	@ApiModelProperty("静态习题数量")
	private Integer statics;

	/**
	 * 动态习题数量
	 */
	@ApiModelProperty("动态习题数量")
	private Integer dynamics;

	/**
	 * 随机习题数量
	 */
	@ApiModelProperty("随机习题数量")
	private Integer randoms;

	private void initRandom(Map<Integer, List<PracticeOperation>> typeMap) {
		this.randoms = typeMap
				.getOrDefault(PracticeOperation.DynamicFlag.RANDOM.getCode(), new ArrayList<>())
				.size();
	}

	private void initDynamic(Map<Integer, List<PracticeOperation>> typeMap) {
		this.dynamics = typeMap
				.getOrDefault(PracticeOperation.DynamicFlag.DYNAMIC.getCode(), new ArrayList<>())
				.size();
	}

	private void initStatic(Map<Integer, List<PracticeOperation>> typeMap) {
		this.statics = typeMap
				.getOrDefault(PracticeOperation.DynamicFlag.STATIC.getCode(), new ArrayList<>())
				.size();
	}

}
