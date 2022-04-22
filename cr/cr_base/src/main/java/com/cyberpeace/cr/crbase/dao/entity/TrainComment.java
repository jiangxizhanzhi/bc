package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("cr_train_comment")
public class TrainComment extends BaseTable {
    @JsonIgnore
    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 发布者id
     */
    private String userId;

    /**
     * 红蓝对抗、团队训练模板id或单兵训练业务模板id
     */
    private String templateId;

    /**
     * 训练类型，同{@link BusinessTemplate#businessType}
     */
    private Integer businessType;

    @TableField(exist = false)
    private String username;
}
