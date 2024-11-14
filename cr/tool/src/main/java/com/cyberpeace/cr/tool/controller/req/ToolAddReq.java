package com.cyberpeace.cr.tool.controller.req;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 工具新增请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class ToolAddReq implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	@NotBlank
	private String name;

	/**
	 * 分类
	 */
	@ApiModelProperty("分类")
	@NotBlank
	@Length(max = 255, message = CrBusinessErrorCode.MAX_7_CATEGORIES)
	private String category;

	/**
	 * 封面
	 */
	@ApiModelProperty("封面")
	@NotBlank
	private String cover;

	/**
	 * 是否破解
	 */
	@ApiModelProperty("是否破解")
	private Boolean cracked;

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
	 * 是否可见
	 */
	@ApiModelProperty("是否可见")
	private Boolean visible;

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
	 * 工具附件
	 */
	@ApiModelProperty("工具附件")
	@NotBlank
	private String files;

	/**
	 * 工具类型
	 */
	@ApiModelProperty("工具类型")
	private Integer toolType;

	/**
	 * 靶标ID
	 */
	@ApiModelProperty("靶标ID")
	private String imageResourceId;

	/**
	 * 工具 public
	 */
	private Boolean toolPublic = true;
}
