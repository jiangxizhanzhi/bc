package com.cyberpeace.oj.practice.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 习题数据导出Bo类
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PracticeDumpLoadBo implements Serializable {

	private Boolean builtin;

	/**
	 * 分类名称
	 *
	 * @see String
	 */
	private String categoryName;

	/**
	 * 标题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 公开
	 *
	 * @see Boolean
	 */
	private Boolean isPublic;

	/**
	 * 所属习题集
	 *
	 * @see String
	 */
	private String practiceSetId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 答案 不同题型记录形式不同. 例: 单选-A, 多选-A|B, 判断-A, 填空-[
	 * {
	 * \"weight\":40,\"value\":\"A|a\"
	 * }
	 * ,
	 * {
	 * \"weight\":60,\"value\":\"B\"
	 * }
	 * ], 简答-纯文本")
	 *
	 * @see String
	 */
	private String answer;

	/**
	 * 难度, 目前仅操作题使用 0-初级, 1-中级, 2-高级
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 知识点
	 *
	 * @see String
	 */
	private String knowledge;

	/**
	 * 分值
	 *
	 * @see Double
	 */
	private Double score;

	private String courseCategoryId;

}
