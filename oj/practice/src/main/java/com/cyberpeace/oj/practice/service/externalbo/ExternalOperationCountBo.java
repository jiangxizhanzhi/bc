package com.cyberpeace.oj.practice.service.externalbo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 外部实操计数博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
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
	 * 静力学
	 *
	 * @see Integer
	 */
	@ApiModelProperty("静态习题数量")
	private Integer statics;

	/**
	 * 动力学
	 *
	 * @see Integer
	 */
	@ApiModelProperty("动态习题数量")
	private Integer dynamics;

	/**
	 * 随机数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("随机习题数量")
	private Integer randoms;

	private void initRandom(Map<Integer, List<PracticeOperation>> typeMap) {
		int randoms = typeMap
				.getOrDefault(PracticeOperation.DynamicFlag.RANDOM.getCode(), new ArrayList<>())
				.size();
		this.randoms = randoms;
	}

	private void initDynamic(Map<Integer, List<PracticeOperation>> typeMap) {
		int dynamics = typeMap
				.getOrDefault(PracticeOperation.DynamicFlag.DYNAMIC.getCode(), new ArrayList<>())
				.size();
		this.dynamics = dynamics;
	}

	private void initStatic(Map<Integer, List<PracticeOperation>> typeMap) {
		int statics = typeMap
				.getOrDefault(PracticeOperation.DynamicFlag.STATIC.getCode(), new ArrayList<>())
				.size();
		this.statics = statics;
	}

}
