package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 技能和知识库
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SkillAndKnowledgeBase extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("知识点id")
    private String resourceId;

    /**
     * 类别id
     */
    @ApiModelProperty("知识点类别id")
    private String categoryId;

    /**
     * 内容
     */
    @ApiModelProperty("知识点名称")
    private String content;

    /**
     * 课程
     */
    @ApiModelProperty("知识点关联课程")
    private String courses;

    /**
     * 重量
     */
    @TableField(exist = false)
    @ApiModelProperty("知识点权重")
    private Integer weight;

}
