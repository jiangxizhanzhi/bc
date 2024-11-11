package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 练习设置转 储负荷博
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticeSetDumpLoadBo implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 封面
	 */
	private String logo;

	/**
	 * 练习类型
	 */
	private Integer practiceType;

	/**
	 * 是否公开
	 */
	private Boolean isPublic;

	/**
	 * 创建者
	 */
	private String createUserId;

	/**
	 * 习题数据
	 */
	private List<? extends PracticeDumpLoadBo> practice;

	/**
	 * 文件映射关系 key=就文件地址 value=新文件地址(相对路径)
	 */
	private Map<String, String> filePathMap;

}
