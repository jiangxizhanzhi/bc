package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class EstimateAlgorithm extends BaseTable {

	/** 格式化算法类型*/
	public static final String FORMAT = "1";

	/** 匹配算法类型*/
	public static final String MATCH = "2";

	/** 打分算法类型*/
	public static final String MARK = "3";

	/** 聚合算法类型*/
	public static final String AGGREGATE = "4";

	/** 全部算法*/
	public static final String ALL = "0";

	String estimateAlgorithmId;

	/**
	 * 算法名称
	 */
	String algorithmName;

	/**
	 * 算法内容
	 */
	String algorithmContent;

	/**
	 * 自定义参数  占位符
	 */
	String params;

	/**
	 * 来源类型：1=预置；2=自定义；
	 */
	String sourceType = "2";

	/**
	 * 分类：1=格式化算法；2=匹配算法；3=评分算法；4=聚合算法；
	 */
	String algorithmType;

	/*** 类型扩展
	 */
	String typeExtend;

	/**
	 * 语言类型：1=java;2=python;
	 */
	String languageType;

	/**
	 * 算法描述
	 */
	private String algorithmDesc;

	/**
	 * 参数个数
	 */
	private Integer paramCount;
}
