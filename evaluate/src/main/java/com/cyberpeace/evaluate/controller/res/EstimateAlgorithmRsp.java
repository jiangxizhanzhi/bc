package com.cyberpeace.evaluate.controller.res;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.AlgorithmParam;
import lombok.Data;

@Data
public class EstimateAlgorithmRsp {

	private String estimateAlgorithmId;

	/**
	 * 算法名称
	 */
	private String algorithmName;

	/**
	 * 算法内容
	 */
	private String algorithmContent;

	/**
	 * 自定义参数  占位符
	 */
	private List<AlgorithmParam> params;

	/**
	 * 来源类型：1=预置；2=自定义；
	 */
	private String sourceType;

	/**
	 * 分类：1=格式化算法；2=匹配算法；3=评分算法；4=聚合算法；
	 */
	private String algorithmType;

	/*** 类型扩展
	 */
	private String typeExtend;

	/**
	 * 语言类型：1=java;2=python;
	 */
	private String languageType;

	/**
	 * 算法描述
	 */
	private String algorithmDesc;

	/**
	 * 参数个数
	 */
	private Integer paramCount;

}
