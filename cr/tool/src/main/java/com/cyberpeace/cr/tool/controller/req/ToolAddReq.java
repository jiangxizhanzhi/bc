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
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class ToolAddReq implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	@NotBlank
	private String name;

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty("分类")
	@NotBlank
	@Length(max = 255, message = CrBusinessErrorCode.MAX_7_CATEGORIES)
	private String category;

	/**
	 * 盖
	 *
	 * @see String
	 */
	@ApiModelProperty("封面")
	@NotBlank
	private String cover;

	/**
	 * 破获
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否破解")
	private Boolean cracked;

	/**
	 * 语言
	 *
	 * @see String
	 */
	@ApiModelProperty("语言")
	@NotBlank
	private String language;

	/**
	 * 平台
	 *
	 * @see String
	 */
	@ApiModelProperty("平台")
	@NotBlank
	private String platforms;

	/**
	 * 可见
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否可见")
	private Boolean visible;

	/**
	 * 版本
	 *
	 * @see String
	 */
	@ApiModelProperty("版本")
	private String version;

	/**
	 * 介绍
	 *
	 * @see String
	 */
	@ApiModelProperty("介绍")
	private String introduction;

	/**
	 * 文件
	 *
	 * @see String
	 */
	@ApiModelProperty("工具附件")
	@NotBlank
	private String files;

	/**
	 * 工具类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("工具类型")
	private Integer toolType;

	/**
	 * 图像资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("靶标ID")
	private String imageResourceId;

	/**
	 * 工具 public
	 *
	 * @see Boolean
	 */
	private Boolean toolPublic = true;
}
