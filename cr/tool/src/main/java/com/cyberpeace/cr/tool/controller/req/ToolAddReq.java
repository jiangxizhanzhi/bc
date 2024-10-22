package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
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

	@ApiModelProperty("是否禁用")
	private Boolean disabled;

	@ApiModelProperty("语言")
	@NotBlank
	private String language;

	@ApiModelProperty("平台")
	@NotBlank
	private String platforms;

	@ApiModelProperty("版本")
	private String version;

	@ApiModelProperty("介绍")
	private String introduction;

	@ApiModelProperty("工具附件")
	@NotBlank
	private String files;

	@ApiModelProperty("靶标ID")
	private String imageResourceId;

	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	private String type;

	@ApiModelProperty("标签")
	private List<String> tag;

	private Boolean toolPublic = true;
}
