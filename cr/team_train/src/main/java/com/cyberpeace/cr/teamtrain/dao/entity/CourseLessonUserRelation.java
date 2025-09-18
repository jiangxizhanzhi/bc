package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("course_lesson_user_relation")
public class CourseLessonUserRelation extends BaseTable {

    /**
     * 课程编号
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("uuid")
    private String resourceId;

    @ApiModelProperty("课程id")
    private String courseId;


    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课时id")
    private String lessonId;

    @ApiModelProperty("学员id")
    private String userId;

    @ApiModelProperty("是否打分，对应是否完成")
    private Boolean scoredFlag;





}
