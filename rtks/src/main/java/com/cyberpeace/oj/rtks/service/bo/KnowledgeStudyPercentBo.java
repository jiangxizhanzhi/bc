package com.cyberpeace.oj.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 知识点完成度统计模型
 * @author yinqingrong
 */
@Data
public class KnowledgeStudyPercentBo implements Serializable {

	/**
	 * 知识点编号
	 */
	private String knowledgeId;

	/**
	 * 知识点名称
	 */
	private String knowledgeName;

	/**
	 * 知识点完成度
	 */
	private String percent;
}
