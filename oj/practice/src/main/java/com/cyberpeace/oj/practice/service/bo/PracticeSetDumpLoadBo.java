package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 实践设置转 储加载博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PracticeSetDumpLoadBo implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 封面
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 练习类型
	 *
	 * @see Integer
	 */
	private Integer practiceType;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	private Boolean isPublic;

	/**
	 * 创建者
	 *
	 * @see String
	 */
	private String createUserId;

	/**
	 * 习题数据
	 *
	 * @see List<? extends PracticeDumpLoadBo>
	 */
	private List<? extends PracticeDumpLoadBo> practice;

	/**
	 * 文件映射关系 key=就文件地址 value=新文件地址(相对路径)
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> filePathMap;

}
