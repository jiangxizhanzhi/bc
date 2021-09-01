package com.cyberpeace.cr.tool.service.bo;
import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("工具库信息")
@Data
public class ToolBo implements Serializable {

	@ApiModelProperty("多个分类i18n名称逗号分割")
	private String categoryI18nName;

	@ApiModelProperty("是否破解")
	private Boolean cracked;

	@ApiModelProperty("靶标名称")
	private String deviceName;

	private Integer id;

	@ApiModelProperty("工具语言")
	private List<String> language;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("平台名称")
	private String platformsNames;

	@ApiModelProperty("发布时间")
	private String publishTime;

	private String resourceId;

	@ApiModelProperty("工具类型")
	private Integer toolType;

	@ApiModelProperty("工具分类")
	private List<Integer> category;

	@ApiModelProperty("封面")
	private String cover;

	@ApiModelProperty("封面连接")
	private String coverUrl;

	@ApiModelProperty("附件列表")
	private List<Integer> files;

	@ApiModelProperty("附件详细列表")
	private List<ToolFile> toolFiles;
}
