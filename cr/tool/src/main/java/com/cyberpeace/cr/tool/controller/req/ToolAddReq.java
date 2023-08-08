package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class ToolAddReq implements Serializable {

	@ApiModelProperty("名称")
	@NotBlank
	private String name;

	@ApiModelProperty("分类")
	@NotBlank
	@Length(max = 255, message = CrBusinessErrorCode.MAX_7_CATEGORIES)
	private String category;

	@ApiModelProperty("封面")
	@NotBlank
	private String cover;

	@ApiModelProperty("是否破解")
	private Boolean cracked;

	@ApiModelProperty("语言")
	@NotBlank
	private String language;

	@ApiModelProperty("平台")
	@NotBlank
	private String platforms;

	@ApiModelProperty("是否可见")
	private Boolean visible;

	@ApiModelProperty("版本")
	private String version;

	@ApiModelProperty("介绍")
	private String introduction;

	@ApiModelProperty("工具附件")
	@NotBlank
	private String files;

	@ApiModelProperty("工具类型")
	private Integer toolType;

	@ApiModelProperty("靶标ID")
	private String imageResourceId;

	@ApiModelProperty("附件详细列表")
	private List<ToolFile> toolFiles;

	private Boolean toolPublic = true;

	@ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

	@ApiModelProperty("授权操作用户编号 ,为编号分割符")
	private String operationAuthIds;

	@ApiModelProperty("授权访问类型 1=共享 2=自定义")
	private Integer readAuthType;

	@ApiModelProperty("授权访问组织编号 ,为分割符")
	private String readAuthIds;

}
