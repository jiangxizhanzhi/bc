package com.cyberpeace.common.model.user;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 组织信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class OrgInfo implements Serializable {

	/**
	 * 组织id，不要使用自增数字编号（必须）
	 */
	private String id;

	/**
	 * 组织名
	 */
	private String name;

	/**
	 * 子级组织
	 */
	private List<OrgInfo> children = Lists.newArrayList();

	/**
	 * 父级组织id
	 */
	private String parent;

	/**
	 * 组织全程
	 */
	private String path;
}
