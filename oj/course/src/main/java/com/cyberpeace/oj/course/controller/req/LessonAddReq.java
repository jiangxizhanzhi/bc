package com.cyberpeace.oj.course.controller.req;

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
    @NotBlank
    private String name;

    @ApiModelProperty("课时类型 0=理论课 1=实验课")
    @NotNull
    private Integer type;

    @ApiModelProperty("知识点")
    private List<String> knowledge;

    @ApiModelProperty("附件编号")
    private String attachments;

    @ApiModelProperty("难度 0=初级 1=中级 2=高级")
    @NotNull
    private Integer difficulty;

    @ApiModelProperty("课时时长")
    @NotNull
    private Integer duration;

    @ApiModelProperty("课时类型 0=未配置 1=选修 2=必须")
    @NotNull
    private Integer compulsory;

    @ApiModelProperty("是否公开 0=隐藏 1=公开")
    @NotNull
    private Boolean isPublic;

    @ApiModelProperty("课程编号")
    @NotBlank
    private String courseId;

    @ApiModelProperty("讲义")
    private MultipartFile handout;

    @ApiModelProperty("视频")
    private MultipartFile video;

    @ApiModelProperty("场景配置信息")
    private String ojSceneConfig;

    @ApiModelProperty("场景存活时间 单位小时")
    private Integer destroyDelay;

    @ApiModelProperty("场景隔离类型")
    private Integer sceneShareMode;

    @ApiModelProperty("习题列表")
    private String practiceList;

}
