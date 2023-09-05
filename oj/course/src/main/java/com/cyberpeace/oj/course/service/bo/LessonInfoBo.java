package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.ojbase.service.bo.check.CheckPoint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LessonInfoBo implements Serializable {

	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课时编号")
	private String resourceId;

	@ApiModelProperty("课程编号")
	private String courseId;

	@ApiModelProperty("课程名称")
	private String courseName;

	@ApiModelProperty("课时名称")
	private String name;

	@ApiModelProperty("是否为内置课程 true=内置")
	private Boolean builtin;

	@ApiModelProperty("类型 0=理论课 1=实验课")
	private Integer type;

	@ApiModelProperty("讲义类型 0=都不是 1=md 2=ppt 3=pdf")
	private Integer handoutType;

	@ApiModelProperty("讲义文件地址")
	private String handoutUrl;

	@ApiModelProperty("视频文件地址")
	private String videoUrl;

	@ApiModelProperty(value = "视频长度")
	private String videoTime;

	@ApiModelProperty(value = "知识检查点")
	private List<CheckPoint> formKnowledgeListData;

	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	@ApiModelProperty("是否公开 true=公开")
	private Boolean isPublic;

	@ApiModelProperty("0=未配置 1=选修 2=必修")
	private Integer compulsory;

	@ApiModelProperty("课时时长")
	private Integer duration;

	@ApiModelProperty("销毁延时")
	private Integer destroyDelay;

	@ApiModelProperty("拓扑编号")
	private String topoId;

	@ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

	@ApiModelProperty("知识点")
	private List<String> knowledge;

	@ApiModelProperty("传贱人编号")
	private String createUser;

	@ApiModelProperty("试卷编号")
	private String paperId;

	@ApiModelProperty("试卷是否公开 true=公开")
	private Boolean paperPublic;

	@ApiModelProperty("热度")
	private Integer heat;

	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	@ApiModelProperty("附件")
	private List<AttachmentBo> lessonFile;

	@ApiModelProperty("下一节")
	private String next;

	@ApiModelProperty("上一节")
	private String pre;

	@ApiModelProperty("习题数量")
	private Integer practiceCount;

	@ApiModelProperty("拓扑数据")
	private JSONObject sceneConfigData;

	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	@ApiModelProperty("场景配置名称")
	private String sceneConfigName;

	@ApiModelProperty("当前用户所属课堂组编号")
	private String groupId;

	@ApiModelProperty("课堂编号")
	private String roomId;

	/**
	 * 设置上节和下节课程编号
	 * @param sortList
	 */
	public void setPreAndNext(List<String> sortList) {

		int index = sortList.indexOf(this.getResourceId());
		if (sortList.size() > 1) {
			if (index < sortList.size() - 1) {
				this.setNext(sortList.get(index + 1));
			}
			if (index > 0) {
				this.setPre(sortList.get(index - 1));
			}
		}
	}

}
