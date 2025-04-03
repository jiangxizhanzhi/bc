package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 工具新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class ToolAddReq implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	@NotBlank
	private String name;

	/**
	 * 类别
	 */
	@ApiModelProperty("分类")
	@NotBlank
	@Length(max = 255, message = CrBusinessErrorCode.MAX_7_CATEGORIES)
	private String category;

	/**
	 * 盖
	 */
	@ApiModelProperty("封面")
	private String cover;

	/**
	 * 破获
	 */
	@ApiModelProperty("是否破解")
	private Boolean cracked;

	/**
	 * 禁用
	 */
	@ApiModelProperty("是否禁用")
	private Boolean disabled;

	/**
	 * 语言
	 */
	@ApiModelProperty("语言")
	@NotBlank
	private String language;

	/**
	 * 平台
	 */
	@ApiModelProperty("平台")
	@NotBlank
	private String platforms;

	/**
	 * 版本
	 */
	@ApiModelProperty("版本")
	private String version;

	/**
	 * 介绍
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	/**
	 * 文件
	 */
	@ApiModelProperty("工具附件")
	@NotBlank
	private String files;

	/**
	 * 图像资源id
	 */
	@ApiModelProperty("靶标ID")
	private String imageResourceId;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	private String type;

	/**
	 * 标记
	 */
	@ApiModelProperty("标签")
	private List<String> tag;

	/**
	 * 工具 public
	 */
	private Boolean toolPublic = true;
}
