package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员课程 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AdminLessonBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
	private String resourceId;

    /**
     * 课程id
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
	private String courseId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课时名称")
	private String name;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("类型")
	private Integer type;

    /**
     * 讲义类型
     *
     * @see Integer
     */
    @ApiModelProperty("讲义类型 0=什么都不是 1=markdown 2=ppt 4=pdf")
	private Integer handoutType;

    /**
     * 讲义 URL
     *
     * @see String
     */
    @ApiModelProperty("讲义地址")
	private String handoutUrl;

    /**
     * 讲义名称
     *
     * @see String
     */
    @ApiModelProperty("讲义名称")
	private String handoutName;

    /**
     * origin markdown 文件
     *
     * @see String
     */
    @ApiModelProperty("讲义原文件地址")
	private String originMarkdownFile;

    /**
     * 视频 URL
     *
     * @see String
     */
    @ApiModelProperty("视频地址")
	private String videoUrl;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
	private Integer difficulty;

    /**
     * 是否公开
     *
     * @see Boolean
     */
    @ApiModelProperty("是否公开 true=公开 false=非公开")
	private Boolean isPublic;

    /**
     * 必修
     *
     * @see Integer
     */
    @ApiModelProperty("是否必修 0=未配置 1=选修 2=必修")
	private Integer compulsory;

    /**
     * 销毁延迟
     *
     * @see Integer
     */
    @ApiModelProperty("销毁时间")
	private Integer destroyDelay;

    /**
     * 期间
     *
     * @see Integer
     */
    @ApiModelProperty("课时时长")
	private Integer duration;

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
     * 场景配置
     *
     * @see JSONObject
     */
    @ApiModelProperty("场景配置数据")
	private JSONObject sceneConfig;

    /**
     * 实践计数
     *
     * @see Integer
     */
    @ApiModelProperty("课后练习习题数量")
	private Integer practiceCount;

    /**
     * 纸id
     *
     * @see String
     */
    @ApiModelProperty("试卷编号")
	private String paperId;

    /**
     * 知识
     *
     * @see List<String>
     */
    @ApiModelProperty("知识点")
	private List<String> knowledge;

    /**
     * 课程档案
     *
     * @see List<AttachmentBo>
     */
    @ApiModelProperty("课时附件数据")
	private List<AttachmentBo> LessonFile;

    @ApiModelProperty(value = "视频时长")
    private Integer videoDuration;

}
