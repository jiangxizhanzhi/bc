package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.cs.service.context.video.VideoCheckPointConfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课时信息Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class LessonInfoBo implements Serializable {

    /**
     * 课时编号
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("课时编号")
	private String resourceId;

    /**
     * 课程编号
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
     * 课时名称
     *
     * @see String
     */
    @ApiModelProperty("课时名称")
	private String name;

    /**
     * 是否为内置课程 true=内置
     *
     * @see Boolean
     */
    @ApiModelProperty("是否为内置课程 true=内置")
	private Boolean builtin;

    /**
     * 类型 0=理论课 1=实验课
     *
     * @see Integer
     */
    @ApiModelProperty("类型 0=理论课 1=实验课")
	private Integer type;

    /**
     * 讲义类型 0=都不是 1=md 2=ppt 3=pdf
     *
     * @see Integer
     */
    @ApiModelProperty("讲义类型 0=都不是 1=md 2=ppt 3=pdf")
	private Integer handoutType;

    /**
     * 讲义文件地址
     *
     * @see String
     */
    @ApiModelProperty("讲义文件地址")
	private String handoutUrl;

    /**
     * 视频文件地址
     *
     * @see String
     */
    @ApiModelProperty("视频文件地址")
	private String videoUrl;

    /**
     * 难度 0=初级 1=中级 2=高级
     *
     * @see Integer
     */
    @ApiModelProperty("难度 0=初级 1=中级 2=高级")
	private Integer difficulty;

    /**
     * 是否公开 true=公开
     *
     * @see Boolean
     */
    @ApiModelProperty("是否公开 true=公开")
	private Boolean isPublic;

    /**
     * 0=未配置 1=选修 2=必修
     *
     * @see Integer
     */
    @ApiModelProperty("0=未配置 1=选修 2=必修")
	private Integer compulsory;

    /**
     * 课时时长
     *
     * @see Integer
     */
    @ApiModelProperty("课时时长")
	private Integer duration;

    /**
     * 销毁延时
     *
     * @see Integer
     */
    @ApiModelProperty("销毁延时")
	private Integer destroyDelay;

    /**
     * 拓扑编号
     *
     * @see String
     */
    @ApiModelProperty("拓扑编号")
	private String topoId;

    /**
     * 场景共享类型
     *
     * @see Integer
     */
    @ApiModelProperty("场景共享类型")
	private Integer sceneShareMode;

    /**
     * 知识点
     *
     * @see List<String>
     */
    @ApiModelProperty("知识点")
	private List<String> knowledge;

    /**
     * 传贱人编号
     *
     * @see String
     */
    @ApiModelProperty("传贱人编号")
	private String createUser;

    /**
     * 试卷编号
     *
     * @see String
     */
    @ApiModelProperty("试卷编号")
	private String paperId;

    /**
     * 试卷是否公开 true=公开
     *
     * @see Boolean
     */
    @ApiModelProperty("试卷是否公开 true=公开")
	private Boolean paperPublic;

    /**
     * 热度
     *
     * @see Integer
     */
    @ApiModelProperty("热度")
	private Integer heat;

    /**
     * 是否收藏
     *
     * @see Boolean
     */
    @ApiModelProperty("是否收藏")
	private Boolean isCollection;

    /**
     * 附件
     *
     * @see List<AttachmentBo>
     */
    @ApiModelProperty("附件")
	private List<AttachmentBo> lessonFile;

    /**
     * 下一节
     *
     * @see String
     */
    @ApiModelProperty("下一节")
	private String next;

    /**
     * 上一节
     *
     * @see String
     */
    @ApiModelProperty("上一节")
	private String pre;

    /**
     * 习题数量
     *
     * @see Integer
     */
    @ApiModelProperty("习题数量")
	private Integer practiceCount;

    /**
     * 拓扑数据
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
     * 当前用户所属课堂组编号
     *
     * @see String
     */
    @ApiModelProperty("当前用户所属课堂组编号")
	private String groupId;

    /**
     * 课堂编号
     *
     * @see String
     */
    @ApiModelProperty("课堂编号")
	private String roomId;

    /**
     * 视频检查点
     *
     * @see VideoCheckPointConfig
     */
    @ApiModelProperty("视频检查点")
	private VideoCheckPointConfig videoCheckPointConfig;

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
