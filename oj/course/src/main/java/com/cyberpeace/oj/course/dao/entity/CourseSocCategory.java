package com.cyberpeace.oj.course.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsPackage: com.cyberpeace.oj.course.dao.entity
 * @Author: hhuang
 * @CreateTime: 2025/04/28  10:08
 * @Description: 课程和soc分类关系
 * @Version: 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oj_course_soc_category")
public class CourseSocCategory extends BaseTable {

    /**
     * 资源编号
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("资源编号")
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
     * soc分类id
     *
     * @see String
     */
    @ApiModelProperty("soc分类id")
    private String socCategoryId;


    /**
     * soc三级分类标签
     *
     * @see String
     */
    @ApiModelProperty("soc三级分类标签")
    private String socThirdCategory;


    /**
     * soc二级分类标签
     *
     * @see String
     */
    @ApiModelProperty("soc二级分类标签")
    private String socSecondCategory;
}
