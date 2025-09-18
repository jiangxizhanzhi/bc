package com.cyberpeace.cr.crbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 模板请求体
 *
 * @author SN
 * @date 2024/11/12
 */
@ApiModel
@Data
public class TemplateReq extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类别ID")
	private String type;

    @ApiModelProperty(value = "xd项目模板类型")
    private Integer xdTemplateType;

	@ApiModelProperty(value = "xd项目模板类别")
	private Integer xdTemplateCategory;

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

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private String difficult;


    public void setXd_template_type(Integer xdTemplateType) {
        this.xdTemplateType = xdTemplateType;
    }

	public void setXd_template_category(Integer xdTemplateCategory) {
		this.xdTemplateCategory = xdTemplateCategory;
	}

}
