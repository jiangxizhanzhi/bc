package com.cyberpeace.cr.tool.controller.res;

import com.cyberpeace.cr.tool.dao.entity.ToolFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel("工具库信息")
@Data
public class ToolRsp implements Serializable {

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
	private List<String> platforms;

	@ApiModelProperty("ID")
	private String resourceId;

	@ApiModelProperty("工具类型")
	private Integer toolType;

	@ApiModelProperty("工具分类")
	private List<String> category;

	@ApiModelProperty("封面")
	private String cover;

	@ApiModelProperty("封面连接")
	private String coverUrl;

	@ApiModelProperty("附件列表")
	private String files;

	@ApiModelProperty("附件详细列表")
	private List<ToolFile> toolFiles;

	@ApiModelProperty("介绍")
	private String introduction;

	@ApiModelProperty("版本")
	private String version;

	@ApiModelProperty("是否可见")
	private Boolean visible;

	@ApiModelProperty("创建时间")
	private String createTime;

	@ApiModelProperty("更新时间")
	private String updateTime;

	@ApiModelProperty("设备资源ID")
	private String deviceResourceId = "";

	@ApiModelProperty("主页")
	private String homePage = "";

	@ApiModelProperty("镜像资源ID")
	private String imageResourceId = "";

	@ApiModelProperty("手册")
	private String manual;

	@ApiModelProperty("场景ID")
	private String scene;

	@ApiModelProperty("场景数据")
	private String sceneData;

	@ApiModelProperty("标签")
	private List<String> tag;

	@ApiModelProperty("是否禁用")
	private Boolean disabled;

	@ApiModelProperty("类型， probe：探测类,attack：攻击类，control：控制类，defense：防御类")
	private String type;
}
