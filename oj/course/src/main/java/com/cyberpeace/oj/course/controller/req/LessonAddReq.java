package com.cyberpeace.oj.course.controller.req;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
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
public class LessonAddReq implements Serializable {

	@ApiModelProperty("课时名称")
	@NotBlank(groups = { Create.class })
	private String name;

	@ApiModelProperty("课时类型 0=理论课 1=实验课")
	@NotNull(groups = { Create.class })
	private Integer type;

	@ApiModelProperty("知识点")
	private List<String> knowledge;

	@ApiModelProperty("附件编号")
	private String attachments;

	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	@NotNull(groups = { Create.class })
	private Integer difficulty;

	@ApiModelProperty("课时时长")
	@NotNull(groups = { Create.class })
	private Integer duration;

	@ApiModelProperty("课时类型 0=未配置 1=选修 2=必须")
	@NotNull(groups = { Create.class })
	private Integer compulsory;

	@ApiModelProperty("是否公开 0=隐藏 1=公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	@ApiModelProperty("课程编号")
	@NotBlank(groups = { Create.class })
	private String courseId;

	@ApiModelProperty("讲义")
	private String handoutUrl;

	@ApiModelProperty("视频")
	private String videoUrl;

	@ApiModelProperty("场景配置信息")
	private JSONObject ojSceneConfig;

	@ApiModelProperty("场景存活时间 单位小时")
	private Integer destroyDelay;

	@ApiModelProperty("场景隔离类型 1=共享 2=个人")
	private Integer sceneShareMode;

	@ApiModelProperty("习题列表")
	private List<PaperPracticeContext> practiceList;

}
