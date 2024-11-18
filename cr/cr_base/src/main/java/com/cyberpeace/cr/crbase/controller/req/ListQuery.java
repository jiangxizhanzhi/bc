package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import com.cyberpeace.common.util.LocalStringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see PageReqBase
 * @since 1.0.0
 *//*
 * 前台使用的查询类
 */
@ApiModel
@Data
public class ListQuery extends PageReqBase {

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练类别ID")
	private String category;

	/**
	 * 搜索
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "检索参数")
	private String search;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 难
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficult;

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	private List<String> ids;

	public void setSearch(String search) {
		String newPattern = search;
		if (!StringUtils.isBlank(search)) {

			newPattern = LocalStringUtil.transfer(search);

		}

		this.search = newPattern;
	}
}