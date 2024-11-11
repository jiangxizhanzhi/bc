package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.cs.service.context.AttachmentBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程信息 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class LessonInfoBo implements Serializable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课时编号")
	private String resourceId;

	/**
	 * 课程id
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 名称
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 内置
	 */
	@ApiModelProperty("是否为内置课程 true=内置")
	private Boolean builtin;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型 0=理论课 1=实验课")
	private Integer type;

	/**
	 * 讲义类型
	 */
	@ApiModelProperty("讲义类型 0=都不是 1=md 2=ppt 3=pdf")
	private Integer handoutType;

	/**
	 * 讲义 URL
	 */
	@ApiModelProperty("讲义文件地址")
	private String handoutUrl;

	/**
	 * 视频 URL
	 */
	@ApiModelProperty("视频文件地址")
	private String videoUrl;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开 true=公开")
	private Boolean isPublic;

	/**
	 * 必修
	 */
	@ApiModelProperty("0=未配置 1=选修 2=必修")
	private Integer compulsory;

	/**
	 * 期间
	 */
	@ApiModelProperty("课时时长")
	private Integer duration;

	/**
	 * 销毁延迟
	 */
	@ApiModelProperty("销毁延时")
	private Integer destroyDelay;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑编号")
	private String topoId;

	/**
	 * 场景共享模式
	 */
	@ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

	/**
	 * 知识
	 */
	@ApiModelProperty("知识点")
	private List<String> knowledge;

	/**
	 * 创建用户
	 */
	@ApiModelProperty("传贱人编号")
	private String createUser;

	/**
	 * 试卷id
	 */
	@ApiModelProperty("试卷编号")
	private String paperId;

	/**
	 * 论文 公共
	 */
	@ApiModelProperty("试卷是否公开 true=公开")
	private Boolean paperPublic;

	/**
	 * 热
	 */
	@ApiModelProperty("热度")
	private Integer heat;

	/**
	 * is 集合
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 课程档案
	 */
	@ApiModelProperty("附件")
	private List<AttachmentBo> lessonFile;

	/**
	 * 下一个
	 */
	@ApiModelProperty("下一节")
	private String next;

	/**
	 * 前
	 */
	@ApiModelProperty("上一节")
	private String pre;

	/**
	 * 练习计数
	 */
	@ApiModelProperty("习题数量")
	private Integer practiceCount;

	/**
	 * 场景配置数据
	 */
	@ApiModelProperty("拓扑数据")
	private JSONObject sceneConfigData;

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景数据")
	private JSONObject sceneData;

	/**
	 * 场景配置名称
	 */
	@ApiModelProperty("场景配置名称")
	private String sceneConfigName;

	/**
	 * 群id
	 */
	@ApiModelProperty("当前用户所属课堂组编号")
	private String groupId;

	/**
	 * 房间id
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
