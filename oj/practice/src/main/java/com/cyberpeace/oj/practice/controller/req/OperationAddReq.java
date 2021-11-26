package com.cyberpeace.oj.practice.controller.req;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class OperationAddReq implements Serializable {

	@ApiModelProperty("标题")
	@NotBlank(groups = { Create.class })
	private String title;

	@ApiModelProperty(name = "practice_set_id", value = "习题集")
	@NotBlank(groups = { Create.class })
	private String practiceSetId;

	@ApiModelProperty(name = "category_id", value = "方向")
	@NotBlank(groups = { Create.class })
	private String categoryId;

	@ApiModelProperty("知识点")
	private List<String> knowledge;

	@ApiModelProperty("难度")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

	@ApiModelProperty("题目描述")
	@NotBlank(groups = { Create.class })
	private String content;

	@ApiModelProperty("附件")
	private MultipartFile file;

	@ApiModelProperty(name = "dynamic_env", value = "是否为动态环境")
	@NotNull(groups = { Create.class })
	private Boolean dynamicEnv;

	@ApiModelProperty(name = "scene_config_name", value = "场景配置名称")
	private String sceneConfigName;

	@ApiModelProperty(name = "oj_scene_config", value = "场景配置")
	private String ojSceneConfig;

	@ApiModelProperty(name = "scene_share_mode", value = "场景共享类型 1=个人 2=共享")
	private Integer sceneShareMode;

	@ApiModelProperty(name = "dynamic_flag", value = "是否为动态flag")
	private Boolean dynamicFlag;

	@ApiModelProperty(name = "destroy_delay", value = "场景存活时间")
	@NotNull(groups = { Create.class })
	private Integer destroyDelay;

	@ApiModelProperty("答案")
	private List<String> answer;

	@ApiModelProperty("分数")
	@NotNull(groups = { Create.class })
	private Double score;

	@ApiModelProperty(name = "is_public", value = "是否公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	@ApiModelProperty(name = "writeup_public", value = "题解是否公开")
	@NotNull(groups = { Create.class })
	private Boolean writeupPublic;

	@ApiModelProperty("题解")
	private String writeup;

	public void setPractice_set_id(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setDynamic_env(Boolean dynamicEnv) {
		this.dynamicEnv = dynamicEnv;
	}

	public void setScene_config_name(String sceneConfigName) {
		this.sceneConfigName = sceneConfigName;
	}

	public void setOj_scene_config(String ojSceneConfig) {
		this.ojSceneConfig = ojSceneConfig;
	}

	public void setScene_share_mode(Integer sceneShareMode) {
		this.sceneShareMode = sceneShareMode;
	}

	public void setDynamic_flag(Boolean dynamicFlag) {
		this.dynamicFlag = dynamicFlag;
	}

	public void setDestroy_delay(Integer destroyDelay) {
		this.destroyDelay = destroyDelay;
	}

	public void setIs_public(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setWriteup_public(Boolean writeupPublic) {
		this.writeupPublic = writeupPublic;
	}
}
