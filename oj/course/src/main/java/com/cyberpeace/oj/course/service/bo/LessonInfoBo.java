package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LessonInfoBo implements Serializable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("课程编号")
	private String courseId;

	@ApiModelProperty("课程名称")
	private String courseName;

	@ApiModelProperty("课时名称")
	private String name;

	@ApiModelProperty("是否为内置课程 true=内置")
	private Boolean builtin;

	@ApiModelProperty("类型 ")
	private Integer type;

	@ApiModelProperty("")
	private Integer handoutType;

	@ApiModelProperty("")
	private String handoutUrl;

	@ApiModelProperty("")
	private String videoUrl;

	@ApiModelProperty("")
	private Integer difficulty;

	@ApiModelProperty("")
	private Integer isPublic;

	@ApiModelProperty("0=未配置 1=选修 2=必修")
	private Integer compulsory;

	@ApiModelProperty("")
	private Integer duration;

	@ApiModelProperty("")
	private Integer destroyDelay;

	@ApiModelProperty("")
	private String topoId;

	@ApiModelProperty("")
	private Integer sceneShareMode;

	@ApiModelProperty("")
	private List<String> knowledge;

	@ApiModelProperty("")
	private String createUser;

	@ApiModelProperty("")
	private String paperId;

	@ApiModelProperty("")
	private Integer paperPublic;

	@ApiModelProperty("")
	private Integer heat;

	@ApiModelProperty("")
	private Boolean isCollection;

	@ApiModelProperty("")
	private List<AttachmentBo> lessonFile;

	@ApiModelProperty("")
	private String next;

	@ApiModelProperty("")
	private String pre;

	@ApiModelProperty("")
	private Integer practiceCount;

	@ApiModelProperty("")
	private JSONObject sceneConfigData;

	@ApiModelProperty("")
	private JSONObject sceneData;

	@ApiModelProperty("")
	private String sceneConfigName;

	/**
	 * 设置上节和下节课程编号
	 * @param infoBo
	 * @param sortList
	 */
	public void setPreAndNext(LessonInfoBo infoBo, List<String> sortList) {

		int index = sortList.indexOf(infoBo.getResourceId());
		if (index > 0 && index < sortList.size() - 1) {
			infoBo.setPre(sortList.get(index - 1));
			infoBo.setNext(sortList.get(index + 1));
		} else {
			if (index == 0) {
				infoBo.setNext(sortList.get(index + 1));
			} else if (index >= sortList.size() - 1) {
				infoBo.setPre(sortList.get(index - 1));
			}
		}
	}

}
