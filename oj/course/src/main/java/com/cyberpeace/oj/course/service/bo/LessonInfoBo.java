package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class LessonInfoBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课时编号")
	private String resourceId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否为内置课程 true=内置")
	private Boolean builtin;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型 0=理论课 1=实验课")
	private Integer type;

	/**
	 * 讲义类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("讲义类型 0=都不是 1=md 2=ppt 3=pdf")
	private Integer handoutType;

	/**
	 * 讲义 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("讲义文件地址")
	private String handoutUrl;

	/**
	 * 视频 URL
	 *
	 * @see String
	 */
	@ApiModelProperty("视频文件地址")
	private String videoUrl;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开 true=公开")
	private Boolean isPublic;

	/**
	 * 必修
	 *
	 * @see Integer
	 */
	@ApiModelProperty("0=未配置 1=选修 2=必修")
	private Integer compulsory;

	/**
	 * 期间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时时长")
	private Integer duration;

	/**
	 * 销毁延迟
	 *
	 * @see Integer
	 */
	@ApiModelProperty("销毁延时")
	private Integer destroyDelay;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

	/**
	 * 知识
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("知识点")
	private List<String> knowledge;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	@ApiModelProperty("传贱人编号")
	private String createUser;

	/**
	 * 纸id
	 *
	 * @see String
	 */
	@ApiModelProperty("试卷编号")
	private String paperId;

	/**
	 * 论文 公共
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("试卷是否公开 true=公开")
	private Boolean paperPublic;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("热度")
	private Integer heat;

	/**
	 * is 集合
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 课程档案
	 *
	 * @see List<AttachmentBo>
	 */
	@ApiModelProperty("附件")
	private List<AttachmentBo> lessonFile;

	/**
	 * 下一个
	 *
	 * @see String
	 */
	@ApiModelProperty("下一节")
	private String next;

	/**
	 * 前
	 *
	 * @see String
	 */
	@ApiModelProperty("上一节")
	private String pre;

	/**
	 * 实践计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题数量")
	private Integer practiceCount;

	/**
	 * 场景配置数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("拓扑数据")
	private JSONObject sceneConfigData;

	/**
	 * 场景数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	/**
	 * 场景配置名称
	 *
	 * @see String
	 */
	@ApiModelProperty("场景配置名称")
	private String sceneConfigName;

	/**
	 * 群id
	 *
	 * @see String
	 */
	@ApiModelProperty("当前用户所属课堂组编号")
	private String groupId;

	/**
	 * 房间id
	 *
	 * @see String
	 */
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
