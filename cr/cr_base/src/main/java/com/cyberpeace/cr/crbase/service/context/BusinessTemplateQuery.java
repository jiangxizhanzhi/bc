package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.LocalStringUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 业务模板查询
 *
 * @author SN
 * @date 2024/11/13
 *//*
 * 后台使用的查询类
 */
@ApiModel
@Data
public class BusinessTemplateQuery extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "业务类型ID")
	private String type;

	/**
	 * 搜索
	 */
	@ApiModelProperty(value = "检索参数")
	private String search;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;


    @ApiModelProperty(value = "xd项目模板类型")
    private Integer xdTemplateType;

    /**
	 * id列表
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