package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 习题数据导出Bo类
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PracticeDumpLoadBo implements Serializable {

	/**
	 * 分类名称
	 */
	private String categoryName;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 公开
	 */
	private Boolean isPublic;

	/**
	 * 所属习题集
	 */
	private String practiceSetId;

	/**
	 * 内容
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
	 */
	private String answer;

	/**
	 * 难度, 目前仅操作题使用 0-初级, 1-中级, 2-高级
	 */
	private Integer difficulty;

	/**
	 * 知识点
	 */
	private String knowledge;

	/**
	 * 分值
	 */
	private Double score;

}
