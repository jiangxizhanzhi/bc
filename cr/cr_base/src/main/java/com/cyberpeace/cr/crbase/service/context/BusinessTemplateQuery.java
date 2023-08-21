package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.LocalStringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/*
 * 后台使用的查询类
 */
@ApiModel
@Data
public class BusinessTemplateQuery extends PageReqBase {

	@ApiModelProperty(value = "业务类型ID")
	private String type;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	private List<String> ids;

	public void setSearch(String search) {
		String newPattern = search;
		if (!StringUtils.isBlank(search)) {

			newPattern = LocalStringUtil.transfer(search);			

		}

		this.search = newPattern;
	}

	private Integer trainCategory;

	@ApiModelProperty("场景类型")
	private Integer sceneType;
}